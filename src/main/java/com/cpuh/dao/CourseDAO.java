package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Course;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // ==========================================
    // ADD COURSE
    // ==========================================

    public boolean addCourse(Course course) {

        String sql = """
                INSERT INTO courses
                (
                    course_name,
                    course_code,
                    duration_years,
                    total_semesters,
                    department_id
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, course.getCourseName());

            pst.setString(2, course.getCourseCode());

            if (course.getDurationYears() > 0) {

                pst.setInt(
                        3,
                        course.getDurationYears()
                );

            } else {

                pst.setNull(
                        3,
                        java.sql.Types.INTEGER
                );
            }

            if (course.getTotalSemesters() > 0) {

                pst.setInt(
                        4,
                        course.getTotalSemesters()
                );

            } else {

                pst.setNull(
                        4,
                        java.sql.Types.INTEGER
                );
            }

            // department_id is NOT NULL
            pst.setInt(
                    5,
                    course.getDepartmentId()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add course.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL COURSES
    // ==========================================

    public List<Course> getAllCourses() {

        List<Course> courses = new ArrayList<>();

        String sql = """
                SELECT
                    c.course_id,
                    c.course_name,
                    c.course_code,
                    c.duration_years,
                    c.total_semesters,
                    c.department_id,
                    d.department_name
                FROM courses c
                LEFT JOIN departments d
                    ON c.department_id = d.department_id
                ORDER BY c.course_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql);
                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                Course course = new Course();

                course.setCourseId(
                        rs.getInt("course_id")
                );

                course.setCourseName(
                        rs.getString("course_name")
                );

                course.setCourseCode(
                        rs.getString("course_code")
                );

                course.setDurationYears(
                        rs.getInt("duration_years")
                );

                course.setTotalSemesters(
                        rs.getInt("total_semesters")
                );

                course.setDepartmentId(
                        rs.getInt("department_id")
                );

                course.setDepartmentName(
                        rs.getString("department_name")
                );

                courses.add(course);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load courses.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return courses;
    }


    // ==========================================
    // DELETE COURSE
    // ==========================================

    public boolean deleteCourse(int courseId) {

        String sql =
                "DELETE FROM courses WHERE course_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    courseId
            );

            int rowsAffected =
                    pst.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete course.\n\n"
                            + "Course ID: " + courseId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH COURSES
    // ==========================================

    public List<Course> searchCourses(String search) {

        List<Course> courses = new ArrayList<>();

        String sql = """
                SELECT
                    c.course_id,
                    c.course_name,
                    c.course_code,
                    c.duration_years,
                    c.total_semesters,
                    c.department_id,
                    d.department_name
                FROM courses c
                LEFT JOIN departments d
                    ON c.department_id = d.department_id
                WHERE
                    c.course_name LIKE ?
                    OR c.course_code LIKE ?
                    OR d.department_name LIKE ?
                ORDER BY c.course_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
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

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Course course = new Course();

                course.setCourseId(
                        rs.getInt("course_id")
                );

                course.setCourseName(
                        rs.getString("course_name")
                );

                course.setCourseCode(
                        rs.getString("course_code")
                );

                course.setDurationYears(
                        rs.getInt("duration_years")
                );

                course.setTotalSemesters(
                        rs.getInt("total_semesters")
                );

                course.setDepartmentId(
                        rs.getInt("department_id")
                );

                course.setDepartmentName(
                        rs.getString("department_name")
                );

                courses.add(course);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search courses.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return courses;
    }
}