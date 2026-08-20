package com.cpuh.ui.transport;

import com.cpuh.dao.TransportDAO;
import com.cpuh.model.Transport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransportPanel extends JPanel {

    private JTable transportTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private TransportDAO transportDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public TransportPanel() {

        transportDAO = new TransportDAO();

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

        loadTransports();
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
                        "Transport Management"
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
                        "+ Add Transport"
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

                "Transport ID",
                "Student ID",
                "Bus Number",
                "Route Name",
                "Pickup Point",
                "Driver Name",
                "Driver Phone",
                "Transport Fee",
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


        transportTable =
                new JTable(
                        tableModel
                );


        transportTable.setRowHeight(
                28
        );


        transportTable.setAutoCreateRowSorter(
                true
        );


        transportTable
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
                        transportTable
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
                        "Delete Selected Transport Record"
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

                new TransportForm(
                        (JFrame) window
                );


                loadTransports();
            }
        });


        btnRefresh.addActionListener(e -> {

            loadTransports();

        });


        btnSearch.addActionListener(e -> {

            searchTransports();

        });


        txtSearch.addActionListener(e -> {

            searchTransports();

        });


        btnDelete.addActionListener(e -> {

            deleteSelectedTransport();

        });
    }


    // ==================================================
    // LOAD TRANSPORTS
    // ==================================================

    private void loadTransports() {

        tableModel.setRowCount(0);


        List<Transport> transports =
                transportDAO.getAllTransports();


        for (Transport transport : transports) {

            addTransportToTable(
                    transport
            );
        }
    }


    // ==================================================
    // ADD TRANSPORT TO TABLE
    // ==================================================

    private void addTransportToTable(
            Transport transport
    ) {

        tableModel.addRow(
                new Object[]{

                        transport.getTransportId(),

                        transport.getStudentId(),

                        transport.getBusNumber(),

                        transport.getRouteName(),

                        transport.getPickupPoint(),

                        transport.getDriverName(),

                        transport.getDriverPhone(),

                        String.format(
                                "₹ %.2f",
                                transport.getTransportFee()
                        ),

                        transport.getStatus()
                }
        );
    }


    // ==================================================
    // SEARCH TRANSPORTS
    // ==================================================

    private void searchTransports() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadTransports();

            return;
        }


        tableModel.setRowCount(0);


        List<Transport> transports =
                transportDAO.searchTransports(
                        search
                );


        for (Transport transport : transports) {

            addTransportToTable(
                    transport
            );
        }
    }


    // ==================================================
    // DELETE TRANSPORT
    // ==================================================

    private void deleteSelectedTransport() {

        int selectedRow =
                transportTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a transport record first.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row
        int modelRow =
                transportTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int transportId =
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


        String busNumber =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                2
                        )
                );


        String routeName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                3
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this transport record?\n\n"
                                + "Transport ID: "
                                + transportId
                                + "\n"
                                + "Student ID: "
                                + studentId
                                + "\n"
                                + "Bus Number: "
                                + busNumber
                                + "\n"
                                + "Route: "
                                + routeName,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    transportDAO.deleteTransport(
                            transportId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Transport record deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadTransports();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete transport record.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}