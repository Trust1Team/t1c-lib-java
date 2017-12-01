package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class BeIdContainerTest extends AbstractTestClass {

    private BeIdContainer container;

    @Before
    public void init() {
        //container = getClient().getBeIdContainer(ContainerType.BEID.getId());
    }

    @Test
    public void getRnData() throws Exception {
    }

    @Test
    public void getBeIdAddress() throws Exception {
    }

    @Test
    public void getBeIdPicture() throws Exception {
    }

    @Test
    public void getRootCertificate() throws Exception {
    }

    @Test
    public void getCitizenCertificate() throws Exception {
    }

    @Test
    public void getNonRepudiationCertificate() throws Exception {
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
    }

    @Test
    public void getRrnCertificate() throws Exception {
    }

    @Test
    public void createInstance() throws Exception {
    }

    @Test
    public void getAllDataFilters() throws Exception {
    }

    @Test
    public void getAllCertificateFilters() throws Exception {
    }

    @Test
    public void getAllData() throws Exception {
    }

    @Test
    public void getAllDataWithFilters() throws Exception {
    }

    @Test
    public void getAllDataWithParsing() throws Exception {
    }

    @Test
    public void getAllCertificates() throws Exception {
    }

    @Test
    public void getAllCertificatesWithFilter() throws Exception {
    }

    @Test
    public void getAllCertificatesWithParsing() throws Exception {
    }

    @Test
    public void verifyPin() throws Exception {
    }

    @Test
    public void authenticate() throws Exception {
    }

    @Test
    public void sign() throws Exception {
    }

    @Test
    public void getType() throws Exception {
    }

    @Test
    public void getTypeId() throws Exception {
    }

}