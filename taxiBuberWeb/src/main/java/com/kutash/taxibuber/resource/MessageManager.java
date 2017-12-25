package com.kutash.taxibuber.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;

    public MessageManager(String locale) {
        String[] country = locale.split("_");
        resourceBundle = ResourceBundle.getBundle("messages",new Locale(country[0],country[1]));
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
