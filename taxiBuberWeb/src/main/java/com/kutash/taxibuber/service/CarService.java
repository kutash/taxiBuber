package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.TripDAO;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.resource.RegulationManager;
import com.kutash.taxibuber.util.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Car service.
 */
public class CarService extends AbstractService<Car>{

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SPACE = "\\s";

    /**
     * Find by id car.
     *
     * @param id the id
     * @return the car
     */
    public Car findById(int id) {
        LOGGER.log(Level.INFO,"Finding car id={}",id);
        CarDAO carDAO = new DAOFactory().getCarDAO();
        return super.findEntityById(id,carDAO);
    }

    /**
     * Find by user id car.
     *
     * @param userId the user id
     * @return the car
     */
    public Car findByUserId(int userId) {
        LOGGER.log(Level.INFO,"Finding car user id={}",userId);
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        Car car = null;
        try {
            transactionManager.beginTransaction(carDAO);
            car = carDAO.findEntityByUserId(userId);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return car;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Car> findAll() {
        LOGGER.log(Level.INFO,"Finding all cars");
        CarDAO carDAO = new DAOFactory().getCarDAO();
        return super.findAll(carDAO);
    }

    /**
     * Find all available list.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     * @param bodyType  the body type
     * @return the list
     */
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
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        List<Car> nearestCars = new ArrayList<>();
        double maxDistance = Double.parseDouble(RegulationManager.getInstance().getProperty("max_distance"));
        if (cars != null) {
            for (Car car : cars) {
                if (car.getLatitude() != null && car.getLongitude() != null) {
                    double distance = defineDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), car);
                    if (distance < maxDistance) {
                        nearestCars.add(car);
                    }
                }
            }
        }
        return nearestCars;
    }

    /**
     * Find all available list.
     *
     * @return the list
     */
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
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        return cars;
    }

    /**
     * Update car car.
     *
     * @param carData the car data
     * @param photo   the photo
     * @param carOld  the car old
     * @return the car
     */
    public Car updateCar(Map<String,String> carData, Part photo, Car carOld){
        LOGGER.log(Level.INFO,"Updating car");
        String photoPath = new FileManager().savePhoto(photo,carOld.getUserId(),true);
        String[] entityBrand = carData.get("brand").split(SPACE);
        CarBrand carBrand = new CarBrand(Integer.parseInt(entityBrand[0]), entityBrand[1]);
        carOld.setBrand(carBrand);
        carOld.setCapacity(Capacity.valueOf(carData.get("capacity")));
        carOld.setRegistrationNumber(carData.get("number"));
        carOld.setModel(carData.get("model"));
        if (StringUtils.isNotEmpty(photoPath)) {
            carOld.setPhotoPath(photoPath);
        }
        CarDAO carDAO = new DAOFactory().getCarDAO();
        return super.update(carOld,carDAO);
    }

    /**
     * Find all brands list.
     *
     * @return the list
     */
    public List<CarBrand> findAllBrands() {
        LOGGER.log(Level.INFO, "Finding all car brands");
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        List<CarBrand> brands = null;
        try {
            transactionManager.beginTransaction(carDAO);
            brands = carDAO.findAllBrands();
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return brands;
    }

    private double defineDistance(double latitude,double longitude, Car car){
        double latitudeClient = Math.toRadians(latitude);
        double latitudeTaxi = Math.toRadians(Double.parseDouble(car.getLatitude()));
        double longitudeClient = Math.toRadians(longitude);
        double longitudeTaxi = Math.toRadians(Double.parseDouble(car.getLongitude()));
        double cosD = Math.sin(latitudeClient)*Math.sin(latitudeTaxi)+Math.cos(latitudeClient)*Math.cos(latitudeTaxi)*Math.cos(longitudeClient-longitudeTaxi);
        return Math.acos(cosD)*6371;
    }

    /**
     * Find brand by id car brand.
     *
     * @param id the id
     * @return the car brand
     */
    public CarBrand findBrandById(int id) {
        LOGGER.log(Level.INFO,"Finding car brand by id={}",id);
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        CarBrand carBrand = null;
        try {
            transactionManager.beginTransaction(carDAO);
            carBrand = carDAO.findBrandById(id);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return carBrand;
    }

    /**
     * Create car int.
     *
     * @param carData the car data
     * @param photo   the photo
     * @param user    the user
     * @return the int
     */
    public int createCar(Map<String,String> carData, Part photo,User user) {
        LOGGER.log(Level.INFO,"Creating car");
        String photoPath = new FileManager().savePhoto(photo,user.getId(),true);
        String[] entityBrand = carData.get("brand").split(SPACE);
        CarBrand carBrand = new CarBrand(Integer.parseInt(entityBrand[0]), entityBrand[1]);
        Car car = new Car(carData.get("number"),Capacity.valueOf(carData.get("capacity")),carData.get("model"),photoPath,false,carBrand,user.getId(), Status.ACTIVE);
        CarDAO carDAO = new DAOFactory().getCarDAO();
        return super.create(car,carDAO);
    }

    /**
     * Is number exist boolean.
     *
     * @param number the number
     * @return the boolean
     */
    public boolean isNumberExist(String number) {
        LOGGER.log(Level.INFO,"Checking is number unique");
        TransactionManager transactionManager = new TransactionManager();
        boolean result = false;
        CarDAO carDAO = new DAOFactory().getCarDAO();
        try {
            transactionManager.beginTransaction(carDAO);
            result = carDAO.isNumberExists(number);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return result;
    }

    /**
     * Is number exist for update boolean.
     *
     * @param number the number
     * @param id     the id
     * @return the boolean
     */
    public boolean isNumberExistForUpdate(String number, int id) {
        LOGGER.log(Level.INFO,"Checking is number unique");
        TransactionManager transactionManager = new TransactionManager();
        boolean result = false;
        CarDAO carDAO = new DAOFactory().getCarDAO();
        try {
            transactionManager.beginTransaction(carDAO);
            result = carDAO.isNumberExistsForUpdate(number,id);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return result;
    }

    /**
     * Set coordinates string.
     *
     * @param carId     the car id
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the string
     */
    public String setCoordinates(String carId,String latitude,String longitude){
        String result = "";
        if (StringUtils.isNotEmpty(carId)) {
            int id = Integer.parseInt(carId);
            String latitudeRound = new BigDecimal(latitude).setScale(6, RoundingMode.UP).toString();
            String longitudeRound = new BigDecimal(longitude).setScale(6, RoundingMode.UP).toString();
            TransactionManager transactionManager = new TransactionManager();
            CarDAO carDAO = new DAOFactory().getCarDAO();
            try {
                transactionManager.beginTransaction(carDAO);
                Car car = carDAO.findEntityById(id);
                if (car != null) {
                    car.setLatitude(latitudeRound);
                    car.setLongitude(longitudeRound);
                    carDAO.update(car);
                    result = "OK";
                }
            }catch (DAOException e) {
                try {
                    transactionManager.rollback();
                } catch (DAOException e1) {
                    LOGGER.catching(Level.ERROR,e1);
                }
                LOGGER.catching(Level.ERROR,e);
            }
            transactionManager.endTransaction();
        }
        return result;
    }

    /**
     * Set available string.
     *
     * @param carId       the car id
     * @param isAvailable the is available
     * @return the string
     */
    public String setAvailable(String carId,String isAvailable){
        LOGGER.log(Level.INFO,"Set is the car available");
        String result = "";
        if (StringUtils.isEmpty(carId)){
            return "no car";
        }
        int id = Integer.parseInt(carId);
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        TripDAO tripDAO = new DAOFactory().getTripDAO();
        try {
            transactionManager.beginTransaction(carDAO,tripDAO);
            Car car = carDAO.findEntityById(id);
            if (car == null){
                return "no car";
            }
            if (Boolean.parseBoolean(isAvailable)){
                car.setAvailable(true);
                carDAO.update(car);
                result = "true";
            }else {
                Trip trip = tripDAO.findStarted(id);
                if (trip != null){
                    result = "trips";
                }else {
                    car.setAvailable(false);
                    carDAO.update(car);
                    result = "false";
                }
            }
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return result;
    }

    /**
     * Delete car car.
     *
     * @param carId the car id
     * @return the car
     */
    public Car deleteCar(String carId){
        LOGGER.log(Level.INFO,"Deleting car");
        Car car = null;
        TransactionManager transactionManager = new TransactionManager();
        CarDAO carDAO = new DAOFactory().getCarDAO();
        try {
            transactionManager.beginTransaction(carDAO);
            car = carDAO.findEntityById(Integer.parseInt(carId));
            new FileManager().deleteFile(car.getPhotoPath(),car.getUserId());
            car.setStatus(Status.ARCHIVED);
            carDAO.update(car);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return car;
    }
}
