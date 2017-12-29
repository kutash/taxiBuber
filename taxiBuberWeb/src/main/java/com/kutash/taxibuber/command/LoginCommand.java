package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.LoginService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private LoginService service;

    LoginCommand(LoginService service){this.service=service;}

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"login command");
        Router router = new Router();
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        User user = null;
        UserRole userRole;
        try {
            user = service.logIn(password,email);
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception in logIn method {}",e);
        }
        if (user == null){
            userRole = UserRole.UNKNOWN;
        }else {
            userRole = user.getRole();
        }
        switch (userRole){
            case ADMIN:
                request.getSession().setAttribute("currentUser",user);
                router.setPage("/controller?command=show_users");
                break;
            case UNKNOWN:
                String language = (String) request.getSession().getAttribute("language");
                request.setAttribute("errorLoginPassMessage", new MessageManager(language).getProperty("message.loginerror"));
                router.setPage(PageManager.getProperty("path.page.login"));
                break;
            case CLIENT:
                request.getSession().setAttribute("currentUser",user);
                router.setPage(PageManager.getProperty("path.page.welcome"));
                break;
        }
        return router;
    }
}
