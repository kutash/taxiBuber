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
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        String distance = request.getParameter(DISTANCE);
        LOGGER.log(Level.INFO,"distance");
        String duration = request.getParameter(DURATION);
        LOGGER.log(Level.INFO,"duration");
        String capacity = request.getParameter(CAPACITY);
        LOGGER.log(Level.INFO,"capacity");
        String carId = request.getParameter(CAR_ID);
        String cost = request.getParameter(COST);
        LOGGER.log(Level.INFO,"cost");
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        String source = null;
        String destination = null;
        String durationText = null;
        try {
            source = new String(request.getParameter(SOURCE).getBytes("ISO-8859-1"),"UTF-8");
            System.out.println("==============================="+source);
            destination = new String(request.getParameter(DESTINATION).getBytes("ISO-8859-1"),"UTF-8");
            durationText = new String(request.getParameter(DURATION_TEXT).getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.ERROR,"Exception while decoding parameters",e);
        }
        User user = (User) request.getSession().getAttribute("currentUser");
        Car car;
        float distanceNumber = Float.parseFloat(distance) / 1000;
        if (StringUtils.isNotEmpty(carId)) {
            System.out.println("not empty carId");
            car = carService.findById(Integer.parseInt(carId));
            if (StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(destination)) {
                System.out.println("not empty source");
                String result = new CostCalculator(carService).defineCost(distance, duration, capacity, carId);
                if (result.equals(cost)) {
                    System.out.println("equals cost");
                    int sourceId = orderService.createAddress(source,user.getId());
                    int destinationId = orderService.createAddress(destination,user.getId());
                    car.setAvailable(false);
                    carService.updateCar(car);
                    Trip trip = new Trip(new BigDecimal(cost), new Date(), distanceNumber, Integer.parseInt(carId), sourceId, destinationId, TripStatus.ORDERED);
                    orderService.createTrip(trip);
                    session.setAttribute("orderMessage", new MessageManager(language).getProperty("message.ordersuccess"));
                } else {
                    System.out.println("not equals cost");
                    request.getSession().setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
                    session.setAttribute("car", car.getBrand().getName() + " " + car.getModel());
                    session.setAttribute("carId", car.getId());
                    session.setAttribute("cost", cost);
                    session.setAttribute("durationText", durationText);
                    session.setAttribute("duration", duration);
                    session.setAttribute("distanceNumber", distanceNumber);
                    session.setAttribute("distance", distance);
                    session.setAttribute("source", source);
                    session.setAttribute("destination", destination);
                }
            } else {
                System.out.println("empty source");
                session.setAttribute("orderMessage", new MessageManager(language).getProperty("message.wrongorder"));
                session.setAttribute("car", car.getBrand().getName() + " " + car.getModel());
                session.setAttribute("carId", car.getId());
                session.setAttribute("cost", cost);
                session.setAttribute("durationText", durationText);
                session.setAttribute("duration", duration);
                session.setAttribute("distanceNumber", distanceNumber);
                session.setAttribute("distance", distance);
            }
        }else {
            System.out.println("empty carId");
            session.setAttribute("orderMessage", new MessageManager(language).getProperty("label.carerror"));
            session.setAttribute("cost", cost);
            session.setAttribute("durationText", durationText);
            session.setAttribute("duration", duration);
            session.setAttribute("distanceNumber", distanceNumber);
            session.setAttribute("distance", distance);
            session.setAttribute("source", source);
            session.setAttribute("destination", destination);
        }
        router.setPage("?command=order");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
