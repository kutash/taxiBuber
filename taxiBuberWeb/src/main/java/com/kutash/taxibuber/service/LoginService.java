package com.kutash.taxibuber.service;

import com.kutash.taxibuber.crypter.Encryptor;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding user by email",e);
        }
        if (user == null){
            return user;
        }else {
            String encryptedPassword = Encryptor.ecnryptPassword(password, email);
            LOGGER.log(Level.DEBUG,"password {}",encryptedPassword);
            if (encryptedPassword.equals(user.getPassword())) {
                return user;
            } else {
                user = null;
            }
        }
        transactionManager.endTransaction();
        return user;
    }
}
