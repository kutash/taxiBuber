package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.AddressDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * The type Address service.
 */
public class AddressService extends AbstractService<Address>{

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Find addresses list.
     *
     * @param userId the user id
     * @return the list
     */
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

    /**
     * Update address.
     *
     * @param newAddress the new address
     * @return the address
     */
    public Address update(Address newAddress){
        LOGGER.log(Level.INFO,"Updatin address id={}",newAddress.getId());
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        return super.update(newAddress,addressDAO);
    }

    /**
     * Delete address address.
     *
     * @param id the id
     * @return the address
     */
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
                LOGGER.catching(Level.ERROR, e1);
            }
            LOGGER.catching(Level.ERROR, e);
        }
        transactionManager.endTransaction();
        return address;
    }
}
