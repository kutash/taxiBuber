package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.AddressDAO;
import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.TripDAO;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.TripStatus;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.websocket.WebSocketSender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * The type Trip service.
 */
public class TripService extends AbstractService<Trip>{

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int METER_IN_KILOMETER = 1000;

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Trip> findAll() {
        LOGGER.log(Level.INFO,"Finding all trips");
        TransactionManager transactionManager = new TransactionManager();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        List<Trip> trips = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trips = tripDAO.findAll();
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(e1);
            }
            LOGGER.catching(e);
        }
        transactionManager.endTransaction();
        return trips;
    }

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @param role   the role
     * @return the list
     */
    public List<Trip> findByUserId(int userId, UserRole role) {
        LOGGER.log(Level.INFO,"Finding all trips by user id");
        TransactionManager transactionManager = new TransactionManager();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        List<Trip> trips = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trips = tripDAO.findByUserId(userId,role);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(e1);
            }
            LOGGER.catching(e);
        }
        transactionManager.endTransaction();
        return trips;
    }

    /**
     * Create int.
     *
     * @param entity the entity
     * @return the int
     */
    public int create(Trip entity) {
        LOGGER.log(Level.INFO,"Creating trip");
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        return super.create(entity, tripDAO);
    }

    /**
     * Start trip trip.
     *
     * @param tripId the trip id
     * @return the trip
     */
    public Trip startTrip(int tripId){
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        Trip trip = null;
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.beginTransaction(tripDAO,userDAO);
            trip = tripDAO.findEntityById(tripId);
            User user = userDAO.findEntityById(trip.getClientId());
            trip.setStatus(TripStatus.STARTED);
            trip = tripDAO.update(trip);
            new WebSocketSender().send(user,"started");
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return trip;
    }

    /**
     * Complete trip trip.
     *
     * @param tripId the trip id
     * @return the trip
     */
    public Trip completeTrip(String tripId){
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        Trip trip = null;
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.beginTransaction(tripDAO);
            trip = tripDAO.findEntityById(Integer.parseInt(tripId));
            trip.setStatus(TripStatus.COMPLETED);
            tripDAO.update(trip);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return trip;
    }

    /**
     * Find started trip.
     *
     * @param carId the car id
     * @return the trip
     */
    public Trip findStarted(int carId) {
        LOGGER.log(Level.INFO,"Finding all trips");
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        TransactionManager transactionManager = new TransactionManager();
        Trip trip = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trip = tripDAO.findStarted(carId);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return trip;
    }

    private int isExist(List<Address> addresses, String address){
        for (Address addr : addresses){
            if (addr.getAddress().equals(address)){
                return addr.getId();
            }
        }
        return 0;
    }

    /**
     * Create trip int.
     *
     * @param data   the data
     * @param userId the user id
     * @return the int
     */
    public int createTrip(HashMap<String,String> data, int userId){
        int result = 0;
        float distanceNumber = Float.parseFloat(data.get("distance")) / METER_IN_KILOMETER;
        int carId = Integer.parseInt(data.get("carId"));
        TransactionManager transactionManager = new TransactionManager();
        DAOFactory daoFactory = new DAOFactory();
        AddressDAO addressDAO = daoFactory.getAddressDAO();
        TripDAO tripDAO = daoFactory.getTripDAO();
        CarDAO carDAO = daoFactory.getCarDAO();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            transactionManager.beginTransaction(addressDAO,tripDAO,carDAO,userDAO);
            List<Address> addresses = addressDAO.findAddressByUserId(userId);
            int sourceId = isExist(addresses,data.get("source"));
            if (sourceId == 0){
                sourceId = addressDAO.create(new Address(data.get("source"),userId, Status.ACTIVE));
            }
            int destinationId = isExist(addresses,data.get("destination"));
            if (destinationId == 0){
                destinationId = addressDAO.create(new Address(data.get("destination"),userId, Status.ACTIVE));
            }
            Car car = carDAO.findEntityById(carId);
            if(!car.isAvailable()){
                return 0;
            }
            car.setAvailable(false);
            carDAO.update(car);
            User user = userDAO.findEntityById(car.getUserId());
            Trip trip = new Trip(new BigDecimal(data.get("cost")), new Date(), distanceNumber, carId, sourceId, destinationId, TripStatus.ORDERED);
            result = tripDAO.create(trip);
            trip = tripDAO.findEntityById(result);
            new WebSocketSender().send(user,trip);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR, e1);
            }
            LOGGER.catching(Level.ERROR, e);
        }
        transactionManager.endTransaction();
        return result;
    }
}
