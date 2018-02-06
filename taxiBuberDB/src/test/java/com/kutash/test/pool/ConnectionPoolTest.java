package com.kutash.test.pool;

import com.kutash.taxibuber.connection.ConnectionPool;
import com.kutash.taxibuber.connection.ProxyConnection;
import com.kutash.taxibuber.exception.DAOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionPoolTest {

    private ConnectionPool connectionPool;

    @BeforeClass
    public void init() throws DAOException {
        connectionPool  = ConnectionPool.getInstance();
    }

    @Test
    public void sizeTest(){
        assertTrue(connectionPool.getPoolSize() == 10);
    }

    @Test(priority = 1)
    public void getOneConnectionTest() throws DAOException {
        ProxyConnection connection = connectionPool.getConnection();
        assertTrue(connectionPool.getPoolSize() == 9);
    }

    @Test(priority = 2)
    public void getConnectionTest(){
        ProxyConnection connection = connectionPool.getConnection();
        ProxyConnection connection1 = connectionPool.getConnection();
        assertNotEquals(connection,connection1);
    }

    @Test(priority = 3)
    public void afterDestroyTest() throws DAOException {
        connectionPool.destroyConnections();
        assertTrue(connectionPool.getPoolSize() == 0);
    }
}
