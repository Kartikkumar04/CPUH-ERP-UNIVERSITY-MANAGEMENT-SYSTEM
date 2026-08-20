package com.cpuh.model;

public class Examination {

    private int examId;
    private String examName;
    private String examType;
    private int subjectId;
    private int semester;
    private String examDate;
    private int totalMarks;
    private int passingMarks;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Examination() {
    }


    public Examination(
            String examName,
            String examType,
            int subjectId,
            int semester,
            String examDate,
            int totalMarks,
            int passingMarks
    ) {

        this.examName = examName;
        this.examType = examType;
        this.subjectId = subjectId;
        this.semester = semester;
        this.examDate = examDate;
        this.totalMarks = totalMarks;
        this.passingMarks = passingMarks;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getExamId() {
        return examId;
    }


    public String getExamName() {
        return examName;
    }


    public String getExamType() {
        return examType;
    }


    public int getSubjectId() {
        return subjectId;
    }


    public int getSemester() {
        return semester;
    }


    public String getExamDate() {
        return examDate;
    }


    public int getTotalMarks() {
        return totalMarks;
    }


    public int getPassingMarks() {
        return passingMarks;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setExamId(int examId) {
        this.examId = examId;
    }


    public void setExamName(String examName) {
        this.examName = examName;
    }


    public void setExamType(String examType) {
        this.examType = examType;
    }


    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }


    public void setSemester(int semester) {
        this.semester = semester;
    }


    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }


    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }


    public void setPassingMarks(int passingMarks) {
        this.passingMarks = passingMarks;
    }
}