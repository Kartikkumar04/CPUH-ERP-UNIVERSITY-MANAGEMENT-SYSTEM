package com.cpuh.ui.examination;

import com.cpuh.dao.ExaminationDAO;
import com.cpuh.model.Examination;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExaminationPanel extends JPanel {

    private JTable examinationTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private ExaminationDAO examinationDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public ExaminationPanel() {

        examinationDAO =
                new ExaminationDAO();

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

        loadExaminations();
    }


    // ==================================================
    // CREATE UI
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
                        "Examination Management"
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
                        "+ Add Examination"
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

                "ID",
                "Exam Name",
                "Exam Type",
                "Subject ID",
                "Semester",
                "Exam Date",
                "Total Marks",
                "Passing Marks"
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


        examinationTable =
                new JTable(
                        tableModel
                );


        examinationTable.setRowHeight(
                28
        );


        examinationTable.setAutoCreateRowSorter(
                true
        );


        examinationTable
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
                        examinationTable
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
                        "Delete Selected Examination"
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

                new ExaminationForm(
                        (JFrame) window
                );


                loadExaminations();
            }
        });


        btnRefresh.addActionListener(e -> {

            loadExaminations();

        });


        btnSearch.addActionListener(e -> {

            searchExaminations();

        });


        txtSearch.addActionListener(e -> {

            searchExaminations();

        });


        btnDelete.addActionListener(e -> {

            deleteSelectedExamination();

        });
    }


    // ==================================================
    // LOAD EXAMINATIONS
    // ==================================================

    private void loadExaminations() {

        tableModel.setRowCount(0);


        List<Examination> examinations =
                examinationDAO
                        .getAllExaminations();


        for (
                Examination exam :
                examinations
        ) {

            addExamToTable(exam);
        }
    }


    // ==================================================
    // ADD EXAM TO TABLE
    // ==================================================

    private void addExamToTable(
            Examination exam
    ) {

        tableModel.addRow(
                new Object[]{

                        exam.getExamId(),

                        exam.getExamName(),

                        exam.getExamType(),

                        exam.getSubjectId(),

                        exam.getSemester(),

                        exam.getExamDate(),

                        exam.getTotalMarks(),

                        exam.getPassingMarks()
                }
        );
    }


    // ==================================================
    // SEARCH EXAMINATIONS
    // ==================================================

    private void searchExaminations() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadExaminations();

            return;
        }


        tableModel.setRowCount(0);


        List<Examination> examinations =
                examinationDAO
                        .searchExaminations(
                                search
                        );


        for (
                Examination exam :
                examinations
        ) {

            addExamToTable(exam);
        }
    }


    // ==================================================
    // DELETE EXAMINATION
    // ==================================================

    private void deleteSelectedExamination() {

        int selectedRow =
                examinationTable
                        .getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an examination first.",
                    "No Examination Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row
        int modelRow =
                examinationTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int examId =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );


        String examName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete examination?\n\n"
                                + examName,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    examinationDAO
                            .deleteExamination(
                                    examId
                            );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Examination deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadExaminations();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete examination.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}