package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.RestException;
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
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class DnieContainerTest extends AbstractTestClass {

    private DnieContainer container;

    @Before
    public void initContainer() {
        container = getClient().getDnieContainer(new GclReader().withId(MockResponseFactory.DNIE_READER_ID).withPinpad(true));
    }

    @Test
    public void getAllDataFilters() {
        assertNotNull(container.getAllDataFilters());
    }

    @Test
    public void getAllCertificateFilters() {
        assertNotNull(container.getAllCertificateFilters());
    }

    @Test
    public void getAllData() {
        DnieAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getInfo());
        assertNotNull(data.getAuthenticationCertificate());
        assertNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getIntermediateCertificate());
        assertNull(data.getIntermediateCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNull(data.getIntermediateCertificate().getParsed());
    }

    @Test(expected = RestException.class)
    public void getAllDataRestException() {
        container.getAllData(Collections.singletonList("throwException"));
    }

    @Test
    public void getAllDataFiltered() {
        DnieAllData data = container.getAllData(Arrays.asList("signing-certificate", "info"));
        assertNotNull(data);
        assertNotNull(data.getInfo());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getIntermediateCertificate());
        assertNotNull(data.getSigningCertificate());
    }

    @Test
    public void getAllDataParsed() {
        DnieAllData data = container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getInfo());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getIntermediateCertificate());
        assertNotNull(data.getIntermediateCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getIntermediateCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        DnieAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNull(certs.getAuthenticationCertificate().getParsed());
        assertNotNull(certs.getIntermediateCertificate());
        assertNull(certs.getIntermediateCertificate().getParsed());
        assertNotNull(certs.getSigningCertificate());
        assertNull(certs.getIntermediateCertificate().getParsed());
    }

    @Test(expected = RestException.class)
    public void getAllCertificateRestException() {
        container.getAllData(Collections.singletonList("throwException"));
    }

    @Test
    public void getAllCertificatesFiltered() {
        DnieAllCertificates data = container.getAllCertificates(Arrays.asList("signing-certificate", "intermediate-certificate"));
        assertNotNull(data);
        assertNull(data.getAuthenticationCertificate());
        assertNotNull(data.getIntermediateCertificate());
        assertNotNull(data.getSigningCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        DnieAllCertificates data = container.getAllCertificates(true);
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getIntermediateCertificate());
        assertNotNull(data.getIntermediateCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getIntermediateCertificate().getParsed());
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
    public void verifyPinWithHardwarePinPad() {
        assertTrue(container.verifyPin());
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
    public void authenticateWithHardwarePinPad() {
        assertNotNull(container.authenticate("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256));
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
    public void signWithHardwarePinPad() {
        assertNotNull(container.sign("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256));
    }

    @Test
    public void getAuthenticationCertificate() {
        T1cCertificate cert = container.getAuthenticationCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificateParsed() {
        T1cCertificate cert = container.getAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getIntermediateCertificate() {
        T1cCertificate cert = container.getIntermediateCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getIntermediateCertificateParsed() {
        T1cCertificate cert = container.getIntermediateCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getSigningCertificate() {
        T1cCertificate cert = container.getSigningCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getSigningCertificateParsed() {
        T1cCertificate cert = container.getSigningCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getInfo() {
        GclDnieInfo info = container.getInfo();
        assertNotNull(info);
        assertNotNull(info.getFirstName());
        assertNotNull(info.getFirstLastName());
        assertNotNull(info.getSecondLastName());
        assertNotNull(info.getIdesp());
        assertNotNull(info.getNumber());
        assertNotNull(info.getSerialNumber());
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.DNIE, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.DNIE.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(DnieAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificateClass() {
        assertEquals(DnieAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void testBeIdAllCerts() {
        DnieAllCertificates certs = container.getAllCertificates();
        T1cCertificate oldAuth = certs.getAuthenticationCertificate();
        T1cCertificate oldInterm = certs.getIntermediateCertificate();
        T1cCertificate oldSign = certs.getSigningCertificate();
        T1cCertificate newCert = new T1cCertificate().withBase64("base");

        certs.setAuthenticationCertificate(newCert);
        assertEquals(newCert, certs.getAuthenticationCertificate());
        assertEquals(certs.withAuthenticationCertificate(oldAuth), certs);

        certs.setIntermediateCertificate(newCert);
        assertEquals(newCert, certs.getIntermediateCertificate());
        assertEquals(certs.withIntermediateCertificate(oldInterm), certs);

        certs.setSigningCertificate(newCert);
        assertEquals(newCert, certs.getSigningCertificate());
        assertEquals(certs.withSigningCertificate(oldSign), certs);
    }

    @Test
    public void testBeIdAllData() {
        DnieAllData data = container.getAllData();
        T1cCertificate oldAuth = data.getAuthenticationCertificate();
        T1cCertificate oldInterm = data.getIntermediateCertificate();
        T1cCertificate oldSign = data.getSigningCertificate();
        T1cCertificate newCert = new T1cCertificate().withBase64("base");

        data.setAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate(oldAuth), data);

        data.setIntermediateCertificate(newCert);
        assertEquals(newCert, data.getIntermediateCertificate());
        assertEquals(data.withIntermediateCertificate(oldInterm), data);

        data.setSigningCertificate(newCert);
        assertEquals(newCert, data.getSigningCertificate());
        assertEquals(data.withSigningCertificate(oldSign), data);

        GclDnieInfo info = new GclDnieInfo();
        data.setInfo(info);
        assertEquals(info, data.getInfo());
        assertEquals(data.withInfo(new GclDnieInfo().withFirstLastName("F")), data);

        assertTrue(StringUtils.isNotEmpty(data.toString()));
    }

    @Test
    public void testGclDnieAllCertificates() {
        GclDnieAllCertificates obj1 = MockResponseFactory.getDnieAllCertificates();
        obj1.setAuthenticationCertificate("auth");
        assertEquals("auth", obj1.getAuthenticationCertificate());
        obj1.setIntermediateCertificate("interm");
        assertEquals("interm", obj1.getIntermediateCertificate());
        obj1.setSigningCertificate("sign");
        assertEquals("sign", obj1.getSigningCertificate());
        obj1 = MockResponseFactory.getDnieAllCertificates();
        GclDnieAllCertificates obj2 = MockResponseFactory.getDnieAllCertificates();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj1);
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, "string");
        assertTrue(StringUtils.isNotEmpty(obj1.toString()));
    }

    @Test
    public void testGclDnieAllData() {
        GclDnieAllData obj1 = MockResponseFactory.getDnieAllData();
        GclDnieInfo info = new GclDnieInfo().withIdesp("1");
        obj1.setInfo(info);
        assertEquals(info, obj1.getInfo());
        obj1.setSigningCertificate("sign");
        assertEquals("sign", obj1.getSigningCertificate());
        obj1 = MockResponseFactory.getDnieAllData();
        GclDnieAllData obj2 = MockResponseFactory.getDnieAllData();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj1);
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, "string");
        assertTrue(StringUtils.isNotEmpty(obj1.toString()));
    }

    @Test
    public void testGclDnieInfo() {
        GclDnieInfo info = MockResponseFactory.getGclDnieInfo();
        info.setFirstLastName("first");
        assertEquals("first", info.getFirstLastName());
        info.setFirstName("first");
        assertEquals("first", info.getFirstName());
        info.setIdesp("idesp");
        assertEquals("idesp", info.getIdesp());
        info.setNumber("number");
        assertEquals("number", info.getNumber());
        info.setSecondLastName("second");
        assertEquals("second", info.getSecondLastName());
        info.setSerialNumber("number");
        assertEquals("number", info.getSerialNumber());

        assertNotEquals(info, "string");
    }

    @Test
    public void getSigningCertificateChain() {
        Map<Integer, T1cCertificate> signChain = container.getSigningCertificateChain();
        assertNotNull(signChain);
        assertTrue(CollectionUtils.isNotEmpty(signChain.entrySet()));
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
        assertNotNull(data.getGivenName());
        assertNotNull(data.getSurName());
        assertNotNull(data.getFullName());

        assertNotNull(data.getDocumentId());

        assertNotNull(data.getAuthenticationCertificateChain());
        assertNotNull(data.getSigningCertificateChain());
        assertNotNull(data.getCertificateChains());
        assertNotNull(data.getAllCertificates());
    }
}