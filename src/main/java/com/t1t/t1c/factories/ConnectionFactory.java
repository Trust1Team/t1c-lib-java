package com.t1t.t1c.factories;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeidRestClient;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDniRestClient;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtRestClient;
import com.t1t.t1c.containers.smartcards.emv.GclEmvRestClient;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibRestClient;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraRestClient;
import com.t1t.t1c.containers.smartcards.piv.GclPivRestClient;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafenetRestClient;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraRestClient;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustRestClient;
import com.t1t.t1c.containers.smartcards.pki.oberthur.GclOberthurRestClient;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.ds.DsRestClient;
import com.t1t.t1c.ocv.OcvRestClient;
import com.t1t.t1c.rest.RestServiceBuilder;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 *
 * Factory to establish connection instances.
 */
public final class ConnectionFactory {
    /*Config*/
    private LibConfig config;
    /*General connections*/
    private GclRestClient gclRestClient;
    private GclAdminRestClient gclAdminRestClient;
    private DsRestClient dsRestClient;
    private OcvRestClient ocvRestClient;

    /*Container connections*/
    private GclBeidRestClient gclBeidRestClient;
    private GclLuxIdRestClient gclLuxIdRestClient;
    private GclLuxTrustRestClient gclLuxTrustRestClient;
    private GclDniRestClient gclDniRestClient;
    private GclPtRestClient gclPtRestClient;
    private GclEmvRestClient gclEmvRestClient;
    private GclMobibRestClient gclMobibRestClient;
    private GclOcraRestClient gclOcraRestClient;
    private GclPivRestClient gclPivRestClient;
    private GclSafenetRestClient gclSafenetRestClient;
    private GclAventraRestClient gclAventraRestClient;
    private GclOberthurRestClient gclOberthurRestClient;

    public ConnectionFactory(LibConfig config) {
        this.config = config;
        resetConnections();
    }

    private void resetConnections(){
        //global connections
        this.dsRestClient = RestServiceBuilder.getDsRestClient(config);
        this.gclRestClient = RestServiceBuilder.getGclRestClient(config);
        this.gclAdminRestClient = RestServiceBuilder.getGclAdminRestClient(config);
        this.ocvRestClient = RestServiceBuilder.getOcvRestClient(config);
        //container specific connections
        this.gclBeidRestClient = RestServiceBuilder.getContainerRestClient(config,GclBeidRestClient.class);
        this.gclLuxIdRestClient = RestServiceBuilder.getContainerRestClient(config,GclLuxIdRestClient.class);
        this.gclLuxTrustRestClient = RestServiceBuilder.getContainerRestClient(config,GclLuxTrustRestClient.class);
        this.gclDniRestClient = RestServiceBuilder.getContainerRestClient(config,GclDniRestClient.class);
        this.gclPtRestClient = RestServiceBuilder.getContainerRestClient(config,GclPtRestClient.class);
        this.gclEmvRestClient = RestServiceBuilder.getContainerRestClient(config,GclEmvRestClient.class);
        this.gclMobibRestClient = RestServiceBuilder.getContainerRestClient(config,GclMobibRestClient.class);
        this.gclOcraRestClient = RestServiceBuilder.getContainerRestClient(config,GclOcraRestClient.class);
        this.gclPivRestClient = RestServiceBuilder.getContainerRestClient(config,GclPivRestClient.class);
        this.gclSafenetRestClient = RestServiceBuilder.getContainerRestClient(config,GclSafenetRestClient.class);
        this.gclAventraRestClient = RestServiceBuilder.getContainerRestClient(config,GclAventraRestClient.class);
        this.gclOberthurRestClient = RestServiceBuilder.getContainerRestClient(config,GclOberthurRestClient.class);
    }

    /*Getters*/
    public GclRestClient getGclRestClient() {
        return gclRestClient;
    }
    public GclAdminRestClient getGclAdminRestClient() {
        return gclAdminRestClient;
    }
    public DsRestClient getDsRestClient() { return dsRestClient; }
    public OcvRestClient getOcvRestClient() {
        return ocvRestClient;
    }
    public GclBeidRestClient getGclBeidRestClient() {
        return gclBeidRestClient;
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
    public GclPtRestClient getGclPtRestClient() {
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
    public GclSafenetRestClient getGclSafenetRestClient() {
        return gclSafenetRestClient;
    }
    public GclAventraRestClient getGclAventraRestClient() {
        return gclAventraRestClient;
    }
    public GclOberthurRestClient getGclOberthurRestClient() {
        return gclOberthurRestClient;
    }
    public LibConfig getConfig() { return config; }
    public void setConfig(LibConfig config) { this.config = config; resetConnections(); }
}