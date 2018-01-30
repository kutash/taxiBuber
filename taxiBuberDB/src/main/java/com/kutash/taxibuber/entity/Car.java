package com.kutash.taxibuber.entity;

public class Car extends AbstractEntity {

    private String registrationNumber;
    private Capacity capacity;
    private String model;
    private String photoPath;
    private boolean available = false;
    private String latitude;
    private String longitude;
    private CarBrand brand;
    private int userId;
    private String driverFullName;
    private Status status;

    public Car(String registrationNumber, Capacity capacity, String model,CarBrand brand) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.brand = brand;
    }

    public Car(String registrationNumber,String model) {
        this.registrationNumber = registrationNumber;
        this.model = model;
    }

    public Car(int id, String registrationNumber, Capacity capacity, String model, String photoPath, boolean isAvailable, String latitude, String longitude, CarBrand brand, int userId, String driverFullName,Status status) {
        super(id);
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.photoPath = photoPath;
        this.available = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.brand = brand;
        this.userId = userId;
        this.driverFullName = driverFullName;
        this.status = status;
    }

    public Car(String registrationNumber, Capacity capacity, String model, String photoPath, boolean isAvailable, CarBrand brand, int userId, Status status) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.photoPath = photoPath;
        this.available = isAvailable;
        this.brand = brand;
        this.userId = userId;
        this.status = status;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public String getDriverFullName() {
        return driverFullName;
    }

    public void setDriverFullName(String driverFullName) {
        this.driverFullName = driverFullName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", model='" + model + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", isAvailable=" + available +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", brand=" + brand +
                ", userId=" + userId +
                ", driverFullName='" + driverFullName + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
