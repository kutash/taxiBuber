package com.kutash.test.dao;

import com.kutash.taxibuber.dao.CommentDAO;
import com.kutash.taxibuber.dao.DAOFactory;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.exception.DAOException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.ibatis.common.jdbc.ScriptRunner;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;


public class CommentDAOTest {

    private static final String DROP_DB = "DROP DATABASE `taxi_buber_test`";
    private Connection connection;
    private CommentDAO commentDAO;

    @BeforeTest
    private void initTest() throws IOException, SQLException {
        String aSQLScriptFilePath = "F:/taxiBuber/taxiBuberDB/src/test/resources/test.sql";

        Properties properties = new Properties();
        properties.load(CommentDAOTest.class.getResourceAsStream("/dataBase.properties"));
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("serverTimezone","Europe/Moscow");
        String url = properties.getProperty("url");
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(url,properties);
        ScriptRunner sr = new ScriptRunner(connection, false, false);
        Reader reader = new BufferedReader(new FileReader(aSQLScriptFilePath));
        sr.runScript(reader);
        commentDAO = new DAOFactory().getCommentDAO();
        commentDAO.setConnection(connection);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findAllTest(){
        commentDAO.findAll();
    }

    @Test(priority = 1)
    public void findEntityByIdTest() throws DAOException {
        Comment expected = new Comment(2,"Пользуюсь этим такси уже год. Все супер, спасибо! Всегда на высоте и очень дешево. Пересадила всех своих знакомых на эту службу:)",3,4,Date.valueOf("2017-02-04"),(byte) 4,"Аркадий", "4.jpg");
        Comment actual = commentDAO.findEntityById(2);
        assertEquals(expected,actual);
    }

    @Test
    public void findEntityByUserIdTest() throws DAOException {
        List<Comment> expected = new ArrayList<>();
        expected.add(new Comment(2,"Пользуюсь этим такси уже год. Все супер, спасибо! Всегда на высоте и очень дешево. Пересадила всех своих знакомых на эту службу:)",3,4,Date.valueOf("2017-02-04"),(byte) 4,"Аркадий", "4.jpg"));
        List<Comment> actual = commentDAO.findEntityByUserId(3);
        assertEquals(expected,actual);
    }

    @Test(priority = 1)
    public void createTest() throws DAOException {
        Comment comment = new Comment(0,"Все супер!!!",3,2,Date.valueOf("2017-02-04"),(byte) 4,"","");
        assertEquals(1,commentDAO.create(comment));
    }

    @Test
    public void updateTest() throws DAOException {
        Comment expected = new Comment(4,"Спасибо вам.",5,2,Date.valueOf("2017-02-04"),(byte) 5,"Борис","2.jpg");
        Comment actual = commentDAO.update(new Comment(4,"Спасибо вам.",5,2,Date.valueOf("2017-02-04"),(byte) 5,"Борис","2.jpg"));
        assertEquals(expected,actual);
    }

    @AfterTest
    public void dropDB() throws SQLException {
        PreparedStatement dropDB = connection.prepareStatement(DROP_DB);
        dropDB.executeUpdate();
        connection.close();
    }
}
