package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.factories.ConnectionFactory;
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
        container = getClient().getBeIdContainer(new GclReader().withId("1111").withPinpad(false));
    }

    @Test
    public void getRnData() throws Exception {
        GclBeIdRn rn = container.getRnData();
        assertNotNull(rn);
    }

    @Test
    public void getBeIdAddress() throws Exception {
        GclBeIdAddress address = container.getBeIdAddress();
        assertNotNull(address);
    }

    @Test
    public void getBeIdPicture() throws Exception {
        String picture = container.getBeIdPicture();
        assertNotNull(picture);
        assertTrue(Base64.isBase64(picture));
    }

    @Test
    public void getRootCertificate() throws Exception {
        T1cCertificate cert = container.getRootCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getRootCertificateParsed() throws Exception {
        T1cCertificate cert = container.getRootCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getCitizenCertificate() throws Exception {
        T1cCertificate cert = container.getCitizenCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getCitizenCertificateParsed() throws Exception {
        T1cCertificate cert = container.getCitizenCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificate() throws Exception {
        T1cCertificate cert = container.getCitizenCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getNonRepudiationCertificateParsed() throws Exception {
        T1cCertificate cert = container.getCitizenCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        T1cCertificate cert = container.getAuthenticationCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificateParsed() throws Exception {
        T1cCertificate cert = container.getAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getRrnCertificate() throws Exception {
        T1cCertificate cert = container.getRrnCertificate();
        assertNotNull(cert);
        assertNull(cert.getParsed());
    }

    @Test
    public void getRrnCertificateParsed() throws Exception {
        T1cCertificate cert = container.getRrnCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getParsed());
    }

    @Test
    public void createInstance() throws Exception {
    }

    @Test
    public void getAllDataFilters() throws Exception {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllDataFilters()));
    }

    @Test
    public void getAllCertificateFilters() throws Exception {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllCertificateFilters()));
    }

    @Test
    public void getAllData() throws Exception {
        BeIdAllData data = (BeIdAllData) container.getAllData();
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
    public void getAllDataWithFilters() throws Exception {
        BeIdAllData data = (BeIdAllData) container.getAllData(Arrays.asList("rn", "root-certificate"));
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
    public void getAllDataWithParsing() throws Exception {
        BeIdAllData data = (BeIdAllData) container.getAllData(Arrays.asList("root-certificate"), true);
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() throws Exception {
        BeIdAllCertificates certs = (BeIdAllCertificates) container.getAllCertificates();
        assertNotNull(certs);
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getCitizenCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRrnCertificate());
    }

    @Test
    public void getAllCertificatesWithFilter() throws Exception {
        BeIdAllCertificates certs = (BeIdAllCertificates) container.getAllCertificates(Arrays.asList("non-repudiation-certificate", "citizen-certificate"));
        assertNotNull(certs);
        assertNull(certs.getRootCertificate());
        assertNotNull(certs.getCitizenCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
        assertNull(certs.getAuthenticationCertificate());
        assertNull(certs.getRrnCertificate());
    }

    @Test
    public void getAllCertificatesWithParsing() throws Exception {
        BeIdAllCertificates certs = (BeIdAllCertificates) container.getAllCertificates(Collections.singletonList("root-certificate"), true);
        assertNotNull(certs);
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getRootCertificate().getParsed());
    }

    @Test
    public void verifyPin() throws Exception {
        assertTrue(container.verifyPin("1111"));
    }

    @Test
    public void authenticate() throws Exception {
        String authenticatedHash = container.sign(new GclAuthenticateOrSignData()
                .withData("ehlWXR2mz8/m04On93dZ5w==").withAlgorithmReference("sha256").withPin("1111"));
        assertNotNull(authenticatedHash);
    }

    @Test
    public void sign() throws Exception {
        String signedHash = container.sign(new GclAuthenticateOrSignData()
                .withData("ehlWXR2mz8/m04On93dZ5w==").withAlgorithmReference("sha256").withPin("1111"));
        assertNotNull(signedHash);
    }

    @Test
    public void getType() throws Exception {
        assertNotNull(container.getType());
        assertEquals(ContainerType.BEID, container.getType());
    }

    @Test
    public void getTypeId() throws Exception {
        assertNotNull(container.getTypeId());
        assertEquals(ContainerType.BEID.getId(), container.getTypeId());
    }
}