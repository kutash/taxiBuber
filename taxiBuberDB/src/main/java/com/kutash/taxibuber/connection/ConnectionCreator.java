package com.kutash.taxibuber.connection;

import com.kutash.taxibuber.resource.DBConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

class ConnectionCreator {

    private static final Logger LOGGER = LogManager.getLogger();
    private Properties properties;
    private String url;

    ConnectionCreator(){
        DBConfigurationManager dbConfigurationManager = new DBConfigurationManager();
        properties = dbConfigurationManager.getProperties();
        url = dbConfigurationManager.getProperty("url");
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL,"Can not register driver {}",e);
            throw new RuntimeException(e);
        }
    }

    /*{
        properties  = new Properties();
        try {
            properties.load(ConnectionCreator.class.getResourceAsStream("/dataBase.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.FATAL,"Exception during loading properties {}",e);
            throw new RuntimeException(e);
        }
        url = properties.getProperty("url");
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL,"Can not register driver {}",e);
            throw new RuntimeException(e);
        }
    }*/

    Connection getConnection() {
        Connection connection ;
        try {
            connection = DriverManager.getConnection(url,properties);
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL,"Can not connect to database {}",e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    static void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR,"Exception during deregistration of drivers {}",e);
            }

        }
    }
}
