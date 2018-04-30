package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.DigestAlgorithm;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        container = getClient().getLuxTrustContainer(new GclReader().withId(MockResponseFactory.LUXTRUST_READER_ID).withPinpad(true));
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
        LuxTrustAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getActivated());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getSigningCertificate());
        assertTrue(CollectionUtils.isNotEmpty(data.getRootCertificates()));
    }

    @Test
    public void getAllDataFiltered() {
        LuxTrustAllData data = container.getAllData(Arrays.asList("activated", "root-certificates"));
        assertNotNull(data);
        assertNotNull(data.getActivated());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getSigningCertificate());
        assertTrue(CollectionUtils.isNotEmpty(data.getRootCertificates()));
    }

    @Test
    public void getAllDataParsed() {
        LuxTrustAllData data = container.getAllData(true);
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
        LuxTrustAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertTrue(CollectionUtils.isNotEmpty(certs.getRootCertificates()));
    }

    @Test
    public void getAllCertificatesFiltered() {
        LuxTrustAllCertificates certs = container.getAllCertificates(Arrays.asList("signing-certificate", "authentication-certificate"));
        assertNotNull(certs);
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNull(certs.getRootCertificates());
    }

    @Test
    public void getAllCertificatesParsed() {
        LuxTrustAllCertificates certs = container.getAllCertificates(Arrays.asList("signing-certificate"), true);
        assertNotNull(certs);
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getSigningCertificate().getBase64());
        assertNotNull(certs.getSigningCertificate().getParsed());
    }

    @Test
    public void verifyPinWithHardwarePinPad() {
        assertTrue(container.verifyPin());
    }

    @Test
    public void authenticateWithHardwarePinPad() {
        assertNotNull(container.authenticate("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256));
    }

    @Test
    public void signWithHardwarePinPad() {
        assertNotNull(container.sign("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256));
    }

    @Test
    public void verifyPin() {
        assertTrue(container.verifyPin("1111"));
    }

    @Test(expected = VerifyPinException.class)
    public void verifyPinIncorrect() {
        container.verifyPin("1112");
    }

    @Test
    public void authenticate() {
        String authenticatedHash = container.authenticate("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256, "1111");
        assertNotNull(authenticatedHash);
    }

    @Test(expected = VerifyPinException.class)
    public void authenticatePinIncorrect() {
        container.authenticate("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256, "1112");
    }

    @Test
    public void sign() {
        String signedHash = container.sign("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256, "1111");
        assertNotNull(signedHash);
    }

    @Test(expected = VerifyPinException.class)
    public void signPinIncorrect() {
        container.sign("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256, "1112");
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

    @Test
    public void getAllDataClass() {
        assertEquals(LuxTrustAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificatesClass() {
        assertEquals(LuxTrustAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void getSigningCertificateChain() {
        Map<Integer, T1cCertificate> signChain = container.getSigningCertificateChain();
        assertNotNull(signChain);
        assertTrue(CollectionUtils.isNotEmpty(signChain.entrySet()));
    }

    @Test
    public void testAllData() {
        LuxTrustAllData obj = new LuxTrustAllData(MockResponseFactory.getGclLuxTrustAllData());
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        List<T1cCertificate> rootCerts = Collections.singletonList(new T1cCertificate().withBase64("root"));
        obj.setRootCertificates(rootCerts);
        assertEquals(rootCerts, obj.getRootCertificates());
        T1cCertificate newCert = new T1cCertificate().withBase64("new");
        obj.setAuthenticationCertificate(newCert);
        assertEquals(newCert, obj.getAuthenticationCertificate());
        obj.setSigningCertificate(newCert);
        assertEquals(newCert, obj.getSigningCertificate());
        obj.setActivated(false);
        assertFalse(obj.getActivated());
    }

    @Test
    public void testAllCertificates() {
        LuxTrustAllCertificates obj = new LuxTrustAllCertificates(MockResponseFactory.getGclLuxTrustAllCertificates());
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        List<T1cCertificate> rootCerts = Collections.singletonList(new T1cCertificate().withBase64("root"));
        obj.setRootCertificates(rootCerts);
        assertEquals(rootCerts, obj.getRootCertificates());
        T1cCertificate newCert = new T1cCertificate().withBase64("new");
        obj.setAuthenticationCertificate(newCert);
        assertEquals(newCert, obj.getAuthenticationCertificate());
        obj.setSigningCertificate(newCert);
        assertEquals(newCert, obj.getSigningCertificate());
    }

    @Test
    public void testGclAllData() {
        GclLuxTrustAllData obj = new GclLuxTrustAllData();
        GclLuxTrustAllData obj2 = new GclLuxTrustAllData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        List<String> rootCerts = Collections.singletonList("root");
        obj.setRootCertificates(rootCerts);
        assertEquals(rootCerts, obj.getRootCertificates());
        String newCert = "new";
        obj.setAuthenticationCertificate(newCert);
        assertEquals(newCert, obj.getAuthenticationCertificate());
        obj.setSigningCertificate(newCert);
        assertEquals(newCert, obj.getSigningCertificate());
        obj.setActivated(false);
        assertFalse(obj.getActivated());
    }

    @Test
    public void testGclAllCertificates() {
        GclLuxTrustAllCertificates obj = new GclLuxTrustAllCertificates();
        GclLuxTrustAllCertificates obj2 = new GclLuxTrustAllCertificates();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        List<String> rootCerts = Collections.singletonList("root");
        obj.setRootCertificates(rootCerts);
        assertEquals(rootCerts, obj.getRootCertificates());
        String newCert = "new";
        obj.setAuthenticationCertificate(newCert);
        assertEquals(newCert, obj.getAuthenticationCertificate());
        obj.setSigningCertificate(newCert);
        assertEquals(newCert, obj.getSigningCertificate());
    }

    @Test
    public void getAuthenticationCertificateChain() {
        Map<Integer, T1cCertificate> signChain = container.getAuthenticationCertificateChain();
        assertNotNull(signChain);
        assertTrue(CollectionUtils.isNotEmpty(signChain.entrySet()));
    }

    @Test
    public void testGenericDataDump() {
        ContainerData data = container.dumpData();
        assertNotNull(data);

        assertNotNull(data.getAuthenticationCertificateChain());
        assertNotNull(data.getSigningCertificateChain());
        assertNotNull(data.getAllCertificates());
    }
}