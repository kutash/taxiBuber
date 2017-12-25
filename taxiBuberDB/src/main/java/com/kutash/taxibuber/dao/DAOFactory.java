package com.kutash.taxibuber.dao;

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
}
