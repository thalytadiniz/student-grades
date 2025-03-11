package com.thalytadiniz.students_grade_app.model;

public class Student {
    private String name;
    private double p1;
    private double p2;
    private double p3;
    private int totalClasses;
    private int absences;
    private String status;
    private double finalNote;

    public Student(String name, double p1, double p2, double p3, int totalClasses, int absences) {
        this.name = name;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.totalClasses = totalClasses;
        this.absences = absences;
    }

    public String getName() {
        return name;
    }

    public double getP1() {
        return p1;
    }

    public double getP2() {
        return p2;
    }

    public double getP3() {
        return p3;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getAbsences() {
        return absences;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFinalNote() {
        return finalNote;
    }

    public void setFinalNote(double finalNote) {
        this.finalNote = finalNote;
    }
}