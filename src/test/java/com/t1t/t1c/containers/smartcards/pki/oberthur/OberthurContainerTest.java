package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraAllCertificates;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraAllData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
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
public class OberthurContainerTest extends AbstractTestClass {

    private OberthurContainer container;

    @Before
    public void initContainer() {
        container = getClient().getOberthurContainer(new GclReader().withId(MockResponseFactory.OBERTHUR_READER_ID).withPinpad(true));
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
        OberthurAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getBase64());
        assertNull(data.getRootCertificate().getParsed());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getIssuerCertificate());
        assertNotNull(data.getIssuerCertificate().getBase64());
        assertNull(data.getIssuerCertificate().getParsed());
        assertNotNull(data.getEncryptionCertificate());
        assertNotNull(data.getEncryptionCertificate().getBase64());
        assertNull(data.getEncryptionCertificate().getParsed());
    }

    @Test
    public void getAllDataFiltered() {
        OberthurAllData data = container.getAllData(Arrays.asList("root-certificate", "issuer-certificate"));
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getSigningCertificate());
        assertNotNull(data.getIssuerCertificate());
        assertNull(data.getEncryptionCertificate());
    }

    @Test
    public void getAllDataParsed() {
        OberthurAllData data = container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getBase64());
        assertNotNull(data.getRootCertificate().getParsed());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNotNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getIssuerCertificate());
        assertNotNull(data.getIssuerCertificate().getBase64());
        assertNotNull(data.getIssuerCertificate().getParsed());
        assertNotNull(data.getEncryptionCertificate());
        assertNotNull(data.getEncryptionCertificate().getBase64());
        assertNotNull(data.getEncryptionCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        OberthurAllData data = container.getAllCertificates();
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getBase64());
        assertNull(data.getRootCertificate().getParsed());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getIssuerCertificate());
        assertNotNull(data.getIssuerCertificate().getBase64());
        assertNull(data.getIssuerCertificate().getParsed());
        assertNotNull(data.getEncryptionCertificate());
        assertNotNull(data.getEncryptionCertificate().getBase64());
        assertNull(data.getEncryptionCertificate().getParsed());
    }

    @Test
    public void getAllCertificatesFiltered() {
        OberthurAllData data = container.getAllCertificates(Arrays.asList("root-certificate", "issuer-certificate"));
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getSigningCertificate());
        assertNotNull(data.getIssuerCertificate());
        assertNull(data.getEncryptionCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        OberthurAllData data = container.getAllCertificates(true);
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getBase64());
        assertNotNull(data.getRootCertificate().getParsed());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNotNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getIssuerCertificate());
        assertNotNull(data.getIssuerCertificate().getBase64());
        assertNotNull(data.getIssuerCertificate().getParsed());
        assertNotNull(data.getEncryptionCertificate());
        assertNotNull(data.getEncryptionCertificate().getBase64());
        assertNotNull(data.getEncryptionCertificate().getParsed());
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
        assertEquals(ContainerType.OBERTHUR, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.OBERTHUR.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(OberthurAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificatesClass() {
        assertEquals(OberthurAllData.class, container.getAllCertificatesClass());
    }

    @Test
    public void getAllAlgoRefsForAuthentication() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllAlgoRefsForAuthentication()));
    }

    @Test
    public void getAllAlgoRefsForSigning() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllAlgoRefsForSigning()));
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
    public void getIssuerCertificate() {
        T1cCertificate cert = container.getIssuerCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getIssuerCertificateParsed() {
        T1cCertificate cert = container.getIssuerCertificate(true);
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
    public void getEncryptionCertificate() {
        T1cCertificate cert = container.getEncryptionCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getEncryptionCertificateParsed() {
        T1cCertificate cert = container.getEncryptionCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }
}