package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.functional.readerapi.GclReaderApiApdu;
import com.t1t.t1c.containers.functional.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdAllData;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAddress;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdRn;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.emv.GclEmvApplication;
import com.t1t.t1c.containers.smartcards.emv.GclEmvPublicKeyCertificate;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.GclPkcs11Slot;
import com.t1t.t1c.containers.smartcards.pkcs11.Pkcs11Container;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.ocv.OcvChallengeVerificationRequest;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;
import java.util.*;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class JavaClientExample {

    private static IT1cClient client;
    private static LibConfig conf;

    public static void main(String[] args) {
        try {
            client = new T1cClient(Paths.get("/usr/local/t1c/application.conf"));
            conf = client.getConnectionFactory().getConfig();
            showMenu();
        } catch (NoConsentException ex) {
            System.out.println("Consent required: Grant consent and try again");
            showMenu();
        }
    }

    private static void showMenu() {
        Scanner scan = new Scanner(System.in);
        /*Instantiate client*/

        // Copy the example configuration file to a folder of your choosing and adjust it below

        System.out.println("DownloadLink: " + client.getDownloadLink());
        System.out.println("===============================================");
        System.out.println("1. Get generic container");
        System.out.println("2. Get reader specific container");
        System.out.println("3. Grant consent");
        System.out.println("4. Resolve shared environment agent & reinitialize");
        System.out.println("5. Get Reader API container");
        System.out.println("6. Exit");
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
                grantConsent();
                break;
            case "4":
                executeCitrixFunctionality();
                break;
            case "5":
                readerApiUseCases(client.getCore().pollCardInserted());
                break;
            case "6":
                //Do nothing
                break;
            default:
                System.out.println("Invalid choice");
                showMenu();
                break;
        }
    }

    private static void grantConsent() {
        try {
            System.out.println("Consent granted: " + client.getCore().getConsent("Consent required", "SWORDFISH", 1, GclAlertLevel.ERROR, GclAlertPosition.CENTER, GclConsentType.READER, 10));
        } catch (UnsupportedOperationException ex) {
            System.out.println(ex.getMessage());
        }
        showMenu();
    }

    private static void executeCitrixFunctionality() {
        Boolean citrix = client.getCore().getInfo().getCitrix();
        if (citrix == null || !citrix) {
            System.out.println("No agents available: GCL not configured for Citrix");
            showMenu();
        } else {
            List<GclAgent> agents = client.getCore().resolveAgent();
            if (CollectionUtils.isEmpty(agents)) {
                System.out.println("No agents found matching the current user.");
                showMenu();
            } else {
                try {
                    GclAgent chosenAgent = agents.get(0);
                    conf.setAgentPort(chosenAgent.getPort().intValue());
                    client = new T1cClient(conf);
                    showMenu();
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid choice");
                    executeCitrixFunctionality();
                }
            }
        }
    }

    private static void executeGenericContainerFunctionality(GclReader reader) {

        Scanner scan = new Scanner(System.in);
        System.out.print("Provide PIN (optional, press enter to skip): ");
        String pin = scan.nextLine();
        IGenericContainer container = client.getGenericContainer(reader, new GclPace().withPin(pin));
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
    }


    private static void executeReaderSpecificContainerFunctionality(GclReader reader) {
        ContainerType type = ContainerUtil.determineContainer(reader.getCard());

        switch (type) {
            case AVENTRA:
                aventraUseCases(reader);
                break;
            case BEID:
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
            case PKCS11:
                pkcs11UseCases(reader);
                break;
            default:
                System.out.println("No matching container type found: " + type);
                break;
        }
    }

    private static void pkcs11UseCases(GclReader reader) {
        Pkcs11Container container = client.getPkcs11Container(reader);

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide Sign PIN: ");
            pin = scan.nextLine();
        }

        System.out.println("Container data dump: " + container.getAllData().toString());

        System.out.println("Pkcs11 info: " + container.getPkcs11Info().toString());

        List<GclPkcs11Slot> slots = container.getPkcs11Slots();
        try {
            for (GclPkcs11Slot slot : slots) {
                System.out.println("Pkcs11 slot: " + slot.toString());
                System.out.println("Slot certificates: " + container.getPkcs11Certificates(slot.getSlotId(), pin));
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

    private static void pivUseCases(GclReader reader) {


        Scanner paceScanner = new Scanner(System.in);
        System.out.print("Please provide PIN: ");
        String pacePin = paceScanner.nextLine();

        if (StringUtils.isNotEmpty(pacePin)) {
            PivContainer container = client.getPivContainer(reader, pacePin);

            Scanner scan = new Scanner(System.in);
            String pin = null;
            if (!reader.getPinpad()) {
                System.out.print("Please provide PIN: ");
                pin = scan.nextLine();
            }

            try {
                if (container.verifyPin(pin)) {
                    System.out.println("PIN verified");
                    //TODO - Container currently only supports verify PIN, re-enable methods below once implemented in container

                    System.out.println("Card data dump: " + container.getAllData().toString());
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
                            .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());
                }
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

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        executeGenericCardUseCases(container, pin);
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

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        executeGenericCardUseCases(container, pin);

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
                    System.out.print("New PIN: ");
                    String newPin = newPinInput.nextLine();
                    Scanner keyRefInput = new Scanner(System.in);
                    System.out.print("Key Ref: ");
                    String keyRef = keyRefInput.nextLine();
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

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        try {
            if (container.verifyPin(pin)) {
                System.out.println("PIN verified");
                System.out.println("OTP: " + container.getChallengeOTP("kgg0MTQ4NTkzNZMA", pin));
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
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

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        try {
            if (container.verifyPin(pin)) {
                System.out.println("PIN verified");
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
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
        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        executeGenericCardUseCases(container, pin);
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
        String signPin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide Sign PIN: ");
            signPin = scan.nextLine();
        }

        try {
            if (container.verifyPin(signPin)) {
                System.out.println("PIN verified");
                System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, signPin));
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }

        scan = new Scanner(System.in);
        String authenticatePin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide Sign PIN: ");
            authenticatePin = scan.nextLine();
        }

        // Authenticate data
        try {
            String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
            System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                    .withBase64Certificate(container.getAuthenticationCertificate().getBase64())
                    .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                    .withHash(challenge)
                    .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, authenticatePin))).getResult());
        } catch (VerifyPinException ex) {
            System.out.println("PIN Error: " + ex.getMessage());
        }

        scan = new Scanner(System.in);
        String addressPin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide Sign PIN: ");
            addressPin = scan.nextLine();
        }

        try {
            System.out.println("Address data: " + container.getAddress(addressPin));
        } catch (VerifyPinException ex) {
            System.out.println("Address data: " + container.getAddress(addressPin));
        }
    }

    private static void beIdUseCases(GclReader reader) {

        BeIdContainer container = client.getBeIdContainer(reader);

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        try {
            if (container.verifyPin(pin)) {
                System.out.println("PIN verified");

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
                GclBeIdRn rn = container.getRnData();
                System.out.println("RN Data: " + rn.toString());
                // Address
                GclBeIdAddress ad = container.getBeIdAddress();
                System.out.println("Address: " + ad.toString());
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
                BeIdAllData bData = container.getAllData(false);
                System.out.println("Card data dump: " + bData.toString());
                // Card certificate dump
                System.out.println("Card certificate dump: " + container.getAllCertificates());

                // Validate RN data
                System.out.println("RN Data valid?: " + client.getOcvClient().validateSignature(rn.getRawData(), rn.getSignature(), DigestAlgorithm.SHA1, bData.getRrnCertificate().getBase64()).getResult());
                // Validate Address
                System.out.println("Address Data valid?: " + client.getOcvClient().validateSignature(Base64.encodeBase64String(ArrayUtils.addAll(Base64.decodeBase64(ad.getRawData()), Base64.decodeBase64(ad.getSignature()))), ad.getSignature(), DigestAlgorithm.SHA1, bData.getRrnCertificate().getBase64()).getResult());
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

    private static void luxIdUseCases(GclReader reader) {
        Scanner paceScan = new Scanner(System.in);
        System.out.print("Please provide PACE PIN: ");
        String pacePin = paceScan.nextLine();
        LuxIdContainer container = client.getLuxIdContainer(reader, new GclPace().withPin(pacePin));

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        try {
            if (container.verifyPin(pin)) {
                System.out.println("PIN verified");
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
                System.out.println("Card data dump: " + container.getAllData().toString());
                System.out.println("Card certificate dump: " + container.getAllCertificates());
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

    private static void luxTrustUseCases(GclReader reader) {
        LuxTrustContainer container = client.getLuxTrustContainer(reader);
        System.out.println("Card is activated: " + container.isActivated());

        Scanner scan = new Scanner(System.in);
        String pin = null;
        if (!reader.getPinpad()) {
            System.out.print("Please provide PIN: ");
            pin = scan.nextLine();
        }

        try {
            if (container.verifyPin(pin)) {
                System.out.println("PIN verified");
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
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

    private static void readerApiUseCases(GclReader reader) {
        ReaderApiContainer container = client.getReaderApiContainer(reader);

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
                new GclReaderApiApdu()
                        .withCla("F1")
                        .withIns("95")
                        .withP1("F7")
                        .withP2("E4")
                        .withData("FE0000040001300000")).toString()
        );

        System.out.println("Executed APDUs: " + container.executeApduCalls(
                Arrays.asList(
                        new GclReaderApiApdu()
                                .withCla("F1")
                                .withIns("95")
                                .withP1("F7")
                                .withP2("E4")
                                .withData("FE0000040001300000"),
                        new GclReaderApiApdu()
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

    public static void executeGenericCardUseCases(GenericContainer container, String pin) {
        try {
            if (container.verifyPin(pin)) {
                System.out.println("PIN verified");
                // Sign data
                System.out.println("Signed hash: " + container.sign("mVEpdyxAT1FWgVnLsKcmqiWvsSuKP6uGAGT528AEQaQ=", DigestAlgorithm.SHA256, pin));

                ContainerData data = container.dumpData();

                String authCert = data.getAuthenticationCertificateChain().get(0).getBase64();

                // Authenticate data
                String challenge = client.getOcvClient().getChallenge(DigestAlgorithm.SHA256).getHash();
                System.out.println("External challenge authenticated: " + client.getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                        .withBase64Certificate(authCert)
                        .withDigestAlgorithm(DigestAlgorithm.SHA256.getStringValue())
                        .withHash(challenge)
                        .withBase64Signature(container.authenticate(challenge, DigestAlgorithm.SHA256, pin))).getResult());
            }
        } catch (VerifyPinException ex) {
            System.out.println("PIN verification failed: " + ex.getMessage());
        }
    }

}