package com.cpuh.ui.attendance;

import com.cpuh.dao.AttendanceDAO;
import com.cpuh.model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendancePanel extends JPanel {

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private AttendanceDAO attendanceDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public AttendancePanel() {

        attendanceDAO = new AttendanceDAO();

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


    // ==================================================
    // INITIALIZE UI
    // ==================================================

    private void initUI() {

        // ==================================================
        // TOP PANEL
        // ==================================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        JLabel title =
                new JLabel(
                        "Attendance Management"
                );

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


        // ==================================================
        // SEARCH PANEL
        // ==================================================

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
                new JButton(
                        "+ Add Attendance"
                );


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


        // ==================================================
        // TABLE
        // ==================================================

        String[] columns = {

                "Attendance ID",
                "Student ID",
                "Subject ID",
                "Faculty ID",
                "Attendance Date",
                "Status"
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


        attendanceTable =
                new JTable(
                        tableModel
                );


        attendanceTable.setRowHeight(
                28
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


        JScrollPane scrollPane =
                new JScrollPane(
                        attendanceTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ==================================================
        // BOTTOM PANEL
        // ==================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        btnDelete =
                new JButton(
                        "Delete Selected Attendance"
                );


        bottomPanel.add(
                btnDelete
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ==================================================
        // BUTTON EVENTS
        // ==================================================

        btnAdd.addActionListener(e -> {

            Window window =
                    SwingUtilities
                            .getWindowAncestor(
                                    this
                            );


            if (window instanceof JFrame) {

                new AttendanceForm(
                        (JFrame) window
                );


                loadAttendance();
            }
        });


        btnRefresh.addActionListener(e -> {

            loadAttendance();

        });


        btnSearch.addActionListener(e -> {

            searchAttendance();

        });


        txtSearch.addActionListener(e -> {

            searchAttendance();

        });


        btnDelete.addActionListener(e -> {

            deleteSelectedAttendance();

        });
    }


    // ==================================================
    // LOAD ATTENDANCE
    // ==================================================

    private void loadAttendance() {

        tableModel.setRowCount(0);


        List<Attendance> attendanceList =
                attendanceDAO.getAllAttendance();


        for (Attendance attendance :
                attendanceList) {

            addAttendanceToTable(
                    attendance
            );
        }
    }


    // ==================================================
    // ADD ATTENDANCE TO TABLE
    // ==================================================

    private void addAttendanceToTable(
            Attendance attendance
    ) {

        tableModel.addRow(
                new Object[]{

                        attendance.getAttendanceId(),

                        attendance.getStudentId(),

                        attendance.getSubjectId(),

                        attendance.getFacultyId(),

                        attendance.getAttendanceDate(),

                        attendance.getStatus()
                }
        );
    }


    // ==================================================
    // SEARCH ATTENDANCE
    // ==================================================

    private void searchAttendance() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadAttendance();

            return;
        }


        tableModel.setRowCount(0);


        List<Attendance> attendanceList =
                attendanceDAO.searchAttendance(
                        search
                );


        for (Attendance attendance :
                attendanceList) {

            addAttendanceToTable(
                    attendance
            );
        }
    }


    // ==================================================
    // DELETE ATTENDANCE
    // ==================================================

    private void deleteSelectedAttendance() {

        int selectedRow =
                attendanceTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an attendance record first.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row
        int modelRow =
                attendanceTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int attendanceId =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );


        int studentId =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                1
                        );


        String date =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                4
                        )
                );


        String status =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                5
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this attendance record?\n\n"
                                + "Attendance ID: "
                                + attendanceId
                                + "\n"
                                + "Student ID: "
                                + studentId
                                + "\n"
                                + "Date: "
                                + date
                                + "\n"
                                + "Status: "
                                + status,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    attendanceDAO.deleteAttendance(
                            attendanceId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadAttendance();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete attendance.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}