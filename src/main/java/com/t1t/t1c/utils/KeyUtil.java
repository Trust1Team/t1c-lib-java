package com.t1t.t1c.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public final class KeyUtil {

    private KeyUtil() {}

    public static PublicKey getPublicKey(String pubKey) {
        try {
            byte[] content = Base64.decodeBase64(pubKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(content);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return null;
        }
    }

}