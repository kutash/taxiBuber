package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID = "userId";
    private static final String LANGUAGE = "language";
    private UserService userService;
    private CarService carService;

    DeleteCommand(UserService userService, CarService carService){
        this.userService = userService;
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"deleting user");
        Router router = new Router();
        int userId = Integer.parseInt(request.getParameter(USER_ID));
        User user = userService.findById(userId);
        user.setStatus(Status.ARCHIVED);
        userService.updateUser(user);
        if (user.getRole().equals(UserRole.DRIVER)) {
            Car car = carService.findByUserId(userId);
            if (car != null) {
                car.setStatus(Status.ARCHIVED);
                carService.updateCar(car);
            }
        }
        String json = new Gson().toJson("ok");
        router.setPage(json);
        return router;
    }
}
