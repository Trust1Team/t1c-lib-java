package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
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
        container = getClient().getEmvContainer(new GclReader().withId(MockResponseFactory.EMV_READER_ID).withPinpad(true));
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

    @Test(expected = EmvContainerException.class)
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

    @Test(expected = EmvContainerException.class)
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

    @Test(expected = EmvContainerException.class)
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
}