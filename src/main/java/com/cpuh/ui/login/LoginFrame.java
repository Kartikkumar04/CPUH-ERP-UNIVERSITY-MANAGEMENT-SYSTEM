package com.cpuh.ui.login;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.cpuh.dao.LoginDAO;
import com.cpuh.model.User;
import com.cpuh.ui.dashboard.DashboardFrame;
import com.cpuh.ui.facultydashboard.FacultyDashboardFrame;
import com.cpuh.ui.hoddashboard.HODDashboardFrame;
import com.cpuh.ui.studentdashboard.StudentDashboardFrame;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JComboBox<String> cmbLoginType;

    private JButton btnLogin;
    private JButton btnExit;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public LoginFrame() {

        setTitle("CPUH ERP Login");

        setSize(500, 380);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setResizable(false);

        initUI();

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
                        30,
                        20,
                        30
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

        JLabel lblTitle =
                new JLabel(
                        "Career Point University ERP",
                        SwingConstants.CENTER
                );

        lblTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 2;

        mainPanel.add(
                lblTitle,
                gbc
        );


        // ==================================================
        // LOGIN TYPE
        // ==================================================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Login As:"),
                gbc
        );


        cmbLoginType =
                new JComboBox<>(
                        new String[]{
                                "ADMIN",
                                "STUDENT",
                                "FACULTY",
                                "HOD"
                        }
                );


        gbc.gridx = 1;

        mainPanel.add(
                cmbLoginType,
                gbc
        );


        // ==================================================
        // USERNAME
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Username:"),
                gbc
        );


        txtUsername =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtUsername,
                gbc
        );


        // ==================================================
        // PASSWORD
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Password:"),
                gbc
        );


        txtPassword =
                new JPasswordField();


        gbc.gridx = 1;

        mainPanel.add(
                txtPassword,
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
                                5
                        )
                );


        btnLogin =
                new JButton("Login");


        btnExit =
                new JButton("Exit");


        buttonPanel.add(
                btnLogin
        );

        buttonPanel.add(
                btnExit
        );


        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;


        mainPanel.add(
                buttonPanel,
                gbc
        );


        add(mainPanel);


        // ==================================================
        // LOGIN BUTTON
        // ==================================================

        btnLogin.addActionListener(
                e -> loginUser()
        );


        // ==================================================
        // ENTER KEY LOGIN
        // ==================================================

        txtPassword.addActionListener(
                e -> loginUser()
        );


        // ==================================================
        // EXIT BUTTON
        // ==================================================

        btnExit.addActionListener(
                e -> System.exit(0)
        );
    }


    // ==================================================
    // LOGIN USER
    // ==================================================

    private void loginUser() {

        String username =
                txtUsername
                        .getText()
                        .trim();


        String password =
                String.valueOf(
                        txtPassword
                                .getPassword()
                );


        String selectedRole =
                String.valueOf(
                        cmbLoginType
                                .getSelectedItem()
                );


        // ==================================================
        // VALIDATION
        // ==================================================

        if (
                username.isEmpty()
                        ||
                password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // DATABASE LOGIN
        // ==================================================

        LoginDAO dao =
                new LoginDAO();


        User user =
                dao.login(
                        username,
                        password
                );


        // ==================================================
        // INVALID LOGIN
        // ==================================================

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // ==================================================
        // GET ACTUAL ROLE
        // ==================================================

        String actualRole =
                getRoleName(
                        user.getRoleId()
                );


        // ==================================================
        // CHECK ROLE
        // ==================================================

        if (
                actualRole == null
                        ||
                !actualRole.equals(
                        selectedRole
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This account does not belong to the "
                            + selectedRole
                            + " portal.\n\n"
                            + "Your account role is: "
                            + actualRole,
                    "Incorrect Login Type",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // LOGIN SUCCESS
        // ==================================================

        JOptionPane.showMessageDialog(
                this,
                "Welcome "
                        + user.getFullName()
                        + "\n\nRole: "
                        + actualRole,
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE
        );


        // Close login window
        dispose();


        // Open correct dashboard
        openDashboard(user);
    }


    // ==================================================
    // OPEN DASHBOARD BASED ON ROLE
    // ==================================================

    private void openDashboard(
            User user
    ) {

        switch (
                user.getRoleId()
        ) {

            // ==========================================
            // ADMIN
            // ==========================================

            case 1:

                new DashboardFrame(
                        user
                );

                break;


            // ==========================================
            // STUDENT
            // ==========================================

            case 2:

                new StudentDashboardFrame(
                        user
                );

                break;


            // ==========================================
            // FACULTY
            // ==========================================

            case 3:

                new FacultyDashboardFrame(user);

                break;


            // ==========================================
            // HOD
            // ==========================================

            case 4:

                new HODDashboardFrame(user);

                break;


            // ==========================================
            // OTHER ROLES
            // ==========================================

            case 5:

                JOptionPane.showMessageDialog(
                        null,
                        "Accountant Dashboard will be connected next.",
                        "Accountant Portal",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardFrame(
                        user
                );

                break;


            case 6:

                JOptionPane.showMessageDialog(
                        null,
                        "Librarian Dashboard will be connected next.",
                        "Librarian Portal",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardFrame(
                        user
                );

                break;


            case 7:

                JOptionPane.showMessageDialog(
                        null,
                        "Examination Cell Dashboard will be connected next.",
                        "Examination Cell Portal",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardFrame(
                        user
                );

                break;


            case 8:

                JOptionPane.showMessageDialog(
                        null,
                        "Placement Dashboard will be connected next.",
                        "Placement Portal",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardFrame(
                        user
                );

                break;


            case 9:

                JOptionPane.showMessageDialog(
                        null,
                        "Warden Dashboard will be connected next.",
                        "Warden Portal",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardFrame(
                        user
                );

                break;


            case 10:

                JOptionPane.showMessageDialog(
                        null,
                        "Transport Dashboard will be connected next.",
                        "Transport Portal",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardFrame(
                        user
                );

                break;


            default:

                JOptionPane.showMessageDialog(
                        null,
                        "Dashboard not configured for this role.",
                        "Access Error",
                        JOptionPane.ERROR_MESSAGE
                );

                break;
        }
    }


    // ==================================================
    // GET ROLE NAME
    // ==================================================

    private String getRoleName(
            int roleId
    ) {

        switch (roleId) {

            case 1:
                return "ADMIN";

            case 2:
                return "STUDENT";

            case 3:
                return "FACULTY";

            case 4:
                return "HOD";

            case 5:
                return "ACCOUNTANT";

            case 6:
                return "LIBRARIAN";

            case 7:
                return "EXAM_CELL";

            case 8:
                return "PLACEMENT";

            case 9:
                return "WARDEN";

            case 10:
                return "TRANSPORT";

            default:
                return null;
        }
    }
}