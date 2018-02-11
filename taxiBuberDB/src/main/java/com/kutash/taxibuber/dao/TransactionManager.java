package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.connection.ConnectionPool;
import com.kutash.taxibuber.connection.ProxyConnection;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;

/**
 * The type Transaction manager.
 */
public class TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private ProxyConnection connection;

    /**
     * Instantiates a new Transaction manager.
     */
    public TransactionManager() {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (DAOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }

    /**
     * Begin transaction.
     *
     * @param abstractDAO the abstract dao
     * @param daos        the daos
     * @throws DAOException the dao exception
     */
    public void beginTransaction(AbstractDAO abstractDAO, AbstractDAO ... daos) throws DAOException {
        LOGGER.log(Level.DEBUG,"Beginning transaction");
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

    /**
     * End transaction.
     */
    public void endTransaction(){
        LOGGER.log(Level.DEBUG,"Returning connection to the pool");
        connection.close();
    }

    /**
     * Commit.
     *
     * @throws DAOException the dao exception
     */
    public void commit() throws DAOException {
        LOGGER.log(Level.DEBUG,"Making commit");
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Exception while making commit",e);
        }
    }

    /**
     * Rollback.
     *
     * @throws DAOException the dao exception
     */
    public void rollback() throws DAOException {
        LOGGER.log(Level.DEBUG,"Making rollback");
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException("Exception while making rollback {}",e);
        }
    }
}
