package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Forgot password command.
 */
public class ForgotPasswordCommand implements Command {

    private static final String EMAIL = "emailForgot";
    private static final String LANGUAGE = "language";
    private LoginService loginService;

    /**
     * Instantiates a new Forgot password command.
     *
     * @param loginService the login service
     */
    ForgotPasswordCommand(LoginService loginService){
        this.loginService=loginService;
    }
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        String email = request.getParameter(EMAIL);
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        User user = loginService.sendPassword(email,language);
        if (user != null){
            request.getSession().setAttribute("sentPassword",new MessageManager(language).getProperty("message.sentpasw"));
            router.setRoute(Router.RouteType.REDIRECT);
        }else {
            request.setAttribute("wrongEmail", new MessageManager(language).getProperty("message.wrongemail"));
        }
        router.setPage(PageManager.getProperty("path.page.index"));

        return router;
    }
}
