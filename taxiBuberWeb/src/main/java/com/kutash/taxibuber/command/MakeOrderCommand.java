package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private OrderService service;

    MakeOrderCommand(OrderService service){
        this.service=service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        LOGGER.log(Level.INFO,"getting cost");
        String distance = request.getParameter(DISTANCE);
        System.out.println(distance);
        String duration = request.getParameter(DURATION);
        System.out.println(duration);
        String carId = request.getParameter(CAR_ID);
        System.out.println(carId);
        String capacity = request.getParameter(CAPACITY);
        System.out.println(capacity);
        String cost = request.getParameter(COST);
        System.out.println(cost);
        String source = request.getParameter(SOURCE);
        String destination = request.getParameter(DESTINATION);
        String language = (String) request.getSession().getAttribute(LANGUAGE);
        if (StringUtils.isNotEmpty(distance) && StringUtils.isNotEmpty(duration)){
            String result = service.defineCost(distance,duration,capacity,carId);
            if (result.equals(cost)) {


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
