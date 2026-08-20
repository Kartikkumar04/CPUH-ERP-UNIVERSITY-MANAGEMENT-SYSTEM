package com.cpuh.model;

public class Attendance {

    // ==========================================
    // FIELDS
    // ==========================================

    private int attendanceId;

    private int studentId;

    private int subjectId;

    private int facultyId;


    // ==========================================
    // DISPLAY FIELDS
    // ==========================================

    private String rollNo;

    private String subjectCode;

    private String subjectName;

    private String facultyName;


    private String attendanceDate;

    private String status;


    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public Attendance() {
    }


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Attendance(
            int studentId,
            int subjectId,
            int facultyId,
            String attendanceDate,
            String status
    ) {

        this.studentId = studentId;

        this.subjectId = subjectId;

        this.facultyId = facultyId;

        this.attendanceDate = attendanceDate;

        this.status = status;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getAttendanceId() {

        return attendanceId;
    }


    public int getStudentId() {

        return studentId;
    }


    public int getSubjectId() {

        return subjectId;
    }


    public int getFacultyId() {

        return facultyId;
    }


    public String getRollNo() {

        return rollNo;
    }


    public String getSubjectCode() {

        return subjectCode;
    }


    public String getSubjectName() {

        return subjectName;
    }


    public String getFacultyName() {

        return facultyName;
    }


    public String getAttendanceDate() {

        return attendanceDate;
    }


    public String getStatus() {

        return status;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setAttendanceId(
            int attendanceId
    ) {

        this.attendanceId = attendanceId;
    }


    public void setStudentId(
            int studentId
    ) {

        this.studentId = studentId;
    }


    public void setSubjectId(
            int subjectId
    ) {

        this.subjectId = subjectId;
    }


    public void setFacultyId(
            int facultyId
    ) {

        this.facultyId = facultyId;
    }


    public void setRollNo(
            String rollNo
    ) {

        this.rollNo = rollNo;
    }


    public void setSubjectCode(
            String subjectCode
    ) {

        this.subjectCode = subjectCode;
    }


    public void setSubjectName(
            String subjectName
    ) {

        this.subjectName = subjectName;
    }


    public void setFacultyName(
            String facultyName
    ) {

        this.facultyName = facultyName;
    }


    public void setAttendanceDate(
            String attendanceDate
    ) {

        this.attendanceDate = attendanceDate;
    }


    public void setStatus(
            String status
    ) {

        this.status = status;
    }
}