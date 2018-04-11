package com.t1t.t1c.core;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.utils.ContainerUtil;
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
public class CoreTest extends AbstractTestClass {

    private ICore core;

    @Before
    public void initCore() {
        this.core = getClient().getCore();
    }

    @Test
    public void getPlatformInfo() {
        PlatformInfo platformInfo = core.getPlatformInfo();
        assertEquals(new PlatformInfo(), platformInfo);
    }

    @Test
    public void getVersion() {
        String version = core.getVersion();
        assertEquals(MockResponseFactory.getGclV2Status().getVersion(), version);
    }

    @Test
    public void activate() {
        assertTrue(core.activate());
    }

    @Test
    public void getPubKey() {
        String publicKey = core.getDsPubKey();
        assertEquals(MockResponseFactory.getGclAdminDsCertificate(), publicKey);
    }

    @Test
    public void setPubKey() {
        assertTrue(core.setDsPubKey(MockResponseFactory.getGclAdminDsCertificate(), "AESKEY"));
    }

    @Test
    public void getInfo() {
        GclInfo info = core.getInfo();
        assertEquals(MockResponseFactory.getGclV2Status(), info);
    }

    @Test
    public void testPollCardInserted() {
        GclReader reader = this.getClient().getCore().pollCardInserted();

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void testPollCardInsertedWithInterval() {
        GclReader reader = this.getClient().getCore().pollCardInserted(5);

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void testPollCardInsertedWithIntervalAndDuration() {
        GclReader reader = this.getClient().getCore().pollCardInserted(5, 1);

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPollCardInsertedWithNegativeNumbers() {
        GclReader reader = this.getClient().getCore().pollCardInserted(-5, -1);

        assertNotNull(reader);
        assertNotNull(reader.getCard());
    }

    @Test
    public void testPollReadersWithCards() {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards();
        List<GclReader> expected = MockResponseFactory.getGclReaders(true);

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithCardsWithInterval() {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(10);
        List<GclReader> expected = MockResponseFactory.getGclReaders(true);

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithCardsWithIntervalAndDuration() {
        List<GclReader> readers = this.getClient().getCore().pollReadersWithCards(1, 1);
        List<GclReader> expected = MockResponseFactory.getGclReaders(true);

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPollReadersWithCardsWithNegativeNumbers() {
        this.getClient().getCore().pollReadersWithCards(-1, -1);
    }

    @Test
    public void testPollReaders() {
        List<GclReader> readers = this.getClient().getCore().pollReaders();
        List<GclReader> expected = MockResponseFactory.getGclReaders(null);

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithInterval() {
        List<GclReader> readers = this.getClient().getCore().pollReaders(15);
        List<GclReader> expected = MockResponseFactory.getGclReaders(null);

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test
    public void testPollReadersWithIntervalAndDuration() {
        List<GclReader> readers = this.getClient().getCore().pollReaders(1, 1);
        List<GclReader> expected = MockResponseFactory.getGclReaders(null);

        assertTrue(CollectionUtils.isNotEmpty(readers));
        assertEquals(expected.size(), readers.size());
        for (GclReader reader : expected) {
            assertTrue(readers.contains(reader));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPollReadersWithNegativeNumbers() {
        this.getClient().getCore().pollReaders(-1, -1);
    }

    @Test
    public void getAuthenticationCapableReaders() {
        List<GclReader> readers = core.getAuthenticationCapableReaders();
        for (GclReader reader : readers) {
            assertTrue(ContainerUtil.canAuthenticate(reader.getCard()));
        }
    }

    @Test
    public void getSignCapableReaders() {
        List<GclReader> readers = core.getSignCapableReaders();
        for (GclReader reader : readers) {
            assertTrue(ContainerUtil.canSign(reader.getCard()));
        }
    }

    @Test
    public void getPinVerificationCapableReaders() {
        List<GclReader> readers = core.getPinVerificationCapableReaders();
        for (GclReader reader : readers) {
            assertTrue(ContainerUtil.canVerifyPin(reader.getCard()));
        }
    }

    @Test
    public void getReader() {
        GclReader reader = core.getReader(MockResponseFactory.BEID_READER_ID);
        assertNotNull(reader);
    }

    @Test
    public void getReaders() {
        List<GclReader> readers = core.getReaders();
        assertEquals(MockResponseFactory.getGclReaders(null), readers);
    }

    @Test
    public void getReadersWithoutInsertedCard() {
        List<GclReader> readers = core.getReadersWithoutInsertedCard();
        for (GclReader reader : readers) {
            assertNull(reader.getCard());
        }
    }

    @Test
    public void getReadersWithInsertedCard() {
        List<GclReader> readers = core.getReadersWithInsertedCard();
        assertEquals(MockResponseFactory.getGclReaders(true), readers);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAgentsNonCitrix() {
        core.getAgents();
    }

    @Test
    public void getAgents() {
        LibConfig conf = getClient().getConnectionFactory().getConfig();
        conf.setCitrix(true);
        getClient().getConnectionFactory().setConfig(conf);
        core = getClient().getCore();
        List<GclAgent> agents = core.getAgents();
        assertTrue(CollectionUtils.isNotEmpty(agents));
        assertTrue(StringUtils.isNotEmpty(agents.get(0).getUsername()));
        assertTrue(StringUtils.isNotEmpty(agents.get(0).getChallenge()));
        assertTrue(StringUtils.isNotEmpty(agents.get(0).getHostname()));
        assertTrue(StringUtils.isNotEmpty(agents.get(0).getLastUpdate()));
        assertNotNull(agents.get(0).getPort());
        assertNotNull(agents.get(0).getMetadata());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConsentWithoutTitle() {
        core.getConsent(null, "word");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConsentWithoutCodeWord() {
        core.getConsent("title", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConsentWithExcessiveTimeout() {
        core.getConsent("title", "word", null, null, null, null, 35);
    }

    @Test
    public void getConsent() {
        assertTrue(core.getConsent("title", "word"));
    }

    @Test
    public void getConsentCitrix() {
        LibConfig conf = getClient().getConnectionFactory().getConfig();
        conf.setCitrix(true);
        getClient().getConnectionFactory().setConfig(conf);
        core = getClient().getCore();
        assertTrue(core.getConsent("title", "word"));
    }
}