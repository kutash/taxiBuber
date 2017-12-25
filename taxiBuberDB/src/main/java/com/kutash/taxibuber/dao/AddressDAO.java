package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Country;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO extends AbstractDAO<Address> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_ADDRESS_BY_ID = "SELECT id_address,city,street,house,flat,type,latitude,longitude,id_user,c.id_country,c.`name` FROM address AS a INNER JOIN country AS c ON a.id_country = c.id_country WHERE id_address = ?";
    private static final String FIND_ADDRESS_BY_USER_ID = "SELECT id_address,city,street,house,flat,type,latitude,longitude,id_user,c.id_country,c.`name`\n" +
            "FROM address AS a INNER JOIN country AS c ON a.id_country = c.id_country WHERE a.id_user = ?";
    private static final String DELETE_ADDRESS_BY_USER_ID = "DELETE FROM address WHERE id_user = ?";
    private static final String DELETE_ADDRESS_BY_ID = "DELETE FROM address WHERE id_address = ?";
    private static final String CREATE_ADDRESS = "INSERT INTO address (city,street,house,flat,type,latitude,longitude,id_user,id_country) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ADDRESS = "UPDATE address  SET city=?,street=?,house=?,flat=?,type=?,latitude=?,longitude=?,id_user=?,id_country=? WHERE id_address=?";

    @Override
    public List<Address> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Address findEntityById(int id) throws DAOException {
        LOGGER.log(Level.INFO,"finding address by id {}",id);
        Address address = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_ADDRESS_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                address = getAddress(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding user by id {}",e);
        }finally {
            close(preparedStatement);
        }
        return address;
    }

    public List<Address> findAddressByUserId(int userId) throws DAOException {
        LOGGER.log(Level.INFO,"finding address by user id {}",userId);
        List<Address> addresses = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_ADDRESS_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Address address = getAddress(resultSet);
                addresses.add(address);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding address by user id {}",e);
        }finally {
            close(preparedStatement);
        }
        return addresses;
    }

    @Override
    public int delete(int id) throws DAOException {
        LOGGER.log(Level.INFO,"deleting addresses by id {}",id);
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(DELETE_ADDRESS_BY_ID);
            preparedStatement.setInt(1,id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while deleting address by id {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    public int deleteByUserId(int userId) throws DAOException {
        LOGGER.log(Level.INFO,"deleting addresses where id user {}",userId);
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(DELETE_ADDRESS_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while deleting address by user id {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public int create(Address entity) throws DAOException {
        LOGGER.log(Level.INFO,"creating address");
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(CREATE_ADDRESS);
            preparedStatement = setValues(preparedStatement,entity);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while creating address {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public Address update(Address entity) throws DAOException {
        LOGGER.log(Level.INFO,"updating address");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(UPDATE_ADDRESS);
            preparedStatement = setValues(preparedStatement,entity);
            preparedStatement.setInt(10,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while updating address {}",e);
        }finally {
            close(preparedStatement);
        }
        return findEntityById(entity.getId());
    }

    private Address getAddress(ResultSet resultSet) throws DAOException {
        Address address;
        try {
            int idAddress = resultSet.getInt("id_address");
            String city = resultSet.getString("city");
            String street = resultSet.getString("street");
            String house = resultSet.getString("house");
            String flat = resultSet.getString("flat");
            String type = resultSet.getString("type");
            String latitude = resultSet.getString("latitude");
            String longitude = resultSet.getString("longitude");
            Country country = new Country(resultSet.getInt("id_country"),resultSet.getString("name"));
            int userId = resultSet.getInt("id_user");
            address = new Address(idAddress,city,street,house,flat,type,latitude,longitude,country,userId);
        }catch (SQLException e){
            throw new DAOException("Exception while getting address from resultSet",e);
        }
        return address;
    }

    private PreparedStatement setValues(PreparedStatement preparedStatement, Address entity) throws DAOException {
        try {
            preparedStatement.setString(1, entity.getCity());
            preparedStatement.setString(2, entity.getStreet());
            preparedStatement.setString(3, entity.getHouse());
            preparedStatement.setString(4, entity.getFlat());
            preparedStatement.setString(5, entity.getType());
            preparedStatement.setString(6, entity.getLatitude());
            preparedStatement.setString(7, entity.getLongitude());
            preparedStatement.setInt(8, entity.getUserId());
            preparedStatement.setInt(9, entity.getCountry().getId());
        }catch (SQLException e){
            throw new DAOException("Exception while set values to prepareStatement {}",e);
        }
        return preparedStatement;
    }
}
