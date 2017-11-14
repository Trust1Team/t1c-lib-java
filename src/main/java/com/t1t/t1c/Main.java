package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.smartcards.eid.be.IBeIdContainer;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.ocv.IOcvClient;

import java.nio.file.Paths;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class Main {

    public static void main(String[] args) {

        LibConfig conf = new LibConfig();
        //conf.setVersion("0.0.1-SNAPSHOT");
        conf.setEnvironment(Environment.DEV);
        //conf.setDsDownloadContextPath("/trust1team/gclds-file/v1");
        conf.setGatewayUri("https://accapim.t1t.be");
        conf.setGclClientUri("https://localhost:10443/v1/");
        conf.setDsContextPath("/trust1team/gclds/v1");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        conf.setOcvContexPath("/trust1team/ocv-api/v1");
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);
        //T1cClient t1cClient = new T1cClient(Paths.get("/usr/local/t1c.conf"));
        T1cClient t1cClient = new T1cClient(conf);
        IOcvClient ocvClient = t1cClient.getOcvClient();
        /*GclReader reader = t1cClient.getCore().pollCardInserted();
        IBeIdContainer container = t1cClient.getBeIdContainer(reader.getId());
        GclBeIDAllCertificates certificates = (GclBeIDAllCertificates) container.getAllCertificates();
        OcvCertificateChainValidationResponse response = ocvClient.validateCertificateChain(
                certificates.getAuthenticationCertificate(),
                certificates.getCitizenCertificate(),
                certificates.getRootCertificate());
        System.out.println(new Gson().toJson(response));*/


        OcvChallengeRequest hashtoAuthenticate = ocvClient.getChallenge(DigestAlgorithm.SHA256);
        GclReader reader = t1cClient.getCore().pollCardInserted();
        IBeIdContainer container = t1cClient.getBeIdContainer(reader.getId());
        String authenticatedData = container.authenticate(new GclAuthenticateOrSignData()
                .withPin("8213")
                .withData(hashtoAuthenticate.getHash())
                .withAlgorithmReference(hashtoAuthenticate.getDigestAlgorithm()));
        OcvChallengeVerificationResponse response = ocvClient.verifyChallenge(new OcvChallengeVerificationRequest()
                .withBase64Certificate(container.getAuthenticationCertificate(false).getBase64())
                .withBase64Signature(authenticatedData)
                .withHash(hashtoAuthenticate.getHash())
                .withDigestAlgorithm(hashtoAuthenticate.getDigestAlgorithm()));


        /*List<GclReader> readers = t1cClient.getSignCapableReaders();
        GclReader reader = readers.get(0);
        GclAuthenticateOrSignData payload = new GclAuthenticateOrSignData()
                .withAlgorithmReference("sha256")
                .withData("E1uHACbPvhLew0gGmBH83lvtKIAKxU2/RezfBOsT6Vs=")
                .withPin("1234");
        String signedData = t1cClient.sign(reader.getId(), payload);*/



        /*List<GclContainer> containers = t1cClient.getCore().getContainers();
        PlatformInfo info = t1cClient.getCore().getPlatformInfo();
        GclContainer container = t1cClient.getCore().getContainers()
        boolean consented = t1cClient.getCore().getConsent("Title for the popup dialog", "code word to be shown in the dialog", 1);
        //List<GclReader> readers = t1cClient.getCore().getReadersWithoutInsertedCard();
        List<GclReader> readers = t1cClient.getCore().getReaders();
        GclReader reader = t1cClient.getCore().getReader(readers.get(0).getId());
        GclStatus status = t1cClient.getCore().getInfo();*/

    }

}