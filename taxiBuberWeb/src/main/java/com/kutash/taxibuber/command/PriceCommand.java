package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PriceCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DISTANCE = "distance";
    private static final String CAPACITY = "capacity";
    private static final String DURATION = "duration";
    private static final String CAR_ID = "carId";
    private static final String LANGUAGE = "language";
    private CarService service;

    PriceCommand(CarService service){
        this.service=service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        LOGGER.log(Level.INFO,"getting cost");
        String distance = request.getParameter(DISTANCE);
        String duration = request.getParameter(DURATION);
        String carId = request.getParameter(CAR_ID);
        String capacity = request.getParameter(CAPACITY);
        Capacity carCapacity = null;
        if (StringUtils.isNotEmpty(carId)){
            Car car = service.findById(Integer.parseInt(carId));
            carCapacity = car.getCapacity();
        } else if (StringUtils.isNotEmpty(capacity)){
            try {
                carCapacity = Capacity.valueOf(capacity);
            }catch (IllegalArgumentException e){
                LOGGER.log(Level.ERROR,"Wrong type of argument capacity",e);
                carCapacity = Capacity.CAR;
            }
        }else {
            carCapacity = Capacity.CAR;
        }
        String result;
        if (StringUtils.isNotEmpty(distance) && StringUtils.isNotEmpty(duration)){
            result = service.defineCost(distance,duration,carCapacity);
        }else {
            String language = (String) request.getSession().getAttribute(LANGUAGE);
            result = new MessageManager(language).getProperty("message.wrongdata");
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }


}
