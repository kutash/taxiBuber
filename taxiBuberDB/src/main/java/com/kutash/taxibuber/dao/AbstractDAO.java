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

public abstract class AbstractDAO<T extends AbstractEntity> {

    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;

    public void setConnection(Connection connection){
        this.connection=connection;
    }

    public abstract List<T> findAll() throws DAOException;

    public abstract T findEntityById(int id) throws DAOException;

    public abstract int delete(int id) throws DAOException;

    public abstract int create(T entity) throws DAOException;

    public abstract T update(T entity) throws DAOException;

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

    void close(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR,"can not close statement {}",e);
            }
        }
    }

}

