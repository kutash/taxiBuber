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

public class CompleteTripCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TRIP_ID = "tripId";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private TripService tripService;

    CompleteTripCommand(TripService tripService) {
        this.tripService = tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Completing trip");
        Router router = new Router();
        String result = "";
        Reader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
        JsonObject data = new Gson().fromJson(reader, JsonObject.class);
        String tripId = data.get(TRIP_ID).getAsString();
        String latitude = data.get(LATITUDE).getAsString();
        String longitude = data.get(LONGITUDE).getAsString();
        Trip trip = tripService.completeTrip(tripId,latitude,longitude);
        if (trip.getStatus().equals(TripStatus.COMPLETED)) {
            result = "OK";
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
