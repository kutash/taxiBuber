package com.kutash.test.dao;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;
import com.ibatis.common.jdbc.ScriptRunner;
import org.testng.annotations.Test;

import static com.kutash.test.query.ListOfQueries.DROP_DB;

public class CarDAOTest {

    @BeforeTest
    private void initTest() throws IOException, SQLException {
        String aSQLScriptFilePath = "F:\\taxiBuber\\taxiBuberDB\\src\\test\\resources\\test.sql";

        Properties properties = new Properties();
        properties.load(UserDAOTest.class.getResourceAsStream("/dataBaseCreate.properties"));
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("serverTimezone","Europe/Moscow");
        String url = properties.getProperty("url");
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection connection = DriverManager.getConnection(url,properties);
        Statement stmt = null;
        try {
            // Initialize object for ScripRunner

            ScriptRunner sr = new ScriptRunner(connection, false, false);

            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));

            // Exctute script
            sr.runScript(reader);

        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }
    }

    @Test
    private void testTest(){
        System.out.println("testing");
    }

    /*@AfterTest
    public void dropDB() throws SQLException {
        PreparedStatement dropDB = connection.prepareStatement(DROP_DB);
        dropDB.executeUpdate();
        connection.close();
    }*/
}
