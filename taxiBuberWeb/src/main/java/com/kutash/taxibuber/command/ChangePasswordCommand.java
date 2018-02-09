package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ChangePasswordCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String OLD_PASSWORD = "oldPassword";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRM = "repeat";
    private static final String CURRENT_USER = "currentUser";
    private static final String LANGUAGE = "language";
    private UserService userService;

    ChangePasswordCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"change password");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute("deletedMessage");
        session.removeAttribute("createMessage");
        session.removeAttribute("updateMessage");
        session.removeAttribute("updatedUser");
        User user = (User) session.getAttribute(CURRENT_USER);
        String language = (String) session.getAttribute(LANGUAGE);
        byte[] oldPassword = request.getParameter(OLD_PASSWORD).getBytes();
        byte[] password = request.getParameter(PASSWORD).getBytes();
        byte[] passwordConfirm = request.getParameter(PASSWORD_CONFIRM).getBytes();
        Map<String,String> errors = userService.changePassword(user,oldPassword,password,passwordConfirm,language);
        if (errors.isEmpty()){
            session.setAttribute("updatePassword",new MessageManager(language).getProperty("message.pswupdated"));
            router.setRoute(Router.RouteType.REDIRECT);
        }else {
            request.setAttribute("errors",errors);
            request.setAttribute("isPassword",true);
        }
        router.setPage(PageManager.getProperty("path.command.edit")+user.getId());
        return router;
    }
}
