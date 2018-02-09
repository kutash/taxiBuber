package com.kutash.test.util;

import com.kutash.taxibuber.util.PasswordEncryptor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PasswordEncryptorTest {

    @DataProvider(name = "data")
    public Object[][] getData() {
        return new Object[][] {
                {"petrov123@mail.ru","2222".getBytes(),"8c1wI+T4B4o=".getBytes()},
                {"tolik123@mail.ru","222222".getBytes(),"Vb6dozraIoA=".getBytes()},
                {"abram123@mail.ru","222222".getBytes(),"IPJukHxB1Dg=".getBytes()}};
    }

    @Test(dataProvider = "data")
    public void encryptPasswordTest(Object[] data){
        byte[] expected = new PasswordEncryptor((String)data[0]).encrypt((byte[]) data[1]);
        byte[] actual = (byte[]) data[2];
        assertArrayEquals(expected,actual);
    }
}
