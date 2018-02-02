package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import com.kutash.taxibuber.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String BIRTHDAY = "birthday";
    private static final String PHONE = "phone";
    private static final String LANGUAGE = "language";
    private static final String USER_ID = "userId";
    private UserService userService;

    UpdateUserCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"saving user");
        HttpSession session = request.getSession();
        session.removeAttribute("deletedMessage");
        session.removeAttribute("createMessage");
        session.removeAttribute("updateMessage");
        session.removeAttribute("updatePassword");
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        Router router = new Router();
        String userId = request.getParameter(USER_ID);
        User user = userService.findUser(userId);
        Map<String, String> userData = getData(request);
        Map<String, String> errors = new Validator().validateUser(userData, language);
        if (!errors.isEmpty()) {
            user.setName(userData.get("name"));
            user.setSurname(userData.get("surname"));
            user.setPatronymic(userData.get("patronymic"));
            user.setPhone(userData.get("phone"));
            request.setAttribute("user", user);
            request.setAttribute("birthday", userData.get("birthday"));
            request.setAttribute("user", user);
            request.setAttribute("errors", errors);
            router.setPage(PageManager.getProperty("path.page.user"));
        } else {
            Part photoPart = null;
            try {
                photoPart = request.getPart("photo");
            } catch (IOException | ServletException e) {
                LOGGER.log(Level.ERROR,"Exception while getting part",e);
            }
            user = userService.updateUser(user,userData,photoPart);
            MessageManager messageManager = new MessageManager(language);
            request.getSession().setAttribute("updatedUser",messageManager.getProperty("message.userupdated"));
            router.setRoute(Router.RouteType.REDIRECT);
            router.setPage(PageManager.getProperty("path.command.edit")+user.getId());
        }

        /*StringBuffer switchLanguage = request.getRequestURL();
        switchLanguage.append("?");
        Map<String, String[]> parameters = request.getParameterMap();
        for (Map.Entry entry: parameters.entrySet()) {
            String key = (String) entry.getKey();
            if (!key.equals("language")) {
                String[] value = (String[]) entry.getValue();
                switchLanguage.append(key);
                switchLanguage.append("=");
                switchLanguage.append(value[0]);
                switchLanguage.append("&");
            }
        }
        switchLanguage.delete(switchLanguage.length()-1,switchLanguage.length());
        request.getSession().setAttribute("switchLanguage2",switchLanguage);*/
        return router;
    }

    private HashMap<String,String> getData(HttpServletRequest request){
        HashMap<String,String> userData = new HashMap<>();
        userData.put("name",request.getParameter(NAME));
        userData.put("surname",request.getParameter(SURNAME));
        userData.put("patronymic",request.getParameter(PATRONYMIC));
        userData.put("birthday",request.getParameter(BIRTHDAY));
        userData.put("phone",request.getParameter(PHONE));
        return userData;
    }
}
