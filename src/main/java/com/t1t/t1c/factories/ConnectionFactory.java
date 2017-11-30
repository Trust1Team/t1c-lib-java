package com.t1t.t1c.factories;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeidRestClientCommon;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDniRestClientCommon;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdRestClientCommon;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtRestClientCommon;
import com.t1t.t1c.containers.smartcards.emv.GclEmvRestClientCommon;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibRestClientCommon;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraRestClientCommon;
import com.t1t.t1c.containers.smartcards.piv.GclPivRestClientCommon;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafenetRestClientCommon;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraRestClientCommon;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustRestClientCommon;
import com.t1t.t1c.containers.smartcards.pki.oberthur.GclOberthurRestClientCommon;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.ds.DsRestClient;
import com.t1t.t1c.ocv.OcvRestClient;
import com.t1t.t1c.rest.*;

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
    private GclBeidRestClientCommon gclBeidRestClient;
    private GclLuxIdRestClientCommon gclLuxIdRestClient;
    private GclLuxTrustRestClientCommon gclLuxTrustRestClient;
    private GclDniRestClientCommon gclDniRestClient;
    private GclPtRestClientCommon gclPtRestClient;
    private GclEmvRestClientCommon gclEmvRestClient;
    private GclMobibRestClientCommon gclMobibRestClient;
    private GclOcraRestClientCommon gclOcraRestClient;
    private GclPivRestClientCommon gclPivRestClient;
    private GclSafenetRestClientCommon gclSafenetRestClient;
    private GclAventraRestClientCommon gclAventraRestClient;
    private GclOberthurRestClientCommon gclOberthurRestClient;

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
        this.gclBeidRestClient = RestServiceBuilder.getContainerRestClient(config,GclBeidRestClientCommon.class);
        this.gclLuxIdRestClient = RestServiceBuilder.getContainerRestClient(config,GclLuxIdRestClientCommon.class);
        this.gclLuxTrustRestClient = RestServiceBuilder.getContainerRestClient(config,GclLuxTrustRestClientCommon.class);
        this.gclDniRestClient = RestServiceBuilder.getContainerRestClient(config,GclDniRestClientCommon.class);
        this.gclPtRestClient = RestServiceBuilder.getContainerRestClient(config,GclPtRestClientCommon.class);
        this.gclEmvRestClient = RestServiceBuilder.getContainerRestClient(config,GclEmvRestClientCommon.class);
        this.gclMobibRestClient = RestServiceBuilder.getContainerRestClient(config,GclMobibRestClientCommon.class);
        this.gclOcraRestClient = RestServiceBuilder.getContainerRestClient(config,GclOcraRestClientCommon.class);
        this.gclPivRestClient = RestServiceBuilder.getContainerRestClient(config,GclPivRestClientCommon.class);
        this.gclSafenetRestClient = RestServiceBuilder.getContainerRestClient(config,GclSafenetRestClientCommon.class);
        this.gclAventraRestClient = RestServiceBuilder.getContainerRestClient(config,GclAventraRestClientCommon.class);
        this.gclOberthurRestClient = RestServiceBuilder.getContainerRestClient(config,GclOberthurRestClientCommon.class);
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
    public GclBeidRestClientCommon getGclBeidRestClient() {
        return gclBeidRestClient;
    }
    public GclLuxIdRestClientCommon getGclLuxIdRestClient() {
        return gclLuxIdRestClient;
    }
    public GclLuxTrustRestClientCommon getGclLuxTrustRestClient() {
        return gclLuxTrustRestClient;
    }
    public GclDniRestClientCommon getGclDniRestClient() {
        return gclDniRestClient;
    }
    public GclPtRestClientCommon getGclPtRestClient() {
        return gclPtRestClient;
    }
    public GclEmvRestClientCommon getGclEmvRestClient() {
        return gclEmvRestClient;
    }
    public GclMobibRestClientCommon getGclMobibRestClient() {
        return gclMobibRestClient;
    }
    public GclOcraRestClientCommon getGclOcraRestClient() {
        return gclOcraRestClient;
    }
    public GclPivRestClientCommon getGclPivRestClient() {
        return gclPivRestClient;
    }
    public GclSafenetRestClientCommon getGclSafenetRestClient() {
        return gclSafenetRestClient;
    }
    public GclAventraRestClientCommon getGclAventraRestClient() {
        return gclAventraRestClient;
    }
    public GclOberthurRestClientCommon getGclOberthurRestClient() {
        return gclOberthurRestClient;
    }
    public LibConfig getConfig() { return config; }
    public void setConfig(LibConfig config) { this.config = config; resetConnections(); }
}