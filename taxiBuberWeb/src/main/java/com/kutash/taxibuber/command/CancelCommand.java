package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DRIVER_ID = "userId";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Cancel saving car");
        Router router = new Router();
        int driverId = Integer.parseInt(request.getParameter(DRIVER_ID));
        request.getSession().setAttribute("isCar", false);
        router.setPage("controller?command=edit&userId="+driverId);
        return router;
    }
}
