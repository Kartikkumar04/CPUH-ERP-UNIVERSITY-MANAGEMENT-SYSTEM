package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Fee;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {

    // ==========================================
    // ADD FEE
    // ==========================================

    public boolean addFee(Fee fee) {

        String sql = """
                INSERT INTO fees
                (
                    student_id,
                    semester,
                    total_fee,
                    paid_amount,
                    due_amount,
                    payment_date,
                    payment_mode,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    fee.getStudentId()
            );

            pst.setInt(
                    2,
                    fee.getSemester()
            );

            pst.setDouble(
                    3,
                    fee.getTotalFee()
            );

            pst.setDouble(
                    4,
                    fee.getPaidAmount()
            );

            pst.setDouble(
                    5,
                    fee.getDueAmount()
            );


            // Payment date
            if (
                    fee.getPaymentDate() == null
                            || fee.getPaymentDate().isBlank()
            ) {

                pst.setNull(
                        6,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        6,
                        java.sql.Date.valueOf(
                                fee.getPaymentDate()
                        )
                );
            }


            // Payment mode
            if (
                    fee.getPaymentMode() == null
                            || fee.getPaymentMode().isBlank()
            ) {

                pst.setNull(
                        7,
                        java.sql.Types.VARCHAR
                );

            } else {

                pst.setString(
                        7,
                        fee.getPaymentMode()
                );
            }


            // Status
            if (
                    fee.getStatus() == null
                            || fee.getStatus().isBlank()
            ) {

                pst.setString(
                        8,
                        "Pending"
                );

            } else {

                pst.setString(
                        8,
                        fee.getStatus()
                );
            }


            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add fee.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL FEES
    // ==========================================

    public List<Fee> getAllFees() {

        List<Fee> fees =
                new ArrayList<>();


        String sql = """
                SELECT
                    fee_id,
                    student_id,
                    semester,
                    total_fee,
                    paid_amount,
                    due_amount,
                    payment_date,
                    payment_mode,
                    status
                FROM fees
                ORDER BY fee_id DESC
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

                Fee fee =
                        new Fee();


                fee.setFeeId(
                        rs.getInt(
                                "fee_id"
                        )
                );


                fee.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );


                fee.setSemester(
                        rs.getInt(
                                "semester"
                        )
                );


                fee.setTotalFee(
                        rs.getDouble(
                                "total_fee"
                        )
                );


                fee.setPaidAmount(
                        rs.getDouble(
                                "paid_amount"
                        )
                );


                fee.setDueAmount(
                        rs.getDouble(
                                "due_amount"
                        )
                );


                if (
                        rs.getDate(
                                "payment_date"
                        ) != null
                ) {

                    fee.setPaymentDate(
                            rs.getDate(
                                    "payment_date"
                            ).toString()
                    );
                }


                fee.setPaymentMode(
                        rs.getString(
                                "payment_mode"
                        )
                );


                fee.setStatus(
                        rs.getString(
                                "status"
                        )
                );


                fees.add(fee);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load fees.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return fees;
    }


    // ==========================================
    // DELETE FEE
    // ==========================================

    public boolean deleteFee(
            int feeId
    ) {

        String sql =
                "DELETE FROM fees " +
                        "WHERE fee_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    feeId
            );


            int rowsAffected =
                    pst.executeUpdate();


            return rowsAffected > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete fee.\n\n"
                            + "Fee ID: "
                            + feeId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH FEES
    // ==========================================

    public List<Fee> searchFees(
            String search
    ) {

        List<Fee> fees =
                new ArrayList<>();


        String sql = """
                SELECT
                    fee_id,
                    student_id,
                    semester,
                    total_fee,
                    paid_amount,
                    due_amount,
                    payment_date,
                    payment_mode,
                    status
                FROM fees
                WHERE
                    CAST(student_id AS CHAR) LIKE ?
                    OR CAST(semester AS CHAR) LIKE ?
                    OR payment_mode LIKE ?
                    OR status LIKE ?
                ORDER BY fee_id DESC
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


            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {

                Fee fee =
                        new Fee();


                fee.setFeeId(
                        rs.getInt(
                                "fee_id"
                        )
                );


                fee.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );


                fee.setSemester(
                        rs.getInt(
                                "semester"
                        )
                );


                fee.setTotalFee(
                        rs.getDouble(
                                "total_fee"
                        )
                );


                fee.setPaidAmount(
                        rs.getDouble(
                                "paid_amount"
                        )
                );


                fee.setDueAmount(
                        rs.getDouble(
                                "due_amount"
                        )
                );


                if (
                        rs.getDate(
                                "payment_date"
                        ) != null
                ) {

                    fee.setPaymentDate(
                            rs.getDate(
                                    "payment_date"
                            ).toString()
                    );
                }


                fee.setPaymentMode(
                        rs.getString(
                                "payment_mode"
                        )
                );


                fee.setStatus(
                        rs.getString(
                                "status"
                        )
                );


                fees.add(fee);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search fees.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return fees;
    }
}