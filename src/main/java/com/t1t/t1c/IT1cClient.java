package com.t1t.t1c;

import com.t1t.t1c.agent.IAgent;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.readerapi.IReaderApiContainer;
import com.t1t.t1c.containers.remoteloading.belfius.IBelfiusContainer;
import com.t1t.t1c.containers.smartcards.eid.be.IBeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.esp.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.ISafenetContainer;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.services.IGenericService;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IT1cClient {

    Core getCore();

    IGenericService getGenericService();

    LibConfig getConfig();

    IAgent getAgent();

    IDsClient getDsClient();

    IOcvClient getOcvClient();

    GenericContainer getGenericContainerFor(String readerId);

    IBeIdContainer getBeIdContainer(String readerId);

    ILuxIdContainer getLuxIdContainer(String readerId, String pin);

    ILuxTrustContainer getLuxTrustContainer(String readerId, String pin);

    IDnieContainer getDnieContainer(String readerId);

    IPtEIdContainer getPtIdContainer(String readerId);

    IEmvContainer getEmvContainer(String readerId);

    IMobibContainer getMobibContainer(String readerId);

    IOcraContainer getOcraContainer(String readerId);

    IAventraContainer getAventraContainer(String readerId);

    IOberthurContainer getOberthurContainer(String readerId);

    IPivContainer getPivContainer(String readerId);

    ISafenetContainer getSafenetContainer(String readerId);

    IReaderApiContainer getReaderContainer(String readerId);

    IBelfiusContainer getBelfiusContainer(String readerId);

    ContainerType getContainerFor(String readerId);

    String getDownloadLink();

    AllData dumpData(String readerId);

    AllData dumpData(String readerId, List<String> filterParameters);

    AllData dumpData(String readerId, String pin);

    AllData dumpData(String readerId, String pin, List<String> filterParameters);

    List<GclReader> getAuthenticateCapableReaders();

    List<GclReader> getSignCapableReaders();

    List<GclReader> getPinVerificationCapableReaders();

    String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin);

    String sign(String readerId, GclAuthenticateOrSignData data, String... pin);

    boolean verifyPin(String readerId, String... pin) throws VerifyPinException;

    String exchangeApiKeyForToken();

    String refreshJwt();
}
