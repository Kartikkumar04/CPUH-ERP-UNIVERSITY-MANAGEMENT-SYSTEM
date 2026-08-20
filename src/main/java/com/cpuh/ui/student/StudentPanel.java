package com.cpuh.ui.student;

import com.cpuh.dao.StudentDAO;
import com.cpuh.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnSearch;

    private StudentDAO studentDAO;

    public StudentPanel() {

        studentDAO = new StudentDAO();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        initUI();

        loadStudents();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void initUI() {

        // ==========================================
        // TOP PANEL
        // ==========================================

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel title =
                new JLabel("Student Management");

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
                new JButton("+ Add Student");

        searchPanel.add(
                new JLabel("Search:")
        );

        searchPanel.add(txtSearch);

        searchPanel.add(btnSearch);

        searchPanel.add(btnRefresh);

        searchPanel.add(btnAdd);

        topPanel.add(
                searchPanel,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );


        // ==========================================
        // TABLE
        // ==========================================

        String[] columns = {

                "ID",
                "Roll No",
                "First Name",
                "Last Name",
                "Gender",
                "DOB",
                "Email",
                "Phone",
                "Admission Year",
                "Semester"
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


        studentTable =
                new JTable(tableModel);

        studentTable.setRowHeight(28);

        studentTable.setAutoCreateRowSorter(true);

        studentTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        JScrollPane scrollPane =
                new JScrollPane(studentTable);

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


        btnEdit =
                new JButton("Edit Selected Student");


        btnDelete =
                new JButton("Delete Selected Student");


        bottomPanel.add(btnEdit);

        bottomPanel.add(btnDelete);


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // ADD BUTTON
        // ==========================================

        btnAdd.addActionListener(e -> {

            Window window =
                    SwingUtilities.getWindowAncestor(
                            this
                    );

            if (window instanceof JFrame) {

                new StudentForm(
                        (JFrame) window
                );

                loadStudents();
            }
        });


        // ==========================================
        // REFRESH BUTTON
        // ==========================================

        btnRefresh.addActionListener(e -> {

            loadStudents();

        });


        // ==========================================
        // SEARCH BUTTON
        // ==========================================

        btnSearch.addActionListener(e -> {

            searchStudents();

        });


        txtSearch.addActionListener(e -> {

            searchStudents();

        });


        // ==========================================
        // EDIT BUTTON
        // ==========================================

        btnEdit.addActionListener(e -> {

            editSelectedStudent();

        });


        // ==========================================
        // DELETE BUTTON
        // ==========================================

        btnDelete.addActionListener(e -> {

            deleteSelectedStudent();

        });
    }


    // ==========================================
    // LOAD STUDENTS
    // ==========================================

    private void loadStudents() {

        tableModel.setRowCount(0);

        List<Student> students =
                studentDAO.getAllStudents();

        for (Student student : students) {

            addStudentToTable(student);
        }
    }


    // ==========================================
    // ADD STUDENT TO TABLE
    // ==========================================

    private void addStudentToTable(Student student) {

        tableModel.addRow(
                new Object[]{

                        student.getStudentId(),

                        student.getRollNo(),

                        student.getFirstName(),

                        student.getLastName(),

                        student.getGender(),

                        student.getDob(),

                        student.getEmail(),

                        student.getPhone(),

                        student.getAdmissionYear(),

                        student.getSemester()
                }
        );
    }


    // ==========================================
    // SEARCH STUDENTS
    // ==========================================

    private void searchStudents() {

        String search =
                txtSearch.getText()
                        .trim()
                        .toLowerCase();


        tableModel.setRowCount(0);


        List<Student> students =
                studentDAO.getAllStudents();


        for (Student student : students) {

            String name =
                    (
                            student.getFirstName()
                                    + " "
                                    + student.getLastName()
                    ).toLowerCase();


            String rollNo =
                    student.getRollNo() == null
                            ? ""
                            : student.getRollNo()
                            .toLowerCase();


            String email =
                    student.getEmail() == null
                            ? ""
                            : student.getEmail()
                            .toLowerCase();


            if (
                    search.isEmpty()
                            ||
                            name.contains(search)
                            ||
                            rollNo.contains(search)
                            ||
                            email.contains(search)
            ) {

                addStudentToTable(student);
            }
        }
    }


    // ==========================================
    // EDIT SELECTED STUDENT
    // ==========================================

    private void editSelectedStudent() {

        int selectedRow =
                studentTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student first.",
                    "No Student Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                studentTable.convertRowIndexToModel(
                        selectedRow
                );


        int studentId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );


        // Find the complete student object
        Student selectedStudent = null;

        List<Student> students =
                studentDAO.getAllStudents();


        for (Student student : students) {

            if (
                    student.getStudentId()
                            == studentId
            ) {

                selectedStudent = student;
                break;
            }
        }


        if (selectedStudent == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student data could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        Window window =
                SwingUtilities.getWindowAncestor(
                        this
                );


        if (window instanceof JFrame) {

            new StudentEditForm(
                    (JFrame) window,
                    selectedStudent,
                    studentDAO,
                    this
            );
        }
    }


    // ==========================================
    // DELETE STUDENT
    // ==========================================

    private void deleteSelectedStudent() {

        int selectedRow =
                studentTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student first.",
                    "No Student Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                studentTable.convertRowIndexToModel(
                        selectedRow
                );


        int studentId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );


        String rollNo =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete student " +
                                rollNo +
                                "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    studentDAO.deleteStudent(
                            studentId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student deleted successfully."
                );

                loadStudents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete student.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // ==========================================
    // PUBLIC REFRESH METHOD
    // ==========================================

    public void refreshStudents() {

        loadStudents();

    }
}