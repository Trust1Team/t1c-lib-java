package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class LuxTrustContainerTest extends AbstractTestClass {

    private LuxTrustContainer container;

    @Before
    public void initContainer() {
        container = getClient().getLuxTrustContainer(new GclReader().withId(MockResponseFactory.LUXTRUST_READER_ID).withPinpad(false), "123456");
    }

    @Test
    public void getAllDataFilters() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllDataFilters()));
    }

    @Test
    public void getAllCertificateFilters() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllCertificateFilters()));
    }

    @Test
    public void getAllData() {
        LuxTrustAllData data = (LuxTrustAllData) container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getActivated());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getSigningCertificate());
        assertTrue(CollectionUtils.isNotEmpty(data.getRootCertificates()));
    }

    @Test
    public void getAllDataFiltered() {
        LuxTrustAllData data = (LuxTrustAllData) container.getAllData(Arrays.asList("activated", "root-certificates"));
        assertNotNull(data);
        assertNotNull(data.getActivated());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getSigningCertificate());
        assertTrue(CollectionUtils.isNotEmpty(data.getRootCertificates()));
    }

    @Test
    public void getAllDataParsed() {
        LuxTrustAllData data = (LuxTrustAllData) container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getActivated());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate().getParsed());
        for (T1cCertificate cert : data.getRootCertificates()) {
            assertNotNull(cert.getParsed());
        }
    }

    @Test
    public void getAllCertificates() {
        LuxTrustAllCertificates certs = (LuxTrustAllCertificates) container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertTrue(CollectionUtils.isNotEmpty(certs.getRootCertificates()));
    }

    @Test
    public void getAllCertificatesFiltered() {
        LuxTrustAllCertificates certs = (LuxTrustAllCertificates) container.getAllCertificates(Arrays.asList("signing-certificate", "authentication-certificate"));
        assertNotNull(certs);
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNull(certs.getRootCertificates());
    }

    @Test
    public void getAllCertificatesParsed() {
        LuxTrustAllCertificates certs = (LuxTrustAllCertificates) container.getAllCertificates(Arrays.asList("signing-certificate"), true);
        assertNotNull(certs);
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getSigningCertificate().getBase64());
        assertNotNull(certs.getSigningCertificate().getParsed());
    }

    @Test
    public void verifyPin() {
        assertTrue(container.verifyPin("123456"));
    }

    @Test
    public void authenticate() {
        String signedHash = container.authenticate(new GclAuthenticateOrSignData()
                .withData("ehlWXR2mz8/m04On93dZ5w==").withAlgorithmReference("sha256").withPin("1111"));
        assertNotNull(signedHash);
    }

    @Test
    public void sign() {
        String signedHash = container.sign(new GclAuthenticateOrSignData()
                .withData("ehlWXR2mz8/m04On93dZ5w==").withAlgorithmReference("sha256").withPin("1111"));
        assertNotNull(signedHash);
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.LUXTRUST, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.LUXTRUST.getId(), container.getTypeId());
    }

    @Test
    public void isActivated() {
        assertTrue(container.isActivated());
    }

    @Test
    public void getRootCertificates() throws Exception {
        List<T1cCertificate> certs = container.getRootCertificates();
        assertTrue(CollectionUtils.isNotEmpty(certs));
        for (T1cCertificate cert : certs) {
            assertNotNull(cert);
            assertNull(cert.getParsed());
        }
    }

    @Test
    public void getRootCertificatesParsed() throws Exception {
        List<T1cCertificate> certs = container.getRootCertificates(true);
        assertTrue(CollectionUtils.isNotEmpty(certs));
        for (T1cCertificate cert : certs) {
            assertNotNull(cert);
            assertNotNull(cert.getParsed());
        }
    }

    @Test
    public void getSigningCertificate() throws Exception {
        assertNotNull(container.getSigningCertificate());
    }

    @Test
    public void getSigningCertificateParsed() throws Exception {
        T1cCertificate cert = container.getSigningCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        assertNotNull(container.getAuthenticationCertificate());
    }

    @Test
    public void getAuthenticationCertificateParsed() throws Exception {
        T1cCertificate cert = container.getAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }
}