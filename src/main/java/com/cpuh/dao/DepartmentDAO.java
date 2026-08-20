package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DepartmentDAO {

    // ==========================================
    // ADD DEPARTMENT
    // ==========================================

    public boolean addDepartment(Department department) {

        String sql = """
                INSERT INTO departments
                (
                    department_name,
                    department_code,
                    hod_name,
                    office_phone,
                    office_email
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, department.getDepartmentName());
            pst.setString(2, department.getDepartmentCode());
            pst.setString(3, department.getHodName());
            pst.setString(4, department.getOfficePhone());
            pst.setString(5, department.getOfficeEmail());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add department.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL DEPARTMENTS
    // ==========================================

    public List<Department> getAllDepartments() {

        List<Department> departments = new ArrayList<>();

        String sql = """
                SELECT
                    department_id,
                    department_name,
                    department_code,
                    hod_name,
                    office_phone,
                    office_email
                FROM departments
                ORDER BY department_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                Department department = new Department();

                department.setDepartmentId(
                        rs.getInt("department_id")
                );

                department.setDepartmentName(
                        rs.getString("department_name")
                );

                department.setDepartmentCode(
                        rs.getString("department_code")
                );

                department.setHodName(
                        rs.getString("hod_name")
                );

                department.setOfficePhone(
                        rs.getString("office_phone")
                );

                department.setOfficeEmail(
                        rs.getString("office_email")
                );

                departments.add(department);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load departments.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return departments;
    }


    // ==========================================
    // UPDATE DEPARTMENT
    // ==========================================

    public boolean updateDepartment(Department department) {

        String sql = """
                UPDATE departments
                SET
                    department_name = ?,
                    department_code = ?,
                    hod_name = ?,
                    office_phone = ?,
                    office_email = ?
                WHERE department_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, department.getDepartmentName());
            pst.setString(2, department.getDepartmentCode());
            pst.setString(3, department.getHodName());
            pst.setString(4, department.getOfficePhone());
            pst.setString(5, department.getOfficeEmail());
            pst.setInt(6, department.getDepartmentId());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to update department.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // DELETE DEPARTMENT
    // ==========================================

    public boolean deleteDepartment(int departmentId) {

        String sql =
                "DELETE FROM departments WHERE department_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, departmentId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete department.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }
}