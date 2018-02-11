package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.TripStatus;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

/**
 * The type Complete trip command.
 */
public class CompleteTripCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TRIP_ID = "tripId";
    private TripService tripService;

    /**
     * Instantiates a new Complete trip command.
     *
     * @param tripService the trip service
     */
    CompleteTripCommand(TripService tripService) {
        this.tripService = tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Completing trip");
        Router router = new Router();
        String result = "";
        String tripId = request.getParameter(TRIP_ID);
        Trip trip = tripService.completeTrip(tripId);
        if (trip.getStatus().equals(TripStatus.COMPLETED)) {
            result = "OK";
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
