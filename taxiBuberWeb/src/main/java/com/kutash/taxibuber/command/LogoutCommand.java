package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.LoginService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CURRENT_USER = "currentUser";
    private LoginService loginService;

    LogoutCommand(LoginService loginService){
        this.loginService = loginService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Log out");
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        if (user.getRole().equals(UserRole.DRIVER)) {
            loginService.logOut(user.getId());
        }
        router.setPage(PageManager.getProperty("path.page.index"));
        request.getSession().invalidate();
        return router;
    }
}
