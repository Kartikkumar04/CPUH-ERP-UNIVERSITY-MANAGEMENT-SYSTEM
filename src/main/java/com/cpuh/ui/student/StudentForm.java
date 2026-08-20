package com.cpuh.ui.student;

import com.cpuh.dao.CourseDAO;
import com.cpuh.dao.StudentDAO;
import com.cpuh.model.Course;
import com.cpuh.model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentForm extends JDialog {

    // =========================================================
    // TEXT FIELDS
    // =========================================================

    private JTextField txtRollNo;

    private JTextField txtFirstName;

    private JTextField txtLastName;

    private JTextField txtDOB;

    private JTextField txtEmail;

    private JTextField txtPhone;

    private JTextArea txtAddress;

    private JTextField txtAdmissionYear;


    // =========================================================
    // COMBO BOXES
    // =========================================================

    private JComboBox<String> cmbGender;

    private JComboBox<Course> cmbCourse;

    private JComboBox<Integer> cmbSemester;


    // =========================================================
    // BUTTONS
    // =========================================================

    private JButton btnSave;

    private JButton btnClear;

    private JButton btnCancel;


    // =========================================================
    // DAOs
    // =========================================================

    private CourseDAO courseDAO;

    private StudentDAO studentDAO;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public StudentForm(JFrame parent) {

        super(
                parent,
                "Add Student",
                true
        );


        // -----------------------------------------------------
        // INITIALIZE DAOs
        // -----------------------------------------------------

        courseDAO =
                new CourseDAO();

        studentDAO =
                new StudentDAO();


        // -----------------------------------------------------
        // WINDOW SETTINGS
        // -----------------------------------------------------

        setSize(
                650,
                750
        );


        setLocationRelativeTo(
                parent
        );


        setResizable(
                false
        );


        setLayout(
                new BorderLayout()
        );


        // -----------------------------------------------------
        // INITIALIZE UI
        // -----------------------------------------------------

        initUI();


        // -----------------------------------------------------
        // LOAD COURSES
        // -----------------------------------------------------

        loadCourses();


        // -----------------------------------------------------
        // SHOW WINDOW
        // -----------------------------------------------------

        setVisible(
                true
        );
    }


    // =========================================================
    // INITIALIZE UI
    // =========================================================

    private void initUI() {

        // =====================================================
        // FORM PANEL
        // =====================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        7,
                        10,
                        7,
                        10
                );


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        gbc.weightx =
                1.0;


        int row = 0;


        // =====================================================
        // ROLL NUMBER
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Register ID / Roll Number *",
                txtRollNo =
                        new JTextField()
        );


        // =====================================================
        // FIRST NAME
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "First Name *",
                txtFirstName =
                        new JTextField()
        );


        // =====================================================
        // LAST NAME
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Last Name",
                txtLastName =
                        new JTextField()
        );


        // =====================================================
        // GENDER
        // =====================================================

        addComboField(
                formPanel,
                gbc,
                row++,
                "Gender",
                cmbGender =
                        new JComboBox<>(
                                new String[]{
                                        "Male",
                                        "Female",
                                        "Other"
                                }
                        )
        );


        // =====================================================
        // DOB
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "DOB (yyyy-mm-dd)",
                txtDOB =
                        new JTextField()
        );


        // =====================================================
        // EMAIL
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Email",
                txtEmail =
                        new JTextField()
        );


        // =====================================================
        // PHONE
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Phone",
                txtPhone =
                        new JTextField()
        );


        // =====================================================
        // ADDRESS
        // =====================================================

        gbc.gridx = 0;

        gbc.gridy = row;

        gbc.weightx = 0;

        formPanel.add(
                new JLabel(
                        "Address"
                ),
                gbc
        );


        txtAddress =
                new JTextArea(
                        3,
                        20
                );


        txtAddress.setLineWrap(
                true
        );


        txtAddress.setWrapStyleWord(
                true
        );


        JScrollPane addressScroll =
                new JScrollPane(
                        txtAddress
                );


        gbc.gridx = 1;

        gbc.weightx = 1.0;


        formPanel.add(
                addressScroll,
                gbc
        );


        row++;


        // =====================================================
        // ADMISSION YEAR
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Admission Year",
                txtAdmissionYear =
                        new JTextField()
        );


        // =====================================================
        // PROGRAM / COURSE
        // =====================================================

        addComboField(
                formPanel,
                gbc,
                row++,
                "Program / Course *",
                cmbCourse =
                        new JComboBox<>()
        );


        // =====================================================
        // SEMESTER
        // =====================================================

        addComboField(
                formPanel,
                gbc,
                row++,
                "Semester *",
                cmbSemester =
                        new JComboBox<>()
        );


        // -----------------------------------------------------
        // INITIAL SEMESTER VALUES
        // -----------------------------------------------------

        loadDefaultSemesters();


        // =====================================================
        // COURSE CHANGE LISTENER
        // =====================================================

        cmbCourse.addActionListener(
                e ->
                        updateSemesterList()
        );


        // =====================================================
        // ADD FORM PANEL
        // =====================================================

        add(
                new JScrollPane(
                        formPanel
                ),
                BorderLayout.CENTER
        );


        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );


        btnSave =
                new JButton(
                        "Create Student"
                );


        btnClear =
                new JButton(
                        "Clear"
                );


        btnCancel =
                new JButton(
                        "Cancel"
                );


        buttonPanel.add(
                btnSave
        );


        buttonPanel.add(
                btnClear
        );


        buttonPanel.add(
                btnCancel
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // BUTTON EVENTS
        // =====================================================

        btnSave.addActionListener(
                e ->
                        saveStudent()
        );


        btnClear.addActionListener(
                e ->
                        clearForm()
        );


        btnCancel.addActionListener(
                e ->
                        dispose()
        );
    }


    // =========================================================
    // ADD TEXT FIELD
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextField field
    ) {

        gbc.gridx = 0;

        gbc.gridy = row;

        gbc.weightx = 0;


        panel.add(
                new JLabel(
                        label
                ),
                gbc
        );


        gbc.gridx = 1;

        gbc.weightx = 1.0;


        panel.add(
                field,
                gbc
        );
    }


    // =========================================================
    // ADD COMBO FIELD
    // =========================================================

    private void addComboField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent component
    ) {

        gbc.gridx = 0;

        gbc.gridy = row;

        gbc.weightx = 0;


        panel.add(
                new JLabel(
                        label
                ),
                gbc
        );


        gbc.gridx = 1;

        gbc.weightx = 1.0;


        panel.add(
                component,
                gbc
        );
    }


    // =========================================================
    // LOAD COURSES
    // =========================================================

    private void loadCourses() {

        cmbCourse.removeAllItems();


        List<Course> courses =
                courseDAO.getAllCourses();


        if (
                courses == null
                        ||
                        courses.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,

                    "No programs/courses found.\n\n"
                            + "Please add a course first from:\n"
                            + "Admin → Courses",

                    "No Programs Available",

                    JOptionPane.WARNING_MESSAGE
            );


            return;
        }


        // -----------------------------------------------------
        // ADD COURSES
        // -----------------------------------------------------

        for (
                Course course :
                courses
        ) {

            cmbCourse.addItem(
                    course
            );
        }


        // -----------------------------------------------------
        // SELECT FIRST COURSE
        // -----------------------------------------------------

        if (
                cmbCourse.getItemCount() > 0
        ) {

            cmbCourse.setSelectedIndex(
                    0
            );
        }


        updateSemesterList();
    }


    // =========================================================
    // LOAD DEFAULT SEMESTERS
    // =========================================================

    private void loadDefaultSemesters() {

        cmbSemester.removeAllItems();


        for (
                int i = 1;
                i <= 8;
                i++
        ) {

            cmbSemester.addItem(
                    i
            );
        }


        if (
                cmbSemester.getItemCount() > 0
        ) {

            cmbSemester.setSelectedIndex(
                    0
            );
        }
    }


    // =========================================================
    // UPDATE SEMESTERS ACCORDING TO COURSE
    // =========================================================

    private void updateSemesterList() {

        Course selectedCourse =
                (Course)
                        cmbCourse.getSelectedItem();


        if (
                selectedCourse == null
        ) {

            loadDefaultSemesters();

            return;
        }


        int totalSemesters =
                selectedCourse.getTotalSemesters();


        // -----------------------------------------------------
        // IF COURSE HAS NO SEMESTER VALUE
        // -----------------------------------------------------

        if (
                totalSemesters <= 0
        ) {

            totalSemesters = 8;
        }


        cmbSemester.removeAllItems();


        for (
                int i = 1;
                i <= totalSemesters;
                i++
        ) {

            cmbSemester.addItem(
                    i
            );
        }


        if (
                cmbSemester.getItemCount() > 0
        ) {

            cmbSemester.setSelectedIndex(
                    0
            );
        }
    }


    // =========================================================
    // SAVE STUDENT
    // =========================================================

    private void saveStudent() {

        // =====================================================
        // READ FORM VALUES
        // =====================================================

        String rollNo =
                txtRollNo
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


        String gender =
                (String)
                        cmbGender
                                .getSelectedItem();


        String dob =
                txtDOB
                        .getText()
                        .trim();


        String email =
                txtEmail
                        .getText()
                        .trim();


        String phone =
                txtPhone
                        .getText()
                        .trim();


        String address =
                txtAddress
                        .getText()
                        .trim();


        String admissionYearText =
                txtAdmissionYear
                        .getText()
                        .trim();


        Integer selectedSemester =
                (Integer)
                        cmbSemester
                                .getSelectedItem();


        Course selectedCourse =
                (Course)
                        cmbCourse
                                .getSelectedItem();


        // =====================================================
        // VALIDATE ROLL NUMBER
        // =====================================================

        if (
                rollNo.isEmpty()
        ) {

            showError(
                    "Please enter Register ID / Roll Number."
            );


            txtRollNo.requestFocus();


            return;
        }


        // =====================================================
        // VALIDATE FIRST NAME
        // =====================================================

        if (
                firstName.isEmpty()
        ) {

            showError(
                    "Please enter First Name."
            );


            txtFirstName.requestFocus();


            return;
        }


        // =====================================================
        // VALIDATE COURSE
        // =====================================================

        if (
                selectedCourse == null
        ) {

            showError(
                    "Please select a Program / Course."
            );


            cmbCourse.requestFocus();


            return;
        }


        // =====================================================
        // VALIDATE SEMESTER
        // =====================================================

        if (
                selectedSemester == null
        ) {

            showError(
                    "Please select a Semester."
            );


            cmbSemester.requestFocus();


            return;
        }


        // =====================================================
        // VALIDATE DOB
        // =====================================================

        if (
                !dob.isEmpty()
        ) {

            try {

                java.sql.Date.valueOf(
                        dob
                );

            } catch (
                    IllegalArgumentException e
            ) {

                showError(
                        "Invalid DOB.\n\n"
                                + "Use format:\n"
                                + "yyyy-mm-dd"
                );


                txtDOB.requestFocus();


                return;
            }
        }


        // =====================================================
        // VALIDATE ADMISSION YEAR
        // =====================================================

        int admissionYear = 0;


        if (
                !admissionYearText.isEmpty()
        ) {

            try {

                admissionYear =
                        Integer.parseInt(
                                admissionYearText
                        );


                if (
                        admissionYear < 1900
                                ||
                                admissionYear > 2100
                ) {

                    showError(
                            "Please enter a valid Admission Year."
                    );


                    txtAdmissionYear.requestFocus();


                    return;
                }

            } catch (
                    NumberFormatException e
            ) {

                showError(
                        "Admission Year must be a number."
                );


                txtAdmissionYear.requestFocus();


                return;
            }
        }


        // =====================================================
        // GET PROGRAM / COURSE IDs
        // =====================================================

        int courseId =
                selectedCourse.getCourseId();


        int departmentId =
                selectedCourse.getDepartmentId();


        // =====================================================
        // VALIDATE COURSE ID
        // =====================================================

        if (
                courseId <= 0
        ) {

            showError(
                    "Invalid Program / Course selected."
            );


            return;
        }


        // =====================================================
        // VALIDATE DEPARTMENT ID
        // =====================================================

        if (
                departmentId <= 0
        ) {

            showError(
                    "The selected Program does not have a valid Department."
            );


            return;
        }


        // =====================================================
        // CREATE STUDENT OBJECT
        // =====================================================

        Student student =
                new Student();


        // -----------------------------------------------------
        // BASIC DETAILS
        // -----------------------------------------------------

        student.setRollNo(
                rollNo
        );


        student.setFirstName(
                firstName
        );


        student.setLastName(
                lastName
        );


        student.setGender(
                gender
        );


        student.setDob(
                dob
        );


        student.setEmail(
                email
        );


        student.setPhone(
                phone
        );


        student.setAddress(
                address
        );


        student.setAdmissionYear(
                admissionYear
        );


        student.setSemester(
                selectedSemester
        );


        // =====================================================
        // PROGRAM / COURSE
        // =====================================================

        student.setCourseId(
                courseId
        );


        // =====================================================
        // DEPARTMENT
        // =====================================================

        student.setDepartmentId(
                departmentId
        );


        // =====================================================
        // SAVE STUDENT
        // =====================================================

        boolean success =
                studentDAO.addStudent(
                        student
                );


        // =====================================================
        // SUCCESS
        // =====================================================

        if (
                success
        ) {

            clearForm();
        }
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        txtRollNo.setText("");


        txtFirstName.setText("");


        txtLastName.setText("");


        txtDOB.setText("");


        txtEmail.setText("");


        txtPhone.setText("");


        txtAddress.setText("");


        txtAdmissionYear.setText("");


        // -----------------------------------------------------
        // RESET GENDER
        // -----------------------------------------------------

        if (
                cmbGender.getItemCount() > 0
        ) {

            cmbGender.setSelectedIndex(
                    0
            );
        }


        // -----------------------------------------------------
        // RESET COURSE
        // -----------------------------------------------------

        if (
                cmbCourse.getItemCount() > 0
        ) {

            cmbCourse.setSelectedIndex(
                    0
            );
        }


        // -----------------------------------------------------
        // RESET SEMESTER
        // -----------------------------------------------------

        updateSemesterList();


        // -----------------------------------------------------
        // FOCUS
        // -----------------------------------------------------

        txtRollNo.requestFocus();
    }


    // =========================================================
    // SHOW ERROR
    // =========================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,

                message,

                "Validation Error",

                JOptionPane.WARNING_MESSAGE
        );
    }
}