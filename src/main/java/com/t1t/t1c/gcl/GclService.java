package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.rest.DsRestClient;
import com.t1t.t1c.rest.GclAdminRestClient;
import com.t1t.t1c.rest.GclRestClient;
import com.t1t.t1c.rest.RestServiceBuilder;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclService {

    private static LibConfig config;
    private static IGclClient gclClient;
    private static IGclAdminClient gclAdminClient;
    private static IDsClient dsClient;

    public GclService(LibConfig config) {
        gclClient = new GclClient(config, RestServiceBuilder.getGCLService(config, GclRestClient.class));
        gclAdminClient = new GclAdminClient(config, RestServiceBuilder.getGCLAdminService(config, GclAdminRestClient.class));
        dsClient = new DsClient(config, RestServiceBuilder.getDSService(config, DsRestClient.class));
    }

    public static IDsClient getDsClient() {
        if (dsClient == null) {
            if (config == null) {
                throw ExceptionFactory.configException("GCLService configuration is not set");
            } else {
                dsClient = new DsClient(config, RestServiceBuilder.getDSService(config, DsRestClient.class));
            }
        }
        return dsClient;
    }

    public static IGclClient getGclClient() {
        if (gclClient == null) {
            if (config == null) {
                throw ExceptionFactory.configException("GCLService configuration is not set");
            } else {
                gclClient = new GclClient(config, RestServiceBuilder.getGCLService(config, GclRestClient.class));
            }
        }
        return gclClient;
    }

    public static IGclAdminClient getGclAdminClient() {
        if (gclAdminClient == null) {
            if (config == null) {
                throw ExceptionFactory.configException("GCLService configuration is not set");
            } else {
                gclAdminClient = new GclAdminClient(config, RestServiceBuilder.getGCLAdminService(config, GclAdminRestClient.class));
            }
        }
        return gclAdminClient;
    }

    public static LibConfig getConfig() {
        return config;
    }

    public static void setConfig(LibConfig config) {
        GclService.config = config;
        gclClient = new GclClient(config, RestServiceBuilder.getGCLService(config, GclRestClient.class));
        gclAdminClient = new GclAdminClient(config, RestServiceBuilder.getGCLAdminService(config, GclAdminRestClient.class));
        dsClient = new DsClient(config, RestServiceBuilder.getDSService(config, DsRestClient.class));
    }
}