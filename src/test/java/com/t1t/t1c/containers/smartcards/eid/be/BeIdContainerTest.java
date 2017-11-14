package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.model.rest.GclBeIdAddress;
import com.t1t.t1c.model.rest.GclBeIdRn;
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
public class BeIdContainerTest extends AbstractTestClass {

    private IBeIdContainer beIdContainer;

    @Before
    public void initContainer() {
        beIdContainer = getClient().getBeIdContainer(ContainerType.BEID.getId());
    }

    @Test
    public void getRnData() throws Exception {
        GclBeIdRn rn = beIdContainer.getRnData();
        assertNotNull(rn);
    }

    @Test
    public void getAddress() throws Exception {
        GclBeIdAddress address = beIdContainer.getAddress();
        assertNotNull(address);
    }

    @Test
    public void getPicture() throws Exception {
        String picture = beIdContainer.getPicture();
        assertTrue(StringUtils.isNotEmpty(picture));
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = beIdContainer.getAllDataFilters();
        BeIdAllData allData = (BeIdAllData) beIdContainer.getAllData(filters);
        assertNotNull(allData);
        assertTrue(StringUtils.isNotEmpty(allData.getAuthenticationCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allData.getRootCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allData.getCitizenCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allData.getNonRepudiationCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allData.getRrnCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allData.getPicture()));
        assertNotNull(allData.getRn());
        assertNotNull(allData.getAddress());
    }

    @Test
    public void getAllCertificates() throws Exception {
        List<String> filters = beIdContainer.getAllCertificateFilters();
        BeIdAllCertificates allCerts = (BeIdAllCertificates) beIdContainer.getAllCertificates(filters);
        assertNotNull(allCerts);
        assertTrue(StringUtils.isNotEmpty(allCerts.getAuthenticationCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allCerts.getRootCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allCerts.getCitizenCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allCerts.getNonRepudiationCertificate().getBase64()));
        assertTrue(StringUtils.isNotEmpty(allCerts.getRrnCertificate().getBase64()));
    }

    @Test
    public void getRootCertificate() throws Exception {
        assertTrue(StringUtils.isNotEmpty(beIdContainer.getRootCertificate(false).getBase64()));
    }

    @Test
    public void getCitizenCertificate() throws Exception {
        assertTrue(StringUtils.isNotEmpty(beIdContainer.getCitizenCertificate(false).getBase64()));
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        assertTrue(StringUtils.isNotEmpty(beIdContainer.getAuthenticationCertificate(false).getBase64()));
    }

    @Test
    public void getNonRepudiationCertificate() throws Exception {
        T1cCertificate cert = beIdContainer.getNonRepudiationCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getRrnCertificate() throws Exception {
        assertTrue(StringUtils.isNotEmpty(beIdContainer.getRrnCertificate(false).getBase64()));
    }

}