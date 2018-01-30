package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.TripDAO;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TripService {

    private static final Logger LOGGER = LogManager.getLogger();

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

    public int createTrip(Trip trip){
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
    }

    public Trip findOrdered(int userId){
        TransactionManager transactionManager = new TransactionManager();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        Trip trip = null;
        try {
            transactionManager.beginTransaction(tripDAO);
            trip = tripDAO.findOrdered(userId);
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
        LOGGER.log(Level.INFO,"Updatin trip id={}",newTrip.getId());
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
}
