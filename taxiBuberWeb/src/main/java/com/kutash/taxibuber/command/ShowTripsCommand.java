package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTripsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CURRENT_USER = "currentUser";
    //private static final String USER_ID = "userId";
    private TripService tripService;

    ShowTripsCommand(TripService tripService){
        this.tripService=tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        System.out.println(user);
        List<Trip> trips;
        UserRole role = user.getRole();
        if (role.equals(UserRole.ADMIN)){
            trips = tripService.findAll();
        }else {
            trips = tripService.findByUserId(user.getId(),role);
        }
        request.setAttribute("trips",trips);
        router.setPage(PageManager.getProperty("path.page.trips"));
        return router;
    }
}
