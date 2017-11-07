package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.ds.DsClient;
import com.t1t.t1c.model.rest.DsDownloadRequest;
import com.t1t.t1c.model.rest.Os;
import com.t1t.t1c.rest.DsRestClient;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LibConfig conf = new LibConfig();
        conf.setVersion("0.0.1-SNAPSHOT");
        conf.setEnvironment(Environment.DEV);
        conf.setDsDownloadContextPath("/trust1team/gclds-file/v1");
        conf.setGatewayUri("https://accapim.t1t.be");
        conf.setGclClientUri("https://localhost:10443/v1/");
        conf.setDsContextPath("/trust1team/gclds/v1");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        Gson gson = new Gson();

        DsClient dsClient = new DsClient(conf, RestServiceBuilder.getDSService(conf, DsRestClient.class));
        System.out.println(gson.toJson(dsClient.getInfo()));
        System.out.println(dsClient.getJWT());
        System.out.println(dsClient.getPubKey());
        System.out.println(dsClient.getUrl());
        System.out.println(dsClient.getDownloadLink(new DsDownloadRequest().withOs(new Os().withArchitecture("64").withName("Windows").withVersion("10"))));
    }

}