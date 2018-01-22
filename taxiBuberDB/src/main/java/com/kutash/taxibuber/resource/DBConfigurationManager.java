package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBConfigurationManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static DBConfigurationManager instance;
    private Properties properties;

    private DBConfigurationManager(){
        properties = new Properties();
        try {
            properties.load(DBConfigurationManager.class.getResourceAsStream("/dataBase.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.FATAL,"Exception during loading properties {}",e);
        }
    }
    
    public static DBConfigurationManager getInstance(){
        if (instance == null) {
            instance = new DBConfigurationManager();
        }
        return instance;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
