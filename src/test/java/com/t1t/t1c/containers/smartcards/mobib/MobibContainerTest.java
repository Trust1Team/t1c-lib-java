package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class MobibContainerTest extends AbstractTestClass {

    private MobibContainer container;

    @Before
    public void initContainer() {
        container = getClient().getMobibContainer(new GclReader().withId(MockResponseFactory.MOBIB_READER_ID).withPinpad(true));
    }

    @Test
    public void getAllData() {
    }

    @Test
    public void getAllCertificates() {
    }

    @Test
    public void isActivated() {
    }

    @Test
    public void getContracts() {
    }

    @Test
    public void getCardIssuing() {
    }

    @Test
    public void getPicture() {
    }

}