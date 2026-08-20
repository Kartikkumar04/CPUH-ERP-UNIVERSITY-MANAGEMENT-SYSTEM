package com.cpuh.ui.studentdashboard;

import com.cpuh.dao.StudentDAO;
import com.cpuh.model.User;
import com.cpuh.ui.student.StudentAttendancePanel;

import javax.swing.*;
import java.awt.*;

public class StudentDashboardFrame extends JFrame {

    private User user;

    private JPanel contentPanel;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public StudentDashboardFrame(
            User user
    ) {

        this.user = user;


        // ==============================================
        // SECURITY CHECK
        // ==============================================

        if (
                user == null
                        ||
                        user.getRoleId() != 2
        ) {

            JOptionPane.showMessageDialog(
                    null,
                    "Access Denied!\n\n"
                            + "Only STUDENT accounts can access "
                            + "the Student Portal.",
                    "Unauthorized Access",
                    JOptionPane.ERROR_MESSAGE
            );

            dispose();

            return;
        }


        setTitle(
                "CPUH ERP - Student Dashboard"
        );


        setSize(
                1200,
                700
        );


        setLocationRelativeTo(
                null
        );


        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );


        initUI();


        setVisible(
                true
        );
    }


    // ==================================================
    // INITIALIZE UI
    // ==================================================

    private void initUI() {

        setLayout(
                new BorderLayout()
        );


        // ==================================================
        // HEADER
        // ==================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );


        header.setBackground(
                new Color(
                        0,
                        70,
                        140
                )
        );


        header.setPreferredSize(
                new Dimension(
                        1200,
                        65
                )
        );


        JLabel title =
                new JLabel(
                        "CPUH ERP - Student Portal"
                );


        title.setForeground(
                Color.WHITE
        );


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );


        title.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        20,
                        0,
                        0
                )
        );


        JLabel userLabel =
                new JLabel(
                        "Student: "
                                + user.getFullName()
                );


        userLabel.setForeground(
                Color.WHITE
        );


        userLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );


        userLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        0,
                        0,
                        20
                )
        );


        header.add(
                title,
                BorderLayout.WEST
        );


        header.add(
                userLabel,
                BorderLayout.EAST
        );


        add(
                header,
                BorderLayout.NORTH
        );


        // ==================================================
        // SIDEBAR
        // ==================================================

        JPanel sidebar =
                new JPanel(
                        new GridLayout(
                                8,
                                1,
                                5,
                                5
                        )
                );


        sidebar.setPreferredSize(
                new Dimension(
                        220,
                        600
                )
        );


        sidebar.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        JButton btnDashboard =
                new JButton(
                        "Dashboard"
                );


        JButton btnProfile =
                new JButton(
                        "My Profile"
                );


        JButton btnAttendance =
                new JButton(
                        "My Attendance"
                );


        JButton btnExamination =
                new JButton(
                        "My Examinations"
                );


        JButton btnFees =
                new JButton(
                        "My Fees"
                );


        JButton btnLibrary =
                new JButton(
                        "Library"
                );


        JButton btnTransport =
                new JButton(
                        "Transport"
                );


        JButton btnLogout =
                new JButton(
                        "Logout"
                );


        sidebar.add(
                btnDashboard
        );


        sidebar.add(
                btnProfile
        );


        sidebar.add(
                btnAttendance
        );


        sidebar.add(
                btnExamination
        );


        sidebar.add(
                btnFees
        );


        sidebar.add(
                btnLibrary
        );


        sidebar.add(
                btnTransport
        );


        sidebar.add(
                btnLogout
        );


        add(
                sidebar,
                BorderLayout.WEST
        );


        // ==================================================
        // CONTENT PANEL
        // ==================================================

        contentPanel =
                new JPanel(
                        new BorderLayout()
                );


        add(
                contentPanel,
                BorderLayout.CENTER
        );


        showDashboard();


        // ==================================================
        // DASHBOARD BUTTON
        // ==================================================

        btnDashboard.addActionListener(
                e -> showDashboard()
        );


        // ==================================================
        // PROFILE BUTTON
        // ==================================================

        btnProfile.addActionListener(
                e -> showProfile()
        );


        // ==================================================
        // ATTENDANCE BUTTON
        // ==================================================

        btnAttendance.addActionListener(
                e -> showAttendance()
        );


        // ==================================================
        // EXAMINATION BUTTON
        // ==================================================

        btnExamination.addActionListener(
                e -> showComingSoon(
                        "My Examinations"
                )
        );


        // ==================================================
        // FEES BUTTON
        // ==================================================

        btnFees.addActionListener(
                e -> showComingSoon(
                        "My Fees"
                )
        );


        // ==================================================
        // LIBRARY BUTTON
        // ==================================================

        btnLibrary.addActionListener(
                e -> showComingSoon(
                        "Library"
                )
        );


        // ==================================================
        // TRANSPORT BUTTON
        // ==================================================

        btnTransport.addActionListener(
                e -> showComingSoon(
                        "Transport"
                )
        );


        // ==================================================
        // LOGOUT BUTTON
        // ==================================================

        btnLogout.addActionListener(
                e -> logout()
        );
    }


    // ==================================================
    // SHOW DASHBOARD
    // ==================================================

    private void showDashboard() {

        contentPanel.removeAll();


        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );


        JLabel welcome =
                new JLabel(
                        "Welcome, "
                                + user.getFullName()
                );


        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Welcome to your Student Portal"
                );


        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );


        JPanel center =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                5,
                                5
                        )
                );


        center.add(
                welcome
        );


        center.add(
                subtitle
        );


        panel.add(
                center
        );


        contentPanel.add(
                panel,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // ==================================================
    // SHOW PROFILE
    // ==================================================

    private void showProfile() {

        contentPanel.removeAll();


        JPanel profilePanel =
                new JPanel(
                        new GridBagLayout()
                );


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );


        gbc.anchor =
                GridBagConstraints.WEST;


        JLabel title =
                new JLabel(
                        "My Profile"
                );


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );


        gbc.gridx = 0;

        gbc.gridy = 0;

        gbc.gridwidth = 2;


        profilePanel.add(
                title,
                gbc
        );


        gbc.gridwidth = 1;


        // ==============================================
        // USER ID
        // ==============================================

        gbc.gridy++;


        profilePanel.add(
                new JLabel(
                        "User ID:"
                ),
                gbc
        );


        gbc.gridx = 1;


        profilePanel.add(
                new JLabel(
                        String.valueOf(
                                user.getUserId()
                        )
                ),
                gbc
        );


        // ==============================================
        // USERNAME
        // ==============================================

        gbc.gridx = 0;

        gbc.gridy++;


        profilePanel.add(
                new JLabel(
                        "Username:"
                ),
                gbc
        );


        gbc.gridx = 1;


        profilePanel.add(
                new JLabel(
                        user.getUsername()
                ),
                gbc
        );


        // ==============================================
        // FULL NAME
        // ==============================================

        gbc.gridx = 0;

        gbc.gridy++;


        profilePanel.add(
                new JLabel(
                        "Full Name:"
                ),
                gbc
        );


        gbc.gridx = 1;


        profilePanel.add(
                new JLabel(
                        user.getFullName()
                ),
                gbc
        );


        // ==============================================
        // EMAIL
        // ==============================================

        gbc.gridx = 0;

        gbc.gridy++;


        profilePanel.add(
                new JLabel(
                        "Email:"
                ),
                gbc
        );


        gbc.gridx = 1;


        profilePanel.add(
                new JLabel(
                        user.getEmail() == null
                                ? "-"
                                : user.getEmail()
                ),
                gbc
        );


        // ==============================================
        // ROLE
        // ==============================================

        gbc.gridx = 0;

        gbc.gridy++;


        profilePanel.add(
                new JLabel(
                        "Role:"
                ),
                gbc
        );


        gbc.gridx = 1;


        profilePanel.add(
                new JLabel(
                        "STUDENT"
                ),
                gbc
        );


        contentPanel.add(
                profilePanel,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // ==================================================
    // SHOW ATTENDANCE
    // ==================================================

    private void showAttendance() {

        contentPanel.removeAll();


        // ==============================================
        // FIND REAL STUDENT ID
        // ==============================================

        StudentDAO studentDAO =
                new StudentDAO();


        int studentId =
                studentDAO.getStudentIdByUserId(
                        user.getUserId()
                );


        // ==============================================
        // STUDENT NOT FOUND
        // ==============================================

        if (
                studentId == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No student profile is linked "
                            + "to this account.\n\n"
                            + "Username: "
                            + user.getUsername()
                            + "\nUser ID: "
                            + user.getUserId(),
                    "Student Profile Not Found",
                    JOptionPane.ERROR_MESSAGE
            );


            showDashboard();


            return;
        }


        // ==============================================
        // OPEN ATTENDANCE
        // ==============================================

        contentPanel.add(
                new StudentAttendancePanel(
                        studentId
                ),
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // ==================================================
    // COMING SOON
    // ==================================================

    private void showComingSoon(
            String module
    ) {

        contentPanel.removeAll();


        JLabel label =
                new JLabel(
                        module
                                + " Module Coming Soon",
                        SwingConstants.CENTER
                );


        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );


        contentPanel.add(
                label,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // ==================================================
    // LOGOUT
    // ==================================================

    private void logout() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                option ==
                        JOptionPane.YES_OPTION
        ) {

            dispose();

            new com.cpuh.ui.login.LoginFrame();
        }
    }
}