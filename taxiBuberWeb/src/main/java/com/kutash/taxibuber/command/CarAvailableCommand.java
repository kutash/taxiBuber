package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class CarAvailableCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String IS_AVAILABLE = "isAvailable";
    private static final String CAR_ID = "carId";
    private CarService service;

    CarAvailableCommand(CarService service){
        this.service=service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"set available");
        Router router = new Router();
        String result;
        Reader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
        JsonObject data = new Gson().fromJson(reader, JsonObject.class);
        String carId = data.get(CAR_ID).getAsString();
        String available = data.get(IS_AVAILABLE).getAsString();
        result = service.setAvailable(carId,available);
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
