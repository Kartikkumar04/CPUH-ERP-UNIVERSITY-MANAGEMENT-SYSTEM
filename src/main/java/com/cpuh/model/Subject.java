package com.cpuh.model;

public class Subject {

    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private int semester;
    private int credits;
    private int courseId;
    private int facultyId;


    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public Subject() {
    }


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Subject(
            String subjectCode,
            String subjectName,
            int semester,
            int credits,
            int courseId,
            int facultyId
    ) {

        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semester = semester;
        this.credits = credits;
        this.courseId = courseId;
        this.facultyId = facultyId;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public int getCredits() {
        return credits;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getFacultyId() {
        return facultyId;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }


    // ==========================================
    // TO STRING
    // ==========================================

    @Override
    public String toString() {

        return subjectCode
                + " - "
                + subjectName;
    }
}