package com.kutash.taxibuber.resource;

import java.util.ResourceBundle;

public class PageManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private PageManager(){}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
