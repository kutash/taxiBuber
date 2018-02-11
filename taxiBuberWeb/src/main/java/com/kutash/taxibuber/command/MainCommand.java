package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.AddressService;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The type Main command.
 */
public class MainCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private AddressService service;
    private CarService carService;
    private TripService tripService;

    /**
     * Instantiates a new Main command.
     *
     * @param addressService the address service
     * @param carService     the car service
     * @param tripService    the trip service
     */
    MainCommand(AddressService addressService,CarService carService,TripService tripService) {
        this.service = addressService;
        this.carService=carService;
        this.tripService=tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"main command");
        Router router = new Router();
        UserRole role;
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user == null){
            router.setPage(PageManager.getProperty("path.page.login"));
        }else {
            role = user.getRole();
            switch (role) {
                case CLIENT:
                    List<Address> addresses = service.findAddresses(user.getId());
                    request.setAttribute("addresses", addresses);
                    router.setPage(PageManager.getProperty("path.page.welcome"));
                    break;
                case DRIVER:
                    Car car = carService.findByUserId(user.getId());
                    if(car != null) {
                        Trip trip = tripService.findStarted(car.getId());
                        request.setAttribute("trip", trip);
                    }
                    request.setAttribute("car", car);
                    router.setPage(PageManager.getProperty("path.page.driver"));
                    break;
            }
        }
        return router;
    }
}
