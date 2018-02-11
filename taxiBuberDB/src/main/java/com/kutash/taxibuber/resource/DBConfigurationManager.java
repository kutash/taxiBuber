package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The type Db configuration manager.
 */
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

    /**
     * Get instance db configuration manager.
     *
     * @return the db configuration manager
     */
    public static DBConfigurationManager getInstance(){
        if (instance == null) {
            instance = new DBConfigurationManager();
        }
        return instance;
    }

    /**
     * Get property string.
     *
     * @param key the key
     * @return the string
     */
    public String getProperty(String key){
        return properties.getProperty(key);
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }
}
