package com.cpuh.model;

public class Course {

    // ==========================================
    // FIELDS
    // ==========================================

    private int courseId;

    private String courseName;

    private String courseCode;

    private int durationYears;

    private int totalSemesters;

    private int departmentId;

    // Department name from departments table
    private String departmentName;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Course() {
    }


    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    public int getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(int durationYears) {
        this.durationYears = durationYears;
    }


    public int getTotalSemesters() {
        return totalSemesters;
    }

    public void setTotalSemesters(int totalSemesters) {
        this.totalSemesters = totalSemesters;
    }


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }


    // ==========================================
    // DEPARTMENT NAME
    // ==========================================

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    // ==========================================
    // TO STRING
    // ==========================================

    @Override
    public String toString() {

        return courseName
                + " (" + courseCode + ")";
    }
}