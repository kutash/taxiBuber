package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.AddressDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger();

    public List<Address> findAddresses(int userId) {
        LOGGER.log(Level.INFO,"Finding addresses by user id");
        TransactionManager transactionManager = new TransactionManager();
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        List<Address> addresses = null;
        try {
            transactionManager.beginTransaction(addressDAO);
            addresses = addressDAO.findAddressByUserId(userId);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding addresses {}",e);
        }
        transactionManager.endTransaction();
        return addresses;
    }

}
