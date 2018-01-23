package com.kutash.taxibuber.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Date parseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        if (StringUtils.isNotEmpty(date)) {
            try {
                Date birthdayUtil = format.parse(date);
                birthday = new Date(birthdayUtil.getTime());
            } catch (ParseException e) {
                LOGGER.log(Level.ERROR,"Exception while parsing date");
            }
        }
        return birthday;
    }
}
