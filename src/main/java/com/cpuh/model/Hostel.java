package com.cpuh.model;

public class Hostel {

    private int hostelId;
    private int studentId;

    private String hostelName;
    private String roomNumber;

    private int floor;

    private String roomType;

    private String checkIn;
    private String checkOut;

    private double hostelFee;

    private String status;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Hostel() {
    }


    public Hostel(
            int studentId,
            String hostelName,
            String roomNumber,
            int floor,
            String roomType,
            String checkIn,
            String checkOut,
            double hostelFee,
            String status
    ) {

        this.studentId = studentId;
        this.hostelName = hostelName;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hostelFee = hostelFee;
        this.status = status;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getHostelId() {
        return hostelId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public double getHostelFee() {
        return hostelFee;
    }

    public String getStatus() {
        return status;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setHostelFee(double hostelFee) {
        this.hostelFee = hostelFee;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}