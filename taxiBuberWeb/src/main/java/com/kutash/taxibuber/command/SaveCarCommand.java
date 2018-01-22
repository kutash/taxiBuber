package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.CarBrand;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.util.FileManager;
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
import java.util.List;
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
        Router router = new Router();
        HttpSession session = request.getSession();
        Car car;
        String number = request.getParameter(NUMBER);
        String model = request.getParameter(MODEL);
        String brand = request.getParameter(BRAND);
        String capacity = request.getParameter(CAPACITY);
        String carId = request.getParameter(CAR_ID);
        String language = (String) session.getAttribute(LANGUAGE);
        User user = (User) session.getAttribute(CURRENT_USER);
        Map<String,String> carData = new HashMap<>();
        carData.put("number",number);
        carData.put("model",model);
        carData.put("brand",brand);
        carData.put("capacity",capacity);
        Map<String,String> errors;
        if (StringUtils.isNotEmpty(carId)){
            carData.put("carId",carId);
            errors = carService.validateCar(carData,language);
            int id = Integer.parseInt(carId);
            Car carOld = carService.findById(id);
            if(carOld != null) {
                if (!errors.isEmpty()) {
                    System.out.println("1");
                    carOld.setModel(model);
                    carOld.setRegistrationNumber(number);
                    List<CarBrand> brands = carService.findAllBrands();
                    request.setAttribute("brands",brands);
                    request.setAttribute("errors",errors);
                    request.setAttribute("car",carOld);
                    request.setAttribute("isCar", true);
                    router.setPage(PageManager.getProperty("path.page.driver"));
                } else {
                    System.out.println("2");
                    String[] entityBrand = brand.split("\\s");
                    CarBrand carBrand = new CarBrand(Integer.parseInt(entityBrand[0]), entityBrand[1]);
                    carOld.setBrand(carBrand);
                    carOld.setCapacity(Capacity.valueOf(capacity));
                    carOld.setRegistrationNumber(number);
                    carOld.setModel(model);
                    String photoPath = savePhoto(user.getId(),request);
                    if (StringUtils.isNotEmpty(photoPath)) {
                        carOld.setPhotoPath(photoPath);
                    }
                    car = carService.updateCar(carOld);
                    request.getSession().setAttribute("updateMessage",new MessageManager(language).getProperty("message.updatedcar"));
                    router.setPage("/controller?command=main");
                    router.setRoute(Router.RouteType.REDIRECT);
                }
            }else {
                request.setAttribute("wrongAction",new MessageManager(language).getProperty("message.wrongid"));
                router.setPage("path.page.error");
            }
        }else {
            errors = carService.validateCar(carData,language);
            if (!errors.isEmpty()) {
                System.out.println("3");
                car = new Car(number,model);
                List<CarBrand> brands = carService.findAllBrands();
                request.setAttribute("brands",brands);
                request.setAttribute("errors",errors);
                request.setAttribute("car", car);
                request.setAttribute("isCar", true);
                router.setPage(PageManager.getProperty("path.page.driver"));
            } else {
                System.out.println("4");
                String photoPath = savePhoto(user.getId(),request);
                String[] entityBrand = brand.split("\\s");
                CarBrand carBrand = new CarBrand(Integer.parseInt(entityBrand[0]), entityBrand[1]);
                car = new Car(number,Capacity.valueOf(capacity),model,photoPath,true,carBrand,user.getId());
                carService.createCar(car);
                request.getSession().setAttribute("createMessage",new MessageManager(language).getProperty("message.createdcar"));
                router.setPage("/controller?command=main");
                router.setRoute(Router.RouteType.REDIRECT);
            }
        }
        return router;
    }

    private String savePhoto(int id,HttpServletRequest request) {
        LOGGER.log(Level.INFO,"saving photo");
        Part photoPart = null;
        try {
            photoPart = request.getPart("photo");
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.ERROR,"Exception while getting part",e);
        }
        return new FileManager().savePhoto(photoPart,id,true);
    }
}
