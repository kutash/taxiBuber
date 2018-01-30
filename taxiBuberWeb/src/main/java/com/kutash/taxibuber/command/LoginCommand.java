package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.LoginService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LANGUAGE = "language";
    private LoginService service;
    private CarService carService;

    LoginCommand(LoginService service,CarService carService){

        this.service=service;
        this.carService=carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"login command");
        Router router = new Router();
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        User user;
        UserRole userRole;
        user = service.logIn(password,email);
        if (user != null) {
            if (user.getStatus().equals(Status.BANNED)) {
                request.setAttribute("errorLoginPassMessage", new MessageManager(language).getProperty("message.banned"));
                router.setPage(PageManager.getProperty("path.page.login"));
            } else {
                userRole = user.getRole();
                switch (userRole) {
                    case ADMIN:
                        request.getSession().setAttribute("currentUser", user);
                        router.setPage(PageManager.getProperty("path.command.users"));
                        break;
                    case CLIENT:
                        request.getSession().setAttribute("currentUser", user);
                        router.setPage(PageManager.getProperty("path.command.main"));
                        break;
                    case DRIVER:
                        request.getSession().setAttribute("currentUser", user);
                        router.setPage(PageManager.getProperty("path.command.main"));
                        break;
                }
            }
        }else {
            request.setAttribute("errorLoginPassMessage", new MessageManager(language).getProperty("message.login"));
            router.setPage(PageManager.getProperty("path.page.login"));
        }
        return router;
    }
}
