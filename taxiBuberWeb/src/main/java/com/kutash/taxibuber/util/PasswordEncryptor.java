package com.kutash.taxibuber.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Класс для шифрования и дешифрования строк
 * Использует библиотеку Apache Codec http://commons.apache.org/codec/
 */
public class PasswordEncryptor {

    private static final Logger LOGGER = LogManager.getLogger();
    
    public PasswordEncryptor(String keyString) {
        keyString = keyString.substring(0,8);
        byte[] key = keyString.getBytes();
        try {
            updateSecretKey(new DESSecretKey(key));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private void updateSecretKey(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        ecipher = Cipher.getInstance(key.getAlgorithm());
        Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public static class DESSecretKey implements SecretKey {

        private final byte[] key;
        /**
         * ключ должен иметь длину 8 байт
         */
        private DESSecretKey(byte[] key) {
            this.key = key;
        }

        @Override
        public String getAlgorithm() {
            return "DES";
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return key;
        }
    }

    private Cipher ecipher;

    /**
     * Функция шифрования
     *
     * @param str строка открытого текста
     * @return зашифрованная строка в формате Base64
     */
    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            return Base64.encodeBase64String(enc);
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            LOGGER.catching(org.apache.logging.log4j.Level.ERROR, e);
        }
        return null;
    }

}
