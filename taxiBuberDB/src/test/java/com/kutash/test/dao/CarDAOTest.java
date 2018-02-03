package com.kutash.test.dao;

import com.kutash.taxibuber.dao.CarDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.entity.Capacity;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.entity.CarBrand;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CarDAOTest {

    private Connection connection;
    private CarDAO carDAO;

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
        carDAO = new DAOFactory().getCarDAO();
        carDAO.setConnection(connection);
    }

    @Test(priority = 1)
    public void findAllTest() throws DAOException {
        List<Car> expected = new ArrayList<>();
        expected.add(new Car(1,"3214MA-5", Capacity.MINIVAN, "Fabia","2car.jpg",true, "53.785461", "27.598741", new CarBrand(1,"﻿ACURA") , 2,"Борис Борискин", Status.ACTIVE));
        expected.add(new Car(2,"3315HJ-7", Capacity.CAR, "E46","3car.jpg",false, "54.235984","26.698541",  new CarBrand(2,"ALFA_ROMEO"), 3,"Анатолий Моржов",Status.ACTIVE));
        expected.add(new Car(3,"7894QJ-5", Capacity.CAR, "Megan","1car.jpg",true, "55.235984","25.698541",  new CarBrand(3,"AUDI"), 1,"Петр Петров",Status.ACTIVE));
        List<Car> actual = carDAO.findAll();
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findAllAvailableTest() throws DAOException {
        List<Car> expected = new ArrayList<>();
        expected.add(new Car(1,"3214MA-5", Capacity.MINIVAN, "Fabia","2car.jpg",true, "53.785461", "27.598741", new CarBrand(1,"﻿ACURA") , 2,"Борис Борискин", Status.ACTIVE));
        expected.add(new Car(3,"7894QJ-5", Capacity.CAR, "Megan","1car.jpg",true, "55.235984","25.698541",  new CarBrand(3,"AUDI"), 1,"Петр Петров",Status.ACTIVE));
        List<Car> actual = carDAO.findAllAvailable();
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findAllAvailableByBodyTypeTest() throws DAOException {
        List<Car> expected = new ArrayList<>();
        expected.add(new Car(3,"7894QJ-5", Capacity.CAR, "Megan","1car.jpg",true, "55.235984","25.698541",  new CarBrand(3,"AUDI"), 1,"Петр Петров",Status.ACTIVE));
        List<Car> actual = carDAO.findAllAvailableByBodyType("CAR");
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findEntityByIdTest() throws DAOException {
        Car expected = new Car(2,"3315HJ-7", Capacity.CAR, "E46","3car.jpg",false, "54.235984","26.698541",  new CarBrand(2,"ALFA_ROMEO"), 3,"Анатолий Моржов",Status.ACTIVE);
        Car actual = carDAO.findEntityById(2);
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void findEntityByUserIdTest() throws DAOException {
        Car expected = new Car(2,"3315HJ-7", Capacity.CAR, "E46","3car.jpg",false, "54.235984","26.698541",  new CarBrand(2,"ALFA_ROMEO"), 3,"Анатолий Моржов",Status.ACTIVE);
        Car actual = carDAO.findEntityByUserId(3);
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void isNumberExistsNegativeTest() throws DAOException {
        assertFalse(carDAO.isNumberExists("6666OO-1"));
    }

    @Test(priority = 1)
    public void isNumberExistsTest() throws DAOException {
        assertTrue(carDAO.isNumberExists("3315HJ-7"));
    }

    @Test(priority = 1)
    public void isNumberExistsForUpdateNegativeTest() throws DAOException {
        assertFalse(carDAO.isNumberExistsForUpdate("5555HJ-7",2));
    }

    @Test(priority = 1)
    public void isNumberExistsForUpdateTest() throws DAOException {
        assertTrue(carDAO.isNumberExistsForUpdate("3214MA-5",2));
    }

    @Test
    public void createTest() throws DAOException {
        int actual = carDAO.create(new Car(0,"7894QJ-5", Capacity.CAR, "Megan","1car.jpg",true, "55.235984","25.698541",  new CarBrand(3,"AUDI"), 1,"Петр Петров",Status.ACTIVE));
        assertEquals(3,actual);
    }

    @Test
    public void updateTest() throws DAOException {
        Car expected = new Car(1,"3214MA-5", Capacity.MINIVAN, "Fabia","2car.jpg",true, "53.785461", "27.598741", new CarBrand(1,"﻿ACURA") , 2,"Борис Борискин", Status.ACTIVE);
        Car actual = carDAO.update(new Car(1,"3214MA-5", Capacity.MINIVAN, "Fabia","2car.jpg",true, "53.785461", "27.598741", new CarBrand(1,"﻿ACURA") , 2,"Борис Борискин", Status.ACTIVE));
        assertEquals(expected,actual);
    }

    @Test
    public void findAllBrandsTest() throws DAOException {
        List<CarBrand> expected = new ArrayList<>();
        expected.add(new CarBrand(1,"﻿ACURA"));
        expected.add(new CarBrand(2,"ALFA_ROMEO"));
        expected.add(new CarBrand(3,"AUDI"));
        expected.add(new CarBrand(4,"ASIA"));
        List<CarBrand> actual = carDAO.findAllBrands();
        assertEquals(expected,actual);
    }

    @Test
    public void findBrandByIdTest() throws DAOException {
        CarBrand expected = new CarBrand(1,"﻿ACURA");
        CarBrand actual = carDAO.findBrandById(1);
        assertEquals(expected,actual);
    }

    @AfterClass
    public void closeConnection() throws SQLException {
        connection.close();
    }

}
