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

    public Address(int id, String address) {
        super(id);
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Address address1 = (Address) o;

        if (userId != address1.userId) return false;
        if (address != null ? !address.equals(address1.address) : address1.address != null) return false;
        return status == address1.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
