package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Hostel;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HostelDAO {

    // ==========================================
    // ADD HOSTEL RECORD
    // ==========================================

    public boolean addHostel(Hostel hostel) {

        String sql = """
                INSERT INTO hostel
                (
                    student_id,
                    hostel_name,
                    room_number,
                    floor,
                    room_type,
                    check_in,
                    check_out,
                    hostel_fee,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    hostel.getStudentId()
            );

            pst.setString(
                    2,
                    hostel.getHostelName()
            );

            pst.setString(
                    3,
                    hostel.getRoomNumber()
            );

            pst.setInt(
                    4,
                    hostel.getFloor()
            );

            pst.setString(
                    5,
                    hostel.getRoomType()
            );


            // ==========================================
            // CHECK IN
            // ==========================================

            if (
                    hostel.getCheckIn() == null
                            || hostel.getCheckIn().isBlank()
            ) {

                pst.setNull(
                        6,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        6,
                        java.sql.Date.valueOf(
                                hostel.getCheckIn()
                        )
                );
            }


            // ==========================================
            // CHECK OUT
            // ==========================================

            if (
                    hostel.getCheckOut() == null
                            || hostel.getCheckOut().isBlank()
            ) {

                pst.setNull(
                        7,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        7,
                        java.sql.Date.valueOf(
                                hostel.getCheckOut()
                        )
                );
            }


            pst.setDouble(
                    8,
                    hostel.getHostelFee()
            );

            pst.setString(
                    9,
                    hostel.getStatus()
            );


            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add hostel record.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL HOSTEL RECORDS
    // ==========================================

    public List<Hostel> getAllHostels() {

        List<Hostel> hostels =
                new ArrayList<>();


        String sql = """
                SELECT
                    hostel_id,
                    student_id,
                    hostel_name,
                    room_number,
                    floor,
                    room_type,
                    check_in,
                    check_out,
                    hostel_fee,
                    status
                FROM hostel
                ORDER BY hostel_id DESC
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

                Hostel hostel =
                        new Hostel();


                hostel.setHostelId(
                        rs.getInt(
                                "hostel_id"
                        )
                );


                hostel.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );


                hostel.setHostelName(
                        rs.getString(
                                "hostel_name"
                        )
                );


                hostel.setRoomNumber(
                        rs.getString(
                                "room_number"
                        )
                );


                hostel.setFloor(
                        rs.getInt(
                                "floor"
                        )
                );


                hostel.setRoomType(
                        rs.getString(
                                "room_type"
                        )
                );


                if (
                        rs.getDate(
                                "check_in"
                        ) != null
                ) {

                    hostel.setCheckIn(
                            rs.getDate(
                                    "check_in"
                            ).toString()
                    );
                }


                if (
                        rs.getDate(
                                "check_out"
                        ) != null
                ) {

                    hostel.setCheckOut(
                            rs.getDate(
                                    "check_out"
                            ).toString()
                    );
                }


                hostel.setHostelFee(
                        rs.getDouble(
                                "hostel_fee"
                        )
                );


                hostel.setStatus(
                        rs.getString(
                                "status"
                        )
                );


                hostels.add(
                        hostel
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load hostel records.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return hostels;
    }


    // ==========================================
    // DELETE HOSTEL RECORD
    // ==========================================

    public boolean deleteHostel(
            int hostelId
    ) {

        String sql =
                "DELETE FROM hostel "
                        + "WHERE hostel_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    hostelId
            );


            int rowsAffected =
                    pst.executeUpdate();


            return rowsAffected > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete hostel record.\n\n"
                            + "Hostel ID: "
                            + hostelId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH HOSTEL RECORDS
    // ==========================================

    public List<Hostel> searchHostels(
            String search
    ) {

        List<Hostel> hostels =
                new ArrayList<>();


        String sql = """
                SELECT
                    hostel_id,
                    student_id,
                    hostel_name,
                    room_number,
                    floor,
                    room_type,
                    check_in,
                    check_out,
                    hostel_fee,
                    status
                FROM hostel
                WHERE
                    hostel_name LIKE ?
                    OR room_number LIKE ?
                    OR room_type LIKE ?
                    OR status LIKE ?
                    OR CAST(student_id AS CHAR) LIKE ?
                ORDER BY hostel_id DESC
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


            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {

                Hostel hostel =
                        new Hostel();


                hostel.setHostelId(
                        rs.getInt(
                                "hostel_id"
                        )
                );


                hostel.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );


                hostel.setHostelName(
                        rs.getString(
                                "hostel_name"
                        )
                );


                hostel.setRoomNumber(
                        rs.getString(
                                "room_number"
                        )
                );


                hostel.setFloor(
                        rs.getInt(
                                "floor"
                        )
                );


                hostel.setRoomType(
                        rs.getString(
                                "room_type"
                        )
                );


                if (
                        rs.getDate(
                                "check_in"
                        ) != null
                ) {

                    hostel.setCheckIn(
                            rs.getDate(
                                    "check_in"
                            ).toString()
                    );
                }


                if (
                        rs.getDate(
                                "check_out"
                        ) != null
                ) {

                    hostel.setCheckOut(
                            rs.getDate(
                                    "check_out"
                            ).toString()
                    );
                }


                hostel.setHostelFee(
                        rs.getDouble(
                                "hostel_fee"
                        )
                );


                hostel.setStatus(
                        rs.getString(
                                "status"
                        )
                );


                hostels.add(
                        hostel
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search hostel records.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return hostels;
    }
}