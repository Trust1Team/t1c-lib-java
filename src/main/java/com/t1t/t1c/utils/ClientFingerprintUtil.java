package com.t1t.t1c.utils;

import com.t1t.t1c.exceptions.ExceptionFactory;
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

    private static final String JLIB_AUTH_TOKEN_LOCATION = "t1c-java-lib-id-token.txt";
    private static final String ENCODING = "UTF-8";

    private ClientFingerprintUtil() {
    }

    public static String getClientFingerPrint(String fingerprintDirectoryPath) {
        File tokenFile = Paths.get(validatePath(fingerprintDirectoryPath)).toFile();
        if (tokenFile.exists()) {
            try {
                String token = FileUtils.readFileToString(tokenFile, ENCODING);
                if (validateFingerprint(token)) {
                    return token;
                } else {
                    log.warn("Deleted token file: {}", tokenFile.delete());
                }
            } catch (IOException ex) {
                log.error("Error retrieving fingerprint token: ", ex);
                log.warn("Deleted token file: {}", tokenFile.delete());
                return getClientFingerPrint(fingerprintDirectoryPath);
            }
        }
        String fingerprint = createFingerprint();
        try {
            FileUtils.writeStringToFile(tokenFile, fingerprint, ENCODING);
        } catch (IOException ex) {
            log.warn("Error writing client fingerprint token to disk: ", ex);
            throw ExceptionFactory.initializationException("Could not write fingerprint token to disk: " + ex.getMessage());
        }
        return fingerprint;
    }

    private static String validatePath(final String fingerprintDirectoryPath) {
        if (!fingerprintDirectoryPath.endsWith(String.valueOf(File.separatorChar))) {
            return fingerprintDirectoryPath + File.separatorChar + JLIB_AUTH_TOKEN_LOCATION;
        } else return fingerprintDirectoryPath + JLIB_AUTH_TOKEN_LOCATION;
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

    public static String createFingerprint() {
        String cuid = Cuid.createCuid();
        String calc = cuid.substring(1, 9).toUpperCase();
        Long decoded = Long.parseLong(calc, 36);
        return cuid + (decoded % 97);
    }

}