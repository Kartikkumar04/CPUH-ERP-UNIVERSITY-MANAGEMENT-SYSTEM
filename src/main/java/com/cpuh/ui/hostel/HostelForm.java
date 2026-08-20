package com.cpuh.ui.hostel;

import com.cpuh.dao.HostelDAO;
import com.cpuh.model.Hostel;

import javax.swing.*;
import java.awt.*;

public class HostelForm extends JDialog {

    private JTextField txtStudentId;
    private JTextField txtHostelName;
    private JTextField txtRoomNumber;
    private JTextField txtFloor;
    private JTextField txtCheckIn;
    private JTextField txtCheckOut;
    private JTextField txtHostelFee;

    private JComboBox<String> cmbRoomType;
    private JComboBox<String> cmbStatus;

    private JButton btnSave;
    private JButton btnCancel;

    private HostelDAO hostelDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public HostelForm(JFrame parent) {

        super(
                parent,
                "Add Hostel Record",
                true
        );

        hostelDAO = new HostelDAO();

        setSize(600, 650);

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
                        "Add Hostel Record"
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
        // HOSTEL NAME
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Hostel Name:"),
                gbc
        );

        txtHostelName =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtHostelName,
                gbc
        );


        // ==================================================
        // ROOM NUMBER
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Room Number:"),
                gbc
        );

        txtRoomNumber =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtRoomNumber,
                gbc
        );


        // ==================================================
        // FLOOR
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Floor:"),
                gbc
        );

        txtFloor =
                new JTextField("1");

        gbc.gridx = 1;

        mainPanel.add(
                txtFloor,
                gbc
        );


        // ==================================================
        // ROOM TYPE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Room Type:"),
                gbc
        );

        cmbRoomType =
                new JComboBox<>(
                        new String[]{
                                "Single",
                                "Double",
                                "Triple"
                        }
                );

        gbc.gridx = 1;

        mainPanel.add(
                cmbRoomType,
                gbc
        );


        // ==================================================
        // CHECK IN
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel(
                        "Check In (YYYY-MM-DD):"
                ),
                gbc
        );

        txtCheckIn =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtCheckIn,
                gbc
        );


        // ==================================================
        // CHECK OUT
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel(
                        "Check Out (YYYY-MM-DD):"
                ),
                gbc
        );

        txtCheckOut =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtCheckOut,
                gbc
        );


        // ==================================================
        // HOSTEL FEE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Hostel Fee:"),
                gbc
        );

        txtHostelFee =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtHostelFee,
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
                                "Active",
                                "Inactive"
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
                        "Save Hostel Record"
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
                e -> saveHostel()
        );

        btnCancel.addActionListener(
                e -> dispose()
        );
    }


    // ==================================================
    // SAVE HOSTEL
    // ==================================================

    private void saveHostel() {

        String studentIdText =
                txtStudentId
                        .getText()
                        .trim();

        String hostelName =
                txtHostelName
                        .getText()
                        .trim();

        String roomNumber =
                txtRoomNumber
                        .getText()
                        .trim();

        String floorText =
                txtFloor
                        .getText()
                        .trim();

        String checkIn =
                txtCheckIn
                        .getText()
                        .trim();

        String checkOut =
                txtCheckOut
                        .getText()
                        .trim();

        String feeText =
                txtHostelFee
                        .getText()
                        .trim();

        String roomType =
                String.valueOf(
                        cmbRoomType
                                .getSelectedItem()
                );

        String status =
                String.valueOf(
                        cmbStatus
                                .getSelectedItem()
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


        if (hostelName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Hostel Name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtHostelName.requestFocus();

            return;
        }


        if (roomNumber.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Room Number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtRoomNumber.requestFocus();

            return;
        }


        if (floorText.isEmpty()) {

            floorText = "1";
        }


        if (feeText.isEmpty()) {

            feeText = "0";
        }


        // ==================================================
        // PARSE NUMBERS
        // ==================================================

        int studentId;
        int floor;
        double hostelFee;


        try {

            studentId =
                    Integer.parseInt(
                            studentIdText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtStudentId.requestFocus();

            return;
        }


        try {

            floor =
                    Integer.parseInt(
                            floorText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Floor must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtFloor.requestFocus();

            return;
        }


        try {

            hostelFee =
                    Double.parseDouble(
                            feeText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hostel fee must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtHostelFee.requestFocus();

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


        if (floor < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Floor cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (hostelFee < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hostel fee cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // DATE VALIDATION
        // ==================================================

        if (!checkIn.isEmpty()) {

            try {

                java.sql.Date.valueOf(
                        checkIn
                );

            } catch (
                    IllegalArgumentException e
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Check In date must be in YYYY-MM-DD format.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                txtCheckIn.requestFocus();

                return;
            }
        }


        if (!checkOut.isEmpty()) {

            try {

                java.sql.Date.valueOf(
                        checkOut
                );

            } catch (
                    IllegalArgumentException e
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Check Out date must be in YYYY-MM-DD format.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                txtCheckOut.requestFocus();

                return;
            }
        }


        // ==================================================
        // CHECK OUT SHOULD NOT BE BEFORE CHECK IN
        // ==================================================

        if (
                !checkIn.isEmpty()
                        && !checkOut.isEmpty()
        ) {

            java.sql.Date checkInDate =
                    java.sql.Date.valueOf(
                            checkIn
                    );

            java.sql.Date checkOutDate =
                    java.sql.Date.valueOf(
                            checkOut
                    );


            if (
                    checkOutDate.before(
                            checkInDate
                    )
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Check Out date cannot be before Check In date.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }


        // ==================================================
        // CREATE HOSTEL OBJECT
        // ==================================================

        Hostel hostel =
                new Hostel();


        hostel.setStudentId(
                studentId
        );

        hostel.setHostelName(
                hostelName
        );

        hostel.setRoomNumber(
                roomNumber
        );

        hostel.setFloor(
                floor
        );

        hostel.setRoomType(
                roomType
        );

        hostel.setCheckIn(
                checkIn.isEmpty()
                        ? null
                        : checkIn
        );

        hostel.setCheckOut(
                checkOut.isEmpty()
                        ? null
                        : checkOut
        );

        hostel.setHostelFee(
                hostelFee
        );

        hostel.setStatus(
                status
        );


        // ==================================================
        // SAVE TO DATABASE
        // ==================================================

        boolean saved =
                hostelDAO.addHostel(
                        hostel
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hostel record added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add hostel record.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}