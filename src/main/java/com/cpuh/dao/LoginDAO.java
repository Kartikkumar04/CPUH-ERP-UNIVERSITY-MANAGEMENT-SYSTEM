package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public User login(String username, String password) {

        User user = null;

        String sql = """
                SELECT
                    user_id,
                    username,
                    password,
                    full_name,
                    email,
                    role_id,
                    status
                FROM users
                WHERE username = ?
                  AND password = ?
                  AND status = 'ACTIVE'
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    user = new User();

                    user.setUserId(
                            rs.getInt("user_id")
                    );

                    user.setUsername(
                            rs.getString("username")
                    );

                    user.setPassword(
                            rs.getString("password")
                    );

                    user.setFullName(
                            rs.getString("full_name")
                    );

                    user.setEmail(
                            rs.getString("email")
                    );

                    user.setRoleId(
                            rs.getInt("role_id")
                    );

                    user.setStatus(
                            rs.getString("status")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return user;
    }
}