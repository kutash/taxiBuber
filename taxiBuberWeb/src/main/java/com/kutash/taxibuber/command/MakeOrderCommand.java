package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.OrderService;
import com.kutash.taxibuber.util.CostCalculator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.SplittableRandom;

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
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private OrderService orderService;
    private CarService carService;

    MakeOrderCommand(OrderService orderService,CarService carService){

        this.carService=carService;
        this.orderService=orderService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        LOGGER.log(Level.INFO,"making order");
        String distance = request.getParameter(DISTANCE);
        String duration = request.getParameter(DURATION);
        String capacity = request.getParameter(CAPACITY);
        String carId = request.getParameter(CAR_ID);
        if (StringUtils.isEmpty(carId)){
            List<Car> cars = carService.findAllAvailable(request.getParameter(LATITUDE),request.getParameter(LONGITUDE),capacity);
        }
        String cost = request.getParameter(COST);
        String source = null;
        String destination = null;
        try {
            source = new String(request.getParameter(SOURCE).getBytes("ISO-8859-1"),"UTF-8");
            destination = new String(request.getParameter(DESTINATION).getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("source"+source);
        User user = (User) request.getSession().getAttribute("currentUser");
        int sourceId = orderService.createAddress(source,user.getId());
        System.out.println("destination"+destination);
        int destinationId = orderService.createAddress(destination,user.getId());
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        if (StringUtils.isNotEmpty(distance) && StringUtils.isNotEmpty(duration) && StringUtils.isNotEmpty(carId)){
            String result = new CostCalculator(carService).defineCost(distance,duration,capacity,carId);
            if (result.equals(cost)) {
                Car car = carService.findById(Integer.parseInt(carId));
                car.setAvailable(false);
                carService.updateCar(car);
                Trip trip = new Trip(new BigDecimal(cost),new Date(),Float.parseFloat(distance)/1000,Integer.parseInt(carId),sourceId,destinationId, TripStatus.ORDERED);
                orderService.createTrip(trip);
                request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.ordersuccess"));
            }else {
                request.getSession().setAttribute("orderMessage",new MessageManager(language).getProperty("message.wrongorder"));
            }
        }else {
            request.getSession().setAttribute("orderMessage",new MessageManager(language).getProperty("message.wrongorder"));
        }
        router.setPage("?command=order");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
