package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewOrderCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private TripService service;

    NewOrderCommand(TripService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        String json;
        User user = (User) request.getSession().getAttribute("currentUser");
        Trip trip = service.findOrdered(user.getId());
        if (trip == null){
            json = new Gson().toJson("no trips");
        }else {
            Address departureAddress = service.findAddressById(trip.getDepartureAddress());
            Address destinationAddress = service.findAddressById(trip.getDestinationAddress());
            trip.setDeparture(departureAddress);
            trip.setDestination(destinationAddress);
            json = new Gson().toJson(trip);
        }
        router.setPage(json);
        return router;
    }
}
