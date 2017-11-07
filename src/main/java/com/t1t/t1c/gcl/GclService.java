package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.rest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclService {
    private static final Logger _LOG = LoggerFactory.getLogger(GclService.class);
    private static GclRestClient gclClient;
    private static GclAdminRestClient gclAdminClient;
    private static DsClient dsService;

    public GclService(LibConfig config) {
        gclClient = RestServiceBuilder.getGCLService(config,GclRestClient.class);
        gclAdminClient = RestServiceBuilder.getGCLAdminService(config,GclAdminRestClient.class);
        dsService = new DsClient(config, RestServiceBuilder.getDSService(config,DsRestClient.class));
    }
}