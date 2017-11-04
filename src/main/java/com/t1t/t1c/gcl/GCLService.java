package com.t1t.t1c.gcl;

import com.t1t.t1c.LibConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GCLService {
    private static final Logger _LOG = LoggerFactory.getLogger(GCLService.class);
    private static GCLClient gclClient;
    private static GCLAdminClient gclAdminClient;
    private static DSClient dsClient;

    public GCLService(LibConfig config) {
        gclClient = RestServiceBuilder.getGCLService(config,GCLClient.class);
        gclAdminClient = RestServiceBuilder.getGCLAdminService(config,GCLAdminClient.class);
        dsClient = RestServiceBuilder.getDSService(config,DSClient.class);
    }
}