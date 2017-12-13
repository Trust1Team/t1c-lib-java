package com.t1t.t1c.utils;

import com.t1t.t1c.exceptions.CertificateOrderingException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.model.T1cCertificate;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

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

    /**
     * Order a list of certificates. The leaf certificate will always have 0 as key.
     * @param certificates list of certificates
     * @return an ordered certificate chain
     * @throws CertificateOrderingException: If multiple root or leaf certificates are deteced, making it impossible to generate a certificate chain
     */
    public static Map<Integer, T1cCertificate> orderCertificates(List<T1cCertificate> certificates) throws CertificateOrderingException {
        Map<X509Certificate, T1cCertificate> x509Map = new HashMap<>();
        List<X509Certificate> certs = new ArrayList<>();
        for (T1cCertificate cert : certificates) {
            X509Certificate x509Certificate = (X509Certificate) cert.getParsed();
            certs.add(x509Certificate);
            x509Map.put(x509Certificate, cert);
        }
        enforeceSingularRootAndLeafCerts(certs);
        Map<Integer, T1cCertificate> certificateMap = new HashMap<>();
        X509Certificate certChain = certs.get(0);
        certs.remove(certChain);
        LinkedList<X509Certificate> chainList= new LinkedList<>();
        chainList.add(certChain);
        Principal certIssuer = certChain.getIssuerDN();
        Principal certSubject = certChain.getSubjectDN();
        while(!certs.isEmpty()){
            List<X509Certificate> tempcerts = new ArrayList<>(certs);
            for (X509Certificate cert : tempcerts){
                if(cert.getIssuerDN().equals(certSubject)){
                    chainList.addFirst(cert);
                    certSubject = cert.getSubjectDN();
                    certs.remove(cert);
                    continue;
                }

                if(cert.getSubjectDN().equals(certIssuer)){
                    chainList.addLast(cert);
                    certIssuer = cert.getIssuerDN();
                    certs.remove(cert);
                    continue;
                }
            }
        }
        int i = 0;
        for (X509Certificate certificate : chainList) {
            certificateMap.put(i, x509Map.get(certificate));
            i++;
        }
        return certificateMap;
    }

    /**
     * Utility method that checks a list of certificates for multiple root or leaf certficates. If multiple are present, creating a certificate chain becomes impossible
     * @param certs list of certificates
     * @throws CertificateOrderingException: if the list contains more than 1 root certificate or more than 1 leaf certificate
     */
    public static void enforeceSingularRootAndLeafCerts(List<X509Certificate> certs) throws CertificateOrderingException {
        List<Principal> issuers = new ArrayList<>();
        List<Principal> subjects = new ArrayList<>();
        X509Certificate rootCert = null;
        for (X509Certificate cert : certs) {
            issuers.add(cert.getIssuerDN());
            subjects.add(cert.getSubjectDN());
            if (cert.getSubjectDN().equals(cert.getIssuerDN())) {
                if (rootCert != null)  {
                    throw ExceptionFactory.certificateOrderingException("Multiple root certificates detected");
                }
                else {
                    rootCert = cert;
                }
            }
        }
        X509Certificate leafCert = null;
        for (X509Certificate cert : certs) {
            if (!issuers.contains(cert.getSubjectDN())) {
                if (leafCert != null) {
                    throw ExceptionFactory.certificateOrderingException("Multiple leaf certificates detected");
                }
                else {
                    leafCert = cert;
                }
            }
        }
    }

}