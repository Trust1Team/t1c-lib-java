package com.t1t.t1c.containers.remoteloading;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
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
        String atr =  container.getAtr();
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
        GclRemoteLoadingCommand command =  container.executeCommand("00B00000FF");
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
}