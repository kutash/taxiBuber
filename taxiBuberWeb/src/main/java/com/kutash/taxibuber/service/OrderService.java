package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger();
    private TransactionManager transactionManager;

    public OrderService() {
        try {
            transactionManager = new TransactionManager();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception while creating TransactionManager {}",e);
        }
    }

    public List<Car> findAllAvailable() {
        LOGGER.log(Level.INFO,"Finding all available cars");
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<Car> cars = null;
        try {
            transactionManager.beginTransaction(carDAO);
            cars = carDAO.findAllAvailable();
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while finding all cars {}",e);
        }
        transactionManager.endTransaction();
        return cars;
    }
}
