package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclReader;
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
public class SafeNetContainerTest extends AbstractTestClass {

    private SafeNetContainer container;

    @Before
    public void initContainer() {
        container = getClient().getSafeNetContainer(new GclReader().withId(MockResponseFactory.SAFENET_READER_ID).withPinpad(true));
    }

    @Test
    public void getAllData() {

    }

    @Test
    public void getAllCertificates() {
    }

    @Test
    public void getCertificates() {
    }

    @Test
    public void getCertificatesWithoutSlotId() {
    }

    @Test
    public void getInfo() {
    }

    @Test
    public void getSlots() {
    }

    @Test
    public void getSlotsWithToken() {
    }
}