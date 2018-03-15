package com.t1t.t1c;

import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.remoteloading.RemoteLoadingContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.ModuleConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.ICore;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.ocv.IOcvClient;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IT1cClient {
    /*General*/
    ICore getCore();

    ConnectionFactory getConnectionFactory();

    /*Clients*/
    IDsClient getDsClient();

    IOcvClient getOcvClient();

    /*Containers*/
    IGenericContainer getGenericContainer(GclReader reader, String... pin);

    BeIdContainer getBeIdContainer(GclReader reader);

    LuxIdContainer getLuxIdContainer(GclReader reader, String pin);

    LuxTrustContainer getLuxTrustContainer(GclReader reader);

    DnieContainer getDnieContainer(GclReader reader);

    EmvContainer getEmvContainer(GclReader reader);

    MobibContainer getMobibContainer(GclReader reader);

    OcraContainer getOcraContainer(GclReader reader);

    AventraContainer getAventraContainer(GclReader reader);

    OberthurContainer getOberthurContainer(GclReader reader);

    PivContainer getPivContainer(GclReader reader, String pin);

    PtEIdContainer getPtIdContainer(GclReader reader);

    SafeNetContainer getSafeNetContainer(GclReader reader);

    SafeNetContainer getSafeNetContainer(GclReader reader, ModuleConfiguration configuration);

    /*Functional containers*/
    RemoteLoadingContainer getRemoteLoadingContainer(GclReader reader);

    ReaderApiContainer getReaderApiContainer();

    /*DS Functionality*/
    String getDownloadLink();

    List<GclReader> getAuthenticateCapableReaders();

    List<GclReader> getSignCapableReaders();

    List<GclReader> getPinVerificationCapableReaders();
}
