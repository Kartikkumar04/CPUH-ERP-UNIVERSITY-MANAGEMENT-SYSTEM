package com.cpuh.ui.student;

import com.cpuh.dao.StudentDAO;
import com.cpuh.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentEditForm extends JDialog {

    private JTextField txtRollNo;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> cmbGender;
    private JTextField txtDob;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JTextField txtAdmissionYear;
    private JTextField txtSemester;

    private JButton btnUpdate;
    private JButton btnCancel;

    private Student student;
    private StudentDAO studentDAO;
    private StudentPanel studentPanel;


    public StudentEditForm(
            JFrame parent,
            Student student,
            StudentDAO studentDAO,
            StudentPanel studentPanel
    ) {

        super(
                parent,
                "Edit Student",
                true
        );

        this.student = student;
        this.studentDAO = studentDAO;
        this.studentPanel = studentPanel;


        setSize(500, 600);

        setLocationRelativeTo(parent);

        setLayout(
                new BorderLayout()
        );


        initUI();

        loadStudentData();

        setVisible(true);
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void initUI() {

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );


        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        5,
                        5,
                        5,
                        5
                );


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        gbc.weightx = 1;


        // Roll Number
        addField(
                formPanel,
                gbc,
                0,
                "Roll No:",
                txtRollNo = new JTextField()
        );


        // First Name
        addField(
                formPanel,
                gbc,
                1,
                "First Name:",
                txtFirstName = new JTextField()
        );


        // Last Name
        addField(
                formPanel,
                gbc,
                2,
                "Last Name:",
                txtLastName = new JTextField()
        );


        // Gender
        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Gender:"),
                gbc
        );


        cmbGender =
                new JComboBox<>(
                        new String[]{
                                "Male",
                                "Female",
                                "Other"
                        }
                );


        gbc.gridx = 1;

        formPanel.add(
                cmbGender,
                gbc
        );


        // DOB
        addField(
                formPanel,
                gbc,
                4,
                "DOB (YYYY-MM-DD):",
                txtDob = new JTextField()
        );


        // Email
        addField(
                formPanel,
                gbc,
                5,
                "Email:",
                txtEmail = new JTextField()
        );


        // Phone
        addField(
                formPanel,
                gbc,
                6,
                "Phone:",
                txtPhone = new JTextField()
        );


        // Address
        addField(
                formPanel,
                gbc,
                7,
                "Address:",
                txtAddress = new JTextField()
        );


        // Admission Year
        addField(
                formPanel,
                gbc,
                8,
                "Admission Year:",
                txtAdmissionYear = new JTextField()
        );


        // Semester
        addField(
                formPanel,
                gbc,
                9,
                "Semester:",
                txtSemester = new JTextField()
        );


        add(
                formPanel,
                BorderLayout.CENTER
        );


        // ==========================================
        // BUTTONS
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );


        btnUpdate =
                new JButton("Update Student");


        btnCancel =
                new JButton("Cancel");


        buttonPanel.add(btnUpdate);

        buttonPanel.add(btnCancel);


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // UPDATE EVENT
        // ==========================================

        btnUpdate.addActionListener(e -> {

            updateStudent();

        });


        // ==========================================
        // CANCEL EVENT
        // ==========================================

        btnCancel.addActionListener(e -> {

            dispose();

        });
    }


    // ==========================================
    // ADD FIELD HELPER
    // ==========================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextField field
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;

        panel.add(
                new JLabel(label),
                gbc
        );


        gbc.gridx = 1;

        panel.add(
                field,
                gbc
        );
    }


    // ==========================================
    // LOAD STUDENT DATA
    // ==========================================

    private void loadStudentData() {

        txtRollNo.setText(
                safe(student.getRollNo())
        );


        txtFirstName.setText(
                safe(student.getFirstName())
        );


        txtLastName.setText(
                safe(student.getLastName())
        );


        String gender =
                student.getGender();


        if (gender != null) {

            cmbGender.setSelectedItem(
                    gender
            );
        }


        txtDob.setText(
                safe(student.getDob())
        );


        txtEmail.setText(
                safe(student.getEmail())
        );


        txtPhone.setText(
                safe(student.getPhone())
        );


        txtAddress.setText(
                safe(student.getAddress())
        );


        if (
                student.getAdmissionYear()
                        > 0
        ) {

            txtAdmissionYear.setText(
                    String.valueOf(
                            student.getAdmissionYear()
                    )
            );
        }


        txtSemester.setText(
                String.valueOf(
                        student.getSemester()
                )
        );
    }


    // ==========================================
    // UPDATE STUDENT
    // ==========================================

    private void updateStudent() {

        String rollNo =
                txtRollNo.getText().trim();


        String firstName =
                txtFirstName.getText().trim();


        String lastName =
                txtLastName.getText().trim();


        String gender =
                String.valueOf(
                        cmbGender.getSelectedItem()
                );


        String dob =
                txtDob.getText().trim();


        String email =
                txtEmail.getText().trim();


        String phone =
                txtPhone.getText().trim();


        String address =
                txtAddress.getText().trim();


        String admissionYearText =
                txtAdmissionYear.getText().trim();


        String semesterText =
                txtSemester.getText().trim();


        // ==========================================
        // VALIDATION
        // ==========================================

        if (
                rollNo.isEmpty()
                        ||
                        firstName.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Roll No and First Name are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int admissionYear = 0;

        if (!admissionYearText.isEmpty()) {

            try {

                admissionYear =
                        Integer.parseInt(
                                admissionYearText
                        );

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admission Year must be a number.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }


        int semester;

        try {

            semester =
                    Integer.parseInt(
                            semesterText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semester must be a number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==========================================
        // UPDATE OBJECT
        // ==========================================

        student.setRollNo(rollNo);

        student.setFirstName(firstName);

        student.setLastName(lastName);

        student.setGender(gender);

        student.setDob(dob);

        student.setEmail(email);

        student.setPhone(phone);

        student.setAddress(address);

        student.setAdmissionYear(
                admissionYear
        );

        student.setSemester(
                semester
        );


        // ==========================================
        // SAVE TO DATABASE
        // ==========================================

        boolean updated =
                studentDAO.updateStudent(
                        student
                );


        if (updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            studentPanel.refreshStudents();


            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update student.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // NULL SAFE STRING
    // ==========================================

    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }
}