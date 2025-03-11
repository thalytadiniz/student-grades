package com.thalytadiniz.students_grade_app;

import com.thalytadiniz.students_grade_app.exception.SheetsAccessException;
import com.thalytadiniz.students_grade_app.service.StudentService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

public class MainApp {
    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        String spreadsheetId = "1d5zWKcD0HQaPVJdxkHRBw5QgHyNkyArVlE9E4fhK92k";
        try {
            logger.info("Starting application...");
            StudentService studentService = new StudentService(spreadsheetId);
            studentService.processStudents();
            logger.info("Application finished successfully.");
        } catch (SheetsAccessException | GeneralSecurityException | IOException e) {
            logger.severe("Error processing the spreadsheet: " + e.getMessage());
        }
    }
}