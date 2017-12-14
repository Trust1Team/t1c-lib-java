package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.containers.smartcards.emv.GclEmvApplicationData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
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

    @Test
    public void testAllData() {
        GclMobibAllData obj = new GclMobibAllData();
        GclMobibAllData obj2 = new GclMobibAllData();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        List<GclMobibContract> contracts = Collections.singletonList(new GclMobibContract());
        obj.setContracts(contracts);
        assertEquals(contracts, obj.getContracts());
        GclMobibCardIssuing cardIssuing = new GclMobibCardIssuing();
        obj.setCardIssuing(cardIssuing);
        assertEquals(cardIssuing, obj.getCardIssuing());
        obj.setActive(false);
        assertFalse(obj.getActive());
        obj.setPicture("pic");
        assertEquals("pic", obj.getPicture());
    }

    @Test
    public void testCardIssuing() {
        GclMobibCardIssuing obj = new GclMobibCardIssuing();
        GclMobibCardIssuing obj2 = new GclMobibCardIssuing();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setCardExpirationDate("1");
        assertEquals("1", obj.getCardExpirationDate());
        obj.setCardHolderBirthDate("2");
        assertEquals("2", obj.getCardHolderBirthDate());
        obj.setCardHolderEndDate("3");
        assertEquals("3", obj.getCardHolderEndDate());
        obj.setCardHolderId("4");
        assertEquals("4", obj.getCardHolderId());
        obj.setCardHolderName("5");
        assertEquals("5", obj.getCardHolderName());
        obj.setCardHolderStartDate("6");
        assertEquals("6", obj.getCardHolderStartDate());
        obj.setCardRevalidationDate("7");
        assertEquals("7", obj.getCardRevalidationDate());
        obj.setCardType(1);
        assertEquals(Integer.valueOf(1), obj.getCardType());
        obj.setCompanyId(2);
        assertEquals(Integer.valueOf(2), obj.getCompanyId());
        obj.setGender(3);
        assertEquals(Integer.valueOf(3), obj.getGender());
        obj.setLanguage(4);
        assertEquals(Integer.valueOf(4), obj.getLanguage());
        obj.setVersion(5);
        assertEquals(Integer.valueOf(5), obj.getVersion());
    }

    @Test
    public void testContract() {
        GclMobibContract obj = new GclMobibContract();
        GclMobibContract obj2 = new GclMobibContract();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));
        
        obj.setAuthenticatorKvc(1);
        assertEquals(Integer.valueOf(1), obj.getAuthenticatorKvc());

        obj.setAuthenticatorValue(2);
        assertEquals(Integer.valueOf(2), obj.getAuthenticatorValue());

        obj.setJourneyInterchangesAllowed(false);

        obj.setOperatorMap(3);
        assertEquals(Integer.valueOf(3), obj.getOperatorMap());

        obj.setPassengersMax(4);
        assertEquals(Integer.valueOf(4), obj.getPassengersMax());

        obj.setPriceAmount(5);
        assertEquals(Integer.valueOf(5), obj.getPriceAmount());

        obj.setProvider(6);
        assertEquals(Integer.valueOf(6), obj.getProvider());

        obj.setRestrictCode(7);
        assertEquals(Integer.valueOf(7), obj.getRestrictCode());

        obj.setRestrictTime(8);
        assertEquals(Integer.valueOf(8), obj.getRestrictTime());

        obj.setSaleDate("s");
        assertEquals("s", obj.getSaleDate());

        obj.setSaleSamCount(9);
        assertEquals(Integer.valueOf(9), obj.getSaleSamCount());

        obj.setSaleSamId(10);
        assertEquals(Integer.valueOf(10), obj.getSaleSamId());

        List<GclMobibSpatial> s = Collections.singletonList(new GclMobibSpatial().withRouteDestination(true));
        obj.setSpatials(s);
        assertEquals(s, obj.getSpatials());

        GclMobibTariff t = new GclMobibTariff();
        obj.setTariff(t);
        assertEquals(t, obj.getTariff());

        obj.setTypeId(11);
        assertEquals(Integer.valueOf(11), obj.getTypeId());

        GclMobibValidityDuration d = new GclMobibValidityDuration();
        obj.setValidityDuration(d);
        assertEquals(d, obj.getValidityDuration());

        obj.setValidityStartDate("v");
        assertEquals("v", obj.getValidityStartDate());

        obj.setVehicleClassAllowed(12);
        assertEquals(Integer.valueOf(12), obj.getVehicleClassAllowed());

        obj.setVersion(13);
        assertEquals(Integer.valueOf(13), obj.getVersion());
    }

    @Test
    public void testCounter() {
        GclMobibCounter obj = new GclMobibCounter();
        GclMobibCounter obj2 = new GclMobibCounter();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setJourneys(1);
        assertEquals(Integer.valueOf(1), obj.getJourneys());

        obj.setType(2);
        assertEquals(Integer.valueOf(2), obj.getType());

        obj.setTime("s");
        assertEquals("s", obj.getTime());
    }

    @Test
    public void testSpatial() {
        GclMobibSpatial obj = new GclMobibSpatial();
        GclMobibSpatial obj2 = new GclMobibSpatial();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setRouteDestination(false);
        assertFalse(obj.getRouteDestination());

        obj.setRouteOrigin(1);
        assertEquals(Integer.valueOf(1), obj.getRouteOrigin());

        obj.setType(2);
        assertEquals(Integer.valueOf(2), obj.getType());
    }

    @Test
    public void testTariff() {
        GclMobibTariff obj = new GclMobibTariff();
        GclMobibTariff obj2 = new GclMobibTariff();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        GclMobibCounter c = new GclMobibCounter();
        obj.setCounter(c);
        assertEquals(c, obj.getCounter());

        obj.setMultimodal(false);
        assertFalse(obj.getMultimodal());

        obj.setNameref(1);
        assertEquals(Integer.valueOf(1), obj.getNameref());
    }

    @Test
    public void testValidityDuration() {
        GclMobibValidityDuration obj = new GclMobibValidityDuration();
        GclMobibValidityDuration obj2 = new GclMobibValidityDuration();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setUnit(1);
        assertEquals(Integer.valueOf(1), obj.getUnit());

        obj.setValue(2);
        assertEquals(Integer.valueOf(2), obj.getValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSigningCertificateChain() {
        container.getSigningCertificateChain();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAuthenticationCertificateChain() {
        container.getAuthenticationCertificateChain();
    }

    @Test
    public void testGenericDataDump() {
        ContainerData data = container.dumpData();
        assertNotNull(data);
        assertNotNull(data.getFullName());
        assertNotNull(data.getDateOfBirth());

        assertNotNull(data.getBase64Picture());
        assertNotNull(data.getValidityStartDate());
        assertNotNull(data.getValidityEndDate());
        assertNotNull(data.getDocumentId());
    }
}