package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static final Logger LOGGER = LogManager.getLogger();

    public Car findById(int id) {
        LOGGER.log(Level.INFO,"Finding car id={}",id);
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        Car car = null;
        try {
            transactionManager.beginTransaction(carDAO);
            car = carDAO.findEntityById(id);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding car by id {}",e);
        }
        transactionManager.endTransaction();
        return car;
    }

    public List<Car> findAll() {
        LOGGER.log(Level.INFO,"Finding all cars");
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<Car> cars = null;
        try {
            transactionManager.beginTransaction(carDAO);
            cars = carDAO.findAll();
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding all cars {}",e);
        }
        transactionManager.endTransaction();
        return cars;
    }

    public List<Car> findAllAvailable(String latitude,String longitude,String bodyType) {
        LOGGER.log(Level.INFO,"Finding all available cars");
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<Car> cars = null;
        try {
            transactionManager.beginTransaction(carDAO);
            if (bodyType == null || StringUtils.isEmpty(bodyType)) {
                cars = carDAO.findAllAvailable();
            }else {
                cars = carDAO.findAllAvailableByBodyType(bodyType);
            }
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding all available cars {}",e);
        }
        transactionManager.endTransaction();
        List<Car> nearestCars = new ArrayList<>();
        if (cars != null) {
            for (Car car : cars) {
                double distance = defineDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), car);
                ///???????????????????????????????????????????????????????
                if ( distance < 40.0) {
                    nearestCars.add(car);
                }
            }
        }
        return nearestCars;
    }

    public List<Car> findAllAvailable() {
        LOGGER.log(Level.INFO,"Finding all available cars");
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<Car> cars = null;
        try {
            transactionManager.beginTransaction(carDAO);
            cars = carDAO.findAllAvailable();
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding all available cars {}",e);
        }
        return cars;
    }

    public Car updateCar(Car newCar){
        LOGGER.log(Level.INFO,"Updating car");
        Car car = null;
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        try {
            transactionManager.beginTransaction(carDAO);
            car = carDAO.update(newCar);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while updating car {}",e);
        }
        transactionManager.endTransaction();
        return car;
    }

    private double defineDistance(double latitude,double longitude, Car car){
        LOGGER.log(Level.INFO,"calculating distance between taxi and client");
        double latitudeClient = Math.toRadians(latitude);
        double latitudeTaxi = Math.toRadians(Double.parseDouble(car.getLatitude()));
        double longitudeClient = Math.toRadians(longitude);
        double longitudeTaxi = Math.toRadians(Double.parseDouble(car.getLongitude()));
        double cosD = Math.sin(latitudeClient)*Math.sin(latitudeTaxi)+Math.cos(latitudeClient)*Math.cos(latitudeTaxi)*Math.cos(longitudeClient-longitudeTaxi);
        return Math.acos(cosD)*6371;
    }
}
