package com.kutash.test.dao;

import com.kutash.taxibuber.dao.AddressDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Country;
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
        Address expected = new Address(1,"Минск","Казинца","38","176","HOME","236548971","125879463",new Country(1,"Belarus"),4);
        Address actual = addressDAO.findEntityById(1);
        assertEquals("wrong data",expected,actual);
    }

    @Test
    public void findAddressByUserIdTest() throws DAOException {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address(1,"Минск","Казинца","38","176","HOME","236548971","125879463",new Country(1,"Belarus"),4));
        expected.add(new Address(2,"Минск","пр. Машерова","32","567","WORK","236848971","175879463", new Country(1,"Belarus"),4));
        List<Address> actual = addressDAO.findAddressByUserId(4);
        assertEquals("wrong data",expected,actual);
    }

    @Test(priority = 2)
    public void deleteTest() throws DAOException {
        assertEquals(1,addressDAO.delete(7));
    }

    @Test(priority = 1)
    public void createTest() throws DAOException {
        Address address = new Address("Минск","Кунцевщина","15","122","HOME","123456789","123456789",new Country(1,"Belarus"),3);
        assertEquals(1,addressDAO.create(address));
    }

    @Test(priority = 1)
    public void updateTest() throws DAOException {
        Address expected = new Address(2,"Минск","пр. Независимости","35","560","WORK","236848971","175879463", new Country(1,"Belarus"),4);
        Address actual = addressDAO.update(new Address(2,"Минск","пр. Независимости","35","560","WORK","236848971","175879463", new Country(1,"Belarus"),4));
        assertEquals("wrong data",expected,actual);
    }

    @Test
    public void deleteByUserIdTest() throws DAOException {
        assertEquals(2,addressDAO.deleteByUserId(1));
    }

    @AfterClass
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
