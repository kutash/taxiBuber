package com.kutash.taxibuber.service;

import com.kutash.taxibuber.util.PasswordEncryptor;
import com.kutash.taxibuber.dao.CommentDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Map;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    public List<User> findAll() {
        LOGGER.log(Level.INFO,"Finding all users");
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        List<User> users = null;
        try {
            transactionManager.beginTransaction(userDAO);
            users = userDAO.findAll();
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding all users {}",e);
        }
        transactionManager.endTransaction();
        return users;
    }

    public User findById(int id) {
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        User user = null;
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.findEntityById(id);
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
        return user;
    }

    public int deleteUsers(int ... ids) {
        LOGGER.log(Level.INFO,"deleting users");
        TransactionManager transactionManager = new TransactionManager();
        int res = 0;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            for (int id : ids) {
                res += userDAO.delete(id);
            }
            transactionManager.commit();
        }catch (DAOException e){
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while deleting user {}",e);
        }
        transactionManager.endTransaction();
        return res;
    }

    public Map<String,String> validateUser(Map<String,String> data,String language){
        return new Validator().validateUser(data,language);
    }

    public int create(User user) {
        TransactionManager transactionManager = new TransactionManager();
        int result = 0;
        if (isUniqueEmail(user.getEmail())) {
            user.setPassword(new PasswordEncryptor(user.getEmail()).encrypt(user.getPassword()));
            UserDAO userDAO = new DAOFactory().getUserDAO();
            try {
                transactionManager.beginTransaction(userDAO);
                result = userDAO.create(user);
                transactionManager.commit();
            } catch (DAOException e) {
                try {
                    transactionManager.rollback();
                } catch (DAOException e1) {
                    LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
                }
                LOGGER.log(Level.ERROR,"Exception while creating user {}",e);
            }
            transactionManager.endTransaction();
        }
        return result;
    }

    public User updateUser(User newUser){
        LOGGER.log(Level.INFO,"Updatin user id={}",newUser.getId());
        UserDAO userDAO = new DAOFactory().getUserDAO();
        TransactionManager transactionManager = new TransactionManager();
        User user = null;
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.update(newUser);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while updating user {}",e);
        }
        transactionManager.endTransaction();
        return user;
    }

    public boolean isUniqueEmail(String email) {
        TransactionManager transactionManager = new TransactionManager();
        boolean result = false;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            result = userDAO.isEmailExists(email);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while checking email {}",e);
        }
        transactionManager.endTransaction();
        return result;
    }

    public List<Comment> findComments(int userId) {
        LOGGER.log(Level.INFO,"Finding comments by user id");
        TransactionManager transactionManager = new TransactionManager();
        CommentDAO commentDAO = new DAOFactory().getCommentDAO();
        List<Comment> comments = null;
        try {
            transactionManager.beginTransaction(commentDAO);
            comments = commentDAO.findEntityByUserId(userId);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding comments {}",e);
        }
        transactionManager.endTransaction();
        return comments;
    }
}
