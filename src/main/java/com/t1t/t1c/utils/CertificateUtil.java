package com.t1t.t1c.utils;

import com.t1t.t1c.model.T1cCertificate;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

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

    public static T1cCertificate createT1cCertificate(String certificate, Boolean... parse) {
        if (StringUtils.isNotEmpty(certificate)) {
            boolean doParse = doParse(parse);
            T1cCertificate cert = new T1cCertificate().withBase64(certificate);
            if (doParse) {
                cert.setParsed(parseCertificate(certificate));
            }
            return cert;
        }
        return null;
    }

    public static List<T1cCertificate> createT1cCertificates(List<String> certificates, Boolean... parse) {
        boolean doParse = doParse(parse);
        List<T1cCertificate> returnValue = new ArrayList<>();
        for (String unparsed : certificates) {
            if (StringUtils.isNotEmpty(unparsed)) {
                T1cCertificate cert = new T1cCertificate().withBase64(unparsed);
                if (doParse) {
                    cert.setParsed(parseCertificate(unparsed));
                }
                returnValue.add(cert);
            }
        }
        return returnValue;
    }

    private static boolean doParse(Boolean... parse) {
        boolean returnValue = false;
        if (parse != null && parse.length > 0 && parse[0] != null) {
            returnValue = parse[0];
        }
        return returnValue;
    }

}