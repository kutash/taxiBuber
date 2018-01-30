package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.AddressService;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewOrderCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private TripService tripService;
    private AddressService addressService;

    NewOrderCommand(TripService tripService, AddressService addressService) {

        this.tripService = tripService;
        this.addressService = addressService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Finding new orders");
        Router router = new Router();
        String json = "Error";
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user != null) {
            Trip trip = tripService.findOrdered(user.getId());
            if (trip != null) {
                Address departureAddress = addressService.findAddressById(trip.getDepartureAddress());
                Address destinationAddress = addressService.findAddressById(trip.getDestinationAddress());
                trip.setDeparture(departureAddress);
                trip.setDestination(destinationAddress);
                json = new Gson().toJson(trip);
            }
        }
        router.setPage(json);
        return router;
    }
}
