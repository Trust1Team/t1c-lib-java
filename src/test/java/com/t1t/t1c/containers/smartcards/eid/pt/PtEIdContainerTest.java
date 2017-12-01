package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class PtEIdContainerTest extends AbstractTestClass {

    /*private IPtEIdContainer ptEIdContainer;

    @Before
    public void initContainer() {
        ptEIdContainer = getClient().getPtIdContainer(ContainerType.PT.getId());
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = ptEIdContainer.getAllDataFilters();
        PtIdAllData data = (PtIdAllData) ptEIdContainer.getAllData(filters);
        assertNotNull(data);
        assertNotNull(data.getNonRepudiationCertificate());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getRootNonRepudiationCertificate());
        assertNotNull(data.getRootAuthenticationCertificate());
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getId());
    }

    @Test
    public void getAllCertificates() throws Exception {
        List<String> filters = ptEIdContainer.getAllCertificateFilters();
        PtIdAllCertificates certs = (PtIdAllCertificates) ptEIdContainer.getAllCertificates(filters);
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getNonRepudiationCertificate());
        assertNotNull(certs.getRootNonRepudiationCertificate());
        assertNotNull(certs.getRootAuthenticationCertificate());
    }

    @Test
    public void getIdData() throws Exception {
        GclPtIdData data = ptEIdContainer.getIdData();
        assertNotNull(data);
        assertNotNull(data.getPhoto());
    }

    @Test
    public void getIdDataWithOutPhoto() throws Exception {
        GclPtIdData data = ptEIdContainer.getIdData();
        assertNotNull(data);
        assertNotNull(data.getPhoto());
    }

    @Test
    public void getPhoto() throws Exception {
        assertNotNull(ptEIdContainer.getPhoto());
    }

    @Test
    public void getRootCertificate() throws Exception {
        assertNotNull(ptEIdContainer.getRootCertificate(false));
    }

    @Test
    public void getRootAuthenticationCertificate() throws Exception {
        T1cCertificate cert = ptEIdContainer.getRootAuthenticationCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getRootNonRepudiationCertificate() throws Exception {
        assertNotNull(ptEIdContainer.getRootNonRepudiationCertificate(false));
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        assertNotNull(ptEIdContainer.getAuthenticationCertificate(false));
    }

    @Test
    public void getNonRepudiationCertificate() throws Exception {
        assertNotNull(ptEIdContainer.getNonRepudiationCertificate(false));
    }*/

}