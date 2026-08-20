package com.cpuh.ui.hostel;

import com.cpuh.dao.HostelDAO;
import com.cpuh.model.Hostel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HostelPanel extends JPanel {

    private JTable hostelTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private HostelDAO hostelDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public HostelPanel() {

        hostelDAO = new HostelDAO();

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

        loadHostels();
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
                        "Hostel Management"
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
                        "+ Add Hostel"
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

                "Hostel ID",
                "Student ID",
                "Hostel Name",
                "Room No",
                "Floor",
                "Room Type",
                "Check In",
                "Check Out",
                "Hostel Fee",
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


        hostelTable =
                new JTable(
                        tableModel
                );


        hostelTable.setRowHeight(
                28
        );


        hostelTable.setAutoCreateRowSorter(
                true
        );


        hostelTable
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
                        hostelTable
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
                        "Delete Selected Hostel Record"
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

                new HostelForm(
                        (JFrame) window
                );


                loadHostels();
            }
        });


        btnRefresh.addActionListener(e -> {

            loadHostels();

        });


        btnSearch.addActionListener(e -> {

            searchHostels();

        });


        txtSearch.addActionListener(e -> {

            searchHostels();

        });


        btnDelete.addActionListener(e -> {

            deleteSelectedHostel();

        });
    }


    // ==================================================
    // LOAD HOSTELS
    // ==================================================

    private void loadHostels() {

        tableModel.setRowCount(0);


        List<Hostel> hostels =
                hostelDAO.getAllHostels();


        for (Hostel hostel : hostels) {

            addHostelToTable(
                    hostel
            );
        }
    }


    // ==================================================
    // ADD HOSTEL TO TABLE
    // ==================================================

    private void addHostelToTable(
            Hostel hostel
    ) {

        tableModel.addRow(
                new Object[]{

                        hostel.getHostelId(),

                        hostel.getStudentId(),

                        hostel.getHostelName(),

                        hostel.getRoomNumber(),

                        hostel.getFloor(),

                        hostel.getRoomType(),

                        hostel.getCheckIn(),

                        hostel.getCheckOut(),

                        String.format(
                                "₹ %.2f",
                                hostel.getHostelFee()
                        ),

                        hostel.getStatus()
                }
        );
    }


    // ==================================================
    // SEARCH HOSTELS
    // ==================================================

    private void searchHostels() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadHostels();

            return;
        }


        tableModel.setRowCount(0);


        List<Hostel> hostels =
                hostelDAO.searchHostels(
                        search
                );


        for (Hostel hostel : hostels) {

            addHostelToTable(
                    hostel
            );
        }
    }


    // ==================================================
    // DELETE HOSTEL
    // ==================================================

    private void deleteSelectedHostel() {

        int selectedRow =
                hostelTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a hostel record first.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row
        int modelRow =
                hostelTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int hostelId =
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


        String hostelName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                2
                        )
                );


        String roomNumber =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                3
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this hostel record?\n\n"
                                + "Hostel ID: "
                                + hostelId
                                + "\n"
                                + "Student ID: "
                                + studentId
                                + "\n"
                                + "Hostel: "
                                + hostelName
                                + "\n"
                                + "Room: "
                                + roomNumber,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    hostelDAO.deleteHostel(
                            hostelId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Hostel record deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadHostels();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete hostel record.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}