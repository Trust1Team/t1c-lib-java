package com.t1t.t1c.containers.smartcards.eid.esp;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieAllData;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.factories.ConnectionFactory;
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
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class DnieContainerTest extends AbstractTestClass {

    /*private IDnieContainer dnieContainer;

    @Before
    public void initContainer() {
        dnieContainer = getClient().getDnieContainer(ContainerType.DNIE.getId());
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = dnieContainer.getAllDataFilters();
        DnieAllData data = (DnieAllData) dnieContainer.getAllData(filters);
        assertNotNull(data);
        assertNotNull(data.getInfo());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getIntermediateCertificate());
        assertNotNull(data.getSigningCertificate());
    }

    @Test
    public void getAllCertificates() throws Exception {
        List<String> filters = dnieContainer.getAllCertificateFilters();
        DnieAllCertificates data = (DnieAllCertificates) dnieContainer.getAllCertificates(filters);
        assertNotNull(data);
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getIntermediateCertificate());
        assertNotNull(data.getSigningCertificate());
    }

    @Test
    public void getInfo() throws Exception {
        assertNotNull(dnieContainer.getInfo());
    }

    @Test
    public void getIntermediateCertificate() throws Exception {
        T1cCertificate cert = dnieContainer.getIntermediateCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        assertNotNull(dnieContainer.getAuthenticationCertificate(false));
    }

    @Test
    public void getSigningCertificate() throws Exception {
        assertNotNull(dnieContainer.getSigningCertificate(false));
    }*/

}