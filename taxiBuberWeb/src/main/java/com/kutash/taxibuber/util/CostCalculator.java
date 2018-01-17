package com.kutash.taxibuber.util;

import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CostCalculator {

    private static final Logger LOGGER = LogManager.getLogger();
    private CarService carService;
    
    public CostCalculator(CarService carService){
        this.carService = carService;
    }

    public String defineCost(String distanceStr,String durationStr,String capacity,String carId){
        Capacity carCapacity;
        if (StringUtils.isNotEmpty(carId)){
            Car car = carService.findById(Integer.parseInt(carId));
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
        int allCars = carService.findAll().size();
        int available = carService.findAllAvailable().size();
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
