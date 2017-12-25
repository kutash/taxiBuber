package com.kutash.taxibuber.connection;

import com.kutash.taxibuber.exception.DAOException;
import com.kutash.taxibuber.resource.DBConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger();
    private final static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private final int  POOL_SIZE = Integer.parseInt(new DBConfigurationManager().getProperty("poolSize"));
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> connectionQueue;

    private ConnectionPool() throws DAOException {
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        ConnectionCreator connectionCreator = new ConnectionCreator();
        for (int i=0;i<POOL_SIZE;i++){
            Connection connection = connectionCreator.getConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            connectionQueue.offer(proxyConnection);
        }
        if (connectionQueue.size()<POOL_SIZE){
            for(int i = connectionQueue.size(); i<POOL_SIZE; i++){
                Connection connection = connectionCreator.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connectionQueue.offer(proxyConnection);
            }
        }
        if (connectionQueue.size()<POOL_SIZE){
            throw new DAOException("Can not create connections");
        }
    }

    public static ConnectionPool getInstance() throws DAOException {
        if (!instanceCreated.get()){
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR,"Exception while taking connection from pool {}",e);
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection){
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Can not enable auto-commit mode {}",e);
        }
        connectionQueue.offer(connection);
    }

    public void destroyConnections(){
        for (int i=0;i<POOL_SIZE;i++){
            try {
                ProxyConnection connection = connectionQueue.take();
                connection.closeConnection();
            }catch (InterruptedException | SQLException e){
                LOGGER.log(Level.ERROR,"Exception while destroying connections {}",e);
            }
        }
        ConnectionCreator.deregisterDrivers();
    }
}
