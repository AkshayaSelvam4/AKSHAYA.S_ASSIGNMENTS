package com.hexaware.petpals.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    public static Connection getConnection() {
        String connectionString = DBPropertyUtil.getConnectionString("db.properties");

        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("Error establishing DB connection: " + e.getMessage());
            return null;
        }
    }
}

