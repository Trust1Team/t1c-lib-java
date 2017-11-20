package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.exceptions.SafeNetContainerException;
import com.t1t.t1c.model.rest.GclSafeNetSlot;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.factories.ConnectionFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class SafeNetContainerTest extends AbstractTestClass {

    private ISafeNetContainer container;

    @Before
    public void initContainer() {
        container = getClient().getSafeNetContainer(ContainerType.SAFENET.getId());
    }

    @Test
    public void getAllData() throws Exception {
        SafeNetAllData data = (SafeNetAllData) container.getAllData();
        assertNotNull(data);
        assertTrue(CollectionUtils.isNotEmpty(data.getSlots()));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() throws Exception {
        container.getAllCertificates();
    }

    @Test
    public void getCertificates() throws Exception {
        List<T1cCertificate> certificates = container.getCertificates(1, "1234");
        assertTrue(CollectionUtils.isNotEmpty(certificates));
        assertNotNull(certificates.get(0));
        assertTrue(StringUtils.isNotEmpty(certificates.get(0).getBase64()));
    }

    @Test(expected = SafeNetContainerException.class)
    public void getCertificatesWithoutSlotId() throws Exception {
        List<T1cCertificate> certificates = container.getCertificates(null, "1234");
    }

    @Test
    public void getInfo() throws Exception {
        assertNotNull(container.getInfo());
    }

    @Test
    public void getSlots() throws Exception {
        List<GclSafeNetSlot> slots = container.getSlots();
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(2, slots.size());
    }

    @Test
    public void getSlotsWithToken() throws Exception {
        List<GclSafeNetSlot> slots = container.getSlotsWithToken();
        assertTrue(CollectionUtils.isNotEmpty(slots));
        assertEquals(1, slots.size());
    }
}