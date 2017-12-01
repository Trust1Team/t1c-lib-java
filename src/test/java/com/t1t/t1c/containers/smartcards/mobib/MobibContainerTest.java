package com.t1t.t1c.containers.smartcards.mobib;

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
public class MobibContainerTest extends AbstractTestClass {

    /*private IMobibContainer container;

    @Before
    public void initContainer() {
        container = getClient().getMobibContainer(ContainerType.MOBIB.getId());
    }

    @Test
    public void getAllData() throws Exception {
        GclMobibAllData data = (GclMobibAllData) container.getAllData();
        GclMobibAllData filteredData = (GclMobibAllData) container.getAllData(container.getAllDataFilters());
        assertNotNull(data);
        assertNotNull(filteredData);
        assertTrue(data.getActive());
        assertNotNull(data.getCardIssuing());
        assertTrue(CollectionUtils.isNotEmpty(data.getContracts()));
        assertTrue(StringUtils.isNotEmpty(data.getPicture()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificates() throws Exception {
        container.getAllCertificates();
    }

    @Test
    public void isActivated() throws Exception {
        assertTrue(container.isActivated());
    }

    @Test
    public void getContracts() throws Exception {
        List<GclMobibContract> contracts = container.getContracts();
        assertTrue(CollectionUtils.isNotEmpty(contracts));
        GclMobibContract contract = contracts.get(0);
        assertNotNull(contract);
        assertNotNull(contract.getPeriodJourneys());
        assertNotNull(contract.getTariff());
        assertNotNull(contract.getTariff().getCounter());
        assertNotNull(contract.getSpatials());
        assertNotNull(contract.getValidityDuration());
    }

    @Test
    public void getCardIssuing() throws Exception {
        GclMobibCardIssuing cardIssuing = container.getCardIssuing();
        assertNotNull(cardIssuing);
    }

    @Test
    public void getPicture() throws Exception {
    }*/

}