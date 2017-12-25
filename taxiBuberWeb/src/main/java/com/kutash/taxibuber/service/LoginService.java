package com.kutash.taxibuber.service;

import com.kutash.taxibuber.crypter.Encryptor;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginService {

    private static final Logger LOGGER = LogManager.getLogger();

    private TransactionManager transactionManager;

    public LoginService() {
        try {
            transactionManager = new TransactionManager();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception while creating TransactionManager {}",e);
        }
    }

    public User logIn(String password, String email) throws DAOException {
        LOGGER.log(Level.INFO,"log in user password {} email {}",password,email);
        User user = null;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.findEntityByEmail(email);
            transactionManager.commit();
        }catch (DAOException e){
            transactionManager.rollback();
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
