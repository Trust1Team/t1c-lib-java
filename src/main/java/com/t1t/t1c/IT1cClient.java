package com.t1t.t1c;

import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.functional.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.ModuleConfiguration;
import com.t1t.t1c.containers.smartcards.pkcs11.Pkcs11Container;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.GclPace;
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
    IGenericContainer getGenericContainer(GclReader reader, GclPace pace);

    IGenericContainer getGenericContainer(GclReader reader);

    BeIdContainer getBeIdContainer(GclReader reader, String version);

    LuxIdContainer getLuxIdContainer(GclReader reader, String version, GclPace pace);

    LuxTrustContainer getLuxTrustContainer(GclReader reader, String version);

    DnieContainer getDnieContainer(GclReader reader, String version);

    EmvContainer getEmvContainer(GclReader reader, String version);

    MobibContainer getMobibContainer(GclReader reader, String version);

    OcraContainer getOcraContainer(GclReader reader, String version);

    AventraContainer getAventraContainer(GclReader reader, String version);

    OberthurContainer getOberthurContainer(GclReader reader, String version);

    PivContainer getPivContainer(GclReader reader, String version, String pacePin);

    PtEIdContainer getPtIdContainer(GclReader reader, String version);

    Pkcs11Container getPkcs11Container(GclReader reader, String version);

    Pkcs11Container getPkcs11Container(GclReader reader, String version, ModuleConfiguration configuration);

    /*Functional containers*/
    ReaderApiContainer getReaderApiContainer(GclReader reader, String version);

    /*DS Functionality*/
    String getDownloadLink();

    List<GclReader> getAuthenticateCapableReaders();

    List<GclReader> getSignCapableReaders();

    List<GclReader> getPinVerificationCapableReaders();
}
