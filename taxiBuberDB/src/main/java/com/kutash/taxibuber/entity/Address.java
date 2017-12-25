package com.kutash.taxibuber.entity;

public class Address extends AbstractEntity {

    private String city;
    private String street;
    private String house;
    private String flat;
    private String type;
    private String latitude;
    private String longitude;
    private Country country;
    private int userId;

    public Address(String city, String street, String house, String flat, String type, String latitude, String longitude, Country country, int userId) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.userId = userId;
    }

    public Address(int id, String city, String street, String house, String flat, String type, String latitude, String longitude, Country country, int userId) {
        super(id);
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

        Address address = (Address) o;

        if (userId != address.userId) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (house != null ? !house.equals(address.house) : address.house != null) return false;
        if (flat != null ? !flat.equals(address.flat) : address.flat != null) return false;
        if (type != null ? !type.equals(address.type) : address.type != null) return false;
        if (latitude != null ? !latitude.equals(address.latitude) : address.latitude != null) return false;
        if (longitude != null ? !longitude.equals(address.longitude) : address.longitude != null) return false;
        return country != null ? country.equals(address.country) : address.country == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (flat != null ? flat.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", type='" + type + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", country=" + country +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
