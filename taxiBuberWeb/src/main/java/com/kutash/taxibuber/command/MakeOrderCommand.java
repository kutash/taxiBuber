package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.TripService;
import com.kutash.taxibuber.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * The type Make order command.
 */
public class MakeOrderCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DISTANCE = "distance";
    private static final String CAPACITY = "capacity";
    private static final String DURATION = "duration";
    private static final String CAR_ID = "carId";
    private static final String LANGUAGE = "language";
    private static final String COST = "cost";
    private static final String SOURCE = "start";
    private static final String DESTINATION = "end";
    private static final String DURATION_TEXT = "durationText";
    private static final String CURRENT_USER = "currentUser";
    private static final int METERS_IN_KILOMETER = 1000;
    private TripService tripService;
    private CarService carService;

    /**
     * Instantiates a new Make order command.
     *
     * @param tripService the trip service
     * @param carService  the car service
     */
    MakeOrderCommand(TripService tripService,CarService carService){
        this.carService=carService;
        this.tripService=tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"making order");
        Router router = new Router();
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        HashMap<String,String> data = getData(request);
        HashMap<String,String> errors = new Validator().validateOrder(data,language);
        if (errors.isEmpty()){
            int tripId = tripService.createTrip(data,user.getId());
            if (tripId != 0) {
                router.setRoute(Router.RouteType.REDIRECT);
            }else {
                errors.put("emptyCar","");
                returnErrors(data,errors,request,language);
                request.setAttribute("orderMessage", new MessageManager(language).getProperty("message.noorder"));
            }
        }else {
            returnErrors(data,errors,request,language);
            request.setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
        }
        router.setPage(PageManager.getProperty("path.command.main"));
        return router;
    }

    private void returnErrors(HashMap<String,String> data,HashMap<String,String> errors,HttpServletRequest request,String language){
        float distanceNumber = 0.0f;
        if (!errors.containsKey("emptyCar")){
            int carId = Integer.parseInt(data.get("carId"));
            Car car = carService.findById(carId);
            request.setAttribute("car", car.getBrand().getName() + " " + car.getModel());
            request.setAttribute("carId", car.getId());
        }if (!errors.containsKey("distance")){
            distanceNumber = Float.parseFloat(data.get("distance")) / METERS_IN_KILOMETER;
            distanceNumber = new BigDecimal(distanceNumber).setScale(2, RoundingMode.UP).floatValue();
        }
        request.setAttribute("errors",errors);
        request.setAttribute("cost", data.get("cost"));
        request.setAttribute("durationText", data.get("durationText"));
        request.setAttribute("duration", data.get("duration"));
        request.setAttribute("distanceNumber", distanceNumber);
        request.setAttribute("distance", data.get("distance"));
        request.setAttribute("source", data.get("source"));
        request.setAttribute("destination", data.get("destination"));
    }

    private HashMap<String,String> getData(HttpServletRequest request){
        HashMap<String,String> data = new HashMap<>();
        String distance = request.getParameter(DISTANCE);
        String duration = request.getParameter(DURATION);
        String capacity = request.getParameter(CAPACITY);
        String carId = request.getParameter(CAR_ID);
        String cost = request.getParameter(COST);
        String source = request.getParameter(SOURCE);
        String destination = request.getParameter(DESTINATION);
        String durationText = request.getParameter(DURATION_TEXT);
        data.put("distance",distance);
        data.put("duration",duration);
        data.put("capacity",capacity);
        data.put("carId",carId);
        data.put("cost",cost);
        data.put("source",source);
        data.put("destination",destination);
        data.put("durationText",durationText);
        return data;
    }
}
