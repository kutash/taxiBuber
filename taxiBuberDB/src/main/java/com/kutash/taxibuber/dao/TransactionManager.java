package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.connection.ConnectionPool;
import com.kutash.taxibuber.connection.ProxyConnection;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private ProxyConnection connection;

    public TransactionManager() {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception while taking connection from the pool",e);
        }
    }

    public void beginTransaction(AbstractDAO abstractDAO, AbstractDAO ... daos) throws DAOException {
        LOGGER.log(Level.INFO,"Beginning transaction");
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DAOException("Can not disable auto-commit mode",e);
        }
        abstractDAO.setConnection(connection);
        for (AbstractDAO dao : daos){
            dao.setConnection(connection);
        }
    }

    public void endTransaction(){
        LOGGER.log(Level.INFO,"Returning connection to the pool");
        try {
            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR,"Exception while releasing connection {}",e);
        }
    }

    public void commit() throws DAOException {
        LOGGER.log(Level.INFO,"Making commit");
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Exception while making commit",e);
        }
    }

    public void rollback() throws DAOException {
        LOGGER.log(Level.INFO,"Making rollback");
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException("Exception while making rollback {}",e);//????????????????????????????
        }
    }
}
