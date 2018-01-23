package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import com.kutash.taxibuber.util.DateParser;
import com.kutash.taxibuber.util.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        Router router = new Router();
        User user;
        Map<String,String> userData = getData(request);
        Map<String,String> errors = userService.validateUser(userData,language);
        if (!errors.isEmpty()){
            user = new User(userData.get("name"),userData.get("surname"),userData.get("patronymic"),userData.get("email"),userData.get("password"),userData.get("phone"));
            request.setAttribute("birthday",userData.get("birthday"));
            request.setAttribute("user", user);
            request.setAttribute("errors", errors);
            request.setAttribute("isErrors",true);
            router.setPage(PageManager.getProperty("path.page.login"));
        }else {
            user = new User(userData.get("name"),userData.get("surname"),userData.get("patronymic"),userData.get("email"),userData.get("password"),UserRole.valueOf(userData.get("role")), DateParser.parseDate(userData.get("birthday")),userData.get("phone"));
            int id = userService.create(user);
            user.setId(id);
            String photoPath = savePhoto(id,request);
            user.setPhotoPath(photoPath);
            user = userService.updateUser(user);
            request.getSession().setAttribute("currentUser",user);
            request.getSession().setAttribute("isCar",true);
            router.setRoute(Router.RouteType.REDIRECT);
            router.setPage("/controller?command=main");

        }
        return router;
    }

    private HashMap<String,String> getData(HttpServletRequest request){
        HashMap<String,String> userData = new HashMap<>();
        userData.put("name",request.getParameter(NAME));
        userData.put("surname",request.getParameter(SURNAME));
        userData.put("patronymic",request.getParameter(PATRONYMIC));
        userData.put("role",request.getParameter(ROLE));
        userData.put("email",request.getParameter(EMAIL));
        userData.put("password",request.getParameter(PASSWORD));
        userData.put("passwordConfirm",request.getParameter(PASSWORD_CONFIRM));
        userData.put("birthday",request.getParameter(BIRTHDAY));
        userData.put("phone",request.getParameter(PHONE));
        return userData;
    }

    private String savePhoto(int id,HttpServletRequest request) {
        LOGGER.log(Level.INFO,"saving photo for the user");
        Part photoPart = null;
        try {
            photoPart = request.getPart("photo");
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.ERROR,"Exception while getting part",e);
        }
        return new FileManager().savePhoto(photoPart,id,false);
    }

}
