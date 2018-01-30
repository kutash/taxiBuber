package com.kutash.taxibuber.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class PhotoManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static PhotoManager instance;
    private Properties properties;

    private PhotoManager(){
        properties = new Properties();
        try {
            properties.load(DBConfigurationManager.class.getResourceAsStream("/photo.properties"));
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }

    public static PhotoManager getInstance(){
        if (instance == null) {
            instance = new PhotoManager();
        }
        return instance;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
