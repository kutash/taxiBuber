package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.util.EmailSender;
import com.kutash.taxibuber.util.PasswordEncryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class LoginService {

    private static final Logger LOGGER = LogManager.getLogger();

    public User logIn(String password, String email) {
        LOGGER.log(Level.INFO,"log in user password {} email {}",password,email);
        TransactionManager transactionManager = new TransactionManager();
        User user = null;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.findEntityByEmail(email);
            transactionManager.commit();
        }catch (DAOException e){
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        if (user != null){
            String encryptedPassword = new PasswordEncryptor(email).encrypt(password);
            LOGGER.log(Level.DEBUG,"password {}",encryptedPassword);
            if (encryptedPassword.equals(user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
        return user;
    }

    public User sendPassword(String email,String language){
        TransactionManager transactionManager = new TransactionManager();
        User user = null;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.findEntityByEmail(email);
            System.out.println(user);
            if (user != null){
                String password = UUID.randomUUID().toString();
                user.setPassword(password);
                new EmailSender().sendNewPassword(user,language);
                user.setPassword(new PasswordEncryptor(user.getEmail()).encrypt(password));
                user = userDAO.update(user);
            }
            transactionManager.commit();
        }catch (DAOException e){
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return user;
    }

    public void logOut(int userId){
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        try {
            transactionManager.beginTransaction(carDAO);
            Car car = carDAO.findEntityByUserId(userId);
            if (car != null && car.isAvailable()) {
                car.setAvailable(false);
                carDAO.update(car);
            }
        }catch (DAOException e){
                try {
                    transactionManager.rollback();
                } catch (DAOException e1) {
                    LOGGER.catching(Level.ERROR,e1);
                }
                LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
    }
}
