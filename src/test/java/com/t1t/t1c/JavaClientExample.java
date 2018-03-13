package com.t1t.t1c;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.remoteloading.GclRemoteLoadingApdu;
import com.t1t.t1c.containers.remoteloading.RemoteLoadingContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.emv.GclEmvApplication;
import com.t1t.t1c.containers.smartcards.emv.GclEmvPublicKeyCertificate;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetSlot;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.GclAgent;
import com.t1t.t1c.core.GclConsent;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.ocv.OcvChallengeVerificationRequest;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class JavaClientExample {
    /*Uris*/
    private static final String OCV_URI = "https://accapim.t1t.be/trust1team/ocv-api/v1";
    private static final String DS_URI = "https://accapim.t1t.be/trust1team/gclds/v1";
    private static final String URI_T1C_GCL = "https://localhost:10443/v1/";
    /*Keys*/
    private static String API_KEY = "44865b13-f94b-45e6-b1cf-2f12d4bd547d";
    private static T1cClient client;
    private static LibConfig conf;

    public static void main(String[] args) {

        /*Config*/
        conf = new LibConfig();
        conf.setEnvironment(Environment.DEV);
        conf.setDsUri(DS_URI);
        conf.setOcvUri(OCV_URI);
        conf.setGclClientUri(URI_T1C_GCL);
        conf.setApiKey(API_KEY);
        conf.setHardwarePinPadForced(false);
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);

        /*Instantiate client*/
        client = new T1cClient(conf);
        showMenu();
    }

    private static void showMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("===============================================");
        System.out.println("1. Get generic container");
        System.out.println("2. Get reader specific container");
        System.out.println("3. Select Citrix agent");
        System.out.println("4. Grant consent");
        System.out.println("5. Exit");
        System.out.println("===============================================");
        System.out.print("Please make a choice: ");
        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                // Poll reader for insterted cards and get first available
                executeGenericContainerFunctionality(client.getCore().pollCardInserted());
                break;
            case "2":
                // Poll reader for insterted cards and get first available
                executeReaderSpecificContainerFunctionality(client.getCore().pollCardInserted());
                break;
            case "3":
                executeCitrixFunctionality();
                break;
            case "4":
                grantConsent();
                break;
            case "5":
                // Do nothing
                break;
            default:
                System.out.println("Invalid choice");
                showMenu();
        }
    }

    private static void grantConsent() {
        System.out.println("Consent granted: " + client.getCore().getConsent("Consent required", "SWORDFISH", 1, GclConsent.AlertLevel.ERROR, GclConsent.AlertPosition.CENTER, GclConsent.Type.READER, 45));
        showMenu();
    }

    private static void executeCitrixFunctionality() {
        List<GclAgent> agents = client.getCore().getAgents();
        Scanner scan = new Scanner(System.in);
        System.out.println("==================== Available agents (username) ====================");
        int i;
        for (i = 0; i < agents.size(); i++) {
            System.out.println(i + ". Select agent with username: \"" + agents.get(i).getUsername() + "\"");
        }
        System.out.println(i + ". Back");
        System.out.println("=====================================================================");
        System.out.print("Please make a choice: ");
        String input = scan.nextLine();
        try {
            Integer choice = Integer.valueOf(input);
            if (choice >= 0 && choice < agents.size()) {
                GclAgent chosenAgent = agents.get(choice);
                conf.setCitrix(true);
                conf.setAgentPort(chosenAgent.getPort());
                conf.setConsentRequired(true);
                showMenu();
            } else if (choice != i) {
                System.out.println("Invalid choice");
                executeCitrixFunctionality();
            } else {
                showMenu();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid choice");
            executeCitrixFunctionality();
        }
    }

    private static void executeGenericContainerFunctionality(GclReader reader) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Provide PIN (optional, press enter to skip): ");
            String pin = scan.nextLine();
            IGenericContainer container = client.getGenericContainer(reader, pin);
            // This returns a marker interface, the return value still needs to be cast to the correct class
            System.out.println("Container all data: " + container.getAllData());
            System.out.println("Container Certificates: " + container.getAllCertificates());

            System.out.println("Generic data dump: " + container.dumpData());

            System.out.println("Authentication chain: " + container.getAuthenticationCertificateChain());
            System.out.println("Signing chain: " + container.getSigningCertificateChain());

            if (StringUtils.isNotBlank(pin)) {
                try {
                    System.out.println("PIN verified: " + container.verifyPin(pin));

                    // Sign data
                    System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

                    // Authenticate data
                    System.out.println("Signed challenge: " + container.authenticate("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));
                } catch (VerifyPinException ex) {
                    System.out.println("PIN verification failed: " + ex.getMessage());
                }
            }
        } catch (NoConsentException ex) {
            System.out.println("Consent required: Grant consent and try again");
        }
    }


    private static void executeReaderSpecificContainerFunctionality(GclReader reader) {
        try {
            ContainerType type = ContainerUtil.determineContainer(reader.getCard());

            switch (type) {
                case AVENTRA:
                    aventraUseCases(reader);
                    break;
                case BEID:
                    remoteLoadingUseCases(reader);
                    beIdUseCases(reader);
                    break;
                case DNIE:
                    dnieUseCases(reader);
                    break;
                case EMV:
                    emvUseCases(reader);
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
                    mobibUseCases(reader);
                    break;
                case OBERTHUR:
                    oberthurUseCases(reader);
                    break;
                case OCRA:
                    ocraUseCases(reader);
                    break;
                case PIV:
                    pivUseCases(reader);
                    break;
                case PT:
                    ptIdUseCases(reader);
                    break;
                case SAFENET:
                    safeNetUseCases(reader);
                    break;
            }
        } catch (NoConsentException ex) {
            System.out.println("Consent required: Grant consent and try again");
        }
    }

    private static void safeNetUseCases(GclReader reader) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        SafeNetContainer container = client.getSafeNetContainer(reader);

        System.out.println("Container data dump: " + container.getAllData().toString());

        System.out.println("SafeNet info: " + container.getSafeNetInfo().toString());

        List<GclSafeNetSlot> slots = container.getSafeNetSlots();
        try {
            for (GclSafeNetSlot slot : slots) {
                System.out.println("SafeNet slot: " + slot.toString());
                if (StringUtils.isNotEmpty(pin)) {
                    System.out.println("Slot certificates: " + container.getSafeNetCertificates(slot.getSlotId(), pin));
                }
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

    private static void pivUseCases(GclReader reader) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        if (StringUtils.isNotEmpty(pin)) {
            PivContainer container = client.getPivContainer(reader, pin);
            try {

                System.out.println("PIN verified: " + container.verifyPin(pin));
                //TODO - Container currently only supports verify PIN, re-enable methods below once implemented in container
                /*System.out.println("Card data dump: " + container.getAllData().toString());
                System.out.println("Card certificates dump: " + container.getAllCertificates().toString());
                System.out.println("Authentication certificate: " + container.getAuthenticationCertificate().getBase64());
                System.out.println("Authentication algorithm references: " + container.getAllAlgoRefsForAuthentication().toString());
                System.out.println("Signing certificate: " + container.getSigningCertificate().getBase64());
                System.out.println("Signing algorithm references: " + container.getAllAlgoRefsForSigning().toString());

                System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA1, pin));

                // Authenticate data
                String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
                System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                        .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                        .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                        .withHash(challenge)
                        .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());*/

            } catch (VerifyPinException ex) {
                System.out.println("PIN verification failed: " + ex.getMessage());
            }
        }
    }

    private static void oberthurUseCases(GclReader reader) {
        OberthurContainer container = client.getOberthurContainer(reader);

        System.out.println("Card data dump: " + container.getAllData().toString());

        System.out.println("Card certificates dump: " + container.getAllCertificates().toString());

        System.out.println("Root certificate: " + container.getRootCertificate().getBase64());

        System.out.println("Authentication certificate: " + container.getAuthenticationCertificate().getBase64());

        System.out.println("Signing certificate: " + container.getSigningCertificate().getBase64());

        System.out.println("Issuer certificate: " + container.getIssuerCertificate().getBase64());

        System.out.println("Encryption certificate: " + container.getEncryptionCertificate().getBase64());

        List<DigestAlgorithm> algoRefsForSigning = container.getAllAlgoRefsForSigning();
        System.out.println("Algorithm references available for signing: " + algoRefsForSigning.toString());

        List<DigestAlgorithm> algoRefsForAuthentication = container.getAllAlgoRefsForSigning();
        System.out.println("Algorithm references available for authentication: " + algoRefsForAuthentication.toString());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        if (StringUtils.isNotEmpty(pin)) {
            try {
                boolean pinVerified = container.verifyPin(pin);
                System.out.println("PIN verified: " + pinVerified);

                // Sign data
                System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

                // Authenticate data
                String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
                System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                        .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                        .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                        .withHash(challenge)
                        .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());

            } catch (VerifyPinException ex) {
                System.out.println("PIN verification failed: " + ex.getMessage());
            }
        }
    }

    private static void aventraUseCases(GclReader reader) {
        AventraContainer container = client.getAventraContainer(reader);

        System.out.println("Card data dump: " + container.getAllData().toString());

        System.out.println("Card certificates dump: " + container.getAllCertificates().toString());

        System.out.println("Root certificate: " + container.getRootCertificate().getBase64());

        System.out.println("Authentication certificate: " + container.getAuthenticationCertificate().getBase64());

        System.out.println("Signing certificate: " + container.getSigningCertificate().getBase64());

        System.out.println("Issuer certificate: " + container.getIssuerCertificate().getBase64());

        System.out.println("Encryption certificate: " + container.getEncryptionCertificate().getBase64());

        System.out.println("Key references: " + container.getAllKeyRefs());

        List<DigestAlgorithm> algoRefsForSigning = container.getAllAlgoRefsForSigning();
        System.out.println("Algorithm references available for signing: " + algoRefsForSigning.toString());

        List<DigestAlgorithm> algoRefsForAuthentication = container.getAllAlgoRefsForSigning();
        System.out.println("Algorithm references available for authentication: " + algoRefsForAuthentication.toString());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        if (StringUtils.isNotEmpty(pin)) {
            try {
                boolean pinVerified = container.verifyPin(pin);
                System.out.println("PIN verified: " + pinVerified);

                // Sign data
                System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

                // Authenticate data
                String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
                System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                        .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                        .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                        .withHash(challenge)
                        .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());

            } catch (VerifyPinException ex) {
                System.out.println("PIN verification failed: " + ex.getMessage());
            }
        }

        Scanner resetPin = new Scanner(System.in);
        System.out.print("Reset PIN? (Y/n): ");
        String doReset = resetPin.nextLine();
        switch (doReset) {
            case "Y":
                try {
                    Scanner pukInput = new Scanner(System.in);
                    System.out.print("PUK: ");
                    String puk = pukInput.nextLine();
                    Scanner newPinInput = new Scanner(System.in);
                    System.out.print("PUK: ");
                    String newPin = pukInput.nextLine();
                    Scanner keyRefInput = new Scanner(System.in);
                    System.out.print("PUK: ");
                    String keyRef = pukInput.nextLine();
                    System.out.println("PIN reset: " + container.resetPin(puk, newPin, keyRef));
                } catch (VerifyPinException ex) {
                    System.out.println("Invalid PUK");
                }
                break;
            case "n":
            default:
                break;
        }
    }

    private static void ocraUseCases(GclReader reader) {
        OcraContainer container = client.getOcraContainer(reader);

        System.out.println("Card data dump: " + container.getAllData().toString());

        System.out.println("Read counter: " + container.readCounter());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        if (StringUtils.isNotEmpty(pin)) {
            try {
                boolean pinVerified = container.verifyPin(pin);
                if (pinVerified) {
                    System.out.println("OTP: " + container.getChallengeOTP("kgg0MTQ4NTkzNZMA", pin));
                }
            } catch (VerifyPinException ex) {
                System.out.println("PIN verification failed: " + ex.getMessage());
            }
        }
    }

    private static void mobibUseCases(GclReader reader) {
        MobibContainer container = client.getMobibContainer(reader);

        System.out.println("Card data dump: " + container.getAllData());

        System.out.println("Cars is active: " + container.getStatus());

        System.out.println("Bas64-encoded picture: " + container.getPicture());

        System.out.println("Contracts: " + container.getContracts());

        System.out.println("Card issuing data: " + container.getCardIssuing());
    }

    private static void emvUseCases(GclReader reader) {
        EmvContainer container = client.getEmvContainer(reader);

        System.out.println("Card data dump: " + container.getAllData().toString());

        System.out.println("Card data dump (filtered): " + container.getAllData(Collections.singletonList("application-data")).toString());

        List<GclEmvApplication> apps = container.getApplications();
        for (GclEmvApplication app : apps) {
            System.out.println("Application: " + app.toString());
            GclEmvPublicKeyCertificate iccCert = container.getIccPublicKeyCertificate(app.getAid());
            if (iccCert != null) System.out.println("Application ICC public key certificate: " + iccCert.toString());
            GclEmvPublicKeyCertificate issCert = container.getIssuerPublicKeyCertificate(app.getAid());
            if (issCert != null) System.out.println("Application Issuer public key certificate: " + app.toString());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        if (StringUtils.isNotEmpty(pin)) {
            System.out.println("PIN verified: " + container.verifyPin(pin));
        }
    }

    private static void dnieUseCases(GclReader reader) {
        DnieContainer container = client.getDnieContainer(reader);

        System.out.println("Card data dump: " + container.getAllData().toString());

        System.out.println("Card certificates dump: " + container.getAllCertificates().toString());

        System.out.println("Base64-encoded authentication certificate: " + container.getAuthenticationCertificate().getBase64());

        System.out.println("Base64-encoded intermediate certificate: " + container.getIntermediateCertificate().getBase64());

        System.out.println("Base64-encoded signing certificate: " + container.getSigningCertificate().getBase64());

        System.out.println("DNIE info: " + container.getInfo().toString());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pin = scanner.nextLine();

        try {
            Boolean pinVerified = container.verifyPin(pin);
            if (pinVerified) {
                // Sign data
                System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

                // Authenticate data
                String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
                System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                        .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                        .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                        .withHash(challenge)
                        .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

    private static void ptIdUseCases(GclReader reader) {
        PtEIdContainer container = client.getPtIdContainer(reader);

        System.out.println("Card data dump: " + container.getAllData().toString());

        System.out.println("Card certificates dump: " + container.getAllCertificates().toString());

        System.out.println("Base64-encoded authentication certificate: " + container.getAuthenticationCertificate().getBase64());

        System.out.println("Base64-encoded root authentication certificate: " + container.getRootAuthenticationCertificate().getBase64());

        System.out.println("Base64-encoded non-repudiation certificate: " + container.getNonRepudiationCertificate().getBase64());

        System.out.println("Base64-encoded root non-repudiation certificate: " + container.getRootNonRepudiationCertificate().getBase64());

        System.out.println("ID data: " + container.getPtIdData().toString());

        Scanner scan = new Scanner(System.in);
        System.out.print("Please provide Sign PIN: ");
        String signPin = scan.nextLine();

        boolean pinVerified = container.verifyPin(signPin);

        // Without hardware PinPad
        System.out.println("PIN verified: " + pinVerified);

        if (pinVerified) {

            // Sign data
            System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, signPin));
        }

        scan = new Scanner(System.in);
        System.out.print("Please provide Authentication PIN: ");
        String authenticatePin = scan.nextLine();

        if (StringUtils.isNotBlank(authenticatePin)) {

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, authenticatePin))).getResult());

        }

        scan = new Scanner(System.in);
        System.out.print("Please provide Address PIN: ");
        String addressPin = scan.nextLine();

        if (StringUtils.isNotBlank(addressPin)) {
            System.out.println("Address data: " + container.getAddress(addressPin));
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
            System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());

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
            System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());

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
            System.out.println("Base64 authentication certificate: " + container.getAuthenticationCertificate().getBase64());
            System.out.println("Base64 non-repudiation certificate: " + container.getNonRepudiationCertificate().getBase64());
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
            System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

            // Authenticate data
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA1).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA1.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA1, pin))).getResult());

            StringBuilder sb = new StringBuilder();
            Iterator<T1cCertificate> it = container.getRootCertificates().iterator();
            while (it.hasNext()) {
                sb.append(it.next().getBase64());
                if (it.hasNext()) sb.append(", ");
            }
            System.out.println("Base64 root certificates: " + sb.toString());
            System.out.println("Base64 authentication certificate: " + container.getAuthenticationCertificate().getBase64());
            System.out.println("Base64 non-repudiation certificate: " + container.getSigningCertificate().getBase64());
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