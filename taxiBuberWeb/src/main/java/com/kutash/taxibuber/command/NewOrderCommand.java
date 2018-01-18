package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.TripService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewOrderCommand implements Command {

    private TripService service;

    NewOrderCommand(TripService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        User user = (User) request.getSession().getAttribute("currentUser");
        Trip trip = service.findOrdered(user.getId());
        String json = new Gson().toJson(trip);
        router.setPage(json);
        return router;
    }
}
