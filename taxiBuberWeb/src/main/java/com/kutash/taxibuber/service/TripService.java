package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.TripDAO;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
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
            LOGGER.log(Level.ERROR,"Exception while finding user by id {}",e);
        }
        transactionManager.endTransaction();
        return trip;
    }
}
