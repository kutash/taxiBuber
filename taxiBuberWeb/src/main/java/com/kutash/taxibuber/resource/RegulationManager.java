package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * The type Regulation manager.
 */
public class RegulationManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static RegulationManager instance;
    private Properties properties;

    private RegulationManager(){
        properties = new Properties();
        try {
            properties.load(DBConfigurationManager.class.getResourceAsStream("/regulation.properties"));
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }

    /**
     * Get instance regulation manager.
     *
     * @return the regulation manager
     */
    public static RegulationManager getInstance(){
        if (instance == null) {
            instance = new RegulationManager();
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
}
