package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PriceCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DISTANCE = "distance";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        LOGGER.log(Level.INFO,"getting cost");
        String distance = request.getParameter(DISTANCE);
        double cost = 22.50;
        String json = new Gson().toJson(cost);
        router.setPage(json);
        return router;
    }
}
