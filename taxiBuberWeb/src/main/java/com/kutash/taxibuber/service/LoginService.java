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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.UUID;

/**
 * The type Login service.
 */
public class LoginService {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Log in user.
     *
     * @param password the password
     * @param email    the email
     * @return the user
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public User logIn(byte[] password, String email) throws UnsupportedEncodingException {
        LOGGER.log(Level.INFO,"log in user email {}",email);
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
            byte[] encryptedPassword = new PasswordEncryptor(email).encrypt(password);
            LOGGER.log(Level.INFO,"password {}",new String(encryptedPassword,"UTF-8"));
            if (!Arrays.equals(encryptedPassword, user.getPassword())) {
                return null;
            }
        }
        return user;
    }

    /**
     * Send password user.
     *
     * @param email    the email
     * @param language the language
     * @return the user
     */
    public User sendPassword(String email,String language){
        LOGGER.log(Level.INFO,"sending password to emai {}",email);
        TransactionManager transactionManager = new TransactionManager();
        User user = null;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.findEntityByEmail(email);
            if (user != null){
                String password = UUID.randomUUID().toString();
                user.setPassword(password.getBytes());
                new EmailSender().sendNewPassword(user,language);
                user.setPassword(new PasswordEncryptor(user.getEmail()).encrypt(password.getBytes()));
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

    /**
     * Log out.
     *
     * @param userId the user id
     */
    public void logOut(int userId){
        LOGGER.log(Level.INFO,"log out user id {}",userId);
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
