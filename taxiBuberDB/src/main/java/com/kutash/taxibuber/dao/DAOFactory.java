package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Trip;

public class DAOFactory {

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public AddressDAO getAddressDAO() {
        return new AddressDAO();
    }

    public CommentDAO getCommentDAO() {
        return new CommentDAO();
    }

    public CarDAO getCarDAO(){
        return new CarDAO();
    }

    public TripDAO getTripDAO() { return new TripDAO(); }
}
