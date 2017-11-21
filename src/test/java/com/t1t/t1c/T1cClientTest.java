package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdAllData;
import com.t1t.t1c.containers.smartcards.eid.dni.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.factories.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static com.t1t.t1c.MockResponseFactory.getDownloadPath;
import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class T1cClientTest extends AbstractTestClass {

    @Test
    public void testGetCore() throws Exception {
        Core core = getClient().getCore();
        assertNotNull(core);
    }

    @Test
    public void testGetConfig() throws Exception {
        LibConfig conf = getClient().getConfig();

        assertNotNull(conf);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAgent() throws Exception {
        getClient().getAgent();

    }

    @Test
    public void testGetDsClient() throws Exception {
        IDsClient dsClient = getClient().getDsClient();

        assertNotNull(dsClient);
    }

    @Test()
    public void testGetOcvClient() throws Exception {
        getClient().getOcvClient();

    }

    @Test
    public void testGetGenericContainerFor() throws Exception {
        GenericContainer genericContainer = getClient().getGenericContainerFor(ContainerType.BEID.getId());

        assertNotNull(genericContainer);
    }

    @Test
    public void testGetBeIdContainer() throws Exception {
        IBeIdContainer genericContainer = getClient().getBeIdContainer(ContainerType.BEID.getId());

        assertNotNull(genericContainer);
    }

    @Test
    public void testGetLuxIdContainer() throws Exception {
        ILuxIdContainer genericContainer = getClient().getLuxIdContainer(ContainerType.LUXID.getId(), "123456");

        assertNotNull(genericContainer);
    }

    @Test
    public void testGetLuxTrustContainer() throws Exception {
        ILuxTrustContainer genericContainer = getClient().getLuxTrustContainer(ContainerType.LUXTRUST.getId(), "123456");

        assertNotNull(genericContainer);
    }

    @Test
    public void testGetDnieContainer() throws Exception {
        IDnieContainer genericContainer = getClient().getDnieContainer(ContainerType.DNIE.getId());

        assertNotNull(genericContainer);
    }

    @Test
    public void testGetPtIdContainer() throws Exception {
        IPtEIdContainer genericContainer = getClient().getPtIdContainer(ContainerType.PT.getId());

        assertNotNull(genericContainer);
    }

    @Test()
    public void testGetEmvContainer() throws Exception {
        IEmvContainer container = getClient().getEmvContainer(ContainerType.EMV.getId());
        assertNotNull(container);
    }

    @Test()
    public void testGetMobibContainer() throws Exception {
        IMobibContainer container = getClient().getMobibContainer(ContainerType.MOBIB.getId());
        assertNotNull(container);
    }

    @Test
    public void testGetOcraContainer() throws Exception {
        IOcraContainer container = getClient().getOcraContainer((ContainerType.OCRA.getId()));
        assertNotNull(container);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAventraContainer() throws Exception {
        getClient().getAventraContainer(ContainerType.AVENTRA.getId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetOberthurContainer() throws Exception {
        getClient().getOberthurContainer(ContainerType.OBERTHUR.getId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetPivContainer() throws Exception {
        getClient().getPivContainer(ContainerType.PIV.getId());
    }

    @Test
    public void testGetSafenetContainer() throws Exception {
        ISafeNetContainer container = getClient().getSafeNetContainer(ContainerType.SAFENET.getId());
        assertNotNull(container);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetReaderContainer() throws Exception {
        getClient().getBelfiusContainer(ContainerType.READER_API.getId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetBelfiusContainer() throws Exception {
        getClient().getBelfiusContainer(ContainerType.READER_API.getId());
    }

    @Test
    public void testGetContainerFor() throws Exception {
        ContainerType luxIdType = getClient().getContainerFor(ContainerType.LUXID.getId());
        ContainerType LuxTrustType = getClient().getContainerFor(ContainerType.LUXTRUST.getId());
        ContainerType BeIdType = getClient().getContainerFor(ContainerType.BEID.getId());
        assertEquals(ContainerType.LUXID, luxIdType);
        assertEquals(ContainerType.LUXTRUST, LuxTrustType);
        assertEquals(ContainerType.BEID, BeIdType);
    }

    @Test
    public void testGetDownloadLink() throws Exception {
        String downloadLink = getClient().getDownloadLink();
        String expected = getConfig().getGatewayUri() + getDownloadPath().getPath();
        assertEquals(expected, downloadLink);
    }

    @Test
    public void testDumpData() throws Exception {
        BeIdAllData allData = (BeIdAllData) getClient().dumpData(ContainerType.BEID.getId());
        assertNotNull(allData);
    }

    @Test
    public void testReadersCanAuthenticate() throws Exception {
        List<GclReader> readersThatCanAuthenticate = getClient().getAuthenticateCapableReaders();
        assertEquals(10, readersThatCanAuthenticate.size());
    }

    @Test
    public void testReadersCanSign() throws Exception {
        List<GclReader> readersThatCanSign = getClient().getAuthenticateCapableReaders();
        assertEquals(10, readersThatCanSign.size());
    }

    @Test
    public void testReadersCanVerifyPin() throws Exception {
        List<GclReader> readersThatCanVerifyPin = getClient().getPinVerificationCapableReaders();
        assertEquals(11, readersThatCanVerifyPin.size());
    }

    @Test
    public void testAuthenticate() throws Exception {
        GclAuthenticateOrSignData dataToAuthenticate = new GclAuthenticateOrSignData().withData("hash").withAlgorithmReference("sha256").withPin("1234");
        String authentication = getClient().authenticate(ContainerType.LUXID.getId(), dataToAuthenticate, "1234");
        assertTrue(StringUtils.isNotEmpty(authentication));
    }

    @Test
    public void testSign() throws Exception {
        GclAuthenticateOrSignData dataToAuthenticate = new GclAuthenticateOrSignData().withData("hash").withAlgorithmReference("sha256").withPin("1234");
        String authentication = getClient().sign(ContainerType.LUXID.getId(), dataToAuthenticate, "1234");
        assertTrue(StringUtils.isNotEmpty(authentication));
    }

    @Test
    public void testVerifyPin() throws Exception {
        boolean verified = getClient().verifyPin(ContainerType.LUXID.getId(), "1234");
        assertTrue(verified);
    }

}