package com.kutash.taxibuber.entity;

public class Address extends AbstractEntity {

    private String address;
    private int userId;

    public Address(String address, int userId) {
        this.address = address;
        this.userId = userId;
    }

    public Address(int id, String address, int userId) {
        super(id);
        this.address = address;
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
