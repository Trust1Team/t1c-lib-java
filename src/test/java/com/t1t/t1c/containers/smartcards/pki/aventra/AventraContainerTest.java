package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class AventraContainerTest extends AbstractTestClass {

    private AventraContainer container;

    @Before
    public void initContainer() {
        container = getClient().getAventraContainer(new GclReader().withId(MockResponseFactory.AVENTRA_READER_ID).withPinpad(true));
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
        AventraAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getAppletInfo());
        assertNotNull(data.getIssuerCertificate());
        assertNull(data.getIssuerCertificate().getParsed());
        assertNotNull(data.getIssuerCertificate().getBase64());
        assertNotNull(data.getSigningCertificate());
        assertNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNotNull(data.getAuthenticationCertificate());
        assertNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNotNull(data.getRootCertificate());
        assertNull(data.getRootCertificate().getParsed());
        assertNotNull(data.getRootCertificate().getBase64());
        assertNotNull(data.getEncryptionCertificate());
        assertNotNull(data.getEncryptionCertificate().getBase64());
        assertNull(data.getEncryptionCertificate().getParsed());
    }

    @Test
    public void getAllDataFiltered() {
        AventraAllData data = container.getAllData(Arrays.asList("applet-info", "root-certificate"));
        assertNotNull(data);
        assertNotNull(data.getAppletInfo());
        assertNotNull(data.getRootCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getSigningCertificate());
        assertNull(data.getIssuerCertificate());
        assertNull(data.getEncryptionCertificate());
    }

    @Test
    public void getAllDataParsed() {
        AventraAllData data = container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getAppletInfo());
        assertNotNull(data.getIssuerCertificate());
        assertNotNull(data.getIssuerCertificate().getParsed());
        assertNotNull(data.getIssuerCertificate().getBase64());
        assertNotNull(data.getSigningCertificate());
        assertNotNull(data.getSigningCertificate().getParsed());
        assertNotNull(data.getSigningCertificate().getBase64());
        assertNotNull(data.getAuthenticationCertificate());
        assertNotNull(data.getAuthenticationCertificate().getParsed());
        assertNotNull(data.getAuthenticationCertificate().getBase64());
        assertNotNull(data.getRootCertificate());
        assertNotNull(data.getRootCertificate().getParsed());
        assertNotNull(data.getRootCertificate().getBase64());
        assertNotNull(data.getEncryptionCertificate());
        assertNotNull(data.getEncryptionCertificate().getBase64());
        assertNotNull(data.getEncryptionCertificate().getParsed());
    }

    @Test
    public void getAllCertificates() {
        AventraAllCertificates certs = container.getAllCertificates();
        assertNotNull(certs.getIssuerCertificate());
        assertNull(certs.getIssuerCertificate().getParsed());
        assertNotNull(certs.getIssuerCertificate().getBase64());
        assertNotNull(certs.getSigningCertificate());
        assertNull(certs.getSigningCertificate().getParsed());
        assertNotNull(certs.getSigningCertificate().getBase64());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNull(certs.getAuthenticationCertificate().getParsed());
        assertNotNull(certs.getAuthenticationCertificate().getBase64());
        assertNotNull(certs.getRootCertificate());
        assertNull(certs.getRootCertificate().getParsed());
        assertNotNull(certs.getRootCertificate().getBase64());
        assertNotNull(certs.getEncryptionCertificate());
        assertNotNull(certs.getEncryptionCertificate().getBase64());
        assertNull(certs.getEncryptionCertificate().getParsed());
    }

    @Test
    public void getAllCertificatesFiltered() {
        AventraAllCertificates data = container.getAllCertificates(Arrays.asList("encryption-certificate", "root-certificate"));
        assertNotNull(data);
        assertNotNull(data.getRootCertificate());
        assertNull(data.getAuthenticationCertificate());
        assertNull(data.getSigningCertificate());
        assertNull(data.getIssuerCertificate());
        assertNotNull(data.getEncryptionCertificate());
    }

    @Test
    public void getAllCertificatesParsed() {
        AventraAllCertificates certs = container.getAllCertificates(true);
        assertNotNull(certs);
        assertNotNull(certs.getIssuerCertificate());
        assertNotNull(certs.getIssuerCertificate().getParsed());
        assertNotNull(certs.getIssuerCertificate().getBase64());
        assertNotNull(certs.getSigningCertificate());
        assertNotNull(certs.getSigningCertificate().getParsed());
        assertNotNull(certs.getSigningCertificate().getBase64());
        assertNotNull(certs.getAuthenticationCertificate());
        assertNotNull(certs.getAuthenticationCertificate().getParsed());
        assertNotNull(certs.getAuthenticationCertificate().getBase64());
        assertNotNull(certs.getRootCertificate());
        assertNotNull(certs.getRootCertificate().getParsed());
        assertNotNull(certs.getRootCertificate().getBase64());
        assertNotNull(certs.getEncryptionCertificate());
        assertNotNull(certs.getEncryptionCertificate().getBase64());
        assertNotNull(certs.getEncryptionCertificate().getParsed());
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
    public void authenticateWithHardwarePinPad() {
        assertNotNull(container.authenticate("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256));
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
    public void signWithHardwarePinPad() {
        assertNotNull(container.sign("ehlWXR2mz8/m04On93dZ5w==", DigestAlgorithm.SHA256));
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.AVENTRA, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.AVENTRA.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(AventraAllData.class, container.getAllDataClass());
    }

    @Test
    public void getAllCertificatesClass() {
        assertEquals(AventraAllCertificates.class, container.getAllCertificatesClass());
    }

    @Test
    public void getAllKeyRefs() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllKeyRefs()));
    }

    @Test
    public void getAllAlgoRefsForAuthentication() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllAlgoRefsForAuthentication()));
    }

    @Test
    public void getAllAlgoRefsForSigning() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllAlgoRefsForSigning()));
    }

    @Test
    public void getRootCertificate() {
        T1cCertificate cert = container.getRootCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getRootCertificateParsed() {
        T1cCertificate cert = container.getRootCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getIssuerCertificate() {
        T1cCertificate cert = container.getIssuerCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getIssuerCertificateParsed() {
        T1cCertificate cert = container.getIssuerCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificate() {
        T1cCertificate cert = container.getAuthenticationCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getAuthenticationCertificateParsed() {
        T1cCertificate cert = container.getAuthenticationCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getSigningCertificate() {
        T1cCertificate cert = container.getSigningCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getSigningCertificateParsed() {
        T1cCertificate cert = container.getSigningCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void getEncryptionCertificate() {
        T1cCertificate cert = container.getEncryptionCertificate();
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNull(cert.getParsed());
    }

    @Test
    public void getEncryptionCertificateParsed() {
        T1cCertificate cert = container.getEncryptionCertificate(true);
        assertNotNull(cert);
        assertNotNull(cert.getBase64());
        assertNotNull(cert.getParsed());
    }

    @Test
    public void resetPin() {
        assertTrue(container.resetPin("1111", "1111", "sign"));
    }

    @Test(expected = VerifyPinException.class)
    public void resetPinWithWrongPuk() {
        assertTrue(container.resetPin("1112", "1111", "sign"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resetPinWithWrongKeyRef() {
        assertTrue(container.resetPin("1111", "1111", "wrong"));
    }

    @Test
    public void testAllCertificates() {
        AventraAllCertificates obj = new AventraAllCertificates(MockResponseFactory.getGclAventraAllCertificates());
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        T1cCertificate newCert = new T1cCertificate().withBase64("new");
        obj.setAuthenticationCertificate(newCert);
        assertEquals(newCert, obj.getAuthenticationCertificate());
        obj.setRootCertificate(newCert);
        assertEquals(newCert, obj.getRootCertificate());
        obj.setIssuerCertificate(newCert);
        assertEquals(newCert, obj.getIssuerCertificate());
        obj.setEncryptionCertificate(newCert);
        assertEquals(newCert, obj.getEncryptionCertificate());
        obj.setSigningCertificate(newCert);
        assertEquals(newCert, obj.getSigningCertificate());
    }

    @Test
    public void testAllData() {
        AventraAllData obj = new AventraAllData(MockResponseFactory.getGclAventraAllData());
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        T1cCertificate newCert = new T1cCertificate().withBase64("new");
        obj.setAuthenticationCertificate(newCert);
        assertEquals(newCert, obj.getAuthenticationCertificate());
        obj.setRootCertificate(newCert);
        assertEquals(newCert, obj.getRootCertificate());
        obj.setIssuerCertificate(newCert);
        assertEquals(newCert, obj.getIssuerCertificate());
        obj.setEncryptionCertificate(newCert);
        assertEquals(newCert, obj.getEncryptionCertificate());
        obj.setSigningCertificate(newCert);
        assertEquals(newCert, obj.getSigningCertificate());
        GclAventraAppletInfo info = new GclAventraAppletInfo().withChangeCounter(1);
        obj.setAppletInfo(info);
        assertEquals(info, obj.getAppletInfo());
    }

    @Test
    public void testGclAllCertificates() {
        GclAventraAllCertificates obj = new GclAventraAllCertificates();
        GclAventraAllCertificates obj2 = new GclAventraAllCertificates();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setRootCertificate("r");
        assertEquals("r", obj.getRootCertificate());
        obj.setIssuerCertificate("i");
        assertEquals("i", obj.getIssuerCertificate());
        obj.setAuthenticationCertificate("a");
        assertEquals("a", obj.getAuthenticationCertificate());
        obj.setSigningCertificate("s");
        assertEquals("s", obj.getSigningCertificate());
        obj.setEncryptionCertificate("e");
        assertEquals("e", obj.getEncryptionCertificate());
    }

    @Test
    public void testGclAllData() {
        GclAventraAllData obj = new GclAventraAllData();
        GclAventraAllData obj2 = new GclAventraAllData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        GclAventraAppletInfo info = new GclAventraAppletInfo().withChangeCounter(1);
        obj.setAppletInfo(info);
        assertEquals(info, obj.getAppletInfo());
        obj.setRootCertificate("r");
        assertEquals("r", obj.getRootCertificate());
        obj.setIssuerCertificate("i");
        assertEquals("i", obj.getIssuerCertificate());
        obj.setAuthenticationCertificate("a");
        assertEquals("a", obj.getAuthenticationCertificate());
        obj.setSigningCertificate("s");
        assertEquals("s", obj.getSigningCertificate());
        obj.setEncryptionCertificate("e");
        assertEquals("e", obj.getEncryptionCertificate());
    }

    @Test
    public void testAppletInfo() {
        GclAventraAppletInfo obj = new GclAventraAppletInfo();
        GclAventraAppletInfo obj2 = new GclAventraAppletInfo();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setChangeCounter(1);
        assertEquals(Integer.valueOf(1), obj.getChangeCounter());
        obj.setName("n");
        assertEquals("n", obj.getName());
        obj.setSerial("s");
        assertEquals("s", obj.getSerial());
        obj.setVersion("v");
        assertEquals("v", obj.getVersion());
    }

    @Test
    public void testPinResetRequest() {
        GclAventraPinResetRequest obj = new GclAventraPinResetRequest();
        GclAventraPinResetRequest obj2 = new GclAventraPinResetRequest();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setNewPin("n");
        assertEquals("n", obj.getNewPin());
        obj.setPuk("p");
        assertEquals("p", obj.getPuk());
        obj.setPrivateKeyReference("r");
        assertEquals("r", obj.getPrivateKeyReference());
    }

    @Test
    public void getSigningCertificateChain() {
        Map<Integer, T1cCertificate> signChain = container.getSigningCertificateChain();
        assertNotNull(signChain);
        assertTrue(CollectionUtils.isNotEmpty(signChain.entrySet()));
    }

    @Test
    public void getAuthenticationCertificateChain() {
        Map<Integer, T1cCertificate> signChain = container.getAuthenticationCertificateChain();
        assertNotNull(signChain);
        assertTrue(CollectionUtils.isNotEmpty(signChain.entrySet()));
    }

    @Test
    public void testGenericDataDump() {
        ContainerData data = container.dumpData();
        assertNotNull(data);
        assertNotNull(data.getDocumentId());

        assertNotNull(data.getAuthenticationCertificateChain());
        assertNotNull(data.getSigningCertificateChain());
        assertNotNull(data.getCertificateChains());
        assertNotNull(data.getAllCertificates());
    }
}