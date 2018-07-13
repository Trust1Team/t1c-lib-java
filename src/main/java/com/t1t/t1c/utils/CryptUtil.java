package com.t1t.t1c.utils;

import com.t1t.t1c.model.T1cPublicKey;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public final class CryptUtil {

    private static final String RSA = "RSA";

    private static T1cPublicKey DEVICE_PUBLIC_KEY;

    private CryptUtil() {}

    public static void setDevicePublicKey(T1cPublicKey devicePublicKey) {
        CryptUtil.DEVICE_PUBLIC_KEY = devicePublicKey;
    }

    public static String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, DEVICE_PUBLIC_KEY.getParsed());
            return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException ex) {
            return null;
        }
    }

    public static String decrypt(String encryptedValue, String privateKey) {
        try {
            byte[] content = Base64.decodeBase64(privateKey.getBytes());
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(content);
            KeyFactory kf = KeyFactory.getInstance(RSA);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, kf.generatePrivate(spec));
            return new String(cipher.doFinal(Base64.decodeBase64(encryptedValue.getBytes())));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}