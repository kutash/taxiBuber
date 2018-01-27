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
    private TripStatus status;
    private Address departure;
    private Address destination;
    private int driverId;
    private int clientId;
    private String driverName;
    private String clientName;

    public Trip(BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress, TripStatus status) {
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
    }

    public Trip(int id, BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress, TripStatus status) {
        super(id);
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
    }

    public Trip(int id, BigDecimal price, Date date, float distance, int idCar, TripStatus status) {
        super(id);
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.status = status;
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

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Address getDeparture() {
        return departure;
    }

    public void setDeparture(Address departure) {
        this.departure = departure;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
