package com.cpuh.ui.fees;

import com.cpuh.dao.FeeDAO;
import com.cpuh.model.Fee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FeePanel extends JPanel {

    private JTable feeTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private FeeDAO feeDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public FeePanel() {

        feeDAO = new FeeDAO();

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

        loadFees();
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
                        "Fee Management"
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
                        "+ Add Fee"
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

                "Fee ID",
                "Student ID",
                "Semester",
                "Total Fee",
                "Paid Amount",
                "Due Amount",
                "Payment Date",
                "Payment Mode",
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


        feeTable =
                new JTable(
                        tableModel
                );


        feeTable.setRowHeight(
                28
        );


        feeTable.setAutoCreateRowSorter(
                true
        );


        feeTable
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
                        feeTable
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
                        "Delete Selected Fee"
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

                new FeeForm(
                        (JFrame) window
                );


                loadFees();
            }
        });


        btnRefresh.addActionListener(e -> {

            loadFees();

        });


        btnSearch.addActionListener(e -> {

            searchFees();

        });


        txtSearch.addActionListener(e -> {

            searchFees();

        });


        btnDelete.addActionListener(e -> {

            deleteSelectedFee();

        });
    }


    // ==================================================
    // LOAD FEES
    // ==================================================

    private void loadFees() {

        tableModel.setRowCount(0);


        List<Fee> fees =
                feeDAO.getAllFees();


        for (Fee fee : fees) {

            addFeeToTable(fee);
        }
    }


    // ==================================================
    // ADD FEE TO TABLE
    // ==================================================

    private void addFeeToTable(
            Fee fee
    ) {

        tableModel.addRow(
                new Object[]{

                        fee.getFeeId(),

                        fee.getStudentId(),

                        fee.getSemester(),

                        String.format(
                                "₹ %.2f",
                                fee.getTotalFee()
                        ),

                        String.format(
                                "₹ %.2f",
                                fee.getPaidAmount()
                        ),

                        String.format(
                                "₹ %.2f",
                                fee.getDueAmount()
                        ),

                        fee.getPaymentDate(),

                        fee.getPaymentMode(),

                        fee.getStatus()
                }
        );
    }


    // ==================================================
    // SEARCH FEES
    // ==================================================

    private void searchFees() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadFees();

            return;
        }


        tableModel.setRowCount(0);


        List<Fee> fees =
                feeDAO.searchFees(
                        search
                );


        for (Fee fee : fees) {

            addFeeToTable(fee);
        }
    }


    // ==================================================
    // DELETE FEE
    // ==================================================

    private void deleteSelectedFee() {

        int selectedRow =
                feeTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a fee record first.",
                    "No Fee Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row
        int modelRow =
                feeTable.convertRowIndexToModel(
                        selectedRow
                );


        int feeId =
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


        int semester =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                2
                        );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this fee record?\n\n"
                                + "Fee ID: "
                                + feeId
                                + "\n"
                                + "Student ID: "
                                + studentId
                                + "\n"
                                + "Semester: "
                                + semester,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    feeDAO.deleteFee(
                            feeId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Fee deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadFees();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete fee.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}