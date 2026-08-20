package com.cpuh.ui.hoddashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.cpuh.model.User;

public class HODDashboardFrame extends JFrame {

    private User user;
    private JPanel contentPanel;

    public HODDashboardFrame(User user) {

        this.user = user;

        setTitle("CPUH ERP - HOD Dashboard");
        setSize(1200, 700);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        initUI();

        setVisible(true);
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
                new Color(0, 70, 140)
        );

        header.setPreferredSize(
                new Dimension(1200, 65)
        );


        JLabel title =
                new JLabel(
                        "CPUH ERP - HOD Portal"
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
                        "HOD: "
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
                new JButton("Dashboard");

        JButton btnProfile =
                new JButton("My Profile");

        JButton btnStudents =
                new JButton("Students");

        JButton btnFaculty =
                new JButton("Faculty");

        JButton btnCourses =
                new JButton("Courses");

        JButton btnAttendance =
                new JButton("Attendance");

        JButton btnExaminations =
                new JButton("Examinations");

        JButton btnLogout =
                new JButton("Logout");


        sidebar.add(btnDashboard);
        sidebar.add(btnProfile);
        sidebar.add(btnStudents);
        sidebar.add(btnFaculty);
        sidebar.add(btnCourses);
        sidebar.add(btnAttendance);
        sidebar.add(btnExaminations);
        sidebar.add(btnLogout);


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
        // EVENTS
        // ==================================================

        btnDashboard.addActionListener(
                e -> showDashboard()
        );


        btnProfile.addActionListener(
                e -> showProfile()
        );


        btnStudents.addActionListener(
                e -> showComingSoon(
                        "Students"
                )
        );


        btnFaculty.addActionListener(
                e -> showComingSoon(
                        "Faculty"
                )
        );


        btnCourses.addActionListener(
                e -> showComingSoon(
                        "Courses"
                )
        );


        btnAttendance.addActionListener(
                e -> showComingSoon(
                        "Attendance"
                )
        );


        btnExaminations.addActionListener(
                e -> showComingSoon(
                        "Examinations"
                )
        );


        btnLogout.addActionListener(
                e -> logout()
        );
    }


    // ==================================================
    // DASHBOARD
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
                        "Welcome to your HOD Portal"
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

        center.add(welcome);
        center.add(subtitle);


        panel.add(center);


        contentPanel.add(
                panel,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();
        contentPanel.repaint();
    }


    // ==================================================
    // PROFILE
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

        gbc.gridy++;


        profilePanel.add(
                new JLabel("User ID:"),
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


        gbc.gridx = 0;
        gbc.gridy++;


        profilePanel.add(
                new JLabel("Username:"),
                gbc
        );


        gbc.gridx = 1;

        profilePanel.add(
                new JLabel(
                        user.getUsername()
                ),
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy++;


        profilePanel.add(
                new JLabel("Full Name:"),
                gbc
        );


        gbc.gridx = 1;

        profilePanel.add(
                new JLabel(
                        user.getFullName()
                ),
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy++;


        profilePanel.add(
                new JLabel("Email:"),
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


        gbc.gridx = 0;
        gbc.gridy++;


        profilePanel.add(
                new JLabel("Role:"),
                gbc
        );


        gbc.gridx = 1;

        profilePanel.add(
                new JLabel("HOD"),
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