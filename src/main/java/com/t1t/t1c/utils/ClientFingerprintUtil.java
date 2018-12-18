package com.t1t.t1c.utils;

import com.t1t.t1c.exceptions.ExceptionFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

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

    public static String getClientFingerPrint(final String fingerprintDirectoryPath) {
        final File tokenFile = Paths.get(validatePath(fingerprintDirectoryPath)).toFile();
        if (tokenFile.exists()) {
            try {
                final String token = FileUtils.readFileToString(tokenFile, ENCODING);
                if (validateFingerprint(token)) {
                    return token;
                } else {
                    log.warn("Deleted token file: {}", tokenFile.delete());
                }
            } catch (final IOException ex) {
                log.error("Error retrieving fingerprint token: ", ex);
                log.warn("Deleted token file: {}", tokenFile.delete());
                return getClientFingerPrint(fingerprintDirectoryPath);
            }
        }
        final String fingerprint = createFingerprint();
        try {
            FileUtils.writeStringToFile(tokenFile, fingerprint, ENCODING);
        } catch (final IOException ex) {
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

    private static boolean validateFingerprint(final String token) {
        Boolean rval;
        try {
            final String resolvedToken = new String(Base64.decodeBase64(token));
            final String checkbits = resolvedToken.substring(resolvedToken.length()- 8, resolvedToken.length());
            final String initUuid = resolvedToken.substring(0, resolvedToken.length() - 9);
            final String initUuidSha = DatatypeConverter.printHexBinary(DigestUtil.sha256DigestOf(initUuid.getBytes())).toLowerCase();
            final String toBeVerified = initUuidSha.substring(initUuidSha.length() -8, initUuidSha.length());
            rval = toBeVerified.equals(checkbits);
        } catch (final Exception ex) {
            rval = false;
        }
        return rval;
    }

    private static String createFingerprint() {
        final String uuid = UUID.randomUUID().toString();
        final String sha256ClientFingerprint = DatatypeConverter.printHexBinary(DigestUtil.sha256DigestOf(uuid.getBytes()));
        final String checkBits = sha256ClientFingerprint.substring(sha256ClientFingerprint.length() - 8, sha256ClientFingerprint.length()).toLowerCase();
        return Base64.encodeBase64String((uuid + "-" + checkBits).getBytes());

    }

}