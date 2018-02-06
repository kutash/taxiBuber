package com.kutash.test.util;

import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.util.CostCalculator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

public class CostCalculatorTest {

    @DataProvider(name = "data")
    public Object[][] getData() {
        return new Object[][] {
                {"10000","600","CAR","","8.20"},
                {"25000","1200","MINIVAN","","19.75"},
                {"10000","600","MINIBUS","","18.20"}};
    }

    @Test(dataProvider = "data")
    public void defineCostTest(Object[] data){
        String expected = new CostCalculator(new CarService()).defineCost((String)data[0],(String)data[1],(String)data[2],(String)data[3]);
        String actual = (String) data[4];
        assertEquals(expected,actual);
    }
}
