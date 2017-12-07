package com.t1t.t1c.containers.smartcards.eid.lux;

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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class LuxIdContainerTest extends AbstractTestClass {

    private LuxIdContainer container;

    @Before
    public void initContainer() {
        container = getClient().getLuxIdContainer(new GclReader().withPinpad(true).withId(MockResponseFactory.LUXID_READER_ID), "123456");
    }

    @Test
    public void getBiometric() {
        GclLuxIdBiometric bio = container.getBiometric();
        assertNotNull(bio);
        assertNotNull(bio.getBirthDate());
        assertNotNull(bio.getDocumentNumber());
        assertNotNull(bio.getDocumentType());
        assertNotNull(bio.getFirstName());
        assertNotNull(bio.getLastName());
        assertNotNull(bio.getGender());
        assertNotNull(bio.getIssuingState());
        assertNotNull(bio.getNationality());
        assertNotNull(bio.getValidityEndDate());
        assertNotNull(bio.getValidityStartDate());
    }

    @Test
    public void getPicture() {
        GclLuxIdPicture pic = container.getPicture();
        assertNotNull(pic);
        assertNotNull(pic.getHeight());
        assertNotNull(pic.getWidth());
        assertNotNull(pic.getImage());
    }

    @Test
    public void getSignatureImage() {
        GclLuxIdSignatureImage sig = container.getSignatureImage();
        assertNotNull(sig);
        assertNotNull(sig.getImage());
    }

    @Test
    public void getAllData() {
        LuxIdAllData data = (LuxIdAllData) container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getNonRepudiationCertificate());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getRootCertificates());
        assertNotNull(data.getSignatureImage());
        assertNotNull(data.getBiometric());
        assertNotNull(data.getPicture());
    }

    @Test
    public void getAllDataFiltered() {
        LuxIdAllData data = (LuxIdAllData) container.getAllData(Arrays.asList("biometric", "root-certificates"));
        assertNotNull(data);
        assertNull(data.getNonRepudiationCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNotNull(data.getRootCertificates());
        assertNull(data.getSignatureImage());
        assertNotNull(data.getBiometric());
        assertNull(data.getPicture());
    }

    @Test
    public void getAllDataParsed() {
        LuxIdAllData data = (LuxIdAllData) container.getAllData(Collections.singletonList("authentication-certificate"), true);
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        LuxIdAllCertificates certs = (LuxIdAllCertificates) container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRootCertificates());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getAllCertificatesFiltered() {
        LuxIdAllCertificates certs = (LuxIdAllCertificates) container.getAllCertificates(Arrays.asList("authentication-certificate", "non-repudiation-certificate"));
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNull(certs.getRootCertificates());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        LuxIdAllCertificates certs = (LuxIdAllCertificates) container.getAllCertificates(true);
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getAuthenticationCertificate().getParsed());
        assertNotNull(certs.getRootCertificates());
        assertNotNull(certs.getNonRepudiationCertificate());
        assertNotNull(certs.getNonRepudiationCertificate().getParsed());
    }

    @Test
    public void getRootCertificates() {
        List<T1cCertificate> certs = container.getRootCertificates();
        assertTrue(CollectionUtils.isNotEmpty(certs));
        for (T1cCertificate cert : certs) {
            assertNotNull(cert);
            assertNull(cert.getParsed());
        }
    }

    @Test
    public void getRootCertificatesParsed() {
        List<T1cCertificate> certs = container.getRootCertificates(true);
        assertTrue(CollectionUtils.isNotEmpty(certs));
        for (T1cCertificate cert : certs) {
            assertNotNull(cert);
            assertNotNull(cert.getParsed());
        }
    }

    @Test
    public void getAuthenticationCertificate() {
        assertNotNull(container.getAuthenticationCertificate());
    }

    @Test
    public void getAuthenticationCertificateParsed() {
        T1cCertificate cert = container.getAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificate() {
        T1cCertificate cert = container.getNonRepudiationCertificate();
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificateParsed() {
        T1cCertificate cert = container.getNonRepudiationCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
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
        assertEquals(ContainerType.LUXID, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.LUXID.getId(), container.getTypeId());
    }
}