package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
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
    private TripService tripService;
    private CarService carService;
    private AddressService addressService;

    MakeOrderCommand(TripService tripService,CarService carService,AddressService addressService){

        this.carService=carService;
        this.tripService=tripService;
        this.addressService=addressService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"making order");
        Router router = new Router();
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        User user = (User) request.getSession().getAttribute("currentUser");
        HttpSession session = request.getSession();
        HashMap<String,String> data = getData(request);
        float distanceNumber = Float.parseFloat(data.get("distance")) / 1000;
        HashMap<String,String> errors = new Validator().validateOrder(data,language);
        if (errors.isEmpty()){
            int carId = Integer.parseInt(data.get("carId"));
            Car car = carService.findById(carId);
            int sourceId = addressService.createAddress(data.get("source"),user.getId());
            int destinationId = addressService.createAddress(data.get("destination"),user.getId());
            car.setAvailable(false);
            carService.updateCar(car);
            Trip trip = new Trip(new BigDecimal(data.get("cost")), new Date(), distanceNumber, carId, sourceId, destinationId, TripStatus.ORDERED);
            tripService.createTrip(trip);
            session.setAttribute("orderMessage", new MessageManager(language).getProperty("message.ordersuccess"));
        }else {
            if (!errors.containsKey("emptyCar")){
                int carId = Integer.parseInt(data.get("carId"));
                Car car = carService.findById(carId);
                session.setAttribute("car", car.getBrand().getName() + " " + car.getModel());
                session.setAttribute("carId", car.getId());
            }
            request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
            session.setAttribute("cost", data.get("cost"));
            session.setAttribute("durationText", data.get("durationText"));
            session.setAttribute("duration", data.get("duration"));
            session.setAttribute("distanceNumber", distanceNumber);
            session.setAttribute("distance", data.get("distance"));
            session.setAttribute("source", data.get("source"));
            session.setAttribute("destination", data.get("destination"));
        }
        router.setPage("path.page.welcome");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
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
