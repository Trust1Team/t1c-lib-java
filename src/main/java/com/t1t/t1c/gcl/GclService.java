package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.rest.DsRestClient;
import com.t1t.t1c.rest.GclAdminRestClient;
import com.t1t.t1c.rest.GclRestClient;
import com.t1t.t1c.rest.RestServiceBuilder;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclService {

    private static IGclClient gclClient;
    private static IGclAdminClient gclAdminClient;
    private static IDsClient dsClient;

    public GclService(LibConfig config) {
        gclClient = new GclClient(config, RestServiceBuilder.getGCLService(config, GclRestClient.class));
        gclAdminClient = new GclAdminClient(config, RestServiceBuilder.getGCLAdminService(config, GclAdminRestClient.class));
        dsClient = new DsClient(config, RestServiceBuilder.getDSService(config, DsRestClient.class));
    }

    public static IDsClient getDsClient() {
        return dsClient;
    }

    public static IGclClient getGclClient() {
        return gclClient;
    }

    public static IGclAdminClient getGclAdminClient() {
        return gclAdminClient;
    }
}