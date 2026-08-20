package com.cpuh.ui.fees;

import com.cpuh.dao.FeeDAO;
import com.cpuh.db.DBConnection;
import com.cpuh.model.Fee;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class FeeForm extends JDialog {

    private JComboBox<StudentItem> cmbStudent;
    private JComboBox<Integer> cmbSemester;

    private JTextField txtTotalFee;
    private JTextField txtPaidAmount;
    private JTextField txtDueAmount;
    private JTextField txtPaymentDate;

    private JComboBox<String> cmbPaymentMode;
    private JComboBox<String> cmbStatus;

    private JButton btnSave;
    private JButton btnCancel;

    private FeeDAO feeDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public FeeForm(JFrame parent) {

        super(
                parent,
                "Add Fee",
                true
        );

        feeDAO = new FeeDAO();

        setSize(600, 650);

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        initUI();

        loadStudents();

        setVisible(true);
    }


    // ==================================================
    // INITIALIZE UI
    // ==================================================

    private void initUI() {

        JPanel mainPanel =
                new JPanel(
                        new GridBagLayout()
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // ==================================================
        // TITLE
        // ==================================================

        JLabel title =
                new JLabel("Add Fee");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(
                title,
                gbc
        );


        // ==================================================
        // STUDENT
        // ==================================================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Student:"),
                gbc
        );


        cmbStudent =
                new JComboBox<>();

        gbc.gridx = 1;

        mainPanel.add(
                cmbStudent,
                gbc
        );


        // ==================================================
        // SEMESTER
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Semester:"),
                gbc
        );


        cmbSemester =
                new JComboBox<>();


        for (int i = 1; i <= 8; i++) {

            cmbSemester.addItem(i);
        }


        gbc.gridx = 1;

        mainPanel.add(
                cmbSemester,
                gbc
        );


        // ==================================================
        // TOTAL FEE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Total Fee:"),
                gbc
        );


        txtTotalFee =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtTotalFee,
                gbc
        );


        // ==================================================
        // PAID AMOUNT
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Paid Amount:"),
                gbc
        );


        txtPaidAmount =
                new JTextField("0");


        gbc.gridx = 1;

        mainPanel.add(
                txtPaidAmount,
                gbc
        );


        // ==================================================
        // DUE AMOUNT
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Due Amount:"),
                gbc
        );


        txtDueAmount =
                new JTextField();

        txtDueAmount.setEditable(false);

        txtDueAmount.setBackground(
                new Color(235, 235, 235)
        );


        gbc.gridx = 1;

        mainPanel.add(
                txtDueAmount,
                gbc
        );


        // ==================================================
        // PAYMENT DATE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel(
                        "Payment Date (YYYY-MM-DD):"
                ),
                gbc
        );


        txtPaymentDate =
                new JTextField(
                        LocalDate.now().toString()
                );


        gbc.gridx = 1;

        mainPanel.add(
                txtPaymentDate,
                gbc
        );


        // ==================================================
        // PAYMENT MODE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Payment Mode:"),
                gbc
        );


        cmbPaymentMode =
                new JComboBox<>(
                        new String[]{
                                "Cash",
                                "UPI",
                                "Card",
                                "Bank Transfer"
                        }
                );


        gbc.gridx = 1;

        mainPanel.add(
                cmbPaymentMode,
                gbc
        );


        // ==================================================
        // STATUS
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Status:"),
                gbc
        );


        cmbStatus =
                new JComboBox<>(
                        new String[]{
                                "Paid",
                                "Partial",
                                "Pending"
                        }
                );


        gbc.gridx = 1;

        mainPanel.add(
                cmbStatus,
                gbc
        );


        // ==================================================
        // BUTTONS
        // ==================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );


        btnSave =
                new JButton(
                        "Save Fee"
                );


        btnCancel =
                new JButton(
                        "Cancel"
                );


        buttonPanel.add(
                btnSave
        );

        buttonPanel.add(
                btnCancel
        );


        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        mainPanel.add(
                buttonPanel,
                gbc
        );


        add(mainPanel);


        // ==================================================
        // AUTOMATIC DUE CALCULATION
        // ==================================================

        txtTotalFee.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                calculateDue();
                            }

                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                calculateDue();
                            }

                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                calculateDue();
                            }
                        }
                );


        txtPaidAmount.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                calculateDue();
                            }

                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                calculateDue();
                            }

                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                calculateDue();
                            }
                        }
                );


        // ==================================================
        // BUTTON EVENTS
        // ==================================================

        btnSave.addActionListener(
                e -> saveFee()
        );


        btnCancel.addActionListener(
                e -> dispose()
        );
    }


    // ==================================================
    // LOAD STUDENTS
    // ==================================================

    private void loadStudents() {

        String sql = """
                SELECT
                    student_id,
                    roll_no,
                    first_name,
                    last_name
                FROM students
                ORDER BY first_name, last_name
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            cmbStudent.removeAllItems();


            while (rs.next()) {

                int studentId =
                        rs.getInt(
                                "student_id"
                        );

                String rollNo =
                        rs.getString(
                                "roll_no"
                        );

                String firstName =
                        rs.getString(
                                "first_name"
                        );

                String lastName =
                        rs.getString(
                                "last_name"
                        );


                cmbStudent.addItem(
                        new StudentItem(
                                studentId,
                                rollNo,
                                firstName,
                                lastName
                        )
                );
            }


            if (
                    cmbStudent.getItemCount()
                            == 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "No students found.\n\n"
                                + "Please add students first.",
                        "No Students",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load students.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==================================================
    // CALCULATE DUE
    // ==================================================

    private void calculateDue() {

        try {

            double total =
                    Double.parseDouble(
                            txtTotalFee
                                    .getText()
                                    .trim()
                    );

            double paid =
                    Double.parseDouble(
                            txtPaidAmount
                                    .getText()
                                    .trim()
                    );


            double due =
                    total - paid;


            if (due < 0) {

                due = 0;
            }


            txtDueAmount.setText(
                    String.format(
                            "%.2f",
                            due
                    )
            );

        } catch (Exception e) {

            txtDueAmount.setText(
                    "0.00"
            );
        }
    }


    // ==================================================
    // SAVE FEE
    // ==================================================

    private void saveFee() {

        StudentItem selectedStudent =
                (StudentItem)
                        cmbStudent
                                .getSelectedItem();


        Integer semester =
                (Integer)
                        cmbSemester
                                .getSelectedItem();


        String totalFeeText =
                txtTotalFee
                        .getText()
                        .trim();


        String paidAmountText =
                txtPaidAmount
                        .getText()
                        .trim();


        String paymentDate =
                txtPaymentDate
                        .getText()
                        .trim();


        String paymentMode =
                String.valueOf(
                        cmbPaymentMode
                                .getSelectedItem()
                );


        String status =
                String.valueOf(
                        cmbStatus
                                .getSelectedItem()
                );


        // ==================================================
        // VALIDATION
        // ==================================================

        if (selectedStudent == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (semester == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select semester.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (totalFeeText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter total fee.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtTotalFee.requestFocus();

            return;
        }


        if (paidAmountText.isEmpty()) {

            paidAmountText = "0";
        }


        double totalFee;
        double paidAmount;


        try {

            totalFee =
                    Double.parseDouble(
                            totalFeeText
                    );

            paidAmount =
                    Double.parseDouble(
                            paidAmountText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fee amounts must be valid numbers.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (totalFee <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Total fee must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (paidAmount < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Paid amount cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (paidAmount > totalFee) {

            JOptionPane.showMessageDialog(
                    this,
                    "Paid amount cannot be greater "
                            + "than total fee.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // VALIDATE DATE
        // ==================================================

        if (!paymentDate.isEmpty()) {

            try {

                java.sql.Date.valueOf(
                        paymentDate
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid payment date.\n\n"
                                + "Use format: YYYY-MM-DD",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }


        // ==================================================
        // CALCULATE DUE
        // ==================================================

        double dueAmount =
                totalFee - paidAmount;


        if (dueAmount < 0) {

            dueAmount = 0;
        }


        // ==================================================
        // AUTOMATIC STATUS
        // ==================================================

        if (paidAmount == 0) {

            status = "Pending";

        } else if (paidAmount < totalFee) {

            status = "Partial";

        } else {

            status = "Paid";
        }


        cmbStatus.setSelectedItem(
                status
        );


        // ==================================================
        // CREATE FEE OBJECT
        // ==================================================

        Fee fee =
                new Fee();


        fee.setStudentId(
                selectedStudent.getStudentId()
        );


        fee.setSemester(
                semester
        );


        fee.setTotalFee(
                totalFee
        );


        fee.setPaidAmount(
                paidAmount
        );


        fee.setDueAmount(
                dueAmount
        );


        fee.setPaymentDate(
                paymentDate
        );


        fee.setPaymentMode(
                paymentMode
        );


        fee.setStatus(
                status
        );


        // ==================================================
        // SAVE DATABASE
        // ==================================================

        boolean saved =
                feeDAO.addFee(
                        fee
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fee added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add fee.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==================================================
    // STUDENT ITEM
    // ==================================================

    private static class StudentItem {

        private int studentId;
        private String rollNo;
        private String firstName;
        private String lastName;


        public StudentItem(
                int studentId,
                String rollNo,
                String firstName,
                String lastName
        ) {

            this.studentId = studentId;

            this.rollNo = rollNo;

            this.firstName = firstName;

            this.lastName = lastName;
        }


        public int getStudentId() {

            return studentId;
        }


        @Override
        public String toString() {

            return rollNo
                    + " - "
                    + firstName
                    + " "
                    + lastName;
        }
    }
}