package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewOrderCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CAR_ID = "carId";
    private TripService tripService;

    NewOrderCommand(TripService tripService) {
        this.tripService = tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Finding new orders");
        Router router = new Router();
        String json;
        int id = Integer.parseInt(request.getParameter(CAR_ID));
        Trip trip = tripService.findOrdered(id);
        if (trip != null) {
            json = new Gson().toJson(trip);
        }else {
            json = new Gson().toJson("no trips");
        }
        router.setPage(json);
        return router;
    }
}
