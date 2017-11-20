package com.t1t.t1c.factories;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeidClient;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDniClient;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdClient;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustClient;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.gcl.GclAdminClient;
import com.t1t.t1c.gcl.GclClient;
import com.t1t.t1c.gcl.IGclAdminClient;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.ocv.OcvClient;
import com.t1t.t1c.rest.*;
import com.t1t.t1c.utils.ContainerUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public final class ConnectionFactory {

    private LibConfig config;
    private IGclClient gclClient;
    private IGclAdminClient gclAdminClient;
    private IDsClient dsClient;
    private IOcvClient ocvClient;

    public ConnectionFactory(LibConfig config) {
        this.config = config;
        this.dsClient = new DsClient(RestServiceBuilder.getDsRestClient(config));
        this.gclClient = new GclClient(RestServiceBuilder.getGclRestClient(config));
        this.gclAdminClient = new GclAdminClient(RestServiceBuilder.getGclAdminRestClient(config),config);
        this.ocvClient = new OcvClient(RestServiceBuilder.getOcvRestClient(config));
    }

    public GenericContainer getGenericContainer(String readerId, String... pin) {
        return getGenericContainer(readerId, ContainerUtil.determineContainer(gclClient.getReader(readerId).getCard()), pin);
    }

    public <V extends GenericContainer, U extends ContainerRestClient> V getContainer(String readerId, Class<V> containerClazz, Class<U> clientClazz, String... pin) throws GenericContainerException {
        try {
            Constructor ctor = containerClazz.getClass().getDeclaredConstructor(String.class, ContainerRestClient.class, String.class);
            return (V)ctor.newInstance(readerId, RestServiceBuilder.getContainerRestClient(config,clientClazz),getPin(pin));
        } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {
            throw ExceptionFactory.genericContainerException(e.getMessage());
        }
    }

    private GenericContainer getGenericContainer(String readerId, ContainerType type, String... pin) {
        switch (type) {
            case BEID:
                return new BeIdContainer(readerId, RestServiceBuilder.getContainerRestClient(config, GclBeidClient.class));
            case LUXID:
                return new LuxIdContainer(readerId, RestServiceBuilder.getContainerRestClient(config, GclLuxIdClient.class), getPin(pin));
            case LUXTRUST:
                return new LuxTrustContainer(readerId, RestServiceBuilder.getContainerRestClient(config, GclLuxTrustClient.class), getPin(pin));
            case DNIE:
                return new DnieContainer(readerId, RestServiceBuilder.getContainerRestClient(config, GclDniClient.class));
                //TODO
/*            case EMV:
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
                return getSafeNetContainer(readerId);*/
            default:
                throw ExceptionFactory.unsupportedOperationException("No container for type found");
        }
    }

    //TODO implement pin constraint in safe way
    private static String getPin(String... pin) { return (pin.length==0)? "" : pin[0]; }

    /*Getters*/
    public IGclClient getGclClient() { return gclClient; }
    public IGclAdminClient getGclAdminClient() { return gclAdminClient; }
    public IDsClient getDsClient() { return dsClient; }
    public IOcvClient getOcvClient() { return ocvClient; }
    public LibConfig getConfig() { return config; }
    public void setConfig(LibConfig config) { this.config = config; }
}