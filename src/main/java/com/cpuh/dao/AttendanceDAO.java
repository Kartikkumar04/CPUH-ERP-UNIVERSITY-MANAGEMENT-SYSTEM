package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Attendance;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // ==================================================
    // ADD ATTENDANCE
    // ==================================================

    public boolean addAttendance(Attendance attendance) {

        String sql = """
                INSERT INTO attendance
                (
                    student_id,
                    subject_id,
                    faculty_id,
                    attendance_date,
                    status
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    attendance.getStudentId()
            );

            pst.setInt(
                    2,
                    attendance.getSubjectId()
            );

            pst.setInt(
                    3,
                    attendance.getFacultyId()
            );

            pst.setDate(
                    4,
                    java.sql.Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            pst.setString(
                    5,
                    attendance.getStatus()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==================================================
    // GET ALL ATTENDANCE
    // ==================================================

    public List<Attendance> getAllAttendance() {

        List<Attendance> attendanceList =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.attendance_id,
                    a.student_id,
                    s.roll_no,
                    a.subject_id,
                    sub.subject_code,
                    a.faculty_id,
                    CONCAT(
                        f.first_name,
                        ' ',
                        COALESCE(f.last_name, '')
                    ) AS faculty_name,
                    a.attendance_date,
                    a.status

                FROM attendance a

                LEFT JOIN students s
                    ON a.student_id = s.student_id

                LEFT JOIN subjects sub
                    ON a.subject_id = sub.subject_id

                LEFT JOIN faculty f
                    ON a.faculty_id = f.faculty_id

                ORDER BY a.attendance_id DESC
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                Attendance attendance =
                        new Attendance();

                attendance.setAttendanceId(
                        rs.getInt(
                                "attendance_id"
                        )
                );

                // Keep IDs internally
                attendance.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );

                attendance.setSubjectId(
                        rs.getInt(
                                "subject_id"
                        )
                );

                attendance.setFacultyId(
                        rs.getInt(
                                "faculty_id"
                        )
                );

                // Display information
                attendance.setRollNo(
                        rs.getString(
                                "roll_no"
                        )
                );

                attendance.setSubjectCode(
                        rs.getString(
                                "subject_code"
                        )
                );

                attendance.setFacultyName(
                        rs.getString(
                                "faculty_name"
                        )
                );

                if (
                        rs.getDate(
                                "attendance_date"
                        ) != null
                ) {

                    attendance.setAttendanceDate(
                            rs.getDate(
                                    "attendance_date"
                            ).toString()
                    );
                }

                attendance.setStatus(
                        rs.getString(
                                "status"
                        )
                );

                attendanceList.add(
                        attendance
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return attendanceList;
    }


    // ==================================================
    // SEARCH ATTENDANCE
    // ==================================================

    public List<Attendance> searchAttendance(
            String search
    ) {

        List<Attendance> attendanceList =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.attendance_id,
                    a.student_id,
                    s.roll_no,
                    a.subject_id,
                    sub.subject_code,
                    a.faculty_id,
                    CONCAT(
                        f.first_name,
                        ' ',
                        COALESCE(f.last_name, '')
                    ) AS faculty_name,
                    a.attendance_date,
                    a.status

                FROM attendance a

                LEFT JOIN students s
                    ON a.student_id = s.student_id

                LEFT JOIN subjects sub
                    ON a.subject_id = sub.subject_id

                LEFT JOIN faculty f
                    ON a.faculty_id = f.faculty_id

                WHERE
                    CAST(a.attendance_id AS CHAR) LIKE ?
                    OR s.roll_no LIKE ?
                    OR sub.subject_code LIKE ?
                    OR CONCAT(
                        f.first_name,
                        ' ',
                        COALESCE(f.last_name, '')
                    ) LIKE ?
                    OR a.attendance_date LIKE ?
                    OR a.status LIKE ?

                ORDER BY a.attendance_id DESC
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            String keyword =
                    "%" + search + "%";


            pst.setString(
                    1,
                    keyword
            );

            pst.setString(
                    2,
                    keyword
            );

            pst.setString(
                    3,
                    keyword
            );

            pst.setString(
                    4,
                    keyword
            );

            pst.setString(
                    5,
                    keyword
            );

            pst.setString(
                    6,
                    keyword
            );


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                while (rs.next()) {

                    Attendance attendance =
                            new Attendance();

                    attendance.setAttendanceId(
                            rs.getInt(
                                    "attendance_id"
                            )
                    );

                    attendance.setStudentId(
                            rs.getInt(
                                    "student_id"
                            )
                    );

                    attendance.setSubjectId(
                            rs.getInt(
                                    "subject_id"
                            )
                    );

                    attendance.setFacultyId(
                            rs.getInt(
                                    "faculty_id"
                            )
                    );

                    attendance.setRollNo(
                            rs.getString(
                                    "roll_no"
                            )
                    );

                    attendance.setSubjectCode(
                            rs.getString(
                                    "subject_code"
                            )
                    );

                    attendance.setFacultyName(
                            rs.getString(
                                    "faculty_name"
                            )
                    );

                    if (
                            rs.getDate(
                                    "attendance_date"
                            ) != null
                    ) {

                        attendance.setAttendanceDate(
                                rs.getDate(
                                        "attendance_date"
                                ).toString()
                        );
                    }

                    attendance.setStatus(
                            rs.getString(
                                    "status"
                            )
                    );

                    attendanceList.add(
                            attendance
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return attendanceList;
    }


    // ==================================================
    // DELETE ATTENDANCE
    // ==================================================

    public boolean deleteAttendance(
            int attendanceId
    ) {

        String sql =
                "DELETE FROM attendance " +
                        "WHERE attendance_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    attendanceId
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==================================================
    // GET ATTENDANCE BY STUDENT
    // ==================================================

    public List<Attendance> getAttendanceByStudentId(
            int studentId
    ) {

        List<Attendance> attendanceList =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.attendance_id,
                    a.student_id,
                    a.subject_id,
                    sub.subject_code,
                    sub.subject_name,
                    a.faculty_id,
                    a.attendance_date,
                    a.status

                FROM attendance a

                LEFT JOIN subjects sub
                    ON a.subject_id = sub.subject_id

                WHERE a.student_id = ?

                ORDER BY a.attendance_date DESC
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    studentId
            );

            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                while (rs.next()) {

                    Attendance attendance =
                            new Attendance();

                    attendance.setAttendanceId(
                            rs.getInt(
                                    "attendance_id"
                            )
                    );

                    attendance.setStudentId(
                            rs.getInt(
                                    "student_id"
                            )
                    );

                    attendance.setSubjectId(
                            rs.getInt(
                                    "subject_id"
                            )
                    );

                    attendance.setSubjectCode(
                            rs.getString(
                                    "subject_code"
                            )
                    );

                    attendance.setSubjectName(
                            rs.getString(
                                    "subject_name"
                            )
                    );

                    attendance.setFacultyId(
                            rs.getInt(
                                    "faculty_id"
                            )
                    );

                    if (
                            rs.getDate(
                                    "attendance_date"
                            ) != null
                    ) {

                        attendance.setAttendanceDate(
                                rs.getDate(
                                        "attendance_date"
                                ).toString()
                        );
                    }

                    attendance.setStatus(
                            rs.getString(
                                    "status"
                            )
                    );

                    attendanceList.add(
                            attendance
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load student attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return attendanceList;
    }
}