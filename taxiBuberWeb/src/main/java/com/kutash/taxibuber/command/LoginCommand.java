package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.LoginService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * The type Login command.
 */
public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LANGUAGE = "language";
    private LoginService service;

    /**
     * Instantiates a new Login command.
     *
     * @param service the service
     */
    LoginCommand(LoginService service){

        this.service=service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"login command");
        Router router = new Router();
        request.getSession().removeAttribute("sentPassword");
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        String email = request.getParameter(EMAIL);
        byte[] password = request.getParameter(PASSWORD).getBytes();
        User user = null;
        UserRole userRole;
        try {
            user = service.logIn(password,email);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (user != null) {
            if (user.getStatus().equals(Status.BANNED)) {
                request.setAttribute("errorLoginPassMessage", new MessageManager(language).getProperty("message.banned"));
                router.setPage(PageManager.getProperty("path.page.login"));
            } else {
                userRole = user.getRole();
                switch (userRole) {
                    case ADMIN:
                        request.getSession().setAttribute("currentUser", user);
                        request.getSession().setMaxInactiveInterval(30*60);
                        router.setPage(PageManager.getProperty("path.command.users"));
                        break;
                    case CLIENT:
                        request.getSession().setAttribute("currentUser", user);
                        request.getSession().setMaxInactiveInterval(10*60);
                        router.setPage(PageManager.getProperty("path.command.main"));
                        break;
                    case DRIVER:
                        request.getSession().setAttribute("currentUser", user);
                        request.getSession().setMaxInactiveInterval(60*60);
                        router.setPage(PageManager.getProperty("path.command.main"));
                        break;
                }
                router.setRoute(Router.RouteType.REDIRECT);
            }
        }else {
            request.setAttribute("errorLoginPassMessage", new MessageManager(language).getProperty("message.login"));
            router.setPage(PageManager.getProperty("path.page.login"));
        }
        return router;
    }
}
