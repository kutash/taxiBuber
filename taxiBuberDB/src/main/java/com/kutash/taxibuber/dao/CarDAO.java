package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.BodyType;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.CarBrand;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO extends AbstractDAO<Car> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_ALL_CARS = "SELECT c.id_car,c.registration_number,c.body_type,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,cb.id_brand,cb.`name` FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand";
    private static final String FIND_ALL_AVAILABLE = "SELECT c.id_car,c.registration_number,c.body_type,c.model,c.photo_path,c.is_available,c.latitude,c.longitude,c.id_user,cb.id_brand,cb.`name` FROM car AS c INNER JOIN car_brand AS cb ON c.id_brand = cb.id_brand WHERE is_available=TRUE";
    @Override
    public List<Car> findAll() throws DAOException {
        LOGGER.log(Level.INFO,"Finding all cars");
        PreparedStatement statement = null;
        List<Car> cars = new ArrayList<>();
        try {
            statement = getPreparedStatement(FIND_ALL_CARS);
            ResultSet resultSet = statement.executeQuery();
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
        PreparedStatement statement = null;
        List<Car> cars = new ArrayList<>();
        try {
            statement = getPreparedStatement(FIND_ALL_AVAILABLE);
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
        return null;
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
            BodyType bodyType = BodyType.valueOf(resultSet.getString("body_type"));
            String model = resultSet.getString("model");
            String photo = resultSet.getString("photo_path");
            boolean isAvailable = resultSet.getBoolean("is_available");
            String latitude = resultSet.getString("latitude");
            String longitude = resultSet.getString("longitude");
            int idUser = resultSet.getInt("id_user");
            CarBrand carBrand = new CarBrand(resultSet.getInt("id_brand"),resultSet.getString("name"));
            car = new Car(idCar,number,bodyType,model,photo,isAvailable,latitude,longitude,carBrand,idUser);
        }catch (SQLException e){
            throw new DAOException("Exception while getting user from resultSet",e);
        }
        return car;
    }
}
