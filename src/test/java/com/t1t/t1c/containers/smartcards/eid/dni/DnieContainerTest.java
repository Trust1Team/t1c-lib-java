package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;

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

    @Test(expected = DnieContainerException.class)
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

    @Test(expected = DnieContainerException.class)
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
        assertEquals(DnieAllCertificates.class, container.getAllCertificateClass());
    }
}