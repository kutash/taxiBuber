package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveCarCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NUMBER = "number";
    private static final String MODEL = "model";
    private static final String BRAND = "brand";
    private static final String CAR_ID = "carId";
    private static final String CAPACITY = "capacity";
    private static final String LANGUAGE = "language";
    private static final String CURRENT_USER = "currentUser";

    private CarService carService;

    SaveCarCommand(CarService carService){
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"saving car");
        Router router;
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);
        session.removeAttribute("deletedMessage");
        session.removeAttribute("createMessage");
        session.removeAttribute("updateMessage");
        session.removeAttribute("updatedUser");
        session.removeAttribute("updatePassword");
        HashMap<String,String> carData = getData(request);
        Map<String,String> errors = new Validator().validateCar(carData,language);
        if (carData.containsKey("carId")){
            router = updateCar(request,carData,errors,language);
        }else {
            router = createCar(request,carData,errors,language);
        }
        return router;
    }

    private HashMap<String,String> getData(HttpServletRequest request){
        HashMap<String,String> carData = new HashMap<>();
        String number = request.getParameter(NUMBER);
        String model = request.getParameter(MODEL);
        String brand = request.getParameter(BRAND);
        String capacity = request.getParameter(CAPACITY);
        String carId = request.getParameter(CAR_ID);
        if (StringUtils.isNotEmpty(carId) && Integer.parseInt(carId) != 0){
            carData.put("carId",carId);
        }
        carData.put("number",number);
        carData.put("model",model);
        carData.put("brand",brand);
        carData.put("capacity",capacity);
        return carData;
    }

    private Router createCar(HttpServletRequest request,Map<String,String> carData,Map<String,String> errors,String language) {
        Car car;
        Router router = new Router();
        User driver = (User) request.getSession().getAttribute(CURRENT_USER);
        if (!errors.isEmpty()) {
            car = new Car(carData.get("number"),carData.get("model"));
            request.setAttribute("errors",errors);
            request.setAttribute("car", car);
            request.setAttribute("isCar", true);
        } else {
            Part photoPart = null;
            try {
                photoPart = request.getPart("photo");
            } catch (IOException | ServletException e) {
                LOGGER.catching(Level.ERROR,e);
            }
            carService.createCar(carData,photoPart,driver);
            request.getSession().setAttribute("createMessage",new MessageManager(language).getProperty("message.createdcar"));
            request.getSession().setAttribute("isCar", false);
            router.setRoute(Router.RouteType.REDIRECT);
        }
        router.setPage(PageManager.getProperty("path.command.edit")+driver.getId());
        return router;
    }

    private Router updateCar(HttpServletRequest request,Map<String,String> carData,Map<String,String> errors,String language){
        Router router = new Router();
        User driver = (User) request.getSession().getAttribute(CURRENT_USER);
        int id = Integer.parseInt(carData.get("carId"));
        Car carOld = carService.findById(id);
        if (!errors.isEmpty()) {
            carOld.setModel(carData.get("model"));
            carOld.setRegistrationNumber(carData.get("number"));
            request.setAttribute("errors",errors);
            request.setAttribute("car",carOld);
            request.setAttribute("isCar", true);
        } else {
            Part photoPart = null;
            try {
                photoPart = request.getPart("photo");
            } catch (IOException | ServletException e) {
                LOGGER.catching(Level.ERROR,e);
            }
            carService.updateCar(carData,photoPart,carOld);
            request.getSession().setAttribute("updateMessage",new MessageManager(language).getProperty("message.updatedcar"));
            request.getSession().setAttribute("isCar", false);
            router.setRoute(Router.RouteType.REDIRECT);
            }
            router.setPage(PageManager.getProperty("path.command.edit")+driver.getId());
        return router;
    }

}
