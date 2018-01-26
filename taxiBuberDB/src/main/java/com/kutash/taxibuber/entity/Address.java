package com.kutash.taxibuber.entity;

public class Address extends AbstractEntity {

    private String address;
    private int userId;
    private Status status;

    public Address(String address, int userId, Status status) {
        this.address = address;
        this.userId = userId;
        this.status = status;
    }

    public Address(int id, String address, int userId, Status status) {
        super(id);
        this.address = address;
        this.userId = userId;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
