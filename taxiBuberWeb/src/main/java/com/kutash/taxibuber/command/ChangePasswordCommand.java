package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.UserService;
import com.kutash.taxibuber.util.PasswordEncryptor;
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
        User user = (User) session.getAttribute(CURRENT_USER);
        String language = (String) session.getAttribute(LANGUAGE);
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String password = request.getParameter(PASSWORD);
        String passwordConfirm = request.getParameter(PASSWORD_CONFIRM);
        Map<String,String> errors = userService.checkPassword(user,oldPassword,password,passwordConfirm,language);
        if (errors.isEmpty()){
            user.setPassword(new PasswordEncryptor(user.getEmail()).encrypt(password));
            user = userService.updateUser(user);
            session.setAttribute("currentUser",user);
            session.setAttribute("updatePassword",new MessageManager(language).getProperty("message.pswupdated"));
            router.setRoute(Router.RouteType.REDIRECT);
        }else {
            request.setAttribute("errors",errors);
            request.setAttribute("isPassword",true);
        }
        router.setPage("controller?command=edit&userId="+user.getId());
        return router;
    }
}