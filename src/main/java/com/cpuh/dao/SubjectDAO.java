package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Subject;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {


    // ==========================================
    // GET ALL SUBJECTS
    // ==========================================

    public List<Subject> getAllSubjects() {

        List<Subject> subjectList =
                new ArrayList<>();


        String sql = """
                SELECT
                    subject_id,
                    subject_code,
                    subject_name,
                    semester,
                    credits,
                    course_id,
                    faculty_id
                FROM subjects
                ORDER BY subject_name
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

                Subject subject =
                        new Subject();


                subject.setSubjectId(
                        rs.getInt(
                                "subject_id"
                        )
                );


                subject.setSubjectCode(
                        rs.getString(
                                "subject_code"
                        )
                );


                subject.setSubjectName(
                        rs.getString(
                                "subject_name"
                        )
                );


                subject.setSemester(
                        rs.getInt(
                                "semester"
                        )
                );


                subject.setCredits(
                        rs.getInt(
                                "credits"
                        )
                );


                subject.setCourseId(
                        rs.getInt(
                                "course_id"
                        )
                );


                subject.setFacultyId(
                        rs.getInt(
                                "faculty_id"
                        )
                );


                subjectList.add(
                        subject
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load subjects.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return subjectList;
    }


    // ==========================================
    // GET SUBJECT BY ID
    // ==========================================

    public Subject getSubjectById(
            int subjectId
    ) {

        String sql = """
                SELECT
                    subject_id,
                    subject_code,
                    subject_name,
                    semester,
                    credits,
                    course_id,
                    faculty_id
                FROM subjects
                WHERE subject_id = ?
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    subjectId
            );


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                if (rs.next()) {

                    Subject subject =
                            new Subject();


                    subject.setSubjectId(
                            rs.getInt(
                                    "subject_id"
                            )
                    );


                    subject.setSubjectCode(
                            rs.getString(
                                    "subject_code"
                            )
                    );


                    subject.setSubjectName(
                            rs.getString(
                                    "subject_name"
                            )
                    );


                    subject.setSemester(
                            rs.getInt(
                                    "semester"
                            )
                    );


                    subject.setCredits(
                            rs.getInt(
                                    "credits"
                            )
                    );


                    subject.setCourseId(
                            rs.getInt(
                                    "course_id"
                            )
                    );


                    subject.setFacultyId(
                            rs.getInt(
                                    "faculty_id"
                            )
                    );


                    return subject;
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to find subject.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return null;
    }


    // ==========================================
    // GET SUBJECTS BY FACULTY
    // ==========================================

    public List<Subject> getSubjectsByFaculty(
            int facultyId
    ) {

        List<Subject> subjectList =
                new ArrayList<>();


        String sql = """
                SELECT
                    subject_id,
                    subject_code,
                    subject_name,
                    semester,
                    credits,
                    course_id,
                    faculty_id
                FROM subjects
                WHERE faculty_id = ?
                ORDER BY semester, subject_name
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    facultyId
            );


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                while (rs.next()) {

                    Subject subject =
                            new Subject();


                    subject.setSubjectId(
                            rs.getInt(
                                    "subject_id"
                            )
                    );


                    subject.setSubjectCode(
                            rs.getString(
                                    "subject_code"
                            )
                    );


                    subject.setSubjectName(
                            rs.getString(
                                    "subject_name"
                            )
                    );


                    subject.setSemester(
                            rs.getInt(
                                    "semester"
                            )
                    );


                    subject.setCredits(
                            rs.getInt(
                                    "credits"
                            )
                    );


                    subject.setCourseId(
                            rs.getInt(
                                    "course_id"
                            )
                    );


                    subject.setFacultyId(
                            rs.getInt(
                                    "faculty_id"
                            )
                    );


                    subjectList.add(
                            subject
                    );
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load faculty subjects.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return subjectList;
    }
}