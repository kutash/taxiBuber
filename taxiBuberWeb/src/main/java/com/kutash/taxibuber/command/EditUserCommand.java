package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.AddressService;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Edit user command.
 */
public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID = "userId";
    private static final String IS_CAR = "isCarParam";
    private static final String SWITCH_LANGUAGE = "switchLanguage";
    private UserService service;
    private CarService carService;
    private AddressService addressService;

    /**
     * Instantiates a new Edit user command.
     *
     * @param service        the service
     * @param carService     the car service
     * @param addressService the address service
     */
    EditUserCommand(UserService service, CarService carService,AddressService addressService){
        this.carService=carService;
        this.service=service;
        this.addressService=addressService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"edit user command");
        Router router = new Router();
        HttpSession session = request.getSession();
        String switchLanguage = request.getParameter(SWITCH_LANGUAGE);
        if (StringUtils.isNotEmpty(switchLanguage) && Boolean.parseBoolean(switchLanguage)){
            session.removeAttribute("deletedMessage");
            session.removeAttribute("createMessage");
            session.removeAttribute("updateMessage");
            session.removeAttribute("updatedUser");
            session.removeAttribute("updatePassword");
        }
        String userId = request.getParameter(USER_ID);
        User user = service.findUser(userId);
        Boolean isCar = (Boolean) request.getAttribute("isCar");
        String isCarParam = request.getParameter(IS_CAR);
        if (user.getRole().equals(UserRole.DRIVER)){
            if (isCar == null || !isCar) {
                Car car = carService.findByUserId(user.getId());
                request.setAttribute("car", car);
            }
            if (StringUtils.isNotEmpty(isCarParam) && Boolean.valueOf(isCarParam)){
                request.getSession().setAttribute("isCar",true);
            }
            List<CarBrand> brands = carService.findAllBrands();
            request.setAttribute("brands",brands);
        }
        if (user.getRole().equals(UserRole.CLIENT)){
            List<Address> addresses = addressService.findAddresses(user.getId());
            request.setAttribute("addresses",addresses);
        }
        request.setAttribute("user",user);
        router.setPage(PageManager.getProperty("path.page.user"));
        return router;
    }
}
