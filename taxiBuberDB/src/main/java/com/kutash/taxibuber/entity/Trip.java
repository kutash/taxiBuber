package com.kutash.taxibuber.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Trip.
 */
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

    /**
     * Instantiates a new Trip.
     *
     * @param price              the price
     * @param date               the date
     * @param distance           the distance
     * @param idCar              the id car
     * @param departureAddress   the departure address
     * @param destinationAddress the destination address
     * @param status             the status
     */
    public Trip(BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress, TripStatus status) {
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
    }

    /**
     * Instantiates a new Trip.
     *
     * @param id                 the id
     * @param price              the price
     * @param date               the date
     * @param distance           the distance
     * @param idCar              the id car
     * @param departureAddress   the departure address
     * @param destinationAddress the destination address
     * @param status             the status
     * @param departure          the departure
     * @param destination        the destination
     * @param driverId           the driver id
     * @param clientId           the client id
     * @param driverName         the driver name
     * @param clientName         the client name
     */
    public Trip(int id, BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress, TripStatus status, Address departure, Address destination, int driverId, int clientId, String driverName, String clientName) {
        super(id);
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
        this.departure = departure;
        this.destination = destination;
        this.driverId = driverId;
        this.clientId = clientId;
        this.driverName = driverName;
        this.clientName = clientName;
    }

    /**
     * Instantiates a new Trip.
     *
     * @param id                 the id
     * @param price              the price
     * @param date               the date
     * @param distance           the distance
     * @param idCar              the id car
     * @param departureAddress   the departure address
     * @param destinationAddress the destination address
     * @param status             the status
     * @param departure          the departure
     * @param destination        the destination
     */
    public Trip(int id, BigDecimal price, Date date, float distance, int idCar, int departureAddress, int destinationAddress, TripStatus status, Address departure, Address destination) {
        super(id);
        this.price = price;
        this.date = date;
        this.distance = distance;
        this.idCar = idCar;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
        this.departure = departure;
        this.destination = destination;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * Gets id car.
     *
     * @return the id car
     */
    public int getIdCar() {
        return idCar;
    }

    /**
     * Sets id car.
     *
     * @param idCar the id car
     */
    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    /**
     * Gets departure address.
     *
     * @return the departure address
     */
    public int getDepartureAddress() {
        return departureAddress;
    }

    /**
     * Sets departure address.
     *
     * @param departureAddress the departure address
     */
    public void setDepartureAddress(int departureAddress) {
        this.departureAddress = departureAddress;
    }

    /**
     * Gets destination address.
     *
     * @return the destination address
     */
    public int getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets destination address.
     *
     * @param destinationAddress the destination address
     */
    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public TripStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TripStatus status) {
        this.status = status;
    }

    /**
     * Gets departure.
     *
     * @return the departure
     */
    public Address getDeparture() {
        return departure;
    }

    /**
     * Sets departure.
     *
     * @param departure the departure
     */
    public void setDeparture(Address departure) {
        this.departure = departure;
    }

    /**
     * Gets destination.
     *
     * @return the destination
     */
    public Address getDestination() {
        return destination;
    }

    /**
     * Sets destination.
     *
     * @param destination the destination
     */
    public void setDestination(Address destination) {
        this.destination = destination;
    }

    /**
     * Gets driver id.
     *
     * @return the driver id
     */
    public int getDriverId() {
        return driverId;
    }

    /**
     * Sets driver id.
     *
     * @param driverId the driver id
     */
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets driver name.
     *
     * @return the driver name
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Sets driver name.
     *
     * @param driverName the driver name
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * Gets client name.
     *
     * @return the client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets client name.
     *
     * @param clientName the client name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Trip trip = (Trip) o;

        if (Float.compare(trip.distance, distance) != 0) return false;
        if (idCar != trip.idCar) return false;
        if (departureAddress != trip.departureAddress) return false;
        if (destinationAddress != trip.destinationAddress) return false;
        if (driverId != trip.driverId) return false;
        if (clientId != trip.clientId) return false;
        if (price != null ? !price.equals(trip.price) : trip.price != null) return false;
        if (date != null ? !date.equals(trip.date) : trip.date != null) return false;
        if (status != trip.status) return false;
        if (departure != null ? !departure.equals(trip.departure) : trip.departure != null) return false;
        if (destination != null ? !destination.equals(trip.destination) : trip.destination != null) return false;
        if (driverName != null ? !driverName.equals(trip.driverName) : trip.driverName != null) return false;
        if (clientName != null ? !clientName.equals(trip.clientName) : trip.clientName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result + idCar;
        result = 31 * result + departureAddress;
        result = 31 * result + destinationAddress;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + driverId;
        result = 31 * result + clientId;
        result = 31 * result + (driverName != null ? driverName.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "price=" + price +
                ", date=" + date +
                ", distance=" + distance +
                ", idCar=" + idCar +
                ", departureAddress=" + departureAddress +
                ", destinationAddress=" + destinationAddress +
                ", status=" + status +
                ", departure=" + departure +
                ", destination=" + destination +
                ", driverId=" + driverId +
                ", clientId=" + clientId +
                ", driverName='" + driverName + '\'' +
                ", clientName='" + clientName + '\'' +
                "} " + super.toString();
    }
}
