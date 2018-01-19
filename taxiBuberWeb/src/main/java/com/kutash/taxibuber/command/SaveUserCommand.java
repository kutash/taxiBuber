package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "duration";
    private static final String ROLE = "carId";
    private static final String EMAIL = "language";
    private static final String PASSWORD = "cost";
    private static final String PASSWORD_CONFIRM = "start";
    private static final String BIRTHDAY = "end";
    private static final String PHONE = "durationText";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String patronymic = request.getParameter(PATRONYMIC);
        UserRole role = UserRole.valueOf(request.getParameter(ROLE));
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String passwordConfirm = request.getParameter(PASSWORD_CONFIRM);
        String birthday = request.getParameter(BIRTHDAY);
        String phone = request.getParameter(PHONE);
        //User user = new User(0.0,name,surname,);
        return router;
    }
}
