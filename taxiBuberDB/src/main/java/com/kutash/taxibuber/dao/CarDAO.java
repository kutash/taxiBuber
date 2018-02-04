package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.CarBrand;
import com.kutash.taxibuber.entity.Status;
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
    private static final String FIND_ALL_CARS = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,c.status,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE c.status != 'ARCHIVED'";
    private static final String FIND_ALL_AVAILABLE = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,c.status,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE c.status ='ACTIVE' AND is_available=TRUE";
    private static final String FIND_AVAILABLE_BY_BODY_TYPE = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,c.status,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE c.status ='ACTIVE' AND is_available=TRUE AND capacity=?";
    private static final String FIND_CAR_BY_ID = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,c.status,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE c.status != 'ARCHIVED' AND id_car = ?";
    private static final String UPDATE_CAR = "UPDATE car SET registration_number=?,model=?,photo_path=?,is_available=?,latitude=?,longitude=?,id_brand=?,id_user=?,capacity=?,status=? WHERE id_car=?";
    private static final String FIND_CAR_BY_USER_ID = "SELECT c.id_car,c.registration_number,c.capacity,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,c.status,\n" +
            "cb.id_brand,cb.`name`,concat(u.name,' ',u.surname) AS driver_name FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand\n" +
            "INNER JOIN user AS u ON c.id_user = u.id_user WHERE c.status != 'ARCHIVED' AND c.id_user = ?";
    private static final String CREATE_CAR = "INSERT INTO car(registration_number,model,photo_path,is_available,latitude,longitude,id_brand,id_user,capacity,status) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String FIND_ALL_BRANDS = "SELECT id_brand,name FROM car_brand";
    private static final String FIND_BRAND_BY_ID = "SELECT id_brand,name FROM car_brand WHERE id_brand = ?";
    private static final String IS_NUMBER_EXISTS = "SELECT registration_number FROM car WHERE registration_number = ?";
    private static final String IS_NUMBER_EXISTS_FOR_UPDATE = "SELECT registration_number FROM car WHERE registration_number = ? AND id_car !=?";

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
    public int create(Car entity) throws DAOException {
        LOGGER.log(Level.INFO,"creating car");
        int result;
        ResultSet generatedKey;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(CREATE_CAR,1);
            preparedStatement = setCarValues(preparedStatement,entity);
            preparedStatement.executeUpdate();
            generatedKey = preparedStatement.getGeneratedKeys();
            generatedKey.next();
            result = generatedKey.getInt(1);
        }catch (SQLException e){
            throw new DAOException("Exception while creating car",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public Car update(Car entity) throws DAOException {
        LOGGER.log(Level.INFO,"updating car");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(UPDATE_CAR);
            preparedStatement = setCarValues(preparedStatement,entity);
            preparedStatement.setInt(11,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while updating car",e);
        }finally {
            close(preparedStatement);
        }
        return findEntityById(entity.getId());
    }

    public Car findEntityByUserId(int userId) throws DAOException {
        LOGGER.log(Level.INFO,"finding car by user id {}",userId);
        Car car = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_CAR_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding car by user id",e);
        }finally {
            close(preparedStatement);
        }
        return car;
    }

    public boolean isNumberExists(String number) throws DAOException {
        LOGGER.log(Level.INFO,"finding is number exists {}",number);
        PreparedStatement preparedStatement = null;
        boolean result;
        try {
            preparedStatement = getPreparedStatement(IS_NUMBER_EXISTS);
            preparedStatement.setString(1,number);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        }catch (SQLException e){
            throw new DAOException("Exception while finding is number unique {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    public boolean isNumberExistsForUpdate(String number, int id) throws DAOException {
        LOGGER.log(Level.INFO,"finding is number exists for updating car {} car id {}",number,id);
        PreparedStatement preparedStatement = null;
        boolean result;
        try {
            preparedStatement = getPreparedStatement(IS_NUMBER_EXISTS_FOR_UPDATE);
            preparedStatement.setString(1,number);
            preparedStatement.setInt(2,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        }catch (SQLException e){
            throw new DAOException("Exception while finding is number unique by car id {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    public List<CarBrand> findAllBrands() throws DAOException {
        LOGGER.log(Level.INFO,"Finding all car brands");
        Statement statement = null;
        List<CarBrand> brands = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_BRANDS);
            while (resultSet.next()) {
                CarBrand carBrand = getCarBrand(resultSet);
                brands.add(carBrand);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while finding all brands",e);
        }finally {
            close(statement);
        }
        return brands;
    }

    public CarBrand findBrandById(int id) throws DAOException {
        LOGGER.log(Level.INFO,"finding car brand by id {}",id);
        CarBrand carBrand = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_BRAND_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                carBrand = getCarBrand(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding car brand by id",e);
        }finally {
            close(preparedStatement);
        }
        return carBrand;
    }

    private PreparedStatement setCarValues(PreparedStatement preparedStatement, Car entity) throws DAOException {
        try {
            preparedStatement.setString(1, entity.getRegistrationNumber());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setString(3, entity.getPhotoPath());
            preparedStatement.setBoolean(4, entity.isAvailable());
            preparedStatement.setString(5, entity.getLatitude());
            preparedStatement.setString(6, entity.getLongitude());
            preparedStatement.setInt(7, entity.getBrand().getId());
            preparedStatement.setInt(8, entity.getUserId());
            preparedStatement.setString(9, entity.getCapacity().name());
            preparedStatement.setString(10,entity.getStatus().name());
        }catch (SQLException e){
            throw new DAOException("Exception while set values to prepareStatement",e);
        }
        return preparedStatement;
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
            Status status = Status.valueOf(resultSet.getString("status"));
            CarBrand carBrand = new CarBrand(resultSet.getInt("id_brand"),resultSet.getString("name"));
            car = new Car(idCar,number,capacity,model,photo,isAvailable,latitude,longitude,carBrand,idUser,driverName,status);
        }catch (SQLException e){
            throw new DAOException("Exception while getting car from resultSet",e);
        }
        return car;
    }

    private CarBrand getCarBrand(ResultSet resultSet) throws DAOException {
        CarBrand carBrand;
        try {
            int brandId = resultSet.getInt("id_brand");
            String name = resultSet.getString("name");
            carBrand = new CarBrand(brandId,name);
        }catch (SQLException e){
            throw new DAOException("Exception while getting car brand from resultSet",e);
        }
        return carBrand;
    }

}
