package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class EmvContainerTest extends AbstractTestClass {

    private EmvContainer container;

    @Before
    public void initContainer() {
        container = getClient().getEmvContainer(new GclReader().withId(MockResponseFactory.EMV_READER_ID).withPinpad(true), "v2.0.0");
    }

    @Test
    public void getApplications() {
        List<GclEmvApplication> apps = container.getApplications();
        assertTrue(CollectionUtils.isNotEmpty(apps));
        for (GclEmvApplication app : apps) {
            assertNotNull(app);
            assertNotNull(app.getAid());
            assertNotNull(app.getLabel());
            assertNotNull(app.getPriority());
        }
    }

    @Test
    public void getApplicationData() {
        GclEmvApplicationData data = container.getApplicationData();
        assertNotNull(data);
        assertNotNull(data.getCountry());
        assertNotNull(data.getCountryCode());
        assertNotNull(data.getEffectiveDate());
        assertNotNull(data.getExpirationDate());
        assertNotNull(data.getLanguage());
        assertNotNull(data.getName());
        assertNotNull(data.getPan());
    }

    @Test
    public void getIccPublicKeyCertificate() {
        GclEmvPublicKeyCertificate cert = container.getIccPublicKeyCertificate("A0000000048002");
        assertNotNull(cert);
        assertNotNull(cert.getData());
        assertNotNull(cert.getExponent());
        assertNotNull(cert.getRemainder());
    }

    @Test(expected = RestException.class)
    public void getIccPublicKeyCertificateWithWrongAid() {
        container.getIccPublicKeyCertificate("A0000000048");
    }

    @Test
    public void getIssuerPublicKeyCertificate() {
        GclEmvPublicKeyCertificate cert = container.getIssuerPublicKeyCertificate("A0000000048002");
        assertNotNull(cert);
        assertNotNull(cert.getData());
        assertNotNull(cert.getExponent());
        assertNotNull(cert.getRemainder());
    }

    @Test(expected = RestException.class)
    public void getIssuerPublicKeyCertificateWithWrongAid() {
        container.getIssuerPublicKeyCertificate("A0000000048");
    }

    @Test
    public void getAllDataFilters() {
        assertNotNull(container.getAllDataFilters());
    }

    @Test
    public void getAllCertificateFilters() {
        assertTrue(CollectionUtils.isEmpty(container.getAllCertificateFilters()));
    }

    @Test
    public void getAllData() {
        GclEmvAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getApplicationData());
        assertNotNull(data.getApplications());
    }

    @Test
    public void getAllDataFiltered() {
        GclEmvAllData data = container.getAllData(Collections.singletonList("applications"));
        assertNotNull(data);
        assertNull(data.getApplicationData());
        assertNotNull(data.getApplications());
    }

    @Test(expected = RestException.class)
    public void getAllDataRestException() {
        container.getAllData(Collections.singletonList("throwException"));
    }

    @Test
    public void getAllDataParsed() {
        assertNotNull(container.getAllData(true));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() {
        container.getAllCertificates();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesFiltered() {
        container.getAllCertificates(Collections.singletonList("cert"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesParsed() {
        container.getAllCertificates(true);
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

    @Test(expected = UnsupportedOperationException.class)
    public void authenticate() {
        container.authenticate(null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sign() {
        container.sign(null, null);
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.EMV, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.EMV.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(GclEmvAllData.class, container.getAllDataClass());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificateClass() {
        assertEquals(AllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void testAidRequest() {
        GclEmvAidRequest obj = new GclEmvAidRequest();
        GclEmvAidRequest obj2 = new GclEmvAidRequest();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        obj.setAid("2");
        assertEquals("2", obj.getAid());
    }

    @Test
    public void testAllData() {
        GclEmvAllData obj = new GclEmvAllData();
        GclEmvAllData obj2 = new GclEmvAllData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        GclEmvApplicationData appData = new GclEmvApplicationData();
        obj.setApplicationData(appData);
        assertEquals(appData, obj.getApplicationData());
        List<GclEmvApplication> apps = Collections.singletonList(new GclEmvApplication());
        obj.setApplications(apps);
        assertEquals(apps, obj.getApplications());
    }

    @Test
    public void testApplication() {
        GclEmvApplication obj = new GclEmvApplication();
        GclEmvApplication obj2 = new GclEmvApplication();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        obj.setAid("a");
        assertEquals("a", obj.getAid());
        obj.setLabel("l");
        assertEquals("l", obj.getLabel());
        obj.setPriority(1L);
        assertEquals(Long.valueOf(1), obj.getPriority());
    }

    @Test
    public void testApplicationData() {
        GclEmvApplicationData obj = new GclEmvApplicationData();
        GclEmvApplicationData obj2 = new GclEmvApplicationData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        obj.setCountry("c");
        assertEquals("c", obj.getCountry());
        obj.setCountryCode("cc");
        assertEquals("cc", obj.getCountryCode());
        obj.setEffectiveDate("e");
        assertEquals("e", obj.getEffectiveDate());
        obj.setExpirationDate("ex");
        assertEquals("ex", obj.getExpirationDate());
        obj.setName("n");
        assertEquals("n", obj.getName());
        obj.setPan("p");
        assertEquals("p", obj.getPan());
        obj.setLanguage("l");
        assertEquals("l", obj.getLanguage());
    }

    @Test
    public void testPubKeyCertificate() {
        GclEmvPublicKeyCertificate obj = new GclEmvPublicKeyCertificate();
        GclEmvPublicKeyCertificate obj2 = new GclEmvPublicKeyCertificate();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        obj.setData("d");
        assertEquals("d", obj.getData());
        obj.setExponent("e");
        assertEquals("e", obj.getExponent());
        obj.setRemainder("r");
        assertEquals("r", obj.getRemainder());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSigningCertificateChain() {
        container.getSigningCertificateChain();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAuthenticationCertificateChain() {
        container.getAuthenticationCertificateChain();
    }

    @Test
    public void testGenericDataDump() {
        ContainerData data = container.dumpData();
        assertNotNull(data);

        assertNotNull(data.getFullName());
        assertNotNull(data.getCountry());

        assertNotNull(data.getValidityStartDate());
        assertNotNull(data.getValidityEndDate());
        assertNotNull(data.getDocumentId());
    }
}