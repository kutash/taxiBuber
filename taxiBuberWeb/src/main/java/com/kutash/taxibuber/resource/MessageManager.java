package com.kutash.taxibuber.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;

    public MessageManager(String locale) {
        System.out.println(locale);
        if (locale != null) {
            resourceBundle = ResourceBundle.getBundle("messages", new Locale(locale));
        }else {
            Locale localeDefault = new Locale("en");
            resourceBundle = ResourceBundle.getBundle("messages", localeDefault);
        }
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
