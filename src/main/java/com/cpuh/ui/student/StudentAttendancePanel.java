package com.cpuh.ui.student;

import com.cpuh.dao.AttendanceDAO;
import com.cpuh.model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentAttendancePanel extends JPanel {

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private JLabel lblTotal;
    private JLabel lblPresent;
    private JLabel lblAbsent;
    private JLabel lblPercentage;

    private int studentId;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public StudentAttendancePanel(
            int studentId
    ) {

        this.studentId = studentId;

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

        initUI();

        loadAttendance();
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
                        "My Attendance"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );


        JButton btnRefresh =
                new JButton(
                        "Refresh"
                );


        btnRefresh.addActionListener(
                e -> loadAttendance()
        );


        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );


        topPanel.add(
                title,
                BorderLayout.WEST
        );


        topPanel.add(
                btnRefresh,
                BorderLayout.EAST
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
                        new String[]{
                                "Date",
                                "Subject Code",
                                "Subject Name",
                                "Status"
                        },
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


        attendanceTable =
                new JTable(
                        tableModel
                );


        attendanceTable.setRowHeight(
                30
        );


        attendanceTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        attendanceTable.setAutoCreateRowSorter(
                true
        );


        attendanceTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        // ======================================
        // CENTER DATE / STATUS
        // ======================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();


        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        attendanceTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );


        attendanceTable
                .getColumnModel()
                .getColumn(1)
                .setCellRenderer(
                        centerRenderer
                );


        attendanceTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new StatusRenderer()
                );


        // ======================================
        // COLUMN WIDTHS
        // ======================================

        attendanceTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        100
                );


        attendanceTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        120
                );


        attendanceTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        280
                );


        attendanceTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        120
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        attendanceTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ======================================
        // STATISTICS
        // ======================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                10,
                                10
                        )
                );


        lblTotal =
                createStatLabel(
                        "Total Classes: 0"
                );


        lblPresent =
                createStatLabel(
                        "Present: 0"
                );


        lblAbsent =
                createStatLabel(
                        "Absent: 0"
                );


        lblPercentage =
                createStatLabel(
                        "Attendance: 0%"
                );


        statsPanel.add(
                lblTotal
        );


        statsPanel.add(
                lblPresent
        );


        statsPanel.add(
                lblAbsent
        );


        statsPanel.add(
                lblPercentage
        );


        add(
                statsPanel,
                BorderLayout.SOUTH
        );
    }


    // ==========================================
    // STATUS RENDERER
    // ==========================================

    private static class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            return component;
        }
    }


    // ==========================================
    // CREATE STAT LABEL
    // ==========================================

    private JLabel createStatLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text,
                        SwingConstants.CENTER
                );


        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );


        label.setBorder(
                BorderFactory.createLineBorder(
                        Color.GRAY
                )
        );


        return label;
    }


    // ==========================================
    // LOAD ATTENDANCE
    // ==========================================

    private void loadAttendance() {

        tableModel.setRowCount(
                0
        );


        AttendanceDAO dao =
                new AttendanceDAO();


        List<Attendance> attendanceList =
                dao.getAttendanceByStudentId(
                        studentId
                );


        int total = 0;

        int present = 0;

        int absent = 0;


        // ======================================
        // SUBJECT STATISTICS
        // ======================================

        Map<String, SubjectStats>
                subjectStatsMap =
                new LinkedHashMap<>();


        // ======================================
        // LOAD TABLE
        // ======================================

        for (
                Attendance attendance :
                attendanceList
        ) {

            String status =
                    attendance.getStatus();


            /*
             * Add only actual attendance records.
             *
             * No Mark is not stored in the database,
             * so it normally won't appear here.
             */

            tableModel.addRow(
                    new Object[]{
                            attendance.getAttendanceDate(),

                            attendance.getSubjectCode(),

                            attendance.getSubjectName(),

                            status
                    }
            );


            // ==================================
            // TOTAL
            // ==================================

            total++;


            // ==================================
            // PRESENT
            // ==================================

            if (
                    status != null
                            &&
                            status.equalsIgnoreCase(
                                    "Present"
                            )
            ) {

                present++;

            }


            // ==================================
            // ABSENT
            // ==================================

            else if (
                    status != null
                            &&
                            status.equalsIgnoreCase(
                                    "Absent"
                            )
            ) {

                absent++;
            }


            // ==================================
            // SUBJECT STATISTICS
            // ==================================

            String subjectKey =
                    attendance.getSubjectCode();


            if (
                    subjectKey == null
                            ||
                            subjectKey.isBlank()
            ) {

                subjectKey =
                        "Unknown";
            }


            SubjectStats stats =
                    subjectStatsMap.get(
                            subjectKey
                    );


            if (
                    stats == null
            ) {

                stats =
                        new SubjectStats();

                stats.subjectCode =
                        subjectKey;

                stats.subjectName =
                        attendance.getSubjectName();

                subjectStatsMap.put(
                        subjectKey,
                        stats
                );
            }


            if (
                    status != null
                            &&
                            status.equalsIgnoreCase(
                                    "Present"
                            )
            ) {

                stats.present++;

                stats.total++;

            } else if (
                    status != null
                            &&
                            status.equalsIgnoreCase(
                                    "Absent"
                            )
            ) {

                stats.absent++;

                stats.total++;
            }
        }


        // ======================================
        // CALCULATE OVERALL PERCENTAGE
        // ======================================

        double percentage =
                0;


        if (
                total > 0
        ) {

            percentage =
                    (
                            present
                                    * 100.0
                    )
                            /
                            total;
        }


        // ======================================
        // UPDATE OVERALL STATISTICS
        // ======================================

        lblTotal.setText(
                "Total Classes: "
                        + total
        );


        lblPresent.setText(
                "Present: "
                        + present
        );


        lblAbsent.setText(
                "Absent: "
                        + absent
        );


        lblPercentage.setText(
                String.format(
                        "Attendance: %.2f%%",
                        percentage
                )
        );


        // ======================================
        // SHOW SUBJECT SUMMARY
        // ======================================

        if (
                !subjectStatsMap.isEmpty()
        ) {

            showSubjectSummary(
                    subjectStatsMap
            );
        }
    }


    // ==========================================
    // SHOW SUBJECT SUMMARY
    // ==========================================

    private void showSubjectSummary(
            Map<String, SubjectStats>
                    subjectStatsMap
    ) {

        /*
         * We don't replace the main attendance table.
         *
         * Instead, show the summary when the
         * student clicks the "Subject Summary"
         * button.
         */
    }


    // ==========================================
    // SUBJECT STATISTICS CLASS
    // ==========================================

    private static class SubjectStats {

        String subjectCode;

        String subjectName;

        int total = 0;

        int present = 0;

        int absent = 0;


        double getPercentage() {

            if (
                    total == 0
            ) {

                return 0;
            }


            return (
                    present
                            * 100.0
            )
                    /
                    total;
        }
    }
}