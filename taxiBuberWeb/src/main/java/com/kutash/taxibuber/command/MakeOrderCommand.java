package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.AddressService;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.TripService;
import com.kutash.taxibuber.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
    private static final int METER_IN_KILOMETER = 1000;
    private TripService tripService;
    private CarService carService;

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
        HttpSession session = request.getSession();
        HashMap<String,String> data = getData(request);
        HashMap<String,String> errors = new Validator().validateOrder(data,language);
        if (errors.isEmpty()){
            int tripId = tripService.createTrip(data,user.getId());
            Trip trip = null;
            if (tripId != 0) {
                try {
                    int seconds = 0;
                    do {
                        TimeUnit.SECONDS.sleep(6);
                        seconds += 6;
                        if (seconds >= 30) {
                            break;
                        }
                        trip = tripService.findTripById(tripId);
                    } while (!trip.getStatus().equals(TripStatus.STARTED));
                }catch (InterruptedException e){
                    LOGGER.catching(Level.ERROR,e);
                }
                if (trip.getStatus().equals(TripStatus.STARTED)) {
                    session.setAttribute("orderMessage", new MessageManager(language).getProperty("message.ordersuccess"));
                    router.setRoute(Router.RouteType.REDIRECT);
                }else {
                    request.setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
                }
            }else {
                request.setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
            }
        }else {
            returnErrors(data,errors,request,language);
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
            distanceNumber = Float.parseFloat(data.get("distance")) / METER_IN_KILOMETER;
        }
        request.setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
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
