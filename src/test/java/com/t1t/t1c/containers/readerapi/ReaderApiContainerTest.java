package com.t1t.t1c.containers.readerapi;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.functional.readerapi.*;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class ReaderApiContainerTest extends AbstractTestClass {

    private ReaderApiContainer container;

    @Before
    public void initContainer() {
        container = getClient().getReaderApiContainer(new GclReader().withId(MockResponseFactory.REMOTE_LOADING_READER_ID).withPinpad(true), "v2.0.0");
    }

    @Test
    public void getAtr() {
        String atr = container.getAtr();
        assertNotNull(atr);
        assertEquals(MockResponseFactory.getReaderApiAtr(), atr);
    }

    @Test
    public void executeApduCall() {
        GclReaderApiCommand command = container.executeApduCall(new GclReaderApiApdu()
                .withCla("cla")
                .withData("data")
                .withIns("ins")
                .withLe("le")
                .withP1("p1")
                .withP2("p2")
        );
        assertNotNull(command);
        assertEquals(MockResponseFactory.getGclReaderApiCommand(0), command);
    }

    @Test
    public void executeApduCalls() {
        List<GclReaderApiCommand> commands = container.executeApduCalls(
                Arrays.asList(
                        new GclReaderApiApdu()
                                .withCla("cla")
                                .withData("data")
                                .withIns("ins")
                                .withLe("le")
                                .withP1("p1")
                                .withP2("p2"),
                        new GclReaderApiApdu().withCla("cla")
                                .withData("data")
                                .withIns("ins")
                                .withLe("le")
                                .withP1("p1")
                                .withP2("p2"))
        );
        assertNotNull(commands);
        assertEquals(2, commands.size());
    }

    @Test
    public void executeCcid() {
        GclReaderApiCommand command = container.executeCcid("VERIFY_PIN_DIRECT", "1E1E8947040C0402010904000000000D000000002000010820FFFFFFFFFFFFFF");
        assertNotNull(command);
        assertEquals(MockResponseFactory.getGclReaderApiCommand(0), command);
    }

    @Test
    public void getCcidFeatures() {
        List<GclReaderApiCcidFeature> features = container.getCcidFeatures();
        assertNotNull(features);
        for (GclReaderApiCcidFeature feature : MockResponseFactory.getGclReaderApiCcidFeatures()) {
            assertTrue(features.contains(feature));
        }
    }

    @Test
    public void executeCommand() {
        GclReaderApiCommand command = container.executeCommand("00B00000FF");
        assertNotNull(command);
        assertEquals(MockResponseFactory.getGclReaderApiCommand(0), command);
    }

    @Test
    public void executeCommands() {
        List<GclReaderApiCommand> commands = container.executeCommands(
                Arrays.asList("00B00000F1", "00B00000F2"));
        assertNotNull(commands);
        assertEquals(2, commands.size());
    }

    @Test
    public void isCardPresent() {
        assertTrue(container.isCardPresent());
    }

    @Test
    public void openSession() {
        String sessionId = container.openSession(30);
        assertNotNull(sessionId);
        assertEquals(MockResponseFactory.getReaderApiSessionId(), sessionId);
    }

    @Test
    public void openSessionWithoutTimeout() {
        String sessionId = container.openSession(null);
        assertNotNull(sessionId);
        assertEquals(MockResponseFactory.getReaderApiSessionId(), sessionId);
    }

    @Test
    public void closeSessionWithSessionId() {
        assertTrue(container.closeSession(MockResponseFactory.getReaderApiSessionId()));
    }

    @Test
    public void closeSessionWithoutSessionId() {
        assertTrue(container.closeSession());
    }

    @Test
    public void getType() {
        assertEquals(ContainerType.READER_API, container.getType());
    }

    @Test
    public void getTypeId() {
        assertEquals(ContainerType.READER_API.getId(), container.getTypeId());
    }

    @Test
    public void testApdu() {
        GclReaderApiApdu obj = new GclReaderApiApdu();
        GclReaderApiApdu obj2 = new GclReaderApiApdu();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setCla("c");
        assertEquals("c", obj.getCla());
        obj.setData("d");
        assertEquals("d", obj.getData());
        obj.setIns("i");
        assertEquals("i", obj.getIns());
        obj.setLe("l");
        assertEquals("l", obj.getLe());
        obj.setP1("1");
        assertEquals("1", obj.getP1());
        obj.setP2("2");
        assertEquals("2", obj.getP2());
    }

    @Test
    public void testCcidFeature() {
        GclReaderApiCcidFeature obj = new GclReaderApiCcidFeature();
        GclReaderApiCcidFeature obj2 = new GclReaderApiCcidFeature();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setControlCode(1L);
        assertEquals(Long.valueOf(1), obj.getControlCode());

        obj.setId("i");
        assertEquals("i", obj.getId());
    }

    @Test
    public void testCcidRequest() {
        GclReaderApiCcidRequest obj = new GclReaderApiCcidRequest();
        GclReaderApiCcidRequest obj2 = new GclReaderApiCcidRequest();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setApdu("a");
        assertEquals("a", obj.getApdu());

        obj.setFeature("f");
        assertEquals("f", obj.getFeature());
    }

    @Test
    public void testCommand() {
        GclReaderApiCommand obj = new GclReaderApiCommand();
        GclReaderApiCommand obj2 = new GclReaderApiCommand();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setRx("r");
        assertEquals("r", obj.getRx());

        obj.setSw("s");
        assertEquals("s", obj.getSw());

        obj.setTx("t");
        assertEquals("t", obj.getTx());
    }

    @Test
    public void testCommandRequest() {
        GclReaderApiCommandRequest obj = new GclReaderApiCommandRequest();
        GclReaderApiCommandRequest obj2 = new GclReaderApiCommandRequest();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setTx("t");
        assertEquals("t", obj.getTx());
    }

    @Test
    public void testOpenSessionRequest() {
        GclReaderApiOpenSessionRequest obj = new GclReaderApiOpenSessionRequest();
        GclReaderApiOpenSessionRequest obj2 = new GclReaderApiOpenSessionRequest();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setTimeout(1L);
        assertEquals(Long.valueOf(1), obj.getTimeout());
    }
}