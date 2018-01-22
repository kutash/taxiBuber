package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetCoordinatesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private CarService carService;

    SetCoordinatesCommand(CarService carService){
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        User user = (User) request.getSession().getAttribute("currentUser");
        Car car = carService.findByUserId(user.getId());
        if (car != null) {
            car.setLatitude(request.getParameter(LATITUDE));
            car.setLongitude(request.getParameter(LONGITUDE));
            carService.updateCar(car);
        }
        router.setPage("Ok");
        return router;
    }
}
