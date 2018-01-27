package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.TripStatus;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripDAO extends AbstractDAO<Trip> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_ALL_TRIPS = "SELECT t.id_trip, t.price, t.date, t.distance, t.id_car, t.departure_address, a.address AS departure_string, t.destination_address, ad.address AS \n" +
            "destination_string,t.status,c.id_user as driver_id,concat(u.surname,' ',u.name) AS driver_name, us.id_user AS client_id,concat(us.surname,' ',us.name)\n" +
            "AS client_name FROM trip AS t INNER JOIN car AS c ON t.id_car = c.id_car INNER JOIN user AS u ON c.id_user = u.id_user INNER JOIN address AS a ON \n" +
            "t.departure_address = a.id_address INNER JOIN user AS us ON a.id_user = us.id_user INNER JOIN address AS ad ON t.destination_address = ad.id_address";
    private static final String FIND_TRIP_BY_ID = "SELECT id_trip,price,date,distance,id_car,departure_address,destination_address,status FROM trip WHERE id_trip = ?";
    private static final String FIND_TRIPS_BY_CLIENT_ID = "SELECT t.id_trip, t.price, t.date, t.distance, t.id_car, t.departure_address, a.address AS departure_string, t.destination_address, ad.address AS\n" +
            "destination_string,t.status,c.id_user as driver_id,concat(u.surname,' ',u.name) AS driver_name, us.id_user AS client_id,concat(us.surname,' ',us.name)\n" +
            "AS client_name FROM trip AS t INNER JOIN car AS c ON t.id_car = c.id_car INNER JOIN user AS u ON c.id_user = u.id_user INNER JOIN address AS a ON\n" +
            "t.departure_address = a.id_address INNER JOIN user AS us ON a.id_user = us.id_user INNER JOIN address AS ad ON t.destination_address = ad.id_address WHERE us.id_user=?";
    private static final String FIND_TRIPS_BY_DRIVER_ID = "SELECT t.id_trip, t.price, t.date, t.distance, t.id_car, t.departure_address, a.address AS departure_string, t.destination_address, ad.address AS\n" +
            "destination_string,t.status,c.id_user as driver_id,concat(u.surname,' ',u.name) AS driver_name, us.id_user AS client_id,concat(us.surname,' ',us.name)\n" +
            "AS client_name FROM trip AS t INNER JOIN car AS c ON t.id_car = c.id_car INNER JOIN user AS u ON c.id_user = u.id_user INNER JOIN address AS a ON\n" +
            "t.departure_address = a.id_address INNER JOIN user AS us ON a.id_user = us.id_user INNER JOIN address AS ad ON t.destination_address = ad.id_address WHERE u.id_user=?";
    private static final String DELETE_TRIP_BY_ID = "DELETE FROM trip WHERE id_trip = ?";
    private static final String CREATE_TRIP = "INSERT INTO trip(price,date,distance,id_car,departure_address,destination_address,status) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_TRIP = "UPDATE trip  SET price=?,date=?,distance=?,id_car=?,departure_address=?,destination_address=?, status=? WHERE id_trip=?";
    private static final String FIND_ORDERED_TRIP = "SELECT t.id_trip,t.price,t.date,t.distance,t.id_car,t.departure_address,t.destination_address,t.status FROM trip as t INNER JOIN car as c ON t.id_car = c.id_car WHERE c.id_user = ? AND t.status='ORDERED'";

    @Override
    public List<Trip> findAll() throws DAOException {
        LOGGER.log(Level.INFO,"Finding all trips");
        Statement statement = null;
        List<Trip> trips = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_TRIPS);
            while (resultSet.next()) {
                Trip trip = getTripForUser(resultSet);
                trips.add(trip);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding all trips",e);
        }finally {
            close(statement);
        }
        return trips;
    }

    public List<Trip> findByUserId(int userId, UserRole role) throws DAOException {
        LOGGER.log(Level.INFO,"Finding trips by user id");
        PreparedStatement preparedStatement = null;
        List<Trip> trips = new ArrayList<>();
        try {
            if (role.equals(UserRole.CLIENT)) {
                preparedStatement = getPreparedStatement(FIND_TRIPS_BY_CLIENT_ID);
            }else if (role.equals(UserRole.DRIVER)){
                preparedStatement = getPreparedStatement(FIND_TRIPS_BY_DRIVER_ID);
            }
            if (preparedStatement != null) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Trip trip = getTripForUser(resultSet);
                    trips.add(trip);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding trips by cli",e);
        }finally {
            close(preparedStatement);
        }
        return trips;
    }

    @Override
    public Trip findEntityById(int id) throws DAOException {
        LOGGER.log(Level.INFO,"find trip by id {}",id);
        Trip trip = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_TRIP_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trip = getTrip(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding trip by id",e);
        }finally {
            close(preparedStatement);
        }
        return trip;
    }


    public Trip findOrdered(int userId) throws DAOException {
        LOGGER.log(Level.INFO,"find trip where status=ORDERED by user id {}",userId);
        Trip trip = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_ORDERED_TRIP);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trip = getTrip(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding ORDERED trip by userId",e);
        }finally {
            close(preparedStatement);
        }
        return trip;
    }

    @Override
    public int delete(int id) throws DAOException {
        LOGGER.log(Level.INFO,"deleting trip with id {}",id);
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(DELETE_TRIP_BY_ID);
            preparedStatement.setInt(1,id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while deleting trip by id",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public int create(Trip entity) throws DAOException {
        LOGGER.log(Level.INFO,"creating trip");
        int result;
        ResultSet generatedKey;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(CREATE_TRIP,1);
            preparedStatement = setTripValues(preparedStatement,entity);
            preparedStatement.executeUpdate();
            generatedKey = preparedStatement.getGeneratedKeys();
            generatedKey.next();
            result = generatedKey.getInt(1);
        }catch (SQLException e){
            throw new DAOException("Exception while creating trip",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public Trip update(Trip entity) throws DAOException {
        LOGGER.log(Level.INFO,"updating trip");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(UPDATE_TRIP);
            preparedStatement = setTripValues(preparedStatement,entity);
            preparedStatement.setInt(8,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while updating trip",e);
        }finally {
            close(preparedStatement);
        }
        return findEntityById(entity.getId());
    }

    private PreparedStatement setTripValues(PreparedStatement preparedStatement, Trip entity) throws DAOException {
        try {
            preparedStatement.setBigDecimal(1, entity.getPrice());
            preparedStatement.setDate(2, new java.sql.Date(entity.getDate().getTime()));
            preparedStatement.setFloat(3, entity.getDistance());
            preparedStatement.setInt(4, entity.getIdCar());
            preparedStatement.setInt(5, entity.getDepartureAddress());
            preparedStatement.setInt(6, entity.getDestinationAddress());
            preparedStatement.setString(7, String.valueOf(entity.getStatus()));
        }catch (SQLException e){
            throw new DAOException("Exception while set values to prepareStatement",e);
        }
        return preparedStatement;
    }

    private Trip getTripForUser(ResultSet resultSet) throws DAOException {
        Trip trip;
        try {
            int idTrip = resultSet.getInt("id_trip");
            BigDecimal price = resultSet.getBigDecimal("price");
            java.util.Date date = resultSet.getDate("date");
            float distance = resultSet.getFloat("distance");
            int carId = resultSet.getInt("id_car");
            int departureAddress = resultSet.getInt("departure_address");
            String departure = resultSet.getString("departure_string");
            int destinationAddress = resultSet.getInt("destination_address");
            String destination = resultSet.getString("destination_string");
            TripStatus status = TripStatus.valueOf(resultSet.getString("status"));
            int driverId = resultSet.getInt("driver_id");
            String driverName = resultSet.getString("driver_name");
            int clientId = resultSet.getInt("client_id");
            String clientName = resultSet.getString("client_name");
            trip = new Trip(idTrip,price,date,distance,carId,status);
            trip.setDriverId(driverId);
            trip.setDriverName(driverName);
            trip.setClientId(clientId);
            trip.setClientName(clientName);
            trip.setDeparture(new Address(departureAddress,departure));
            trip.setDestination(new Address(destinationAddress,destination));
        }catch (SQLException e){
            throw new DAOException("Exception while getting trip from resultSet",e);
        }
        return trip;
    }

    private Trip getTrip(ResultSet resultSet) throws DAOException {
        Trip trip;
        try {
            int idTrip = resultSet.getInt("id_trip");
            BigDecimal price = resultSet.getBigDecimal("price");
            java.util.Date date = resultSet.getDate("date");
            float distance = resultSet.getFloat("distance");
            int carId = resultSet.getInt("id_car");
            int departureAddress = resultSet.getInt("departure_address");
            int destinationAddress = resultSet.getInt("destination_address");
            TripStatus status = TripStatus.valueOf(resultSet.getString("status"));
            trip = new Trip(idTrip,price,date,distance,carId,departureAddress,destinationAddress,status);
        }catch (SQLException e){
            throw new DAOException("Exception while getting trip from resultSet",e);
        }
        return trip;
    }
}
