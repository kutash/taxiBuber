package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private OrderService service;

    OrderCommand(OrderService orderService) {
        this.service = orderService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"order command");
        Router router = new Router();
        User user = (User) request.getSession().getAttribute("currentUser");
        List<Address> addresses = service.findAddresses(user.getId());
        request.setAttribute("addresses",addresses);
        router.setPage(PageManager.getProperty("path.page.welcome"));
        return router;
    }
}
