package com.t1t.t1c.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class CertificateUtil {

    private static final Logger log = LoggerFactory.getLogger(CertificateUtil.class);

    private static final String X509 = "X.509";

    private CertificateUtil() {
    }

    public static Certificate parseCertificate(String base64EncodedCertificate) {
        try {
            byte[] certBytes = Base64.decodeBase64(base64EncodedCertificate);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(new ByteArrayInputStream(certBytes));
        } catch (CertificateException ex) {
            log.error("Failed to parse base64 encoded certificate: ", ex);
            return null;
        }
    }

}