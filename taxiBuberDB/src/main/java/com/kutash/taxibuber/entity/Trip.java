package com.kutash.taxibuber.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Trip extends AbstractEntity {

    private BigDecimal price;
    private Date date;
    private float distance;
    private int idCar;
    private int departureAddress;
    private int destinationAddress;

    public Trip(BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress) {
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
    }

    public Trip(int id, BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress) {
        super(id);
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(int departureAddress) {
        this.departureAddress = departureAddress;
    }

    public int getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
