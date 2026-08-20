package com.cpuh.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/cpuh_erp";
    private static final String USER = "root";
    private static final String PASSWORD = "Rishu";

    public static Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("✅ Database Connected Successfully!");

            return connection;

        } catch (Exception e) {

            System.out.println("❌ Database Connection Failed!");
            e.printStackTrace();

            return null;
        }
    }
}