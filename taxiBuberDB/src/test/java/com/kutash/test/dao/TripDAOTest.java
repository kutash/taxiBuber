package com.kutash.test.dao;

import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.TripDAO;
import com.kutash.taxibuber.entity.*;
import com.kutash.taxibuber.exception.DAOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class TripDAOTest {

    private Connection connection;
    private TripDAO tripDAO;

    @BeforeClass
    public void initUserDAOTest() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(UserDAOTest.class.getResourceAsStream("/dataBaseTest.properties"));
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("serverTimezone","Europe/Moscow");
        String url = properties.getProperty("url");
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(url,properties);
        tripDAO = new DAOFactory().getTripDAO();
        tripDAO.setConnection(connection);
    }

    @Test(priority = 1)
    public void findAllTest() throws DAOException {
        List<Trip> expected = new ArrayList<>();
        expected.add(new Trip(1,new BigDecimal("15.30"), Date.valueOf("2017-01-05"), 23.2f,1, 1, 2, TripStatus.STARTED,new Address(1,"Минск Казинца 38"),new Address(2,"Минск пр. Машерова 32"),2,5,"Борискин Борис","Васин Василий"));
        expected.add(new Trip(2,new BigDecimal("22.25"), Date.valueOf("2017-02-04"), 28.5f,2, 3, 4, TripStatus.STARTED,new Address(3,"Минск Ленина 125"),new Address(4,"Минск Асаналиева 7"),3,4,"Моржов Анатолий","Абрамович Аркадий"));
        expected.add(new Trip(3,new BigDecimal("20.82"), Date.valueOf("2017-03-06"), 17.9f,1, 2, 1, TripStatus.ORDERED,new Address(2,"Минск пр. Машерова 32"),new Address(1,"Минск Казинца 38"),2,5,"Борискин Борис","Васин Василий"));
        expected.add(new Trip(4,new BigDecimal("13.00"), Date.valueOf("2017-04-07"), 13.7f,2, 4, 3, TripStatus.ORDERED,new Address(4,"Минск Асаналиева 7"),new Address(3,"Минск Ленина 125"),3,4,"Моржов Анатолий","Абрамович Аркадий"));
        List<Trip> actual = tripDAO.findAll();
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findByUserIdTest() throws DAOException {
        List<Trip> expected = new ArrayList<>();
        expected.add(new Trip(3,new BigDecimal("20.82"), Date.valueOf("2017-03-06"), 17.9f,1, 2, 1, TripStatus.ORDERED,new Address(2,"Минск пр. Машерова 32"),new Address(1,"Минск Казинца 38"),2,5,"Борискин Борис","Васин Василий"));
        expected.add(new Trip(1,new BigDecimal("15.30"), Date.valueOf("2017-01-05"), 23.2f,1, 1, 2, TripStatus.STARTED,new Address(1,"Минск Казинца 38"),new Address(2,"Минск пр. Машерова 32"),2,5,"Борискин Борис","Васин Василий"));
        List<Trip> actual = tripDAO.findByUserId(2, UserRole.DRIVER);
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findEntityById() throws DAOException {
        Trip expected = new Trip(2,new BigDecimal("22.25"), Date.valueOf("2017-02-04"), 28.5f,2, 3, 4, TripStatus.STARTED,new Address(3,"Минск Ленина 125"),new Address(4,"Минск Асаналиева 7"),3,4,"Моржов Анатолий","Абрамович Аркадий");
        Trip actual = tripDAO.findEntityById(2);
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findOrderedTest() throws DAOException {
        Trip expected = new Trip(4,new BigDecimal("13.00"), Date.valueOf("2017-04-07"), 13.7f,2, 4, 3, TripStatus.ORDERED,new Address(4,"Минск Асаналиева 7"),new Address(3,"Минск Ленина 125"));
        Trip actual = tripDAO.findOrdered(2);
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findStartedTest() throws DAOException {
        Trip expected = new Trip(1,new BigDecimal("15.30"), Date.valueOf("2017-01-05"), 23.2f,1, 1, 2, TripStatus.STARTED,new Address(1,"Минск Казинца 38"),new Address(2,"Минск пр. Машерова 32"));
        Trip actual = tripDAO.findStarted(1);
        assertEquals(expected,actual);
    }

    @Test(priority = 2)
    public void createTest() throws DAOException {
        int actual = tripDAO.create(new Trip(new BigDecimal("30.70"), Date.valueOf("2017-05-08"), 22.3f,1, 1, 2, TripStatus.ORDERED));
        assertEquals(5,actual);
    }

    @Test
    public void updateTest() throws DAOException {
        Trip expected = new Trip(3,new BigDecimal("20.82"), Date.valueOf("2017-03-06"), 17.9f,1, 2, 1, TripStatus.ORDERED,new Address(2,"Минск пр. Машерова 32"),new Address(1,"Минск Казинца 38"),2,5,"Борискин Борис","Васин Василий");
        Trip actual = tripDAO.update(new Trip(3,new BigDecimal("20.82"), Date.valueOf("2017-03-06"), 17.9f,1, 2, 1, TripStatus.ORDERED,new Address(2,"Минск пр. Машерова 32"),new Address(1,"Минск Казинца 38"),2,5,"Борискин Борис","Васин Василий"));
        assertEquals(expected,actual);
    }

    @AfterClass
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
