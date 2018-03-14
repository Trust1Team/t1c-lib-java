package com.t1t.t1c.containers.smartcards.eid.pt;

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
import java.util.Map;

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
    public void testAddress() {
        GclPtIdAddress ad = MockResponseFactory.getPtIdAddress();
        GclPtIdAddress ad2 = MockResponseFactory.getPtIdAddress();
        assertEquals(ad, ad);
        assertEquals(ad.hashCode(), ad2.hashCode());
        assertEquals(ad, ad2);
        assertNotEquals(ad, "string");
        assertTrue(StringUtils.isNotEmpty(ad.toString()));
        ad.setAbbrBuildingType("a");
        assertEquals("a", ad.getAbbrBuildingType());
        ad.setAbbrStreetType("b");
        assertEquals("b", ad.getAbbrStreetType());
        ad.setBuildingType("c");
        assertEquals("c", ad.getBuildingType());
        ad.setCivilParish("d");
        assertEquals("d", ad.getCivilParish());
        ad.setCivilParishDescription("e");
        assertEquals("e", ad.getCivilParishDescription());
        ad.setDistrict("f");
        assertEquals("f", ad.getDistrict());
        ad.setDistrictDescription("g");
        assertEquals("g", ad.getDistrictDescription());
        ad.setDoorNo("h");
        assertEquals("h", ad.getDoorNo());
        ad.setFloor("i");
        assertEquals("i", ad.getFloor());
        ad.setGenAddressNum("j");
        assertEquals("j", ad.getGenAddressNum());
        ad.setIsNational(false);
        assertFalse(ad.getIsNational());
        ad.setLocality("l");
        assertEquals("l", ad.getLocality());
        ad.setMunicipality("m");
        assertEquals("m", ad.getMunicipality());
        ad.setMunicipalityDescription("n");
        assertEquals("n", ad.getMunicipalityDescription());
        ad.setPlace("o");
        assertEquals("o", ad.getPlace());
        ad.setPostalLocality("p");
        assertEquals("p", ad.getPostalLocality());
        ad.setRawData("q");
        assertEquals("q", ad.getRawData());
        ad.setSide("r");
        assertEquals("r", ad.getSide());
        ad.setStreetName("s");
        assertEquals("s", ad.getStreetName());
        ad.setStreetType("t");
        assertEquals("t", ad.getStreetType());
        ad.setType("u");
        assertEquals("u", ad.getType());
        ad.setZip3("v");
        assertEquals("v", ad.getZip3());
        ad.setZip4("w");
        assertEquals("w", ad.getZip4());
    }

    @Test
    public void testGclAllCertificates() {
        GclPtIdAllCertificates data = MockResponseFactory.getPtIdAllCertificates();
        GclPtIdAllCertificates data2 = MockResponseFactory.getPtIdAllCertificates();
        assertEquals(data, data);
        assertEquals(data.hashCode(), data2.hashCode());
        assertEquals(data, data2);
        assertNotEquals(data, "string");
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        data.setRootNonRepudiationCertificate("cert");
        assertEquals("cert", data.getRootNonRepudiationCertificate());
        assertEquals(data.withRootNonRepudiationCertificate("c"), data);
        data.setAuthenticationCertificate("cert");
        assertEquals("cert", data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate("c"), data);
        data.setNonRepudiationCertificate("cert");
        assertEquals("cert", data.getNonRepudiationCertificate());
        assertEquals(data.withNonRepudiationCertificate("c"), data);
        data.setRootAuthenticationCertificate("cert");
        assertEquals("cert", data.getRootAuthenticationCertificate());
        assertEquals(data.withRootAuthenticationCertificate("c"), data);
        data.setRootCertificate("cert");
        assertEquals("cert", data.getRootCertificate());
        assertEquals(data.withRootCertificate("c"), data);
    }

    @Test
    public void testGclAllData() {
        GclPtIdAllData data = MockResponseFactory.getPtIdAllData();
        GclPtIdAllData data2 = MockResponseFactory.getPtIdAllData();
        assertEquals(data, data);
        assertEquals(data.hashCode(), data2.hashCode());
        assertEquals(data, data2);
        assertNotEquals(data, "string");
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        data.setRootNonRepudiationCertificate("cert");
        assertEquals("cert", data.getRootNonRepudiationCertificate());
        assertEquals(data.withRootNonRepudiationCertificate("c"), data);
        data.setAuthenticationCertificate("cert");
        assertEquals("cert", data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate("c"), data);
        data.setNonRepudiationCertificate("cert");
        assertEquals("cert", data.getNonRepudiationCertificate());
        assertEquals(data.withNonRepudiationCertificate("c"), data);
        data.setRootAuthenticationCertificate("cert");
        assertEquals("cert", data.getRootAuthenticationCertificate());
        assertEquals(data.withRootAuthenticationCertificate("c"), data);
        data.setRootCertificate("cert");
        assertEquals("cert", data.getRootCertificate());
        assertEquals(data.withRootCertificate("c"), data);
        GclPtIdData id = new GclPtIdData().withAccidentalIndications("a");
        data.setId(id);
        assertEquals(id, data.getId());
        assertEquals(data.withId(new GclPtIdData()), data);
    }

    @Test
    public void testAllCertificates() {
        PtIdAllCertificates data = new PtIdAllCertificates(MockResponseFactory.getPtIdAllCertificates());
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        T1cCertificate newCert = new T1cCertificate().withBase64("cer");
        T1cCertificate other = new T1cCertificate().withBase64("other");
        data.setRootNonRepudiationCertificate(newCert);
        assertEquals(newCert, data.getRootNonRepudiationCertificate());
        assertEquals(data.withRootNonRepudiationCertificate(other), data);
        data.setAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate(other), data);
        data.setNonRepudiationCertificate(newCert);
        assertEquals(newCert, data.getNonRepudiationCertificate());
        assertEquals(data.withNonRepudiationCertificate(other), data);
        data.setRootAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getRootAuthenticationCertificate());
        assertEquals(data.withRootAuthenticationCertificate(other), data);
        data.setRootCertificate(newCert);
        assertEquals(newCert, data.getRootCertificate());
        assertEquals(data.withRootCertificate(other), data);
    }

    @Test
    public void testAllData() {
        PtIdAllData data = new PtIdAllData(MockResponseFactory.getPtIdAllData());
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        T1cCertificate newCert = new T1cCertificate().withBase64("cer");
        T1cCertificate other = new T1cCertificate().withBase64("other");
        data.setRootNonRepudiationCertificate(newCert);
        assertEquals(newCert, data.getRootNonRepudiationCertificate());
        assertEquals(data.withRootNonRepudiationCertificate(other), data);
        data.setAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getAuthenticationCertificate());
        assertEquals(data.withAuthenticationCertificate(other), data);
        data.setNonRepudiationCertificate(newCert);
        assertEquals(newCert, data.getNonRepudiationCertificate());
        assertEquals(data.withNonRepudiationCertificate(other), data);
        data.setRootAuthenticationCertificate(newCert);
        assertEquals(newCert, data.getRootAuthenticationCertificate());
        assertEquals(data.withRootAuthenticationCertificate(other), data);
        data.setRootCertificate(newCert);
        assertEquals(newCert, data.getRootCertificate());
        assertEquals(data.withRootCertificate(other), data);
        GclPtIdData id = new GclPtIdData().withAccidentalIndications("a");
        data.setId(id);
        assertEquals(id, data.getId());
        assertEquals(data.withId(new GclPtIdData()), data);
    }

    @Test
    public void testPtIdData() {
        GclPtIdData data = MockResponseFactory.getPtIdData();
        GclPtIdData data2 = MockResponseFactory.getPtIdData();
        assertEquals(data, data);
        assertEquals(data.hashCode(), data2.hashCode());
        assertEquals(data, data2);
        assertNotEquals(data, "string");
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        data.setPhoto("s");
        assertEquals("s", data.getPhoto());
        assertEquals(data.withPhoto("s2"), data);
        data.setAccidentalIndications("1");
        assertEquals("1", data.getAccidentalIndications());
        data.setCivilianNumber("2");
        assertEquals("2", data.getCivilianNumber());
        data.setCountry("3");
        assertEquals("3", data.getCountry());
        data.setDateOfBirth("4");
        assertEquals("4", data.getDateOfBirth());
        data.setDocumentNumber("5");
        assertEquals("5", data.getDocumentNumber());
        data.setDocumentNumberPan("6");
        assertEquals("6", data.getDocumentNumberPan());
        data.setDocumentType("7");
        assertEquals("7", data.getDocumentType());
        data.setDocumentVersion("8");
        assertEquals("8", data.getDocumentVersion());
        data.setGender("9");
        assertEquals("9", data.getGender());
        data.setGivenNameFather("10");
        assertEquals("10", data.getGivenNameFather());
        data.setGivenNameMother("11");
        assertEquals("11", data.getGivenNameMother());
        data.setHealthNo("12");
        assertEquals("12", data.getHealthNo());
        data.setHeight("13");
        assertEquals("13", data.getHeight());
        data.setIssuingEntity("14");
        assertEquals("14", data.getIssuingEntity());
        data.setLocalOfRequest("15");
        assertEquals("15", data.getLocalOfRequest());
        data.setMrz1("16");
        assertEquals("16", data.getMrz1());
        data.setMrz2("17");
        assertEquals("17", data.getMrz2());
        data.setMrz3("18");
        assertEquals("18", data.getMrz3());
        data.setName("19");
        assertEquals("19", data.getName());
        data.setNationality("20");
        assertEquals("20", data.getNationality());
        data.setRawData("21");
        assertEquals("21", data.getRawData());
        data.setSurname("22");
        assertEquals("22", data.getSurname());
        data.setSocialSecurityNo("23");
        assertEquals("23", data.getSocialSecurityNo());
        data.setSurnameFather("24");
        assertEquals("24", data.getSurnameFather());
        data.setSurnameMother("25");
        assertEquals("25", data.getSurnameMother());
        data.setTaxNo("26");
        assertEquals("26", data.getTaxNo());
        data.setValidityBeginDate("27");
        assertEquals("27", data.getValidityBeginDate());
        data.setValidityEndDate("28");
        assertEquals("28", data.getValidityEndDate());
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

        assertNotNull(data.getStreetAndNumber());
        assertNotNull(data.getMunicipality());
        assertNotNull(data.getZipCode());

        assertNotNull(data.getNationality());
        assertNotNull(data.getBase64Picture());
        assertNotNull(data.getValidityStartDate());
        assertNotNull(data.getValidityEndDate());
        assertNotNull(data.getDocumentId());

        assertNotNull(data.getAuthenticationCertificateChain());
        assertNotNull(data.getSigningCertificateChain());
        assertNotNull(data.getCertificateChains());
        assertNotNull(data.getAllCertificates());
    }
}