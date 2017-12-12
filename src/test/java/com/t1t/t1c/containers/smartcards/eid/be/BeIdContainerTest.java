package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class BeIdContainerTest extends AbstractTestClass {

    private BeIdContainer container;

    @Before
    public void initContainer() {
        container = getClient().getBeIdContainer(new GclReader().withId(MockResponseFactory.BEID_READER_ID).withPinpad(true));
    }

    @Test
    public void getRnData() {
        GclBeIdRn rn = container.getRnData();
        assertNotNull(rn);
    }

    @Test
    public void getBeIdAddress() {
        GclBeIdAddress address = container.getBeIdAddress();
        assertNotNull(address);
    }

    @Test
    public void getBeIdPicture() {
        String picture = container.getBeIdPicture();
        assertNotNull(picture);
        assertTrue(Base64.isBase64(picture));
    }

    @Test
    public void getRootCertificate() {
        T1cCertificate cert = container.getRootCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getRootCertificateParsed() {
        T1cCertificate cert = container.getRootCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getCitizenCertificate() {
        T1cCertificate cert = container.getCitizenCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getCitizenCertificateParsed() {
        T1cCertificate cert = container.getCitizenCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificate() {
        T1cCertificate cert = container.getNonRepudiationCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificateParsed() {
        T1cCertificate cert = container.getNonRepudiationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificate() {
        T1cCertificate cert = container.getAuthenticationCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificateParsed() {
        T1cCertificate cert = container.getAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getRrnCertificate() {
        T1cCertificate cert = container.getRrnCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getRrnCertificateParsed() {
        T1cCertificate cert = container.getRrnCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void createInstance() {
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
        BeIdAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getAddress());
        assertNotNull(data.getRn());
        assertNotNull(data.getPicture());
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getCitizenCertificate());
        assertNotNull(data.getNonRepudiationCertificate());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getRrnCertificate());
    }

    @Test
    public void getAllDataWithFilters() {
        BeIdAllData data = container.getAllData(Arrays.asList("rn", "root-certificate"));
        assertNotNull(data);
        assertNull(data.getAddress());
        assertNotNull(data.getRn());
        assertNull(data.getPicture());
        assertNotNull(data.getRootCertificate());
        assertNull(data.getCitizenCertificate());
        assertNull(data.getNonRepudiationCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getRrnCertificate());
    }

    @Test
    public void getAllDataWithParsing() {
        BeIdAllData data = container.getAllData( true);
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        BeIdAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getCitizenCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRrnCertificate());
    }

    @Test
    public void getAllCertificatesWithFilter() {
        BeIdAllCertificates certs = container.getAllCertificates(Arrays.asList("non-repudiation-certificate", "citizen-certificate"));
        assertNotNull(certs);
        assertNull(certs.getRootCertificate());
        assertNotNull(certs.getCitizenCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
        assertNull(certs.getAuthenticationCertificate());
        assertNull(certs.getRrnCertificate());
    }

    @Test
    public void getAllCertificatesWithParsing() {
        BeIdAllCertificates certs = container.getAllCertificates( true);
        assertNotNull(certs);
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getRootCertificate().getParsed());
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
        assertNotNull(container.getType());
        assertEquals(ContainerType.BEID, container.getType());
    }

    @Test
    public void getTypeId() {
        assertNotNull(container.getTypeId());
        assertEquals(ContainerType.BEID.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(BeIdAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificatesClass() {
        assertEquals(BeIdAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void testBeIdAllCerts() {
        BeIdAllCertificates certs = container.getAllCertificates();
        T1cCertificate oldAuth = certs.getAuthenticationCertificate();
        T1cCertificate oldRoot = certs.getAuthenticationCertificate();
        T1cCertificate oldCitizen = certs.getAuthenticationCertificate();
        T1cCertificate oldNonRep = certs.getAuthenticationCertificate();
        T1cCertificate oldRrn = certs.getAuthenticationCertificate();
        T1cCertificate newCert = new T1cCertificate().withBase64("base");

        certs.setAuthenticationCertificate(newCert);
        assertEquals(newCert, certs.getAuthenticationCertificate());
        assertEquals(certs.withAuthenticationCertificate(oldAuth), certs);

        certs.setRootCertificate(newCert);
        assertEquals(newCert, certs.getRootCertificate());
        assertEquals(certs.withRootCertificate(oldRoot), certs);

        certs.setCitizenCertificate(newCert);
        assertEquals(newCert, certs.getCitizenCertificate());
        assertEquals(certs.withCitizenCertificate(oldCitizen), certs);

        certs.setNonRepudiationCertificate(newCert);
        assertEquals(newCert, certs.getNonRepudiationCertificate());
        assertEquals(certs.withNonRepudiationCertificate(oldNonRep), certs);

        certs.setRrnCertificate(newCert);
        assertEquals(newCert, certs.getRrnCertificate());
        assertEquals(certs.withRrnCertificate(oldRrn), certs);

        assertTrue(StringUtils.isNotEmpty(container.getAllCertificates().toString()));
    }

    @Test
    public void testBeIdAllDataClass() {
        BeIdAllData data = container.getAllData();
        T1cCertificate oldAuth = data.getAuthenticationCertificate();
        T1cCertificate oldRoot = data.getAuthenticationCertificate();
        T1cCertificate oldCitizen = data.getAuthenticationCertificate();
        T1cCertificate oldNonRep = data.getAuthenticationCertificate();
        T1cCertificate oldRrn = data.getAuthenticationCertificate();
        GclBeIdAddress oldAddress = data.getAddress();
        String oldPic = data.getPicture();
        GclBeIdRn oldRn = data.getRn();

        T1cCertificate newCert = new T1cCertificate().withBase64("base");

        data.setAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate(oldAuth), data);

        data.setRootCertificate(newCert);
        assertEquals(newCert, data.getRootCertificate());
        assertEquals(data.withRootCertificate(oldRoot), data);

        data.setCitizenCertificate(newCert);
        assertEquals(newCert, data.getCitizenCertificate());
        assertEquals(data.withCitizenCertificate(oldCitizen), data);

        data.setNonRepudiationCertificate(newCert);
        assertEquals(newCert, data.getNonRepudiationCertificate());
        assertEquals(data.withNonRepudiationCertificate(oldNonRep), data);

        data.setRrnCertificate(newCert);
        assertEquals(newCert, data.getRrnCertificate());
        assertEquals(data.withRrnCertificate(oldRrn), data);

        data.setPicture("picture");
        assertEquals("picture", data.getPicture());
        assertEquals(data.withPicture(oldPic), data);

        GclBeIdRn newRn = new GclBeIdRn().withCardNumber("1");
        data.setRn(newRn);
        assertEquals(newRn, data.getRn());
        assertEquals(data.withRn(oldRn), data);

        GclBeIdAddress newAddress = new GclBeIdAddress().withMunicipality("Brussels");
        data.setAddress(newAddress);
        assertEquals(newAddress, data.getAddress());
        assertEquals(data.withAddress(oldAddress), data);

        assertTrue(StringUtils.isNotEmpty(data.toString()));
    }

    @Test
    public void testGclBeIdAddress() {
        GclBeIdAddress address = container.getBeIdAddress();
        address.setMunicipality("municipality");
        assertEquals("municipality", address.getMunicipality());
        address.setRawData("raw");
        assertEquals("raw", address.getRawData());
        address.setSignature("sign");
        assertEquals("sign", address.getSignature());
        address.setStreetAndNumber("street");
        assertEquals("street", address.getStreetAndNumber());
        address.setVersion(1);
        assertEquals(Integer.valueOf(1), address.getVersion());
        address.setZipcode("1000");
        assertEquals("1000", address.getZipcode());
        assertEquals(address.withMunicipality("Brussels"), address);
        assertEquals(address.withRawData("rawdata"), address);
        assertEquals(address.withSignature("signature"), address);
        assertEquals(address.withStreetAndNumber("street 1"), address);
        assertEquals(address.withVersion(2), address);
        assertEquals(address.withZipcode("9000"), address);
        GclBeIdAddress obj1 = new GclBeIdAddress().withZipcode("1000");
        GclBeIdAddress obj2 = new GclBeIdAddress().withZipcode("1000");
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertTrue(StringUtils.isNotEmpty(address.toString()));

        assertNotEquals(obj1, "string");
    }

    @Test
    public void testGclBeIdAllCertificates() {
        GclBeIdAllCertificates obj1 = MockResponseFactory.getGclBeIdAllCertificates();
        obj1.setCitizenCertificate("citizen");
        assertEquals("citizen", obj1.getCitizenCertificate());
        obj1.setNonRepudiationCertificate("non-rep");
        assertEquals("non-rep", obj1.getNonRepudiationCertificate());
        obj1 = MockResponseFactory.getGclBeIdAllCertificates();
        GclBeIdAllCertificates obj2 = MockResponseFactory.getGclBeIdAllCertificates();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj1);
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, "string");
        assertTrue(StringUtils.isNotEmpty(obj1.toString()));
    }

    @Test
    public void testGclBeIdAllData() {
        GclBeIdAllData obj1 = MockResponseFactory.getGclBeIdAllData();
        obj1.setRootCertificate("root");
        assertEquals("root", obj1.getRootCertificate());
        GclBeIdRn rn = new GclBeIdRn().withCardNumber("1");
        obj1.setRn(rn);
        assertEquals(rn, obj1.getRn());
        obj1 = MockResponseFactory.getGclBeIdAllData();
        GclBeIdAllData obj2 = MockResponseFactory.getGclBeIdAllData();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, "string");
        assertEquals(obj1, obj1);
        assertTrue(StringUtils.isNotEmpty(obj1.toString()));
    }

    @Test
    public void testGclBeIdRn() {
        GclBeIdRn obj1 = container.getRnData();
        obj1.setBirthDate("date");
        assertEquals("date", obj1.getBirthDate());
        obj1.setBirthLocation("Ghent");
        assertEquals("Ghent", obj1.getBirthLocation());
        obj1.setCardDeliveryMunicipality("Ghent");
        assertEquals("Ghent", obj1.getCardDeliveryMunicipality());
        obj1.setCardNumber("1");
        assertEquals("1", obj1.getCardNumber());
        obj1.setCardValidityDateBegin("2");
        assertEquals("2", obj1.getCardValidityDateBegin());
        obj1.setCardValidityDateEnd("3");
        assertEquals("3", obj1.getCardValidityDateEnd());
        obj1.setChipNumber("3");
        assertEquals("3", obj1.getChipNumber());
        obj1.setDocumentType("0");
        assertEquals("0", obj1.getDocumentType());
        obj1.setFirstNames("J");
        assertEquals("J", obj1.getFirstNames());
        obj1.setName("d");
        assertEquals("d", obj1.getName());
        obj1.setNationalNumber("1");
        assertEquals("1", obj1.getNationalNumber());
        obj1.setNationality("b");
        assertEquals("b", obj1.getNationality());
        obj1.setNobleCondition("1");
        assertEquals("1", obj1.getNobleCondition());
        obj1.setPictureHash("h");
        assertEquals("h", obj1.getPictureHash());
        obj1.setRawData("raw");
        assertEquals("raw", obj1.getRawData());
        obj1.setSex("f");
        assertEquals("f", obj1.getSex());
        obj1.setSignature("sig");
        assertEquals("sig", obj1.getSignature());
        obj1.setSpecialStatus("2");
        assertEquals("2", obj1.getSpecialStatus());
        obj1.setThirdName("f");
        assertEquals("f", obj1.getThirdName());
        obj1.setVersion(1);
        assertEquals(Integer.valueOf(1), obj1.getVersion());
        obj1 = MockResponseFactory.getGclBeIdRnData();
        GclBeIdRn obj2 = MockResponseFactory.getGclBeIdRnData();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, "string");
    }
}