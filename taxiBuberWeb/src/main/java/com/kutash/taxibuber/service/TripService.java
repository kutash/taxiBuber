package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.*;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TripService {

    private static final Logger LOGGER = LogManager.getLogger();

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
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding trip by id {}",e);
        }
        transactionManager.endTransaction();
        return trip;
    }

    public Address findAddressById(int id) {
        LOGGER.log(Level.INFO,"Finding address id={}",id);
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        TransactionManager transactionManager = new TransactionManager();
        Address address = null;
        try {
            transactionManager.beginTransaction(addressDAO);
            address = addressDAO.findEntityById(id);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding address by id {}",e);
        }
        transactionManager.endTransaction();
        return address;
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
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding trip by id {}",e);
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
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while updating trip {}",e);
        }
        transactionManager.endTransaction();
        return trip;
    }
}
