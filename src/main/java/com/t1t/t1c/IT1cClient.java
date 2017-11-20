package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.readerapi.IReaderApiContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.ISafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.ocv.IOcvClient;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IT1cClient {
    Core getCore();
    LibConfig getConfig();

    /*Clients*/
    IDsClient getDsClient();
    IOcvClient getOcvClient();

    /*Containers*/
    List<GenericContainer> getGenericContainer(String readerId);
    BeIdContainer getBeIdContainer(String readerId);
    LuxIdContainer getLuxIdContainer(String readerId, String pin);
    LuxTrustContainer getLuxTrustContainer(String readerId, String pin);
/*    IDnieContainer getDnieContainer(String readerId);
    IEmvContainer getEmvContainer(String readerId);
    IMobibContainer getMobibContainer(String readerId);
    IOcraContainer getOcraContainer(String readerId);
    IAventraContainer getAventraContainer(String readerId);
    IOberthurContainer getOberthurContainer(String readerId);
    IPivContainer getPivContainer(String readerId);
    IPtEIdContainer getPtIdContainer(String readerId);
    ISafeNetContainer getSafeNetContainer(String readerId);
    ISafeNetContainer getSafeNetContainer(String readerId, SafeNetContainerConfiguration configuration);
    IReaderApiContainer getReaderContainer(String readerId);*/

    /*DS Functionality*/
    String getDownloadLink();
    List<GclReader> getAuthenticateCapableReaders();
    List<GclReader> getSignCapableReaders();
    List<GclReader> getPinVerificationCapableReaders();
    String exchangeApiKeyForToken();
    String refreshJwt();
}
