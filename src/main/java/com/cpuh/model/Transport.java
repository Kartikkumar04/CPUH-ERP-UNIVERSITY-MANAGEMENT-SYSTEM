package com.cpuh.model;

public class Transport {

    private int transportId;
    private int studentId;

    private String busNumber;
    private String routeName;
    private String pickupPoint;
    private String driverName;
    private String driverPhone;

    private double transportFee;

    private String status;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Transport() {
    }


    public Transport(
            int studentId,
            String busNumber,
            String routeName,
            String pickupPoint,
            String driverName,
            String driverPhone,
            double transportFee,
            String status
    ) {

        this.studentId = studentId;
        this.busNumber = busNumber;
        this.routeName = routeName;
        this.pickupPoint = pickupPoint;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.transportFee = transportFee;
        this.status = status;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getTransportId() {
        return transportId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public double getTransportFee() {
        return transportFee;
    }

    public String getStatus() {
        return status;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public void setTransportFee(double transportFee) {
        this.transportFee = transportFee;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}