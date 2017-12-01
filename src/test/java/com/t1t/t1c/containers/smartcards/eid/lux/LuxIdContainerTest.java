package com.t1t.t1c.containers.smartcards.eid.lux;

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
public class LuxIdContainerTest extends AbstractTestClass {

    /*private ILuxIdContainer luxIdContainer;

    @Before
    public void initContainer() {
        luxIdContainer = getClient().getLuxIdContainer(ContainerType.LUXID.getId(), "1234");
    }

    @Test
    public void getBiometric() throws Exception {
        GclLuxIdBiometric bio = luxIdContainer.getBiometric();
        assertNotNull(bio);
        assertNotNull(bio.getBirthDate());
        assertNotNull(bio.getDocumentNumber());
        assertNotNull(bio.getDocumentType());
        assertNotNull(bio.getFirstName());
        assertNotNull(bio.getLastName());
        assertNotNull(bio.getGender());
        assertNotNull(bio.getIssuingState());
        assertNotNull(bio.getNationality());
        assertNotNull(bio.getValidityEndDate());
        assertNotNull(bio.getValidityStartDate());
    }

    @Test
    public void getPicture() throws Exception {
        GclLuxIdPicture pic = luxIdContainer.getPicture();
        assertNotNull(pic);
        assertNotNull(pic.getHeight());
        assertNotNull(pic.getWidth());
        assertNotNull(pic.getImage());
        assertNotNull(pic.getRawData());
    }

    @Test
    public void getSignatureImage() throws Exception {
        GclLuxIdSignatureImage sig = luxIdContainer.getSignatureImage();
        assertNotNull(sig);
        assertNotNull(sig.getImage());
        assertNotNull(sig.getRawData());
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = luxIdContainer.getAllDataFilters();
        LuxIdAllData data = (LuxIdAllData) luxIdContainer.getAllData(filters);
        assertNotNull(data);
        assertNotNull(data.getNonRepudiationCertificate());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getRootCertificates());
        assertNotNull(data.getSignatureObject());
        assertNotNull(data.getSignatureImage());
        assertNotNull(data.getBiometric());
        assertNotNull(data.getPicture());
    }

    @Test
    public void getAllCertificates() throws Exception {
        List<String> filters = luxIdContainer.getAllCertificateFilters();
        LuxIdAllCertificates certs = (LuxIdAllCertificates) luxIdContainer.getAllCertificates(filters);
        assertNotNull(certs);
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getRootCertificates());
        assertNotNull(certs.getNonRepudiationCertificate());
    }

    @Test
    public void getRootCertificates() throws Exception {
        List<T1cCertificate> cert = luxIdContainer.getRootCertificates(true);
        assertTrue(CollectionUtils.isNotEmpty(cert));
    }

    @Test
    public void getAuthenticationCertificate() throws Exception {
        assertNotNull(luxIdContainer.getAuthenticationCertificate(false));
    }

    @Test
    public void getNonRepudiationCertificate() throws Exception {
        T1cCertificate cert = luxIdContainer.getNonRepudiationCertificate(true);
        assertTrue(StringUtils.isNotEmpty(cert.getBase64()));
        assertNotNull(cert.getParsed());
    }*/

}