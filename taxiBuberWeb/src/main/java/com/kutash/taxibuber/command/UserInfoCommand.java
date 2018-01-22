package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserInfoCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID = "userId";
    private UserService service;

    UserInfoCommand(UserService orderService) {
        this.service=orderService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        LOGGER.log(Level.INFO,"getting user info");
        String userId = request.getParameter(USER_ID);
        int id = Integer.parseInt(userId);
        User user = service.findById(id);
        List<Comment> comments = service.findComments(id);
        user.setComments(comments);
        String json = new Gson().toJson(user);
        router.setPage(json);
        return router;
    }
}
