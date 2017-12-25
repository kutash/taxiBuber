package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBConfigurationManager {

    /*private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("dataBase");

    private DBConfigurationManager(){}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }*/

    private static final Logger LOGGER = LogManager.getLogger();
    private Properties properties;

    {
        properties = new Properties();
        try {
            properties.load(DBConfigurationManager.class.getResourceAsStream("/dataBase.properties"));
            System.out.println(properties.toString());
        } catch (IOException e) {
            LOGGER.log(Level.FATAL,"Exception during loading properties {}",e);
        }
    }

    public DBConfigurationManager(){}

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
