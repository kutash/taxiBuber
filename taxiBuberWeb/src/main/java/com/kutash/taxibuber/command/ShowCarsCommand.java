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
import java.util.List;

public class ShowCarsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private CarService carService;

    ShowCarsCommand(CarService carService) {
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"show cars command");
        Router router = new Router();
        List<Car> carList = carService.findAll();
        request.setAttribute("cars",carList);
        router.setPage(PageManager.getProperty("path.page.cars"));
        return router;
    }
}
