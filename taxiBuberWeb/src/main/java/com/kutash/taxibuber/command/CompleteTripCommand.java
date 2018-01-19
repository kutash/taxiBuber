package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.TripStatus;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteTripCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TRIP_ID = "tripId";
    private TripService tripService;
    private CarService carService;

    CompleteTripCommand(TripService tripService,CarService carService) {

        this.tripService = tripService;
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Completing trip");
        Router router = new Router();
        Trip trip = tripService.findTripById(Integer.parseInt(request.getParameter(TRIP_ID)));
        trip.setStatus(TripStatus.COMPLETED);
        tripService.updateTrip(trip);
        Car car = carService.findById(trip.getIdCar());
        car.setAvailable(true);
        carService.updateCar(car);
        router.setPage(PageManager.getProperty("path.page.driver"));
        return router;
    }
}
