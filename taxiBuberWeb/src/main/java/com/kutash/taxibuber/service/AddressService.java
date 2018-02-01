package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.*;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return addresses;
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
                LOGGER.catching(Level.ERROR, e1);;
            }
            LOGGER.catching(Level.ERROR, e);
        }
        transactionManager.endTransaction();
        return address;
    }

    public Address update(Address newAddress){
        LOGGER.log(Level.INFO,"Updatin address id={}",newAddress.getId());
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        TransactionManager transactionManager = new TransactionManager();
        Address address = null;
        try {
            transactionManager.beginTransaction(addressDAO);
            address = addressDAO.update(newAddress);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR, e1);;
            }
            LOGGER.catching(Level.ERROR, e);
        }
        transactionManager.endTransaction();
        return address;
    }

    public Address deleteAddress(String id){
        int addressId = Integer.parseInt(id);
        Address address = null;
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.beginTransaction(addressDAO);
            address = addressDAO.findEntityById(addressId);
            address.setStatus(Status.ARCHIVED);
            address = addressDAO.update(address);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR, e1);;
            }
            LOGGER.catching(Level.ERROR, e);
        }
        transactionManager.endTransaction();
        return address;
    }
}
