package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCarCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CAR_ID = "carId";
    private CarService service;

    EditCarCommand(CarService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO, "edit car command");
        Router router = new Router();
        int id = Integer.parseInt(request.getParameter(CAR_ID));
        Car car = service.findById(id);
        request.setAttribute("car", car);
        router.setPage(PageManager.getProperty("path.page.car"));
        return router;
    }
}
