package com.t1t.t1c.services;

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
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.ISafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.GclAdminClient;
import com.t1t.t1c.gcl.GclClient;
import com.t1t.t1c.gcl.IGclAdminClient;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.ocv.OcvClient;
import com.t1t.t1c.rest.*;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class FactoryService {

    private static LibConfig config;
    private static IGclClient gclClient;
    private static IGclAdminClient gclAdminClient;
    private static IDsClient dsClient;
    private static ContainerRestClient containerRestClient;
    private static IOcvClient ocvClient;

    private FactoryService() {
    }

    public static IDsClient getDsClient() {
        if (dsClient == null) {
            checkConfigPresent();
            dsClient = new DsClient(getDsRestClient());

        }
        return dsClient;
    }

    public static IGclClient getGclClient() {
        if (gclClient == null) {
            checkConfigPresent();
            gclClient = new GclClient(getGclRestClient());

        }
        return gclClient;
    }

    public static IGclAdminClient getGclAdminClient() {
        if (gclAdminClient == null) {
            checkConfigPresent();
            gclAdminClient = new GclAdminClient(getGclAdminRestClient());
        }
        return gclAdminClient;
    }

    public static IOcvClient getOcvClient() {
        if (ocvClient == null) {
            checkConfigPresent();
            ocvClient = new OcvClient(getOcvRestClient());
        }
        return ocvClient;
    }

    public static GclAdminRestClient getGclAdminRestClient() {
        return RestServiceBuilder.getGclAdminRestClient(config);
    }

    public static GclRestClient getGclRestClient() {
        return RestServiceBuilder.getGclRestClient(config);
    }

    public static DsRestClient getDsRestClient() {
        return RestServiceBuilder.getDsRestClient(config);
    }

    public static OcvRestClient getOcvRestClient() {
        return RestServiceBuilder.getOcvRestClient(config);
    }

    //TODO - implement Citrix
    public static IAgent getAgent() {
        throw ExceptionFactory.unsupportedOperationException("Unsupported");
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
            case READER_API:
                return getReaderContainer(readerId);
            case SAFENET:
                return getSafeNetContainer(readerId);
            default:
                throw ExceptionFactory.unsupportedOperationException("No container for type found");
        }
    }

    public static IBeIdContainer getBeIdContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new BeIdContainer(readerId, getContainerRestClient());
    }

    public static ILuxIdContainer getLuxIdContainer(String readerId, String pin) {
        checkConfigAndReaderIdPresent(readerId);
        return new LuxIdContainer(readerId, getContainerRestClient(), pin);
    }

    public static ILuxTrustContainer getLuxTrustContainer(String readerId, String pin) {
        checkConfigAndReaderIdPresent(readerId);
        return new LuxTrustContainer(readerId, getContainerRestClient(), pin);
    }

    public static IDnieContainer getDnieContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new DnieContainer(readerId, getContainerRestClient());
    }

    public static IPtEIdContainer getPtIdContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new PtEIdContainer(readerId, getContainerRestClient());
    }

    public static IEmvContainer getEmvContainer(String readerId) {
        checkConfigAndReaderIdPresent(readerId);
        return new EmvContainer(readerId, getContainerRestClient());
    }

    public static IMobibContainer getMobibContainer(String readerId) {
        return new MobibContainer(readerId, getContainerRestClient());
    }

    public static IOcraContainer getOcraContainer(String readerId) {
        return new OcraContainer(readerId, getContainerRestClient());
    }

    public static IAventraContainer getAventraContainer(String readerId) {
        //TODO - AVENTRA
        throw ExceptionFactory.unsupportedOperationException("Unsupported");
    }


    public static IOberthurContainer getOberthurContainer(String readerId) {
        //TODO - OBERTHUR
        throw ExceptionFactory.unsupportedOperationException("Unsupported");
    }

    public static IPivContainer getPivContainer(String readerId) {
        //TODO - PIV
        throw ExceptionFactory.unsupportedOperationException("Unsupported");
    }

    public static ISafeNetContainer getSafeNetContainer(String readerId) {
        return getSafeNetContainer(readerId, null);
    }

    public static ISafeNetContainer getSafeNetContainer(String readerId, SafeNetContainerConfiguration configuration) {
        if (configuration == null) {
            configuration = new SafeNetContainerConfiguration();
        }
        return new SafeNetContainer(readerId, getContainerRestClient(), configuration);
    }

    public static IReaderApiContainer getReaderContainer(String readerId) {
        //TODO - READERAPI
        throw ExceptionFactory.unsupportedOperationException("Unsupported");
    }

    public static IBelfiusContainer getBelfiusContainer(String readerId) {
        //TODO - BELFIUS
        throw ExceptionFactory.unsupportedOperationException("Unsupported");
    }

    public static LibConfig getConfig() {
        return config;
    }

    public static void setConfig(LibConfig config) {
        FactoryService.config = config;
        if (config != null) {
            gclClient = new GclClient(RestServiceBuilder.getGclRestClient(config));
            gclAdminClient = new GclAdminClient(RestServiceBuilder.getGclAdminRestClient(config));
            dsClient = new DsClient(RestServiceBuilder.getDsRestClient(config));
            ocvClient = new OcvClient(RestServiceBuilder.getOcvRestClient(config));
            containerRestClient = RestServiceBuilder.getContainerRestClient(config);
        } else {
            gclClient = null;
            gclAdminClient = null;
            dsClient = null;
            ocvClient = null;
            containerRestClient = null;
        }
    }

    //
    // Utility methods
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