package com.cpuh.ui.dashboard;

import com.cpuh.model.User;

import com.cpuh.ui.attendance.AttendancePanel;
import com.cpuh.ui.course.CoursePanel;
import com.cpuh.ui.department.DepartmentPanel;
import com.cpuh.ui.examination.ExaminationPanel;
import com.cpuh.ui.faculty.FacultyPanel;
import com.cpuh.ui.student.StudentPanel;
import com.cpuh.ui.fees.FeePanel;
import com.cpuh.ui.library.LibraryPanel;
import com.cpuh.ui.hostel.HostelPanel;
import com.cpuh.ui.transport.TransportPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private User user;
    private JPanel contentPanel;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public DashboardFrame(User user) {

        this.user = user;

        // ADMIN ONLY
        if (user == null || user.getRoleId() != 1) {

            JOptionPane.showMessageDialog(
                    null,
                    "Access Denied!\n\n"
                            + "Only ADMIN can access the main ERP dashboard.",
                    "Unauthorized Access",
                    JOptionPane.ERROR_MESSAGE
            );

            dispose();
            return;
        }


        setTitle("U ERP System- Admin Dashboard");

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
                new Color(
                        0,
                        70,
                        140
                )
        );

        header.setPreferredSize(
                new Dimension(
                        1200,
                        60
                )
        );


        JLabel title =
                new JLabel(
                        "CPUH ERP - Admin Dashboard"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
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


        JLabel adminLabel =
                new JLabel(
                        "Admin: "
                                + user.getFullName()
                );

        adminLabel.setForeground(
                Color.WHITE
        );

        adminLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        adminLabel.setBorder(
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
                adminLabel,
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
                new JPanel();


        sidebar.setLayout(
                new GridLayout(
                        12,
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


        JButton btnDashboard =
                new JButton("Dashboard");

        JButton btnStudents =
                new JButton("Students");

        JButton btnFaculty =
                new JButton("Faculty");

        JButton btnDepartment =
                new JButton("Departments");

        JButton btnCourse =
                new JButton("Courses");

        JButton btnAttendance =
                new JButton("Attendance");

        JButton btnExam =
                new JButton("Examinations");

        JButton btnFees =
                new JButton("Fees");

        JButton btnLibrary =
                new JButton("Library");

        JButton btnHostel =
                new JButton("Hostel");

        JButton btnTransport =
                new JButton("Transport");

        JButton btnLogout =
                new JButton("Logout");


        sidebar.add(btnDashboard);
        sidebar.add(btnStudents);
        sidebar.add(btnFaculty);
        sidebar.add(btnDepartment);
        sidebar.add(btnCourse);
        sidebar.add(btnAttendance);
        sidebar.add(btnExam);
        sidebar.add(btnFees);
        sidebar.add(btnLibrary);
        sidebar.add(btnHostel);
        sidebar.add(btnTransport);
        sidebar.add(btnLogout);


        add(
                sidebar,
                BorderLayout.WEST
        );


        // ==================================================
        // CENTER CONTENT
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
        // DASHBOARD
        // ==================================================

        btnDashboard.addActionListener(
                e -> showDashboard()
        );


        // ==================================================
        // STUDENTS
        // ==================================================

        btnStudents.addActionListener(e -> {

            showPanel(
                    new StudentPanel()
            );

        });


        // ==================================================
        // FACULTY
        // ==================================================

        btnFaculty.addActionListener(e -> {

            showPanel(
                    new FacultyPanel()
            );

        });


        // ==================================================
        // DEPARTMENTS
        // ==================================================

        btnDepartment.addActionListener(e -> {

            showPanel(
                    new DepartmentPanel()
            );

        });


        // ==================================================
        // COURSES
        // ==================================================

        btnCourse.addActionListener(e -> {

            showPanel(
                    new CoursePanel()
            );

        });


        // ==================================================
        // ATTENDANCE
        // ==================================================

        btnAttendance.addActionListener(e -> {

            showPanel(
                    new AttendancePanel()
            );

        });


        // ==================================================
        // EXAMINATIONS
        // ==================================================

        btnExam.addActionListener(e -> {

            showPanel(
                    new ExaminationPanel()
            );

        });


        // ==================================================
        // FEES
        // ==================================================

        btnFees.addActionListener(e -> {

            showPanel(
                    new FeePanel()
            );

        });


        // ==================================================
        // LIBRARY
        // ==================================================

        btnLibrary.addActionListener(e -> {

            showPanel(
                    new LibraryPanel()
            );

        });


        // ==================================================
        // HOSTEL
        // ==================================================

        btnHostel.addActionListener(e -> {

            showPanel(
                    new HostelPanel()
            );

        });


        // ==================================================
        // TRANSPORT
        // ==================================================

        btnTransport.addActionListener(e -> {

            showPanel(
                    new TransportPanel()
            );

        });


        // ==================================================
        // LOGOUT
        // ==================================================

        btnLogout.addActionListener(
                e -> logout()
        );
    }


    // ==================================================
    // SHOW PANEL
    // ==================================================

    private void showPanel(
            JPanel panel
    ) {

        contentPanel.removeAll();

        contentPanel.add(
                panel,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // ==================================================
    // SHOW DASHBOARD
    // ==================================================

    private void showDashboard() {

        contentPanel.removeAll();


        JPanel dashboardPanel =
                new JPanel(
                        new GridBagLayout()
                );


        JLabel welcome =
                new JLabel(
                        "Welcome, "
                                + user.getFullName(),
                        SwingConstants.CENTER
                );


        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Administrator Control Panel",
                        SwingConstants.CENTER
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


        dashboardPanel.add(
                center
        );


        contentPanel.add(
                dashboardPanel,
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