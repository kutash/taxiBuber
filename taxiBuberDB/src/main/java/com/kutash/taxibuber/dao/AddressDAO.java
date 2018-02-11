package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Address dao.
 */
public class AddressDAO extends AbstractDAO<Address> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_ADDRESS_BY_ID = "SELECT id_address,address,id_user,status FROM address WHERE id_address = ?";
    private static final String FIND_ADDRESS_BY_USER_ID = "SELECT id_address, address, id_user, status FROM address WHERE status != 'ARCHIVED' AND id_user = ?";
    private static final String CREATE_ADDRESS = "INSERT INTO address (address,id_user,status) VALUES (?,?,?)";
    private static final String UPDATE_ADDRESS = "UPDATE address SET address=?,id_user=?,status=? WHERE id_address=?";

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

    /**
     * Find address by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws DAOException the dao exception
     */
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
    public int create(Address entity) throws DAOException {
        LOGGER.log(Level.INFO,"creating address");
        int result;
        ResultSet generatedKeys;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(CREATE_ADDRESS,1);
            preparedStatement = setValues(preparedStatement,entity);
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            result = generatedKeys.getInt(1);
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
            preparedStatement.setInt(4,entity.getId());
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
            String fullAddress = resultSet.getString("address");
            int userId = resultSet.getInt("id_user");
            Status status = Status.valueOf(resultSet.getString("status"));
            address = new Address(idAddress,fullAddress,userId,status);
        }catch (SQLException e){
            throw new DAOException("Exception while getting address from resultSet",e);
        }
        return address;
    }

    private PreparedStatement setValues(PreparedStatement preparedStatement, Address entity) throws DAOException {
        try {
            preparedStatement.setString(1, entity.getAddress());
            preparedStatement.setInt(2, entity.getUserId());
            preparedStatement.setString(3,entity.getStatus().name());
        }catch (SQLException e){
            throw new DAOException("Exception while set values to prepareStatement {}",e);
        }
        return preparedStatement;
    }
}
