package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetRequest;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.utils.ContainerUtil;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class JavaClientExample {
    /*Keys*/
    private static String API_KEY = "2cc27598-2af7-48af-a2df-c7352e5368ff";
    /*Uris*/
    private static final String URI_GATEWAY = "https://accapim.t1t.be";
    private static final String OCV_CONTEXT_PATH = "/trust1team/ocv-api/v1";
    private static final String DS_CONTEXT_PATH = "/trust1team/gclds/v1";
    private static final String URI_T1C_GCL = "https://localhost:10443/v1/";
    private static final Gson GSON = new Gson();

    private static T1cClient client;

    public static void main(String[] args) {
        /*Config*/
        LibConfig conf = new LibConfig();
        conf.setEnvironment(Environment.DEV);
        conf.setDsUri(URI_GATEWAY);
        conf.setOcvUri(URI_GATEWAY);
        conf.setOcvContextPath(OCV_CONTEXT_PATH);
        conf.setDsContextPath(DS_CONTEXT_PATH);
        conf.setGclClientUri(URI_T1C_GCL);
        conf.setApiKey(API_KEY);
        conf.setHardwarePinPadForced(false);
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);

        /*Instantiate client*/
        client = new T1cClient(conf);

        // Poll reader for insterted cards and get first available
        GclReader reader = client.getCore().pollCardInserted();

        ContainerType type = ContainerUtil.determineContainer(reader.getCard());

        switch (type) {
            case AVENTRA:
                break;
            case BEID:
                beIdUseCases(reader);
                break;
            case DNIE:
                break;
            case EMV:
                break;
            case EST:
                break;
            case LUXID:
                luxIdUseCases(reader);
                break;
            case LUXTRUST:
                break;
            case MOBIB:
                break;
            case OBERTHUR:
                break;
            case OCRA:
                break;
            case PIV:
                break;
            case PT:
                break;
            case READER_API:
                break;
            case SAFENET:
                break;
        }
    }

    private static void beIdUseCases(GclReader reader) {

        BeIdContainer container = client.getBeIdContainer(reader);

        // Comment
        //System.out.println(container.getAllData().toString());

        // With hardware PinPad
        //container.verifyPin();

        // Without hardware PinPad
        //container.verifyPin("1111");

        // Sign data
        System.out.println(container.sign(new GclAuthenticateOrSignData().withData("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=").withAlgorithmReference("SHA256")));

    }

    private static void luxIdUseCases(GclReader reader) {
        LuxIdContainer container = client.getLuxIdContainer(reader, "123456");
        System.out.println(GSON.toJson(container.getAllData()));
    }

}