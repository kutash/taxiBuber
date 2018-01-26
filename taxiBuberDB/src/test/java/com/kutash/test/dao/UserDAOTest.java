package com.kutash.test.dao;

import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.dao.UserDAO;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.exception.DAOException;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static com.kutash.test.query.ListOfQueries.*;
import static org.junit.Assert.assertEquals;

public class UserDAOTest {

    /*private Connection connection;
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

    @Test
    public void findAllTest() throws DAOException {
        List<User> expected = new ArrayList<>();
        expected.add(new User(1,0.0f,"Петр","Петров","Петрович","petrov123@mail.ru","2222", UserRole.ADMIN,Date.valueOf("1989-11-05"),null,null));
        expected.add(new User(2,4.2f,"Борис","Борискин","Борисович","borisov123@mail.ru","3333",UserRole.DRIVER,Date.valueOf("2003-05-11"),null,"8(029)2851148"));
        expected.add(new User(3,4.0f,"Анатолий","Моржов","Петрович","tolik123@mail.ru","5555",UserRole.DRIVER,Date.valueOf("1995-09-08"),null,"8(029)3351148"));
        expected.add(new User(4,5.0f,"Аркадий","Абрамович","Иванович","abram123@mail.ru","6666",UserRole.CLIENT,Date.valueOf("1985-08-10"),null,"8(029)3366668"));
        expected.add(new User(5,3.2f,"Василий","Васин","Васильевич","vasya123@mail.ru","7777",UserRole.CLIENT,Date.valueOf("1969-07-15"),null,"8(029)2356489"));
        List<User> actual = userDAO.findAll();
        assertEquals("wrong data",expected,actual);
    }

    @Test
    public void findEntityByIdTest() throws DAOException {
        User expected = new User(4,5.0f,"Аркадий","Абрамович","Иванович","abram123@mail.ru","6666",UserRole.CLIENT,Date.valueOf("1985-08-10"),null,"8(029)3366668");
        User actual = userDAO.findEntityById(4);
        assertEquals("wrong data",expected,actual);
    }

    @Test(priority = 2)
    public void deleteTest() throws DAOException {
        assertEquals(userDAO.delete(6),1);
    }

    @Test(priority = 1)
    public void createTest() throws DAOException {
        User user = new User(0,0.0f,"Петр","Малашевич","Семенович","petya1234@mail.ru","3030",UserRole.DRIVER,Date.valueOf("1985-08-15"),null,"8(033)1234567");
        assertEquals(6,userDAO.create(user));
    }

    @Test(priority = 1)
    public void updateTest() throws DAOException {
        User expected = new User(2,4.7f,"Борис","Борискин","Борисович","bor123@mail.ru","55555",UserRole.DRIVER,Date.valueOf("2003-05-11"),null,"8(044)7894561");
        User actual = userDAO.update(new User(2,4.7f,"Борис","Борискин","Борисович","bor123@mail.ru","55555",UserRole.DRIVER,Date.valueOf("2003-05-11"),null,"8(044)7894561"));
        assertEquals("wrong data",expected,actual);
    }

    @AfterClass
    public void closeConnection() throws SQLException {
        connection.close();
    }*/
}
