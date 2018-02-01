package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

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
        Reader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
        JsonObject data = new Gson().fromJson(reader, JsonObject.class);
        String carId = data.get(CAR_ID).getAsString();
        String latitude = data.get(LATITUDE).getAsString();
        String longitude = data.get(LONGITUDE).getAsString();
        String result = carService.setCoordinates(carId,latitude,longitude);
        String json = new Gson().toJson(result);
        router.setPage(json);
        return router;
    }
}
