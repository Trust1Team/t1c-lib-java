package com.t1t.t1c.containers.smartcards.eid.esp;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.model.rest.GclDnieAllCertificates;
import com.t1t.t1c.model.rest.GclDnieAllData;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.services.FactoryService;
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
public class DnieContainerTest extends AbstractTestClass {

    private IDnieContainer dnieContainer;

    @Before
    public void initContainer() {
        dnieContainer = getClient().getDnieContainer(ContainerType.DNIE.getId());
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = dnieContainer.getAllDataFilters();
        GclDnieAllData data = (GclDnieAllData) dnieContainer.getAllData(filters);
        assertNotNull(data);
        assertNotNull(data.getInfo());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getIntermediateCertificate());
        assertNotNull(data.getSigningCertificate());
    }

    @Test
    public void getAllCertificates() throws Exception {
        List<String> filters = dnieContainer.getAllCertificateFilters();
        GclDnieAllCertificates data = (GclDnieAllCertificates) dnieContainer.getAllCertificates(filters);
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
    }

}