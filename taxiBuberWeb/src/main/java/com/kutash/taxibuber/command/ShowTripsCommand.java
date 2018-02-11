package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.TripService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Show trips command.
 */
public class ShowTripsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CURRENT_USER = "currentUser";
    private static final String SWITCH_LANGUAGE = "switchLanguage";
    private TripService tripService;

    /**
     * Instantiates a new Show trips command.
     *
     * @param tripService the trip service
     */
    ShowTripsCommand(TripService tripService){
        this.tripService=tripService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Showing trips");
        Router router = new Router();
        HttpSession session = request.getSession();
        String switchLanguage = request.getParameter(SWITCH_LANGUAGE);
        if (StringUtils.isNotEmpty(switchLanguage) && Boolean.parseBoolean(switchLanguage)){
            session.removeAttribute("isCreated");
        }
        User user = (User) session.getAttribute(CURRENT_USER);
        if (user == null){
            router.setPage(PageManager.getProperty("path.page.error403"));
        }else {
            List<Trip> trips;
            UserRole role = user.getRole();
            if (role.equals(UserRole.ADMIN)) {
                trips = tripService.findAll();
            } else {
                trips = tripService.findByUserId(user.getId(), role);
            }
            request.setAttribute("trips", trips);
            router.setPage(PageManager.getProperty("path.page.trips"));
        }
        return router;
    }
}
