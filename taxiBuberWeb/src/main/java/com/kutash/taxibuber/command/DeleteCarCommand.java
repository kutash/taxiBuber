package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCarCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CAR_ID = "carId";
    private static final String DRIVER_ID = "userId";
    private static final String LANGUAGE = "language";
    private CarService carService;
    private UserService userService;

    DeleteCarCommand(CarService carService, UserService userService) {

        this.carService=carService;
        this.userService=userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Deleting car");
        Router router = new Router();
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        String driverId = request.getParameter(DRIVER_ID);
        User driver = userService.findById(Integer.parseInt(driverId));
        int id = Integer.parseInt(request.getParameter(CAR_ID));
        Car car = carService.findById(id);
        car.setStatus(Status.ARCHIVED);
        carService.updateCar(car);
        router.setRoute(Router.RouteType.REDIRECT);
        MessageManager messageManager = new MessageManager(language);
        request.getSession().setAttribute("deletedMessage",messageManager.getProperty("label.car")+" "+messageManager.getProperty("message.deleted"));
        router.setPage(PageManager.getProperty("path.command.edit")+driver.getId());
        return router;
    }
}
