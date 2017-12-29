package com.kutash.taxibuber.entity;

public class Car extends AbstractEntity {

    private String registrationNumber;
    private Capacity capacity;
    private String model;
    private String photoPath;
    private boolean isAvailable;
    private String latitude;
    private String longitude;
    private CarBrand brand;
    private int userId;

    public Car(String registrationNumber, Capacity capacity, String model, String photoPath, boolean isAvailable, String latitude, String longitude, CarBrand brand, int userId) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.photoPath = photoPath;
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.brand = brand;
        this.userId = userId;
    }

    public Car(int id, String registrationNumber, Capacity capacity, String model, String photoPath, boolean isAvailable, String latitude, String longitude, CarBrand brand, int userId) {
        super(id);
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.photoPath = photoPath;
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.brand = brand;
        this.userId = userId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Capacity getBodyType() {
        return capacity;
    }

    public void setBodyType(Capacity bodyType) {
        this.capacity = bodyType;
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
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Car car = (Car) o;

        if (isAvailable != car.isAvailable) return false;
        if (userId != car.userId) return false;
        if (registrationNumber != null ? !registrationNumber.equals(car.registrationNumber) : car.registrationNumber != null)
            return false;
        if (capacity != car.capacity) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (photoPath != null ? !photoPath.equals(car.photoPath) : car.photoPath != null) return false;
        if (latitude != null ? !latitude.equals(car.latitude) : car.latitude != null) return false;
        if (longitude != null ? !longitude.equals(car.longitude) : car.longitude != null) return false;
        return brand != null ? brand.equals(car.brand) : car.brand == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (photoPath != null ? photoPath.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", model='" + model + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", isAvailable=" + isAvailable +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", brand=" + brand +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
