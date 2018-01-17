package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.OrderService;
import com.kutash.taxibuber.util.CostCalculator;
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
        String result;
        if (StringUtils.isNotEmpty(distance) && StringUtils.isNotEmpty(duration)){
            result = new CostCalculator(service).defineCost(distance,duration,capacity,carId);
        }else {
            String language = (String) request.getSession().getAttribute(LANGUAGE);
            result = new MessageManager(language).getProperty("message.wrongdata");
        }
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }


}
