package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.AddressService;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private AddressService service;
    private CarService carService;

    MainCommand(AddressService addressService,CarService carService) {
        this.service = addressService;
        this.carService=carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"main command");
        Router router = new Router();
        UserRole role;
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user == null){
            role = UserRole.UNKNOWN;
        }else {
            role = user.getRole();
        }
        switch (role){
            case CLIENT:
                List<Address> addresses = service.findAddresses(user.getId());
                request.setAttribute("addresses",addresses);
                router.setPage(PageManager.getProperty("path.page.welcome"));
                break;
            case DRIVER:
                Car car = carService.findByUserId(user.getId());
                List<CarBrand> brands = carService.findAllBrands();
                request.getSession().setAttribute("currentUser",user);
                request.setAttribute("car", car);
                request.setAttribute("brands",brands);
                router.setPage(PageManager.getProperty("path.page.driver"));
                break;
            case UNKNOWN:
                router.setPage(PageManager.getProperty("path.page.login"));
                break;
        }
        return router;
    }
}
