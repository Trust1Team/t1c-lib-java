package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibValidityDuration;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class OcraContainerTest extends AbstractTestClass {

    private OcraContainer container;

    @Before
    public void initContainer() {
        container = getClient().getOcraContainer(new GclReader().withId(MockResponseFactory.OCRA_READER_ID).withPinpad(true));
    }

    @Test
    public void getAllDataFilters() {
        assertTrue(CollectionUtils.isNotEmpty(container.getAllDataFilters()));
    }

    @Test
    public void getAllCertificateFilters() {
        assertTrue(CollectionUtils.isEmpty(container.getAllCertificateFilters()));
    }

    @Test
    public void getAllData() {
        GclOcraAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getCounter());
    }

    @Test
    public void getAllDataFiltered() {
        GclOcraAllData data = container.getAllData(Collections.singletonList("counter"));
        assertNotNull(data);
        assertNotNull(data.getCounter());
    }

    @Test
    public void getAllDataParsed() {
        GclOcraAllData data = container.getAllData(true);
        assertNotNull(data);
        assertNotNull(data.getCounter());
    }

    @Test(expected = RestException.class)
    public void getAllDataWithRestException() {
        GclOcraAllData data = container.getAllData(Collections.singletonList("throwException"));
        assertNotNull(data);
        assertNotNull(data.getCounter());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() {
        container.getAllCertificates();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesFiltered() {
        container.getAllCertificates(Collections.singletonList("filter"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesParsed() {
        container.getAllCertificates(true);
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

    @Test(expected = UnsupportedOperationException.class)
    public void authenticate() {
        container.authenticate(null, null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sign() {
        container.sign(null, null, null);
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.OCRA, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.OCRA.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(GclOcraAllData.class, container.getAllDataClass());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesClass() {
        container.getAllCertificatesClass();
    }

    @Test
    public void getChallengeOTP() {
        Long otp = container.getChallengeOTP("kgg0MTQ4NTkzNZMA", "1111");
        assertNotNull(otp);
    }

    @Test(expected = VerifyPinException.class)
    public void getChallengeOTPWithWrongPin() {
        container.getChallengeOTP("kgg0MTQ4NTkzNZMA", "1112");
    }

    @Test
    public void getChallengeOTPWithHardwarePinPad() {
        container.getChallengeOTP("kgg0MTQ4NTkzNZMA");
    }

    @Test
    public void readCounter() {
        assertTrue(StringUtils.isNotEmpty(container.readCounter()));
    }

    @Test(expected = VerifyPinException.class)
    public void readCounterWithWrongPin() {
        container.readCounter("1112");
    }

    @Test
    public void testAllData() {
        GclOcraAllData obj = new GclOcraAllData();
        GclOcraAllData obj2 = new GclOcraAllData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setCounter("s");
        assertEquals("s", obj.getCounter());
    }

    @Test
    public void testChallenge() {
        GclOcraChallengeData obj = new GclOcraChallengeData();
        GclOcraChallengeData obj2 = new GclOcraChallengeData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setPin("pin");
        assertEquals("pin", obj.getPin());
        assertEquals(obj.withPin("pin2"), obj);

        obj.setChallenge("challenge");
        assertEquals("challenge", obj.getChallenge());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSigningCertificateChain() {
        container.getSigningCertificateChain();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAuthenticationCertificateChain() {
        container.getAuthenticationCertificateChain();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGenericDataDump() {
        container.dumpData();
    }
}