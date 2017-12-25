package com.kutash.taxibuber.service;

import com.kutash.taxibuber.crypter.Encryptor;
import com.kutash.taxibuber.dao.CommentDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private TransactionManager transactionManager;

    public UserService() {
        try {
            transactionManager = new TransactionManager();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception while creating TransactionManager {}",e);
        }
    }

    public List<User> findAll() {
        LOGGER.log(Level.INFO,"Finding all users");
        UserDAO userDAO = new DAOFactory().getUserDAO();
        List<User> users = null;
        try {
            transactionManager.beginTransaction(userDAO);
            users = userDAO.findAll();
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while finding all users {}",e);
        }
        transactionManager.endTransaction();
        return users;
    }

    public User findById(int id){
        UserDAO userDAO = new DAOFactory().getUserDAO();
        User user = null;
        try {
            transactionManager.beginTransaction(userDAO);
            user = userDAO.findEntityById(id);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while finding user by id {}",e);
        }
        transactionManager.endTransaction();
        return user;
    }

    public int deleteUsers(int ... ids) throws SQLException, DAOException {
        LOGGER.log(Level.INFO,"deleting users");
        int res = 0;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            for (int id : ids) {
                res += userDAO.delete(id);
            }
            transactionManager.commit();
        }catch (DAOException e){
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while deleting user {}",e);
        }
        transactionManager.endTransaction();
        return res;
    }

    public int create(User user){
        int result = 0;
        if (isUniqueEmail(user.getEmail())) {
            user.setPassword(Encryptor.ecnryptPassword(user.getPassword(),user.getEmail()));
            UserDAO userDAO = new DAOFactory().getUserDAO();
            //AddressDAO

            try {
                transactionManager.beginTransaction(userDAO);
                result = userDAO.create(user);
                transactionManager.commit();
            } catch (DAOException e) {
                transactionManager.rollback();
                LOGGER.log(Level.ERROR,"Exception while creating user {}",e);
            }
            transactionManager.endTransaction();
        }

        return result;
    }

    private boolean isUniqueEmail(String email){
        boolean result = false;
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            result = userDAO.isEmailExists(email);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while checking email {}",e);
        }
        transactionManager.endTransaction();
        return result;
    }

    public List<Comment> findComments(int userId){
        LOGGER.log(Level.INFO,"Finding comments by user id");
        CommentDAO commentDAO = new DAOFactory().getCommentDAO();
        List<Comment> comments = null;
        try {
            transactionManager.beginTransaction(commentDAO);
            comments = commentDAO.findEntityByUserId(userId);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while finding comments {}",e);
        }
        transactionManager.endTransaction();
        return comments;
    }
}
