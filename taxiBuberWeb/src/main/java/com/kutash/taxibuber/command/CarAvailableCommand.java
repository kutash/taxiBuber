package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarAvailableCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String IS_AVAILABLE = "distance";
    private static final String CAR_ID = "carId";
    private CarService service;

    CarAvailableCommand(CarService service){
        this.service=service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"");
        Router router = new Router();
        String result;
        String available = request.getParameter(IS_AVAILABLE);
        String carId = request.getParameter(CAR_ID);
        if (StringUtils.isEmpty(carId) || Integer.parseInt(carId) == 0 || service.findById(Integer.parseInt(carId)) == null){
            result = "no car";
        }else {
            Car car = service.findById(Integer.parseInt(carId));
            car.setAvailable(Boolean.parseBoolean(available));
            service.updateCar(car);
            result = "ok";
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
