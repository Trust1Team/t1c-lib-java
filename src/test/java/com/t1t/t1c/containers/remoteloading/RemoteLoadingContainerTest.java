package com.t1t.t1c.containers.remoteloading;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraAllData;
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
public class RemoteLoadingContainerTest extends AbstractTestClass {

    private RemoteLoadingContainer container;

    @Before
    public void initContainer() {
        container = getClient().getRemoteLoadingContainer(new GclReader().withId(MockResponseFactory.REMOTE_LOADING_READER_ID).withPinpad(true));
    }

    @Test
    public void getAtr() {
        String atr = container.getAtr();
        assertNotNull(atr);
        assertEquals(MockResponseFactory.getRemoteLoadingAtr(), atr);
    }

    @Test
    public void executeApduCall() {
        GclRemoteLoadingCommand command = container.executeApduCall(new GclRemoteLoadingApdu()
                .withCla("cla")
                .withData("data")
                .withIns("ins")
                .withLe("le")
                .withP1("p1")
                .withP2("p2")
        );
        assertNotNull(command);
        assertEquals(MockResponseFactory.getGclRemoteLoadingCommand(0), command);
    }

    @Test
    public void executeApduCalls() {
        List<GclRemoteLoadingCommand> commands = container.executeApduCalls(
                Arrays.asList(
                        new GclRemoteLoadingApdu()
                                .withCla("cla")
                                .withData("data")
                                .withIns("ins")
                                .withLe("le")
                                .withP1("p1")
                                .withP2("p2"),
                        new GclRemoteLoadingApdu().withCla("cla")
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
        GclRemoteLoadingCommand command = container.executeCcid("VERIFY_PIN_DIRECT", "1E1E8947040C0402010904000000000D000000002000010820FFFFFFFFFFFFFF");
        assertNotNull(command);
        assertEquals(MockResponseFactory.getGclRemoteLoadingCommand(0), command);
    }

    @Test
    public void getCcidFeatures() {
        List<GclRemoteLoadingCcidFeature> features = container.getCcidFeatures();
        assertNotNull(features);
        for (GclRemoteLoadingCcidFeature feature : MockResponseFactory.getGclRemoteLoadingCcidFeatures()) {
            assertTrue(features.contains(feature));
        }
    }

    @Test
    public void executeCommand() {
        GclRemoteLoadingCommand command = container.executeCommand("00B00000FF");
        assertNotNull(command);
        assertEquals(MockResponseFactory.getGclRemoteLoadingCommand(0), command);
    }

    @Test
    public void executeCommands() {
        List<GclRemoteLoadingCommand> commands = container.executeCommands(
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
        assertEquals(MockResponseFactory.getRemoteLoadingSessionId(), sessionId);
    }

    @Test
    public void openSessionWithoutTimeout() {
        String sessionId = container.openSession(null);
        assertNotNull(sessionId);
        assertEquals(MockResponseFactory.getRemoteLoadingSessionId(), sessionId);
    }

    @Test
    public void closeSessionWithSessionId() {
        assertTrue(container.closeSession(MockResponseFactory.getRemoteLoadingSessionId()));
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
        GclRemoteLoadingApdu obj = new GclRemoteLoadingApdu();
        GclRemoteLoadingApdu obj2 = new GclRemoteLoadingApdu();
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
        GclRemoteLoadingCcidFeature obj = new GclRemoteLoadingCcidFeature();
        GclRemoteLoadingCcidFeature obj2 = new GclRemoteLoadingCcidFeature();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setControlCode(1);
        assertEquals(Integer.valueOf(1), obj.getControlCode());

        obj.setId("i");
        assertEquals("i", obj.getId());
    }

    @Test
    public void testCcidRequest() {
        GclRemoteLoadingCcidRequest obj = new GclRemoteLoadingCcidRequest();
        GclRemoteLoadingCcidRequest obj2 = new GclRemoteLoadingCcidRequest();
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
        GclRemoteLoadingCommand obj = new GclRemoteLoadingCommand();
        GclRemoteLoadingCommand obj2 = new GclRemoteLoadingCommand();
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
        GclRemoteLoadingCommandRequest obj = new GclRemoteLoadingCommandRequest();
        GclRemoteLoadingCommandRequest obj2 = new GclRemoteLoadingCommandRequest();
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
        GclRemoteLoadingOpenSessionRequest obj = new GclRemoteLoadingOpenSessionRequest();
        GclRemoteLoadingOpenSessionRequest obj2 = new GclRemoteLoadingOpenSessionRequest();
        assertEquals(obj, obj);
        assertEquals(obj, obj2);
        assertEquals(obj.hashCode(), obj2.hashCode());
        assertNotEquals(obj, "string");
        assertTrue(StringUtils.isNotEmpty(obj.toString()));

        obj.setTimeout(1);
        assertEquals(Integer.valueOf(1), obj.getTimeout());
    }
}