package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.model.rest.GclLuxTrustAllCertificates;
import com.t1t.t1c.model.rest.GclLuxTrustAllData;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, FactoryService.class})
public class LuxTrustContainerTest extends AbstractTestClass {

    private ILuxTrustContainer luxTrustContainer;

    @Before
    public void initContainer() {
        luxTrustContainer = getClient().getLuxTrustContainer(ContainerType.LUXTRUST.getId(), "1234");
    }

    @Test
    public void isLuxTrustActivated() throws Exception {
        assertTrue(luxTrustContainer.isLuxTrustActivated());
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = luxTrustContainer.getAllDataFilters();
        GclLuxTrustAllData data = (GclLuxTrustAllData) luxTrustContainer.getAllData(filters);
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getNonRepudiationCertificate());
        assertNotNull(data.getRootCertificates());
    }

    @Test
    public void getAllCertificates() throws Exception {
        List<String> filters = luxTrustContainer.getAllCertificateFilters();
        GclLuxTrustAllCertificates data = (GclLuxTrustAllCertificates) luxTrustContainer.getAllCertificates(filters);
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getNonRepudiationCertificate());
        assertNotNull(data.getRootCertificates());
    }

    @Test
    public void getRootCertificate() throws Exception {
        T1cCertificate cert = luxTrustContainer.getRootCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        assertNotNull(luxTrustContainer.getAuthenticationCertificate(false));
    }

    @Test
    public void getSigningCertificate() throws Exception {
        T1cCertificate cert = luxTrustContainer.getSigningCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
    }

}