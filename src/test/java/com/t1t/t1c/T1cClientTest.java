package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.ICore;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class T1cClientTest extends AbstractTestClass {

    @Test
    public void testGetCore() {
        ICore core = getClient().getCore();
        assertNotNull(core);
    }

    @Test
    public void testGetConfig() {
        LibConfig conf = getClient().getConnectionFactory().getConfig();
        assertNotNull(conf);
    }

    @Test
    public void testGetDsClient() {
        IDsClient dsClient = getClient().getDsClient();

        assertNotNull(dsClient);
    }

    @Test()
    public void testGetOcvClient() {
        assertNotNull(getClient().getOcvClient());

    }

    @Test
    public void testGetGenericContainer() {
        IGenericContainer genericContainer = getClient().getGenericContainer(new GclReader().withId(MockResponseFactory.BEID_READER_ID).withPinpad(false).withCard(MockResponseFactory.getBeIdCard()));

        assertNotNull(genericContainer);
    }

    @Test
    public void testGetBeIdContainer() {
        BeIdContainer container = getClient().getBeIdContainer(new GclReader().withId(MockResponseFactory.BEID_READER_ID).withPinpad(false));

        assertNotNull(container);
    }

    @Test
    public void testGetLuxIdContainer() {
        LuxIdContainer container = getClient().getLuxIdContainer(new GclReader().withId(MockResponseFactory.LUXID_READER_ID).withPinpad(false), "123456");

        assertNotNull(container);
    }

    @Test
    public void testGetLuxTrustContainer() {
        LuxTrustContainer container = getClient().getLuxTrustContainer(new GclReader().withId(MockResponseFactory.LUXTRUST_READER_ID).withPinpad(false));

        assertNotNull(container);
    }

    @Test
    public void testGetDnieContainer() {
        DnieContainer container = getClient().getDnieContainer(new GclReader().withId(MockResponseFactory.DNIE_READER_ID).withPinpad(false));

        assertNotNull(container);
    }

    @Test
    public void testGetPtIdContainer() {
        PtEIdContainer container = getClient().getPtIdContainer(new GclReader().withId(MockResponseFactory.PT_READER_ID).withPinpad(false));

        assertNotNull(container);
    }

    @Test()
    public void testGetEmvContainer() {
        EmvContainer container = getClient().getEmvContainer(new GclReader().withId(MockResponseFactory.EMV_READER_ID).withPinpad(false));
        assertNotNull(container);
    }

    @Test()
    public void testGetMobibContainer() {
        MobibContainer container = getClient().getMobibContainer(new GclReader().withId(MockResponseFactory.MOBIB_READER_ID).withPinpad(false));
        assertNotNull(container);
    }

    @Test
    public void testGetOcraContainer() {
        OcraContainer container = getClient().getOcraContainer(new GclReader().withId(MockResponseFactory.OCRA_READER_ID).withPinpad(false));
        assertNotNull(container);
    }

    @Test
    public void testGetAventraContainer() {
        assertNotNull(getClient().getAventraContainer(new GclReader().withId(MockResponseFactory.AVENTRA_READER_ID).withPinpad(false)));
    }

    @Test
    public void testGetOberthurContainer() {
        assertNotNull(getClient().getOberthurContainer(new GclReader().withId(MockResponseFactory.OBERTHUR_READER_ID).withPinpad(false)));
    }

    @Test
    public void testGetPivContainer() {
        assertNotNull(getClient().getPivContainer(new GclReader().withId(MockResponseFactory.PIV_READER_ID).withPinpad(false), "1111"));
    }

    @Test
    public void testGetSafenetContainer() {
        try {
            SafeNetContainer container = getClient().getSafeNetContainer(new GclReader().withId(MockResponseFactory.SAFENET_READER_ID).withPinpad(false));
            assertNotNull(container);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Driver not found"));
        }
    }

    @Test
    public void testGetReaderContainer() {
        assertNotNull(getClient().getReaderApiContainer());
    }

    @Test
    public void testGetRemoteLoadingContainer() {
        getClient().getRemoteLoadingContainer(new GclReader().withId(MockResponseFactory.REMOTE_LOADING_READER_ID).withPinpad(false));
    }

    @Test
    public void testGetDownloadLink() {
        String downloadLink = getClient().getDownloadLink();
        assertNotNull(downloadLink);
    }

    @Test
    public void testReadersCanAuthenticate() {
        List<GclReader> readersThatCanAuthenticate = getClient().getAuthenticateCapableReaders();
        assertEquals(8, readersThatCanAuthenticate.size());
    }

    @Test
    public void testReadersCanSign() {
        List<GclReader> readersThatCanSign = getClient().getAuthenticateCapableReaders();
        assertEquals(8, readersThatCanSign.size());
    }

    @Test
    public void testReadersCanVerifyPin() {
        List<GclReader> readersThatCanVerifyPin = getClient().getPinVerificationCapableReaders();
        assertEquals(10, readersThatCanVerifyPin.size());
    }

}