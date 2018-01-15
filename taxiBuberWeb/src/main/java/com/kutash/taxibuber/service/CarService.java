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
                if (defineDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), car) < 40.0) {
                    nearestCars.add(car);
                }
            }
        }
        return nearestCars;
    }

    public String defineCost(String distanceStr,String durationStr,Capacity capacity){
        double distance = Double.parseDouble(distanceStr);
        double duration = Double.parseDouble(durationStr);
        double capacityCost = defineCapacityCost(capacity);
        BigDecimal cost = calculateTotalCost(distance, duration, capacityCost);
        return cost.toString();
    }

    private List<Car> findAllAvailable() {
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

    private double defineDistance(double latitude,double longitude, Car car){
        LOGGER.log(Level.INFO,"calculating distance between taxi and client");
        double latitudeClient = Math.toRadians(latitude);
        double latitudeTaxi = Math.toRadians(Double.parseDouble(car.getLatitude()));
        double longitudeClient = Math.toRadians(longitude);
        double longitudeTaxi = Math.toRadians(Double.parseDouble(car.getLongitude()));
        double cosD = Math.sin(latitudeClient)*Math.sin(latitudeTaxi)+Math.cos(latitudeClient)*Math.cos(latitudeTaxi)*Math.cos(longitudeClient-longitudeTaxi);
        return Math.acos(cosD)*6371;
    }

    private BigDecimal calculateTotalCost(double distance, double duration, double capacityCost) {
        double costPerKilometer = 0.37;
        double distanceCost = distance/1000*costPerKilometer;
        double costPerMinute = 0.05;
        double durationCost = duration/60*costPerMinute;
        double landing = 1.80;
        double coefficient = calculateCoefficient();
        double resultCost = distanceCost+durationCost+capacityCost+landing+coefficient;
        return new BigDecimal(resultCost).setScale(2, RoundingMode.UP);
    }

    private double defineCapacityCost(Capacity capacity) {
        double result = 0.0;
        switch (capacity){
            case CAR:
                result = 0.0;
                break;
            case MINIVAN:
                result = 5.0;
                break;
            case MINIBUS:
                result = 10.0;
                break;
        }
        return result;
    }

    private double calculateCoefficient() {
        int allCars = findAll().size();
        int available = findAllAvailable().size();
        int busy = allCars - available;
        double percent = busy/allCars;
        double coefficient = 0.0;
        if (percent<0.25){
            coefficient = 0.0;
        }else if (percent>=0.25 && percent<0.50){
            coefficient = 2.0;
        }else if (percent>=0.50 && percent<0.75){
            coefficient = 3.0;
        }else if (percent>=0.75){
            coefficient = 4.0;
        }
        return coefficient;
    }
}
