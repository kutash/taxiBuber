package com.kutash.test.util;

import com.kutash.taxibuber.util.Validator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    @DataProvider(name = "data")
    public Object[][] getData() {
        HashMap<String,String> data1 = new HashMap<>();
        data1.put("name","");
        data1.put("surname","==");
        data1.put("patronymic","55");
        data1.put("phone","fjgk");
        data1.put("birthday","23.25.1989");
        data1.put("role","7895");
        data1.put("email","oyur#$^&");
        data1.put("password","128");
        data1.put("passwordConfirm","jhkj");
        HashMap<String,String> expected1 = new HashMap<>();
        expected1.put("nameBlank","Это поле не может быть пустым.");
        expected1.put("surname","Минимум 3 максимум 45 символов, только буквы");
        expected1.put("patronymic","Минимум 3 максимум 45 символов, только буквы");
        expected1.put("date","Неверный формат даты");
        expected1.put("email","Неверный формат!");
        expected1.put("phone","Неверный номер телефона");
        expected1.put("role","Выберите роль");
        expected1.put("password","Допустимые символы:буквы,цифры,_,-,минимум 6 максимум 40");
        expected1.put("passwordConfirm","Пароль не совпадает");
        HashMap<String,String> data2 = new HashMap<>();
        data2.put("name","");
        data2.put("surname","==");
        data2.put("phone","fjgk");
        data2.put("birthday","23.25.1989");
        data2.put("role","7895");
        data2.put("email","oyur#$^&");
        data2.put("password","22222222");
        data2.put("passwordConfirm","jhkj");
        HashMap<String,String> expected2 = new HashMap<>();
        expected2.put("nameBlank","This field cannot be blank.");
        expected2.put("surname","At least 3 maximum 45 characters,only letters");
        expected2.put("date","Invalid date format");
        expected2.put("email","Invalid email!");
        expected2.put("phone","Invalid phone number");
        expected2.put("role","Choose the right role");
        expected2.put("passwordConfirm","Password doesn't match");
        return new Object[][] {
                {expected1,data1,"ru"},
                {expected2,data2,"en"}};
    }

    @Test(dataProvider = "data")
    public void validateUserTest(Object[] data){
        HashMap<String,String> expected = (HashMap<String, String>) data[0];
        HashMap<String,String> actual = new Validator().validateUser((HashMap<String, String>) data[1], (String) data[2]);
        assertEquals(expected,actual);
    }

    @DataProvider(name = "data2")
    public Object[][] getDataForCheckPassword() {
        HashMap<String,String> expected1 = new HashMap<>();
        expected1.put("password","Допустимые символы:буквы,цифры,_,-,минимум 6 максимум 40");
        HashMap<String,String> expected2 = new HashMap<>();
        expected2.put("passwordConfirm","Пароль не совпадает");
        HashMap<String,String> expected3 = new HashMap<>();
        expected3.put("password","Only letters, numbers, _, -, minimum 6 maximum 40");
        HashMap<String,String> expected4 = new HashMap<>();
        expected4.put("passwordConfirm","Password doesn't match");
        return new Object[][] {
                {expected1,"1y2u".getBytes(),"1y2u".getBytes(),"ru"},
                {expected2,"123qwe".getBytes(),"Vb6dozr".getBytes(),"ru"},
                {expected3,"1y2u".getBytes(),"1y2u".getBytes(),"en"},
                {expected4,"123qwe".getBytes(),"Vb6dozr".getBytes(),"en"}};
    }

    @Test(dataProvider = "data2")
    public void checkPasswordTest(Object[] data){
        HashMap<String,String> expected = (HashMap<String, String>) data[0];
        HashMap<String,String> actual = new Validator().checkPassword((byte[]) data[1], (byte[]) data[2],(String) data[3]);
        assertEquals(expected,actual);
    }

    @DataProvider(name = "data3")
    public Object[][] getDataForValidateCar() {
        HashMap<String,String> data1 = new HashMap<>();
        data1.put("number","12");
        data1.put("model","");
        data1.put("capacity","55");
        data1.put("brand","fjgk");
        HashMap<String,String> expected1 = new HashMap<>();
        expected1.put("number","Enter valid number");
        expected1.put("modelBlank","This field cannot be blank.");
        expected1.put("capacity","Choose valid capacity");
        expected1.put("brand","Choose valid brand");
        HashMap<String,String> data2 = new HashMap<>();
        data2.put("number","12");
        data2.put("model","");
        data2.put("capacity","55");
        data2.put("brand","fjgk");
        HashMap<String,String> expected2 = new HashMap<>();
        expected2.put("number","Введите действительный номер");
        expected2.put("modelBlank","Это поле не может быть пустым.");
        expected2.put("capacity","Выберите вместимость");
        expected2.put("brand","Выберите марку");
        return new Object[][] {
                {expected1,data1,"en"},
                {expected2,data2,"ru"}};
    }

    @Test(dataProvider = "data3")
    public void validateCarTest(Object[] data){
        HashMap<String,String> expected = (HashMap<String, String>) data[0];
        HashMap<String,String> actual = new Validator().validateCar((HashMap<String, String>) data[1], (String) data[2]);
        assertEquals(expected,actual);
    }

    @DataProvider(name = "data4")
    public Object[][] getDataForValidateOrder() {
        HashMap<String,String> data1 = new HashMap<>();
        data1.put("carId","");
        data1.put("distance","");
        data1.put("duration","");
        data1.put("destination","");
        data1.put("source","");
        HashMap<String,String> expected1 = new HashMap<>();
        expected1.put("emptyCar","No cars found for your request, please wait a few minutes");
        expected1.put("distance","Incorrect order parameters");
        expected1.put("sourceError","Specify the point of departure");
        expected1.put("destError","Specify destination");
        HashMap<String,String> data2 = new HashMap<>();
        data2.put("carId","");
        data2.put("distance","");
        data2.put("duration","");
        data2.put("destination","");
        data2.put("source","");
        HashMap<String,String> expected2 = new HashMap<>();
        expected2.put("emptyCar","По вашему запросу машин не найдено, подождите пару минут");
        expected2.put("distance","Неверные параметры заказа");
        expected2.put("sourceError","Укажите пункт отправления");
        expected2.put("destError","Укажиет место назначения");
        return new Object[][] {
                {expected1,data1,"en"},
                {expected2,data2,"ru"}};
    }

    @Test(dataProvider = "data4")
    public void validateOrderTest(Object[] data){
        HashMap<String,String> expected = (HashMap<String, String>) data[0];
        HashMap<String,String> actual = new Validator().validateOrder((HashMap<String, String>) data[1], (String) data[2]);
        assertEquals(expected,actual);
    }
}
