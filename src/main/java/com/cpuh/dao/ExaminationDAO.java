package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Examination;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExaminationDAO {

    // ==========================================
    // ADD EXAMINATION
    // ==========================================

    public boolean addExamination(Examination exam) {

        String sql = """
                INSERT INTO examinations
                (
                    exam_name,
                    exam_type,
                    subject_id,
                    semester,
                    exam_date,
                    total_marks,
                    passing_marks
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    exam.getExamName()
            );

            pst.setString(
                    2,
                    exam.getExamType()
            );

            pst.setInt(
                    3,
                    exam.getSubjectId()
            );

            pst.setInt(
                    4,
                    exam.getSemester()
            );

            if (
                    exam.getExamDate() == null
                            || exam.getExamDate().isBlank()
            ) {

                pst.setNull(
                        5,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        5,
                        java.sql.Date.valueOf(
                                exam.getExamDate()
                        )
                );
            }

            pst.setInt(
                    6,
                    exam.getTotalMarks()
            );

            pst.setInt(
                    7,
                    exam.getPassingMarks()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add examination.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL EXAMINATIONS
    // ==========================================

    public List<Examination> getAllExaminations() {

        List<Examination> examinations =
                new ArrayList<>();

        String sql = """
                SELECT
                    exam_id,
                    exam_name,
                    exam_type,
                    subject_id,
                    semester,
                    exam_date,
                    total_marks,
                    passing_marks
                FROM examinations
                ORDER BY exam_id DESC
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

                Examination exam =
                        new Examination();

                exam.setExamId(
                        rs.getInt("exam_id")
                );

                exam.setExamName(
                        rs.getString("exam_name")
                );

                exam.setExamType(
                        rs.getString("exam_type")
                );

                exam.setSubjectId(
                        rs.getInt("subject_id")
                );

                exam.setSemester(
                        rs.getInt("semester")
                );

                if (
                        rs.getDate("exam_date")
                                != null
                ) {

                    exam.setExamDate(
                            rs.getDate(
                                    "exam_date"
                            ).toString()
                    );
                }

                exam.setTotalMarks(
                        rs.getInt("total_marks")
                );

                exam.setPassingMarks(
                        rs.getInt("passing_marks")
                );

                examinations.add(exam);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load examinations.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return examinations;
    }


    // ==========================================
    // DELETE EXAMINATION
    // ==========================================

    public boolean deleteExamination(
            int examId
    ) {

        String sql =
                "DELETE FROM examinations " +
                        "WHERE exam_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    examId
            );

            int rowsAffected =
                    pst.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete examination.\n\n"
                            + "Exam ID: "
                            + examId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH EXAMINATIONS
    // ==========================================

    public List<Examination> searchExaminations(
            String search
    ) {

        List<Examination> examinations =
                new ArrayList<>();

        String sql = """
                SELECT
                    exam_id,
                    exam_name,
                    exam_type,
                    subject_id,
                    semester,
                    exam_date,
                    total_marks,
                    passing_marks
                FROM examinations
                WHERE
                    exam_name LIKE ?
                    OR exam_type LIKE ?
                    OR CAST(subject_id AS CHAR) LIKE ?
                    OR CAST(semester AS CHAR) LIKE ?
                ORDER BY exam_id DESC
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            String keyword =
                    "%" + search + "%";

            pst.setString(1, keyword);
            pst.setString(2, keyword);
            pst.setString(3, keyword);
            pst.setString(4, keyword);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Examination exam =
                        new Examination();

                exam.setExamId(
                        rs.getInt("exam_id")
                );

                exam.setExamName(
                        rs.getString("exam_name")
                );

                exam.setExamType(
                        rs.getString("exam_type")
                );

                exam.setSubjectId(
                        rs.getInt("subject_id")
                );

                exam.setSemester(
                        rs.getInt("semester")
                );

                if (
                        rs.getDate("exam_date")
                                != null
                ) {

                    exam.setExamDate(
                            rs.getDate(
                                    "exam_date"
                            ).toString()
                    );
                }

                exam.setTotalMarks(
                        rs.getInt("total_marks")
                );

                exam.setPassingMarks(
                        rs.getInt("passing_marks")
                );

                examinations.add(exam);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search examinations.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return examinations;
    }
}