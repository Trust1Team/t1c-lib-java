package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.remoteloading.*;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetRequest;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.ocv.OcvChallengeVerificationRequest;
import com.t1t.t1c.utils.ContainerUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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

        Scanner scan = new Scanner(System.in);
        System.out.println("===============================================");
        System.out.println("1. Get generic container");
        System.out.println("2. Get reader specific container");
        System.out.println("===============================================");
        System.out.print("Please make a choice: ");
        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                executeGenericContainerFunctionality(reader);
                break;
            case "2":
                executeReaderSpecificContainerFunctionality(reader);
                break;
            default:
                throw new IllegalArgumentException("Invalid choice");
        }
    }

    private static void executeGenericContainerFunctionality(GclReader reader) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Provide PIN (optional, press enter to skip): ");
        String pin = scan.nextLine();
        IGenericContainer container = client.getGenericContainer(reader, pin);
        System.out.println("Data dump: " + container.getAllData());
        System.out.println("Certificate dump: " + container.getAllCertificates());
        boolean pinVerified = container.verifyPin(pin);
        System.out.println("PIN verified: " + pinVerified);
        if (pinVerified) {
            // Sign data
            System.out.println("Signed hash: " + container.sign(new GclAuthenticateOrSignData()
                    .withData("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=")
                    .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                    .withPin(pin)));

            // Authenticate data
            System.out.println("Signed challenge: " + container.authenticate(new GclAuthenticateOrSignData()
                            .withData("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=")
                            .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                            .withPin(pin)));
        }
    }

    private static void executeReaderSpecificContainerFunctionality(GclReader reader) {
        ContainerType type = ContainerUtil.determineContainer(reader.getCard());

        switch (type) {
            case AVENTRA:
                break;
            case BEID:
                remoteLoadingUseCases(reader);
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
                luxTrustUseCases(reader);
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
            case SAFENET:
                break;
        }
    }

    private static void beIdUseCases(GclReader reader) {

        BeIdContainer container = client.getBeIdContainer(reader);

        // With hardware PinPad/Without Java lib PIN input
        //System.out.println("PIN verified: " + container.verifyPin());

        Scanner scan = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scan.nextLine();

        boolean pinVerified = container.verifyPin(pin);

        // Without hardware PinPad
        System.out.println("PIN verified: " + pinVerified);

        if (pinVerified) {

            // Sign data
            System.out.println("Signed hash: " + container.sign(new GclAuthenticateOrSignData()
                    .withData("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=")
                    .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                    .withPin(pin)));

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(new GclAuthenticateOrSignData()
                            .withData(challenge)
                            .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                            .withPin(pin)))).getResult());

            // Rn data
            System.out.println("RN Data: " + container.getRnData().toString());
            // Address
            System.out.println("Address: " + container.getBeIdAddress().toString());
            // Picture
            System.out.println("Base64 picture: " + container.getBeIdPicture());
            // Root certificate
            System.out.println("Base64 root certificate: " + container.getRootCertificate().getBase64());
            // Citizen certificate
            System.out.println("Base64 citizen certificate: " + container.getCitizenCertificate().getBase64());
            // Non-repudiation certificate
            System.out.println("Base64 non-repudiation certificate: " + container.getNonRepudiationCertificate().getBase64());
            // Authentication certificate
            System.out.println("Base64 authentication certificate: " + container.getAuthenticationCertificate().getBase64());
            // RRN certificate
            System.out.println("Base64 RRN certificate: " + container.getRrnCertificate().getBase64());

            // Card data dump
            System.out.println("Card data dump: " + container.getAllData().toString());
            // Card certificate dump
            System.out.println("Card certificate dump: " + container.getAllCertificates());
        }
    }

    private static void luxIdUseCases(GclReader reader) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scan.nextLine();
        LuxIdContainer container = client.getLuxIdContainer(reader, pin);

        boolean pinVerified = container.verifyPin(pin);

        // Without hardware PinPad
        System.out.println("PIN verified: " + pinVerified);

        if (pinVerified) {

            // Sign data
            System.out.println("Signed hash: " + container.sign(new GclAuthenticateOrSignData()
                    .withData("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=")
                    .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                    .withPin(pin)));

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(new GclAuthenticateOrSignData()
                            .withData(challenge)
                            .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                            .withPin(pin)))).getResult());

            System.out.println("Biometric data: " + container.getBiometric());

            System.out.println("Picture data: " + container.getPicture());
            System.out.println("Signature image data: " + container.getSignatureImage());
            StringBuilder sb = new StringBuilder();
            Iterator<T1cCertificate> it = container.getRootCertificates().iterator();
            while (it.hasNext()) {
                sb.append(it.next().getBase64());
                if (it.hasNext()) sb.append(", ");
            }
            System.out.println("Base64 root certificates: " + sb.toString());
            System.out.println("Base64 authentication certificate: " + container.getAuthenticationCertificate());
            System.out.println("Base64 non-repudiation certificate: " + container.getNonRepudiationCertificate());
            System.out.println("Card data dump: " + container.getAllData());
            System.out.println("Card certificate dump: " + container.getAllCertificates());
        }
    }

    private static void luxTrustUseCases(GclReader reader) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scan.nextLine();
        LuxTrustContainer container = client.getLuxTrustContainer(reader);

        boolean pinVerified = container.verifyPin(pin);

        // Without hardware PinPad
        System.out.println("PIN verified: " + pinVerified);

        if (pinVerified) {

            // Sign data
            System.out.println("Signed hash: " + container.sign(new GclAuthenticateOrSignData()
                    .withData("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=")
                    .withAlgorithmReference(DigestAlgorithm.SHA256.getStringValue())
                    .withPin(pin)));

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA1).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA1.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(new GclAuthenticateOrSignData()
                            .withData(challenge)
                            .withAlgorithmReference(DigestAlgorithm.SHA1.getStringValue())
                            .withPin(pin)))).getResult());

            StringBuilder sb = new StringBuilder();
            Iterator<T1cCertificate> it = container.getRootCertificates().iterator();
            while (it.hasNext()) {
                sb.append(it.next().getBase64());
                if (it.hasNext()) sb.append(", ");
            }
            System.out.println("Base64 root certificates: " + sb.toString());
            System.out.println("Base64 authentication certificate: " + container.getAuthenticationCertificate());
            System.out.println("Base64 non-repudiation certificate: " + container.getSigningCertificate());
            System.out.println("Card data dump: " + container.getAllData());
            System.out.println("Card certificate dump: " + container.getAllCertificates());
        }
    }

    private static void remoteLoadingUseCases(GclReader reader) {
        RemoteLoadingContainer container = client.getRemoteLoadingContainer(reader);

        System.out.println("ATR: " + container.getAtr());

        System.out.println("Executed command: " + container.executeCommand("00A4020C023F00").toString());

        System.out.println("Executed commands: " + container.executeCommands(
                Arrays.asList(
                        "00A4020C023F00",
                        "00A4020C02DF01",
                        "00A4020C024031",
                        "00B00000B3")).toString()
        );

        System.out.println("Executed APDU: " + container.executeApduCall(
                new GclRemoteLoadingApdu()
                    .withCla("F1")
                    .withIns("95")
                    .withP1("F7")
                    .withP2("E4")
                    .withData("FE0000040001300000")).toString()
        );

        System.out.println("Executed APDUs: " + container.executeApduCalls(
                Arrays.asList(
                    new GclRemoteLoadingApdu()
                            .withCla("F1")
                            .withIns("95")
                            .withP1("F7")
                            .withP2("E4")
                            .withData("FE0000040001300000"),
                    new GclRemoteLoadingApdu()
                            .withCla("F1")
                            .withIns("95")
                            .withP1("F7")
                            .withP2("E4")
                            .withData("FE0000040001300000")
                )).toString()
        );

        System.out.println("Available CCID Features: " + container.getCcidFeatures().toString());

        System.out.println("Execute CCID: " + container.executeCcid("GET_TLV_PROPERTIES", "1E1E8947040C0402010904000000000D000000002000010820FFFFFFFFFFFFFF").toString());

        System.out.println("Card present: " + container.isCardPresent());

        String sessionId = container.openSession(30);
        System.out.println("Session ID: " + sessionId);
        System.out.println("Close session: " + container.closeSession(sessionId));
    }

}