package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Ban command.
 */
public class BanCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID = "userId";
    private UserService userService;

    /**
     * Instantiates a new Ban command.
     *
     * @param userService the user service
     */
    BanCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"ban user");
        Router router = new Router();
        String result = userService.banUser(request.getParameter(USER_ID));
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
