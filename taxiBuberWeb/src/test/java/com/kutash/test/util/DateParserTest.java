package com.kutash.test.util;

import com.kutash.taxibuber.util.DateParser;
import com.kutash.taxibuber.util.PasswordEncryptor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateParserTest {

    @DataProvider(name = "data")
    public Object[][] getData() {
        return new Object[][] {
                {"1989-05-11",new Date(610833600000L)},
                {"2005-08-17",new Date(1124222400000L)},
                {"1958-02-06",new Date(-375591600000L)}};
    }

    @Test(dataProvider = "data")
    public void parseDateTest(Object[] data){
        Date expected = (Date) data[1];
        Date actual = DateParser.parseDate((String)data[0]);
        assertEquals(expected,actual);
    }
}
