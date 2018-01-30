package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUsersCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private UserService service;

    ShowUsersCommand(UserService service){this.service=service;}

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"show users command");
        Router router = new Router();
        List<User> userList = service.findAll();
        request.setAttribute("users",userList);
        router.setPage(PageManager.getProperty("path.page.users"));
        return router;
    }
}
