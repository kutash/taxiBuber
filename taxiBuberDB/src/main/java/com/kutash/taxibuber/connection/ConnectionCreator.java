package com.kutash.taxibuber.connection;

import com.kutash.taxibuber.resource.DBConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

class ConnectionCreator {

    private static final Logger LOGGER = LogManager.getLogger();
    private int poolSize;
    private Properties properties;
    private String url;

    ConnectionCreator(){
        properties = DBConfigurationManager.getInstance().getProperties();
        url = DBConfigurationManager.getInstance().getProperty("url");
        poolSize  = Integer.parseInt(DBConfigurationManager.getInstance().getProperty("poolSize"));
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.catching(Level.FATAL,e);
            throw new RuntimeException(e);
        }
    }

    Connection getConnection() {
        Connection connection ;
        try {
            connection = DriverManager.getConnection(url,properties);
        } catch (SQLException e) {
            LOGGER.catching(Level.FATAL,e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    public int getPoolSize() {
        return poolSize;
    }

    static void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.catching(Level.ERROR,e);
            }

        }
    }
}
