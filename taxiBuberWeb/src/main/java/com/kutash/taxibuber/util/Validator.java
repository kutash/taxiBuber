package com.kutash.taxibuber.util;

import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PASSWORD = "^[а-яА-Яa-zA-Z0-9_-]{6,40}$";
    private static final String NAME = "^[a-zA-Zа-яА-Я]*[a-zA-Zа-яА-Я-\\s]{3,44}$";
    private static final String EMAIL = "^([-._'a-z0-9])+(\\+)?([-._'a-z0-9])+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$";
    private static final String PHONE = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$";
    private static final String DATE = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";

    public Map<String,String> validateUser(Map<String,String> data,String language){
        LOGGER.log(Level.INFO,"validating user");
        MessageManager messageManager = new MessageManager(language);
        Pattern patternName = Pattern.compile(NAME);
        Pattern patternEmail = Pattern.compile(EMAIL);
        Pattern patternPassword = Pattern.compile(PASSWORD);
        Pattern patternPhone = Pattern.compile(PHONE);
        Pattern patternDate = Pattern.compile(DATE);

        String name = data.get("name");
        String surname = data.get("surname");
        String patronymic = data.get("patronymic");
        String email = data.get("email");
        String password = data.get("password");
        String phone = data.get("phone");
        String birthday = data.get("birthday");
        String role = data.get("role");
        String passwordConfirm = data.get("passwordConfirm");

        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(name)){
            Matcher nameMatcher = patternName.matcher(name);
            if (!nameMatcher.matches()){
                map.put("name", messageManager.getProperty("label.errorname"));
            }
        }else {
            map.put("nameBlank", messageManager.getProperty("label.blank"));
        }
        if (StringUtils.isNotEmpty(surname)){
            Matcher surnameMatcher = patternName.matcher(surname);
            if (!surnameMatcher.matches()){
                map.put("surname", messageManager.getProperty("label.errorname"));
            }
        }else {
            map.put("surnameBlank", messageManager.getProperty("label.blank"));
        }
        if (StringUtils.isNotEmpty(patronymic)) {
            Matcher patronymicMatcher = patternName.matcher(patronymic);
            if (!patronymicMatcher.matches()) {
                map.put("patronymic", messageManager.getProperty("label.errorname"));
            }
        }
        if (StringUtils.isNotEmpty(birthday)) {
            Matcher dateMatcher = patternDate.matcher(birthday);
            if (!dateMatcher.matches() || !checkBirthday(birthday)) {
                map.put("date", messageManager.getProperty("label.wrongdate"));
            }
        }
        if (StringUtils.isNotEmpty(email)) {
            if(new UserService().isUniqueEmail(email)) {
                Matcher emailMatcher = patternEmail.matcher(email);
                if (!emailMatcher.matches()) {
                    map.put("email", messageManager.getProperty("label.invalidemail"));
                }
                if (email.toCharArray().length > 90) {
                    map.put("emailSize", messageManager.getProperty("label.maxemail"));
                }
            }else {
                map.put("notunique", messageManager.getProperty("label.notunique"));
            }
        }else {
            map.put("emailBlank", messageManager.getProperty("label.blank"));
        }
        if (StringUtils.isNotEmpty(phone)) {
            Matcher phoneMatcher = patternPhone.matcher(phone);
            if (!phoneMatcher.matches()) {
                map.put("phone", messageManager.getProperty("label.invalidphone"));
            }
        }
        if (StringUtils.isNotEmpty(password)) {
            Matcher passwordMatcher = patternPassword.matcher(password);
            if (!passwordMatcher.matches()) {
                map.put("password", messageManager.getProperty("label.invalidpsw"));
            }
        }else {
            map.put("passwordBlank", messageManager.getProperty("label.blank"));
        }

        if (!passwordConfirm.equals(password)) {
            map.put("passwordConfirm", messageManager.getProperty("label.notmatch"));
        }
        if (!role.equals(UserRole.CLIENT.name()) && !role.equals(UserRole.DRIVER.name())){
            map.put("role", messageManager.getProperty("label.roleerror"));
        }
        return map;
    }

    private boolean checkBirthday(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            Date birthdayUtil = format.parse(birthday);
            date = new Date(birthdayUtil.getTime());
        } catch (ParseException e) {
            LOGGER.log(Level.ERROR,"Exception while parsing date");
        }
        Date now = new Date();
        return !date.after(now);
    }
}
