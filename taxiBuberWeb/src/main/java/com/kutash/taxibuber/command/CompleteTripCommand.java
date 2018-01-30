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
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
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
        String latitude = request.getParameter(LATITUDE);
        String longitude = request.getParameter(LONGITUDE);
        Trip trip = tripService.findTripById(Integer.parseInt(request.getParameter(TRIP_ID)));
        trip.setStatus(TripStatus.COMPLETED);
        tripService.updateTrip(trip);
        Car car = carService.findById(trip.getIdCar());
        car.setAvailable(true);
        car.setLatitude(latitude);
        car.setLongitude(longitude);
        carService.updateCar(car);
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPage(PageManager.getProperty("path.command.main"));
        return router;
    }
}
