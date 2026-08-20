package com.cpuh.ui.attendance;

import com.cpuh.dao.AttendanceDAO;
import com.cpuh.model.Attendance;

import javax.swing.*;
import java.awt.*;

public class AttendanceForm extends JDialog {

    private JTextField txtStudentId;
    private JTextField txtSubjectId;
    private JTextField txtFacultyId;
    private JTextField txtAttendanceDate;

    private JComboBox<String> cmbStatus;

    private JButton btnSave;
    private JButton btnCancel;

    private AttendanceDAO attendanceDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public AttendanceForm(JFrame parent) {

        super(
                parent,
                "Add Attendance",
                true
        );

        attendanceDAO =
                new AttendanceDAO();

        setSize(550, 500);

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        initUI();

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
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // ==================================================
        // TITLE
        // ==================================================

        JLabel title =
                new JLabel(
                        "Add Attendance"
                );

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
        // STUDENT ID
        // ==================================================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Student ID:"),
                gbc
        );

        txtStudentId =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtStudentId,
                gbc
        );


        // ==================================================
        // SUBJECT ID
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Subject ID:"),
                gbc
        );

        txtSubjectId =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtSubjectId,
                gbc
        );


        // ==================================================
        // FACULTY ID
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Faculty ID:"),
                gbc
        );

        txtFacultyId =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtFacultyId,
                gbc
        );


        // ==================================================
        // ATTENDANCE DATE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel(
                        "Attendance Date:"
                ),
                gbc
        );

        txtAttendanceDate =
                new JTextField();

        txtAttendanceDate.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        gbc.gridx = 1;

        mainPanel.add(
                txtAttendanceDate,
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
                                "Present",
                                "Absent",
                                "Leave"
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
                        "Save Attendance"
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
        // BUTTON EVENTS
        // ==================================================

        btnSave.addActionListener(
                e -> saveAttendance()
        );

        btnCancel.addActionListener(
                e -> dispose()
        );
    }


    // ==================================================
    // SAVE ATTENDANCE
    // ==================================================

    private void saveAttendance() {

        String studentIdText =
                txtStudentId
                        .getText()
                        .trim();

        String subjectIdText =
                txtSubjectId
                        .getText()
                        .trim();

        String facultyIdText =
                txtFacultyId
                        .getText()
                        .trim();

        String attendanceDate =
                txtAttendanceDate
                        .getText()
                        .trim();

        String status =
                String.valueOf(
                        cmbStatus.getSelectedItem()
                );


        // ==================================================
        // REQUIRED FIELD VALIDATION
        // ==================================================

        if (studentIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student ID.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtStudentId.requestFocus();

            return;
        }


        if (subjectIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Subject ID.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtSubjectId.requestFocus();

            return;
        }


        if (facultyIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Faculty ID.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtFacultyId.requestFocus();

            return;
        }


        if (attendanceDate.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Attendance Date.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtAttendanceDate.requestFocus();

            return;
        }


        // ==================================================
        // PARSE IDs
        // ==================================================

        int studentId;
        int subjectId;
        int facultyId;


        try {

            studentId =
                    Integer.parseInt(
                            studentIdText
                    );

            subjectId =
                    Integer.parseInt(
                            subjectIdText
                    );

            facultyId =
                    Integer.parseInt(
                            facultyIdText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID, Subject ID and Faculty ID "
                            + "must be valid numbers.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (studentId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (subjectId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Subject ID must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (facultyId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Faculty ID must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // DATE VALIDATION
        // ==================================================

        try {

            java.sql.Date.valueOf(
                    attendanceDate
            );

        } catch (
                IllegalArgumentException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Date must be in YYYY-MM-DD format.\n\n"
                            + "Example: 2026-08-09",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            txtAttendanceDate.requestFocus();

            return;
        }


        // ==================================================
        // CREATE ATTENDANCE OBJECT
        // ==================================================

        Attendance attendance =
                new Attendance();

        attendance.setStudentId(
                studentId
        );

        attendance.setSubjectId(
                subjectId
        );

        attendance.setFacultyId(
                facultyId
        );

        attendance.setAttendanceDate(
                attendanceDate
        );

        attendance.setStatus(
                status
        );


        // ==================================================
        // SAVE TO DATABASE
        // ==================================================

        boolean saved =
                attendanceDAO.addAttendance(
                        attendance
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Attendance added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add attendance.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}