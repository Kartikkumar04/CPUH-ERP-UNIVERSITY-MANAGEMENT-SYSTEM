package com.cpuh.model;

public class Student {

    // ==========================================
    // FIELDS
    // ==========================================

    private int studentId;

    private int userId;

    private String rollNo;

    private String firstName;

    private String lastName;

    private String gender;

    private String dob;

    private String email;

    private String phone;

    private String address;

    private int admissionYear;

    private int semester;

    private int departmentId;

    private int courseId;


    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public Student() {
    }


    // ==========================================
    // STUDENT ID
    // ==========================================

    public int getStudentId() {

        return studentId;
    }


    public void setStudentId(
            int studentId
    ) {

        this.studentId = studentId;
    }


    // ==========================================
    // USER ID
    // ==========================================

    public int getUserId() {

        return userId;
    }


    public void setUserId(
            int userId
    ) {

        this.userId = userId;
    }


    // ==========================================
    // ROLL NUMBER
    // ==========================================

    public String getRollNo() {

        return rollNo;
    }


    public void setRollNo(
            String rollNo
    ) {

        this.rollNo = rollNo;
    }


    // ==========================================
    // FIRST NAME
    // ==========================================

    public String getFirstName() {

        return firstName;
    }


    public void setFirstName(
            String firstName
    ) {

        this.firstName = firstName;
    }


    // ==========================================
    // LAST NAME
    // ==========================================

    public String getLastName() {

        return lastName;
    }


    public void setLastName(
            String lastName
    ) {

        this.lastName = lastName;
    }


    // ==========================================
    // GENDER
    // ==========================================

    public String getGender() {

        return gender;
    }


    public void setGender(
            String gender
    ) {

        this.gender = gender;
    }


    // ==========================================
    // DATE OF BIRTH
    // ==========================================

    public String getDob() {

        return dob;
    }


    public void setDob(
            String dob
    ) {

        this.dob = dob;
    }


    // ==========================================
    // EMAIL
    // ==========================================

    public String getEmail() {

        return email;
    }


    public void setEmail(
            String email
    ) {

        this.email = email;
    }


    // ==========================================
    // PHONE
    // ==========================================

    public String getPhone() {

        return phone;
    }


    public void setPhone(
            String phone
    ) {

        this.phone = phone;
    }


    // ==========================================
    // ADDRESS
    // ==========================================

    public String getAddress() {

        return address;
    }


    public void setAddress(
            String address
    ) {

        this.address = address;
    }


    // ==========================================
    // ADMISSION YEAR
    // ==========================================

    public int getAdmissionYear() {

        return admissionYear;
    }


    public void setAdmissionYear(
            int admissionYear
    ) {

        this.admissionYear = admissionYear;
    }


    // ==========================================
    // SEMESTER
    // ==========================================

    public int getSemester() {

        return semester;
    }


    public void setSemester(
            int semester
    ) {

        this.semester = semester;
    }


    // ==========================================
    // DEPARTMENT ID
    // ==========================================

    public int getDepartmentId() {

        return departmentId;
    }


    public void setDepartmentId(
            int departmentId
    ) {

        this.departmentId = departmentId;
    }


    // ==========================================
    // COURSE ID
    // ==========================================

    public int getCourseId() {

        return courseId;
    }


    public void setCourseId(
            int courseId
    ) {

        this.courseId = courseId;
    }
}