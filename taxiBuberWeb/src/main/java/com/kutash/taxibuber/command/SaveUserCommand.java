package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import com.kutash.taxibuber.util.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SaveUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String ROLE = "role";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRM = "repeat";
    private static final String BIRTHDAY = "birthday";
    private static final String PHONE = "phone";
    private static final String LANGUAGE = "language";
    private UserService userService;

    SaveUserCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"saving user");
        Router router = new Router();
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String patronymic = request.getParameter(PATRONYMIC);
        String role = request.getParameter(ROLE);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String passwordConfirm = request.getParameter(PASSWORD_CONFIRM);
        String birthday = request.getParameter(BIRTHDAY);
        String phone = request.getParameter(PHONE);
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        System.out.println(language);
        User user;
        Map<String,String> userData = new HashMap<>();
        userData.put("name",name);
        userData.put("surname",surname);
        userData.put("patronymic",patronymic);
        userData.put("role",role);
        userData.put("email",email);
        userData.put("password",password);
        userData.put("passwordConfirm",passwordConfirm);
        userData.put("birthday",birthday);
        userData.put("phone",phone);
        Map<String,String> errors = userService.validateUser(userData,language);
        if (errors.size()!= 0){
            user = new User(name,surname,patronymic,email,password,phone);
            request.setAttribute("birthday",birthday);
            request.setAttribute("user", user);
            request.setAttribute("errors", errors);
            request.setAttribute("isErrors","true");
            request.setAttribute("role",role);
            router.setPage(PageManager.getProperty("path.page.login"));
        }else {
            user = new User(name,surname,patronymic,email,password,UserRole.valueOf(role),parseDate(birthday),phone);
            int id = userService.create(user);
            user.setId(id);
            String photoPath = savePhoto(id,request);
            if (photoPath != null) {
                user.setPhotoPath(photoPath);
                user = userService.updateUser(user);
            }
            request.getSession().setAttribute("currentUser",user);
            router.setRoute(Router.RouteType.REDIRECT);
            if (user.getRole().equals(UserRole.CLIENT)){
                router.setPage("/controller?command=order");
            }else if (user.getRole().equals(UserRole.DRIVER)){
                router.setPage(PageManager.getProperty("path.page.car"));
            }
        }
        return router;
    }

    private Date parseDate(String date){
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

    private String savePhoto(int id,HttpServletRequest request) {
        LOGGER.log(Level.INFO,"saving photo");
        Part photoPart = null;
        try {
            photoPart = request.getPart("photo");
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.ERROR,"Exception while getting part",e);
        }
        return new FileManager().savePhoto(photoPart,id);
    }

}
