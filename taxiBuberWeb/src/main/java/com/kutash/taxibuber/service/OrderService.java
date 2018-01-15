package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.AddressDAO;
import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger();

    public List<Address> findAddresses(int userId) {
        LOGGER.log(Level.INFO,"Finding addresses by user id");
        TransactionManager transactionManager = new TransactionManager();
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        List<Address> addresses = null;
        try {
            transactionManager.beginTransaction(addressDAO);
            addresses = addressDAO.findAddressByUserId(userId);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while finding addresses {}",e);
        }
        transactionManager.endTransaction();
        return addresses;
    }

    public int createAddress(Address address){
        TransactionManager transactionManager = new TransactionManager();
        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        int result = 0;
        try {
            transactionManager.beginTransaction(addressDAO);
            result = addressDAO.create(address);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.log(Level.ERROR,"Exception while making rollback",e1);
            }
            LOGGER.log(Level.ERROR,"Exception while creating address {}",e);
        }
        transactionManager.endTransaction();
        return result;
    }


    public String defineCost(String distanceStr,String durationStr,String capacity,String carId){
        Capacity carCapacity;
        if (StringUtils.isNotEmpty(carId)){
            Car car = new CarService().findById(Integer.parseInt(carId));
            carCapacity = car.getCapacity();
        } else if (StringUtils.isNotEmpty(capacity)){
            try {
                carCapacity = Capacity.valueOf(capacity);
            }catch (IllegalArgumentException e){
                LOGGER.log(Level.ERROR,"Wrong type of argument capacity",e);
                carCapacity = Capacity.CAR;
            }
        }else {
            carCapacity = Capacity.CAR;
        }
        double distance = Double.parseDouble(distanceStr);
        double duration = Double.parseDouble(durationStr);
        double capacityCost = defineCapacityCost(carCapacity);
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
        int allCars = new CarService().findAll().size();
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
