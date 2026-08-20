package com.cpuh.ui.facultydashboard;

import com.cpuh.dao.AttendanceDAO;
import com.cpuh.dao.StudentDAO;
import com.cpuh.dao.SubjectDAO;
import com.cpuh.db.DBConnection;
import com.cpuh.model.Attendance;
import com.cpuh.model.Student;
import com.cpuh.model.Subject;
import com.cpuh.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class FacultyAttendancePanel extends JPanel {

    private User user;

    private int facultyId;

    private JComboBox<Subject> cmbSubject;

    private JTextField txtDate;

    private JTable studentTable;

    private DefaultTableModel tableModel;

    private JButton btnLoadStudents;
    private JButton btnSave;
    private JButton btnChangeAttendance;
    private JButton btnRefresh;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public FacultyAttendancePanel(User user) {

        this.user = user;

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        findFacultyId();

        initUI();

        loadSubjects();
    }


    // ==========================================
    // FIND FACULTY ID
    // ==========================================

    private void findFacultyId() {

        String sql =
                "SELECT faculty_id " +
                        "FROM faculty " +
                        "WHERE user_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    user.getUserId()
            );

            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                if (rs.next()) {

                    facultyId =
                            rs.getInt(
                                    "faculty_id"
                            );

                } else {

                    facultyId = -1;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            facultyId = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to find faculty profile.\n\n"
                            + e.getMessage(),
                    "Faculty Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // INITIALIZE UI
    // ==========================================

    private void initUI() {

        // ======================================
        // TITLE
        // ======================================

        JLabel title =
                new JLabel(
                        "Mark Attendance"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );


        // ======================================
        // FORM PANEL
        // ======================================

        JPanel formPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                5
                        )
                );


        formPanel.add(
                new JLabel(
                        "Subject:"
                )
        );


        cmbSubject =
                new JComboBox<>();


        cmbSubject.setPreferredSize(
                new Dimension(
                        280,
                        30
                )
        );


        formPanel.add(
                cmbSubject
        );


        formPanel.add(
                new JLabel(
                        "Date:"
                )
        );


        txtDate =
                new JTextField(
                        LocalDate.now().toString()
                );


        txtDate.setPreferredSize(
                new Dimension(
                        120,
                        30
                )
        );


        formPanel.add(
                txtDate
        );


        btnLoadStudents =
                new JButton(
                        "Load Students"
                );


        formPanel.add(
                btnLoadStudents
        );


        btnChangeAttendance =
                new JButton(
                        "Change Attendance"
                );


        formPanel.add(
                btnChangeAttendance
        );


        btnRefresh =
                new JButton(
                        "Refresh"
                );


        formPanel.add(
                btnRefresh
        );


        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        topPanel.add(
                title,
                BorderLayout.NORTH
        );


        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // ======================================
        // TABLE
        // ======================================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Student ID",
                                "Roll No",
                                "Student Name",
                                "Present",
                                "Absent"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return column >= 3;
                    }


                    @Override
                    public Class<?> getColumnClass(
                            int column
                    ) {

                        if (column >= 3) {

                            return Boolean.class;
                        }

                        return Object.class;
                    }


                    @Override
                    public void setValueAt(
                            Object value,
                            int row,
                            int column
                    ) {

                        /*
                         * Only one checkbox can be
                         * selected for each student.
                         */

                        if (
                                column >= 3
                                        &&
                                        Boolean.TRUE.equals(
                                                value
                                        )
                        ) {

                            super.setValueAt(
                                    false,
                                    row,
                                    3
                            );

                            super.setValueAt(
                                    false,
                                    row,
                                    4
                            );

                            super.setValueAt(
                                    true,
                                    row,
                                    column
                            );

                            return;
                        }

                        super.setValueAt(
                                value,
                                row,
                                column
                        );
                    }
                };


        studentTable =
                new JTable(
                        tableModel
                );


        studentTable.setRowHeight(
                32
        );


        studentTable.setAutoCreateRowSorter(
                true
        );


        studentTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        // ======================================
        // CENTER CHECKBOXES
        // ======================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();


        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        studentTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );


        studentTable
                .getColumnModel()
                .getColumn(1)
                .setCellRenderer(
                        centerRenderer
                );


        studentTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new CheckBoxRenderer()
                );


        studentTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new CheckBoxRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        studentTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ======================================
        // BOTTOM PANEL
        // ======================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );


        btnSave =
                new JButton(
                        "Save Attendance"
                );


        btnSave.setPreferredSize(
                new Dimension(
                        180,
                        35
                )
        );


        bottomPanel.add(
                btnSave
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ======================================
        // BUTTON EVENTS
        // ======================================

        btnLoadStudents.addActionListener(
                e -> loadStudents()
        );


        btnChangeAttendance.addActionListener(
                e -> changeAttendance()
        );


        btnRefresh.addActionListener(
                e -> refreshPanel()
        );


        btnSave.addActionListener(
                e -> saveAttendance()
        );
    }


    // ==========================================
    // CHECKBOX RENDERER
    // ==========================================

    private static class CheckBoxRenderer
            extends JCheckBox
            implements
            javax.swing.table.TableCellRenderer {

        public CheckBoxRenderer() {

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );
        }


        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            setSelected(
                    Boolean.TRUE.equals(
                            value
                    )
            );

            setBackground(
                    table.getBackground()
            );

            return this;
        }
    }


    // ==========================================
    // LOAD SUBJECTS
    // ==========================================

    private void loadSubjects() {

        cmbSubject.removeAllItems();


        if (facultyId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Faculty profile is not linked to this login.",
                    "Faculty Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        SubjectDAO subjectDAO =
                new SubjectDAO();


        List<Subject> subjects =
                subjectDAO.getSubjectsByFaculty(
                        facultyId
                );


        for (
                Subject subject :
                subjects
        ) {

            cmbSubject.addItem(
                    subject
            );
        }


        if (subjects.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No subjects are assigned to you.",
                    "No Subjects",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    // ==========================================
    // LOAD STUDENTS
    // ==========================================

    private void loadStudents() {

        tableModel.setRowCount(
                0
        );


        Subject selectedSubject =
                (Subject)
                        cmbSubject.getSelectedItem();


        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject.",
                    "Subject Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String date =
                txtDate
                        .getText()
                        .trim();


        if (!isValidDate(date)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter date in YYYY-MM-DD format.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        StudentDAO studentDAO =
                new StudentDAO();


        List<Student> students =
                studentDAO.getAllStudents();


        for (
                Student student :
                students
        ) {

            String fullName =
                    student.getFirstName();


            if (
                    student.getLastName() != null
                            &&
                            !student.getLastName().isBlank()
            ) {

                fullName =
                        fullName
                                + " "
                                + student.getLastName();
            }


            String existingStatus =
                    getExistingAttendanceStatus(
                            student.getStudentId(),
                            selectedSubject.getSubjectId(),
                            date
                    );


            boolean present =
                    "Present".equalsIgnoreCase(
                            existingStatus
                    );


            boolean absent =
                    "Absent".equalsIgnoreCase(
                            existingStatus
                    );


            tableModel.addRow(
                    new Object[]{
                            student.getStudentId(),
                            student.getRollNo(),
                            fullName,
                            present,
                            absent
                    }
            );
        }


        if (students.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No students found.",
                    "No Students",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    // ==========================================
    // GET EXISTING ATTENDANCE
    // ==========================================

    private String getExistingAttendanceStatus(
            int studentId,
            int subjectId,
            String date
    ) {

        String sql = """
                SELECT status
                FROM attendance
                WHERE student_id = ?
                AND subject_id = ?
                AND attendance_date = ?
                LIMIT 1
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    studentId
            );


            pst.setInt(
                    2,
                    subjectId
            );


            pst.setDate(
                    3,
                    java.sql.Date.valueOf(
                            date
                    )
            );


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getString(
                            "status"
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }


    // ==========================================
    // CHANGE ATTENDANCE
    // ==========================================

    private void changeAttendance() {

        Subject selectedSubject =
                (Subject)
                        cmbSubject.getSelectedItem();


        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject first.",
                    "Subject Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String date =
                txtDate
                        .getText()
                        .trim();


        if (!isValidDate(date)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid date.\n"
                            + "Format: YYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        loadStudents();


        JOptionPane.showMessageDialog(
                this,
                "Existing attendance loaded.\n\n"
                        + "You can now change Present "
                        + "or Absent.\n\n"
                        + "Click Save Attendance "
                        + "after making changes.",
                "Change Attendance",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ==========================================
    // SAVE ATTENDANCE
    // ==========================================

    private void saveAttendance() {

        Subject selectedSubject =
                (Subject)
                        cmbSubject.getSelectedItem();


        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject.",
                    "Subject Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String date =
                txtDate
                        .getText()
                        .trim();


        if (!isValidDate(date)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid date.\n"
                            + "Format: YYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (
                tableModel.getRowCount() == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please load students first.",
                    "No Students",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ======================================
        // STOP CELL EDITING
        // ======================================

        if (
                studentTable.isEditing()
        ) {

            studentTable
                    .getCellEditor()
                    .stopCellEditing();
        }


        int subjectId =
                selectedSubject.getSubjectId();


        AttendanceDAO attendanceDAO =
                new AttendanceDAO();


        int saved = 0;

        int updated = 0;

        int skipped = 0;


        // ======================================
        // PROCESS STUDENTS
        // ======================================

        for (
                int row = 0;
                row < tableModel.getRowCount();
                row++
        ) {

            int studentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            row,
                                            0
                                    )
                                    .toString()
                    );


            boolean present =
                    Boolean.TRUE.equals(
                            tableModel.getValueAt(
                                    row,
                                    3
                            )
                    );


            boolean absent =
                    Boolean.TRUE.equals(
                            tableModel.getValueAt(
                                    row,
                                    4
                            )
                    );


            /*
             * Neither checkbox selected:
             * do not save anything for this student.
             */

            if (
                    !present
                            &&
                            !absent
            ) {

                skipped++;

                continue;
            }


            String status;


            if (present) {

                status = "Present";

            } else {

                status = "Absent";
            }


            boolean exists =
                    attendanceExists(
                            studentId,
                            subjectId,
                            date
                    );


            // ==================================
            // UPDATE EXISTING
            // ==================================

            if (exists) {

                boolean result =
                        updateAttendance(
                                studentId,
                                subjectId,
                                date,
                                status
                        );


                if (result) {

                    updated++;

                } else {

                    skipped++;
                }

            }


            // ==================================
            // INSERT NEW
            // ==================================

            else {

                Attendance attendance =
                        new Attendance(
                                studentId,
                                subjectId,
                                facultyId,
                                date,
                                status
                        );


                boolean result =
                        attendanceDAO.addAttendance(
                                attendance
                        );


                if (result) {

                    saved++;

                } else {

                    skipped++;
                }
            }
        }


        // ======================================
        // RESULT
        // ======================================

        JOptionPane.showMessageDialog(
                this,
                "Attendance processing completed.\n\n"
                        + "New records: "
                        + saved
                        + "\n"
                        + "Changed records: "
                        + updated
                        + "\n"
                        + "Not marked: "
                        + skipped,
                "Attendance Result",
                JOptionPane.INFORMATION_MESSAGE
        );


        /*
         * Reload from database.
         */

        loadStudents();
    }


    // ==========================================
    // CHECK EXISTING ATTENDANCE
    // ==========================================

    private boolean attendanceExists(
            int studentId,
            int subjectId,
            String date
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM attendance
                WHERE student_id = ?
                AND subject_id = ?
                AND attendance_date = ?
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    studentId
            );


            pst.setInt(
                    2,
                    subjectId
            );


            pst.setDate(
                    3,
                    java.sql.Date.valueOf(
                            date
                    )
            );


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to check attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return false;
    }


    // ==========================================
    // UPDATE ATTENDANCE
    // ==========================================

    private boolean updateAttendance(
            int studentId,
            int subjectId,
            String date,
            String status
    ) {

        String sql = """
                UPDATE attendance
                SET
                    status = ?,
                    faculty_id = ?
                WHERE student_id = ?
                AND subject_id = ?
                AND attendance_date = ?
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    status
            );


            pst.setInt(
                    2,
                    facultyId
            );


            pst.setInt(
                    3,
                    studentId
            );


            pst.setInt(
                    4,
                    subjectId
            );


            pst.setDate(
                    5,
                    java.sql.Date.valueOf(
                            date
                    )
            );


            return pst.executeUpdate() > 0;


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // VALIDATE DATE
    // ==========================================

    private boolean isValidDate(
            String date
    ) {

        try {

            LocalDate.parse(
                    date
            );

            return true;

        } catch (Exception e) {

            return false;
        }
    }


    // ==========================================
    // REFRESH
    // ==========================================

    private void refreshPanel() {

        Subject selectedSubject =
                (Subject)
                        cmbSubject.getSelectedItem();


        String currentDate =
                txtDate
                        .getText()
                        .trim();


        tableModel.setRowCount(
                0
        );


        loadSubjects();


        // ======================================
        // RESTORE SUBJECT
        // ======================================

        if (
                selectedSubject != null
        ) {

            for (
                    int i = 0;
                    i < cmbSubject.getItemCount();
                    i++
            ) {

                Subject subject =
                        cmbSubject.getItemAt(
                                i
                        );


                if (
                        subject.getSubjectId()
                                ==
                                selectedSubject.getSubjectId()
                ) {

                    cmbSubject.setSelectedIndex(
                            i
                    );

                    break;
                }
            }
        }


        // ======================================
        // RESTORE DATE
        // ======================================

        if (
                !currentDate.isBlank()
                        &&
                        isValidDate(
                                currentDate
                        )
        ) {

            txtDate.setText(
                    currentDate
            );

        } else {

            txtDate.setText(
                    LocalDate.now().toString()
            );
        }


        // ======================================
        // RELOAD STUDENTS
        // ======================================

        if (
                cmbSubject.getSelectedItem()
                        != null
        ) {

            loadStudents();
        }
    }
}