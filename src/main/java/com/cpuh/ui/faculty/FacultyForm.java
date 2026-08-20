package com.cpuh.ui.faculty;

import com.cpuh.dao.FacultyDAO;
import com.cpuh.model.Faculty;

import javax.swing.*;
import java.awt.*;

public class FacultyForm extends JDialog {

    private JTextField txtEmployeeId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> cmbGender;
    private JTextField txtDob;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtQualification;
    private JTextField txtDesignation;
    private JTextField txtSalary;
    private JTextField txtJoiningDate;

    private JComboBox<DepartmentItem> cmbDepartment;

    private JButton btnSave;
    private JButton btnCancel;

    private FacultyDAO facultyDAO;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public FacultyForm(JFrame parent) {

        super(parent, "Add Faculty", true);

        facultyDAO = new FacultyDAO();

        setSize(600, 650);
        setLocationRelativeTo(parent);

        initUI();

        loadDepartments();

        // IMPORTANT:
        // Show the Faculty Form
        setVisible(true);
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void initUI() {

        JPanel mainPanel = new JPanel();

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        mainPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6,
                        6,
                        6,
                        6
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        int row = 0;


        // ==========================================
        // TITLE
        // ==========================================

        JLabel title =
                new JLabel("Add Faculty");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;

        mainPanel.add(
                title,
                gbc
        );

        row++;

        gbc.gridwidth = 1;


        // ==========================================
        // EMPLOYEE ID
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Employee ID:"),
                gbc
        );

        txtEmployeeId =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtEmployeeId,
                gbc
        );

        row++;


        // ==========================================
        // FIRST NAME
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("First Name:"),
                gbc
        );

        txtFirstName =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtFirstName,
                gbc
        );

        row++;


        // ==========================================
        // LAST NAME
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Last Name:"),
                gbc
        );

        txtLastName =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtLastName,
                gbc
        );

        row++;


        // ==========================================
        // GENDER
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
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

        mainPanel.add(
                cmbGender,
                gbc
        );

        row++;


        // ==========================================
        // DOB
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel(
                        "DOB (YYYY-MM-DD):"
                ),
                gbc
        );

        txtDob =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtDob,
                gbc
        );

        row++;


        // ==========================================
        // EMAIL
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Email:"),
                gbc
        );

        txtEmail =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtEmail,
                gbc
        );

        row++;


        // ==========================================
        // PHONE
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Phone:"),
                gbc
        );

        txtPhone =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtPhone,
                gbc
        );

        row++;


        // ==========================================
        // QUALIFICATION
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Qualification:"),
                gbc
        );

        txtQualification =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtQualification,
                gbc
        );

        row++;


        // ==========================================
        // DESIGNATION
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Designation:"),
                gbc
        );

        txtDesignation =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtDesignation,
                gbc
        );

        row++;


        // ==========================================
        // SALARY
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Salary:"),
                gbc
        );

        txtSalary =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtSalary,
                gbc
        );

        row++;


        // ==========================================
        // JOINING DATE
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel(
                        "Joining Date (YYYY-MM-DD):"
                ),
                gbc
        );

        txtJoiningDate =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtJoiningDate,
                gbc
        );

        row++;


        // ==========================================
        // DEPARTMENT
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = row;

        mainPanel.add(
                new JLabel("Department:"),
                gbc
        );

        cmbDepartment =
                new JComboBox<>();

        gbc.gridx = 1;

        mainPanel.add(
                cmbDepartment,
                gbc
        );

        row++;


        // ==========================================
        // BUTTONS
        // ==========================================

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
                        "Save Faculty"
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
        gbc.gridy = row;
        gbc.gridwidth = 2;

        mainPanel.add(
                buttonPanel,
                gbc
        );


        // ==========================================
        // EVENTS
        // ==========================================

        btnSave.addActionListener(
                e -> saveFaculty()
        );

        btnCancel.addActionListener(
                e -> dispose()
        );


        // ==========================================
        // ADD PANEL
        // ==========================================

        add(
                new JScrollPane(
                        mainPanel
                )
        );
    }


    // ==========================================
    // LOAD DEPARTMENTS
    // ==========================================

    private void loadDepartments() {

        cmbDepartment.removeAllItems();

        String sql =
                "SELECT department_id, department_name " +
                        "FROM departments " +
                        "ORDER BY department_name";

        try (
                java.sql.Connection con =
                        com.cpuh.db.DBConnection
                                .getConnection();

                java.sql.PreparedStatement pst =
                        con.prepareStatement(sql);

                java.sql.ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                cmbDepartment.addItem(
                        new DepartmentItem(
                                rs.getInt(
                                        "department_id"
                                ),
                                rs.getString(
                                        "department_name"
                                )
                        )
                );
            }

            // No departments found
            if (cmbDepartment.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No departments found.\n\n"
                                + "Please create a department "
                                + "first.",
                        "No Departments",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load departments.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // SAVE FACULTY
    // ==========================================

    private void saveFaculty() {

        String employeeId =
                txtEmployeeId
                        .getText()
                        .trim();

        String firstName =
                txtFirstName
                        .getText()
                        .trim();

        String lastName =
                txtLastName
                        .getText()
                        .trim();

        String email =
                txtEmail
                        .getText()
                        .trim();

        String salaryText =
                txtSalary
                        .getText()
                        .trim();


        // ==========================================
        // REQUIRED FIELDS
        // ==========================================

        if (employeeId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Employee ID.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtEmployeeId.requestFocus();

            return;
        }


        if (firstName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter First Name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtFirstName.requestFocus();

            return;
        }


        if (salaryText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Salary.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtSalary.requestFocus();

            return;
        }


        // ==========================================
        // DEPARTMENT
        // ==========================================

        DepartmentItem department =
                (DepartmentItem)
                        cmbDepartment
                                .getSelectedItem();

        if (department == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department.",
                    "Department Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==========================================
        // SALARY
        // ==========================================

        double salary;

        try {

            salary =
                    Double.parseDouble(
                            salaryText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Salary must be a valid number.",
                    "Invalid Salary",
                    JOptionPane.ERROR_MESSAGE
            );

            txtSalary.requestFocus();

            return;
        }


        if (salary < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Salary cannot be negative.",
                    "Invalid Salary",
                    JOptionPane.ERROR_MESSAGE
            );

            txtSalary.requestFocus();

            return;
        }


        // ==========================================
        // DATE VALIDATION
        // ==========================================

        String dob =
                txtDob
                        .getText()
                        .trim();

        String joiningDate =
                txtJoiningDate
                        .getText()
                        .trim();


        if (!dob.isEmpty()) {

            try {

                java.sql.Date.valueOf(dob);

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "DOB must be in YYYY-MM-DD format.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );

                txtDob.requestFocus();

                return;
            }
        }


        if (!joiningDate.isEmpty()) {

            try {

                java.sql.Date.valueOf(
                        joiningDate
                );

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Joining Date must be in "
                                + "YYYY-MM-DD format.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );

                txtJoiningDate.requestFocus();

                return;
            }
        }


        // ==========================================
        // CREATE FACULTY OBJECT
        // ==========================================

        Faculty faculty =
                new Faculty();

        faculty.setEmployeeId(
                employeeId
        );

        faculty.setFirstName(
                firstName
        );

        faculty.setLastName(
                lastName
        );

        faculty.setGender(
                cmbGender
                        .getSelectedItem()
                        .toString()
        );

        faculty.setDob(
                dob
        );

        faculty.setEmail(
                email
        );

        faculty.setPhone(
                txtPhone
                        .getText()
                        .trim()
        );

        faculty.setQualification(
                txtQualification
                        .getText()
                        .trim()
        );

        faculty.setDesignation(
                txtDesignation
                        .getText()
                        .trim()
        );

        faculty.setSalary(
                salary
        );

        faculty.setJoiningDate(
                joiningDate
        );

        // Get the REAL department ID
        faculty.setDepartmentId(
                department.getDepartmentId()
        );


        // ==========================================
        // SAVE TO DATABASE
        // ==========================================

        boolean saved =
                facultyDAO.addFaculty(
                        faculty
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Faculty added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add faculty.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // DEPARTMENT ITEM
    // ==========================================

    private static class DepartmentItem {

        private int departmentId;

        private String departmentName;


        public DepartmentItem(
                int departmentId,
                String departmentName
        ) {

            this.departmentId =
                    departmentId;

            this.departmentName =
                    departmentName;
        }


        public int getDepartmentId() {

            return departmentId;
        }


        @Override
        public String toString() {

            return departmentName
                    + " (ID: "
                    + departmentId
                    + ")";
        }
    }
}