package com.t1t.t1c.core;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static com.t1t.t1c.MockResponseFactory.*;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.verifyAll;

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
        verifyAll();
        assertTrue(result);
    }

    @Test
    public void testGetPubKey() throws Exception {
        String pubKey = getClient().getCore().getPubKey();
        verifyAll();
        assertEquals(getPublicKeyResponse().getData(), pubKey);
    }

    @Test
    public void testSetPubKey() throws Exception {
        getClient().getCore().setPubKey(getPublicKeyResponse().getData());
        verifyAll();
        // We don't expect any errors
    }

    @Test
    public void testGetInfo() throws Exception {
        GclStatus info = this.getClient().getCore().getInfo();
        verifyAll();
        assertEquals(getGclV1Status().getData(), info);
    }

    @Test
    public void testGetContainers() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReaders();
        List<GclReader> expectedReaders = getAllReaders(true).getData();
        verifyAll();
        assertNotNull(readers);
        assertEquals(expectedReaders.size(), readers.size());
        for (GclReader reader : expectedReaders) {
            boolean containsReader = readers.contains(reader);
            assertTrue(containsReader);
        }
    }

    @Test
    public void pollCardInserted() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted();
        verifyAll();
        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void pollCardInsertedWithInterval() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted(5);
        verifyAll();
        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void pollCardInsertedWithIntervalAndDuration() throws Exception {
        GclReader reader = this.getClient().getCore().pollCardInserted(5, 1);
        verifyAll();
        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void pollReadersWithCards() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards();
        List<GclReader> expected = getAllReaders(false).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void pollReadersWithCardsWithInterval() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(10);
        List<GclReader> expected = getAllReaders(false).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void pollReadersWithCardsWithIntervalAndDuration() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(1, 1);
        List<GclReader> expected = getAllReaders(false).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void pollReaders() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders();
        List<GclReader> expected = getAllReaders(true).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void pollReadersWithInterval() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders(15);
        List<GclReader> expected = getAllReaders(true).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void pollReadersWithIntervalAndDuration() throws Exception {
        List<GclReader> readers = this.getClient().getCore().pollReaders(1, 1);
        List<GclReader> expected = getAllReaders(true).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void getReader() throws Exception {
        GclReader reader = this.getClient().getCore().getReader(ContainerType.BEID.getId());
        GclReader expected = getReaderWithCard(ContainerType.BEID);
        assertEquals(expected, reader);
    }

    @Test
    public void getReaders() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReaders();
        List<GclReader> expected = getAllReaders(true).getData();
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void getReadersWithoutInsertedCard() throws Exception {
        List<GclReader> readers = this.getClient().getCore().getReadersWithoutInsertedCard();
        List<GclReader> expected = Collections.singletonList(getReaderWithCard(null));
        verifyAll();
        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void getUrl() throws Exception {
        String url = this.getClient().getCore().getUrl();
        String expected = this.getConfig().getGclClientUri();
        assertEquals(expected, url);
    }

    @Test
    public void getConsent() throws Exception {
        boolean consented = this.getClient().getCore().getConsent("Title", "CodeWord", 5);
        assertTrue(consented);
    }
}