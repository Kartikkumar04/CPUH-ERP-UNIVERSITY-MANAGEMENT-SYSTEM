package com.cpuh.ui.course;

import com.cpuh.dao.CourseDAO;
import com.cpuh.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursePanel extends JPanel {

    private JTable courseTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private CourseDAO courseDAO;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public CoursePanel() {

        courseDAO = new CourseDAO();

        setLayout(
                new BorderLayout(10, 10)
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        initUI();

        loadCourses();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void initUI() {

        // ==========================================
        // TOP PANEL
        // ==========================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );


        JLabel title =
                new JLabel("Course Management");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        topPanel.add(
                title,
                BorderLayout.WEST
        );


        // ==========================================
        // SEARCH PANEL
        // ==========================================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        txtSearch =
                new JTextField(15);


        btnSearch =
                new JButton("Search");


        btnRefresh =
                new JButton("Refresh");


        btnAdd =
                new JButton("+ Add Course");


        searchPanel.add(
                new JLabel("Search:")
        );

        searchPanel.add(
                txtSearch
        );

        searchPanel.add(
                btnSearch
        );

        searchPanel.add(
                btnRefresh
        );

        searchPanel.add(
                btnAdd
        );


        topPanel.add(
                searchPanel,
                BorderLayout.EAST
        );


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // ==========================================
        // COURSE TABLE
        // ==========================================

        String[] columns = {

                "ID",
                "Course Name",
                "Course Code",
                "Duration (Years)",
                "Total Semesters",
                "Department"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        courseTable =
                new JTable(tableModel);


        courseTable.setRowHeight(28);

        courseTable.setAutoCreateRowSorter(true);


        courseTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        courseTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ==========================================
        // BOTTOM PANEL
        // ==========================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        btnDelete =
                new JButton(
                        "Delete Selected Course"
                );


        bottomPanel.add(
                btnDelete
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // BUTTON EVENTS
        // ==========================================

        // ADD COURSE

        btnAdd.addActionListener(e -> {

            Window window =
                    SwingUtilities
                            .getWindowAncestor(this);


            if (window instanceof JFrame) {

                new CourseForm(
                        (JFrame) window
                );

                loadCourses();
            }

        });


        // REFRESH

        btnRefresh.addActionListener(e -> {

            loadCourses();

        });


        // SEARCH

        btnSearch.addActionListener(e -> {

            searchCourses();

        });


        // ENTER KEY SEARCH

        txtSearch.addActionListener(e -> {

            searchCourses();

        });


        // DELETE

        btnDelete.addActionListener(e -> {

            deleteSelectedCourse();

        });
    }


    // ==========================================
    // LOAD COURSES
    // ==========================================

    private void loadCourses() {

        tableModel.setRowCount(0);


        List<Course> courses =
                courseDAO.getAllCourses();


        for (Course course : courses) {

            tableModel.addRow(
                    new Object[]{

                            course.getCourseId(),

                            course.getCourseName(),

                            course.getCourseCode(),

                            course.getDurationYears(),

                            course.getTotalSemesters(),

                            course.getDepartmentName()
                    }
            );
        }
    }


    // ==========================================
    // SEARCH COURSES
    // ==========================================

    private void searchCourses() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadCourses();

            return;
        }


        tableModel.setRowCount(0);


        List<Course> courses =
                courseDAO.searchCourses(
                        search
                );


        for (Course course : courses) {

            tableModel.addRow(
                    new Object[]{

                            course.getCourseId(),

                            course.getCourseName(),

                            course.getCourseCode(),

                            course.getDurationYears(),

                            course.getTotalSemesters(),

                            course.getDepartmentName()
                    }
            );
        }
    }


    // ==========================================
    // DELETE COURSE
    // ==========================================

    private void deleteSelectedCourse() {

        int selectedRow =
                courseTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a course first.",
                    "No Course Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row

        int modelRow =
                courseTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int courseId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );


        String courseName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,

                        "Delete course \""
                                + courseName
                                + "\"?",

                        "Confirm Delete",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    courseDAO.deleteCourse(
                            courseId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course deleted successfully."
                );


                loadCourses();

            } else {

                JOptionPane.showMessageDialog(
                        this,

                        "Failed to delete course.",

                        "Error",

                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}