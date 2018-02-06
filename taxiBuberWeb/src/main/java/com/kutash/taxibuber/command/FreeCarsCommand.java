package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FreeCarsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String BODY_TYPE = "bodyType";
    private static final String CURRENT_USER = "currentUser";
    private CarService service;

    FreeCarsCommand(CarService carService) {
        this.service = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"getting available cars");
        Router router = new Router();
        String latitude = request.getParameter(LATITUDE);
        String longitude = request.getParameter(LONGITUDE);
        String bodyType = request.getParameter(BODY_TYPE);
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        List<Car> cars = service.findAllAvailable(latitude,longitude,bodyType);
        String json = new Gson().toJson(cars);
        router.setPage(json);
        return router;
    }
}
