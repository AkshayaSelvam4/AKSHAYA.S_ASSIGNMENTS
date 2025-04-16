package com.hexaware.petpals.util;

import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionString(String filename) {
        Properties props = new Properties();

        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                System.out.println("Properties file '" + filename + "' not found in classpath");
                return null;
            }

            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            return url + "?user=" + user + "&password=" + pass;
        } catch (Exception e) {
            System.out.println("Error reading database properties: " + e.getMessage());
            return null;
        }
    }
}


