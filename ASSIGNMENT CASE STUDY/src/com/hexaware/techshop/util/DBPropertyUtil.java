package com.hexaware.techshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties getConnectionProperties(String fileName) throws IOException {
        Properties props = new Properties();

        
        InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (input == null) {
            throw new IOException("Properties file '" + fileName + "' not found in classpath");
        }

        props.load(input);
        return props;
    }
}
