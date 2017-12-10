package com.t1t.t1c.containers.smartcards.mobib;

import com.sun.org.apache.regexp.internal.RE;
import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
public class MobibContainerTest extends AbstractTestClass {

    private MobibContainer container;

    @Before
    public void initContainer() {
        container = getClient().getMobibContainer(new GclReader().withId(MockResponseFactory.MOBIB_READER_ID).withPinpad(true));
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
        GclMobibAllData data = container.getAllData();
        assertNotNull(data);
        assertNotNull(data.getActive());
        assertNotNull(data.getCardIssuing());
        assertNotNull(data.getContracts());
        assertNotNull(data.getPicture());
    }

    @Test
    public void getAllDataFiltered() {
        GclMobibAllData data = container.getAllData(Arrays.asList("picture", "card-issuing"));
        assertNotNull(data);
        assertNull(data.getActive());
        assertNotNull(data.getCardIssuing());
        assertNull(data.getContracts());
        assertNotNull(data.getPicture());
    }

    @Test(expected = RestException.class)
    public void getAllDataWithRestException() {
        container.getAllData(Collections.singletonList("throwException"));
    }

    @Test
    public void getAllDataParsed() {
        assertNotNull(container.getAllData(true));
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

    @Test(expected = UnsupportedOperationException.class)
    public void verifyPin() {
        container.verifyPin();
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
        assertEquals(ContainerType.MOBIB, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.MOBIB.getId(), container.getTypeId());
    }

    @Test
    public void getAllDataClass() {
        assertEquals(GclMobibAllData.class, container.getAllDataClass());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllCertificateClass() {
        container.getAllCertificatesClass();
    }

    @Test
    public void getStatus() {
        assertTrue(container.getStatus());
    }

    @Test
    public void getPicture() {
        assertNotNull(container.getPicture());
    }

    @Test
    public void getCardIssuing() {
        GclMobibCardIssuing cardIssuing = container.getCardIssuing();
        assertNotNull(cardIssuing);
        assertNotNull(cardIssuing.getCardExpirationDate());
        assertNotNull(cardIssuing.getCardHolderBirthDate());
        assertNotNull(cardIssuing.getCardHolderEndDate());
        assertNotNull(cardIssuing.getCardHolderName());
        assertNotNull(cardIssuing.getCardHolderId());
        assertNotNull(cardIssuing.getCardHolderStartDate());
        assertNotNull(cardIssuing.getCardRevalidationDate());
        assertNotNull(cardIssuing.getCardType());
        assertNotNull(cardIssuing.getCompanyId());
        assertNotNull(cardIssuing.getGender());
        assertNotNull(cardIssuing.getLanguage());
        assertNotNull(cardIssuing.getVersion());
    }

    @Test
    public void getContracts() {
        List<GclMobibContract> contracts = container.getContracts();
        assertTrue(CollectionUtils.isNotEmpty(contracts));
        for (GclMobibContract contract : contracts) {
            assertNotNull(contract);
            assertNotNull(contract.getAuthenticatorKvc());
            assertNotNull(contract.getAuthenticatorValue());
            assertNotNull(contract.getJourneyInterchangesAllowed());
            assertNotNull(contract.getPassengersMax());
            assertNotNull(contract.getPriceAmount());
            assertNotNull(contract.getProvider());
            assertNotNull(contract.getRestrictCode());
            assertNotNull(contract.getRestrictTime());
            assertNotNull(contract.getOperatorMap());
            assertNotNull(contract.getTypeId());
            assertNotNull(contract.getSaleDate());
            assertNotNull(contract.getSaleSamCount());
            assertNotNull(contract.getSaleSamId());
            assertTrue(CollectionUtils.isNotEmpty(contract.getSpatials()));
            for (GclMobibSpatial spatial : contract.getSpatials()) {
                assertNotNull(spatial.getRouteDestination());
                assertNotNull(spatial.getRouteOrigin());
                assertNotNull(spatial.getType());
            }
            assertNotNull(contract.getTariff());
            assertNotNull(contract.getTariff().getCounter());
            assertNotNull(contract.getTariff().getMultimodal());
            assertNotNull(contract.getTariff().getCounter());
            assertNotNull(contract.getTariff().getCounter().getTime());
            assertNotNull(contract.getTariff().getCounter().getType());
            assertNotNull(contract.getTariff().getCounter().getJourneys());
            assertNotNull(contract.getValidityDuration());
            assertNotNull(contract.getValidityDuration().getUnit());
            assertNotNull(contract.getValidityDuration().getValue());
            assertNotNull(contract.getValidityStartDate());
            assertNotNull(contract.getVehicleClassAllowed());
            assertNotNull(contract.getVersion());
        }
    }
}