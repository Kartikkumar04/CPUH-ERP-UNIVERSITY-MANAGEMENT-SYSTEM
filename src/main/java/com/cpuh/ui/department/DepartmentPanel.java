package com.cpuh.ui.department;

import com.cpuh.dao.DepartmentDAO;
import com.cpuh.model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel extends JPanel {

    private JTable departmentTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    private DepartmentDAO departmentDAO;

    public DepartmentPanel() {

        departmentDAO = new DepartmentDAO();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        initUI();

        loadDepartments();
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
                new JLabel("Department Management");

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
                new JButton("+ Add Department");

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
                "Department Name",
                "Code",
                "HOD Name",
                "Office Phone",
                "Office Email"
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


        departmentTable =
                new JTable(tableModel);

        departmentTable.setRowHeight(28);

        departmentTable.setAutoCreateRowSorter(true);

        departmentTable
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
                        departmentTable
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

        btnEdit =
                new JButton("Edit Selected Department");

        btnDelete =
                new JButton("Delete Selected Department");


        bottomPanel.add(btnEdit);

        bottomPanel.add(btnDelete);


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // BUTTON EVENTS
        // ==========================================

        btnAdd.addActionListener(e ->
                addDepartment()
        );


        btnEdit.addActionListener(e ->
                editSelectedDepartment()
        );


        btnDelete.addActionListener(e ->
                deleteSelectedDepartment()
        );


        btnRefresh.addActionListener(e ->
                loadDepartments()
        );


        btnSearch.addActionListener(e ->
                searchDepartments()
        );


        txtSearch.addActionListener(e ->
                searchDepartments()
        );
    }


    // ==========================================
    // LOAD DEPARTMENTS
    // ==========================================

    private void loadDepartments() {

        tableModel.setRowCount(0);

        List<Department> departments =
                departmentDAO.getAllDepartments();

        for (Department department : departments) {

            addDepartmentToTable(department);
        }
    }


    // ==========================================
    // ADD ROW TO TABLE
    // ==========================================

    private void addDepartmentToTable(
            Department department
    ) {

        tableModel.addRow(
                new Object[]{

                        department.getDepartmentId(),

                        department.getDepartmentName(),

                        department.getDepartmentCode(),

                        department.getHodName(),

                        department.getOfficePhone(),

                        department.getOfficeEmail()
                }
        );
    }


    // ==========================================
    // SEARCH DEPARTMENTS
    // ==========================================

    private void searchDepartments() {

        String search =
                txtSearch.getText()
                        .trim()
                        .toLowerCase();

        tableModel.setRowCount(0);

        List<Department> departments =
                departmentDAO.getAllDepartments();


        for (Department department : departments) {

            String name =
                    department.getDepartmentName() == null
                            ? ""
                            : department
                            .getDepartmentName()
                            .toLowerCase();

            String code =
                    department.getDepartmentCode() == null
                            ? ""
                            : department
                            .getDepartmentCode()
                            .toLowerCase();

            String hod =
                    department.getHodName() == null
                            ? ""
                            : department
                            .getHodName()
                            .toLowerCase();


            if (
                    name.contains(search)
                            ||
                            code.contains(search)
                            ||
                            hod.contains(search)
            ) {

                addDepartmentToTable(
                        department
                );
            }
        }
    }


    // ==========================================
    // ADD DEPARTMENT
    // ==========================================

    private void addDepartment() {

        JTextField txtName =
                new JTextField();

        JTextField txtCode =
                new JTextField();

        JTextField txtHod =
                new JTextField();

        JTextField txtPhone =
                new JTextField();

        JTextField txtEmail =
                new JTextField();


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );


        panel.add(
                new JLabel("Department Name:")
        );

        panel.add(txtName);


        panel.add(
                new JLabel("Department Code:")
        );

        panel.add(txtCode);


        panel.add(
                new JLabel("HOD Name:")
        );

        panel.add(txtHod);


        panel.add(
                new JLabel("Office Phone:")
        );

        panel.add(txtPhone);


        panel.add(
                new JLabel("Office Email:")
        );

        panel.add(txtEmail);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Department",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result ==
                        JOptionPane.OK_OPTION
        ) {

            String name =
                    txtName.getText().trim();

            String code =
                    txtCode.getText().trim();


            if (
                    name.isEmpty()
                            ||
                            code.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department Name and Code are required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            Department department =
                    new Department();

            department.setDepartmentName(name);

            department.setDepartmentCode(code);

            department.setHodName(
                    txtHod.getText().trim()
            );

            department.setOfficePhone(
                    txtPhone.getText().trim()
            );

            department.setOfficeEmail(
                    txtEmail.getText().trim()
            );


            boolean success =
                    departmentDAO.addDepartment(
                            department
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department added successfully."
                );

                loadDepartments();

            }
        }
    }


    // ==========================================
    // EDIT DEPARTMENT
    // ==========================================

    private void editSelectedDepartment() {

        int selectedRow =
                departmentTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department first.",
                    "No Department Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                departmentTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int departmentId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );


        String currentName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );

        String currentCode =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                2
                        )
                );

        String currentHod =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                3
                        )
                );

        String currentPhone =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                4
                        )
                );

        String currentEmail =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                5
                        )
                );


        JTextField txtName =
                new JTextField(currentName);

        JTextField txtCode =
                new JTextField(currentCode);

        JTextField txtHod =
                new JTextField(currentHod);

        JTextField txtPhone =
                new JTextField(currentPhone);

        JTextField txtEmail =
                new JTextField(currentEmail);


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );


        panel.add(
                new JLabel("Department Name:")
        );

        panel.add(txtName);


        panel.add(
                new JLabel("Department Code:")
        );

        panel.add(txtCode);


        panel.add(
                new JLabel("HOD Name:")
        );

        panel.add(txtHod);


        panel.add(
                new JLabel("Office Phone:")
        );

        panel.add(txtPhone);


        panel.add(
                new JLabel("Office Email:")
        );

        panel.add(txtEmail);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Department",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result ==
                        JOptionPane.OK_OPTION
        ) {

            String name =
                    txtName.getText().trim();

            String code =
                    txtCode.getText().trim();


            if (
                    name.isEmpty()
                            ||
                            code.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department Name and Code are required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            Department department =
                    new Department();

            department.setDepartmentId(
                    departmentId
            );

            department.setDepartmentName(
                    name
            );

            department.setDepartmentCode(
                    code
            );

            department.setHodName(
                    txtHod.getText().trim()
            );

            department.setOfficePhone(
                    txtPhone.getText().trim()
            );

            department.setOfficeEmail(
                    txtEmail.getText().trim()
            );


            boolean success =
                    departmentDAO.updateDepartment(
                            department
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department updated successfully."
                );

                loadDepartments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update department.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // ==========================================
    // DELETE DEPARTMENT
    // ==========================================

    private void deleteSelectedDepartment() {

        int selectedRow =
                departmentTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department first.",
                    "No Department Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                departmentTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int departmentId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );


        String departmentName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete department \""
                                + departmentName
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    departmentDAO.deleteDepartment(
                            departmentId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department deleted successfully."
                );

                loadDepartments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete department.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}