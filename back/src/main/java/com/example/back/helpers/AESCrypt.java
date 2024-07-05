package com.example.back.helpers;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESCrypt {

    private static final String ALGORITHM = "AES";
    private static final byte[] STATIC_KEY = "B374A26A71490437".getBytes(); // 16 bytes key for AES-128

    // Encrypt a string using a static key
    public static String encrypt(String strToEncrypt) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(STATIC_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt a string using a static key
    public static String decrypt(String strToDecrypt) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(STATIC_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = Base64.getDecoder().decode(strToDecrypt);
        return new String(cipher.doFinal(decryptedBytes), "UTF-8");
    }
}
