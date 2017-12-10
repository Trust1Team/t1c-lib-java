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
}