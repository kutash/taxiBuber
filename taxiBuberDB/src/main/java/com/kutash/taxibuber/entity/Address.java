package com.kutash.taxibuber.entity;

/**
 * The type Address.
 */
public class Address extends AbstractEntity {

    private String address;
    private int userId;
    private Status status;

    /**
     * Instantiates a new Address.
     *
     * @param address the address
     * @param userId  the user id
     * @param status  the status
     */
    public Address(String address, int userId, Status status) {
        this.address = address;
        this.userId = userId;
        this.status = status;
    }

    /**
     * Instantiates a new Address.
     *
     * @param id      the id
     * @param address the address
     * @param userId  the user id
     * @param status  the status
     */
    public Address(int id, String address, int userId, Status status) {
        super(id);
        this.address = address;
        this.userId = userId;
        this.status = status;
    }

    /**
     * Instantiates a new Address.
     *
     * @param id      the id
     * @param address the address
     */
    public Address(int id, String address) {
        super(id);
        this.address = address;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                "} " + super.toString();
    }
}
