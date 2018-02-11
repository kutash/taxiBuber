package com.kutash.taxibuber.connection;

import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Connection pool.
 */
public class ConnectionPool implements Cloneable{

    private static final Logger LOGGER = LogManager.getLogger();
    private final static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static final int  DEFAULT_POOL_SIZE = 10;
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> connectionQueue;

    private ConnectionPool() throws DAOException {
        if (instance != null) {
            throw new IllegalStateException("Already instantiated");
        }
        ConnectionCreator connectionCreator = new ConnectionCreator();
        int POOL_SIZE;
        if(connectionCreator.getPoolSize() != 0){
            POOL_SIZE = connectionCreator.getPoolSize();
        }else {
            POOL_SIZE = DEFAULT_POOL_SIZE;
        }
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++){
            Connection connection = connectionCreator.getConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            connectionQueue.offer(proxyConnection);
        }
        if (connectionQueue.size() < POOL_SIZE){
            for(int i = connectionQueue.size(); i < POOL_SIZE; i++){
                Connection connection = connectionCreator.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connectionQueue.offer(proxyConnection);
            }
        }
        if (connectionQueue.size() < POOL_SIZE){
            throw new DAOException("Can not create connections");
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     * @throws DAOException the dao exception
     */
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

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR,"Exception while taking connection from pool {}",e);
        }
        return connection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    void releaseConnection(ProxyConnection connection){
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Can not enable auto-commit mode {}",e);
        }
        connectionQueue.offer(connection);
    }

    /**
     * Get pool size int.
     *
     * @return the int
     */
    public int getPoolSize(){
        return connectionQueue.size();
    }

    /**
     * Destroy connections.
     */
    public void destroyConnections(){

        while (connectionQueue.size() != 0){
            try {
                ProxyConnection connection = connectionQueue.take();
                connection.closeConnection();
            }catch (InterruptedException | SQLException e){
                LOGGER.log(Level.ERROR,"Exception while destroying connections {}",e);
            }
        }
        ConnectionCreator.deregisterDrivers();
    }


    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("Cannot clone instance of this class");
    }
}
