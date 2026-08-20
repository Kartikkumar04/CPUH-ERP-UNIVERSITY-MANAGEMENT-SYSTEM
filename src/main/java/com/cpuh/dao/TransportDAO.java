package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Transport;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransportDAO {

    // ==========================================
    // ADD TRANSPORT RECORD
    // ==========================================

    public boolean addTransport(Transport transport) {

        String sql = """
                INSERT INTO transport
                (
                    student_id,
                    bus_number,
                    route_name,
                    pickup_point,
                    driver_name,
                    driver_phone,
                    transport_fee,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    transport.getStudentId()
            );

            pst.setString(
                    2,
                    transport.getBusNumber()
            );

            pst.setString(
                    3,
                    transport.getRouteName()
            );

            pst.setString(
                    4,
                    transport.getPickupPoint()
            );

            pst.setString(
                    5,
                    transport.getDriverName()
            );

            pst.setString(
                    6,
                    transport.getDriverPhone()
            );

            pst.setDouble(
                    7,
                    transport.getTransportFee()
            );

            pst.setString(
                    8,
                    transport.getStatus()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add transport record.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL TRANSPORT RECORDS
    // ==========================================

    public List<Transport> getAllTransports() {

        List<Transport> transports =
                new ArrayList<>();

        String sql = """
                SELECT
                    transport_id,
                    student_id,
                    bus_number,
                    route_name,
                    pickup_point,
                    driver_name,
                    driver_phone,
                    transport_fee,
                    status
                FROM transport
                ORDER BY transport_id DESC
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

                Transport transport =
                        new Transport();

                transport.setTransportId(
                        rs.getInt(
                                "transport_id"
                        )
                );

                transport.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );

                transport.setBusNumber(
                        rs.getString(
                                "bus_number"
                        )
                );

                transport.setRouteName(
                        rs.getString(
                                "route_name"
                        )
                );

                transport.setPickupPoint(
                        rs.getString(
                                "pickup_point"
                        )
                );

                transport.setDriverName(
                        rs.getString(
                                "driver_name"
                        )
                );

                transport.setDriverPhone(
                        rs.getString(
                                "driver_phone"
                        )
                );

                transport.setTransportFee(
                        rs.getDouble(
                                "transport_fee"
                        )
                );

                transport.setStatus(
                        rs.getString(
                                "status"
                        )
                );

                transports.add(
                        transport
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load transport records.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return transports;
    }


    // ==========================================
    // DELETE TRANSPORT RECORD
    // ==========================================

    public boolean deleteTransport(
            int transportId
    ) {

        String sql =
                "DELETE FROM transport "
                        + "WHERE transport_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    transportId
            );

            int rowsAffected =
                    pst.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete transport record.\n\n"
                            + "Transport ID: "
                            + transportId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH TRANSPORT RECORDS
    // ==========================================

    public List<Transport> searchTransports(
            String search
    ) {

        List<Transport> transports =
                new ArrayList<>();

        String sql = """
                SELECT
                    transport_id,
                    student_id,
                    bus_number,
                    route_name,
                    pickup_point,
                    driver_name,
                    driver_phone,
                    transport_fee,
                    status
                FROM transport
                WHERE
                    bus_number LIKE ?
                    OR route_name LIKE ?
                    OR pickup_point LIKE ?
                    OR driver_name LIKE ?
                    OR driver_phone LIKE ?
                    OR status LIKE ?
                    OR CAST(student_id AS CHAR) LIKE ?
                ORDER BY transport_id DESC
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

            pst.setString(
                    7,
                    keyword
            );

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Transport transport =
                        new Transport();

                transport.setTransportId(
                        rs.getInt(
                                "transport_id"
                        )
                );

                transport.setStudentId(
                        rs.getInt(
                                "student_id"
                        )
                );

                transport.setBusNumber(
                        rs.getString(
                                "bus_number"
                        )
                );

                transport.setRouteName(
                        rs.getString(
                                "route_name"
                        )
                );

                transport.setPickupPoint(
                        rs.getString(
                                "pickup_point"
                        )
                );

                transport.setDriverName(
                        rs.getString(
                                "driver_name"
                        )
                );

                transport.setDriverPhone(
                        rs.getString(
                                "driver_phone"
                        )
                );

                transport.setTransportFee(
                        rs.getDouble(
                                "transport_fee"
                        )
                );

                transport.setStatus(
                        rs.getString(
                                "status"
                        )
                );

                transports.add(
                        transport
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search transport records.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return transports;
    }
}