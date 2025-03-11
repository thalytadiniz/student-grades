package com.thalytadiniz.students_grade_app.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.thalytadiniz.students_grade_app.config.SheetsConfig;
import com.thalytadiniz.students_grade_app.exception.SheetsAccessException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StudentService {

    private static final Logger logger = Logger.getLogger(StudentService.class.getName());

    private final Sheets sheetsService;
    private final String spreadsheetId;
    private final String sheetName = "engenharia_de_software"; 
    private final String range = sheetName + "!A4:H27"; 
    private final int totalClasses = 60; 
    private final int absenceLimit = (int) (totalClasses * 1.25); 

    public StudentService(String spreadsheetId) throws GeneralSecurityException, IOException {
        this.spreadsheetId = spreadsheetId;
        this.sheetsService = SheetsConfig.getSheetsService(); 
        logger.info("StudentService initialized successfully. Spreadsheet ID: " + spreadsheetId); 
    }

    public void processStudents() throws SheetsAccessException, IOException {
        logger.info("Starting student processing..."); 

        List<List<Object>> studentsData = getStudentsData();
        logger.info("Student data read successfully. Total students: " + studentsData.size()); 

        List<List<Object>> processedData = calculateStudentStatus(studentsData);
        logger.info("Student status calculated successfully."); 

        updateStudentsData(processedData);
        logger.info("Spreadsheet updated successfully."); 
    }

    private List<List<Object>> getStudentsData() throws IOException {
        logger.info("Reading spreadsheet data..."); 
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }

    private List<List<Object>> calculateStudentStatus(List<List<Object>> studentsData) {
        logger.info("Calculating student status..."); 
        List<List<Object>> processedData = new ArrayList<>();

        for (List<Object> row : studentsData) {
            try {
                String registration = (String) row.get(0);
                String name = (String) row.get(1);
                int absences = Integer.parseInt((String) row.get(2));
                double p1 = Double.parseDouble((String) row.get(3));
                double p2 = Double.parseDouble((String) row.get(4));
                double p3 = Double.parseDouble((String) row.get(5));

                String status;
                double finalApprovalGrade = 0;

                if (absences > absenceLimit) { 
                    status = "AbsentFailed for Absence";
                    logger.warning("Student " + name + " (Registration: " + registration + ") failed due to absences."); 
                } else {
                    double average = (p1 + p2 + p3) / 3;

                    if (average < 50) {
                        status = "failed by grade";
                        logger.warning("Student " + name + " (Registration: " + registration + ") failed by grade."); 
                    } else if (average >= 50 && average < 70) {
                        status = "final test";
                        finalApprovalGrade = 100 - average; 
                        logger.info("Student " + name + " (Registration: " + registration + ") needs a final test. Required grade: " + finalApprovalGrade); 
                    } else {
                        status = "approved";
                        logger.info("Student " + name + " (Registration: " + registration + ") approved."); 
                    }
                }

                List<Object> processedRow = new ArrayList<>(row);
                processedRow.add(status);
                processedRow.add(finalApprovalGrade);
                processedData.add(processedRow);
            } catch (NumberFormatException e) {
                logger.severe("Error processing row: " + row + ". Reason: " + e.getMessage()); 
            }
        }

        return processedData;
    }

    private void updateStudentsData(List<List<Object>> processedData) throws IOException {
        logger.info("Updating spreadsheet with results..."); 
        ValueRange body = new ValueRange().setValues(processedData);
        sheetsService.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();
    }
}