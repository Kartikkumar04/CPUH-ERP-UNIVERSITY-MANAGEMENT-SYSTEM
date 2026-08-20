package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Student;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // ==========================================
    // ADD STUDENT + AUTOMATIC LOGIN ACCOUNT
    // ==========================================

    public boolean addStudent(Student student) {

        String checkStudentSql = """
                SELECT student_id
                FROM students
                WHERE roll_no = ?
                """;

        String checkUserSql = """
                SELECT user_id
                FROM users
                WHERE username = ?
                """;

        String userSql = """
                INSERT INTO users
                (
                    username,
                    password,
                    full_name,
                    email,
                    role_id,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String studentSql = """
                INSERT INTO students
                (
                    roll_no,
                    first_name,
                    last_name,
                    gender,
                    dob,
                    email,
                    phone,
                    address,
                    admission_year,
                    semester,
                    user_id
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;


        Connection con = null;

        try {

            // ==========================================
            // GET CONNECTION
            // ==========================================

            con = DBConnection.getConnection();


            // ==========================================
            // START TRANSACTION
            // ==========================================

            con.setAutoCommit(false);


            // ==========================================
            // CHECK DUPLICATE STUDENT ROLL NO
            // ==========================================

            try (
                    PreparedStatement pst =
                            con.prepareStatement(
                                    checkStudentSql
                            )
            ) {

                pst.setString(
                        1,
                        student.getRollNo()
                );


                try (
                        ResultSet rs =
                                pst.executeQuery()
                ) {

                    if (rs.next()) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Student already exists with Roll No:\n"
                                        + student.getRollNo(),
                                "Duplicate Roll Number",
                                JOptionPane.WARNING_MESSAGE
                        );

                        con.rollback();

                        return false;
                    }
                }
            }


            // ==========================================
            // CHECK DUPLICATE USERNAME
            // ==========================================

            try (
                    PreparedStatement pst =
                            con.prepareStatement(
                                    checkUserSql
                            )
            ) {

                pst.setString(
                        1,
                        student.getRollNo()
                );


                try (
                        ResultSet rs =
                                pst.executeQuery()
                ) {

                    if (rs.next()) {

                        JOptionPane.showMessageDialog(
                                null,
                                "A login account already exists "
                                        + "for Roll No:\n"
                                        + student.getRollNo(),
                                "Login Account Exists",
                                JOptionPane.WARNING_MESSAGE
                        );

                        con.rollback();

                        return false;
                    }
                }
            }


            // ==========================================
            // CREATE USER ACCOUNT
            // ==========================================

            int userId;


            try (
                    PreparedStatement pst =
                            con.prepareStatement(
                                    userSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                // --------------------------------------
                // FULL NAME
                // --------------------------------------

                String fullName =
                        student.getFirstName();


                if (
                        student.getLastName() != null
                                &&
                                !student.getLastName().isBlank()
                ) {

                    fullName =
                            student.getFirstName()
                                    + " "
                                    + student.getLastName();
                }


                // --------------------------------------
                // USERNAME = ROLL NUMBER
                // --------------------------------------

                pst.setString(
                        1,
                        student.getRollNo()
                );


                // --------------------------------------
                // PASSWORD = ROLL NUMBER
                // --------------------------------------

                pst.setString(
                        2,
                        student.getRollNo()
                );


                // --------------------------------------
                // FULL NAME
                // --------------------------------------

                pst.setString(
                        3,
                        fullName
                );


                // --------------------------------------
                // EMAIL
                // --------------------------------------

                if (
                        student.getEmail() == null
                                ||
                                student.getEmail().isBlank()
                ) {

                    pst.setNull(
                            4,
                            java.sql.Types.VARCHAR
                    );

                } else {

                    pst.setString(
                            4,
                            student.getEmail()
                    );
                }


                // --------------------------------------
                // ROLE = STUDENT
                // --------------------------------------

                pst.setInt(
                        5,
                        2
                );


                // --------------------------------------
                // ACTIVE ACCOUNT
                // --------------------------------------

                pst.setString(
                        6,
                        "ACTIVE"
                );


                // --------------------------------------
                // INSERT USER
                // --------------------------------------

                int rows =
                        pst.executeUpdate();


                if (rows == 0) {

                    throw new Exception(
                            "Failed to create student login account."
                    );
                }


                // --------------------------------------
                // GET GENERATED USER ID
                // --------------------------------------

                try (
                        ResultSet keys =
                                pst.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        throw new Exception(
                                "Failed to get generated user ID."
                        );
                    }


                    userId =
                            keys.getInt(1);
                }
            }


            // ==========================================
            // CREATE STUDENT RECORD
            // ==========================================

            try (
                    PreparedStatement pst =
                            con.prepareStatement(
                                    studentSql
                            )
            ) {

                // --------------------------------------
                // ROLL NUMBER
                // --------------------------------------

                pst.setString(
                        1,
                        student.getRollNo()
                );


                // --------------------------------------
                // FIRST NAME
                // --------------------------------------

                pst.setString(
                        2,
                        student.getFirstName()
                );


                // --------------------------------------
                // LAST NAME
                // --------------------------------------

                if (
                        student.getLastName() == null
                                ||
                                student.getLastName().isBlank()
                ) {

                    pst.setNull(
                            3,
                            java.sql.Types.VARCHAR
                    );

                } else {

                    pst.setString(
                            3,
                            student.getLastName()
                    );
                }


                // --------------------------------------
                // GENDER
                // --------------------------------------

                pst.setString(
                        4,
                        student.getGender()
                );


                // --------------------------------------
                // DOB
                // --------------------------------------

                if (
                        student.getDob() == null
                                ||
                                student.getDob().isBlank()
                ) {

                    pst.setNull(
                            5,
                            java.sql.Types.DATE
                    );

                } else {

                    pst.setDate(
                            5,
                            java.sql.Date.valueOf(
                                    student.getDob()
                            )
                    );
                }


                // --------------------------------------
                // EMAIL
                // --------------------------------------

                if (
                        student.getEmail() == null
                                ||
                                student.getEmail().isBlank()
                ) {

                    pst.setNull(
                            6,
                            java.sql.Types.VARCHAR
                    );

                } else {

                    pst.setString(
                            6,
                            student.getEmail()
                    );
                }


                // --------------------------------------
                // PHONE
                // --------------------------------------

                if (
                        student.getPhone() == null
                                ||
                                student.getPhone().isBlank()
                ) {

                    pst.setNull(
                            7,
                            java.sql.Types.VARCHAR
                    );

                } else {

                    pst.setString(
                            7,
                            student.getPhone()
                    );
                }


                // --------------------------------------
                // ADDRESS
                // --------------------------------------

                if (
                        student.getAddress() == null
                                ||
                                student.getAddress().isBlank()
                ) {

                    pst.setNull(
                            8,
                            java.sql.Types.LONGVARCHAR
                    );

                } else {

                    pst.setString(
                            8,
                            student.getAddress()
                    );
                }


                // --------------------------------------
                // ADMISSION YEAR
                // --------------------------------------

                if (
                        student.getAdmissionYear() > 0
                ) {

                    pst.setInt(
                            9,
                            student.getAdmissionYear()
                    );

                } else {

                    pst.setNull(
                            9,
                            java.sql.Types.INTEGER
                    );
                }


                // --------------------------------------
                // SEMESTER
                // --------------------------------------

                pst.setInt(
                        10,
                        student.getSemester()
                );


                // --------------------------------------
                // USER ID
                // --------------------------------------

                pst.setInt(
                        11,
                        userId
                );


                // --------------------------------------
                // INSERT STUDENT
                // --------------------------------------

                int rows =
                        pst.executeUpdate();


                if (rows == 0) {

                    throw new Exception(
                            "Failed to create student record."
                    );
                }
            }


            // ==========================================
            // COMMIT
            // ==========================================

            con.commit();


            // ==========================================
            // SUCCESS MESSAGE
            // ==========================================

            JOptionPane.showMessageDialog(
                    null,

                    "Student added successfully!\n\n"

                            + "Name: "
                            + student.getFirstName()
                            + " "
                            + (
                            student.getLastName() == null
                                    ? ""
                                    : student.getLastName()
                    )

                            + "\nRoll No: "
                            + student.getRollNo()

                            + "\n\n"

                            + "LOGIN DETAILS"

                            + "\nUsername: "
                            + student.getRollNo()

                            + "\nPassword: "
                            + student.getRollNo(),

                    "Student Account Created",

                    JOptionPane.INFORMATION_MESSAGE
            );


            return true;


        } catch (Exception e) {

            e.printStackTrace();


            // ==========================================
            // ROLLBACK
            // ==========================================

            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception rollbackException) {

                rollbackException.printStackTrace();
            }


            JOptionPane.showMessageDialog(
                    null,

                    "Failed to add student.\n\n"
                            + e.getMessage(),

                    "Database Error",

                    JOptionPane.ERROR_MESSAGE
            );


            return false;


        } finally {

            // ==========================================
            // CLOSE CONNECTION
            // ==========================================

            try {

                if (con != null) {

                    con.setAutoCommit(true);

                    con.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }


    // ==========================================
    // GET ALL STUDENTS
    // ==========================================

    public List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();


        String sql = """
                SELECT
                    student_id,
                    user_id,
                    roll_no,
                    first_name,
                    last_name,
                    gender,
                    dob,
                    email,
                    phone,
                    address,
                    admission_year,
                    semester,
                    department_id,
                    course_id
                FROM students
                ORDER BY student_id DESC
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

                Student student =
                        new Student();


                student.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );


                student.setUserId(
                        rs.getInt(
                                "user_id"
                        )
                );


                student.setRollNo(
                        rs.getString(
                                "roll_no"
                        )
                );


                student.setFirstName(
                        rs.getString(
                                "first_name"
                        )
                );


                student.setLastName(
                        rs.getString(
                                "last_name"
                        )
                );


                student.setGender(
                        rs.getString(
                                "gender"
                        )
                );


                if (
                        rs.getDate(
                                "dob"
                        ) != null
                ) {

                    student.setDob(
                            rs.getDate(
                                    "dob"
                            ).toString()
                    );
                }


                student.setEmail(
                        rs.getString(
                                "email"
                        )
                );


                student.setPhone(
                        rs.getString(
                                "phone"
                        )
                );


                student.setAddress(
                        rs.getString(
                                "address"
                        )
                );


                student.setAdmissionYear(
                        rs.getInt(
                                "admission_year"
                        )
                );


                student.setSemester(
                        rs.getInt(
                                "semester"
                        )
                );


                student.setDepartmentId(
                        rs.getInt(
                                "department_id"
                        )
                );


                student.setCourseId(
                        rs.getInt(
                                "course_id"
                        )
                );


                students.add(
                        student
                );
            }


        } catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    null,

                    "Failed to load students:\n\n"
                            + e.getMessage(),

                    "Database Error",

                    JOptionPane.ERROR_MESSAGE
            );
        }


        return students;
    }


    // ==========================================
    // UPDATE STUDENT
    // ==========================================

    public boolean updateStudent(
            Student student
    ) {

        String sql = """
                UPDATE students
                SET
                    roll_no = ?,
                    first_name = ?,
                    last_name = ?,
                    gender = ?,
                    dob = ?,
                    email = ?,
                    phone = ?,
                    address = ?,
                    admission_year = ?,
                    semester = ?
                WHERE student_id = ?
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    student.getRollNo()
            );


            pst.setString(
                    2,
                    student.getFirstName()
            );


            pst.setString(
                    3,
                    student.getLastName()
            );


            pst.setString(
                    4,
                    student.getGender()
            );


            // DOB
            if (
                    student.getDob() == null
                            ||
                            student.getDob().isBlank()
            ) {

                pst.setNull(
                        5,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        5,
                        java.sql.Date.valueOf(
                                student.getDob()
                        )
                );
            }


            pst.setString(
                    6,
                    student.getEmail()
            );


            pst.setString(
                    7,
                    student.getPhone()
            );


            pst.setString(
                    8,
                    student.getAddress()
            );


            // Admission Year
            if (
                    student.getAdmissionYear() > 0
            ) {

                pst.setInt(
                        9,
                        student.getAdmissionYear()
                );

            } else {

                pst.setNull(
                        9,
                        java.sql.Types.INTEGER
                );
            }


            pst.setInt(
                    10,
                    student.getSemester()
            );


            pst.setInt(
                    11,
                    student.getStudentId()
            );


            int rowsAffected =
                    pst.executeUpdate();


            return rowsAffected > 0;


        } catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    null,

                    "Failed to update student:\n\n"
                            + e.getMessage(),

                    "Database Error",

                    JOptionPane.ERROR_MESSAGE
            );


            return false;
        }
    }


    // ==========================================
    // DELETE STUDENT
    // ==========================================

    public boolean deleteStudent(
            int studentId
    ) {

        String sql =
                "DELETE FROM students WHERE student_id = ?";


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


            int rowsAffected =
                    pst.executeUpdate();


            return rowsAffected > 0;


        } catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    null,

                    "Failed to delete student.\n\n"
                            + "Student ID: "
                            + studentId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),

                    "Database Delete Error",

                    JOptionPane.ERROR_MESSAGE
            );


            return false;
        }
    }


    // ==========================================
    // GET STUDENT ID BY USER ID
    // ==========================================

    public int getStudentIdByUserId(
            int userId
    ) {

        String sql = """
                SELECT student_id
                FROM students
                WHERE user_id = ?
                """;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    userId
            );


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(
                            "student_id"
                    );
                }
            }


        } catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    null,

                    "Failed to find student account.\n\n"
                            + e.getMessage(),

                    "Database Error",

                    JOptionPane.ERROR_MESSAGE
            );
        }


        return -1;
    }
}