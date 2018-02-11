package com.kutash.taxibuber.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type Message manager.
 */
public class MessageManager {

    private ResourceBundle resourceBundle;

    /**
     * Instantiates a new Message manager.
     *
     * @param locale the locale
     */
    public MessageManager(String locale) {
        Locale.setDefault(new Locale("en", "US"));
        if (locale != null) {
            String[] country = locale.split("_");
            if (country.length == 2){
                resourceBundle = ResourceBundle.getBundle("messages", new Locale(country[0],country[1]));
            }else {
                resourceBundle = ResourceBundle.getBundle("messages", new Locale(locale));
            }
        }else {
            resourceBundle = ResourceBundle.getBundle("messages");
        }
    }

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
