package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Delete car command.
 */
public class DeleteCarCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CAR_ID = "carId";
    private static final String DRIVER_ID = "userId";
    private static final String LANGUAGE = "language";
    private CarService carService;

    /**
     * Instantiates a new Delete car command.
     *
     * @param carService the car service
     */
    DeleteCarCommand(CarService carService) {
        this.carService=carService;

    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Deleting car");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute("createMessage");
        session.removeAttribute("updateMessage");
        session.removeAttribute("updatedUser");
        session.removeAttribute("updatePassword");
        String language = (String) session.getAttribute(LANGUAGE);
        MessageManager messageManager = new MessageManager(language);
        String id = request.getParameter(DRIVER_ID);
        int driverId = Integer.parseInt(id);
        String carId = request.getParameter(CAR_ID);
        Car car = carService.deleteCar(carId);
        if(car.getStatus().equals(Status.ARCHIVED)) {
            router.setRoute(Router.RouteType.REDIRECT);
            request.getSession().setAttribute("deletedMessage", messageManager.getProperty("label.car") + " " + messageManager.getProperty("message.deleted"));
            router.setPage(PageManager.getProperty("path.command.edit") + driverId);
        }else {
            request.getSession().setAttribute("wasNotDeleted", messageManager.getProperty("label.car") + " " + messageManager.getProperty("message.notdeleted"));
            router.setPage(PageManager.getProperty("path.command.edit") + driverId);
        }
        return router;
    }
}
