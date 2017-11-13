package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.model.rest.GclEmvAllData;
import com.t1t.t1c.model.rest.GclEmvApplication;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, FactoryService.class})
public class EmvContainerTest extends AbstractTestClass {

    private IEmvContainer container;

    @Before
    public void initContainer() {
        container = getClient().getEmvContainer(ContainerType.EMV.getId());
    }

    @Test
    public void getAllData() throws Exception {
        List<String> filters = container.getAllDataFilters();
        GclEmvAllData data = (GclEmvAllData) container.getAllData(filters);
        assertNotNull(data);
        assertNotNull(data.getApplicationData());
        assertNotNull(data.getApplications());
        assertTrue(CollectionUtils.isNotEmpty(data.getApplications()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() throws Exception {
        container.getAllCertificates(Collections.EMPTY_LIST);
    }

    @Test
    public void getApplications() throws Exception {
        List<GclEmvApplication> applications = container.getApplications();
        assertTrue(CollectionUtils.isNotEmpty(applications));
    }

    @Test
    public void getApplicationData() throws Exception {
        assertNotNull(container.getApplicationData());
    }

    @Test
    public void getIccPublicKeyCertificate() throws Exception {
        assertNotNull(container.getIccPublicKeyCertificate("aid"));
    }

    @Test
    public void getIssuerPublicKeyCertificate() throws Exception {
        assertNotNull(container.getIccPublicKeyCertificate("aid"));
    }

}