package com.kutash.taxibuber.service;

import com.kutash.taxibuber.dao.AbstractDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TransactionManager;
import com.kutash.taxibuber.dao.TripDAO;
import com.kutash.taxibuber.entity.AbstractEntity;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractService<T extends AbstractEntity> {

    private static final Logger LOGGER = LogManager.getLogger();

    public int create(T entity, AbstractDAO<T> dao){
        int result = 0;
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.beginTransaction(dao);
            result = dao.create(entity);
            transactionManager.commit();
        } catch (DAOException e) {
            try {
                transactionManager.rollback();
            } catch (DAOException e1) {
                LOGGER.catching(Level.ERROR,e1);
            }
            LOGGER.catching(Level.ERROR,e);
        }
        transactionManager.endTransaction();
        return result;
    }
}
