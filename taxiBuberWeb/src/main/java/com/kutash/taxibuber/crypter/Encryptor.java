package com.kutash.taxibuber.crypter;

public class Encryptor {

    public static String ecnryptPassword(String password,String email){
        String key = email.substring(0,8);
        PasswordEncryptor encryptor = new PasswordEncryptor(key.getBytes());
        return encryptor.encrypt(password);
    }
}
