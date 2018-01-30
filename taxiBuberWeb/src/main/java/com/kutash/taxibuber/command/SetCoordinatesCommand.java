package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SetCoordinatesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String CAR_ID = "carId";

    private CarService carService;

    SetCoordinatesCommand(CarService carService){
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"setting coordinates to the car");
        Router router = new Router();
        String result = "ERROR";
        String carId = request.getParameter(CAR_ID);
        if (StringUtils.isNotEmpty(carId)) {
            int id = Integer.parseInt(carId);
            Car car = carService.findById(id);
            String latitude = new BigDecimal(request.getParameter(LATITUDE)).setScale(6, RoundingMode.UP).toString();
            String longitude = new BigDecimal(request.getParameter(LONGITUDE)).setScale(6, RoundingMode.UP).toString();
            if (car != null) {
                car.setLatitude(latitude);
                car.setLongitude(longitude);
                carService.updateCar(car);
                result = "OK";
            }
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
