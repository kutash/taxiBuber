package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import com.kutash.taxibuber.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;

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
        request.getSession().removeAttribute("sentPassword");
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        Router router = new Router();
        User user;
        HashMap<String,String> userData = getData(request);
        HashMap<String,String> errors = new Validator().validateUser(userData,language);
        if (!errors.isEmpty()){
            user = new User(userData.get("name"),userData.get("surname"),userData.get("patronymic"),userData.get("email"),userData.get("password").getBytes(),userData.get("phone"));
            request.setAttribute("birthday",userData.get("birthday"));
            request.setAttribute("user", user);
            request.setAttribute("errors", errors);
            request.setAttribute("isErrors",true);
            router.setPage(PageManager.getProperty("path.page.login"));
        }else {
            Part photoPart = null;
            try {
                photoPart = request.getPart("photo");
            } catch (IOException | ServletException e) {
                LOGGER.log(Level.ERROR,"Exception while getting part",e);
            }
            user = userService.saveUser(userData,photoPart);
            request.getSession().setAttribute("currentUser",user);
            request.getSession().setAttribute("isCar",true);
            router.setRoute(Router.RouteType.REDIRECT);
            router.setPage(PageManager.getProperty("path.command.edit")+user.getId());
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
        String password  = new String(request.getParameter(PASSWORD));
        String passwordConfirm  = new String(request.getParameter(PASSWORD_CONFIRM));
        userData.put("password", password);
        userData.put("passwordConfirm",passwordConfirm);
        userData.put("birthday",request.getParameter(BIRTHDAY));
        userData.put("phone",request.getParameter(PHONE));
        return userData;
    }
}
