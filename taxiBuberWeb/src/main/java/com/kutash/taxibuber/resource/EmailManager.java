package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

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

    public static EmailManager getInstance(){
        if (instance == null) {
            instance = new EmailManager();
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
