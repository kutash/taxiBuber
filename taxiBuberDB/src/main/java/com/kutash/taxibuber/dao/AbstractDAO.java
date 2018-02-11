package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.AbstractEntity;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * The type Abstract dao.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractDAO<T extends AbstractEntity> {

    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;

    /**
     * Set connection.
     *
     * @param connection the connection
     */
    public void setConnection(Connection connection){
        this.connection=connection;
    }

    /**
     * Find all list.
     *
     * @return the list
     * @throws DAOException the dao exception
     */
    public abstract List<T> findAll() throws DAOException;

    /**
     * Find entity by id t.
     *
     * @param id the id
     * @return the t
     * @throws DAOException the dao exception
     */
    public abstract T findEntityById(int id) throws DAOException;

    /**
     * Create int.
     *
     * @param entity the entity
     * @return the int
     * @throws DAOException the dao exception
     */
    public abstract int create(T entity) throws DAOException;

    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     * @throws DAOException the dao exception
     */
    public abstract T update(T entity) throws DAOException;

    /**
     * Gets prepared statement.
     *
     * @param sql the sql
     * @return the prepared statement
     * @throws DAOException the dao exception
     */
    PreparedStatement getPreparedStatement(String sql) throws DAOException {
        if (connection != null) {
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                throw new DAOException("Exception while creation PreparedStatement",e);
            }
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new DAOException("connection or statement is null");
    }

    /**
     * Gets prepared statement.
     *
     * @param sql the sql
     * @param i   the int
     * @return the prepared statement
     * @throws DAOException the dao exception
     */
    PreparedStatement getPreparedStatement(String sql,int i) throws DAOException {
        if (connection != null) {
            PreparedStatement statement;
            try {
                statement = connection.prepareStatement(sql,i);
            } catch (SQLException e) {
                throw new DAOException("Exception while creating Statement");
            }
            if (statement != null) {
                return statement;
            }
        }
        throw new DAOException("connection or statement is null");
    }

    /**
     * Gets statement.
     *
     * @return the statement
     * @throws DAOException the dao exception
     */
    Statement getStatement() throws DAOException {
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new DAOException("Exception while creating Statement");
            }
            if (statement != null) {
                return statement;
            }
        }
        throw new DAOException("connection or statement is null");
    }

    /**
     * Close.
     *
     * @param statement the statement
     */
    void close(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.catching(Level.ERROR,e);
            }
        }
    }

}

