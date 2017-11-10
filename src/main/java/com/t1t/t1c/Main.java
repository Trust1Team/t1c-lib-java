package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
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

        /*GclService.setConfig(conf);
        IDsClient dsClient = GclService.getDsClient();
        System.out.println("DS Info: " + gson.toJson(dsClient.getInfo()));
        String jwt = dsClient.getJWT();
        System.out.println("DS JWT: " + jwt);
        System.out.println("DS Public Key: " + dsClient.getPubKey());
        System.out.println("DS URL: " + dsClient.getUrl());
        System.out.println("DS Download link: " + dsClient.getDownloadLink(new DsDownloadRequest().withOs(new Os().withArchitecture("64").withName("Windows").withVersion("10"))));
        System.out.println("DS Device Info: " + gson.toJson(dsClient.getDevice("BC323A7D139B0623")));
        jwt = dsClient.refreshJWT(jwt);
        System.out.println("DS Refreshed JWT: " + dsClient.refreshJWT(jwt));
        conf.setJwt(jwt);
        GclService.setConfig(conf);
        IGclClient gclClient = GclService.getGclClient();
        IGclAdminClient gclAdminClient = GclService.getGclAdminClient();
        System.out.println("GCL Containers: " + gson.toJson(gclClient.getContainers()));
        System.out.println("GCL Info: " + gson.toJson(gclClient.getInfo()));
        System.out.println("GCL Readers: " + gson.toJson(gclClient.getReaders()));
        System.out.println("GCL URL: " + gson.toJson(gclClient.getUrl()));
        System.out.println("GCL Readers without cards: " + gson.toJson(gclClient.getReadersWithoutInsertedCard()));
        List<GclReader> readers = gclClient.getReadersWithInsertedCard();
        System.out.println("GCL Readers with cards: " + gson.toJson(readers));
        for (GclReader reader : readers) {
            System.out.println("GCL Individual reader: " + gson.toJson(gclClient.getReader(reader.getId())));
        }
        System.out.println("GCL Admin public key: " + gclAdminClient.getPublicKey());*/
        T1cClient t1cClient = new T1cClient(conf);

        System.out.println(gson.toJson(t1cClient.dumpData("57a3e2e71c48cee9")));
        //String sign = t1cClient.sign("57a3e2e71c48cee9", new GclAuthenticateOrSignData().withData("dWq99zklgwsr54Mah+Ki+Y5w0LUi2FnBoYTQENSf3PM=").withPin("8213"));
        //System.out.println("AUTH: " + auth);
        //System.out.println("SIGN: " + sign);
        //System.out.println(sign.equals(auth));

        //IBeIdContainer container = t1cClient.getBeIdContainer("57a3e2e71c48cee9");

        //System.out.println(gson.toJson(t1cClient.getGenericContainerFor("57a3e2e71c48cee9").getAllDataFilters()));
        //System.out.println(gson.toJson(t1cClient.dumpData("57a3e2e71c48cee9", Collections.singletonList("rrn-certificate"))));

        //System.out.println(gson.toJson(container.verifyPin("57a3e2e71c48cee9", "8213")));
    }

}
