package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static final Logger LOGGER = LogManager.getLogger();
    private TransactionManager transactionManager;

    public CarService() {
        try {
            transactionManager = new TransactionManager();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception while creating TransactionManager {}",e);
        }
    }

    public List<Car> findAll() {
        LOGGER.log(Level.INFO,"Finding all cars");
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<Car> cars = null;
        try {
            transactionManager.beginTransaction(carDAO);
            cars = carDAO.findAll();
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while finding all cars {}",e);
        }
        transactionManager.endTransaction();
        return cars;
    }

    public List<Car> findAllAvailable(String latitude,String longitude) {
        LOGGER.log(Level.INFO,"Finding all available cars");
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<Car> cars = null;
        try {
            transactionManager.beginTransaction(carDAO);
            cars = carDAO.findAllAvailable();
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            LOGGER.log(Level.ERROR,"Exception while finding all available cars {}",e);
        }
        transactionManager.endTransaction();
        List<Car> nearestCars = new ArrayList<>();
        if (cars != null) {
            for (Car car : cars) {
                if (defineDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), car) <= 2) {
                    nearestCars.add(car);
                }
            }
        }
        return nearestCars;
    }

    private double defineDistance(double latitude,double longitude, Car car){
        LOGGER.log(Level.INFO,"calculating distance between taxi and client {}");
        double latitudeClient = Math.toRadians(latitude);
        double latitudeTaxi = Math.toRadians(Double.parseDouble(car.getLatitude()));
        double longitudeClient = Math.toRadians(longitude);
        double longitudeTaxi = Math.toRadians(Double.parseDouble(car.getLongitude()));
        double cosD = Math.sin(latitudeClient)*Math.sin(latitudeTaxi)+Math.cos(latitudeClient)*Math.cos(latitudeTaxi)*Math.cos(longitudeClient-longitudeTaxi);
        return Math.acos(cosD)*6371;
    }
}
