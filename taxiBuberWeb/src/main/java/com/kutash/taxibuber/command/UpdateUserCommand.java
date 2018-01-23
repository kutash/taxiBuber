package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
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
import java.util.HashMap;
import java.util.List;
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
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        Router router = new Router();
        String userId = request.getParameter(USER_ID);
        int id;
        User user;
        if (StringUtils.isEmpty(userId) || Integer.parseInt(userId)==0 || userService.findById(Integer.parseInt(userId)) == null){
            router.setPage(PageManager.getProperty("path.page.error"));
        }else {
            id = Integer.parseInt(userId);
            user = userService.findById(id);
            Map<String, String> userData = getData(request);
            Map<String, String> errors = userService.validateUser(userData, language);
            if (!errors.isEmpty()) {
                user.setName(userData.get("name"));
                user.setSurname(userData.get("surname"));
                user.setPatronymic(userData.get("patronymic"));
                user.setPhone(userData.get("phone"));
                List<Comment> comments = userService.findComments(id);
                request.setAttribute("comments", comments);
                request.setAttribute("user", user);
                request.setAttribute("birthday", userData.get("birthday"));
                request.setAttribute("user", user);
                request.setAttribute("errors", errors);
                router.setPage(PageManager.getProperty("path.page.user"));
            } else {
                String photoPath = savePhoto(id, request);
                user.setName(userData.get("name"));
                user.setSurname(userData.get("surname"));
                user.setPatronymic(userData.get("patronymic"));
                user.setPhone(userData.get("phone"));
                user.setBirthday(DateParser.parseDate(userData.get("birthday")));
                if (StringUtils.isNotEmpty(photoPath)) {
                    user.setPhotoPath(photoPath);
                }
                userService.updateUser(user);
                router.setRoute(Router.RouteType.REDIRECT);
                router.setPage("/controller?command=main");
            }
        }
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
