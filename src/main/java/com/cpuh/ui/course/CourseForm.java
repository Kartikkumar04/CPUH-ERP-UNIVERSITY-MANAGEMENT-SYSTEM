package com.cpuh.ui.course;

import com.cpuh.dao.CourseDAO;
import com.cpuh.db.DBConnection;
import com.cpuh.model.Course;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseForm extends JDialog {

    private JTextField txtCourseName;
    private JTextField txtCourseCode;
    private JTextField txtDuration;
    private JTextField txtSemesters;

    private JComboBox<DepartmentItem> cmbDepartment;

    private JButton btnSave;
    private JButton btnCancel;

    private CourseDAO courseDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public CourseForm(JFrame parent) {

        super(
                parent,
                "Add New Course",
                true
        );

        courseDAO = new CourseDAO();

        setSize(500, 450);
        setLocationRelativeTo(parent);
        setResizable(false);

        initUI();

        loadDepartments();

        setVisible(true);
    }


    // ==================================================
    // CREATE UI
    // ==================================================

    private void initUI() {

        JPanel mainPanel = new JPanel();

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        mainPanel.setLayout(
                new GridLayout(
                        6,
                        2,
                        10,
                        15
                )
        );


        // ==================================================
        // COURSE NAME
        // ==================================================

        mainPanel.add(
                new JLabel("Course Name:")
        );

        txtCourseName =
                new JTextField();

        mainPanel.add(
                txtCourseName
        );


        // ==================================================
        // COURSE CODE
        // ==================================================

        mainPanel.add(
                new JLabel("Course Code:")
        );

        txtCourseCode =
                new JTextField();

        mainPanel.add(
                txtCourseCode
        );


        // ==================================================
        // DURATION
        // ==================================================

        mainPanel.add(
                new JLabel("Duration (Years):")
        );

        txtDuration =
                new JTextField();

        mainPanel.add(
                txtDuration
        );


        // ==================================================
        // TOTAL SEMESTERS
        // ==================================================

        mainPanel.add(
                new JLabel("Total Semesters:")
        );

        txtSemesters =
                new JTextField();

        mainPanel.add(
                txtSemesters
        );


        // ==================================================
        // DEPARTMENT DROPDOWN
        // ==================================================

        mainPanel.add(
                new JLabel("Department:")
        );

        cmbDepartment =
                new JComboBox<>();

        mainPanel.add(
                cmbDepartment
        );


        // ==================================================
        // BUTTONS
        // ==================================================

        btnSave =
                new JButton("Save Course");

        btnCancel =
                new JButton("Cancel");


        mainPanel.add(
                btnSave
        );

        mainPanel.add(
                btnCancel
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // ==================================================
        // BUTTON EVENTS
        // ==================================================

        btnSave.addActionListener(e -> {

            saveCourse();

        });


        btnCancel.addActionListener(e -> {

            dispose();

        });
    }


    // ==================================================
    // LOAD DEPARTMENTS FROM MYSQL
    // ==================================================

    private void loadDepartments() {

        String sql = """
                SELECT
                    department_id,
                    department_name
                FROM departments
                ORDER BY department_name
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            List<DepartmentItem> departments =
                    new ArrayList<>();


            while (rs.next()) {

                int id =
                        rs.getInt(
                                "department_id"
                        );

                String name =
                        rs.getString(
                                "department_name"
                        );


                departments.add(
                        new DepartmentItem(
                                id,
                                name
                        )
                );
            }


            cmbDepartment.removeAllItems();


            for (
                    DepartmentItem department
                    : departments
            ) {

                cmbDepartment.addItem(
                        department
                );
            }


            if (cmbDepartment.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No departments found.\n\n" +
                                "Please create a department first.",
                        "No Departments",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load departments.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==================================================
    // SAVE COURSE
    // ==================================================

    private void saveCourse() {

        String courseName =
                txtCourseName
                        .getText()
                        .trim();


        String courseCode =
                txtCourseCode
                        .getText()
                        .trim();


        String durationText =
                txtDuration
                        .getText()
                        .trim();


        String semestersText =
                txtSemesters
                        .getText()
                        .trim();


        // ==================================================
        // VALIDATE COURSE NAME
        // ==================================================

        if (courseName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter course name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtCourseName.requestFocus();

            return;
        }


        // ==================================================
        // VALIDATE DEPARTMENT
        // ==================================================

        DepartmentItem selectedDepartment =
                (DepartmentItem)
                        cmbDepartment.getSelectedItem();


        if (selectedDepartment == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // NUMBER VARIABLES
        // ==================================================

        int durationYears = 0;

        int totalSemesters = 0;


        // ==================================================
        // CONVERT NUMBERS
        // ==================================================

        try {

            if (!durationText.isEmpty()) {

                durationYears =
                        Integer.parseInt(
                                durationText
                        );
            }


            if (!semestersText.isEmpty()) {

                totalSemesters =
                        Integer.parseInt(
                                semestersText
                        );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration and Total Semesters must be numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // ==================================================
        // VALIDATE NUMBERS
        // ==================================================

        if (durationYears < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (totalSemesters < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Total semesters cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // CREATE COURSE OBJECT
        // ==================================================

        Course course =
                new Course();


        course.setCourseName(
                courseName
        );


        course.setCourseCode(
                courseCode.isEmpty()
                        ? null
                        : courseCode
        );


        course.setDurationYears(
                durationYears
        );


        course.setTotalSemesters(
                totalSemesters
        );


        // IMPORTANT:
        // Get actual ID from selected department

        course.setDepartmentId(
                selectedDepartment.getId()
        );


        // ==================================================
        // SAVE TO DATABASE
        // ==================================================

        boolean saved =
                courseDAO.addCourse(
                        course
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Course added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add course.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==================================================
    // DEPARTMENT ITEM
    // ==================================================

    private static class DepartmentItem {

        private int id;
        private String name;


        public DepartmentItem(
                int id,
                String name
        ) {

            this.id = id;
            this.name = name;
        }


        public int getId() {

            return id;
        }


        @Override
        public String toString() {

            return name;
        }
    }
}