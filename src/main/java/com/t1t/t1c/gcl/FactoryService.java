package com.t1t.t1c.gcl;

import com.t1t.t1c.agent.IAgent;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.readerapi.IReaderApiContainer;
import com.t1t.t1c.containers.remoteloading.belfius.IBelfiusContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.be.IBeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.esp.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.esp.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.ISafenetContainer;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.rest.ContainerRestClient;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class FactoryService {

    private static LibConfig config;
    private static IGclClient gclClient;
    private static IGclAdminClient gclAdminClient;
    private static IDsClient dsClient;
    private static ContainerRestClient containerRestClient;

    public static IDsClient getDsClient() {
        if (dsClient == null) {
            checkConfigPresent();
            dsClient = new DsClient(config, RestServiceBuilder.getDsRestClient(config));

        }
        return dsClient;
    }

    public static IGclClient getGclClient() {
        if (gclClient == null) {
            checkConfigPresent();
            gclClient = new GclClient(config, RestServiceBuilder.getGclRestClient(config));

        }
        return gclClient;
    }

    public static IGclAdminClient getGclAdminClient() {
        if (gclAdminClient == null) {
            checkConfigPresent();
            gclAdminClient = new GclAdminClient(config, RestServiceBuilder.getGclAdminRestClient(config));
        }
        return gclAdminClient;
    }

    //TODO - implement OCV
    public static IOcvClient getOcvClient() {
        throw new UnsupportedOperationException();
    }

    //TODO - implement Citrix
    public static IAgent getAgent() {
        throw new UnsupportedOperationException();
    }

    public static GenericContainer getGenericContainer(String readerId, String... pin) {
        return getGenericContainer(readerId, ContainerUtil.determineContainer(getGclClient().getReader(readerId).getCard()), pin);
    }

    public static GenericContainer getGenericContainer(String readerId, ContainerType type, String... pin) {
        switch (type) {
            case BEID:
                return getBeIdContainer(readerId);
            case LUXID:
                return getLuxIdContainer(readerId, getPin(pin));
            case LUXTRUST:
                return getLuxTrustContainer(readerId, getPin(pin));
            case DNIE:
                return getDnieContainer(readerId);
            case EMV:
                return getEmvContainer(readerId);
            case PT:
                return getPtIdContainer(readerId);
            case MOBIB:
                return getMobibContainer(readerId);
            case OCRA:
                return getOcraContainer(readerId);
            case AVENTRA:
                return getAventraContainer(readerId);
            case OBERTHUR:
                return getOberthurContainer(readerId);
            case PIV:
                return getPivContainer(readerId);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static IBeIdContainer getBeIdContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new BeIdContainer(config, readerId, getContainerRestClient());
    }

    public static ILuxIdContainer getLuxIdContainer(String readerId, String pin) {
        checkConfigAndReaderIdPresent(readerId);
        return new LuxIdContainer(config, readerId, getContainerRestClient(), pin);
    }

    public static ILuxTrustContainer getLuxTrustContainer(String readerId, String pin) {
        checkConfigAndReaderIdPresent(readerId);
        return new LuxTrustContainer(config, readerId, getContainerRestClient(), pin);
    }

    public static IDnieContainer getDnieContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new DnieContainer(config, readerId, getContainerRestClient());
    }

    public static IPtEIdContainer getPtIdContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new PtEIdContainer(config, readerId, getContainerRestClient());
    }

    public static IEmvContainer getEmvContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new EmvContainer(config, readerId, getContainerRestClient());
    }

    public static IMobibContainer getMobibContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - MOBIB

    public static IOcraContainer getOcraContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - OCRA

    public static IAventraContainer getAventraContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - AVENTRA

    public static IOberthurContainer getOberthurContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - OBERTHUR

    public static IPivContainer getPivContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - PIV

    public static ISafenetContainer getSafenetContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - SAFENET

    public static IReaderApiContainer getReaderContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - READERAPI

    public static IBelfiusContainer getBelfiusContainer(String readerId) {
        throw new UnsupportedOperationException();
    }
    //TODO - BELFIUS

    public static LibConfig getConfig() {
        return config;
    }

    public static void setConfig(LibConfig config) {
        FactoryService.config = config;
        if (config != null) {
            gclClient = new GclClient(config, RestServiceBuilder.getGclRestClient(config));
            gclAdminClient = new GclAdminClient(config, RestServiceBuilder.getGclAdminRestClient(config));
            dsClient = new DsClient(config, RestServiceBuilder.getDsRestClient(config));
            containerRestClient = RestServiceBuilder.getContainerRestClient(config);
        } else {
            gclClient = null;
            gclAdminClient = null;
            dsClient = null;
            containerRestClient = null;
        }
    }

    // Utility methods
    //
    //

    public static ContainerRestClient getContainerRestClient() {
        if (containerRestClient == null) {
            checkConfigPresent();
            containerRestClient = RestServiceBuilder.getContainerRestClient(config);
        }
        return containerRestClient;
    }

    private static void checkConfigPresent() {
        if (config == null) {
            throw ExceptionFactory.configException("GclService configuration is not set");
        }
    }

    private static void checkConfigAndReaderIdPresent(String readerId) {
        checkConfigPresent();
        if (StringUtils.isBlank(readerId)) {
            throw ExceptionFactory.genericContainerException("Cannot instantiate container, reader ID not provided");
        }
    }

    private static String getPin(String... pin) {
        if (pin.length == 0) {
            throw ExceptionFactory.genericContainerException("PIN required for container");
        }
        return pin[0];
    }
}