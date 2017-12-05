package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAddress;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAllData;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdRn;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieAllData;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieInfo;
import com.t1t.t1c.containers.smartcards.eid.lux.*;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdAllCertificates;
import com.t1t.t1c.containers.smartcards.emv.*;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibAllData;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibCardIssuing;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibContract;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraAllData;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetInfo;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetSlot;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustAllCertificates;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustAllData;
import com.t1t.t1c.core.GclCard;
import com.t1t.t1c.core.GclContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclStatus;
import com.t1t.t1c.ds.DsDevice;
import com.t1t.t1c.ds.DsDownloadPath;
import com.t1t.t1c.ds.DsToken;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.model.T1cResponse;
import okhttp3.mockwebserver.MockResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class MockResponseFactory {

    private static final Logger log = LoggerFactory.getLogger(MockResponseFactory.class);

    private static final String JSON_EXTENSION = ".json";
    private static final String RESPONSE_RESOURCE_PATH = "/responses/";

    public static final String NO_CARD_READER_ID = "57a3e2e71c48ce00";
    public static final String AVENTRA_READER_ID = "57a3e2e71c48ce01";
    public static final String BEID_READER_ID = "57a3e2e71c48ce02";
    public static final String DNIE_READER_ID = "57a3e2e71c48ce03";
    public static final String EMV_READER_ID = "57a3e2e71c48ce04";
    public static final String EST_READER_ID = "57a3e2e71c48ce05";
    public static final String LUXID_READER_ID = "57a3e2e71c48ce06";
    public static final String LUXTRUST_READER_ID = "57a3e2e71c48ce07";
    public static final String MOBIB_READER_ID = "57a3e2e71c48ce08";
    public static final String OBERTHUR_READER_ID = "57a3e2e71c48ce09";
    public static final String OCRA_READER_ID = "57a3e2e71c48ce10";
    public static final String PIV_READER_ID = "57a3e2e71c48ce11";
    public static final String PT_READER_ID = "57a3e2e71c48ce12";
    public static final String SAFENET_READER_ID = "57a3e2e71c48ce13";



    private static final Gson GSON = new Gson();

    private MockResponseFactory() {
    }

    public static T1cResponse<Object> getSuccessResponse() {
        return new T1cResponse<>()
                .withSuccess(true)
                .withData(null);
    }

    //
    // GCL Core
    //

    public static T1cResponse<String> getGclAdminCertificateResponse() {
        return getSuccessResponse(getGclAdminCertificate());
    }

    public static T1cResponse<GclStatus> getGclV1StatusResponse() {
        return getSuccessResponse(getGclV1Status());
    }

    public static T1cResponse<List<GclReader>> getGclReadersResponse(Boolean onlyCardsInserted) {
        return getSuccessResponse(getGclReaders(onlyCardsInserted));
    }

    public static T1cResponse<GclReader> getGclReaderResponse(String readerId) {
        return getSuccessResponse(getGclReader(readerId));
    }

    public static T1cResponse<List<GclContainer>> getAllContainersResponse() {
        return new T1cResponse<List<GclContainer>>().withSuccess(true).withData(getAllContainers());
    }

    public static List<GclContainer> getAllContainers() {
        List<GclContainer> containers = new ArrayList<>();
        for (ContainerType type : ContainerType.values()) {
            containers.add(new GclContainer().withId(type.getId()).withName(type.getId()).withVersion("1.0"));
        }
        return containers;
    }

    public static GclReader getGclReader(String readerId) {
        GclReader reader;
        switch (readerId) {
            case AVENTRA_READER_ID:
                reader = getReaderWithCard(ContainerType.AVENTRA, false);
                break;
            case BEID_READER_ID:
                reader = getReaderWithCard(ContainerType.BEID, false);
                break;
            case DNIE_READER_ID:
                reader = getReaderWithCard(ContainerType.DNIE, false);
                break;
            case EMV_READER_ID:
                reader = getReaderWithCard(ContainerType.EMV, false);
                break;
            case LUXID_READER_ID:
                reader = getReaderWithCard(ContainerType.LUXID, false);
                break;
            case LUXTRUST_READER_ID:
                reader = getReaderWithCard(ContainerType.LUXTRUST, false);
                break;
            case MOBIB_READER_ID:
                reader = getReaderWithCard(ContainerType.MOBIB, false);
                break;
            case OBERTHUR_READER_ID:
                reader = getReaderWithCard(ContainerType.OBERTHUR, false);
                break;
            case OCRA_READER_ID:
                reader = getReaderWithCard(ContainerType.OCRA, false);
                break;
            case PIV_READER_ID:
                reader = getReaderWithCard(ContainerType.PIV, false);
                break;
            case PT_READER_ID:
                reader = getReaderWithCard(ContainerType.PT, false);
                break;
            case NO_CARD_READER_ID:
            default:
                reader = getReaderWithCard(null, false);
                break;
        }
        return reader;
    }

    public static List<GclReader> getGclReaders(Boolean onlyCardsInserted) {
        List<GclReader> readers = new ArrayList<>();
        if (onlyCardsInserted == null) {
            readers.add(getReaderWithCard(ContainerType.BEID, false));
            readers.add(getReaderWithCard(ContainerType.AVENTRA, false));
            readers.add(getReaderWithCard(ContainerType.DNIE, false));
            readers.add(getReaderWithCard(ContainerType.EMV, false));
            readers.add(getReaderWithCard(ContainerType.LUXID, false));
            readers.add(getReaderWithCard(ContainerType.LUXTRUST, false));
            readers.add(getReaderWithCard(ContainerType.MOBIB, false));
            readers.add(getReaderWithCard(ContainerType.OBERTHUR, false));
            readers.add(getReaderWithCard(ContainerType.OCRA, false));
            readers.add(getReaderWithCard(ContainerType.PIV, false));
            readers.add(getReaderWithCard(ContainerType.PT, false));
            readers.add(getReaderWithCard(null, false));
        }
        else if (onlyCardsInserted) {
            readers.add(getReaderWithCard(ContainerType.BEID, false));
            readers.add(getReaderWithCard(ContainerType.AVENTRA, false));
            readers.add(getReaderWithCard(ContainerType.DNIE, false));
            readers.add(getReaderWithCard(ContainerType.EMV, false));
            readers.add(getReaderWithCard(ContainerType.LUXID, false));
            readers.add(getReaderWithCard(ContainerType.LUXTRUST, false));
            readers.add(getReaderWithCard(ContainerType.MOBIB, false));
            readers.add(getReaderWithCard(ContainerType.OBERTHUR, false));
            readers.add(getReaderWithCard(ContainerType.OCRA, false));
            readers.add(getReaderWithCard(ContainerType.PIV, false));
            readers.add(getReaderWithCard(ContainerType.PT, false));
        } else {
            readers.add(getReaderWithCard(null, false));
        }
        return readers;
    }

    public static GclReader getReaderWithCard(ContainerType type, boolean pinPad) {
        GclReader reader = new GclReader().withName("Bit4id miniLector").withPinpad(false);
        if (type == null) {
            reader = reader.withCard(null).withId(NO_CARD_READER_ID);
        } else {
            switch (type) {
                case AVENTRA:
                    reader = getReader(pinPad, AVENTRA_READER_ID, getAventraCard());
                    break;
                case BEID:
                    reader = getReader(pinPad, BEID_READER_ID, getBeIdCard());
                    break;
                case DNIE:
                    reader = getReader(pinPad, DNIE_READER_ID, getDnieCard());
                    break;
                case EMV:
                    reader = getReader(pinPad, EMV_READER_ID, getEmvCard());
                    break;
                case LUXID:
                    reader = getReader(pinPad, LUXID_READER_ID, getLuxIdCard());
                    break;
                case LUXTRUST:
                    reader = getReader(pinPad, LUXTRUST_READER_ID, getLuxTrustCard());
                    break;
                case MOBIB:
                    reader = getReader(pinPad, MOBIB_READER_ID, getMobibCard());
                    break;
                case OBERTHUR:
                    reader = getReader(pinPad, OBERTHUR_READER_ID, getOberthurCard());
                    break;
                case OCRA:
                    reader = getReader(pinPad, OCRA_READER_ID, getOcraCard());
                    break;
                case PIV:
                    reader = getReader(pinPad, PIV_READER_ID, getPivCard());
                    break;
                case PT:
                    reader = getReader(pinPad, PT_READER_ID, getPtIdCard());
                    break;
                default:
                    throw new UnsupportedOperationException("Not a smartcard container or not yet supported");
            }
        }
        return reader;
    }

    public static GclCard getAventraCard() {
        return new GclCard().withAtr("3BF51800008131FE454D794549449A").withDescription(Collections.singletonList("Aventra ActiveSecurity MyEID"));
    }

    public static GclCard getBeIdCard() {
        return new GclCard().withAtr("3B9813400AA503010101AD1311").withDescription(Collections.singletonList("Belgium Electronic ID card"));
    }

    public static GclCard getDnieCard() {
        return new GclCard().withAtr("3B7F380000006A444E496520024C340113039000").withDescription(Collections.singletonList("DNI electronico (Spanish electronic ID card)"));
    }

    public static GclCard getEmvCard() {
        return new GclCard().withAtr("3B67000000000000009000")
                .withDescription(Arrays.asList("Axa Bank (Belgium) Mastercard Gold / Axa Bank Belgium", "MisterCash & Proton card", "VISA Card (emitted by Bank Card Company - Belgium)"));
    }

    public static GclCard getLuxIdCard() {
        return new GclCard().withAtr("3B8F800180318065B0850300EF120FFF82900073").withDescription(Collections.singletonList("Grand Duchy of Luxembourg / Identity card with LuxTrust certificate (eID)"));
    }

    public static GclCard getLuxTrustCard() {
        return new GclCard().withAtr("3B7D94000080318065B0831100C883009000")
                .withDescription(Arrays.asList("personal identity card (ID card) of Republic of Lithuania", "LuxTrust card"));
    }

    public static GclCard getMobibCard() {
        return new GclCard().withAtr("3B6F0000805A2C23C3000302028497A4829000").withDescription(Collections.singletonList("MOBIB card for DeLijn (public transport operator for the Flemish part of Belgium)"));
    }

    public static GclCard getOberthurCard() {
        return new GclCard().withAtr("3BDD18008131FE4580F9A000000077010800079000FE").withDescription(Collections.singletonList("Oberthur Cosmo v7 IAS ECC"));
    }

    public static GclCard getOcraCard() {
        return new GclCard().withAtr("3B7F94000080318065B0850300EF120FFF829000")
                .withDescription(Arrays.asList("Gemalto Classic V4", "Juridic Person's Token (PKI)"));
    }

    public static GclCard getPivCard() {
        return new GclCard().withAtr("3B7D96000080318065B0831117E583009000").withDescription(Collections.singletonList("Gemalto IDPrime PIV Card2.0 (eID)"));
    }

    public static GclCard getPtIdCard() {
        return new GclCard().withAtr("3B9813400AA503010101AD1311").withDescription(Collections.singletonList("Portugese eID Card"));
    }

    public static GclReader getReader(boolean pinPad, String id, GclCard card) {
        return new GclReader().withName("Bit4id miniLector").withPinpad(pinPad).withId(id).withCard(card);
    }

    public static GclStatus getGclV1Status() {
        return new GclStatus()
                .withActivated(true)
                .withArch("x86_64")
                .withCitrix(false)
                .withLogLevel("info")
                .withManaged(false)
                .withOs("10.13.1")
                .withUid("B7289D3AEB22D233")
                .withVersion("1.6.0");
    }

    public static String getGclAdminCertificate() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjC4a5oOpZr7Yci7WEiLbZsOEk48TkjtvANpUkRMtwNyPVvhmaZib9qKx2JQRjg74cdpqvpCBQZ2w/7/30G1ptrB654PkDK0F3Z2AZJp0LEZoCaYQ+8ubWSbpAvM3dlUl9MeDP5O4gTuEaYatqrBGpSZwVc9xjCs/OKYKgIXXjV7tILogAWWo4MmxSfyr/c7fe1CUGN7uTuiGtR5djmk369SPGc1vUNuqxh2fC9Nsmp0mtB23jxi0D0bpi5Dn7G4Jif6DX9DiF2ktXpM9dmo93N6BOX3tbstw6I0KFyXpvjpVtAO8LYI/d7QlgNOp0fcQj5DUCH8UIY3x1nTnoPeC5QIDAQAB";
    }

    //
    // BE ID responses
    //

    public static T1cResponse<GclBeIdAllData> getGclBeIdAllDataResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclBeIdAllData data = getGclBeIdAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("address")) data.setAddress(null);
            if (!filterParams.contains("picture")) data.setPicture(null);
            if (!filterParams.contains("rn")) data.setRn(null);
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("citizen-certificate")) data.setCitizenCertificate(null);
            if (!filterParams.contains("non-repudiation-certificate")) data.setNonRepudiationCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("rrn-certificate")) data.setRrnCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclBeIdAllCertificates> getGclBeIdAllCertificatesResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclBeIdAllCertificates data = getGclBeIdAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("citizen-certificate")) data.setCitizenCertificate(null);
            if (!filterParams.contains("non-repudiation-certificate")) data.setNonRepudiationCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("rrn-certificate")) data.setRrnCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclBeIdRn> getBeIdRnResponse() {
        return getSuccessResponse(getGclBeIdRnData());
    }

    public static T1cResponse<GclBeIdAddress> getBeIdAddressResponse() {
        return getSuccessResponse(getGclBeIdAddress());
    }

    public static T1cResponse<String> getBeIdPictureResponse() {
        return getSuccessResponse(getBeIdPicture());
    }

    public static T1cResponse<String> getBeIdRootCertificateResponse() {
        return getSuccessResponse(getBeIdRootCertificate());
    }

    public static T1cResponse<String> getBeIdCitizenCertificateResponse() {
        return getSuccessResponse(getBeIdCitizenCertificate());
    }

    public static T1cResponse<String> getBeIdNonRepudiationCertificateResponse() {
        return getSuccessResponse(getBeIdNonRepudiationCertificate());
    }

    public static T1cResponse<String> getBeIdAuthenticationCertificateResponse() {
        return getSuccessResponse(getBeIdAuthenticationCertificate());
    }

    public static T1cResponse<String> getBeIdRrnCertificateResponse() {
        return getSuccessResponse(getBeIdRrnCertificate());
    }

    public static T1cResponse<Object> getBeIdVerifyPinSuccess() {
        return getSuccessResponse(null);
    }

    private static GclBeIdAllData getGclBeIdAllData() {
        return new GclBeIdAllData()
                .withAddress(getGclBeIdAddress())
                .withPicture(getBeIdPicture())
                .withRn(getGclBeIdRnData())
                .withRootCertificate(getBeIdRootCertificate())
                .withCitizenCertificate(getBeIdCitizenCertificate())
                .withNonRepudiationCertificate(getBeIdNonRepudiationCertificate())
                .withAuthenticationCertificate(getBeIdAuthenticationCertificate())
                .withRrnCertificate(getBeIdRrnCertificate());
    }

    private static GclBeIdAllCertificates getGclBeIdAllCertificates() {
        return new GclBeIdAllCertificates()
                .withRootCertificate(getBeIdRootCertificate())
                .withCitizenCertificate(getBeIdCitizenCertificate())
                .withNonRepudiationCertificate(getBeIdNonRepudiationCertificate())
                .withAuthenticationCertificate(getBeIdAuthenticationCertificate())
                .withRrnCertificate(getBeIdRrnCertificate());
    }

    public static GclBeIdAddress getGclBeIdAddress() {
        return new GclBeIdAddress()
                .withMunicipality("Gent")
                .withRawData("U3RhdGlvbnN0cmFhdCAxIAIEOTAwMAMER2VudA==")
                .withSignature("lI+0Jcu/a/Ubi6gGgNA1hCTXC2JUz81XelmpKHMolGAugCGXLMhg5CI2y80SPGokcol/UQYOrl+ZDqQIqxlFb3OzFUbsW4YRXpm5BS7ZT3DRr+n2uc7PDMkZ1BhvB7V2mYgCMZLLDyyXPoMYbM/MJ0tL/6tdIKds/DlKu3nkk6rGoi77DyIpi2OGL67v1ItbvL6eW9jzW/NcxdpULciql6lK+bAfMWs8lYqdFxADU65m/Ac9vmNHofYAVv8GQt3OiPZrL97ysisDJ6gbtX1I05P4czNv4msU2GqxYIQUqRFEoKLs//VooS75hwjbU5/QVUtvXjxusqWvUomNG65AzQ==")
                .withStreetAndNumber("Stationstraat 1")
                .withVersion(0)
                .withZipcode("9000");
    }

    public static GclBeIdRn getGclBeIdRnData() {
        return new GclBeIdRn()
                .withBirthDate("01 JAN  1980")
                .withBirthLocation("Gent")
                .withCardDeliveryMunicipality("Gent")
                .withCardNumber("111111111111")
                .withCardValidityDateBegin("01.01.2015")
                .withCardValidityDateEnd("01.01.2100")
                .withChipNumber("534C479040110001123175F912924239")
                .withDocumentType("01")
                .withFirstNames("John John")
                .withName("Doe")
                .withNationalNumber("800101111112")
                .withNationality("Belg")
                .withNobleCondition("")
                .withPictureHash("588331D8A6F6B4812B4E953982AA5616FE29C03A")
                .withRawData("MTExMTExMTExMTExAhBTTEdAEQESMXUSQjkDDQowMS4wMS4yMDE1BA0KMDEuMDEuMjEwMAUER2VudAYLODAwMTAxMTExMTIHDQpEb2VKb2huIEpvaG4NCgRCZWxnCwRHZW50DAwwMSBKQU4gIDE5ODANCgFNDg8CMDEQATARFFgx2KYrTjlWFik6")
                .withSex("M")
                .withSignature("tRdHRSu/OxNTV2O8fpikBxxxxxxxxxxBck4NttikWWto98Kax2dgxYl8mYJ8USB90R9yeUJOZCHCwvYVnEIaglTNgci3D0BOhK4b+xVLgogxsXPVnIHBhM99v5tVXoIu9DVUXrlCmkGJm1X/qUBTAouu6tzs2TimF5yUXOKFQAsoHNIr+Id7DDMiJ6QEhUE3BBy3tfb+Fr7VYBWpvq2daHpP8Se2vXAvZ6lOh56xgsNHthaLA1l4Wlro7pgBGvlXPDMnU/uFI3/0XaGC3UWs6izzWUkqsE3swvCdwfGkKRhaTIe9LWF898TUoZqs8/5GDt1qMARL/lB5Qbw==")
                .withSpecialStatus("0")
                .withThirdName("P")
                .withVersion(0);
    }

    public static String getBeIdCitizenCertificate() {
        return "MIIF3jCCA8agAwIBAgIQWBUaGivh6fcivH8bAczCQTANBgkqhkiG9w0BAQsFADAoMQswCQYDVQQGEwJCRTEZMBcGA1UEAxMQQmVsZ2l1bSBSb290IENBNDAeFw0xNTExMjUxMDAwMDBaFw0yNzA3MjUxMDAwMDBaMDMxCzAJBgNVBAYTAkJFMRMwEQYDVQQDEwpDaXRpemVuIENBMQ8wDQYDVQQFEwYyMDE2MzMwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQDfGN4pNL9TOrWX4cR0j2yW3Yi/+q1dLmKnMoQRGbyc0dnnhkvOmOYhkQseKK6mttgqqo5bC4j9sHDv8t8IClzW0uhkAkGNOOvcgvQBsXu2yB/C+NszN2HMuiNbxY07zWitTK8Gc5h+PB6HNBb253w8+TcnhrIpBImSdwOVpOV1kREFyrpCthTGUHYVDrrJD1cgpvrNmr1Upyx/VTlEaC+yE4o682gVkcyIgMhn/myah0EYDevxveNH2tkaPKajFMUZgrs31QBl1uoHDo0DD8s6ik4skyx+0dRSE35gGUdJTBM/L+cCtQks5Ak7d1uaQCY8sPYQ5og4B76sbcdRMMvGaWIkKAjgNAOBwfcf8DICErPdy0cd6JkIi8BxUc2+N3OJK5MJTFOdrf5KXYr8sUAeaxZSH48JYjDsvrfGmlh/LsZF3fc+XR+/XnsU/LVGWqClI080zp++WsZ074WNgeXbC8HQQ2eNILy+LkWm398YpGE5H5qLsIIHy5kU8sPM+IjsvTvR67RYWirIIdfUFn3H6K22y7yrMbTbZOFd10KMKA4h/dnEJM7BPm4CJXJGFrgB+5yBLa4RRDs069njvOEQOerFTi433DbbqT8t0ubjaktRBk8eFlIsUH5wd9avme4k3N5kQW45jd6CT5YKNhsoADt2QhpVqkmx8pDOUchNywIDAQABo4H4MIH1MA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAGAQH/AgEAMEMGA1UdIAQ8MDowOAYGYDgMAQECMC4wLAYIKwYBBQUHAgEWIGh0dHA6Ly9yZXBvc2l0b3J5LmVpZC5iZWxnaXVtLmJlMB0GA1UdDgQWBBS7IO2+z+OXCptHNilk99tY4ikEjDA3BgNVHR8EMDAuMCygKqAohiZodHRwOi8vY3JsLmVpZC5iZWxnaXVtLmJlL2JlbGdpdW00LmNybDARBglghkgBhvhCAQEEBAMCAAcwHwYDVR0jBBgwFoAUZ+jxTk+ztfMHbwicDIPZetlb50kwDQYJKoZIhvcNAQELBQADggIBAEwvqBFstnQL6SoT4bjoo5V8fy2ei+IMK8W5yqSphbDySqVHgghQjdZ8+tisU+jZcwGiBLeeDE+4Gdft2FFNSMbfepmyhv6/k2WTINY34hjOPevaEzAdjeNNSYwHGsw84msR0Cskz/RWfhGpmbJyA5esuctpHQomI5NMROkFTrq30BMm3Xzk9FBvcQ3HzaiM65c0up6EzyfC3+9D97VoqenC1D2Y91NWSSxweTPnuRExNZBBwvNUJ1sn4nyYG+/iEXISo99pInipB4dTOcJYannqr3JpYBdoXKavEP+SP2Zpq2/r2ArwNMPhfCABmfCo1hQiKvbxAKqTnYXeUbST/OYtWsMp0/T/Fptw2a26sykVWv13HxQ8L4I6ODb6tMvo6F6bZZHXhuW38oGBOtZGQhFTscbDfJm+14+2krr24fuN/nCpWJD6mgtvudilfIRI0y1ehE93R0baxaMgornnt/w6616NPm6S7LRcO9YNCnXrQHDUHmC/CUkVqPI1D/gdim70gGTH/FQLoP7NKm86DE0dr6oLxGS+/h0Q0S+gSEdvg+V0gOntbnXtdH20uTduNjUitUbI3mkjNrA0lTZJJWcXXVvLM8iEPSE4sVeRuqrmi7SZZMbjOpFM0fK6dScHmEniBqU4hWZMVlgvQon7ywAd7aIedPZcPCMLzAG4enw0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
    }

    public static String getBeIdRootCertificate() {
        return "MIIFjjCCA3agAwIBAgIITzMgjMWUvzgwDQYJKoZIhvcNAQELBQAwKDELMAkGA1UEBhMCQkUxGTAXBgNVBAMTEEJlbGdpdW0gUm9vdCBDQTQwHhcNMTMwNjI2MTIwMDAwWhcNMjgwMTI4MTIwMDAwWjAoMQswCQYDVQQGEwJCRTEZMBcGA1UEAxMQQmVsZ2l1bSBSb290IENBNDCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAJiQrvrHHm+O4AU6syN4TNHWL911PFsY6E9euwVml5NAWTdw9p2mcmEOYGx424jFLpSQVNxxxoh3LsIpdWUMRQfuiDqzvZx/4dCBaeKL/AMRJuL1d6wU73XKSkdDr5uH6H2Yf19zSiUOm2x4k3aNLyT+VryF11b1Prp67CBk63OBmG0WUaB+ExtBHOkfPaHRHFA04MigoVFt3gLQRGh1V+H1rm1hydTzd6zzpoJHp3ujWD4r4kLCrxVFV0QZ44usvAPlhKoecF0feiKtegS1pS+FjGHA9S85yxZknEV8N6bbK5YP7kgNLDDCNFJ6G7MMpf8MEygXWMb+WrynTetWnIV6jTzZA1RmaZuqmIMDvWTA7JNkiDJQOJBWQ3Ehp+Vn7li1MCIjXlEDYJ2wRmcRZQ0bsUzaM/V3p+Q+j8S3osma3Pc6+dDzxL+Og/lnRnLlDapXx28XB9urUR5H03Ozm77B9/mYgIeM8Y1XntlCCELBeuJeEYJUqc0FsGxWNwjsBtRoZ4dva1rvzkXmjJuNIR4YILg8G4kKLhr9JDrtyCkvI9Xm8GDjqQIJ2KpQiJHBLJA0gKxlYem8CSO/an3AOxqTNZjWbQx6E32OPB/rsU28ldadi9c8yeRyXLWpUF4Ghjyoc4OdrAkXmljnkzLMC459xGL8gj6LyNb6UzX0eYA9AgMBAAGjgbswgbgwDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wQgYDVR0gBDswOTA3BgVgOAwBATAuMCwGCCsGAQUFBwIBFiBodHRwOi8vcmVwb3NpdG9yeS5laWQuYmVsZ2l1bS5iZTAdBgNVHQ4EFgQUZ+jxTk+ztfMHbwicDIPZetlb50kwEQYJYIZIAYb4QgEBBAQDAgAHMB8GA1UdIwQYMBaAFGfo8U5Ps7XzB28InAyD2XrZW+dJMA0GCSqGSIb3DQEBCwUAA4ICAQAliTyrUcumj9h1IRKZNIKlt2hAyNW1jdkYz+fJ57PDOqtpsvmfoZmuP+6rSbhDi1J9pfi7Yc9fIDwxGwfkiPJAiRBl1axdtpnkoAMEcxQonLnxMiS6/n6nQqIXpb0O34YAYANJn5Lujd5V9I+iv5rrzXjvcZPNxgEB3x6fJdpVapbokhgrDrg1g7XqEY+JYiJPmv7dXIzhZ6lN29foBzoik1o6Wp4UnC4UsFTgx/hN56cjkdXMCR9JHQMWmcCzkk2ZUN2SPYL14xK3dCHAdPclmjVoUSZoyXEoHM14FTvV1Ode1UCJB/EE0l9M8HQapVXIUhSmqO1R+A3RD8cvjP9P5FDoxCmeGTrqcXKIj1qWs7gsrXZ0yDCs7HyST5/oM+GQ9OLhU9wrH4cMwW8NsORyCqZqeghSbd5hE+Almj4SmxjPht7krtQXRGebf5o6q+RLHXnFCzBtqJeA6U6Apr1SrSvEpkOXLIVs2nt8o/YpAoUMxOrwPRwbjqfl0UUS6IvKZhALeMBe6GvXpMiTqmlta7cD13yKJQBAvzuE3QJMPY0XAis6CWA3zUVbznuQ2Vtk0MBtlQEb+w3OsUh4eIg/AkONJ4/24AFfOzklmB7k93t7Xa/YuVWb8go37wtq/A9HvFieImuusfghZ6EU9pvUPmL62NPY54g+nFm2qMtMWQ==";
    }

    public static String getBeIdNonRepudiationCertificate() {
        return "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";
    }

    public static String getBeIdAuthenticationCertificate() {
        return "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";
    }

    public static String getBeIdRrnCertificate() {
        return "MIIEszCCApugAwIBAgILBAAAAAABUT4dZXswDQYJKoZIhvcNAQELBQAwKDELMAkGA1UEBhMCQkUxGTAXBgNVBAMTEEJlbGdpdW0gUm9vdCBDQTQwHhcNMTUxMTI1MTAwMDAwWhcNMjcwNDI1MTAwMDAwWjApMQswCQYDVQQGEwJCRTEMMAoGA1UEChMDUlJOMQwwCgYDVQQDEwNSUk4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCitW6LAD8+7T2txr5L2RxNLCOczNukeZOaoD4Ygw8RD4RCffX+Ogcb2ww8vbzrM6qzE8aVzKHvHveCjv42v9Z1w13hPc9zxf6/w6AGVXw68fSoGNtEqVgSHqXSiUq99lhP6RtpwULYwDwdIWOGlNM9SBOvFvyBP8GIX4CVvoUQ1CCuKpfgyZuEB4fzTNOGFfPJYu7UbN3uhtQEOf5P1hRy+6eeSky20r8R/Vy5TJCX+ZzPNIRp9ykhp8q0u+a330WRR3sbwGoN+da60B+wVKTfoHFu3Je+IMRtmPET4oAhJuyn6hnqmrZSGXeyfgz0Z6tYi6zX3izxqoaW8dTkQmcxAgMBAAGjgdwwgdkwDgYDVR0PAQH/BAQDAgbAMEMGA1UdIAQ8MDowOAYGYDgMAQEEMC4wLAYIKwYBBQUHAgEWIGh0dHA6Ly9yZXBvc2l0b3J5LmVpZC5iZWxnaXVtLmJlMB0GA1UdDgQWBBT3O7ujyHKZZueUrb/jzajcH7hjtDA3BgNVHR8EMDAuMCygKqAohiZodHRwOi8vY3JsLmVpZC5iZWxnaXVtLmJlL2JlbGdpdW00LmNybDAJBgNVHRMEAjAAMB8GA1UdIwQYMBaAFGfo8U5Ps7XzB28InAyD2XrZW+dJMA0GCSqGSIb3DQEBCwUAA4ICAQA13oNlq3Vg2FK8ahwnev5FcjMrFQnO2uSn/BWz4TwlJYXUdpk6bSCfmj4wmw4QsLEyQ/1cYXbdOKg9srK5f2QZavVMcOYmwE54lhxkjGMhnD6R0mm+cAnU+yH8dXxComxxI+22B1JESMT/F5xJQrRkq3wfdxNqzfGNIzh4l3xWzwGtInST7f1tHNn2umwa0Ipr7v11z5wVqTABFIxXu8DU182Fry9VnDQU6GoJ6AYmfC6lUY6VDHvKTZLdv80KRqwftK4tCqRHZX25DZ2ZIdhhvvyyCxjzZV8PvKAz1A8H7EkPzw88YJSNSKSX69FDQuo0teEn4xLHWP2mJKrTHsz/CfJQWq/+xjNZsjm25/G/ZfwNCphvkqnj3DiFYU7a9l2d4uV0PVzli4eGIJ2ElF7trToUgJrxjA4sdpw/UMlRAfZnCs70vxRfku3DCeHaohUhVixBTONRK3Kkadu5uYA2ZhuwazV4Dzsz5iu033/wR4jKIwUx2/MmSoZRoIWZXFjiHadR6NzneoNb79VlBbgzUBSX2RpvIORl7nKdYUfYtwXh6nps01BP3SQgaHn009NLJ9Ufel7QSGOrfQ3uqdQ0mZgxZzxSkyBtZxYC81dB2ZbIhKaRBk1DmhZziMBjLNkXUGwtK24mHLU0F0LpKLHzg/6AX/mE+Z0DK5LThJIUgA==";
    }

    public static String getBeIdPicture() {
        return "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";
    }

    //
    // LuxId responses
    //

    public static T1cResponse<GclLuxIdAllData> getLuxIdAllDataResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclLuxIdAllData data = getGclLuxIdAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("biometric")) data.setBiometric(null);
            if (!filterParams.contains("picture")) data.setPicture(null);
            if (!filterParams.contains("root-certificates")) data.setRootCertificates(null);
            if (!filterParams.contains("signature-image")) data.setSignatureImage(null);
            if (!filterParams.contains("non-repudiation-certificate")) data.setNonRepudiationCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclLuxIdAllCertificates> getLuxIdAllCertificatesResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclLuxIdAllCertificates data = getGclLuxIdAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificates")) data.setRootCertificates(null);
            if (!filterParams.contains("non-repudiation-certificate")) data.setNonRepudiationCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclLuxIdPicture> getLuxIdPictureResponse() {
        return getSuccessResponse(getGclLuxIdPicture());
    }

    public static T1cResponse<GclLuxIdBiometric> getLuxIdBiometricResponse() {
        return getSuccessResponse(getGclLuxIdBiometric());
    }

    public static T1cResponse<GclLuxIdSignatureImage> getLuxIdSignatureImageResponse() {
        return getSuccessResponse(getGclLuxIdSignatureImage());
    }

    public static T1cResponse<Object> getLuxIdVerifyPinSuccess() {
        return getSuccessResponse(null);
    }

    public static T1cResponse<String> getLuxIdAuthenticationCertificateResponse() {
        return getBeIdAuthenticationCertificateResponse();
    }

    public static T1cResponse<String> getLuxIdNonRepudiationCertificateResponse() {
        return getBeIdNonRepudiationCertificateResponse();
    }

    public static T1cResponse<List<String>> getLuxIdRootCertificatesResponse() {
        return getSuccessResponse(getGclLuxIdRootCertificates());
    }

    public static GclLuxIdAllData getGclLuxIdAllData() {
        return new GclLuxIdAllData()
                .withBiometric(getGclLuxIdBiometric())
                .withPicture(getGclLuxIdPicture())
                .withSignatureImage(getGclLuxIdSignatureImage())
                .withRootCertificates(getGclLuxIdRootCertificates())
                .withAuthenticationCertificate(getBeIdAuthenticationCertificate())
                .withNonRepudiationCertificate(getBeIdNonRepudiationCertificate())
                .withSignatureObject(getGclLuxIdSignatureObject());
    }

    public static GclLuxIdBiometric getGclLuxIdBiometric() {
        return new GclLuxIdBiometric()
                .withBirthDate("830819")
                .withDocumentNumber("SPEC04168")
                .withDocumentType("ID")
                .withFirstName("SPECIMEN")
                .withGender("F")
                .withIssuingState("LUX")
                .withLastName("SPECIMEN")
                .withNationality("LUX")
                .withValidityEndDate("251116")
                .withValidityStartDate("151116")
                .withRawData("YV1fH1pJRExVWFNQRUMwNTIxNjcxNzAzMjM8PDw8PDw8PDw4MzA4MTkzRjI3MDMyMzVMVVg8PDw8PDw8PDw8PDRTUEVDSU1FTjw8U1BFQ0lNRU48PDw8PDw8PDw8PDw=");
    }

    public static GclLuxIdPicture getGclLuxIdPicture() {
        return new GclLuxIdPicture().withHeight(320)
                .withImage("/0//UQAvAAAAAADwAAABQAAAAAAAAAAAAAAA8AAA....uq9eK159DRO61Ufrf9ICA/9k=")
                .withWidth(240)
                .withRawData("dYIljn9hgiWJAgEBf2CCJYGhD4ACAQGBAQKHAgEBiAIACF8ugiVrRkFDADAxMAAAACVrAAEAACVdAAAAAAAAAAAAAAAAAAAAAAIBAPABQAAAAAAAAP9P/1EALwAAAAAA8AAAAUAAAAAAAAAAAAAAAPAAAAFAAAAAAAAAAAAAAwcBAQcBAQcBAf9SAAwAAAAIAQUEBAAA/1wAI0JvGG7qbupuvGcAZwBm4l9MX0xfZEgDSANIRU/ST9JPYf9kACIAAUNyZWF0ZWQgYnk6IEpKMjAwMCB2ZXJzaW9uIDQuMf+QAAoAAAAAJLEAAf9SAAwAAAAIAQUEBAAA/5PPoXgVAzZPevWYMXl9xEwa6J1Zd/btnUSNWrJj4c/8GVeLwvdSF33kEwszvJ8Mem2DV8DqQCueHNVEoOGgnYDb0SiLwZapW8BUgAPZ3EEbJSvQRcPiPQ+RHD4joC+htcGyASWs5dsEGhQnYoUytUdyDfq9C6ryFmI5tWCX9IIy2hzkkQabLmdN2lTBdpUPQiWzJ0QqLsFeAZinDwXXNLXRov44ZuaSOF6BEd6p6SIcFELH0RsK6bx6vVeAgMfJV4+SkQ+HSomY/mczlQnNA3TwR0tnftsrBCjcQUNNVod6YeU22vhXlbyCn0hri+Do65F/jFN+M5UbsUt5CC9an6As4kt4Lk9+yDnm5ShICBFxfcY+8yDao56Ys11ITrJMpO2po1OnLQ0mH8dSVisJi89KcMQCe4a4tJ3zgcYwWQpJdyFS4cG53/fW4fKXKO/iysbxMs8ol5h43O/N9YeYU0lQm9jYiQFOmrgyIz9b4uIZ7IELFM6nWsdQOmhmkbL9dGnMq7OUCBeoxv8yNDhc7fcCenvfX5bszOBQekw1/dd1UG44Y2OJ2XEG3GjHmlJIgIDHt3j4dyh3ScI4NBbfyFjJiLWmJmZvPiZ85bYX/go+g08TTlhIhduZWO/b5KFZb/E5kNgxQiUmsBbpGkJgIWuJeKoLNv6pFrlv7R8f+7koF9geHHCfTCnQv8NMZoVqGp2+scgXHimj9eKEX0XVHtkceJMBriC+2nDCdmsl0hDkH5phocntxv53d/5RCHCcUnNvur7NaAHQyHmg0DPaGDPr4cSjitPKrsGBjyf+do1NAxONgJWt270NOo3ufz5gRWw4DSdzDgW0RNwZl+HGfqX7DJcBQJknpdCrxh0bRBEgn8Ld38p2oyZFxDmdC77y4zWkPA4dk61470qY9DTgBRxs6yKolqkW/PwP/OysB4pjheRrVjgxoEHLjKFwfMbNpAYohM+zZf2ScMPIMzP8CK7MxRaB0Kbz1G3VvICAgICAgICA8UBnSP0Nyc3VxuF1zPz6rd0RWcbXmjLjqBDxM3Dgb1OlC+xhZpVQ+s0q4RuYeygoy1u6dsB0oFN9QJ8HVmgr4EXAMMBVQUnykqciaoJxGR+n2JTH+WMHAURDE7W8cw0LZkXl3n2ai5+ogyM+5NRD/i7Hw2ItyHklpgO6ModfrDInL05oA2eM6/8ILTQZcZEVYP9uwfC0AKlCYI6CID52IEQ1sSr7IkKDTz0zSEqhp6RlgPmWqOUkv7e5YJ2ThzE9fRefCZyTL7XavLHo273qZd4deUK7uzMoC/prkzUcDG6yEeYRdntaPInNmcnRkRkwFkyJmdnI4pM0vElQE595jfZZ3i57csTlybv5stEiphL0THwebd96xTEp2rciwQUrcvuDNpf2yac7wN0jHXG0APttuTBflT0R2agI6g6OZEEONms//ruJROXRWgYcFvidsocE+jr7vouQg7iAzJnzZubftZxjszJNZTowExQ574+Vgg84i1Yau7d77n1kDvBJnC6chQ0XGivB0xF+6MHEANpU13AKLdoLgOPez/NDjz0p1bHuwpcPpq4dUPYeBy0DAeO5QtTIm+wRCQOinlcQRix3qzin7zPl7372xD7SULUBrUe3zF/8Yi5pGnQXZ3snmViUeOsYotqgkONmAcGLveDQKQP1LrH5t9OL9CNH2UV1/FnLU7IL7yFYKvab3N4yv/ScTfcxxjBpVg7jHxhov2/muxrzQ0qeB8hQthrl23gGMZSeF4VlOoO22v1QeLvt/beFla2QlOBS4TTACdxk0rggDbg7ITWz+0PmXpOhHkJwDoVj4C6LdOoZBCSy/aE5I0OphmQfz/Y1ORWxhlkMW1aEMtk2Euh/pPUoxmo0WvWFYB87Ca3PuBoqvDR10QUeivL45ntgjERrgFq2eSmsRkudLv2yB3Zxhz9wob7QWbo9sr61SU8+3lNfUZuCqboQ4RCtnwTxJRafRf8aZ5AycXFlPL42XZHGl0IrMm3sei8snsGMc63yHAPDkbXpPhkqtY2Zq3EBUPDYDXZsvv8PDDemszfBuTe6QgPwcorzPa+axUCzrepWYhaFHLgg1TwdRCPGbbwQFNk72FFDl9eH9WUx1t3dbzCI1pXc+s3MO+620fDSi/rpHONUHGKYFaW5OWTscp0OY1Wfvc+VwVtq1YORqydu2CdAlnBDKDJm6DZsFvGkBs4qURNUXTCxUIivlBK9aetwban4SWPJ7N+2SYCAgICA8UA6v3w8TOAyD9er44CXdZKsZIdI8ICh68evbtTBxPE8XxDje25iZfDHFQnExIOTfkYh2EwkWJRCq3xVDiPKoB1GAVAPWc0Z94naIWvfa9nu2CZ2/CvrXN6Wz6NTgMdw/hpKCtd3fYs8lmSoTUqeQvwHl7uzDe3VRrywHvhw5PyPt1q4J9RDoKOqSSCDG78/z+oKqtmzuBZO1vLX2lAWjAaubSLkMa4lhH9jt7FwO3St6OM/AtPmBthw/R3aSIVNQ3v7nZYEAkEjWybITwhvYgiiGBcy0z9uFjZvncDo4KkXVqkJWUHXHE+bAMcAcTX7tCJa3fiBcBwv95KLolhqpMRcJ9+Jfgforawd8+aCLYC6kg5HNd1/C4tUokjmiw10CSXW6cbcat5LbQO0G6Ni8AJ4HjmaSD4Zi5MVTOxV3nh+gExxDLvN/jpWrik3/i3+InhtQrXwasK+tWu0DOUIRrzM47KeeCWdqpQ5nQaVy0Gh9bDINZwhR32197U19Ph4yL0CDfZkzHoiiFSl7OQjdvQCM69ao9Zngmuz35Fi0OEZmYk8uOiGju3kDmkA80Igu94goNpUWtr55LEwEYZXoXdgRHZ9RFEYQFvcUUgPSr2FQ9rUlUfPhprRsK/uJM9mzg/iFlMsAKfBHuffqavDQWpAVucF6bF6YLivQxoxPqdrgHD1NfCheryAp7pjgNXcPiXh9r9mgL8BuL3KdMGrP6oxK9R8xTgH0cGcMKlKwkkhe5ivufcjuYt7dbihI+5GYJqDY2gakD4oSFfmpo+7qxfudptigUOm7Bhvr7NSQ9v9mf7GCTrsubg656YKuum451aYLfbgSxLBfrNMu8qepLzFjPO7NjORrMookRAcTcc3NKMrdRJb/Z5csijMMGn9WTb+Hy0CgiTeiO2vSk1Yx4/6jt1ZN59T7QG2MtB4TtspgkyX02a6HV0MTfHyFAaQQ3wf6N4Gpp81fo9Vas9KeT3j4SXBQ+5CykbJJvgsOdxdOdWX145JKsaLsKOM/xrecGMktDRTxD6NC3FF+atjXrWmqHmYw4Qh7X+NjoHb3Be1sa+AgPD5f8u+egPN1Wt4fOmedF3ZedE+obQA0v2OaBRyyccdIzupOktnvG9n5ly5HQ442jpRU5ooNnz3wiy6Vt8IcgwxxYQrFhQf36hes0ZO95x97acUGIiyVraU7RMLE2KXJ2Ae+815szOxSb6Qbcgh90bkBVFcM56aZ3ZIe3zoAd900K4NOWAJ7bk2U7xNyUFZI2aHmWW2jbsZVP4S23Yf/wAPhUG5Ovl7CCjPP3j11CdEoaVcnjYuHWZpQQ1wrxXDGDO7HXl+djMv3gwdduXC4KvY/YIR61mJU5NavTekB6usnjpKTLJrRFddbPjzTWTUB3+laV0gUYPf2vr/YrRATH8Gve9bPJ1T2HmPx78KfKneFjoAFMd6Pfn1f98q5VfmQHS2SnJgJa7P13piSPqL4E5e7eiBFNooUN+lGerxhYEGQcxfYEjcRxmgkNHJ8xQ93hMIhOXAZvqnTu14CMpBPB/PVMLGYPOCGw9Z0dUJYPk7/vOjXJjwE4sJDEjZxIHkcn8jHsnF6B8SPoIfYznK63R0SJ7OkoEuxd1UJhd101Ezm+eX08rnE+UTwEbG1/nQMlGxR1n5MONxAoV25E/8z9+6ve43ZMXWKlf+Cn0rMiFZfGjJLBddg48sWY4ssrV58mY2/3uWRyRFMH6k2yyWcG4UvKHTihGnUK6Z9gAy38Ub7I22J4iFgHHnOfQ59pTiUT//Z/9/n/ehjnwW97iBX1layQpiiB1l+OemqdGTEJb1JbHTuBHGtv2k17OSfzKuOXeAgIDGpn1kgIDjooBKJfDVbgdF+9G2sxeAkiDJbYTND4GiC7c7+v811fFoOsHh+SB/JJRnkcmpZILYFEFCEzsWDEbEkcpqLHM2fWoyxGnjGvGlhS/hgKjBLoCAwOMAyf7aAJ9Zrn7hqcm3ksD9hmCa8laKQaNqfcRB1OyamL7uu95HCcDaK5mEI6ctUlYgVQ56E9+MAdg0Qcarkcw1DaLVL69Mhx6VDBopC/DA+g6/6JKAnKo8NYvHU+dSbNSoQVwS1AJfgICt1GFdbphiFB4SAF/L4B2ovpvU30uzU3e0layHspQAMRAywDJgfUQcCED0uiFr+Dn85qppOcR4bTR9RkBq51CLgRVkmweokVrnQO5pWVLu2cjP3WA3qzFQ1hjeov3Q/TRm6hWHayWrOGvhoDxpISNQKKFRYJQfw23lko8+4VLqNTdqcAyVMl20AAkI4I7TW5QqUw+teY1IZzH7Sz7yRrGfPND/QJzzg7CYQ5k5/s6qjEeZm4cMH3QfWNEsVdQL2i19enAZV1ugV/CG+5pMYPUwdj4FnKffIIdKAJmDDRbYS7ABi/TShN07yonm5TdbpkcsebDGsLePZ8gzrqzyMH/1GlSNzRY6vPMGgIDxYHkOz4X6f+8OIgzb8YAIKqfNAG6rtc4pnY3FzSfA3efw+PjAUBU0ndLSXXp7VVhFS2TYSYiIhaXGwYDnS4qZGm3jAP4gzG6cieKyOIj+BPYazwIBtX3zfu3d1peYUZYYfHgeB58ib98kC6HfcMygaCl3xwRFneBqltSgB0GVbietjRsuw0QA1c2eExHKrdlx4bysWHzUoDo4P18m41p2SqaHXLetsPm3D8oxBFyKcGyWXdhi22Dr4Fk8d77GEFGtDKs+Nkj0XeU13NvoaFTEgvA0HPtuDggn4vGfE7zW6Bb19vuYbOjR2MVOPdNM27ZXCcJtvL2qUR3K/SPIEZ97giMQ4i6nCuQ3frZMW+Q1BA7VAP5gMwEQHWOQaajTChP8S981/dTziYZhDNvMda2Lyp8bgPUCV0wFtxHpVONZkn0HJs0n1vvypQsuEcwNBwhPFV2V43LwHaQCmvR+NZf0AZOcajrmiT2UIHbgWNlZkbPCr1Q4qYC94Hc9XNxagOGKHcLh1kjAbbQh6UaILpG283qa9GLYj/TS9Gn8b5qNkGOb6wBDHTyicm2gUEW7r8APqa51jj549XOyh/tWA2Ff2CC/oArYqfX3p2lKZXcW4NWu49WPPztWwUu04Glv8onuuBN6ix7qSuDsum+bkUFiyLtG7hc/F/w/+kDMdb+3xcNdPWYBUW9z2guVXRhelOmXnHw/171OQtDDanjQa0BpbLrkaGt3iOlE9lqQgNowSCURiIx3B3dbtN6Lty6otMoGKwSUmU3oeiGZhaGRbKhPAbZ4k5+LpzPtHDCHgATIVGEdrDuYguvk3y4aFu1K5fudqIUf0+/xaH5cZIzEV/bBI1QOrOHbvu523wuRGgFfoF/MWUCPKzKIA1KyWWd5U0fjHr7Ah4DFhuUuxlKFW396t0hL9O4uWUZezZWZJvkerUmE2sFXVNrvRPVxFb78rxeNzxbhoDe6gRiJpcs13vYzvMZmXZepyClqru685MCSCVLn/chdm/3x5IJn5bIfajB6TWwr4e/trOCP1ZDhOlpNQYZ5wRvpDJRirlOxJLm30/ZH+OKL/wHoE/Z5FDxRy8FWgWMWyZAR9X5YSKRTU6TRJs6ZlMnkGilEjnt4OsnamDuRSaWUrg7PSHsobpQxBZJAzChklptGEvGGMiRKBft8khbAFV7xndM2Hja7xaZxA9REx3Ba7HYprXO1qIgYNaXzpFGghUYjSyOgpIIunISrGcn2GYsrj59rjxT+akIllvbpgICAgOFAbTPs98mhAOTO4UAo4FpdQ4gxdScHcc6Kstex9KAJAEOK2EnHYfHgACLuoYxHRVm3gVngSLyD0p2o9kKTxnDCYA+UsWVyD29qfNHfPtwkco0BBwPLKsAI0ip4q50pKIyFfS6kCfYTLduDXjkDQBb0kVq/mMeHKyRzf7bvcRsaRhrt7s1E1faqSJ24oUrx5YK8dPYE4rk8DIG+YyzTGaTI/0dhvmRZHaZkSyLzCGhpvwmkcTo5koMhnMxogMxUjYlCdkadC4JCXKNR5eKkgFK9qqR/RnPiMilpK3gNdmu1hA9BoSq7/JNAz6YUqpDWYJNJ+yy2fMAYWCaJ5GqVGxCDCkM9k6QG4buO0VMM2H20F9PLDKr3jVLd/OG2/e/PpqV3fPNEAJYhZ7MH6AVotuOrof4u8cs03pgWhie4atIPjSOv/fRa/LvPPEOPsoDMbmRUMrx6XgybguIgfmSK1c/ctHQ5KiL6qnHkBpBEHEi4XaIgogA0vRccu3phLm9RkAd28Ov6Fz9VDrswdEaHBrlIm34BF9kU8NbcwE3SRQ0SW5dTBcwqC+8RCQxdnKQL2jjazGqCl58NqHXq2CjUYUf02qc73eZZgtiXQco7jZ/5P81+v4cYZhk17m9aM0olLbCPDC3MhhW6v/qI8wW+vTBjMlBGhz7/JKjYmNETCkGGlisq/xg5ZY5cbfd++/GG5x17FxPGEtNItXvkHqtS1VuyM/pV3/2XDnFw3vWg682k0ujSjq38FQedfBJubuQWC7LLy5rcFF3O7lucPd5l4PGcaAxVyS5zloBTmcsGgz7lHuHkslezgM/pHcuY7d8M+dnxNBmAzDY/Vob8jBULd+LCG+RDoSJJueUODyeXe6PpeBc/hyHbkXNKDnU/2gByss6ELmqwpw/+9ufphUlz9BaUOPXfv9VhhSxkZdDddq9C6gOw7ZWKATvrpHINstuQy16jawArFWxET3pCKoTOePgrBy3aWcKnT3SHV4DHpSmaROKgpTMkQN3nv6BOK42MvuhX5aGoALVskWBuVis5kBZAkJtgznkY4uO29VkPXMgFqR4g6KopRCxjQ3LmWlD6VUusHFlfNeVuPjkf5MEduRb79AZJtbxY7/NTS63ID2E1hGgG2T/GKxGrmoII6SlgDATqSk2VZW21apaxPTt/T03S998Zc6zO/ZvC+1xnZ+7ZNhnt6c4JrUWNlPAe6VPbtSUPD1AGlH9jmP4GcArNyhdLJRA9R6z6LikcziBRctI6AKn+AnenFi0TPHREzds/r4dfQ1WGq5ioLVy2r9o5ipKmcOihTLP+Eu0qXZHVgwjsFuF8uXDEF4ZzU4d3A5YXUSVw9EkuHIFH59Q+y5PKDWshQx0teCDAVHKZY4bV0dSHs+ozX+0JEjDG9wy+/NJBMgGBMy+fRrHkLO7+/yuYRFXsIIDQIeBRV4CA8UAcSpQ3ZCK7orNg8UBm8+ibCOc3iteR4cD6lqChql55uL4kHkR+lKLM8aSXONHN53oKp0A9frziccC3ZQEFrQ3lkYrfuU8pkTKrB4vk8VAHSXO3M7L1RArb49Vb0iC1/A5dt/M0tI0UVb6NdFTjOLChxqKjQkRqWg4yHtI8H3Y2j7B1uwZzw+7HKoazAIQH4kSbaeo9pWf1RtGO+pX/ReF8NQ50k+M9XM7PHl3iKDXwrYQ+n3rDMPUU11Mbh+aQ/K7dtmtKQkPwNJlPyFp7xOKgHXNRDAKe1emmj1hkURX/Kt1ThtqxHXk1Uf0J0O276NBiDLICPlp0jl4a1ulPZYpqpVE7z3a2Wa9en+OZdlqR0SSCz2haSh+o1F11ahEG1hbRgC7T3avhufgiJhH5v6yfG9/Sg3f3vHBIxPJKeDwyHugxDqQ3tjVasaZa6q9UcW6O8ULG/dqyCQUd5UI7kDheAI4R4C+7U0FZCukjarQMdlZKSC4RnqSdvWwr4VQoEcK0C4hujyVS2VGVN43M3V6YEDj0DxOeh9HDPLt67URj9TqQcYOhrEFy9DXNpShoATsD1OYozioqV3SefosIsgthnsadPqS/LarDoeJWEfeAxDiBzRxq92s6d+inMGGjnDKbGNmMBXrU/1Y26yiAMo6aSKTlpS75e3p6bTJmB5coDl60Pt6ze0cAXu3uGiIIbG4HelLozrfMoU8C8+nZBwD5L/hnoRl5i0i96EVLf8zc68tHQIhJEQyEbfgQUR//a+E6rnPPaYxs3yaSXs569tKQW6hOlMP1T1th7q7EvjiqjbNCwuBqVJ1gAiXdkBxQhU6cijQwPBkXCMgYbGmHyM5XbPHTCtJeCMVk9Ht+/0DzXSiMCP8QnJJKPYLhTU9WestBV0+Ubh+u+fcUbeAHFN/fbECVd/VBTAs1mYq+dvyZtaLxSSIEPBs/dlN0UqeIvJ5dZusqL8KNytBoWryjOjxDBxMeVHXUedtKTPxx6BmaP2SrvdBDHo7qx9gsGm/tnJRN88dawaQIN24ddN5WzF58VL4x3RcdNU4W++kI8iuDaLR3A4dQzwdLxjty1wJn/nWQdODCbrF9Qpw7x2h9MIhllBfF1uLDGmKOvky/XZZqZYRs8IahuJBRE5wx/ud2Yysn/HxlGUNwhzYM9MwtcI86B8JnHgv5ZP4ac9TCSHNRIWLAlNKh46wygmQ50ce4oFaF4ma3AYJ/tFChEVhGnPIbVCxlDjWW6aO0Gt/Uyb7vPKGDwwBzG4FLy5WbViCFWKOiz3HXC/gC7zcFfbMjWXD5owCz8WMA61dUxjc+OXKuL6gvoA6KwUQjj/RWODrRRuVqjdTiRAxhOto1GyPfGPU4yFIDQw1z9X+AXWWwBJqYyOS5HOgzfAcdbltdewxtM+ATH5YqjXOr5LD59PbMH7sIoQXD99E/TIG/6zdgqbumhXHVaE5P2jEYKU1q6fHU5yb4fUzu1J3xq/SejQ7vkiGPpLf3YkbLVjATWKyBEM4FJ8k03QJXySxSmtlyZ3UQZMXiUX3kALbF/GHiNYsb/k1t+Xuv+NAX2HU9tl6Z4PaKqgDmWoTngpFw+OV5ETl3U/YK9h80RHikxqB3ZkjivQOkjS2MoE21HjfhQcSy3OFsNcO0VifKaIwnkaHnE3iliFyieK4ka6zC76Zvv7Cr7n9jnxbJd6N7Klhp4mImpI/IWHCobORH20U8sLg3s8tamGD3D8wVkLllKCU+aR0ihuxDM9IbvQ5xDbCaUriTJxOTFNFcuZUJmPWRw89+etIBxIrNuZd84KzeAu6igWAfxetoeFotbWWflqtOFRIeMBJmTd3mxbErvHyCmD0BDpapQYQQ6VOXMXxAYuRoTmnBCTtamEarre4vvRS4b+EKWy96MnTxkeookfCHksRX5i+M5BL/M55TyyIxl/9sxOsOMMfy0j/IK4ip7SJrVbC3IC5e+630+3/tmNoPBc4HyIludbnJjEGfagHqP6TeK7fjxmOHbcK/2OQjGRBSIpHNIoGUOhNcxjcwOt7O/0NPBfD5S/X9AYifWhwAb3GwJbMlJhDhP2wz9Uw0q/vcssF+Bk8R0f9E8VV+GGFoAoNog8EMi2sn8gK2jNT4wkLTP41VksxoR/iBFm/75NprwxguE68QcS3jhmaXRNYUJkQ8m4ccv3tRKf0BqAgtC3/XjOKBEDrxGvqwGE9yKvcMTSxh2N4TyLIV3Bh1OmGX7z8bv+iKfODJd5dT/1ipdvG+H9D4vsVT/2uOinv9wbvhTGRy4HlUUDOSo3Smzrjchx2JMlXwwuopbWhKTS7WtoTyvp39HgSaeIY/tp6vX8aBB5edw/OzXdA3FVFaOcLL3sD7mmddkfiVG9IMQeAbHBmflMKkBbbaitYBCZM21IzXuQKWxzss/gjppCm0VBlqL3oe7KBz3LFsZdehjfh0myhQTwA0Bu3em+Px9kvpulBS8pEKqHCRXvHj6JbNJvsWsj5I9chRsTOmeiyFh7sQ9p3zocOwWwOz2hJ/bfKVusQPLUX0ZGDxPZsC4pJfH8lSKkNZKwjL/gfvg9wI2dHnxWE+ooQEDLZmBrKAgICAxNxI9L3xSIDyZ+DiaOeHfNw4i+nJLFA9Rp/pwWtWnRotwd5yuYDFxnEIHW4S1LKNwS19jeKHC6yoaV0yufJq1DqBEELPHajG1/surriXe4d9rcnjeDrGAvO/ZKh48WtiL+DLS8GAeRsUkXq3OIEf2KuZz7jtpz1Mr873umEfKhbAGixEvPI54SfKTanyFKWDym3VwIzMgMixfi6yXUbXsszl8ufw/qt3z2LOkjTapZcUVPqSjslwMM3qQrhftIAbUHUbG803A4UsGZOV8P1m2kzAgU9bj9I6LgR1BGHHC1zk2vjRHVtUOYsLjDrYMbiPiyp+jP464lviQzrIFy6/Hu83UC1efGBNGOGq40nRt9e97hbksmt7p41T47mo84S5s+m/s8TcaqIEPH9S/KwCnv7VE+Dgs2l0hAhwysRPrwQIDTb0lA8YShNP3siM9IElLhVF/rwrNust7VozqWBYQG5ZKx/3Uf2YF9AMLxuii7aG0x1M+FrF+k4hH00Yr6QKPicjSmJBFMNW7Al6Qs1ALoaoCH/AqChEox/CIlJLN21PuIvWur7JELUA8o8fXPi72edvGzqQc9IJJFNGOjxd6as1Kp1NVdbMKDRKrHxqk0hMDgc3AEXe44sXo0D1AAO1erhGefzS+PN1UaEsOSaoN8yU1INknGs9BLfcGImsglBVFaCf4mn5q2/Odk+es9KuQUgVJFiOhdcIYMpzVBp/2W1+WNzJmbyodgnuMWVRZK3NAr4xwGMgLImkbRBifOD41qGrDSaxOVZ0C5YARqkCG4RctoRpoNuQeidwuj7XuOYj97hpg7nVpeSMg2BuvFyGNWdv12ONWNpsMYfa8Y2P94Hlve3axvBLaaytpFDyRsZrs5L2QWHHm5hkn6uDotXx9+xbHxtbIMjq/y3U5JNCgJ6e6f3g2JxXe7JlyVKiJ1/HGn8LHvEhkUnUzhUkq7EAixzBsgg1uQMxqxIOVpMDMtCieBUyB4RXZiAPBQRLxMEzeKYLKx8WWI96CE2ir1tsN2CPRcAD4O+D7tvErTBRh3JfzoTFFH/qGe2iT+lcZ5u/nVNJqQLyZ8TO9snFz44cVkGvFBUnkxtqjwDLfd+ZzcWi4CDYEodwDOfLSeKXtP9ane+IPu5Dsi+ychOPat0dkxMkkuuChC88f7D3SFXDo/7Mn89OHL92BQLmuNeJN1DrHDjmKqN2DBKmPP3417F0DGDzdGgr6HMgN4Ey0ErMxP2Ai3LzhzA9v3J0xfRYzUuKqYg4v1uBVSj7P8MsTsvqJUDmLhSyPifl/HnD+FJlOIVV0kBQHUkG95jlkO1jsOy8ZaP1Uyqb7OEcKGv/LvolWZJhEFR9iGOB4dwiv0X1YNEPi3LD+Gs4iFY8Lj8GegkW7gMnaSholv7/EbEFjlQ/Q7b4v41D2fb1ws8yZT2mvQMh6eAQQ389n6UYU81+uKoiujJo51QQ7qmGk3BFBBrxGqWkfZZPeJE8K2pXjx5ObwBHeOyXHBrLR72KIbM28dGQzHrj2tO38ehDh6iT77RKv9k/+MOxvLK8eaexrjGt4xu4Guu14J96wfTIKioxPkVXzZ+1kId95FzU+sjL6sfSkCeKaXV1nRStokCJIpfsR0+zkGpZwAuEhHsi9HrV4WHI7nsuwa3PC/YLPGpogzQsCHkrSc3gbperKJ0niIDnf4b86EF4JuU3ym74hmnVOkkswAYtLhyGmHiFC2i8fXUq0pQzMuZb0ior9omhWEMVphUxVoR1JmbgXsjrVbCwFAWKLU3PfHJov1xglsK8QkXHx+DkWr+3Y2cEcxM3oESabM5Smn3cvvlPWTt9BF2kSj/frVvD+xLsrHvFpEUJNNG8jp0wfcThqo4j/WH0vomArBOYpYp06W8pfbZzWV/6MoRuZWzOmviyjhs9/gIqXsu4h8L5sCc8fT54RZe7xRF7FIfWeUB2NH5H2UOsxBqUxVm6KRA7Tb3qMvFLjZP1QakEopYFSDc9knJ6cUSInV0HsySL6HkjHd+A56jKotIbMU7dMalMurpz8td0osdcn4wRcK9V3r4LkFC+iMOo/kH5vQr7UUZc/wmGgy0kz7FEW6VxdgJyK5iP06MLpwyTWPZJkD9QM9OU74m4e8irVpX1vsDNISY36NamyG+4LdEqdWDwtiJlF6xh/1ZLkcaCtRTvwDC+74xu+PZ7bf4RwhtHjbBiJSF+YQSyhHzhak3fefdgaKhLmHSpKcdAOHgEoiqiS/nKHx8/lgQ+N4az6cwWcIwLuuTySG1s8MklHwgC9JGMTN9ut/Jw9WoH7gihhUkw4OVALhznWi5iQxOkWNvKXUYEV9b7aYheR/jixj2ksJ+SLmyVGGlHCM48YUtL2zcBQG+lmzTFk6TnuB8eSpBwqqr7Al7Qc+uXoNppaAIVNEf57JS7S6ejNCHBLyEKbFuV0fUxj8o5ba8h20leJkrN7qvXitefQ0TutVH63/SAgP/Z");
    }

    public static GclLuxIdSignatureImage getGclLuxIdSignatureImage() {
        return new GclLuxIdSignatureImage()
                .withImage("/0//UQAvAAAAAADwAAABQAAAAAAAAAAAAAAA8AAA....uq9eK159DRO61Ufrf9ICA/9k=")
                .withRawData("Z4IJYAIBAV9DgglY/0//UQAvAAAAAADwAAAAUAAAAAAAAAAAAAAA8AAAAFAAAAAAAAAAAAADBwEBBwEBBwEB/1IADAAAAAMBBQQEAAD/XAAjQm8Ybupu6m68ZwBnAGbiX0xfTF9kSANIA0hFT9JP0k9h/2QAIgABQ3JlYXRlZCBieTogSkoyMDAwIHZlcnNpb24gNC4x/5AACgAAAAAIzAAB/1IADAAAAAMBBQQEAAD/k8+sTAI1cqDF00HOMcLaEOkGEwYsxhyAgMB0WD4BoHQgEeC5Hy8V3YXzw0cYUoCAweRh8KkHkjJLB/HsRb9cABOGUIfid+08HbwpWzvc+SQnx4zO4/6CbPP0KGAebNSlKLS3x+bzRwEOvOyTj1uAgMfDSQ9Vx8LIjS93w3HUpAcZVsWKfuPGS0AkSgBHrUPlIfAactaM6joQSLa/PbNHWEHz7FkcEml6hN5zb6NMa0i3f1muFBoOV0u9CBHQXPkycYNlOxV8Fpg2fzps182NI560oCXP8MGqLA3iEPNipqWAgM7XTttFrM41oCr4Ou02sdQUlpLu3QCswGarGG6+qSLp2UepJiyVzIUk6JGuz8/sm8MMRnK7ex5s/dTWeOgjHsK9qxWr+e1QPCjoX4+gWYEdbfVP9sxfbl8IW43+7HlUtEoQb35oFggEVjvLflVNLX/Y3R+Yccd2t3Sm/qe0dvtvgIDn4bu5pMebLzYg6U3qidwqAtFnB72u3I+Zv8NafP6qBcOvJhOiOfJ/TCz/T9W1zL/5BrtvqpiGLfyGyvElpL8BtUsJihPhtJuUzZxUuk9smlXbXfMDW3Awt9md3AKLsXzxORIIRCIPmpThjUf3DEmRVoBV6zcVNuD7quLS273hnN11zR/WpNyUWRlqJAr9aQQDK0V5GNmDi3oj5tdohSJQFJfr0wYVF3FI2Pgsy9YYapX6sdhG97S92zT/PFJAWJ+4L5sORBeH+oukMT+AgPBg4+GHgIDH8QX4BYW9gMKll0qyw8DqLGaAgPD/AR4YajGASSG9mwZwZc3xHG3PqrOyV4ds7esG5U3QISLlgID0l5B8FGDXnK89Qh6LDA2ee5CJyMufcZ6tWiswJFaD360fIvELrlWKv7IS0gG3BwfRpqwCGRe6Ax5JsXgh56e4zbqT0p7lawhhDCNryLj2qT09MntL/u87giu/gID2gdoXUACwOTcdXJMEuEFpbTaOQrLPecdHIr3i0/1jHOQTq5akgnguksB+ANW7hOtURUBKoTgsJq89z4d7eFuseqp9n09FcQpp/CldkQzqaXHlGnNhV+lLrEdWNwSy/x/G7bUHqf36LdPcYieJI+JZ+AsRaknacJyl+ynwJ/LPh+t7tvgUY/i7/3xohN7m3llI4zmzQlcxtkKs65vmMe2EFvNcPdC0XbafMsI85d9W6WoASo/ybaykid7wSvc901QenKYg+TJhPYCA6h476b5Fh7X+XBExRRkz6kq8/OFZbB5/pEcG6OAT4oQlQ7ee1RcxXsWP14VbTqB/vFewPKq3qJb6bZ710s6CBw0YpgteXfpmpQVP/SlVjRs4LSKTINzT3U7vVQEG0BYEtf59wQNma/vVAX/E+TJXlJ5xdfPhBpa5mxsTSSBqdaebftBVVGc6AR1Fh37A6zpC3Z8xzNnAN0oYrMzZRISJgMU+CGCORoN1iUVTzFH963ZcdB7pQh3YHGh0cjJBQZLkaqoYoqe5t96P1aubWAsmiV4BNQF3mILbQPZHW7T3ZF5UEiNNKGIe6gp/nNnlXuHoqsczB+U2ONtZJgo20bVrdvurV4Vkac0JcPuAgPwDAJW/QUIIf4CA+F4PoQDnl6slK4m5SAVOgID8RjpXwJBsqkdo16N26tDgG7SL6Yx7owAMxutHmOmChtM0qo1fd0y6izQ3tCxs710ue+YhP++CSGSzgID8CzprqEAwKu0kaa4fT7pVwHC/0AJGYyaEKSieC+zdy74lakClE8OZr2z3lFUJUbEWZMIaTOEYWWcsSivt3oVX4HQuALF0Ye73YPQQOpWsCHCj8VcTKxUlULwCuvhnWyjambyiLkPSky8txSJUgID8Cj8C98C2KeCdpPWIYzMLvLVY4EFHBEc0eqUXMmnrNjFE2omw5r/InS0DE6u/bZRPtnDhOD2V5iyl5dRNNTygEqQG9h3QxTBd7dxuCZHSJFB4olxlmvxI1eAIN0B6O9Q/kiA7dGP34JYi2bRKoeyeQ7C9MtvmUyj/eb+o6otBkg4No04IRst4hn/sgyJuohkt+ov+jXMgUwXkmknk0JP4WjyA+vcQ/btw/XrgnMmtdbfqmvY2EcjaHb64FdXIvxcZ0MR0MqVnuMrLtQ3KmDm79mejYi5mSoqON3lUKZESaBGW46LGsQM3xd7I5BEew8AE2Zm+1B2cYYZv5wRQAliD6z5tYrJk8K0z6/ung8BIQp2AgPmPyp1Z+Bm7U/Vwlsvx8mu/TCJmAwfXGLq8B2xmX6weMrNcDRaaO+iqdBhdH3Mbwo7LnDbUUVSzL8ErbjJBjjaKiLbLJNAKYNNO40psARm4U9hQaiP5qaqDNjjSosOXBxvkMTPIPUXDlL7h4FM00vrsOVk73B5BEW0MLNF6N/TLKwozcGN5DPpjnNxv4yQlTdUm5Xe5u5oJgcDTaeMXsl8lv3qLq2TzX26PbuZ+cO/6BWVQ68+Ldp/u1/dppGFCc7cVTQlOET5FStaotdluNv09MPw5ClVqwrNNNcWNiN2h5ZtJItdFK9k3U7beP8kfM/h9sCwKfXZIBvUeum/8tm7sn7eRHQjmYAG3kzd8EBwdy9vyt95CGwtWxiVGuIH/GKD01w2CYPUoa57AAuP/FF1yW0jXKcktO/5TfLpB9Gz0VtPvAPii5CVO+/dUaPVKkCBD3VjR6UlBx6qJHZsYUkpr0InzolooQQvlj85oREzr6enE3UMikkE07fVXoVmMzdX9Y/V+dkfyI8SoaMYV6JIvVvHKdX81cm7mVy4lPqH1aWenjXB4HEr2ZpkZ8715l8wY4QYhpAQ0wBV/5pSX/G24OOND9Ud4e7z7q8XrYwwBrAltXX7jFooS5QA+cZ5+2UYFnZb08tUBQYGDpN4qKvstazqgKprORTWlsDUvFMoUdL7t0Jlv3Dkr/EnWv11juqdvwq13AMBxHsM9YGhOHTrWQa8+HCSOMn4Mj00iPuJsgID/2Q==");
    }

    public static String getGclLuxIdSignatureObject() {
        return "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";
    }

    public static GclLuxIdAllCertificates getGclLuxIdAllCertificates() {
        return new GclLuxIdAllCertificates()
                .withRootCertificates(getGclLuxIdRootCertificates())
                .withAuthenticationCertificate(getLuxIdAuthenticationCertificate())
                .withNonRepudiationCertificate(getLuxIdNonRepudiationCertificate());
    }

    public static List<String> getGclLuxIdRootCertificates() {
        return Arrays.asList("MIIF1DCCA7ygAwIBAgIUJWqO2zHYg/8cG+ZbkBmgxalx9HEwDQYJKoZIhvcNAQELBQAwSzELMAkGA1UEBhMCTFUxFjAUBgNVBAoMDUx1eFRydXN0IFMuQS4xJDAiBgNVBAMMG0x1eFRydXN0IFRFU1QgR2xvYmFsIFJvb3QgMjAeFw0xNTAyMjMxNTAyNTRaFw0zNTAyMjMxNTAyNTRaMEsxCzAJBgNVBAYTAkxVMRYwFAYDVQQKDA1MdXhUcnVzdCBTLkEuMSQwIgYDVQQDDBtMdXhUcnVzdCBURVNUIEdsb2JhbCBSb290IDIwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQC1ITLTwiiH691uP56CmA3sMqnJgkUB1JfUfeco3VGRbASAbPBNrC6TpwwFRirka7rPSnelxrAVAcyILUu+jsQbQbgQ1Y7zRa/yHt7TY5wATHzD5yQjc710J0nVEt5UMNcwCdml5y8V+mw2cSDtwef9NlqZDlOMDaljBMOwPttY399mOvhWZo+q91BNm5lu5iLLM48aaYVPKZ61rHlvR/Fu1RNQ5jm1uv+Fd1r7b1m/NnWp9BhKa7Bbw9j7qff+I/iROjPz3c7d0B95mivjAja7y9lvAU5NjX13h74yUQ30itOrtX74MJPzrXVtM78fXHruFcb3MFe9aJ3Oc6MsK9pv4vIhrVIrByfCFHqI+LHEjaqQmIw25pI6oH21WrDspoaPLcE3Tes1Btul4C5OQsuwrwM7tnzzV1eT296ZfIyMBOhxEihDoruSJI/+Ec7P7WXasCZFc/3mOQMimbq16NtgBBlfrGmCI+gv7dc5R7YVAhwkXO8l/W6yRpzBSAR0VQg2GaaNkU41sEmGqjbgp3iOS7Sw3FjBeeHoGuo7Zp4+Oi1YuuAodHqhua8RK3eG5vDym+VBS3kUxaLQJtfeUz7mttPXqyqSOAKvQR6uHiv8Ozh74GCR0eJ1/KsMk6OFMdX690dOovvlZd7MhVb+yfgtJj1BpzMhN7DIzPAGqVjUiwIDAQABo4GvMIGsMA8GA1UdEwEB/wQFMAMBAf8wSQYDVR0gBEIwQDA+BgkrgSsBh2cBAQowMTAvBggrBgEFBQcCARYjaHR0cHM6Ly9yZXBvc2l0b3J5LnRlc3QubHV4dHJ1c3QubHUwDgYDVR0PAQH/BAQDAgEGMB8GA1UdIwQYMBaAFK/SnP9FfTTH4pddDQ8y04hv8Jk4MB0GA1UdDgQWBBSv0pz/RX00x+KXXQ0PMtOIb/CZODANBgkqhkiG9w0BAQsFAAOCAgEAlt28P7WD056cbefvah/Rpq50kujKYyN2htQ5flJSh38/b30R2F/AESP9oRaC1CGm7JiM7JK7goV/ZZ4XLu4FWlo0sx/TY7SUKbd4YRRTJpsS69QJr3c3FXaMx41/Clo1CcHAhw57L4qy+SJ2Q46v6csxwIV1wsCG2gZR6wD0BfBBngsnx9P8ofZSFm3N3NzoMI3t7nhTsBCVtwIgEmZOnFMEOPQ0mtUVnL23jgfw4RwSQTGPjGnerzPQ6wVqBiOX9AIb0/c05nRBcKKsyQvsc5qsHc7Kdz5P7OgdHVNarsXkvyF/r0llgTrvAe0ybl99qsIY+Gv16+5xpkduQJIJf25re0uLRNiS+hs8mRb0dRNnyBd0HuudVIMS+MAkLfFtkoa1EpQUOpDQj9pd1kok/w62078+NlQxEAw0zYbz2JEpac9pBFUcFRanwVZJynecqD3ZH48rUOU+PePNY8JYBaPGm6abZtlCaV3n6NnydXDKIEoVsbylDuPvId2UYQHw9mRKi07h1Km2fKoM0/2BOd6EM7b3O0v1vXSyfuhHCKnoF6tqoFpQOqXAlF4n3uTNa4RKrrUHoqbJpQtkMTcpclo2XyHcrDAQuxEflfB10zggdKY9ggvguTEJoux1fhEbzMX7rKTMx+UsPuj8g6hSVcJkuC2cgcC0PrKV7ypBW9A=",
                "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");
    }

    public static String getLuxIdAuthenticationCertificate() {
        return "MIIFyTCCA7GgAwIBAgIDFvl0MA0GCSqGSIb3DQEBCwUAMFMxCzAJBgNVBAYTAkxVMRYwFAYDVQQKDA1MdXhUcnVzdCBTLkEuMSwwKgYDVQQDDCNMdXhUcnVzdCBURVNUIEdsb2JhbCBRdWFsaWZpZWQgQ0EgMzAeFw0xNzAzMjQxNjM4MDJaFw0yMjA0MjQxNjM4MDJaMIGbMQswCQYDVQQGEwJMVTEkMCIGA1UEAxMbU3BlY2ltZW4tNTIxNiBTcGVjaW1lbi01MjE2MRYwFAYDVQQEEw1TcGVjaW1lbi01MjE2MRYwFAYDVQQqEw1TcGVjaW1lbi01MjE2MR0wGwYDVQQFExQxMTg3MTQ0NjU3MDEwMDAwNzA1ODEXMBUGA1UEDBMOUHJpdmF0ZSBQZXJzb24wggEhMA0GCSqGSIb3DQEBAQUAA4IBDgAwggEJAoIBAObqfilbPv/Xf3wg82MWEuDn3fjuxOu72J1o+ZshQL4aue/7rB7Adst3sS9GW5aCJyT1LORqIsUDNYwPC4SU387KoNpBZ3obZSzKbXeDPcOjbPrl2CMa7i+Mdj7JlRnzyMDW5ELaThJdiqDXZ7FWuV+fXWit+ed6fTbBS6i/xn6SPBdcSOfWwQX6+yEUKlYdKWG/m88L1fT1d3yS0HoU9f/VsNLEp/tIFWNswbAFIRClSvV8BRfB/7SbCa47tVU+jxw6GH6M6HGOIUor3qfbpgO0Ehjbb+hozdQhJTg7ZwRLiF36m/ENxjhLT+CG3n82sVDC4B9GJFZ1eml4VZaGsrcCAwEAAaOCAVwwggFYMG0GCCsGAQUFBwEBBGEwXzAoBggrBgEFBQcwAYYcaHR0cDovL29jc3AudGVzdC5sdXh0cnVzdC5sdTAzBggrBgEFBQcwAoYnaHR0cDovL2NhLnRlc3QubHV4dHJ1c3QubHUvTFRUR1FDQTMuY3J0ME4GA1UdIARHMEUwOQYJK4ErAYdnCgMfMCwwKgYIKwYBBQUHAgEWHmh0dHBzOi8vcmVwb3NpdG9yeS5sdXh0cnVzdC5sdTAIBgYEAI96AQIwGAYIKwYBBQUHAQMEDDAKMAgGBgQAjkYBBDAOBgNVHQ8BAf8EBAMCBaAwHwYDVR0jBBgwFoAUHAqD4NMk1EEpFg1By02tJPOxg7AwOQYDVR0fBDIwMDAuoCygKoYoaHR0cDovL2NybC50ZXN0Lmx1eHRydXN0Lmx1L0xUVEdRQ0EzLmNybDARBgNVHQ4ECgQIRAFF8TVLrPcwDQYJKoZIhvcNAQELBQADggIBADcJLQQihXF8LaYBNFPak9Z8CK/oKY4XIv8QRer50ul8ta5kcmkotMPuWhkM9wMaX6cMj1J5+NIJenjM1Ux1W7AKSYZcOEmwVmeHTx3ISwiGdLx65jTkNEB0+nYngNZx99dpSo+ZTagfneALbGMVvA4TEZkrEQPNZF1HhZONlAAekTz37TyvI+7PYaZiJRjPC5A7AiX1FRDcG7dQlW5bdFaohXLqpyXIDIDB0vKXVvQCtOwiTWONK58ROYG2LJOmQ9R3wujO4SzC6Sr8o2bK6lewrfOVviKjgLtDouD7EDh5tvREQ0F1m+tSzO4IdoQgS7tUKRFYyuxwLlpcgmpvLkM571eF3/bVbuopcw/oi7NfCJqsKjF9r9sWXWR+Ah82wH0u1TMI0aF2ron8S8rmb/LVZuzZy0T0MycP7UU0tiHKkzP166AYG8qVqOcwBenrwd0AvnXXHsyOUDLlQCekx8EyFtM9fHPSLypX1uIX2ie8qUhjvqffs7PPdzhjnUeDD+NUwlbvWlw6Vs8SfSjGQtjyA3PP3UyrsSxYq7En9efqeghezUNmcUYWb9nSSVpamE5OhjudMZOHR5P5f9XmjOd04jgNqOZVBohrW/oB9+ubOi+4bjLUn4kj0N6IaNQqCnszSfj2yh2zGPyQr2Wg6orv8aj9+w+kOHByw25/5Ppg";
    }

    public static String getLuxIdNonRepudiationCertificate() {
        return "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";
    }

    //
    // LuxTrust responses
    //

    public static T1cResponse<GclLuxTrustAllData> getGclLuxTrustAllDataResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclLuxTrustAllData data = getGclLuxTrustAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("activated")) data.setActivated(null);
            if (!filterParams.contains("root-certificates")) data.setRootCertificates(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclLuxTrustAllCertificates> getGclLuxTrustAllCertificatesResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclLuxTrustAllCertificates data = getGclLuxTrustAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificates")) data.setRootCertificates(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<Object> getLuxTrustVerifyPinResponse() {
        return getSuccessResponse(null);
    }

    public static T1cResponse<String> getGclLuxTrustAuthenticationCertificate() {
        return getSuccessResponse(getluxTrustAuthenticationCertificate());
    }

    public static T1cResponse<String> getGclLuxTrustSigningCertificate() {
        return getSuccessResponse(getLuxTrustSigningCertificate());
    }

    public static T1cResponse<List<String>> getGclLuxTrustRootCertificates() {
        return getSuccessResponse(getLuxTrustRootCertificates());
    }

    public static GclLuxTrustAllCertificates getGclLuxTrustAllCertificates() {
        return new GclLuxTrustAllCertificates()
                .withRootCertificates(getLuxTrustRootCertificates())
                .withSigningCertificate(getLuxTrustSigningCertificate())
                .withAuthenticationCertificate(getluxTrustAuthenticationCertificate());
    }

    public static GclLuxTrustAllData getGclLuxTrustAllData() {
        return new GclLuxTrustAllData()
                .withActivated(getLuxTrustActivated())
                .withRootCertificates(getLuxTrustRootCertificates())
                .withSigningCertificate(getLuxTrustSigningCertificate())
                .withAuthenticationCertificate(getluxTrustAuthenticationCertificate());
    }

    public static List<String> getLuxTrustRootCertificates() {
        return Arrays.asList(
                "MIIFwzCCA6ugAwIBAgIUCn6m30tEntpqJIWe5rgV0xZ/u7EwDQYJKoZIhvcNAQELBQAwRjELMAkGA1UEBhMCTFUxFjAUBgNVBAoMDUx1eFRydXN0IFMuQS4xHzAdBgNVBAMMFkx1eFRydXN0IEdsb2JhbCBSb290IDIwHhcNMTUwMzA1MTMyMTU3WhcNMzUwMzA1MTMyMTU3WjBGMQswCQYDVQQGEwJMVTEWMBQGA1UECgwNTHV4VHJ1c3QgUy5BLjEfMB0GA1UEAwwWTHV4VHJ1c3QgR2xvYmFsIFJvb3QgMjCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBANeFl78RmOnwYoNMPIf5U2o3C/IPPIfOb9wmKb3FibrJgz337spbxm1Jc7TJRqMbNBM/wYlFV/TZsfs2ZUv7COJIcRHIbjuend+JZTemhfY7RBi2xjcwYkSSl2l9QjAk5A0MiWtj3sXh306pFGxT4GHO9hcvHTy95iJMHZP1EMShduxq3sVs35a0VkBCwGKSMKEtFZSg0iAGCW5qbeXrt77U8PEVfIvmTroTzEsnXpk8F12PgX8zPU/TPxvsXD/wPEx1bvKm1Z3aLQdjAsZy6ZS8TEmVT4hSyNvoaYL4zDRbIvCGp4m9SAptZoFtyMhk+wHh9OHe2Z7d21vUKpkmFRseTJIpgp7VkoGSQXAZ96Tlk0u8d2cx3Rz9MXANF5kM+Qw5GSoXtTBxVdUPrljhPS80m8+f9niFwpN6cj5mj5wWEWCPnolvZ77gR1o7DJpni89Gxq44o/KnvObWhWszJHAiS8sIm7vI+AIpHb4gDEa/a4ebsypmQjVGbKq6rfmYe+lQVRQxv7HaLe2ArWgk+2mr2HETMOZns4dA/Yl+8kPREd8vZS9kzl8UubG/Mb2HeFpZZYiq/FkySIbWTLkpS5XTdvN3JW1CHDiDTf2jX5t/Lax5Gw5CMZdjpPuKadUiDTSQMC6otOBttpSsvItO13D8xTiOZCXhTTmQzsmHhFhxAgMBAAGjgagwgaUwDwYDVR0TAQH/BAUwAwEB/zBCBgNVHSAEOzA5MDcGByuBKwEBAQowLDAqBggrBgEFBQcCARYeaHR0cHM6Ly9yZXBvc2l0b3J5Lmx1eHRydXN0Lmx1MA4GA1UdDwEB/wQEAwIBBjAfBgNVHSMEGDAWgBT/GCh2+UgFLKGu8SsbK7JT+Et8szAdBgNVHQ4EFgQU/xgodvlIBSyhrvErGyuyU/hLfLMwDQYJKoZIhvcNAQELBQADggIBAGoZFO1uecEsh9QNcH7X9njJCwROxLHOk3D+sFTAMs2ZMGQXvw/l4jP9BzZAcg4atmpZ1gDlaCDdLnINH2pkMSCEfUmmWjfrRcmF9dTHF5kH5ptV5AzoqbTOjFu1EVzPig4N1qx3gf4ynCSecs5U89BvolbW7MM3LGVYvlcAGvI1+ut7MV3CwRI9loGIlonBWVx65n9wNOeD4rHh4bhY79SV5GCc8JaXcozrhAIuZY+kt9J/Z93I055cqqmkoCUUBpvsT34tC38ddfEz2O3OuHVtPlu5mB0xDVbYQw8wkbIEa91WvpWAVWe+2M2D2RjuLg+GLZKecBPs3lHJQ3gCpU3I+V/EkVhGFndadKpAvAefMLmx9xIX3eP/JEAdemrRTxgKqpAd60Ae36EeRJIQmvKN4dFLRp7oRUKX6kWZ8+xm1QL68qZKJKrezrnK+T+Tb/mjuuqlPpmt/f97mfVl7vBZKGfXkJWkE4SphMHozs51k2MavDzq1WQfLSoSOcbDWjLtR5EWDrw4wVDej8oqkDQc7kGUnF4ZLvhFSZl0kbAEb+MEWrGrKqv+x9CWttrhSmQGbmBNvUJO/3jaJMobtNeWOWyu8Q6qp31IiyBMz2TWuJdGsE7RKlY6oJO9r4Ak4Ap+58rVyuiFVdw2KuGUaJPHZnJED4AhMmwlxyOAgwrr",
                "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");
    }

    public static String getLuxTrustSigningCertificate() {
        return "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";
    }

    public static String getluxTrustAuthenticationCertificate() {
        return "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";
    }

    public static Boolean getLuxTrustActivated() {
        return true;
    }

    // TODO clean up the rest of the responses below

    public static T1cResponse<String> getPublicKeyResponseDer() {
        return new T1cResponse<String>()
                .withSuccess(true)
                .withData("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjC4a5oOpZr7Yci7WEiLbZsOEk48TkjtvANpUkRMtwNyPVvhmaZib9qKx2JQRjg74cdpqvpCBQZ2w/7/30G1ptrB654PkDK0F3Z2AZJp0LEZoCaYQ+8ubWSbpAvM3dlUl9MeDP5O4gTuEaYatqrBGpSZwVc9xjCs/OKYKgIXXjV7tILogAWWo4MmxSfyr/c7fe1CUGN7uTuiGtR5djmk369SPGc1vUNuqxh2fC9Nsmp0mtB23jxi0D0bpi5Dn7G4Jif6DX9DiF2ktXpM9dmo93N6BOX3tbstw6I0KFyXpvjpVtAO8LYI/d7QlgNOp0fcQj5DUCH8UIY3x1nTnoPeC5QIDAQAB");
    }

    public static T1cResponse<String> getPublicKeyResponsePem() {
        return new T1cResponse<String>()
                .withSuccess(true)
                .withData("LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUFqQzRhNW9PcFpyN1ljaTdXRWlMYgpac09FazQ4VGtqdHZBTnBVa1JNdHdOeVBWdmhtYVppYjlxS3gySlFSamc3NGNkcHF2cENCUVoydy83LzMwRzFwCnRyQjY1NFBrREswRjNaMkFaSnAwTEVab0NhWVErOHViV1NicEF2TTNkbFVsOU1lRFA1TzRnVHVFYVlhdHFyQkcKcFNad1ZjOXhqQ3MvT0tZS2dJWFhqVjd0SUxvZ0FXV280TW14U2Z5ci9jN2ZlMUNVR043dVR1aUd0UjVkam1rMwo2OVNQR2MxdlVOdXF4aDJmQzlOc21wMG10QjIzanhpMEQwYnBpNURuN0c0SmlmNkRYOURpRjJrdFhwTTlkbW85CjNONkJPWDN0YnN0dzZJMEtGeVhwdmpwVnRBTzhMWUkvZDdRbGdOT3AwZmNRajVEVUNIOFVJWTN4MW5Ubm9QZUMKNVFJREFRQUIKLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tCg==");
    }

    public static DsDevice getDsDevice() {
        return new DsDevice().withActivated(true).withCoreVersion("1.4.0").withManaged(false).withUuid("B7289D3AEB22D233");
    }

    public static DsToken getToken() {
        return new DsToken().withToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwOi8vVDFDLURTIiwiYXVkIjoiR0NMIiwiZXhwIjoxNTEwMjg2MDIwLCJqdGkiOiItVE9MMWNHZHVLTl9LTGFGLVZSSkJ3IiwiaWF0IjoxNTEwMjgyNDIwLCJuYmYiOjE1MTAyODIzMDAsInN1YiI6IkdDTC1JRCIsImFjdGl2YXRpb24iOnRydWUsInBsdWdpbnMiOlsicGx1Z2luMSIsInBsdWdpbjIiLCJwbHVnaW4zIl19.JncS0rdgQ19r-lj5Yvtkw3evx3iufPX19WhoTrOW0H_Qu6L7HagkOZlszRMEivm2trYldvOivtzqNJFuz0XODcJE6rKF3DOlkyUkE-PHgPd-NFd8ME21FHCKLswkcJw4xPxAhreat6ybN3BuH1cdAMgrqFkR-avsKQOLYmW2LwSsmApdv7HBEo6YX3IxfX7HbzSI71D6fxEhqIsvg8u5ZrJGAjdFPEaIeo58yDDjZ-zwa7tKpg9w5Jt5Ubl3VePyLOE9zl1CycYov-qod9BC6pOuYptYrayY8pzGKSXrYdpEMoqRAXGCfrpIAxVUuR0prcTpHKeUU6lFPUzdanw8DQ");
    }

    public static DsDownloadPath getDownloadPath() {
        return new DsDownloadPath().withPath("/trust1team/gclds-file/v1/installer.dmg");
    }

    public static T1cResponse<String> getCertificateResponse() {
        return new T1cResponse<String>().withSuccess(true).withData("MIIFjjCCA3agAwIBAgIIOyEC3pZbHakwDQYJKoZIhvcNAQEFBQAwKDELMAkGA1UEBhMCQkUxGTAXBgNVBAMTEEJlbGdpdW0gUm9vdCBDQTMwHhcNMTMwNjI2MTIwMDAwWhcNMjgwMTI4MTIwMDAwWjAoMQswCQYDVQQGEwJCRTEZMBcGA1UEAxMQQmVsZ2l1bSBSb290IENBMzCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAKjyAZ2Lg8kHoIX7JLc3BeZ1Tzy9MEv7Bnr59xcJezc/xJJdO4V3bwMltKFfNvqsQ5H/GQADFJ0GmTLLPDI5AoeUjBubRZ9hwruUuQ11+vhtoVhuEuZUxofEIU2yJtiSOONwpo/GIb9C4YZ5h+7ltDpC3MvsFyyordpzgwqSHvFwTCmls5SpU05UbF7ZVPcfVf24A5IgHLpZTgQfAvnzPlm++eJY+sNoNzTBoe6iZphmPbxuPNcJ6slV8qMQQk50/g+KmoPpHX4AvoTr4/7TMTvuK8jS1dEn+fdVKdx9qo9ZZRHFW/TXEn5SrNUu99xhzlE/WBurrVwFoKCWCjmO0CnekJlw0NTr3HBTG5D4AiDjNFUYaIcGJk/ha9rzHzY+WpGdoFZxhbP83ZGeoqkgBr8UzfOFCY8cyUN2db6hpIaK6Nuoho6QWnn+TSNh5Hjui5miqpGxS73gYlT2Qww16h8gFTJQ49fiS+QHlwRw5cqFuqfFLE3nFFF9KIamS4TSe7T4dNGY2VbHzpaGVT4wy+fl7gWsfaUkvhM4b00DzgDiJ9BHiKytNLmzoa3Sneij/CKur0dJ5OdMiAqUpSd0Oe8pdIbmQm1oP5cjckiQjxx7+vSxWtacpGowWK8+7oEsYc+7fLt3GD6q/O5Xi440Pd/sFJmfqRf3C1PPMdBqXcwjAgMBAAGjgbswgbgwDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wQgYDVR0gBDswOTA3BgVgOAoBATAuMCwGCCsGAQUFBwIBFiBodHRwOi8vcmVwb3NpdG9yeS5laWQuYmVsZ2l1bS5iZTAdBgNVHQ4EFgQUuLxsAI9bGYWdJQGc8BncQI7QOCswEQYJYIZIAYb4QgEBBAQDAgAHMB8GA1UdIwQYMBaAFLi8bACPWxmFnSUBnPAZ3ECO0DgrMA0GCSqGSIb3DQEBBQUAA4ICAQBFYjv/mKX+VcyxEacckgx4L8XvFkIFPXzjEnDnAtCCkROU/k5n1jjVK+ODOn+Q4kJg6Nd7K47+zTXcrSe1tB2gVMsyaCN9scy4phLX1qT48sThCjUtooxfIoRycpdlf14HcUPCYlASTCapZU0MnAbzfpzxm49Ik/A2JWxAhxXVRHwOu3TMGiQ4W/VyVawxjwQMO8TneBDombmkXsI9bI0OxWUh2A5dKlqu0sYvE0dz8xDxr9ZkmZqYcPIKizCZlaP1ZsSlCi5S31gn3EUP+fd21q6ZXgU+50/qgoh/0UUaHRpedPQBES/FYc2IQZ2XjhmeTwM+9Lk7tnzHeHp3dgCoOfceyPUaVkWiXMWcNAvvkDVELvXfJpRxwcRfS5Ks5oafOfj81RzGUbmpwl2usOeCRwdWE8gPvbfWNQQC8MJquDl5HdeuzUesTXUqXeEkyAOo6YnF3g0qGcLI9NXusji1egRUZ7B4XCvG52lTB7Wgd/wVFzS3f4mAmYTGJXH+N/lrBBGKuTJ5XncJaliFUKxGP6VmNyaaLUF5IlTqC9CGHPLSXOgDokt2G9pNwFm2t7AcpwAmegkMNpgcgTd+qk2yljEaT8wf953jUAFedbpN3tX/3i+uvHOOmWjQOxJg2lVKkC+bkWa2FrTBDdrlEWVaLrY+M+xeIctrC0WnP7u4xg==");
    }

    public static T1cResponse<String> getSignedHashResponse() {
        return new T1cResponse<String>().withSuccess(true).withData("mpzLEPr4Q+PNNZ4IhbvzhcRe5Q+pW7wPliKT8UIu1OFAspuFYQmE466O6weh9j4M1VJsHnQ8VOl42E/evMRGpUJHQuXMraQhK9hGvn+Xvr68aNF1G3LuZjTMH+Je9iWP/5HoJLru/vizLbrtcyaP5VJCDpfbcfSoRr0+QxOz7eT1XWpu1LLPsHquKKALCbbVLrbEzjMwZRQOPHSeAXDz72U8HfxzcOf2Iqjacw8ValIIXOma5BolLEPSE6e+27wgzXXqXNliqLn9PeThTVoCQS3vXtinFtZLZ6A5DinP9jZGCdmWHnw9x0V3phMtzWI+1w4r9+7DzliJqQH8LgnVXA==");
    }

    public static T1cResponse<GclDnieInfo> getDnieInfoResponse() {
        return new T1cResponse<GclDnieInfo>().withSuccess(true)
                .withData(new GclDnieInfo()
                        .withFirstLastName("Doe")
                        .withFirstName("John")
                        .withSecondLastName("Doe")
                        .withIdesp("IDesp")
                        .withNumber("1234")
                        .withSerialNumber("5678"));
    }

    public static T1cResponse<GclDnieAllData> getDnieAllDataResponse() {
        String cert = getCertificateResponse().getData();
        return new T1cResponse<GclDnieAllData>().withSuccess(true)
                .withData(new GclDnieAllData()
                        .withIntermediateCertificate(cert)
                        .withSigningCertificate(cert)
                        .withInfo(getDnieInfoResponse().getData())
                        .withAuthenticationCertificate(cert));
    }

    public static T1cResponse<GclDnieAllCertificates> getDnieAllCertificatesResponse() {
        String cert = getCertificateResponse().getData();
        return new T1cResponse<GclDnieAllCertificates>().withSuccess(true)
                .withData(new GclDnieAllCertificates()
                        .withIntermediateCertificate(cert)
                        .withSigningCertificate(cert)
                        .withAuthenticationCertificate(cert));
    }

    public static T1cResponse<GclPtIdAllCertificates> getPtIdAllCertificatesResponse() {
        String cert = getCertificateResponse().getData();
        return new T1cResponse<GclPtIdAllCertificates>().withSuccess(true)
                .withData(new GclPtIdAllCertificates()
                        .withRootCertificate(cert)
                        .withAuthenticationCertificate(cert)
                        .withNonRepudiationCertificate(cert)
                        .withRootAuthenticationCertificate(cert)
                        .withRootNonRepudiationCertificate(cert));
    }

    public static T1cResponse<List<GclEmvApplication>> getEmvApplicationResponse() {
        GclEmvApplication app = new GclEmvApplication().withAid("123").withPriority(0);
        return new T1cResponse<List<GclEmvApplication>>().withSuccess(true)
                .withData(Arrays.asList(app, app));
    }

    public static T1cResponse<GclEmvApplicationData> getEmvAppDataResponse() {
        GclEmvApplicationData appData = new GclEmvApplicationData().withCountry("Belgium").withCountryCode("BE").withEffectiveDate("1/1/2000").withExpirationDate("1/1/2100").withLanguage("FR").withName("name").withPan("pan");
        return new T1cResponse<GclEmvApplicationData>().withSuccess(true).withData(appData);
    }

    public static T1cResponse<GclEmvAllData> getEmvAllDataResponse() {
        GclEmvAllData data = new GclEmvAllData().withApplicationData(getEmvAppDataResponse().getData()).withApplications(getEmvApplicationResponse().getData());
        return new T1cResponse<GclEmvAllData>().withSuccess(true).withData(data);
    }

    public static T1cResponse<GclEmvPublicKeyCertificate> getEmvCertResponse(GclEmvAidRequest request) {
        GclEmvPublicKeyCertificate cert = new GclEmvPublicKeyCertificate().withData("data").withExponent("exponent").withRemainder("remainder");
        return getSuccessResponse(cert);
    }

    public static T1cResponse<Boolean> getBooleanResponse() {
        return getSuccessResponse(true);
    }

    public static T1cResponse<GclMobibCardIssuing> getMobibCardIssuingResponse() {
        String json = "{\n" +
                "\"card_expiration_date\": \"2016-01-31\",\n" +
                "\"card_holder_birth_date\": \"1964-07-23\",\n" +
                "\"card_holder_end_date\": \"2016-01-31\",\n" +
                "\"card_holder_id\": \"6060575401800002365\",\n" +
                "\"card_holder_name\": \"MIAO- ERH WANG LIU \",\n" +
                "\"card_holder_start_date\": \"2012-02-11\",\n" +
                "\"card_revalidation_date\": \"2012-02-10\",\n" +
                "\"card_type\": 1,\n" +
                "\"company_id\": 18,\n" +
                "\"gender\": 1,\n" +
                "\"language\": 2,\n" +
                "\"version\": 1\n" +
                "}";
        return getSuccessResponse(GSON.fromJson(json, GclMobibCardIssuing.class));
    }

    public static T1cResponse<List<GclMobibContract>> getMobibContractResponse() {
        String json = "{\n" +
                "\"authenticator_kvc\": 17,\n" +
                "\"authenticator_value\": 587,\n" +
                "\"journey_interchanges_allowed\": false, \n" +
                "\"passengers_max\": 7,\n" +
                "\"period_journeys\": {\n" +
                "\"max_number_of_trips\": 13,\n" +
                "\"period\": 2\n" +
                "},\n" +
                "\"price_amount\": 500,\n" +
                "\"provider\": 1,\n" +
                "\"restrict_code\": 5,\n" +
                "\"restrict_time\": 2,\n" +
                "\"sale_date\": \"2014-03-06\",\n" +
                "\"sale_sam_count\": 15,\n" +
                "\"sale_sam_id\": 25,\n" +
                "\"spatials\": [\n" +
                "{\n" +
                "\"type\": 7\n" +
                "}\n" +
                "],\n" +
                "\"tariff\": {\n" +
                "\"counter\": {\n" +
                "\"time\": \"2017-02-21T06:20:00\",\n" +
                "\"type\": 4\n" +
                "},\n" +
                "\"multimodal\": true,\n" +
                "\"nameref\": 231\n" +
                "},\n" +
                "\"validity_duration\": {\n" +
                "\"unit\": 2,\n" +
                "\"value\": 7\n" +
                "},\n" +
                "\"validity_start_date\": \"2014-03-06\",\n" +
                "\"vehicle_class_allowed\": 1,\n" +
                "\"version\": 4\n" +
                "}";
        GclMobibContract contract = GSON.fromJson(json, GclMobibContract.class);
        List<GclMobibContract> contracts = Arrays.asList(contract, contract);
        return getSuccessResponse(contracts);
    }

    public static T1cResponse<GclMobibAllData> getMobibAllDataResponse() {
        String json = "{\n" +
                "\"active\": true,\n" +
                "\"card-issuing\": {\n" +
                "\"card_expiration_date\": \"2016-01-31\",\n" +
                "\"card_holder_birth_date\": \"1964-07-23\",\n" +
                "\"card_holder_end_date\": \"2016-01-31\",\n" +
                "\"card_holder_id\": \"6060575401800002365\",\n" +
                "\"card_holder_name\": \"MIAO- ERH WANG LIU \",\n" +
                "\"card_holder_start_date\": \"2012-02-11\",\n" +
                "\"card_revalidation_date\": \"2012-02-10\",\n" +
                "\"card_type\": 1,\n" +
                "\"company_id\": 18,\n" +
                "\"gender\": 1,\n" +
                "\"language\": 2,\n" +
                "\"version\": 1\n" +
                "},\n" +
                "\"contracts\": [\n" +
                "{\n" +
                "\"authenticator_kvc\": 17,\n" +
                "\"authenticator_value\": 587,\n" +
                "\"journey_interchanges_allowed\": false,\n" +
                "\"passengers_max\": 7,\n" +
                "\"period_journeys\": {\n" +
                "\"max_number_of_trips\": 13,\n" +
                "\"period\": 2\n" +
                "},\n" +
                "\"price_amount\": 500,\n" +
                "\"provider\": 1,\n" +
                "\"restrict_code\": 5,\n" +
                "\"restrict_time\": 2,\n" +
                "\"sale_date\": \"2014-03-06\",\n" +
                "\"sale_sam_count\": 15,\n" +
                "\"sale_sam_id\": 25,\n" +
                "\"spatials\": [\n" +
                "{\n" +
                "\"type\": 7\n" +
                "}\n" +
                "],\n" +
                "\"tariff\": {\n" +
                "\"counter\": {\n" +
                "\"time\": \"2017-02-21T06:20:00\",\n" +
                "\"type\": 4\n" +
                "},\n" +
                "\"multimodal\": true,\n" +
                "\"nameref\": 231\n" +
                "},\n" +
                "\"validity_duration\": {\n" +
                "\"unit\": 2,\n" +
                "\"value\": 7\n" +
                "},\n" +
                "\"validity_start_date\": \"2014-03-06\",\n" +
                "\"vehicle_class_allowed\": 1,\n" +
                "\"version\": 4\n" +
                "},\n" +
                "{\n" +
                "\"authenticator_kvc\": 18,\n" +
                "\"authenticator_value\": 588,\n" +
                "\"journey_interchanges_allowed\": false,\n" +
                "\"passengers_max\": 7,\n" +
                "\"price_amount\": 1000,\n" +
                "\"provider\": 1,\n" +
                "\"sale_date\": \"2017-02-06\",\n" +
                "\"sale_sam_count\": 1,\n" +
                "\"sale_sam_id\": 2,\n" +
                "\"spatials\": [\n" +
                "{\n" +
                "\"type\": 0\n" +
                "}\n" +
                "],\n" +
                "\"tariff\": {\n" +
                "\"counter\": {\n" +
                "\"journeys\": \"4\",\n" +
                "\"type\": 5\n" +
                "},\n" +
                "\"multimodal\": true,\n" +
                "\"nameref\": 2\n" +
                "},\n" +
                "\"validity_start_date\": \"2017-02-06\",\n" +
                "\"vehicle_class_allowed\": 1,\n" +
                "\"version\": 4\n" +
                "}\n" +
                "\n" +
                "],\n" +
                "\"picture\": \"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==\"\n" +
                "}";
        return getSuccessResponse(GSON.fromJson(json, GclMobibAllData.class));
    }

    public static T1cResponse<GclOcraAllData> getGclOcraAllDataResponse() {
        return getSuccessResponse(new GclOcraAllData().withCounter("Data Counter"));
    }

    public static T1cResponse<String> getStringResponse() {
        return getSuccessResponse("This is a String response");
    }

    public static T1cResponse<List<String>> getStringListResponse() {
        return getSuccessResponse(Arrays.asList("This is a String response", "This is a second String response"));
    }

    public static T1cResponse<GclSafeNetInfo> getSafeNetInfoResponse() {
        String json = "{\n" +
                "\"cryptoki_version\": \"2.20\",\n" +
                "\"manufacturer_id\": \"SafeNet, Inc.\",\n" +
                "\"flags\": 7,\n" +
                "\"library_description\": \"SafeNet eToken PKCS#11\",\n" +
                "\"library_version\": \"9.1\"\n" +
                "}";
        return getSuccessResponse(GSON.fromJson(json, GclSafeNetInfo.class));
    }

    public static T1cResponse<List<GclSafeNetSlot>> getSafeNetSlotsResponse(Boolean tokenPresent) {
        List<GclSafeNetSlot> slots = new ArrayList<>();
        String jsonWithToken = "{\n" +
                "\"slot_id\": 0,\n" +
                "\"description\": \"SafeNet eToken 5100\",\n" +
                "\"flags\": 7,\n" +
                "\"hardware_version\": \"2.0\",\n" +
                "\"firmware_version\": \"0.0\"\n" +
                "}";
        if (tokenPresent == null || !tokenPresent) {
            String jsonWithoutToken = "{\n" +
                    "\"slot_id\": 1,\n" +
                    "\"description\": \"\",\n" +
                    "\"flags\": 2,\n" +
                    "\"hardware_version\": \"2.0\",\n" +
                    "\"firmware_version\": \"0.0\"\n" +
                    "}";
            slots.add(GSON.fromJson(jsonWithoutToken, GclSafeNetSlot.class));
        }
        slots.add(GSON.fromJson(jsonWithToken, GclSafeNetSlot.class));
        return getSuccessResponse(slots);
    }

    private static <T> T1cResponse<T> getSuccessResponse(T data) {
        return new T1cResponse<T>().withSuccess(true).withData(data);
    }

    private static T1cCertificate getT1cCertificate() {
        return new T1cCertificate().withBase64(getCertificateResponse().getData());
    }

    private static List<String> splitFilterParams(String filter) {
        if (StringUtils.isEmpty(filter)) {
            return Collections.emptyList();
        }
        return Arrays.asList(filter.split(","));
    }

    public static MockResponse getMockResponseForResource(int httpStatusCode, String resourceFilename) {
        try {
            return new MockResponse().setResponseCode(httpStatusCode).setBody(IOUtils.toString(MockResponseFactory.class.getResourceAsStream(RESPONSE_RESOURCE_PATH + resourceFilename + JSON_EXTENSION)));
        } catch (IOException ex) {
            log.warn("Failed to retrieve JSON response {}: {}", resourceFilename, ex);
            return new MockResponse();
        }
    }
}