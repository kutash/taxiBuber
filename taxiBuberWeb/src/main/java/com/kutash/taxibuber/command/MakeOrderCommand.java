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
    private OrderService orderService;
    private CarService carService;

    MakeOrderCommand(OrderService orderService,CarService carService){

        this.carService=carService;
        this.orderService=orderService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"making order");
        Router router = new Router();
        String distance = request.getParameter(DISTANCE);
        String duration = request.getParameter(DURATION);
        String capacity = request.getParameter(CAPACITY);
        String carId = request.getParameter(CAR_ID);
        String cost = request.getParameter(COST);
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        String source = null;
        String destination = null;
        String durationText = null;
        try {
            source = new String(request.getParameter(SOURCE).getBytes("ISO-8859-1"),"UTF-8");
            destination = new String(request.getParameter(DESTINATION).getBytes("ISO-8859-1"),"UTF-8");
            durationText = new String(request.getParameter(DURATION_TEXT).getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.ERROR,"Exception while decoding parameters",e);
        }
        float distanceNumber = Float.parseFloat(distance) / 1000;
        User user = (User) request.getSession().getAttribute("currentUser");
        int sourceId = orderService.createAddress(source,user.getId());
        int destinationId = orderService.createAddress(destination,user.getId());
        Car car;
        if (StringUtils.isNotEmpty(carId)) {
            car = carService.findById(Integer.parseInt(carId));
            if (StringUtils.isNotEmpty(distance) && StringUtils.isNotEmpty(duration)) {
                String result = new CostCalculator(carService).defineCost(distance, duration, capacity, carId);
                if (result.equals(cost)) {
                    car.setAvailable(false);
                    carService.updateCar(car);
                    Trip trip = new Trip(new BigDecimal(cost), new Date(), distanceNumber, Integer.parseInt(carId), sourceId, destinationId, TripStatus.ORDERED);
                    orderService.createTrip(trip);
                    request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.ordersuccess"));
                } else {
                    request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
                    request.getSession().setAttribute("cost", cost);
                    request.getSession().setAttribute("duration", durationText);
                    request.getSession().setAttribute("distance", distanceNumber);
                    request.getSession().setAttribute("source", source);
                    request.getSession().setAttribute("destination", destination);
                    request.getSession().setAttribute("car", car.getBrand().getName()+" "+car.getModel());
                    request.getSession().setAttribute("carId", car.getId());
                }
            } else {
                request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
                request.getSession().setAttribute("cost", cost);
                request.getSession().setAttribute("duration", durationText);
                request.getSession().setAttribute("distance", distanceNumber);
                request.getSession().setAttribute("source", source);
                request.getSession().setAttribute("destination", destination);
                request.getSession().setAttribute("car", car.getModel() + " " + car.getBrand());
                request.getSession().setAttribute("carId", car.getId());
            }
        }else {
            request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
            request.getSession().setAttribute("cost", cost);
            request.getSession().setAttribute("duration", durationText);
            request.getSession().setAttribute("distance", distanceNumber);
            request.getSession().setAttribute("source", source);
            request.getSession().setAttribute("destination", destination);
        }
        router.setPage("?command=order");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }

}
