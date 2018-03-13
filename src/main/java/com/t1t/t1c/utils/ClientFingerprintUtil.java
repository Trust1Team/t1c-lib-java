package com.t1t.t1c.utils;

import cool.graph.cuid.Cuid;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public final class ClientFingerprintUtil {

    private static final Logger log = LoggerFactory.getLogger(ClientFingerprintUtil.class);

    private static final String JLIB_AUTH_TOKEN_LOCATION = "t1c-java-lib-id-token";
    private static final String ENCODING = "UTF-8";

    private ClientFingerprintUtil() {}

    public static void main(String[] args) {
        String fingerprint = createFingerprint();
        System.out.println("Generated token: " + fingerprint);
        System.out.println("Validate fingerprint: " + validateFingerprint(fingerprint));
    }

    public static String getClientFingerPrint() {
        File tokenFile = Paths.get(JLIB_AUTH_TOKEN_LOCATION).toFile();
        if (tokenFile.exists()) {
            try {
                String token = FileUtils.readFileToString(tokenFile,ENCODING);
                if (validateFingerprint(token)) {
                    return token;
                } else {
                    log.warn("Deleted token file: {}", tokenFile.delete());
                }
            } catch (IOException ex) {
                log.error("Error retrieving fingerprint token: ", ex);
                log.warn("Deleted token file: {}", tokenFile.delete());
                return getClientFingerPrint();
            }
        }
        String fingerprint = createFingerprint();
        try {
            FileUtils.writeStringToFile(tokenFile, fingerprint, ENCODING);
        } catch (IOException ex) {
            log.warn("Error writing client fingerprint token to disk: ", ex);
        }
        return fingerprint;
    }

    private static boolean validateFingerprint(String token) {
        Boolean rval;
        try {
            String droppedModulus = token.substring(0, token.length() - 2);
            String calc = droppedModulus.substring(1, 9);
            Long decoded = Long.parseLong(calc, 36);
            rval = token.equalsIgnoreCase(droppedModulus + (decoded % 97));
        } catch (Exception ex) {
            rval = false;
        }
        return rval;
    }

    private static String createFingerprint() {
        String cuid = Cuid.createCuid();
        String calc = cuid.substring(1, 9).toUpperCase();
        Long decoded = Long.parseLong(calc, 36);
        return cuid + (decoded % 97);
    }

}