package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.CarBrand;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDAO extends AbstractDAO<Car> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_ALL_CARS = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user";
    private static final String FIND_ALL_AVAILABLE = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE is_available=TRUE";
    private static final String FIND_AVAILABLE_BY_BODY_TYPE = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE is_available=TRUE AND capacity=?";
    private static final String FIND_CAR_BY_ID = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE id_car = ?";

    @Override
    public List<Car> findAll() throws DAOException {
        LOGGER.log(Level.INFO,"Finding all cars");
        Statement statement = null;
        List<Car> cars = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_CARS);
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding all cars",e);
        }finally {
            close(statement);
        }
        return cars;
    }

    public List<Car> findAllAvailable() throws DAOException {
        LOGGER.log(Level.INFO,"Finding all available cars");
        Statement statement = null;
        List<Car> cars = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_AVAILABLE);
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding all available cars",e);
        }finally {
            close(statement);
        }
        return cars;
    }

    public List<Car> findAllAvailableByBodyType(String capacity) throws DAOException {
        LOGGER.log(Level.INFO,"Finding all available cars with body type {}",capacity);
        PreparedStatement statement = null;
        List<Car> cars = new ArrayList<>();
        try {
            statement = getPreparedStatement(FIND_AVAILABLE_BY_BODY_TYPE);
            statement.setString(1, capacity);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding all available cars",e);
        }finally {
            close(statement);
        }
        return cars;
    }

    @Override
    public Car findEntityById(int id) throws DAOException {
        LOGGER.log(Level.INFO,"finding car by id {}",id);
        Car car = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_CAR_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding car by id",e);
        }finally {
            close(preparedStatement);
        }
        return car;
    }

    @Override
    public int delete(int id) throws DAOException {
        return 0;
    }

    @Override
    public int create(Car entity) throws DAOException {
        return 0;
    }

    @Override
    public Car update(Car entity) throws DAOException {
        return null;
    }

    private Car getCar(ResultSet resultSet) throws DAOException {
        Car car;
        try {
            int idCar = resultSet.getInt("id_car");
            String number = resultSet.getString("registration_number");
            Capacity capacity = Capacity.valueOf(resultSet.getString("capacity"));
            String model = resultSet.getString("model");
            String photo = resultSet.getString("photo_path");
            boolean isAvailable = resultSet.getBoolean("is_available");
            String latitude = resultSet.getString("latitude");
            String longitude = resultSet.getString("longitude");
            int idUser = resultSet.getInt("id_user");
            String driverName = resultSet.getString("driver_name");
            CarBrand carBrand = new CarBrand(resultSet.getInt("id_brand"),resultSet.getString("name"));
            car = new Car(idCar,number,capacity,model,photo,isAvailable,latitude,longitude,carBrand,idUser,driverName);
        }catch (SQLException e){
            throw new DAOException("Exception while getting user from resultSet",e);
        }
        return car;
    }
}
