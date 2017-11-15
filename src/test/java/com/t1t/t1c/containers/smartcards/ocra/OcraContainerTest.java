package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.rest.GclOcraAllData;
import com.t1t.t1c.model.rest.GclOcraChallengeData;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.services.FactoryService;
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
@PrepareForTest({RestServiceBuilder.class, FactoryService.class})
public class OcraContainerTest extends AbstractTestClass {

    private IOcraContainer container;

    @Before
    public void initContainer() {
        container = getClient().getOcraContainer(ContainerType.OCRA.getId());
    }

    @Test
    public void getAllData() throws Exception {
        GclOcraAllData data = (GclOcraAllData) container.getAllData();
        GclOcraAllData filteredData = (GclOcraAllData) container.getAllData(container.getAllDataFilters());
        assertNotNull(data);
        assertNotNull(filteredData);
        assertTrue(StringUtils.isNotEmpty(data.getCounter()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() throws Exception {
        container.getAllCertificates();
    }

    @Test
    public void getCounter() throws Exception {
        assertTrue(StringUtils.isNotEmpty(container.getCounter("1234")));
        assertTrue(StringUtils.isNotEmpty(container.getCounter()));
    }

    @Test
    public void challenge() throws Exception {
        GclOcraChallengeData dataWithPin = new GclOcraChallengeData().withPin("1234").withChallenge("This is a challenge");
        GclOcraChallengeData dataWithoutPin = new GclOcraChallengeData().withChallenge("This is a challenge");
        assertTrue(StringUtils.isNotEmpty(container.challenge(dataWithPin)));
        assertTrue(StringUtils.isNotEmpty(container.challenge(dataWithoutPin)));
    }

}