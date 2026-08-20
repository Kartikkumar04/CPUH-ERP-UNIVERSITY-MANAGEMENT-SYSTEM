package com.cpuh.ui.examination;

import com.cpuh.dao.ExaminationDAO;
import com.cpuh.db.DBConnection;
import com.cpuh.model.Examination;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ExaminationForm extends JDialog {

    private JTextField txtExamName;
    private JComboBox<String> cmbExamType;
    private JComboBox<SubjectItem> cmbSubject;
    private JComboBox<Integer> cmbSemester;
    private JTextField txtExamDate;
    private JTextField txtTotalMarks;
    private JTextField txtPassingMarks;

    private JButton btnSave;
    private JButton btnCancel;

    private ExaminationDAO examinationDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public ExaminationForm(JFrame parent) {

        super(
                parent,
                "Add Examination",
                true
        );

        examinationDAO =
                new ExaminationDAO();

        setSize(600, 600);

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        initUI();

        loadSubjects();

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
                        "Add Examination"
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
        // EXAM NAME
        // ==================================================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Exam Name:"),
                gbc
        );


        txtExamName =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                txtExamName,
                gbc
        );


        // ==================================================
        // EXAM TYPE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Exam Type:"),
                gbc
        );


        cmbExamType =
                new JComboBox<>(
                        new String[]{
                                "Mid Semester",
                                "End Semester",
                                "Practical",
                                "Internal",
                                "Other"
                        }
                );


        gbc.gridx = 1;

        mainPanel.add(
                cmbExamType,
                gbc
        );


        // ==================================================
        // SUBJECT
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Subject:"),
                gbc
        );


        cmbSubject =
                new JComboBox<>();


        gbc.gridx = 1;

        mainPanel.add(
                cmbSubject,
                gbc
        );


        // ==================================================
        // SEMESTER
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Semester:"),
                gbc
        );


        cmbSemester =
                new JComboBox<>();


        for (
                int i = 1;
                i <= 8;
                i++
        ) {

            cmbSemester.addItem(i);
        }


        gbc.gridx = 1;

        mainPanel.add(
                cmbSemester,
                gbc
        );


        // ==================================================
        // EXAM DATE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel(
                        "Exam Date (YYYY-MM-DD):"
                ),
                gbc
        );


        txtExamDate =
                new JTextField(
                        LocalDate.now().toString()
                );


        gbc.gridx = 1;

        mainPanel.add(
                txtExamDate,
                gbc
        );


        // ==================================================
        // TOTAL MARKS
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Total Marks:"),
                gbc
        );


        txtTotalMarks =
                new JTextField("100");


        gbc.gridx = 1;

        mainPanel.add(
                txtTotalMarks,
                gbc
        );


        // ==================================================
        // PASSING MARKS
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Passing Marks:"),
                gbc
        );


        txtPassingMarks =
                new JTextField("40");


        gbc.gridx = 1;

        mainPanel.add(
                txtPassingMarks,
                gbc
        );


        // ==================================================
        // BUTTON PANEL
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
                        "Save Examination"
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


        add(
                mainPanel
        );


        // ==================================================
        // EVENTS
        // ==================================================

        btnSave.addActionListener(
                e -> saveExamination()
        );


        btnCancel.addActionListener(
                e -> dispose()
        );
    }


    // ==================================================
    // LOAD SUBJECTS
    // ==================================================

    private void loadSubjects() {

        String sql = """
                SELECT subject_id, subject_code, subject_name
                FROM subjects
                ORDER BY subject_name
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            cmbSubject.removeAllItems();


            while (rs.next()) {

                int id =
                        rs.getInt(
                                "subject_id"
                        );

                String code =
                        rs.getString(
                                "subject_code"
                        );

                String name =
                        rs.getString(
                                "subject_name"
                        );


                cmbSubject.addItem(
                        new SubjectItem(
                                id,
                                code,
                                name
                        )
                );
            }


            if (
                    cmbSubject.getItemCount()
                            == 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "No subjects found.\n\n"
                                + "Please add subjects first.",
                        "No Subjects",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load subjects.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==================================================
    // SAVE EXAMINATION
    // ==================================================

    private void saveExamination() {

        String examName =
                txtExamName
                        .getText()
                        .trim();


        String examType =
                String.valueOf(
                        cmbExamType
                                .getSelectedItem()
                );


        SubjectItem selectedSubject =
                (SubjectItem)
                        cmbSubject
                                .getSelectedItem();


        Integer semester =
                (Integer)
                        cmbSemester
                                .getSelectedItem();


        String examDate =
                txtExamDate
                        .getText()
                        .trim();


        String totalMarksText =
                txtTotalMarks
                        .getText()
                        .trim();


        String passingMarksText =
                txtPassingMarks
                        .getText()
                        .trim();


        // ==================================================
        // VALIDATION
        // ==================================================

        if (examName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter exam name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtExamName.requestFocus();

            return;
        }


        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (semester == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select semester.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (examDate.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter exam date.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // VALIDATE DATE
        // ==================================================

        try {

            java.sql.Date.valueOf(
                    examDate
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\n\n"
                            + "Use format: YYYY-MM-DD",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // VALIDATE MARKS
        // ==================================================

        int totalMarks;

        int passingMarks;


        try {

            totalMarks =
                    Integer.parseInt(
                            totalMarksText
                    );

            passingMarks =
                    Integer.parseInt(
                            passingMarksText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Total Marks and Passing Marks "
                            + "must be numbers.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (totalMarks <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Total marks must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (passingMarks < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passing marks cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (passingMarks > totalMarks) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passing marks cannot be greater "
                            + "than total marks.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // CREATE EXAMINATION OBJECT
        // ==================================================

        Examination exam =
                new Examination();


        exam.setExamName(
                examName
        );


        exam.setExamType(
                examType
        );


        exam.setSubjectId(
                selectedSubject.getSubjectId()
        );


        exam.setSemester(
                semester
        );


        exam.setExamDate(
                examDate
        );


        exam.setTotalMarks(
                totalMarks
        );


        exam.setPassingMarks(
                passingMarks
        );


        // ==================================================
        // SAVE TO DATABASE
        // ==================================================

        boolean saved =
                examinationDAO
                        .addExamination(
                                exam
                        );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Examination added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add examination.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==================================================
    // SUBJECT ITEM CLASS
    // ==================================================

    private static class SubjectItem {

        private int subjectId;
        private String subjectCode;
        private String subjectName;


        public SubjectItem(
                int subjectId,
                String subjectCode,
                String subjectName
        ) {

            this.subjectId = subjectId;

            this.subjectCode = subjectCode;

            this.subjectName = subjectName;
        }


        public int getSubjectId() {

            return subjectId;
        }


        @Override
        public String toString() {

            return subjectCode
                    + " - "
                    + subjectName;
        }
    }
}