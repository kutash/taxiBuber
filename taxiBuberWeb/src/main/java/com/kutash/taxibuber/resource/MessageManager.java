package com.kutash.taxibuber.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;
    private String locale = "en_US";

    public MessageManager(String locale) {
        if (locale != null && !locale.isEmpty()){
            this.locale = locale;
        }
        String[] country = this.locale.split("_");
        resourceBundle = ResourceBundle.getBundle("messages",new Locale(country[0],country[1]));
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
