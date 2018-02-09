package com.kutash.test.dao;

import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    private Connection connection;
    private UserDAO userDAO;

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
        userDAO = new DAOFactory().getUserDAO();
        userDAO.setConnection(connection);
    }

    @Test(priority = 3)
    public void findAllTest() throws DAOException {
        List<User> expected = new ArrayList<>();
        expected.add(new User(1,0.0f,"Петр","Петров","Петрович","petrov123@mail.ru","222222".getBytes(), UserRole.DRIVER,Date.valueOf("1989-11-05"),"1.jpg","8(029)2851148", Status.ACTIVE));
        expected.add(new User(2,4.2f,"Борис","Борискин","Борисович","borisov123@mail.ru","333333".getBytes(),UserRole.DRIVER,Date.valueOf("2003-05-11"),"2.jpg","8(029)2851148", Status.ACTIVE));
        expected.add(new User(3,4.0f,"Анатолий","Моржов","Петрович","tolik123@mail.ru","555555".getBytes(),UserRole.DRIVER,Date.valueOf("1995-09-08"),"3.jpg","8(029)3351148", Status.ACTIVE));
        expected.add(new User(4,5.0f,"Аркадий","Абрамович","Иванович","abram123@mail.ru","666666".getBytes(),UserRole.CLIENT,Date.valueOf("1984-08-10"),"4.jpg","8(029)3366668", Status.ACTIVE));
        expected.add(new User(5,3.2f,"Василий","Васин","Васильевич","vasya123@mail.ru","777777".getBytes(),UserRole.CLIENT,Date.valueOf("1969-07-15"),"5.jpg","8(029)2356489", Status.ACTIVE));
        List<User> actual = userDAO.findAll();
        assertEquals(expected,actual);
    }

    @Test(priority = 2)
    public void findEntityByIdTest() throws DAOException {
        User expected = new User(4,5.0f,"Аркадий","Абрамович","Иванович","abram123@mail.ru","666666".getBytes(),UserRole.CLIENT,Date.valueOf("1984-08-10"),"4.jpg","8(029)3366668", Status.ACTIVE);
        User actual = userDAO.findEntityById(4);
        assertEquals(expected,actual);
    }

    @Test
    public void findEntityByEmailTest()throws DAOException {
        User expected = new User(4,5.0f,"Аркадий","Абрамович","Иванович","abram123@mail.ru","666666".getBytes(),UserRole.CLIENT,Date.valueOf("1984-08-10"),"4.jpg","8(029)3366668", Status.ACTIVE);
        User actual = userDAO.findEntityByEmail("abram123@mail.ru");
        assertEquals(expected,actual);
    }

    @Test
    public void isEmailExistNegativeTest()throws DAOException {
        assertFalse(userDAO.isEmailExists("kutash123@mail.ru"));
    }

    @Test
    public void isEmailExistTest()throws DAOException {
        assertTrue(userDAO.isEmailExists("abram123@mail.ru"));
    }

    @Test
    public void createTest() throws DAOException {
        User user = new User(0,3.2f,"Василий","Васин","Васильевич","vasya123@mail.ru","777777".getBytes(),UserRole.CLIENT,Date.valueOf("1969-07-15"),"5.jpg","8(029)2356489", Status.ACTIVE);
        assertEquals(5,userDAO.create(user));
    }

    @Test
    public void updateTest() throws DAOException {
        User expected = new User(2,4.2f,"Борис","Борискин","Борисович","borisov123@mail.ru","333333".getBytes(),UserRole.DRIVER,Date.valueOf("2003-05-11"),"2.jpg","8(029)2851148", Status.ACTIVE);
        User actual = userDAO.update(new User(2,4.2f,"Борис","Борискин","Борисович","borisov123@mail.ru","333333".getBytes(),UserRole.DRIVER,Date.valueOf("2003-05-11"),"2.jpg","8(029)2851148", Status.ACTIVE));
        assertEquals(expected,actual);
    }

    @AfterClass
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
