package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class SafeNetContainerTest extends AbstractTestClass {

    private SafeNetContainer container;

    @Before
    public void initContainer() {
        container = new SafeNetContainer(new GclReader().withId(MockResponseFactory.SAFENET_READER_ID).withPinpad(true), getSafeNetRestClient());
        try {
            container = getClient().getSafeNetContainer(new GclReader().withId(MockResponseFactory.SAFENET_READER_ID).withPinpad(true));
        } catch (IllegalArgumentException ex) {
            // Do nothing
        }
    }


    @Test
    public void getAllDataFilters() {
        assertTrue(CollectionUtils.isEmpty(container.getAllDataFilters()));
    }

    @Test
    public void getAllCertificateFilters() {
        assertTrue(CollectionUtils.isEmpty(container.getAllCertificateFilters()));
    }

    @Test
    public void getAllData() {
        SafeNetAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getSlots());
    }

    @Test
    public void getAllDataFiltered() {
        SafeNetAllData data = container.getAllData(Collections.singletonList(""));
        assertNotNull(data);
    }

    @Test
    public void getAllDataParsed() {
        SafeNetAllData data = container.getAllData(true);
        assertNotNull(data);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() {
        container.getAllCertificates();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesFiltered() {
        container.getAllCertificates(Collections.singletonList(""));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesParsed() {
        container.getAllCertificates(true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void verifyPin() {
        container.verifyPin();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void authenticate() {
        container.authenticate(null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sign() {
        container.sign(null, null);
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.SAFENET, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.SAFENET.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(SafeNetAllData.class, container.getAllDataClass());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesClass() {
        container.getAllCertificatesClass();
    }

    @Test
    public void getSafeNetCertificates() {
        SafeNetCertificates certs = container.getSafeNetCertificates(1, "1111");
        assertNotNull(certs);
        assertTrue(CollectionUtils.isNotEmpty(certs.getCertificates()));
        assertNotNull(certs.getCertificates().get(0));
        assertNotNull(certs.getCertificates().get(0).getBase64());
        assertNull(certs.getCertificates().get(0).getParsed());
    }

    @Test
    public void getSafeNetCertificatesParsed() {
        SafeNetCertificates certs = container.getSafeNetCertificates(1, "1111", true);
        assertNotNull(certs);
        assertTrue(CollectionUtils.isNotEmpty(certs.getCertificates()));
        assertNotNull(certs.getCertificates().get(0));
        assertNotNull(certs.getCertificates().get(0).getBase64());
        assertNotNull(certs.getCertificates().get(0).getParsed());
    }

    @Test(expected = VerifyPinException.class)
    public void getSafeNetCertificatesWithWrongPin() {
        SafeNetCertificates certs = container.getSafeNetCertificates(1, "1112");
        assertNotNull(certs);
        assertTrue(CollectionUtils.isNotEmpty(certs.getCertificates()));
    }

    @Test(expected = NullPointerException.class)
    public void getSafeNetCertificatesWithoutSlotId() {
        container.getSafeNetCertificates(null, "1111");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSafeNetCertificatesWithoutPin() {
        container.getSafeNetCertificates(1, null);
    }

    @Test
    public void getSafeNetInfo() {
        GclSafeNetInfo info = container.getSafeNetInfo();
        assertNotNull(info);
        assertNotNull(info.getCryptokiVersion());
        assertNotNull(info.getFlags());
        assertNotNull(info.getLibraryDescription());
        assertNotNull(info.getLibraryVersion());
        assertNotNull(info.getManufacturerId());
    }

    @Test
    public void getSafeNetSlots() {
        List<GclSafeNetSlot> slots = container.getSafeNetSlots();
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(2, slots.size());
        assertNotNull(slots.get(0));
        assertNotNull(slots.get(0).getDescription());
        assertNotNull(slots.get(0).getFirmwareVersion());
        assertNotNull(slots.get(0).getFlags());
        assertNotNull(slots.get(0).getHardwareVersion());
        assertNotNull(slots.get(0).getSlotId());
    }

    @Test
    public void getSafeNetSlotsWithTokensPresentTrue() {
        List<GclSafeNetSlot> slots = container.getSafeNetSlotsWithTokensPresent(true);
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(1, slots.size());
        assertEquals(Integer.valueOf(1), slots.get(0).getFlags());
    }

    @Test
    public void getSafeNetSlotsWithTokensPresentFalse() {
        List<GclSafeNetSlot> slots = container.getSafeNetSlotsWithTokensPresent(false);
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(1, slots.size());
        assertEquals(Integer.valueOf(0), slots.get(0).getFlags());
    }

    @Test
    public void getModulePath() {
        assertNotNull(container.getModulePath());
    }

    @Test
    public void testGclSafeNetInfo() {
        GclSafeNetInfo info = MockResponseFactory.getGclSafeNetInfo();
        GclSafeNetInfo info2 = MockResponseFactory.getGclSafeNetInfo();
        assertEquals(info, info);
        assertEquals(info, info2);
        assertEquals(info.hashCode(), info2.hashCode());
        assertNotEquals(info, "string");
        assertTrue(StringUtils.isNotEmpty(info.toString()));
        info.setCryptokiVersion("1");
        assertEquals("1", info.getCryptokiVersion());
        info.setFlags(5);
        assertEquals(Integer.valueOf(5), info.getFlags());
        assertEquals(info.withFlags(0), info);
        info.setLibraryVersion("2");
        assertEquals("2", info.getLibraryVersion());
        info.setLibraryDescription("3");
        assertEquals("3", info.getLibraryDescription());
        info.setManufacturerId("4");
        assertEquals("4", info.getManufacturerId());
    }

    @Test
    public void testGclSafeNetRequest() {
        GclSafeNetRequest req = new GclSafeNetRequest();
        GclSafeNetRequest req2 = new GclSafeNetRequest();
        assertEquals(req, req);
        assertEquals(req, req2);
        assertEquals(req.hashCode(), req2.hashCode());
        assertTrue(StringUtils.isNotEmpty(req.toString()));
        assertNotEquals(req, "string");
        req.setModule("mod");
        assertEquals("mod", req.getModule());
        req.setPin("pin");
        assertEquals("pin", req.getPin());
        req.setSlotId(1);
        assertEquals(Integer.valueOf(1), req.getSlotId());
    }

    @Test
    public void testGclSafeNetSlot() {
        GclSafeNetSlot slot = MockResponseFactory.getGclSafeNetSlotWithToken();
        GclSafeNetSlot slot2 = MockResponseFactory.getGclSafeNetSlotWithToken();
        assertEquals(slot, slot);
        assertEquals(slot, slot2);
        assertEquals(slot.hashCode(), slot2.hashCode());
        assertNotEquals(slot, "string");
        assertTrue(StringUtils.isNotEmpty(slot.toString()));
        slot.setDescription("1");
        assertEquals("1", slot.getDescription());
        slot.setFlags(5);
        assertEquals(Integer.valueOf(5), slot.getFlags());
        assertEquals(slot.withFlags(0), slot);
        slot.setFirmwareVersion("2");
        assertEquals("2", slot.getFirmwareVersion());
        slot.setHardwareVersion("3");
        assertEquals("3", slot.getHardwareVersion());
        slot.setSlotId(6);
        assertEquals(Integer.valueOf(6), slot.getSlotId());
    }

    @Test
    public void testGclSafeNetAllData() {
        SafeNetAllData data = container.getAllData();
        SafeNetAllData data2 = container.getAllData();
        assertEquals(data, data);
        assertEquals(data.hashCode(), data2.hashCode());
        assertEquals(data, data2);
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        List<GclSafeNetSlot> slots = Arrays.asList(MockResponseFactory.getGclSafeNetSlotWithoutToken(), MockResponseFactory.getGclSafeNetSlotWithoutToken(), MockResponseFactory.getGclSafeNetSlotWithToken());
        data.setSlots(slots);
        assertEquals(slots, data.getSlots());
    }

    @Test
    public void testGclSafeNetCertificates() {
        SafeNetCertificates certs = container.getSafeNetCertificates(1, "1111");
        SafeNetCertificates certs2 = container.getSafeNetCertificates(1, "1111");
        assertEquals(certs, certs);
        assertEquals(certs.hashCode(), certs2.hashCode());
        assertEquals(certs, certs2);
        assertTrue(StringUtils.isNotEmpty(certs.toString()));
        List<T1cCertificate> certList = Arrays.asList(new T1cCertificate().withBase64("cert1"), new T1cCertificate().withBase64("cert2"));
        certs.setCertificates(certList);
        assertEquals(certList, certs.getCertificates());
    }

    @Test
    public void testSafeNetConfiguration() {
        ModuleConfiguration conf = new ModuleConfiguration(Paths.get("linux"), Paths.get("mac"), Paths.get("win"));
        ModuleConfiguration conf1 = new ModuleConfiguration();
        ModuleConfiguration conf2 = new ModuleConfiguration();
        assertEquals(conf1, conf1);
        assertEquals(conf1, conf2);
        assertEquals(conf1.hashCode(), conf2.hashCode());
        assertNotEquals(conf1, "string");
        assertTrue(StringUtils.isNotEmpty(conf1.toString()));
        Path linux = Paths.get("l");
        Path windows = Paths.get("w");
        Path mac = Paths.get("m");
        conf.setLinux(linux);
        assertEquals(linux, conf.getLinux());
        assertEquals(conf.withLinux(Paths.get("linux")), conf);
        conf.setWindows(windows);
        assertEquals(windows, conf.getWindows());
        assertEquals(conf.withWindows(Paths.get("win")), conf);
        conf.setMac(mac);
        assertEquals(mac, conf.getMac());
        assertEquals(conf.withMac(Paths.get("mac")), conf);
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