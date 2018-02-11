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
import javax.servlet.http.HttpSession;

/**
 * The type Logout command.
 */
public class LogoutCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CURRENT_USER = "currentUser";
    private LoginService loginService;

    /**
     * Instantiates a new Logout command.
     *
     * @param loginService the login service
     */
    LogoutCommand(LoginService loginService){
        this.loginService = loginService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Log out");
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CURRENT_USER);
        if(user != null) {
            if (user.getRole().equals(UserRole.DRIVER)) {
                loginService.logOut(user.getId());
            }
        }
        session.removeAttribute("currentUser");
        session.invalidate();
        router.setPage(PageManager.getProperty("path.page.index"));
        return router;
    }
}
