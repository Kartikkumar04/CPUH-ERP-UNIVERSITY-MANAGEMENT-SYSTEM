package com.cpuh.ui.transport;

import com.cpuh.dao.TransportDAO;
import com.cpuh.model.Transport;

import javax.swing.*;
import java.awt.*;

public class TransportForm extends JDialog {

    private JTextField txtStudentId;
    private JTextField txtBusNumber;
    private JTextField txtRouteName;
    private JTextField txtPickupPoint;
    private JTextField txtDriverName;
    private JTextField txtDriverPhone;
    private JTextField txtTransportFee;

    private JComboBox<String> cmbStatus;

    private JButton btnSave;
    private JButton btnCancel;

    private TransportDAO transportDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public TransportForm(JFrame parent) {

        super(
                parent,
                "Add Transport Record",
                true
        );

        transportDAO = new TransportDAO();

        setSize(600, 620);

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
                        "Add Transport Record"
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
        // BUS NUMBER
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Bus Number:"),
                gbc
        );

        txtBusNumber =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtBusNumber,
                gbc
        );


        // ==================================================
        // ROUTE NAME
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Route Name:"),
                gbc
        );

        txtRouteName =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtRouteName,
                gbc
        );


        // ==================================================
        // PICKUP POINT
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Pickup Point:"),
                gbc
        );

        txtPickupPoint =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtPickupPoint,
                gbc
        );


        // ==================================================
        // DRIVER NAME
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Driver Name:"),
                gbc
        );

        txtDriverName =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtDriverName,
                gbc
        );


        // ==================================================
        // DRIVER PHONE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Driver Phone:"),
                gbc
        );

        txtDriverPhone =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtDriverPhone,
                gbc
        );


        // ==================================================
        // TRANSPORT FEE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Transport Fee:"),
                gbc
        );

        txtTransportFee =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtTransportFee,
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
                        "Save Transport Record"
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
                e -> saveTransport()
        );

        btnCancel.addActionListener(
                e -> dispose()
        );
    }


    // ==================================================
    // SAVE TRANSPORT
    // ==================================================

    private void saveTransport() {

        String studentIdText =
                txtStudentId
                        .getText()
                        .trim();

        String busNumber =
                txtBusNumber
                        .getText()
                        .trim();

        String routeName =
                txtRouteName
                        .getText()
                        .trim();

        String pickupPoint =
                txtPickupPoint
                        .getText()
                        .trim();

        String driverName =
                txtDriverName
                        .getText()
                        .trim();

        String driverPhone =
                txtDriverPhone
                        .getText()
                        .trim();

        String feeText =
                txtTransportFee
                        .getText()
                        .trim();

        String status =
                String.valueOf(
                        cmbStatus.getSelectedItem()
                );


        // ==================================================
        // VALIDATION
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


        if (feeText.isEmpty()) {

            feeText = "0";
        }


        int studentId;

        double transportFee;


        // ==================================================
        // STUDENT ID
        // ==================================================

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


        // ==================================================
        // TRANSPORT FEE
        // ==================================================

        try {

            transportFee =
                    Double.parseDouble(
                            feeText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Transport fee must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtTransportFee.requestFocus();

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


        if (transportFee < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Transport fee cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // CREATE TRANSPORT OBJECT
        // ==================================================

        Transport transport =
                new Transport();


        transport.setStudentId(
                studentId
        );

        transport.setBusNumber(
                busNumber
        );

        transport.setRouteName(
                routeName
        );

        transport.setPickupPoint(
                pickupPoint
        );

        transport.setDriverName(
                driverName
        );

        transport.setDriverPhone(
                driverPhone
        );

        transport.setTransportFee(
                transportFee
        );

        transport.setStatus(
                status
        );


        // ==================================================
        // SAVE TO DATABASE
        // ==================================================

        boolean saved =
                transportDAO.addTransport(
                        transport
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Transport record added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add transport record.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}