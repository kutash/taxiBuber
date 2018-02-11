package com.kutash.taxibuber.resource;

import java.util.ResourceBundle;

/**
 * The type Page manager.
 */
public class PageManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private PageManager(){}

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
