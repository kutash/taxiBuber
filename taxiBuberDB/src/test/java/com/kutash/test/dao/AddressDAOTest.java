package com.kutash.test.dao;

import com.kutash.taxibuber.dao.AddressDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.exception.DAOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class AddressDAOTest {

    private Connection connection;
    private AddressDAO addressDAO;

    @BeforeClass
    public void initAddressDAOTest() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(UserDAOTest.class.getResourceAsStream("/dataBaseTest.properties"));
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("serverTimezone","Europe/Moscow");
        String url = properties.getProperty("url");
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(url,properties);
        addressDAO = new DAOFactory().getAddressDAO();
        addressDAO.setConnection(connection);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findAllTest(){
        addressDAO.findAll();
    }

    @Test
    public void findEntityByIdTest() throws DAOException {
        Address expected = new Address(1,"Минск Казинца 38", 5, Status.ACTIVE);
        Address actual = addressDAO.findEntityById(1);
        assertEquals(expected,actual);
    }

    @Test
    public void findAddressByUserIdTest() throws DAOException {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address(1,"Минск Казинца 38", 5, Status.ACTIVE));
        expected.add(new Address(2,"Минск пр. Машерова 32", 5,Status.ACTIVE));
        List<Address> actual = addressDAO.findAddressByUserId(5);
        assertEquals(expected,actual);
    }

    @Test
    public void createTest() throws DAOException {
        Address address = new Address(0,"Минск,Кунцевщина 15",3,Status.ACTIVE);
        assertEquals(5,addressDAO.create(address));
    }

    @Test(priority = 1)
    public void updateTest() throws DAOException {
        Address expected = new Address(3,"Минск Ульяновская 123", 4,Status.ACTIVE);
        Address actual = addressDAO.update(new Address(3,"Минск Ульяновская 123", 4,Status.ACTIVE));
        assertEquals(expected,actual);
    }

    @AfterClass
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
