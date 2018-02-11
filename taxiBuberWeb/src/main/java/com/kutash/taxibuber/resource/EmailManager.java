package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * The type Email manager.
 */
public class EmailManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static EmailManager instance;
    private Properties properties;

    private EmailManager(){
        properties = new Properties();
        try {
            properties.load(DBConfigurationManager.class.getResourceAsStream("/email.properties"));
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }

    /**
     * Get instance email manager.
     *
     * @return the email manager
     */
    public static EmailManager getInstance(){
        if (instance == null) {
            instance = new EmailManager();
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
