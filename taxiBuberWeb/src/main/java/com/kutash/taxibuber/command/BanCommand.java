package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.LoginService;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BanCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID = "userId";
    private UserService userService;
    private CarService carService;

    BanCommand(UserService userService, CarService carService){
        this.userService = userService;
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"ban user");
        Router router = new Router();
        int userId = Integer.parseInt(request.getParameter(USER_ID));
        User user = userService.findById(userId);
        if (user.getStatus().equals(Status.ACTIVE)) {
            user.setStatus(Status.BANNED);
        }else {
            user.setStatus(Status.ACTIVE);
        }
        userService.updateUser(user);
        if (user.getRole().equals(UserRole.DRIVER)) {
            Car car = carService.findByUserId(userId);
            if (car.getStatus().equals(Status.ACTIVE)){
                car.setStatus(Status.BANNED);
            }else {
                car.setStatus(Status.ACTIVE);
            }
            carService.updateCar(car);
        }
        router.setPage("controller?command=show_users");
        return router;
    }
}
