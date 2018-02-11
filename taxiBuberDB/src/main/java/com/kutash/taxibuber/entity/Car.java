package com.kutash.taxibuber.entity;

/**
 * The type Car.
 */
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

    /**
     * Instantiates a new Car.
     *
     * @param registrationNumber the registration number
     * @param model              the model
     */
    public Car(String registrationNumber,String model) {
        this.registrationNumber = registrationNumber;
        this.model = model;
    }

    /**
     * Instantiates a new Car.
     *
     * @param id                 the id
     * @param registrationNumber the registration number
     * @param capacity           the capacity
     * @param model              the model
     * @param photoPath          the photo path
     * @param isAvailable        the is available
     * @param latitude           the latitude
     * @param longitude          the longitude
     * @param brand              the brand
     * @param userId             the user id
     * @param driverFullName     the driver full name
     * @param status             the status
     */
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

    /**
     * Instantiates a new Car.
     *
     * @param registrationNumber the registration number
     * @param capacity           the capacity
     * @param model              the model
     * @param photoPath          the photo path
     * @param isAvailable        the is available
     * @param brand              the brand
     * @param userId             the user id
     * @param status             the status
     */
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

    /**
     * Gets registration number.
     *
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets registration number.
     *
     * @param registrationNumber the registration number
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets photo path.
     *
     * @return the photo path
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Sets photo path.
     *
     * @param photoPath the photo path
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Is available boolean.
     *
     * @return the boolean
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets available.
     *
     * @param available the available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public CarBrand getBrand() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public Capacity getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets driver full name.
     *
     * @return the driver full name
     */
    public String getDriverFullName() {
        return driverFullName;
    }

    /**
     * Sets driver full name.
     *
     * @param driverFullName the driver full name
     */
    public void setDriverFullName(String driverFullName) {
        this.driverFullName = driverFullName;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Car car = (Car) o;

        if (available != car.available) return false;
        if (userId != car.userId) return false;
        if (registrationNumber != null ? !registrationNumber.equals(car.registrationNumber) : car.registrationNumber != null)
            return false;
        if (capacity != car.capacity) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (photoPath != null ? !photoPath.equals(car.photoPath) : car.photoPath != null) return false;
        if (latitude != null ? !latitude.equals(car.latitude) : car.latitude != null) return false;
        if (longitude != null ? !longitude.equals(car.longitude) : car.longitude != null) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        if (driverFullName != null ? !driverFullName.equals(car.driverFullName) : car.driverFullName != null)
            return false;
        return status == car.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (photoPath != null ? photoPath.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (driverFullName != null ? driverFullName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
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
