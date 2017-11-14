package com.t1t.t1c.core;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.model.DsPublicKeyEncoding;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.services.FactoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static com.t1t.t1c.MockResponseFactory.*;
import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, FactoryService.class})
public class CoreTest extends AbstractTestClass {
    @Test
    public void testActivate() throws Exception {
        boolean result = getClient().getCore().activate();

        assertTrue(result);
    }

    @Test
    public void testGetPubKeyWithEncodingParam() throws Exception {
        String defaultKey = getClient().getCore().getPubKey();
        String derExpected = getPublicKeyResponseDer().getData();
        String der = getClient().getCore().getPubKey(DsPublicKeyEncoding.DER);
        String pem = getClient().getCore().getPubKey(DsPublicKeyEncoding.PEM);
        assertEquals(der, defaultKey);
        assertNotEquals(der, pem);
    }

    @Test
    public void testGetPubKey() throws Exception {
        String pubKey = getClient().getCore().getPubKey();

        assertEquals(getPublicKeyResponseDer().getData(), pubKey);
    }

    @Test
    public void testSetPubKey() throws Exception {
        getClient().getCore().setPubKey(getPublicKeyResponseDer().getData());

        // We don't expect any errors
    }

    @Test
    public void testGetInfo() throws Exception {
        GclStatus info = this.getClient().getCore().getInfo();

        assertEquals(getGclV1Status().getData(), info);
    }

    @Test
    public void testGetContainers() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReaders();
        List<GclReader> expectedReaders = getAllReaders(true).getData();

        assertNotNull(readers);
        assertEquals(expectedReaders.size(), readers.size());
        for (GclReader reader : expectedReaders) {
            boolean containsReader = readers.contains(reader);
            assertTrue(containsReader);
        }
    }

    @Test
    public void testPollCardInserted() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted();

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void testPollCardInsertedWithInterval() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted(5);

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void testPollCardInsertedWithIntervalAndDuration() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted(5, 1);

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPollCardInsertedWithNegativeNumbers() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted(-5, -1);

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void testPollReadersWithCards() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards();
        List<GclReader> expected = getAllReaders(false).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithCardsWithInterval() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(10);
        List<GclReader> expected = getAllReaders(false).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithCardsWithIntervalAndDuration() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(1, 1);
        List<GclReader> expected = getAllReaders(false).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPollReadersWithCardsWithNegativeNumbers() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(-1, -1);
        List<GclReader> expected = getAllReaders(false).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReaders() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders();
        List<GclReader> expected = getAllReaders(true).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithInterval() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders(15);
        List<GclReader> expected = getAllReaders(true).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithIntervalAndDuration() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders(1, 1);
        List<GclReader> expected = getAllReaders(true).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPollReadersWithNegativeNumbers() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders(-1, -1);
        List<GclReader> expected = getAllReaders(true).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testGetReader() throws Exception {
        GclReader reader = this.getClient().getCore().getReader(ContainerType.BEID.getId());
        GclReader expected = getReaderWithCard(ContainerType.BEID);
        assertEquals(expected, reader);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetReaderWithEmptyId() throws Exception {
        GclReader reader = this.getClient().getCore().getReader("");
        GclReader expected = getReaderWithCard(ContainerType.BEID);
        assertEquals(expected, reader);
    }

    @Test
    public void testGetReaders() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReaders();
        List<GclReader> expected = getAllReaders(true).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testGetReadersWithoutInsertedCard() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReadersWithoutInsertedCard();
        List<GclReader> expected = Collections.singletonList(getReaderWithCard(null));

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testGetUrl() throws Exception {
        String url = this.getClient().getCore().getUrl();
        String expected = this.getConfig().getGclClientUri();
        assertEquals(expected, url);
    }

    @Test
    public void testGetConsent() throws Exception {
        boolean consented = this.getClient().getCore().getConsent("Title", "CodeWord", 5);
        assertTrue(consented);
    }

    @Test
    public void testGetReadersWithCard() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReadersWithInsertedCard();
        List<GclReader> expected = getAllReaders(false).getData();

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }
}