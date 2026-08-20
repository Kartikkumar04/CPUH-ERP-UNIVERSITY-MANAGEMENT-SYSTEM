package com.cpuh.ui.faculty;

import com.cpuh.dao.FacultyDAO;
import com.cpuh.model.Faculty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FacultyPanel extends JPanel {

    private JTable facultyTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private FacultyDAO facultyDAO;

    public FacultyPanel() {

        facultyDAO = new FacultyDAO();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        initUI();

        loadFaculty();
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
                new JLabel("Faculty Management");

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

        searchPanel.add(
                new JLabel("Search:")
        );

        txtSearch =
                new JTextField(15);

        btnSearch =
                new JButton("Search");

        btnRefresh =
                new JButton("Refresh");

        btnAdd =
                new JButton("+ Add Faculty");

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
                "Employee ID",
                "First Name",
                "Last Name",
                "Gender",
                "DOB",
                "Email",
                "Phone",
                "Qualification",
                "Designation",
                "Salary",
                "Joining Date",
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

        facultyTable =
                new JTable(tableModel);

        facultyTable.setRowHeight(28);

        facultyTable.setAutoCreateRowSorter(true);

        facultyTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        facultyTable
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
                        "Delete Selected Faculty"
                );

        bottomPanel.add(btnDelete);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // BUTTON EVENTS
        // ==========================================

        btnAdd.addActionListener(e -> {

            Window window =
                    SwingUtilities
                            .getWindowAncestor(this);

            if (window instanceof JFrame) {

                new FacultyForm(
                        (JFrame) window
                );

                loadFaculty();
            }
        });

        btnRefresh.addActionListener(e -> {

            loadFaculty();

        });

        btnSearch.addActionListener(e -> {

            searchFaculty();

        });

        txtSearch.addActionListener(e -> {

            searchFaculty();

        });

        btnDelete.addActionListener(e -> {

            deleteSelectedFaculty();

        });
    }

    // ==========================================
    // LOAD FACULTY
    // ==========================================

    private void loadFaculty() {

        tableModel.setRowCount(0);

        List<Faculty> facultyList =
                facultyDAO.getAllFaculty();

        for (Faculty faculty : facultyList) {

            addFacultyRow(faculty);
        }
    }

    // ==========================================
    // ADD ROW
    // ==========================================

    private void addFacultyRow(
            Faculty faculty
    ) {

        tableModel.addRow(
                new Object[]{

                        faculty.getFacultyId(),

                        faculty.getEmployeeId(),

                        faculty.getFirstName(),

                        faculty.getLastName(),

                        faculty.getGender(),

                        faculty.getDob(),

                        faculty.getEmail(),

                        faculty.getPhone(),

                        faculty.getQualification(),

                        faculty.getDesignation(),

                        faculty.getSalary(),

                        faculty.getJoiningDate(),

                        faculty.getDepartmentName()
                }
        );
    }

    // ==========================================
    // SEARCH FACULTY
    // ==========================================

    private void searchFaculty() {

        String search =
                txtSearch.getText()
                        .trim();

        if (search.isEmpty()) {

            loadFaculty();

            return;
        }

        tableModel.setRowCount(0);

        List<Faculty> facultyList =
                facultyDAO.searchFaculty(
                        search
                );

        for (Faculty faculty : facultyList) {

            addFacultyRow(faculty);
        }
    }

    // ==========================================
    // DELETE FACULTY
    // ==========================================

    private void deleteSelectedFaculty() {

        int selectedRow =
                facultyTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a faculty member first.",
                    "No Faculty Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Convert sorted row to model row
        int modelRow =
                facultyTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int facultyId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        String employeeId =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete faculty "
                                + employeeId
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    facultyDAO.deleteFaculty(
                            facultyId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Faculty deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadFaculty();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete faculty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}