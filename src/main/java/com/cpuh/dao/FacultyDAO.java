package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Faculty;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    // ==========================================
    // ADD FACULTY
    // ==========================================

    public boolean addFaculty(Faculty faculty) {

        String sql = """
                INSERT INTO faculty
                (
                    employee_id,
                    first_name,
                    last_name,
                    gender,
                    dob,
                    email,
                    phone,
                    qualification,
                    designation,
                    salary,
                    joining_date,
                    department_id
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, faculty.getEmployeeId());
            pst.setString(2, faculty.getFirstName());
            pst.setString(3, faculty.getLastName());
            pst.setString(4, faculty.getGender());

            // DOB
            if (faculty.getDob() == null ||
                    faculty.getDob().isBlank()) {

                pst.setNull(5, java.sql.Types.DATE);

            } else {

                pst.setDate(
                        5,
                        java.sql.Date.valueOf(
                                faculty.getDob()
                        )
                );
            }

            pst.setString(6, faculty.getEmail());
            pst.setString(7, faculty.getPhone());
            pst.setString(8, faculty.getQualification());
            pst.setString(9, faculty.getDesignation());

            pst.setDouble(
                    10,
                    faculty.getSalary()
            );

            // Joining Date
            if (faculty.getJoiningDate() == null ||
                    faculty.getJoiningDate().isBlank()) {

                pst.setNull(
                        11,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        11,
                        java.sql.Date.valueOf(
                                faculty.getJoiningDate()
                        )
                );
            }

            // Department
            pst.setInt(
                    12,
                    faculty.getDepartmentId()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add faculty.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL FACULTY
    // ==========================================

    public List<Faculty> getAllFaculty() {

        List<Faculty> facultyList =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.faculty_id,
                    f.employee_id,
                    f.first_name,
                    f.last_name,
                    f.gender,
                    f.dob,
                    f.email,
                    f.phone,
                    f.qualification,
                    f.designation,
                    f.salary,
                    f.joining_date,
                    f.department_id,
                    d.department_name
                FROM faculty f
                LEFT JOIN departments d
                    ON f.department_id = d.department_id
                ORDER BY f.faculty_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql);
                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                Faculty faculty =
                        new Faculty();

                faculty.setFacultyId(
                        rs.getInt("faculty_id")
                );

                faculty.setEmployeeId(
                        rs.getString("employee_id")
                );

                faculty.setFirstName(
                        rs.getString("first_name")
                );

                faculty.setLastName(
                        rs.getString("last_name")
                );

                faculty.setGender(
                        rs.getString("gender")
                );

                if (rs.getDate("dob") != null) {

                    faculty.setDob(
                            rs.getDate("dob").toString()
                    );
                }

                faculty.setEmail(
                        rs.getString("email")
                );

                faculty.setPhone(
                        rs.getString("phone")
                );

                faculty.setQualification(
                        rs.getString("qualification")
                );

                faculty.setDesignation(
                        rs.getString("designation")
                );

                faculty.setSalary(
                        rs.getDouble("salary")
                );

                if (rs.getDate("joining_date") != null) {

                    faculty.setJoiningDate(
                            rs.getDate(
                                    "joining_date"
                            ).toString()
                    );
                }

                faculty.setDepartmentId(
                        rs.getInt("department_id")
                );

                faculty.setDepartmentName(
                        rs.getString("department_name")
                );

                facultyList.add(faculty);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load faculty.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return facultyList;
    }


    // ==========================================
    // DELETE FACULTY
    // ==========================================

    public boolean deleteFaculty(int facultyId) {

        String sql =
                "DELETE FROM faculty WHERE faculty_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, facultyId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete faculty.\n\n"
                            + "Faculty ID: " + facultyId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH FACULTY
    // ==========================================

    public List<Faculty> searchFaculty(
            String search
    ) {

        List<Faculty> facultyList =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.faculty_id,
                    f.employee_id,
                    f.first_name,
                    f.last_name,
                    f.gender,
                    f.dob,
                    f.email,
                    f.phone,
                    f.qualification,
                    f.designation,
                    f.salary,
                    f.joining_date,
                    f.department_id,
                    d.department_name
                FROM faculty f
                LEFT JOIN departments d
                    ON f.department_id = d.department_id
                WHERE
                    f.employee_id LIKE ?
                    OR f.first_name LIKE ?
                    OR f.last_name LIKE ?
                    OR f.email LIKE ?
                    OR f.designation LIKE ?
                ORDER BY f.faculty_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            String keyword =
                    "%" + search + "%";

            pst.setString(1, keyword);
            pst.setString(2, keyword);
            pst.setString(3, keyword);
            pst.setString(4, keyword);
            pst.setString(5, keyword);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Faculty faculty =
                        new Faculty();

                faculty.setFacultyId(
                        rs.getInt("faculty_id")
                );

                faculty.setEmployeeId(
                        rs.getString("employee_id")
                );

                faculty.setFirstName(
                        rs.getString("first_name")
                );

                faculty.setLastName(
                        rs.getString("last_name")
                );

                faculty.setGender(
                        rs.getString("gender")
                );

                if (rs.getDate("dob") != null) {

                    faculty.setDob(
                            rs.getDate("dob").toString()
                    );
                }

                faculty.setEmail(
                        rs.getString("email")
                );

                faculty.setPhone(
                        rs.getString("phone")
                );

                faculty.setQualification(
                        rs.getString("qualification")
                );

                faculty.setDesignation(
                        rs.getString("designation")
                );

                faculty.setSalary(
                        rs.getDouble("salary")
                );

                if (rs.getDate("joining_date") != null) {

                    faculty.setJoiningDate(
                            rs.getDate(
                                    "joining_date"
                            ).toString()
                    );
                }

                faculty.setDepartmentId(
                        rs.getInt("department_id")
                );

                faculty.setDepartmentName(
                        rs.getString(
                                "department_name"
                        )
                );

                facultyList.add(faculty);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search faculty.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return facultyList;
    }
}