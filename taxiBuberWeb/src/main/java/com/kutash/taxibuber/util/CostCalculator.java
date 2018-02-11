package com.kutash.taxibuber.util;

import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.resource.RegulationManager;
import com.kutash.taxibuber.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The type Cost calculator.
 */
public class CostCalculator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int METER_IN_KILOMETER = 1000;
    private static final int SECOND_IN_MINUTE = 60;
    private static final double FOURTH = 0.25;
    private static final double HALF = 0.50;
    private static final double THREE_QUARTERS = 0.75;
    private CarService carService;

    /**
     * Instantiates a new Cost calculator.
     *
     * @param carService the car service
     */
    public CostCalculator(CarService carService){
        this.carService = carService;
    }

    /**
     * Define cost string.
     *
     * @param distanceStr the distance str
     * @param durationStr the duration str
     * @param capacity    the capacity
     * @param carId       the car id
     * @return the string
     */
    public String defineCost(String distanceStr,String durationStr,String capacity,String carId){
        Capacity carCapacity;
        if (StringUtils.isNotEmpty(carId)){
            Car car = carService.findById(Integer.parseInt(carId));
            carCapacity = car.getCapacity();
        } else if (StringUtils.isNotEmpty(capacity)){
            try {
                carCapacity = Capacity.valueOf(capacity);
            }catch (IllegalArgumentException e){
                LOGGER.catching(Level.ERROR, e);
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
        double costPerKilometer = Double.parseDouble(RegulationManager.getInstance().getProperty("cost_kilometer"));
        double distanceCost = distance / METER_IN_KILOMETER * costPerKilometer;
        double costPerMinute = Double.parseDouble(RegulationManager.getInstance().getProperty("cost_minute"));
        double durationCost = duration / SECOND_IN_MINUTE * costPerMinute;
        double landing = Double.parseDouble(RegulationManager.getInstance().getProperty("cost_landing"));
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
                result = Double.parseDouble(RegulationManager.getInstance().getProperty("minivan_increase"));
                break;
            case MINIBUS:
                result = Double.parseDouble(RegulationManager.getInstance().getProperty("minibus_increase"));
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
        if (percent<FOURTH){
            coefficient = 0.0;
        }else if (percent>=FOURTH && percent<HALF){
            coefficient = Double.parseDouble(RegulationManager.getInstance().getProperty("fourth_half"));
        }else if (percent>=HALF && percent<THREE_QUARTERS){
            coefficient = Double.parseDouble(RegulationManager.getInstance().getProperty("half_three_quarters"));
        }else if (percent>=THREE_QUARTERS){
            coefficient = Double.parseDouble(RegulationManager.getInstance().getProperty("after_three_quarters"));
        }
        return coefficient;
    }
}
