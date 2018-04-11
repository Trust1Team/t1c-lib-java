package com.t1t.t1c.containers.smartcards.pkcs11;

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
public class Pkcs11ContainerTest extends AbstractTestClass {

    private Pkcs11Container container;

    @Before
    public void initContainer() {
        container = new Pkcs11Container(new GclReader().withId(MockResponseFactory.PKCS11_READER_ID).withPinpad(true), getPkcs11RestClient());
        try {
            container = getClient().getPkcs11Container(new GclReader().withId(MockResponseFactory.PKCS11_READER_ID).withPinpad(true));
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
        Pkcs11AllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getSlots());
    }

    @Test
    public void getAllDataFiltered() {
        Pkcs11AllData data = container.getAllData(Collections.singletonList(""));
        assertNotNull(data);
    }

    @Test
    public void getAllDataParsed() {
        Pkcs11AllData data = container.getAllData(true);
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
        assertEquals(ContainerType.PKCS11, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.PKCS11.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(Pkcs11AllData.class, container.getAllDataClass());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificatesClass() {
        container.getAllCertificatesClass();
    }

    @Test
    public void getPkcs11Certificates() {
        Pkcs11Certificates certs = container.getPkcs11Certificates(1L, "1111");
        assertNotNull(certs);
        assertTrue(CollectionUtils.isNotEmpty(certs.getCertificates()));
        assertNotNull(certs.getCertificates().get(0));
        assertNotNull(certs.getCertificates().get(0).getBase64());
        assertNull(certs.getCertificates().get(0).getParsed());
    }

    @Test
    public void getPkcs11CertificatesParsed() {
        Pkcs11Certificates certs = container.getPkcs11Certificates(1L, "1111", true);
        assertNotNull(certs);
        assertTrue(CollectionUtils.isNotEmpty(certs.getCertificates()));
        assertNotNull(certs.getCertificates().get(0));
        assertNotNull(certs.getCertificates().get(0).getBase64());
        assertNotNull(certs.getCertificates().get(0).getParsed());
    }

    @Test(expected = VerifyPinException.class)
    public void getPkcs11CertificatesWithWrongPin() {
        Pkcs11Certificates certs = container.getPkcs11Certificates(1L, "1112");
        assertNotNull(certs);
        assertTrue(CollectionUtils.isNotEmpty(certs.getCertificates()));
    }

    @Test(expected = NullPointerException.class)
    public void getPkcs11CertificatesWithoutSlotId() {
        container.getPkcs11Certificates(null, "1111");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPkcs11CertificatesWithoutPin() {
        container.getPkcs11Certificates(1L, null);
    }

    @Test
    public void getPkcs11Info() {
        GclPkcs11Info info = container.getPkcs11Info();
        assertNotNull(info);
        assertNotNull(info.getCryptokiVersion());
        assertNotNull(info.getFlags());
        assertNotNull(info.getLibraryDescription());
        assertNotNull(info.getLibraryVersion());
        assertNotNull(info.getManufacturerId());
    }

    @Test
    public void getPkcs11Slots() {
        List<GclPkcs11Slot> slots = container.getPkcs11Slots();
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
    public void getPkcs11SlotsWithTokensPresentTrue() {
        List<GclPkcs11Slot> slots = container.getPkcs11SlotsWithTokensPresent(true);
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(1, slots.size());
        assertEquals(Integer.valueOf(1), slots.get(0).getFlags());
    }

    @Test
    public void getPkcs11SlotsWithTokensPresentFalse() {
        List<GclPkcs11Slot> slots = container.getPkcs11SlotsWithTokensPresent(false);
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(1, slots.size());
        assertEquals(Integer.valueOf(0), slots.get(0).getFlags());
    }

    @Test
    public void getModulePath() {
        assertNotNull(container.getModulePath());
    }

    @Test
    public void testGclPkcs11Info() {
        GclPkcs11Info info = MockResponseFactory.getGclPkcs11Info();
        GclPkcs11Info info2 = MockResponseFactory.getGclPkcs11Info();
        assertEquals(info, info);
        assertEquals(info, info2);
        assertEquals(info.hashCode(), info2.hashCode());
        assertNotEquals(info, "string");
        assertTrue(StringUtils.isNotEmpty(info.toString()));
        info.setCryptokiVersion("1");
        assertEquals("1", info.getCryptokiVersion());
        info.setFlags(5L);
        assertEquals(Long.valueOf(5), info.getFlags());
        assertEquals(info.withFlags(0L), info);
        info.setLibraryVersion("2");
        assertEquals("2", info.getLibraryVersion());
        info.setLibraryDescription("3");
        assertEquals("3", info.getLibraryDescription());
        info.setManufacturerId("4");
        assertEquals("4", info.getManufacturerId());
    }

    @Test
    public void testGclPkcs11Request() {
        GclPkcs11Request req = new GclPkcs11Request();
        GclPkcs11Request req2 = new GclPkcs11Request();
        assertEquals(req, req);
        assertEquals(req, req2);
        assertEquals(req.hashCode(), req2.hashCode());
        assertTrue(StringUtils.isNotEmpty(req.toString()));
        assertNotEquals(req, "string");
        req.setModule("mod");
        assertEquals("mod", req.getModule());
        req.setPin("pin");
        assertEquals("pin", req.getPin());
        req.setSlotId(1L);
        assertEquals(Long.valueOf(1), req.getSlotId());
    }

    @Test
    public void testGclPkcs11Slot() {
        GclPkcs11Slot slot = MockResponseFactory.getGclPkcs11SlotWithToken();
        GclPkcs11Slot slot2 = MockResponseFactory.getGclPkcs11SlotWithToken();
        assertEquals(slot, slot);
        assertEquals(slot, slot2);
        assertEquals(slot.hashCode(), slot2.hashCode());
        assertNotEquals(slot, "string");
        assertTrue(StringUtils.isNotEmpty(slot.toString()));
        slot.setDescription("1");
        assertEquals("1", slot.getDescription());
        slot.setFlags(5L);
        assertEquals(Long.valueOf(5), slot.getFlags());
        assertEquals(slot.withFlags(0L), slot);
        slot.setFirmwareVersion("2");
        assertEquals("2", slot.getFirmwareVersion());
        slot.setHardwareVersion("3");
        assertEquals("3", slot.getHardwareVersion());
        slot.setSlotId(6L);
        assertEquals(Long.valueOf(6), slot.getSlotId());
    }

    @Test
    public void testGclPkcs11AllData() {
        Pkcs11AllData data = container.getAllData();
        Pkcs11AllData data2 = container.getAllData();
        assertEquals(data, data);
        assertEquals(data.hashCode(), data2.hashCode());
        assertEquals(data, data2);
        assertTrue(StringUtils.isNotEmpty(data.toString()));
        List<GclPkcs11Slot> slots = Arrays.asList(MockResponseFactory.getGclPkcs11SlotWithoutToken(), MockResponseFactory.getGclPkcs11SlotWithoutToken(), MockResponseFactory.getGclPkcs11SlotWithToken());
        data.setSlots(slots);
        assertEquals(slots, data.getSlots());
    }

    @Test
    public void testGclPkcs11Certificates() {
        Pkcs11Certificates certs = container.getPkcs11Certificates(1L, "1111");
        Pkcs11Certificates certs2 = container.getPkcs11Certificates(1L, "1111");
        assertEquals(certs, certs);
        assertEquals(certs.hashCode(), certs2.hashCode());
        assertEquals(certs, certs2);
        assertTrue(StringUtils.isNotEmpty(certs.toString()));
        List<T1cCertificate> certList = Arrays.asList(new T1cCertificate().withBase64("cert1"), new T1cCertificate().withBase64("cert2"));
        certs.setCertificates(certList);
        assertEquals(certList, certs.getCertificates());
    }

    @Test
    public void testPkcs11Configuration() {
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