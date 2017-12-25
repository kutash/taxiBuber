package com.kutash.test.dao;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.kutash.test.query.ListOfQueries.*;

public class CountryDAOTest {

    private Connection connection;

    @BeforeTest
    public void initDAOTest() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(UserDAOTest.class.getResourceAsStream("/dataBaseCreate.properties"));
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("serverTimezone","Europe/Moscow");
        String url = properties.getProperty("url");
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(url,properties);
        PreparedStatement createDB = connection.prepareStatement(CREATE_DB);
        createDB.executeUpdate();
        PreparedStatement useDB = connection.prepareStatement(USE_DB);
        useDB.executeUpdate();
        PreparedStatement createUser = connection.prepareStatement(CREATE_USER);
        createUser.executeUpdate();
        PreparedStatement fillUser = connection.prepareStatement(FILL_USER);
        fillUser.executeUpdate();
        PreparedStatement createCountry = connection.prepareStatement(CREATE_COUNTRY);
        createCountry.executeUpdate();
        PreparedStatement fillCountry = connection.prepareStatement(FILL_COUNTRY);
        fillCountry.executeUpdate();
        PreparedStatement createAddress = connection.prepareStatement(CREATE_ADDRESS);
        createAddress.executeUpdate();
        PreparedStatement fillAddress = connection.prepareStatement(FILL_ADDRESS);
        fillAddress.executeUpdate();
        PreparedStatement createCarBrand = connection.prepareStatement(CREATE_CAR_BRAND);
        createCarBrand.executeUpdate();
        PreparedStatement fillCarBrand = connection.prepareStatement(FILL_CAR_BRAND);
        fillCarBrand.executeUpdate();
        PreparedStatement createCar = connection.prepareStatement(CREATE_CAR);
        createCar.executeUpdate();
        PreparedStatement fillCar = connection.prepareStatement(FILL_CAR);
        fillCar.executeUpdate();
        PreparedStatement createTrip = connection.prepareStatement(CREATE_TRIP);
        createTrip.executeUpdate();
        PreparedStatement fillTrip = connection.prepareStatement(FILL_TRIP);
        fillTrip.executeUpdate();
    }

    @AfterTest
    public void dropDB() throws SQLException {
        PreparedStatement dropDB = connection.prepareStatement(DROP_DB);
        dropDB.executeUpdate();
        connection.close();
    }
}
