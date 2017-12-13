package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
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

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class PtEIdContainerTest extends AbstractTestClass {

    private PtEIdContainer container;

    @Before
    public void initContainer() {
        container = getClient().getPtIdContainer(new GclReader().withId(MockResponseFactory.PT_READER_ID).withPinpad(true));
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
        PtIdAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getId());
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getRootNonRepudiationCertificate());
        assertNotNull(data.getNonRepudiationCertificate());
    }

    @Test
    public void getAllDataFiltered() {
        PtIdAllData data = container.getAllData(Arrays.asList("id", "root-authentication-certificate"));
        assertNotNull(data);
        assertNotNull(data.getId());
        assertNull(data.getRootCertificate());
        assertNotNull(data.getRootAuthenticationCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getRootNonRepudiationCertificate());
        assertNull(data.getNonRepudiationCertificate());
    }

    @Test
    public void getAllDataParsed() {
        PtIdAllData data = container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getRootAuthenticationCertificate());
        assertNotNull(data.getRootAuthenticationCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        PtIdAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getRootAuthenticationCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRootNonRepudiationCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getAllCertificatesFiltered() {
        PtIdAllCertificates certs = container.getAllCertificates(Arrays.asList("root-certificate", "root-non-repudiation-certificate", "non-repudiation-certificate"));
        assertNotNull(certs);
        assertNotNull(certs.getRootCertificate());
        assertNull(certs.getRootAuthenticationCertificate());
        assertNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRootNonRepudiationCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        PtIdAllCertificates certs = container.getAllCertificates(true);
        assertNotNull(certs);
        assertNotNull(certs.getRootAuthenticationCertificate());
        assertNotNull(certs.getRootAuthenticationCertificate().getParsed());
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
    public void getType() {
        assertEquals(ContainerType.PT, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.PT.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(PtIdAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificateClass() {
        assertEquals(PtIdAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void getPtIdDataWithPhoto() {
        GclPtIdData data = container.getPtIdData(true);
        assertNotNull(data);
        assertNotNull(data.getPhoto());
    }

    @Test
    public void getPtIdDataWithoutPhoto() {
        GclPtIdData data = container.getPtIdData();
        assertNotNull(data);
        assertNull(data.getPhoto());
    }

    @Test
    public void getPtIdPhoto() {
        assertTrue(StringUtils.isNotEmpty(container.getPtIdPhoto()));
    }

    @Test
    public void getRootCertificate() {
        T1cCertificate cert = container.getRootCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getRootCertificateParsed() {
        T1cCertificate cert = container.getRootCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getRootAuthenticationCertificate() {
        T1cCertificate cert = container.getRootAuthenticationCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getRootAuthenticationCertificateParsed() {
        T1cCertificate cert = container.getRootAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getRootNonRepudiationCertificate() {
        T1cCertificate cert = container.getRootNonRepudiationCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getRootNonRepudiationCertificateParsed() {
        T1cCertificate cert = container.getRootNonRepudiationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
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
    public void getNonRepudiationCertificate() {
        T1cCertificate cert = container.getNonRepudiationCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificateParsed() {
        T1cCertificate cert = container.getNonRepudiationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAddress() {
        GclPtIdAddress address = container.getAddress("1111");
        assertNotNull(address);
    }

    @Test(expected = VerifyPinException.class)
    public void getAddressWithWrongPin() {
        container.getAddress("1112");
    }

    @Test
    public void getAddressWithHardwarePinPad() {
        GclPtIdAddress address = container.getAddress();
        assertNotNull(address);
    }
}