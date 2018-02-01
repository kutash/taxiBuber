package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.*;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TripService extends AbstractService<Trip>{

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int METER_IN_KILOMETER = 1000;

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

    /*public int createTrip(Trip trip){
        int result = 0;
        TransactionManager transactionManager = new TransactionManager();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        try {
            transactionManager.beginTransaction(tripDAO);
            result = tripDAO.create(trip);
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
        return result;
    }*/

    public int create(Trip entity) {
        LOGGER.log(Level.INFO,"Creating trip");
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        return super.create(entity, tripDAO);
    }

    public Trip findOrdered(int tripId){
        TransactionManager transactionManager = new TransactionManager();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        Trip trip = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trip = tripDAO.findOrdered(tripId);
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

    public Trip findTripById(int id) {
        LOGGER.log(Level.INFO,"Finding trip id={}",id);
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        TransactionManager transactionManager = new TransactionManager();
        Trip trip = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trip = tripDAO.findEntityById(id);
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

    public Trip updateTrip(Trip newTrip){
        LOGGER.log(Level.INFO,"Updating trip id={}",newTrip.getId());
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        TransactionManager transactionManager = new TransactionManager();
        Trip trip = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trip = tripDAO.update(newTrip);
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

    public Trip startTrip(int tripId){
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        Trip trip = null;
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.beginTransaction(tripDAO,carDAO);
            trip = tripDAO.findEntityById(tripId);
            Car car = carDAO.findEntityById(trip.getIdCar());
            car.setAvailable(false);
            carDAO.update(car);
            trip.setStatus(TripStatus.STARTED);
            trip = tripDAO.update(trip);
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

    public Trip completeTrip(String tripId,String latitude,String longitude){
        String latitudeRound = new BigDecimal(latitude).setScale(6, RoundingMode.UP).toString();
        String longitudeRound = new BigDecimal(longitude).setScale(6, RoundingMode.UP).toString();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        Trip trip = null;
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.beginTransaction(tripDAO,carDAO);
            trip = tripDAO.findEntityById(Integer.parseInt(tripId));
            trip.setStatus(TripStatus.COMPLETED);
            Car car = carDAO.findEntityById(trip.getIdCar());
            car.setLatitude(latitudeRound);
            car.setLongitude(longitudeRound);
            carDAO.update(car);
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

    private Address isExist(List<Address> addresses, Address address){
        for (Address addr : addresses){
            if (addr.getAddress().equals(address.getAddress())){
                return addr;
            }
        }
        return address;
    }

    public int createTrip(HashMap<String,String> data, int userId){
        int result = 0;
        float distanceNumber = Float.parseFloat(data.get("distance")) / METER_IN_KILOMETER;
        int carId = Integer.parseInt(data.get("carId"));
        int sourceId;
        int destinationId;
        Address source = new Address(data.get("source"),userId, Status.ACTIVE);
        Address destination = new Address(data.get("destination"),userId, Status.ACTIVE);
        TransactionManager transactionManager = new TransactionManager();
        DAOFactory daoFactory = new DAOFactory();
        AddressDAO addressDAO = daoFactory.getAddressDAO();
        TripDAO tripDAO = daoFactory.getTripDAO();
        CarDAO carDAO = daoFactory.getCarDAO();
        try {
            transactionManager.beginTransaction(addressDAO,tripDAO,carDAO);
            List<Address> addresses = addressDAO.findAddressByUserId(userId);
            source = isExist(addresses,source);
            if (source.getId() == 0){
                sourceId = addressDAO.create(source);
            }else {
                sourceId = source.getId();
            }
            destination = isExist(addresses,destination);
            if (destination.getId() == 0){
                destinationId = addressDAO.create(destination);
            }else {
                destinationId = destination.getId();
            }
            Car car = carDAO.findEntityById(carId);
            if(!car.isAvailable()){
                return 0;
            }
            car.setAvailable(false);
            carDAO.update(car);
            Trip trip = new Trip(new BigDecimal(data.get("cost")), new Date(), distanceNumber, carId, sourceId, destinationId, TripStatus.ORDERED);
            result = tripDAO.create(trip);
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
