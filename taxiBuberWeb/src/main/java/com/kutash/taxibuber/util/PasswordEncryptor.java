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
 * The type Password encryptor.
 */
public class PasswordEncryptor {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new Password encryptor.
     *
     * @param keyString the key string
     */
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

    /**
     * The type Des secret key.
     */
    public static class DESSecretKey implements SecretKey {

        private final byte[] key;

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
     * Encrypt byte [ ].
     *
     * @param utf8 the utf 8
     * @return the byte [ ]
     */
    public byte[] encrypt(byte[] utf8) {
        try {
            byte[] enc = ecipher.doFinal(utf8);
            return Base64.encodeBase64(enc);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            LOGGER.catching(org.apache.logging.log4j.Level.ERROR, e);
        }
        return null;
    }

}
