package com.kutash.taxibuber.util;

import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.CarBrand;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PASSWORD = "^[а-яА-Яa-zA-Z0-9_-]{6,40}$";
    private static final String NAME = "^[a-zA-Zа-яА-Я]*[a-zA-Zа-яА-Я-\\s]{3,44}$";
    private static final String EMAIL = "^([-._'a-z0-9])+(\\+)?([-._'a-z0-9])+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$";
    private static final String PHONE = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$";
    private static final String DATE = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";
    private static final String NUMBER = "\\d{4}[A-Z]{2}-\\d";
    private static final String MODEL = "^[а-яА-Яa-zA-Z0-9-\\s]{3,40}$";
    private static final String SPACE = "\\s";
    private static final String DIGIT = "\\d+";

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

    public Map<String,String> validateCar(Map<String,String> data ,String language) {
        LOGGER.log(Level.INFO, "validating car");
        MessageManager messageManager = new MessageManager(language);
        Pattern patternNumber = Pattern.compile(NUMBER);
        Pattern patternModel = Pattern.compile(MODEL);

        String number = data.get("number");
        String model = data.get("model");
        String brand = data.get("brand");
        String capacity = data.get("capacity");
        Map<String, String> map = new HashMap<>();
        CarService service = new CarService();

        if (StringUtils.isNotEmpty(number)) {
            Matcher numberMatcher = patternNumber.matcher(number);
            if (!numberMatcher.matches()) {
                map.put("number", messageManager.getProperty("label.errornumber"));
            }else {
                if(data.get("carId") != null){
                    if (!service.isUniqueNumberForUpdare(number,Integer.parseInt(data.get("carId")))) {
                        map.put("number", messageManager.getProperty("label.notuniquenumber"));
                    }
                }else {
                    if (!service.isUniqueNumber(number)) {
                        map.put("number", messageManager.getProperty("label.notuniquenumber"));
                    }
                }
            }
        } else {
            map.put("numberBlank", messageManager.getProperty("label.blank"));
        }

        if (StringUtils.isNotEmpty(model)){
            Matcher modelMatcher = patternModel.matcher(model);
            if (!modelMatcher.matches()){
                map.put("model", messageManager.getProperty("label.errormodel"));
            }
        }else {
            map.put("modelBlank", messageManager.getProperty("label.blank"));
        }
        if (!checkBrand(brand)){
            map.put("brand", messageManager.getProperty("label.errorbrand"));
        }
        if(!checkCapacity(capacity)){
            map.put("capacity", messageManager.getProperty("label.errorcapacity"));
        }
        return map;
    }

    public HashMap<String,String> validateOrder(HashMap<String,String> data,String language){
        LOGGER.log(Level.INFO, "validating order");
        HashMap<String, String> map = new HashMap<>();
        MessageManager messageManager = new MessageManager(language);
        if (StringUtils.isEmpty(data.get("carId"))){
            map.put("emptyCar",messageManager.getProperty("label.emptyCar"));
        }
        if (StringUtils.isEmpty(data.get("duration")) || StringUtils.isEmpty(data.get("distance"))) {
            map.put("distance", messageManager.getProperty("message.wrongorder"));
        }else {
            String result = new CostCalculator(new CarService()).defineCost(data.get("distance"), data.get("duration"), data.get("capacity"), data.get("carId"));
            if (!result.equals(data.get("cost"))){
                data.put("cost",result);
                map.put("costError",messageManager.getProperty("message.wrongorder"));
            }
        }
        if (StringUtils.isEmpty(data.get("source"))){
            map.put("sourceError",messageManager.getProperty("label.sourceerror"));
        }
        if (StringUtils.isEmpty(data.get("destination"))){
            map.put("destError",messageManager.getProperty("label.desterror"));
        }
        return map;
    }

    private boolean checkCapacity(String capacity) {
        boolean result = false;
        if (StringUtils.isNotEmpty(capacity)) {
            Set<String> names = new HashSet<>();
            names.add(Capacity.CAR.name());
            names.add(Capacity.MINIBUS.name());
            names.add(Capacity.MINIVAN.name());
            result = names.contains(capacity);
        }
        return result;
    }

    private boolean checkBrand(String brand){
        boolean result = false;
        Pattern patternDigit = Pattern.compile(DIGIT);
        if (StringUtils.isNotEmpty(brand)) {
            String[] entity = brand.split(SPACE);
            Matcher digitMatcher = patternDigit.matcher(entity[0]);
            if (digitMatcher.matches() && entity.length == 2) {
                CarBrand carBrand = new CarService().findBrandById(Integer.parseInt(entity[0]));
                if (carBrand.getName().equals(entity[1])) {
                    result = true;
                }
            }
        }
        return result;
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
