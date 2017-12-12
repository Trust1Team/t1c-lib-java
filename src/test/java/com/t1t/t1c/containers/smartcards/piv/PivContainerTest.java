package com.t1t.t1c.containers.smartcards.piv;

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
public class PivContainerTest extends AbstractTestClass {

    private PivContainer container;

    @Before
    public void initContainer() {
        container = getClient().getPivContainer(new GclReader().withId(MockResponseFactory.PIV_READER_ID).withPinpad(true), "1111");
    }

    @Test(expected = IllegalArgumentException.class)
    public void initContainerWithoutPin() {
        getClient().getPivContainer(new GclReader().withId(MockResponseFactory.PIV_READER_ID).withPinpad(true), null);
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
        PivAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getFacialImage());
        assertNotNull(data.getPrintedInformation());
    }

    @Test
    public void getAllDataFiltered() {
        PivAllData data = container.getAllData(Arrays.asList("facial-image", "signing-certificate"));
        assertNotNull(data);
        assertNull(data.getAuthenticationCertificate());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getFacialImage());
        assertNull(data.getPrintedInformation());
    }

    @Test
    public void getAllDataParsed() {
        PivAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNotNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getFacialImage());
        assertNotNull(data.getPrintedInformation());
    }

    @Test
    public void getAllCertificates() {
        PivAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getAuthenticationCertificate().getBase64());
        assertNull(certs.getAuthenticationCertificate().getParsed());
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getSigningCertificate().getBase64());
        assertNull(certs.getSigningCertificate().getParsed());
    }

    @Test
    public void getAllCertificatesFiltered() {
        PivAllCertificates certs = container.getAllCertificates(Collections.singletonList("authentication-certificate"));
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getAuthenticationCertificate().getBase64());
        assertNull(certs.getAuthenticationCertificate().getParsed());
        assertNull(certs.getSigningCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        PivAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getAuthenticationCertificate().getBase64());
        assertNotNull(certs.getAuthenticationCertificate().getParsed());
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getSigningCertificate().getBase64());
        assertNotNull(certs.getSigningCertificate().getParsed());
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
        assertEquals(ContainerType.PIV, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.PIV.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(PivAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificatesClass() {
        assertEquals(PivAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void getPrintedInformation() {
        GclPivPrintedInformation printedInformation = container.getPrintedInformation();
        assertNotNull(printedInformation);
        assertNotNull(printedInformation.getAgencyCardSerialNumber());
        assertNotNull(printedInformation.getEmployeeAffiliation());
        assertNotNull(printedInformation.getExpirationDate());
        assertNotNull(printedInformation.getIssuerIdentification());
        assertNotNull(printedInformation.getName());
        assertNotNull(printedInformation.getOrganizationAffiliationLine1());
        assertNotNull(printedInformation.getOrganizationAffiliationLine2());
    }

    @Test
    public void getFacialImage() {
        GclPivFacialImage facialImage = container.getFacialImage();
        assertNotNull(facialImage);
        assertNotNull(facialImage.getImage());
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
    public void getAllAlgoRefsForAuthentication() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllAlgoRefsForAuthentication()));
    }

    @Test
    public void getAllAlgoRefsForSigning() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllAlgoRefsForSigning()));
    }
}