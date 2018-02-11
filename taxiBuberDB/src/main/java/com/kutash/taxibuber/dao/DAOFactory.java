package com.kutash.taxibuber.dao;

/**
 * The type Dao factory.
 */
public class DAOFactory {

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    /**
     * Gets address dao.
     *
     * @return the address dao
     */
    public AddressDAO getAddressDAO() {
        return new AddressDAO();
    }

    /**
     * Gets comment dao.
     *
     * @return the comment dao
     */
    public CommentDAO getCommentDAO() {
        return new CommentDAO();
    }

    /**
     * Get car dao car dao.
     *
     * @return the car dao
     */
    public CarDAO getCarDAO(){
        return new CarDAO();
    }

    /**
     * Gets trip dao.
     *
     * @return the trip dao
     */
    public TripDAO getTripDAO() { return new TripDAO(); }
}
