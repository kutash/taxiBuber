package com.kutash.test.util;

import com.kutash.taxibuber.util.PasswordEncryptor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

public class PasswordEncryptorTest {

    @DataProvider(name = "data")
    public Object[][] getData() {
        return new Object[][] {
                {"petrov123@mail.ru","2222","8c1wI+T4B4o="},
                {"tolik123@mail.ru","222222","Vb6dozraIoA="},
                {"abram123@mail.ru","222222","IPJukHxB1Dg="}};
    }

    @Test(dataProvider = "data")
    public void encryptPasswordTest(Object[] data){
        String expected = new PasswordEncryptor((String)data[0]).encrypt((String)data[1]);
        String actual = (String) data[2];
        assertEquals(expected,actual);
    }
}
