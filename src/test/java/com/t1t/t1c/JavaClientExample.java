package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclSafeNetRequest;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class JavaClientExample {
    /*Keys*/
    private static String API_KEY = "2cc27598-2af7-48af-a2df-c7352e5368ff";
    /*Uris*/
    private static final String URI_GATEWAY = "https://accapim.t1t.be";
    private static final String URI_T1C_GCL = "https://localhost:10443/v1/";
    /*Context paths*/
    private static final String CONTEXT_DS = "/trust1team/gclds/v1";
    private static final String CONTEXT_OCV = "/trust1team/ocv-api/v1";

    public static void main(String[] args) {
        /*Config*/
        LibConfig conf = new LibConfig();
        conf.setEnvironment(Environment.DEV);
        conf.setDsUri(URI_GATEWAY);
        conf.setOcvUri(URI_GATEWAY);
        conf.setGclClientUri(URI_T1C_GCL);
        conf.setDsContextPath(CONTEXT_DS);
        conf.setApiKey(API_KEY);
        conf.setOcvContextPath(CONTEXT_OCV);
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);

        /*Instantiate client*/
        T1cClient client = new T1cClient(conf);

        GclReader reader = client.getCore().pollCardInserted();
        System.out.println(new Gson().toJson(client.getMobibContainer(reader.getId()).getAllData()));
        SafeNetContainerConfiguration config = new SafeNetContainerConfiguration();
        System.out.println(new Gson().toJson(new GclSafeNetRequest().withModule(config.getMac().toString())));
        /*T1cClient t1cClient = new T1cClient(Paths.get("/usr/local/t1c.conf"));
        T1cClient t1cClient = new T1cClient(conf);
        IOcvClient ocvClient = t1cClient.getOcvClient();
        *//*GclReader reader = t1cClient.getCore().pollCardInserted();
        IBeIdContainer container = t1cClient.getBeIdContainer(reader.getId());
        GclBeIDAllCertificates certificates = (GclBeIDAllCertificates) container.getAllCertificates();
        OcvCertificateChainValidationResponse response = ocvClient.validateCertificateChain(
                certificates.getAuthenticationCertificate(),
                certificates.getCitizenCertificate(),
                certificates.getRootCertificate());
        System.out.println(new Gson().toJson(response));*//*

        OcvChallengeRequest hashtoAuthenticate = ocvClient.getChallenge(DigestAlgorithm.SHA256);
        GclReader reader = t1cClient.getCore().pollCardInserted();

        //ILuxTrustContainer container = t1cClient.getLuxTrustContainer(reader.getId(), "123456");
        //IPtEIdContainer container = t1cClient.getPtIdContainer(reader.getId());
        //IDnieContainer container = t1cClient.getDnieContainer(reader.getId());
        IEmvContainer container = t1cClient.getEmvContainer(reader.getId());
        List<GclEmvApplication> applications = container.getApplications();
        GclEmvApplicationData applicationData = container.getApplicationData();
        String aid = "A0000000048002";
        GclEmvCertificate certificate = container.getIccPublicKeyCertificate(aid);
        GclDnieInfo info = container.getInfo();
        List<String> filter = Arrays.asList("application-data");
        GclEmvAllData data = (GclEmvAllData) container.getAllData(filter);

        DnieAllData allData = (DnieAllData) container.getAllData(filter);
        DnieAllCertificates allCertificates = (DnieAllCertificates) container.getAllCertificates();
        T1cCertificate intermediateCertificate = container.getIntermediateCertificate(parseCertificates);
        boolean parseCertificates = true;
        GclPtIdData idData = container.getIdData();
        GclPtIdData idData = container.getIdDataWithOutPhoto();
        String photo = container.getPhoto();
                //IBeIdContainer container = t1cClient.getBeIdContainer(reader.getId());
        container.isActivated();
        //ILuxIdContainer container = t1cClient.getLuxIdContainer(reader.getId(), "123456");
        GclLuxIdBiometric biometric = container.getBiometric();
        GclLuxIdPicture picture = container.getPicture();
        T1cCertificate rootCertificate = container.getRootCertificate(true);
        List<T1cCertificate> rootCertificates = container.getRootCertificates(true);
        T1cCertificate authenticationCertificate = container.getAuthenticationCertificate(parseCertificates);
        boolean parseCertificates = true;
        T1cCertificate signingCertificate = container.getSigningCertificate(parseCertificates);
        List<String> filters = Arrays.asList("authentication-certificate","signing-certificate");
        boolean parseCertificates = true;
        PtIdAllData data = (PtIdAllData) container.getAllData(filters, true);
        List<String> filters = Arrays.asList("root-certificate","authentication-certificate");
        boolean parseCertificates = true;
        PtIdAllCertificates certificates = (PtIdAllCertificates) container.getAllCertificates(filters, true);
        List<String> filters = Arrays.asList("root_certificate","root_non_repudiation_certificate","non_repudiaton_certificate");
        boolean parseCertificates = false;
        PtIdAllData data = (PtIdAllData) container.getAllData(filters, parseCertificates);

        *//*GclBeIdRn rnData = container.getRnData();
        GclBeIdAddress address = container.getAddress();
        String base64Picture = container.getPicture();
        T1cCertificate citizenCertificate = container.getCitizenCertificate(true);
        T1cCertificate nonRepudiationCertificate = container.getNonRepudiationCertificate(true);
        T1cCertificate rrnCertificate = container.getRrnCertificate(true);
        BeIdAllData allData = (BeIdAllData) container.getAllData();
        List<String> filters = Arrays.asList("root-certificate", "citizen-certificate", "non-repudiation-certificate");
        boolean parseCertificates = true;
        BeIdAllCertificates certificates = (BeIdAllCertificates) container.getAllCertificates(filters, parseCertificates);*//*

        String signedData = container.sign(new GclAuthenticateOrSignData()
                .withAlgorithmReference(DigestAlgorithm.SHA1.getStringValue())
                .withData("I2e+u/sgy7fYgh+DWA0p2jzXQ7E=")
                .withPin("1324"));

        String signedData = container.sign(new GclAuthenticateOrSignData()
                .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                .withData("OTY4ODM2ODg3ODg3YWViYzdlZDBiMDgwMjQxZGQ5N2M4N2ZlMWRhZQ==")
                .withPin("1324"));


        //PinVerificationResponse pinVerified = container.verifyPin("8214");
        //System.out.println(new Gson().toJson(pinVerified));
        PinVerificationResponse pinVerified = container.verifyPin("1234");

        String authenticatedData = container.authenticate(new GclAuthenticateOrSignData()
                .withPin("1324")
                .withData(hashtoAuthenticate.getHash())
                .withAlgorithmReference(DigestAlgorithm.SHA1.getStringValue()));
        *//*
        OcvChallengeVerificationResponse response = ocvClient.verifyChallenge(new OcvChallengeVerificationRequest()
                .withBase64Certificate(container.getAuthenticationCertificate(false).getBase64())
                .withBase64Signature(authenticatedData)
                .withHash(hashtoAuthenticate.getHash())
                .withDigestAlgorithm(hashtoAuthenticate.getDigestAlgorithm()));*//*


        *//*List<GclReader> readers = t1cClient.getSignCapableReaders();
        GclReader reader = readers.get(0);
        GclAuthenticateOrSignData payload = new GclAuthenticateOrSignData()
                .withAlgorithmReference("sha256")
                .withData("E1uHACbPvhLew0gGmBH83lvtKIAKxU2/RezfBOsT6Vs=")
                .withPin("1234");
        String signedData = t1cClient.sign(reader.getId(), payload);*//*



        *//*List<GclContainer> containers = t1cClient.getCore().getContainers();
        PlatformInfo info = t1cClient.getCore().getPlatformInfo();
        GclContainer container = t1cClient.getCore().getContainers()
        boolean consented = t1cClient.getCore().getConsent("Title for the popup dialog", "code word to be shown in the dialog", 1);
        //List<GclReader> readers = t1cClient.getCore().getReadersWithoutInsertedCard();
        List<GclReader> readers = t1cClient.getCore().getReaders();
        GclReader reader = t1cClient.getCore().getReader(readers.get(0).getId());
        GclStatus status = t1cClient.getCore().getInfo();*/

    }

}