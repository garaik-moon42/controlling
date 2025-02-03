package com.moon42.kh;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private final String dbHost;
    private final String dbPort;
    private final String dbName;
    private final String dbUser;
    private final String dbPassword;


    public Configuration(String propertyFileName) {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propertyFilePath = rootPath + propertyFileName;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load properties file: " + propertyFilePath, e);
        }
        dbHost = properties.getProperty("db.host");
        dbPort = properties.getProperty("db.port");
        dbName = properties.getProperty("db.name");
        dbUser = properties.getProperty("db.user");
        dbPassword = properties.getProperty("db.password");
    }

    public String getDbHost() {
        return dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
