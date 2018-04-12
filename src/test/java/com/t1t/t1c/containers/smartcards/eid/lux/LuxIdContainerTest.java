package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.ExceptionFactory;
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
import java.util.List;
import java.util.Map;

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
        LuxIdAllData data = container.getAllData();
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
        LuxIdAllData data = container.getAllData(Arrays.asList("biometric", "root-certificates"));
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
        LuxIdAllData data = container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        LuxIdAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRootCertificates());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getAllCertificatesFiltered() {
        LuxIdAllCertificates certs = container.getAllCertificates(Arrays.asList("authentication-certificate", "non-repudiation-certificate"));
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNull(certs.getRootCertificates());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        LuxIdAllCertificates certs = container.getAllCertificates(true);
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
        assertEquals(ContainerType.LUXID, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.LUXID.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(LuxIdAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificatesClass() {
        assertEquals(LuxIdAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test(expected = LuxIdContainerException.class)
    public void createInstance() {
        container = getClient().getLuxIdContainer(new GclReader().withPinpad(true).withId(MockResponseFactory.LUXID_READER_ID), null);
    }

    @Test
    public void testGclLuxIdAllCertificates() {
        GclLuxIdAllCertificates certs = MockResponseFactory.getGclLuxIdAllCertificates();
        certs.setAuthenticationCertificate("auth");
        assertEquals("auth", certs.getAuthenticationCertificate());
        certs.setNonRepudiationCertificate("nonr");
        assertEquals("nonr", certs.getNonRepudiationCertificate());
        assertTrue(StringUtils.isNotEmpty(certs.toString()));
        certs = MockResponseFactory.getGclLuxIdAllCertificates();
        GclLuxIdAllCertificates certs2 = MockResponseFactory.getGclLuxIdAllCertificates();
        assertEquals(certs, certs);
        assertEquals(certs.hashCode(), certs2.hashCode());
        assertEquals(certs, certs2);
        assertNotEquals(certs, "string");
    }

    @Test
    public void testGclLuxIdAllData() {
        GclLuxIdAllData data = MockResponseFactory.getGclLuxIdAllData();
        data.setSignatureObject("obj");
        assertEquals("obj", data.getSignatureObject());
        GclLuxIdBiometric bio = new GclLuxIdBiometric().withBirthDate("1");
        data.setBiometric(bio);
        assertEquals(bio, data.getBiometric());
        List<String> rootCerts = Arrays.asList("cert1", "cert2");
        data.setRootCertificates(rootCerts);
        assertEquals(rootCerts, data.getRootCertificates());
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        data = MockResponseFactory.getGclLuxIdAllData();
        GclLuxIdAllData data2 = MockResponseFactory.getGclLuxIdAllData();
        assertEquals(data, data);
        assertEquals(data.hashCode(), data2.hashCode());
        assertEquals(data, data2);
        assertNotEquals(data, "string");
    }

    @Test
    public void testGcLLuxIdBiometric() {
        GclLuxIdBiometric bio = MockResponseFactory.getGclLuxIdBiometric();
        bio.setBirthDate("1");
        assertEquals("1", bio.getBirthDate());
        bio.setDocumentNumber("2");
        assertEquals("2", bio.getDocumentNumber());
        bio.setDocumentType("3");
        assertEquals("3", bio.getDocumentType());
        bio.setFirstName("4");
        assertEquals("4", bio.getFirstName());
        bio.setGender("5");
        assertEquals("5", bio.getGender());
        bio.setIssuingState("6");
        assertEquals("6", bio.getIssuingState());
        bio.setLastName("7");
        assertEquals("7", bio.getLastName());
        bio.setNationality("8");
        assertEquals("8", bio.getNationality());
        bio.setRawData("9");
        assertEquals("9", bio.getRawData());
        bio.setValidityEndDate("10");
        assertEquals("10", bio.getValidityEndDate());
        bio.setValidityStartDate("11");
        assertEquals("11", bio.getValidityStartDate());
        bio = MockResponseFactory.getGclLuxIdBiometric();
        GclLuxIdBiometric bio2 = MockResponseFactory.getGclLuxIdBiometric();
        assertEquals(bio, bio);
        assertEquals(bio.hashCode(), bio2.hashCode());
        assertEquals(bio, bio2);
        assertNotEquals(bio, "string");
    }

    @Test
    public void testGclLuxIdPicture() {
        GclLuxIdPicture pic = MockResponseFactory.getGclLuxIdPicture();
        pic.setHeight(1L);
        assertEquals(Long.valueOf(1), pic.getHeight());
        pic.setWidth(2L);
        assertEquals(Long.valueOf(2), pic.getWidth());
        pic.setImage("img");
        assertEquals("img", pic.getImage());
        pic.setRawData("raw");
        assertEquals("raw", pic.getRawData());
        pic = MockResponseFactory.getGclLuxIdPicture();
        GclLuxIdPicture pic2 = MockResponseFactory.getGclLuxIdPicture();
        assertEquals(pic, pic);
        assertEquals(pic.hashCode(), pic2.hashCode());
        assertEquals(pic, pic2);
        assertNotEquals(pic, "string");
    }

    @Test
    public void testGclLuxIdSignature() {
        GclLuxIdSignatureImage pic = MockResponseFactory.getGclLuxIdSignatureImage();
        pic.setImage("img");
        assertEquals("img", pic.getImage());
        pic.setRawData("raw");
        assertEquals("raw", pic.getRawData());
        pic = MockResponseFactory.getGclLuxIdSignatureImage();
        GclLuxIdSignatureImage pic2 = MockResponseFactory.getGclLuxIdSignatureImage();
        assertEquals(pic, pic);
        assertEquals(pic.hashCode(), pic2.hashCode());
        assertEquals(pic, pic2);
        assertNotEquals(pic, "string");
    }

    @Test
    public void testLuxIdAllCertificates() {
        LuxIdAllCertificates certs = container.getAllCertificates();
        T1cCertificate newCert = new T1cCertificate().withBase64("base");
        List<T1cCertificate> oldRootCerts = certs.getRootCertificates();
        T1cCertificate oldAuth = certs.getAuthenticationCertificate();
        T1cCertificate oldNon = certs.getNonRepudiationCertificate();
        certs.setRootCertificates(Arrays.asList(newCert, newCert));
        assertEquals(Arrays.asList(newCert, newCert), certs.getRootCertificates());
        assertEquals(certs.withRootCertificates(oldRootCerts), certs);
        certs.setAuthenticationCertificate(newCert);
        assertEquals(newCert, certs.getAuthenticationCertificate());
        assertEquals(certs.withAuthenticationCertificate(oldAuth), certs);
        certs.setNonRepudiationCertificate(newCert);
        assertEquals(newCert, certs.getNonRepudiationCertificate());
        assertEquals(certs.withNonRepudiationCertificate(oldNon), certs);

        assertTrue(StringUtils.isNotEmpty(certs.toString()));
    }

    @Test
    public void testLuxIdAllData() {
        LuxIdAllData data = container.getAllData();
        T1cCertificate newCert = new T1cCertificate().withBase64("base");
        List<T1cCertificate> oldRootCerts = data.getRootCertificates();
        T1cCertificate oldAuth = data.getAuthenticationCertificate();
        T1cCertificate oldNon = data.getNonRepudiationCertificate();
        GclLuxIdBiometric oldBio = data.getBiometric();
        GclLuxIdSignatureImage oldSig = data.getSignatureImage();
        GclLuxIdPicture oldPic = data.getPicture();
        String oldSigObj = data.getSignatureObject();
        data.setRootCertificates(Arrays.asList(newCert, newCert));
        assertEquals(Arrays.asList(newCert, newCert), data.getRootCertificates());
        assertEquals(data.withRootCertificates(oldRootCerts), data);
        data.setAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate(oldAuth), data);
        data.setNonRepudiationCertificate(newCert);
        assertEquals(newCert, data.getNonRepudiationCertificate());
        assertEquals(data.withNonRepudiationCertificate(oldNon), data);

        GclLuxIdBiometric bio = new GclLuxIdBiometric().withBirthDate("1");
        data.setBiometric(bio);
        assertEquals(bio, data.getBiometric());
        assertEquals(data.withBiometric(oldBio), data);

        GclLuxIdSignatureImage img = new GclLuxIdSignatureImage().withImage("img");
        data.setSignatureImage(img);
        assertEquals(img, data.getSignatureImage());
        assertEquals(data.withSignatureImage(oldSig), data);

        GclLuxIdPicture pic = new GclLuxIdPicture().withImage("pic");
        data.setPicture(pic);
        assertEquals(pic, data.getPicture());
        assertEquals(data.withPicture(oldPic), data);

        data.setSignatureObject("obj");
        assertEquals("obj", data.getSignatureObject());
        assertEquals(data.withSignatureObject(oldSigObj), data);

        assertTrue(StringUtils.isNotEmpty(data.toString()));
    }

    @Test
    public void testLuxIdContainerException() {
        LuxIdContainerException ex = ExceptionFactory.luxIdContainerException("message");
        assertEquals(Long.valueOf(ErrorCodes.ERROR_LUXID_CONTAINER_REST), ex.getErrorCode());
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
        assertNotNull(data.getDateOfBirth());
        assertNotNull(data.getGender());

        assertNotNull(data.getNationality());
        assertNotNull(data.getBase64Picture());
        assertNotNull(data.getValidityStartDate());
        assertNotNull(data.getValidityEndDate());
        assertNotNull(data.getDocumentId());
        assertNotNull(data.getBase64SignatureImage());

        assertNotNull(data.getAuthenticationCertificateChain());
        assertNotNull(data.getSigningCertificateChain());
        assertNotNull(data.getCertificateChains());
        assertNotNull(data.getAllCertificates());
    }
}