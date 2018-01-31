package com.kutash.taxibuber.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;

    public MessageManager(String locale) {
        System.out.println(locale);
        Locale.setDefault(new Locale("en", "US"));
        if (locale != null) {
            resourceBundle = ResourceBundle.getBundle("messages", new Locale(locale));
        }
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
