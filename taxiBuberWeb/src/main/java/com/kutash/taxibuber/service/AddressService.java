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

public class AddressService {

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

    public int createAddress(String newAddress, int userId){
        int result = 0;
        List<Address> addresses = findAddresses(userId);
        for (Address address : addresses){
            if (address.getAddress().equals(newAddress)){
                result = address.getId();
            }
        }
        if (result == 0) {
            TransactionManager transactionManager = new TransactionManager();
            AddressDAO addressDAO = new DAOFactory().getAddressDAO();
            try {
                transactionManager.beginTransaction(addressDAO);
                result = addressDAO.create(new Address(newAddress,userId));
                transactionManager.commit();
            } catch (DAOException e) {
                try {
                    transactionManager.rollback();
                } catch (DAOException e1) {
                    LOGGER.log(Level.ERROR, "Exception while making rollback", e1);
                }
                LOGGER.log(Level.ERROR, "Exception while creating address {}", e);
            }
            transactionManager.endTransaction();
        }
        return result;
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
}
