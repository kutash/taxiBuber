package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_ALL_USERS = "SELECT id_user,role,email,password,rating,`name`,surname,patronymic,birthday,photo_path,phone,status FROM user WHERE status != 'ARCHIVED'";
    private static final String FIND_USER_BY_ID = "SELECT id_user,role,email,password,rating,`name`,surname,patronymic,birthday,photo_path,phone,status FROM user WHERE id_user = ?";
    private static final String CREATE_USER = "INSERT INTO user(name,surname,patronymic,birthday,email,role,password,rating,photo_path,phone,status) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE user  SET name=?,surname=?,patronymic=?,birthday=?,email=?,role=?,password=?,rating=?,photo_path=?,phone=?,status=? WHERE id_user=?";
    private static final String FIND_USER_BY_EMAIL = "SELECT id_user,role,email,password,rating,`name`,surname,patronymic,birthday,photo_path,phone,status FROM user WHERE email = ?";
    private static final String IS_EMAIL_EXISTS = "SELECT email FROM user WHERE email = ?";

    @Override
    public List<User> findAll() throws DAOException {
        LOGGER.log(Level.INFO,"Finding all users");
        Statement statement = null;
        List<User> users = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding all users",e);
        }finally {
            close(statement);
        }
        return users;
    }

    @Override
    public User findEntityById(int id) throws DAOException {
        LOGGER.log(Level.INFO,"find user by id {}",id);
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding user by id",e);
        }finally {
            close(preparedStatement);
        }
        return user;
    }

    @Override
    public int create(User entity) throws DAOException {
        LOGGER.log(Level.INFO,"creating user");
        int result;
        ResultSet generatedKey;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(CREATE_USER,1);
            preparedStatement = setUserValues(preparedStatement,entity);
            preparedStatement.executeUpdate();
            generatedKey = preparedStatement.getGeneratedKeys();
            generatedKey.next();
            result = generatedKey.getInt(1);
        }catch (SQLException e){
            throw new DAOException("Exception while creating user",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public User update(User entity) throws DAOException {
        LOGGER.log(Level.INFO,"updating user");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(UPDATE_USER);
            preparedStatement = setUserValues(preparedStatement,entity);
            preparedStatement.setInt(12,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while updating user",e);
        }finally {
            close(preparedStatement);
        }
        return findEntityById(entity.getId());
    }

    public User findEntityByEmail(String email) throws DAOException {
        LOGGER.log(Level.INFO,"find user by email {}",email);
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            preparedStatement = getPreparedStatement(FIND_USER_BY_EMAIL);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding user by email",e);
        }finally {
            close(preparedStatement);
        }
        return user;
    }

    public boolean isEmailExists(String email) throws DAOException {
        LOGGER.log(Level.INFO,"finding is email exists {}",email);
        PreparedStatement preparedStatement = null;
        boolean result;
        try {
            preparedStatement = getPreparedStatement(IS_EMAIL_EXISTS);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        }catch (SQLException e){
            throw new DAOException("Exception while finding user by email {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    private User getUser(ResultSet resultSet) throws DAOException {
        User user;
        try {
            int idUser = resultSet.getInt("id_user");
            String firstName = resultSet.getString("name");
            String lastName = resultSet.getString("surname");
            String middleName = resultSet.getString("patronymic");
            java.util.Date birthday = resultSet.getDate("birthday");
            UserRole role = UserRole.valueOf(resultSet.getString("role"));
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String photoPath = resultSet.getString("photo_path");
            String phone = resultSet.getString("phone");
            float rating = resultSet.getFloat("rating");
            Status status = Status.valueOf(resultSet.getString("status"));
            user = new User(idUser, rating, firstName, lastName, middleName, email, password, role, birthday, photoPath,phone,status);
        }catch (SQLException e){
            throw new DAOException("Exception while getting user from resultSet",e);
        }
        return user;
    }

    private PreparedStatement setUserValues(PreparedStatement preparedStatement, User entity) throws DAOException {
        try {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getPatronymic());
            if (entity.getBirthday() == null){
                preparedStatement.setDate(4,null);
            }else {
                preparedStatement.setDate(4, new java.sql.Date(entity.getBirthday().getTime()));
            }
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getRole().name());
            preparedStatement.setString(7, entity.getPassword());
            preparedStatement.setFloat(8, entity.getRating());
            preparedStatement.setString(9, entity.getPhotoPath());
            preparedStatement.setString(10, entity.getPhone());
            preparedStatement.setString(11,entity.getStatus().name());
        }catch (SQLException e){
            throw new DAOException("Exception while set values to prepareStatement",e);
        }
        return preparedStatement;
    }
}
