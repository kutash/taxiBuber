package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.*;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.util.DateParser;
import com.kutash.taxibuber.util.FileManager;
import com.kutash.taxibuber.util.PasswordEncryptor;
import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService extends AbstractService<User>{

    private static final Logger LOGGER = LogManager.getLogger();

    public List<User> findAll() {
        LOGGER.log(Level.INFO,"Finding all users");
        UserDAO userDAO = new DAOFactory().getUserDAO();
        return super.findAll(userDAO);
    }

    public User saveUser(Map<String,String> userData,Part photoPart){
        String encryptedPassword = new PasswordEncryptor(userData.get("email")).encrypt(userData.get("password"));
        User user = new User(userData.get("name"),userData.get("surname"),userData.get("patronymic"),userData.get("email"),encryptedPassword,UserRole.valueOf(userData.get("role")), DateParser.parseDate(userData.get("birthday")),userData.get("phone"), Status.ACTIVE);
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(userDAO);
            int id = userDAO.create(user);
            user.setId(id);
            String photoPath = new FileManager().savePhoto(photoPart,id,false);
            user.setPhotoPath(photoPath);
            user = userDAO.update(user);
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
        return user;
    }

    public User updateUser(User user,Map<String,String> userData,Part photo){
        LOGGER.log(Level.INFO,"Updating user id={}",user.getId());
        String photoPath = new FileManager().savePhoto(photo,user.getId(),false);
        user.setName(userData.get("name"));
        user.setSurname(userData.get("surname"));
        user.setPatronymic(userData.get("patronymic"));
        user.setPhone(userData.get("phone"));
        user.setBirthday(DateParser.parseDate(userData.get("birthday")));
        if (StringUtils.isNotEmpty(photoPath)) {
            user.setPhotoPath(photoPath);
        }
        UserDAO userDAO = new DAOFactory().getUserDAO();
        return super.update(user,userDAO);
    }

    public boolean isEmailExist(String email) {
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
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return result;
    }

    public Map<String,String> changePassword(User user, String oldPassword,String password,String passwordConfirm,String language){
        String encryptedPassword = new PasswordEncryptor(user.getEmail()).encrypt(oldPassword);
        Map<String, String> errors = new HashMap<>();
        if (user.getPassword().equals(encryptedPassword)) {
            errors = new Validator().checkPassword(password, passwordConfirm, language);
            if (errors.isEmpty()){
                user.setPassword(new PasswordEncryptor(user.getEmail()).encrypt(password));
                UserDAO userDAO = new DAOFactory().getUserDAO();
                super.update(user,userDAO);
            }
        }else {
            errors.put("wrongPassword",new MessageManager(language).getProperty("label.wrongpassword"));
        }
        return errors;
    }

    public User findUser(String userId){
        User user = null;
        int id = Integer.parseInt(userId);
        TransactionManager transactionManager = new TransactionManager();
        CommentDAO commentDAO = new DAOFactory().getCommentDAO();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        try {
            transactionManager.beginTransaction(commentDAO, userDAO);
            user = userDAO.findEntityById(id);
            List<Comment> comments = commentDAO.findEntityByUserId(id);
            user.setComments(comments);
        }catch (DAOException e) {
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

    public void createComment(Comment commentNew){
        TransactionManager transactionManager = new TransactionManager();
        CommentDAO commentDAO = new DAOFactory().getCommentDAO();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        List<Comment> comments;
        try {
            transactionManager.beginTransaction(commentDAO,userDAO);
            int created = commentDAO.create(commentNew);
            if (created > 0) {
                comments = commentDAO.findEntityByUserId(commentNew.getUserId());
                int sum = 0;
                for (Comment comment : comments) {
                    sum += comment.getMark();
                }
                float result = (float) sum / comments.size();
                float rating = new BigDecimal(result).setScale(1, RoundingMode.UP).floatValue();
                User user = userDAO.findEntityById(commentNew.getUserId());
                user.setRating(rating);
                userDAO.update(user);
                transactionManager.commit();
            }else {
                transactionManager.rollback();
            }
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
    }

    public User deleteUser(String userId){
        int id = Integer.parseInt(userId);
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        User user = null;
        try {
            transactionManager.beginTransaction(userDAO,carDAO);
            user = userDAO.findEntityById(id);
            user.setStatus(Status.ARCHIVED);
            user = userDAO.update(user);
            if (user.getRole().equals(UserRole.DRIVER)) {
                Car car = carDAO.findEntityByUserId(id);
                if (car != null) {
                    car.setStatus(Status.ARCHIVED);
                    carDAO.update(car);
                }
            }
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
        return user;
    }

    public String banUser(String userId){
        String result;
        int id = Integer.parseInt(userId);
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new DAOFactory().getUserDAO();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        User user;
        try {
            transactionManager.beginTransaction(userDAO,carDAO);
            user = userDAO.findEntityById(id);
            if (user.getStatus().equals(Status.ACTIVE)) {
                user.setStatus(Status.BANNED);
                result = "banned";
            }else {
                user.setStatus(Status.ACTIVE);
                result = "unbanned";
            }
            user = userDAO.update(user);
            if (user.getRole().equals(UserRole.DRIVER)) {
                Car car = carDAO.findEntityByUserId(id);
                if (car != null) {
                    if (user.getStatus().equals(Status.BANNED)) {
                        car.setStatus(Status.BANNED);
                    } else {
                        car.setStatus(Status.ACTIVE);
                    }
                    carDAO.update(car);
                }
            }
            transactionManager.commit();
        } catch (DAOException e) {
            result = "error";
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return result;
    }
}
