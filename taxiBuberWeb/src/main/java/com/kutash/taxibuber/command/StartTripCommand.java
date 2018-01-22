package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.TripStatus;
import com.kutash.taxibuber.service.TripService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartTripCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TRIP_ID = "tripId";
    private TripService service;

    StartTripCommand(TripService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Starting new trip");
        Router router = new Router();
        String result = "ERROR";
        String tripId = request.getParameter(TRIP_ID);
        if (StringUtils.isNotEmpty(tripId)) {
            Trip trip = service.findTripById(Integer.parseInt(tripId));
            trip.setStatus(TripStatus.STARTED);
            trip = service.updateTrip(trip);
            if (trip.getStatus().equals(TripStatus.STARTED)){
                result = "OK";
            }
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
