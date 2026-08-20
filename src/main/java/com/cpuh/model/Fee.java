package com.cpuh.model;

public class Fee {

    private int feeId;
    private int studentId;
    private int semester;

    private double totalFee;
    private double paidAmount;
    private double dueAmount;

    private String paymentDate;
    private String paymentMode;
    private String status;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Fee() {
    }


    public Fee(
            int studentId,
            int semester,
            double totalFee,
            double paidAmount,
            double dueAmount,
            String paymentDate,
            String paymentMode,
            String status
    ) {

        this.studentId = studentId;
        this.semester = semester;
        this.totalFee = totalFee;
        this.paidAmount = paidAmount;
        this.dueAmount = dueAmount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.status = status;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getFeeId() {
        return feeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSemester() {
        return semester;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getStatus() {
        return status;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}