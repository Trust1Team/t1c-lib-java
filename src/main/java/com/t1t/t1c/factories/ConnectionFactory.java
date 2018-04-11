package com.t1t.t1c.factories;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.readerapi.GclReaderApiRestClient;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDniRestClient;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdRestClient;
import com.t1t.t1c.containers.smartcards.emv.GclEmvRestClient;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibRestClient;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraRestClient;
import com.t1t.t1c.containers.smartcards.piv.GclPivRestClient;
import com.t1t.t1c.containers.smartcards.pkcs11.GclPkcs11RestClient;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraRestClient;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustRestClient;
import com.t1t.t1c.containers.smartcards.pki.oberthur.GclOberthurRestClient;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclCitrixRestClient;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.ds.DsRestClient;
import com.t1t.t1c.ocv.OcvRestClient;
import com.t1t.t1c.rest.RestServiceBuilder;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 * <p>
 * Factory to establish connection instances.
 */
public final class ConnectionFactory {
    /*Config*/
    private LibConfig config;
    /*General connections*/
    private GclRestClient gclRestClient;
    private GclCitrixRestClient gclCitrixRestClient;
    private GclAdminRestClient gclAdminRestClient;
    private DsRestClient dsRestClient;
    private OcvRestClient ocvRestClient;

    /*Container connections*/
    private GclBeIdRestClient gclBeIdRestClient;
    private GclLuxIdRestClient gclLuxIdRestClient;
    private GclLuxTrustRestClient gclLuxTrustRestClient;
    private GclDniRestClient gclDniRestClient;
    private GclPtIdRestClient gclPtRestClient;
    private GclEmvRestClient gclEmvRestClient;
    private GclMobibRestClient gclMobibRestClient;
    private GclOcraRestClient gclOcraRestClient;
    private GclPivRestClient gclPivRestClient;
    private GclPkcs11RestClient gclSafenetRestClient;
    private GclAventraRestClient gclAventraRestClient;
    private GclOberthurRestClient gclOberthurRestClient;
    private GclReaderApiRestClient gclReaderApiRestClient;

    public ConnectionFactory(LibConfig config) {
        this.config = config;
        resetConnections();
    }

    private void resetConnections() {
        //global connections

        this.dsRestClient = RestServiceBuilder.getDsRestClient(config);
        this.gclRestClient = RestServiceBuilder.getGclRestClient(config);
        this.gclCitrixRestClient = RestServiceBuilder.getGclCitrixRestClient(config);
        this.gclAdminRestClient = RestServiceBuilder.getGclAdminRestClient(config);
        this.ocvRestClient = RestServiceBuilder.getOcvRestClient(config);
        //container specific connections
        this.gclBeIdRestClient = RestServiceBuilder.getContainerRestClient(config, GclBeIdRestClient.class);
        this.gclLuxIdRestClient = RestServiceBuilder.getContainerRestClient(config, GclLuxIdRestClient.class);
        this.gclLuxTrustRestClient = RestServiceBuilder.getContainerRestClient(config, GclLuxTrustRestClient.class);
        this.gclDniRestClient = RestServiceBuilder.getContainerRestClient(config, GclDniRestClient.class);
        this.gclPtRestClient = RestServiceBuilder.getContainerRestClient(config, GclPtIdRestClient.class);
        this.gclEmvRestClient = RestServiceBuilder.getContainerRestClient(config, GclEmvRestClient.class);
        this.gclMobibRestClient = RestServiceBuilder.getContainerRestClient(config, GclMobibRestClient.class);
        this.gclOcraRestClient = RestServiceBuilder.getContainerRestClient(config, GclOcraRestClient.class);
        this.gclPivRestClient = RestServiceBuilder.getContainerRestClient(config, GclPivRestClient.class);
        this.gclSafenetRestClient = RestServiceBuilder.getContainerRestClient(config, GclPkcs11RestClient.class);
        this.gclAventraRestClient = RestServiceBuilder.getContainerRestClient(config, GclAventraRestClient.class);
        this.gclOberthurRestClient = RestServiceBuilder.getContainerRestClient(config, GclOberthurRestClient.class);
        this.gclReaderApiRestClient = RestServiceBuilder.getContainerRestClient(config, GclReaderApiRestClient.class);
    }

    /*Getters*/
    public GclRestClient getGclRestClient() {
        return gclRestClient;
    }

    public GclAdminRestClient getGclAdminRestClient() {
        return gclAdminRestClient;
    }

    public GclCitrixRestClient getGclCitrixRestClient() {
        return gclCitrixRestClient;
    }

    public DsRestClient getDsRestClient() {
        return dsRestClient;
    }

    public OcvRestClient getOcvRestClient() {
        return ocvRestClient;
    }

    public GclBeIdRestClient getGclBeIdRestClient() {
        return gclBeIdRestClient;
    }

    public GclLuxIdRestClient getGclLuxIdRestClient() {
        return gclLuxIdRestClient;
    }

    public GclLuxTrustRestClient getGclLuxTrustRestClient() {
        return gclLuxTrustRestClient;
    }

    public GclDniRestClient getGclDniRestClient() {
        return gclDniRestClient;
    }

    public GclPtIdRestClient getGclPtRestClient() {
        return gclPtRestClient;
    }

    public GclEmvRestClient getGclEmvRestClient() {
        return gclEmvRestClient;
    }

    public GclMobibRestClient getGclMobibRestClient() {
        return gclMobibRestClient;
    }

    public GclOcraRestClient getGclOcraRestClient() {
        return gclOcraRestClient;
    }

    public GclPivRestClient getGclPivRestClient() {
        return gclPivRestClient;
    }

    public GclPkcs11RestClient getGclSafenetRestClient() {
        return gclSafenetRestClient;
    }

    public GclAventraRestClient getGclAventraRestClient() {
        return gclAventraRestClient;
    }

    public GclOberthurRestClient getGclOberthurRestClient() {
        return gclOberthurRestClient;
    }

    public GclReaderApiRestClient getGclReaderApiRestClient() {
        return gclReaderApiRestClient;
    }

    public LibConfig getConfig() {
        return config;
    }

    public void setConfig(LibConfig config) {
        this.config = config;
        resetConnections();
    }
}