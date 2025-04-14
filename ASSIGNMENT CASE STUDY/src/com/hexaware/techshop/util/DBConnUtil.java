package com.hexaware.techshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    private static final String PROPERTIES_FILE = "db.properties";  

    public static Connection getConnection() throws SQLException {
        try {
            // Load properties using the updated DBPropertyUtil
            Properties props = DBPropertyUtil.getConnectionProperties(PROPERTIES_FILE);

            String url = props.getProperty("url");
            String user = props.getProperty("username");
            String pass = props.getProperty("password");

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new SQLException("Failed to connect to DB: " + e.getMessage());
        }
    }
}
