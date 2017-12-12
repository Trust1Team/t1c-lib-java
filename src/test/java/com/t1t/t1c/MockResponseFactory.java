package com.t1t.t1c;

import com.google.gson.Gson;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.remoteloading.GclRemoteLoadingCcidFeature;
import com.t1t.t1c.containers.remoteloading.GclRemoteLoadingCommand;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAddress;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdAllData;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdRn;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieAllData;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieInfo;
import com.t1t.t1c.containers.smartcards.eid.lux.*;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdAddress;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdAllData;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdData;
import com.t1t.t1c.containers.smartcards.emv.*;
import com.t1t.t1c.containers.smartcards.mobib.*;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraAllData;
import com.t1t.t1c.containers.smartcards.piv.GclPivAllCertificates;
import com.t1t.t1c.containers.smartcards.piv.GclPivAllData;
import com.t1t.t1c.containers.smartcards.piv.GclPivFacialImage;
import com.t1t.t1c.containers.smartcards.piv.GclPivPrintedInformation;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetInfo;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetSlot;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraAllCertificates;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraAllData;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraAppletInfo;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustAllCertificates;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustAllData;
import com.t1t.t1c.containers.smartcards.pki.oberthur.GclOberthurAllCertificates;
import com.t1t.t1c.containers.smartcards.pki.oberthur.GclOberthurAllData;
import com.t1t.t1c.core.GclCard;
import com.t1t.t1c.core.GclContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclStatus;
import com.t1t.t1c.ds.DsDevice;
import com.t1t.t1c.ds.DsDownloadPath;
import com.t1t.t1c.ds.DsToken;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
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
    public static final String REMOTE_LOADING_READER_ID = "57a3e2e71c48ce14";
    private static final Logger log = LoggerFactory.getLogger(MockResponseFactory.class);
    private static final String JSON_EXTENSION = ".json";
    private static final String RESPONSE_RESOURCE_PATH = "/responses/";
    private static final Gson GSON = new Gson();

    private MockResponseFactory() {
    }

    public static <T> T1cResponse<T> getSuccessResponseWithoutData() {
        return new T1cResponse<T>()
                .withSuccess(true)
                .withData(null);
    }

    private static <T> T1cResponse<T> getSuccessResponse(T data) {
        return new T1cResponse<T>().withSuccess(true).withData(data);
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
        } else if (onlyCardsInserted) {
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
    // Generic interface responses
    //

    public static T1cResponse<String> getSignedHashResponse(String pin) throws RestException {
        if (pin != null && !"1111".equals(pin)) {
            throw new RestException("sign failed", 412, "https://localhost:10443/v1/plugins/pluginid/readerid/method", "{\n" +
                    "  \"code\": 103,\n" +
                    "  \"description\": \"Wrong pin, 2 tries remaining\",\n" +
                    "  \"success\": false\n" +
                    "}");
        }
        return new T1cResponse<String>().withSuccess(true).withData("mpzLEPr4Q+PNNZ4IhbvzhcRe5Q+pW7wPliKT8UIu1OFAspuFYQmE466O6weh9j4M1VJsHnQ8VOl42E/evMRGpUJHQuXMraQhK9hGvn+Xvr68aNF1G3LuZjTMH+Je9iWP/5HoJLru/vizLbrtcyaP5VJCDpfbcfSoRr0+QxOz7eT1XWpu1LLPsHquKKALCbbVLrbEzjMwZRQOPHSeAXDz72U8HfxzcOf2Iqjacw8ValIIXOma5BolLEPSE6e+27wgzXXqXNliqLn9PeThTVoCQS3vXtinFtZLZ6A5DinP9jZGCdmWHnw9x0V3phMtzWI+1w4r9+7DzliJqQH8LgnVXA==");
    }

    public static T1cResponse<Object> verifyPin(String pin) throws RestException {
        if (!"1111".equals(pin)) {
            throw new RestException("PIN verification failed", 412, "https://localhost:10443/v1/plugins/pluginid/readerid/method", "{\n" +
                    "  \"code\": 103,\n" +
                    "  \"description\": \"Wrong pin, 2 tries remaining\",\n" +
                    "  \"success\": false\n" +
                    "}");
        }
        return getSuccessResponseWithoutData();
    }

    //
    // BE ID responses
    //

    public static T1cResponse<GclBeIdAllData> getGclBeIdAllDataResponse(String filter) throws RestException {
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

    public static T1cResponse<GclBeIdAllCertificates> getGclBeIdAllCertificatesResponse(String filter) throws RestException {
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

    public static GclBeIdAllData getGclBeIdAllData() {
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

    public static GclBeIdAllCertificates getGclBeIdAllCertificates() {
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
        return "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";
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

    public static T1cResponse<GclLuxIdAllData> getLuxIdAllDataResponse(String filter) throws RestException {
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

    public static T1cResponse<GclLuxIdAllCertificates> getLuxIdAllCertificatesResponse(String filter) throws RestException {
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
                .withRawData("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");
    }

    public static GclLuxIdSignatureImage getGclLuxIdSignatureImage() {
        return new GclLuxIdSignatureImage()
                .withImage("/0//UQAvAAAAAADwAAABQAAAAAAAAAAAAAAA8AAA....uq9eK159DRO61Ufrf9ICA/9k=")
                .withRawData("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");
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

    public static T1cResponse<GclLuxTrustAllData> getGclLuxTrustAllDataResponse(String filter) throws RestException {
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

    public static T1cResponse<GclLuxTrustAllCertificates> getGclLuxTrustAllCertificatesResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclLuxTrustAllCertificates data = getGclLuxTrustAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificates")) data.setRootCertificates(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
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
                "MIIGcjCCBFqgAwIBAgIUQT3qGijCJThFVY4Efz4qi1ubrq4wDQYJKoZIhvcNAQELBQAwRjELMAkGA1UEBhMCTFUxFjAUBgNVBAoMDUx1eFRydXN0IFMuQS4xHzAdBgNVBAMMFkx1eFRydXN0IEdsb2JhbCBSb290IDIwHhcNMTUwMzA2MTQxMjE1WhcNMzUwMzA1MTMyMTU3WjBOMQswCQYDVQQGEwJMVTEWMBQGA1UECgwNTHV4VHJ1c3QgUy5BLjEnMCUGA1UEAwweTHV4VHJ1c3QgR2xvYmFsIFF1YWxpZmllZCBDQSAzMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAuZ5iXSmFbP80gWb0kieYsImcyIo3QYg+XA3NlwH6QtI0PgZEG9dSo8pM7VMIzE5zq8tgJ50HnPdYflvfhkEKvAW2NuNX6hi/6HK4Nye+kB+INjpfAHmLft3GT95e+frk/t7hJNorK44xzqfWZKLNGysEHIriddcePWOk3J/VMc9CsSemeZbmeZW1/xXeqolMS7JIDZ3+0DgVCYsKIK+b3sAQ8iqXbQlQyvymG6QyoQoJbuEP23iawRMWKNWk+sjzOkPAAQDtgEEVdggzzudLSM04C5CjeLlLYuXgljler9bKRk9wW8nkareLZsn9uCDihGXGyC5m9jseGY1KAnlV8usLjBFAiW5OCnzcOg+CPsVucoRhS6uvXcu7VtHRGo5yLysJVv7sj6cx5lMvQKAMLviVi3kphZKYfqVLAVFJpXTpunY2GayVGf/uOpzNoiSRpcxxYjmAlPKNeTgXVl5Mc0zojgT/MZTGFN7ov7n01yodN6OhfTADacvaKfj2C2CwdCJvMqvlUuCKrvuXbdZrtRm3BZXrghGhuQmG0Tir7VVCI0WZjVjyHs2rpUcCQ6+D1WymKhzp0mrXdaFzYRce7FrEk69JWzWVp/9/GKnnb0//camavEaI4V64MVxYAir5AL/j7d4JIOqhPPU14ajxmC6dEH84guVs0Lo/dwVTUzsCAwEAAaOCAU4wggFKMBIGA1UdEwEB/wQIMAYBAf8CAQAwQwYDVR0gBDwwOjA4BggrgSsBAQEKAzAsMCoGCCsGAQUFBwIBFh5odHRwczovL3JlcG9zaXRvcnkubHV4dHJ1c3QubHUwagYIKwYBBQUHAQEEXjBcMCsGCCsGAQUFBzABhh9odHRwOi8vbHRncm9vdC5vY3NwLmx1eHRydXN0Lmx1MC0GCCsGAQUFBzAChiFodHRwOi8vY2EubHV4dHJ1c3QubHUvTFRHUkNBMi5jcnQwDgYDVR0PAQH/BAQDAgEGMB8GA1UdIwQYMBaAFP8YKHb5SAUsoa7xKxsrslP4S3yzMDMGA1UdHwQsMCowKKAmoCSGImh0dHA6Ly9jcmwubHV4dHJ1c3QubHUvTFRHUkNBMi5jcmwwHQYDVR0OBBYEFGOPwosDsauO2FNHlh2ZqH32rKh1MA0GCSqGSIb3DQEBCwUAA4ICAQADB6M/edbOO9iJCOnVxayJ1NBk08/BVKlHwe7HBYAzT6Kmo3TbMUwOpcGI2e/NBCR3F4wTzXOVvFmvdBl7sdS6uMSLBTrav+5LChcFDBQj26X5VQDcXkA8b/u6J4Ve7CwoSesYg9H0fsJ3v12QrmGUUao9gbamKP1TFriO+XiIaDLYectruusRktIke9qy8MCpNSarZqr3oD3c/+N5D3lDlGpaz1IL8TpbubFEQHPCr6JiwR+qSqGRfxv8vIvOOAVxe7np5QhtwmCkXdMOPQ/XOOuEA06bez+zHkASX64at7dXru+4JUEbpijjMA+1jbFZr20OeBIQZL7oEst+FF8lFuvmucC9TS9QnlF28WJExvpIknjS7LhFMGXB9w380q38ZOuKjPZpoztYeyUpf8gxzV7fE5Q1okhnsDZ+12vBzBruzJcwtNuXyLyIh3fVN0LunVd+NP2kGjB2t9WD2Y0CaKxWx8snDdrSbAi46TpNoe04eroWgZOvdN0hEmf2d8tYBSJ/XZekU9sCAww5vxHnXJi6CZHhjt8f1mMhyE2gBvmpk4CFetViO2sG0n/nsxCQNpnclsax/eJuXmGiZ3OPCIRijI5gy3pLRgnbgLyktWoOkmT/gxtWDLfVZwEt52JL8d550KIgttyRqX81LJWGSDdpnzeRVQEnzAt6+RebAQ==");
    }

    public static String getLuxTrustSigningCertificate() {
        return "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";
    }

    public static String getluxTrustAuthenticationCertificate() {
        return "MIIGajCCBFKgAwIBAgIDF92jMA0GCSqGSIb3DQEBCwUAME4xCzAJBgNVBAYTAkxVMRYwFAYDVQQKDA1MdXhUcnVzdCBTLkEuMScwJQYDVQQDDB5MdXhUcnVzdCBHbG9iYWwgUXVhbGlmaWVkIENBIDMwHhcNMTYwMzE1MTAyNDIwWhcNMTkwMzE1MTAyNDIwWjB7MQswCQYDVQQGEwJMVTEUMBIGA1UEAxMLU1BBUkUgTjM1MDQxDjAMBgNVBAQTBU4zNTA0MQ4wDAYDVQQqEwVTUEFSRTEdMBsGA1UEBRMUMTAxNjcyNTY1MzAwNzY4Njc1NzIxFzAVBgNVBAwTDlByaXZhdGUgUGVyc29uMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArtl0QBpFlvBB+21RPzn6xX8baxGoBKAL7GN45WjGzcD0JmSxlypUO2pZNg8wVzq2bxTI9rukrQxm+W2MkYyEmtjn9M27k+ySNv4xQlI+KsgxhH9AIU2Ve7F1aNvCHPR1gpE62bRZkeOocDKbDesjzMXsZBkrHwH+wAEvXno80C48zXEuzcuBEQVcBM7acFuR7a2nLMC+wxb3+eR/fR9VR+2af7WUPsfPsnoHyHz8IAg9g1X37m9GmUl8zijwTKmaFcHn10PumpYe9Du08FujYuV+WmMQUowZdjhrEylUUJ34n5fSuXRaCl9haAU6CwrIWAVx/2z7z554rvy+q2IukwIDAQABo4ICIjCCAh4wDAYDVR0TAQH/BAIwADBmBggrBgEFBQcBAQRaMFgwJwYIKwYBBQUHMAGGG2h0dHA6Ly9xY2Eub2NzcC5sdXh0cnVzdC5sdTAtBggrBgEFBQcwAoYhaHR0cDovL2NhLmx1eHRydXN0Lmx1L0xUR1FDQTMuY3J0MIIBLgYDVR0gBIIBJTCCASEwggETBggrgSsBAQoDCDCCAQUwgdYGCCsGAQUFBwICMIHJGoHGTHV4VHJ1c3QgSU5URUdSQVRJT04gQ0VSVElGSUNBVEUgb24gU1NDRCBjb21wbGlhbnQgd2l0aCBFVFNJIFRTIDEwMiAwNDIgTENQKyBjZXJ0aWZpY2F0ZSBwb2xpY3kuIEtleSBHZW5lcmF0aW9uIGJ5IENTUC4gU29sZSBBdXRob3Jpc2VkIFVzYWdlOiBBdXRoZW50aWNhdGlvbiBhbmQgRW5jcnlwdGlvbiBmb3IgSW50ZWdyYXRpb24gUHVycG9zZXMuMCoGCCsGAQUFBwIBFh5odHRwczovL3JlcG9zaXRvcnkubHV4dHJ1c3QubHUwCAYGBACPegEDMAsGA1UdDwQEAwIEsDAfBgNVHSMEGDAWgBRjj8KLA7GrjthTR5Ydmah99qyodTAzBgNVHR8ELDAqMCigJqAkhiJodHRwOi8vY3JsLmx1eHRydXN0Lmx1L0xUR1FDQTMuY3JsMBEGA1UdDgQKBAhJ22kjtmtd4jANBgkqhkiG9w0BAQsFAAOCAgEAgK0PUbmk3ATVf6tuhiI5tXlXNTq+mB8bt5jTrtcwwopMk3k1MQNU1gTTwgvuiDd1J8lgK2LVPxtmVc/+xe857NgxeH6/dvLAGZbIQND88/ayCLT/iyAaxQxWf0J1MjHRR6ijvNRtq8rdj0WCmBRZ14y71jmIF0IjtSZSoeFNTvqswmOvM3vdHTB6KzJ9siBNPMCOifmvSZhGwVo+TT3Zg5+y8nZpbQXC2SrgAyyP83bUmvkLX6QdlMdJe6LLeYdzgDxHBOV+1iqBPlUTxyNPm53te/FlKhwYzfbNPwJdqn1xH/9WVHke6SbHChW05TAZdNtteRQvjLw0OUzUB4RTDhu/RAYBzB9mvPdZFiEN9ufRe0eCl/aRQzHatm22cHldKBCjqdJnne2ErcJvjQ0kiV3fOsKEowS7LZXQs3TyloM8dTlW25VAZQqxIT6QIPX2/p6AiIYMJhBLxuOCsF4KIkfdlW6qGmcUU063B7qCMAa5r94ZUAz3IRmBOWJwovcTYvnPvkylN9+ISdGLVociRtEtUz2I7VkUiOigJQA9qJWxHgdp106eJgqYHnHyfxQMcu/ZqO+BrvhHJu4uxQwwylJc4UB579S418Xfs48P0WKw4YOObqvZV31ZcQFL8DIEKaUIef74CtprZ8ldgVemiuGq0elEY3J3hC+FPsErbE4=";
    }

    public static Boolean getLuxTrustActivated() {
        return true;
    }

    //
    // Remote loading responses
    //

    public static T1cResponse<String> getRemoteLoadingAtrResponse() {
        return getSuccessResponse(getRemoteLoadingAtr());
    }

    public static T1cResponse<GclRemoteLoadingCommand> getRemoteLoadingCommandResponse() {
        return getSuccessResponse(getGclRemoteLoadingCommand(0));
    }

    public static T1cResponse<List<GclRemoteLoadingCommand>> getRemoteLoadingCommandsResponses(int amountOfCommands) {
        List<GclRemoteLoadingCommand> remoteLoadingCommands = new ArrayList<>();
        for (int i = 0; i < amountOfCommands; i++) {
            remoteLoadingCommands.add(getGclRemoteLoadingCommand(i));
        }
        return getSuccessResponse(remoteLoadingCommands);
    }

    public static T1cResponse<List<GclRemoteLoadingCcidFeature>> getRemoteLoadingCcidFeaturesResponse() {
        return getSuccessResponse(getGclRemoteLoadingCcidFeatures());
    }

    public static T1cResponse<Boolean> getRemoteLoadingIsPresentResponse() {
        return getSuccessResponse(true);
    }

    public static T1cResponse<String> getRemoteLoadingOpenSessionResponse() {
        return getSuccessResponse(getRemoteLoadingSessionId());
    }

    public static T1cResponse<String> getRemoteLoadingCloseSessionResponse(String sessionId) {
        return getSuccessResponse(sessionId);
    }

    public static String getRemoteLoadingSessionId() {
        return "sessionId";
    }

    public static List<GclRemoteLoadingCcidFeature> getGclRemoteLoadingCcidFeatures() {
        List<GclRemoteLoadingCcidFeature> features = new ArrayList<>();
        features.add(new GclRemoteLoadingCcidFeature()
                .withId("VERIFY_PIN_DIRECT")
                .withControlCode(1));
        features.add(new GclRemoteLoadingCcidFeature()
                .withId("MODIFY_PIN_DIRECT")
                .withControlCode(2));
        return features;
    }

    public static GclRemoteLoadingCommand getGclRemoteLoadingCommand(int txIncrement) {
        return new GclRemoteLoadingCommand()
                .withRx("RESPONSE_DATA")
                .withTx("00B00000B" + txIncrement)
                .withSw("9000");
    }

    public static String getRemoteLoadingAtr() {
        return "3B8F800180318065B0850300EF120FFF82900073";
    }

    //
    // Portuguese ID responses
    //

    public static T1cResponse<GclPtIdData> getGclPtIdDataResponse(Boolean includePhoto) {
        GclPtIdData data = getPtIdData();
        if (includePhoto == null || !includePhoto) data.setPhoto(null);
        return getSuccessResponse(data);
    }

    public static T1cResponse<String> getPtIdPhotoResponse() {
        return getSuccessResponse(getPtIdPhoto());
    }

    public static T1cResponse<String> getPtIdRootAuthenticationResponse() {
        return getSuccessResponse(getPtIdRootAuthenticationCertificate());
    }

    public static T1cResponse<String> getPtIdRootNonRepudiationCertificateResponse() {
        return getSuccessResponse(getPtIdRootNonRepudiationCertificate());
    }

    public static T1cResponse<String> getPtIdRootCertificateResponse() {
        return getSuccessResponse(getPtIdRootCertificate());
    }

    public static T1cResponse<String> getPtIdAuthenticationCertificateResponse() {
        return getSuccessResponse(getPtIdAuthenticationCertificate());
    }

    public static T1cResponse<String> getPtIdNonRepudiationCertificateResponse() {
        return getSuccessResponse(getPtIdNonRepudiationCertificate());
    }

    public static T1cResponse<GclPtIdAllData> getPtIdAllDataResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclPtIdAllData data = getPtIdAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("id")) data.setId(null);
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("non-repudiation-certificate")) data.setNonRepudiationCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("root-non-repudiation-certificate")) data.setRootNonRepudiationCertificate(null);
            if (!filterParams.contains("root-authentication-certificate")) data.setRootAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclPtIdAllCertificates> getPtIdAllCertificatesResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclPtIdAllCertificates data = getPtIdAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("non-repudiation-certificate")) data.setNonRepudiationCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("root-non-repudiation-certificate")) data.setRootNonRepudiationCertificate(null);
            if (!filterParams.contains("root-authentication-certificate")) data.setRootAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclPtIdAddress> getPtIdAddressResponse(String pin) {
        if (pin != null && !"1111".equals(pin)) {
            throw new RestException("PIN verification failed", 412, "https://localhost:10443/v1/plugins/pluginid/readerid/method", "{\n" +
                    "  \"code\": 103,\n" +
                    "  \"description\": \"Wrong pin, 2 tries remaining\",\n" +
                    "  \"success\": false\n" +
                    "}");
        }
        return getSuccessResponse(getPtIdAddress());
    }

    public static GclPtIdAddress getPtIdAddress() {
        return new GclPtIdAddress()
                .withAbbrStreetType("AV")
                .withBuildingType("")
                .withCivilParish("110623")
                .withCivilParishDescription("Nossa Senhora de Fátima")
                .withDistrict("11")
                .withDistrictDescription("Lisboa")
                .withDoorNo("202")
                .withFloor("")
                .withGenAddressNum("200801")
                .withIsNational(true)
                .withLocality("Lisboa")
                .withMunicipality("1106")
                .withMunicipalityDescription("Lisboa")
                .withPlace("")
                .withPostalLocality("LISBOA")
                .withRawData("TgBQVAAAMTEAAExpc2JvYQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAxMTA2AAAAAExpc2JvYQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAxMTA2MjMAAAAAAABOb3NzYSBTZW5ob3JhIGRlIEbDoXRpbWEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQVYAAAAAAAAAAAAAAAAAAAAAAABBdmVuaWRhAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANSBkZSBPdXR1YnJvAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyMDIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAExpc2JvYQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAxMDUwAAAAADA2NQAAAExJU0JPQQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjAwODAxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                .withSide("")
                .withStreetName("5 de Outubro")
                .withStreetType("Avenida")
                .withType("N")
                .withZip3("065")
                .withZip4("1050")
                .withAbbrBuildingType("");
    }

    public static GclPtIdAllCertificates getPtIdAllCertificates() {
        return new GclPtIdAllCertificates()
                .withAuthenticationCertificate(getPtIdAuthenticationCertificate())
                .withRootAuthenticationCertificate(getPtIdRootAuthenticationCertificate())
                .withRootCertificate(getPtIdRootCertificate())
                .withNonRepudiationCertificate(getPtIdNonRepudiationCertificate())
                .withRootNonRepudiationCertificate(getPtIdRootNonRepudiationCertificate());
    }

    public static GclPtIdAllData getPtIdAllData() {
        return new GclPtIdAllData()
                .withId(getPtIdData())
                .withAuthenticationCertificate(getPtIdAuthenticationCertificate())
                .withRootAuthenticationCertificate(getPtIdRootAuthenticationCertificate())
                .withRootCertificate(getPtIdRootCertificate())
                .withNonRepudiationCertificate(getPtIdNonRepudiationCertificate())
                .withRootNonRepudiationCertificate(getPtIdRootNonRepudiationCertificate());
    }

    public static String getPtIdAuthenticationCertificate() {
        return "MIIHOjCCBiKgAwIBAgIIG/8avN/kl+IwDQYJKoZIhvcNAQEFBQAwgYQxQTA/BgNVBAMMOChUZXN0ZSkgRUMgZGUgQXV0ZW50aWNhw6fDo28gZG8gQ2FydMOjbyBkZSBDaWRhZMOjbyAwMDA0MRQwEgYDVQQLDAtzdWJFQ0VzdGFkbzEcMBoGA1UECgwTQ2FydMOjbyBkZSBDaWRhZMOjbzELMAkGA1UEBhMCUFQwHhcNMTMwNDA4MDA0MjQ4WhcNMTYxMDMxMDAwMDAwWjCBwzELMAkGA1UEBhMCUFQxHDAaBgNVBAoME0NhcnTDo28gZGUgQ2lkYWTDo28xHDAaBgNVBAsME0NpZGFkw6NvIFBvcnR1Z3XDqnMxKzApBgNVBAsMIihUZXN0ZSkgQXV0ZW50aWNhw6fDo28gZG8gQ2lkYWTDo28xETAPBgNVBAQMCFJldm9nYWRvMQwwCgYDVQQqDANBbmExEzARBgNVBAUTCkJJOTkwMDAxODIxFTATBgNVBAMMDEFuYSBSZXZvZ2FkbzCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAvDM9tdEQwMZHHiFyp7LtAKNXkcMuijYRKsWODK2AsgAv5ATABbqDjXVdeBxAn5yvEmNu9RXn51I/1xrKwxn1UDEzZqa4DflUbiJ2CU6uuZ4Lo66jDI6WlpOiuZoJpRovomsJcK7IppDT/QjNJTZ2OIUHvdYOh7FiuNKFhqOd3GMCAwEAAaOCA/EwggPtMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgOIMB0GA1UdDgQWBBRrKy4QpC4ElPRpmJHxFWqxMBsu1TAfBgNVHSMEGDAWgBQHOXKAehir07mbzDzxNlUUGFMH7jCCAg8GA1UdIASCAgYwggICMIHIBghghGwBAQECFDCBuzCBuAYIKwYBBQUHAgIwgasegagATwAgAGMAZQByAHQAaQBmAGkAYwBhAGQAbwAgAGUAbQBpAHQAaQBkAG8AIABzAGUAZwB1AG4AZABvACAAZQBzAHQAYQAgAHAAbwBsAO0AdABpAGMAYQAgAOkAIAB1AHQAaQBsAGkAegBhAGQAbwAgAHAAYQByAGEAIABhAHUAdABlAG4AdABpAGMAYQDnAOMAbwAgAGQAbwAgAEMAaQBkAGEAZADjAG8wfgYLYIRsAQEBAgQCAAcwbzBtBggrBgEFBQcCARZhaHR0cDovL3BraS50ZXN0ZS5jYXJ0YW9kZWNpZGFkYW8ucHQvcHVibGljby9wb2xpdGljYXMvZHBjL2NjX3N1Yi1lY19jaWRhZGFvX2F1dGVudGljYWNhb19kcGMuaHRtbDA2BghghGwBAQECCjAqMCgGCCsGAQUFBwIBFhxodHRwOi8vd3d3LnNjZWUuZ292LnB0L3BjZXJ0MH0GDGCEbAEBAQIEAgABATBtMGsGCCsGAQUFBwIBFl9odHRwOi8vcGtpLnRlc3RlLmNhcnRhb2RlY2lkYWRhby5wdC9wdWJsaWNvL3BvbGl0aWNhcy9wYy9jY19zdWItZWNfY2lkYWRhb19hdXRlbnRpY2FjYW9fcGMuaHRtbDBxBgNVHR8EajBoMGagZKBihmBodHRwOi8vcGtpLnRlc3RlLmNhcnRhb2RlY2lkYWRhby5wdC9wdWJsaWNvL2xyYy9jY19zdWItZWNfY2lkYWRhb19hdXRlbnRpY2FjYW9fY3JsMDAwNF9wMDAwMS5jcmwwdwYDVR0uBHAwbjBsoGqgaIZmaHR0cDovL3BraS50ZXN0ZS5jYXJ0YW9kZWNpZGFkYW8ucHQvcHVibGljby9scmMvY2Nfc3ViLWVjX2NpZGFkYW9fYXV0ZW50aWNhY2FvX2NybDAwMDRfZGVsdGFfcDAwMDEuY3JsMFEGCCsGAQUFBwEBBEUwQzBBBggrBgEFBQcwAYY1aHR0cDovL29jc3AuYXVjLnRlc3RlLmNhcnRhb2RlY2lkYWRhby5wdC9wdWJsaWNvL29jc3AwEQYJYIZIAYb4QgEBBAQDAgCgMCgGA1UdCQQhMB8wHQYIKwYBBQUHCQExERgPMTk2MDA4MTkxMjAwMDBaMA0GCSqGSIb3DQEBBQUAA4IBAQA9xYO1TD0+IJMvNVaQaEKlNgh/xNqU6k7zBeSkeS3x/tYGxpzVjWMW56PqKx+DtIn+Jp44EdajAEZ5uJZl+ZEZbbt84wKXfwBI4rOglHuoPvSKi6pS5llxJCjxPbZN6IsaMW945DrS5sHJo+1KW1murxd2q0mQxRfT8Eg0Rtv1VMqPKjjMA61nZ29EmANi+FMo0A2ye2VfIoONkWqn0ma3GkVK548Py2RtZ7RicnYVm9tZXtlOpcuEsd+Ng1o0l+77nLBPhVNQ9n/mLGADC1d8BA7MjHByNm1eLgwjVC+X4nJ1EcJ/j6kbhUtIIfn5meMRHrJqva13oycVvfWJaTTf";
    }

    public static String getPtIdNonRepudiationCertificate() {
        return "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";
    }

    public static String getPtIdRootCertificate() {
        return "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";
    }

    public static String getPtIdRootNonRepudiationCertificate() {
        return "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";
    }

    public static String getPtIdRootAuthenticationCertificate() {
        return "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";
    }

    public static String getPtIdPhoto() {
        return "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";
    }

    public static GclPtIdData getPtIdData() {
        return new GclPtIdData()
                .withAccidentalIndications("Sem ID Esq;Sem ID Dta")
                .withCivilianNumber("990001822")
                .withCountry("PRT")
                .withDateOfBirth("19 08 1960")
                .withDocumentNumber("99000182 2 ZZ2")
                .withDocumentNumberPan("9999000000026918")
                .withDocumentType("Cartão de Cidadão")
                .withDocumentVersion("001.001.11")
                .withGender("F")
                .withGivenNameFather("Carlos")
                .withGivenNameMother("Maria")
                .withHealthNo("898765392")
                .withHeight("1,68")
                .withIssuingEntity("República Portuguesa")
                .withLocalOfRequest("AMA")
                .withMrz1("I<PRT990001822<ZZ29<<<<<<<<<<<")
                .withMrz2("6008190F1610316PRT<<<<<<<<<<<8")
                .withMrz3("REVOGADO<<ANA<<<<<<<<<<<<<<<<<")
                .withName("Ana")
                .withNationality("PRT")
                .withPhoto(getPtIdPhoto())
                .withRawData("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")
                .withSocialSecurityNo("11999999960")
                .withSurname("Revogado")
                .withSurnameFather("Revogado")
                .withSurnameMother("Revogado")
                .withTaxNo("399990046")
                .withValidityBeginDate("08 04 2013")
                .withValidityEndDate("31 10 2016");
    }

    //
    // DNIE responses
    //

    public static T1cResponse<GclDnieInfo> getGclDnieInfoResponse() {
        return getSuccessResponse(getGclDnieInfo());
    }

    public static T1cResponse<String> getGclDnieAuthenticationCertificateResponse() {
        return getSuccessResponse(getDnieAuthenticationCertificate());
    }

    public static T1cResponse<String> getGclDnieIntermediateCertificateResponse() {
        return getSuccessResponse(getDnieIntermediateCertificate());
    }

    public static T1cResponse<String> getGclDnieSigningCertificateResponse() {
        return getSuccessResponse(getDnieSigningCertificate());
    }

    public static T1cResponse<GclDnieAllData> getGclDnieAllDataResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclDnieAllData data = getDnieAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("info")) data.setInfo(null);
            if (!filterParams.contains("intermediate-certificate")) data.setIntermediateCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclDnieAllCertificates> getGclDnieAllCertificatesResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclDnieAllCertificates data = getDnieAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("intermediate-certificate")) data.setIntermediateCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static GclDnieAllData getDnieAllData() {
        return new GclDnieAllData()
                .withInfo(getGclDnieInfo())
                .withAuthenticationCertificate(getDnieAuthenticationCertificate())
                .withIntermediateCertificate(getDnieIntermediateCertificate())
                .withSigningCertificate(getDnieSigningCertificate());
    }

    public static GclDnieAllCertificates getDnieAllCertificates() {
        return new GclDnieAllCertificates()
                .withAuthenticationCertificate(getDnieAuthenticationCertificate())
                .withIntermediateCertificate(getDnieIntermediateCertificate())
                .withSigningCertificate(getDnieSigningCertificate());
    }

    public static String getDnieAuthenticationCertificate() {
        return "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";
    }

    public static String getDnieIntermediateCertificate() {
        return "MIIFxTCCA62gAwIBAgIQTC76D3cRLAdEAtoYr7n+fjANBgkqhkiG9w0BAQsFADBdMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTEVMBMGA1UEAwwMQUMgUkFJWiBETklFMB4XDTA2MDIyNzEwNTMxMloXDTIxMDIyNjIyNTk1OVowXDELMAkGA1UEBhMCRVMxKDAmBgNVBAoMH0RJUkVDQ0lPTiBHRU5FUkFMIERFIExBIFBPTElDSUExDTALBgNVBAsMBEROSUUxFDASBgNVBAMMC0FDIEROSUUgMDAxMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArP5KmdLkp/2ND+57jLnX+4k1f86Z36TGrvy3MuANV+ft8NbNOxZcpcGSPw6WYa+m0zKfhQ+sQETHhMHMo7Gb6nlhqCmNTpTdj35K0Bn5maLnTE8F8/p/6pT9CLrEPYV5eX7ObNCiqk2vnuWQMcVAwRX077QYGKNzW98p9Kxiq4mYZRR0ObnxYNd61WDczOduP5C/dUIrGk3JfFLINLGV0d/9EaeHl7zjkzOYyCScGq+tlrkKVgW6BQwI/gXw4XRYNhFjCAPzb8caLevxQxGnzRUZ8dTOUOeqgOZ1FXfNuig7Koek4qkdyQKowYNp4S3NweuuvbsbPJZqVzCqzwCq9wIDAQABo4IBgDCCAXwwEgYDVR0TAQH/BAgwBgEB/wIBADAdBgNVHQ4EFgQUGomoxe6Pdl1VcYnzOzW9qgUAlW8wHwYDVR0jBBgwFoAUjkX0n3PF/y8bBdsBR2AbA4qBt7owDgYDVR0PAQH/BAQDAgEGMDcGA1UdIAQwMC4wLAYEVR0gADAkMCIGCCsGAQUFBwIBFhZodHRwOi8vd3d3LmRuaWUuZXMvZHBjMIHcBgNVHR8EgdQwgdEwgc6ggcuggciGIGh0dHA6Ly9jcmxzLmRuaWUuZXMvY3Jscy9BUkwuY3JshoGjbGRhcDovL2xkYXAuZG5pZS5lcy9DTj1DUkwsQ049QUMlMjBSQUlaJTIwRE5JRSxPVT1ETklFLE89RElSRUNDSU9OJTIwR0VORVJBTCUyMERFJTIwTEElMjBQT0xJQ0lBLEM9RVM/YXV0aG9yaXR5UmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RjbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDANBgkqhkiG9w0BAQsFAAOCAgEAcpi2LlDeKvi54BNoqtJy/ZLi9Qm+W68L/WFAqzg+za4gbhilpL/rlSYnBPAx4X17kJe0COywhwX/u+q8Iywt7LPdX3VheWwJBHY75jkCveLThpSvkDD69/DCi+4P/GnaoXeycJX2Hs70h/lw6PM62Vn6686+tf/X/kYApAwiCIW7MeQFrDtB/ZLUQF9nlbi6Iimcaew4Csi1Y0tsEjaRmFiZxMohSuaZaW7EpiAFmns19LRhKHpGYlHHuqXVKghSAkGRgAAyp45CsA0PkhK2vq/PL2Ey8o4wrtdyiZSi370DA+khg8x9hSBD/lOWk0Ivzop+Gb4ONHldjZRUW+ipX1wvtYoGhumETvf9oZXrLEJr6dV44U2Sdz8bhhkCt3G75u0zxaZTOexewhpRDyx6RoWSeUs0Z7Pppwxlb4kjmqfH10TlSEQj1I5yzi6p8Y2Eu3UG6XmI+LEZy6PhYo+ky5OS+ktJgqiYAKW161X9E2+iSBF77ikbFUhsvvVc4zDJeqHktCkNj3CnktWqjmzwd6G16HegLQW+nbn0bkTUO98hNOyuRzkb7o8wsWpX5pZPNQ1wwz3lAZQ7TL72N25JEOTUt9oZe4SoWrycjHyJ4uXPGJLUKPXISOyi9uKq/gKePNNF8/TfmBsEOfmjXQ7PDA55y7dW05EoO3RMg669K8A=";
    }

    public static String getDnieSigningCertificate() {
        return "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";
    }

    public static GclDnieInfo getGclDnieInfo() {
        return new GclDnieInfo()
                .withSecondLastName("MEJIA")
                .withFirstLastName("ESCOBAR")
                .withFirstName("DIANA CAROLINA")
                .withIdesp("AMF108370")
                .withNumber("54350330L")
                .withSerialNumber("8644E25A8A30A4");
    }

    //
    // EMV responses
    //

    public static T1cResponse<List<GclEmvApplication>> getGclEmvApplicationsResponse() {
        return getSuccessResponse(getGclEmvApplications());
    }

    public static T1cResponse<GclEmvApplicationData> getGclEmvApplicationDataResponse() {
        return getSuccessResponse(getGclEmvApplicationData());
    }

    public static T1cResponse<GclEmvPublicKeyCertificate> getGclEmvIccPublicKeyCertificateResponse(String aid) throws RestException {
        if (!"A0000000048002".equals(aid))
            throw ExceptionFactory.restException("wrong aid", 412, "https://localhost:10443/v1", null);
        return getSuccessResponse(getGclEmvIccPublicKeyCertificate());
    }

    public static T1cResponse<GclEmvPublicKeyCertificate> getGclEmvIssuerPublicKeyCertificateResponse(String aid) throws RestException {
        if (!"A0000000048002".equals(aid))
            throw ExceptionFactory.restException("wrong aid", 412, "https://localhost:10443/v1", null);
        return getSuccessResponse(getGclEmvIssuerPublicKeyCertificate());
    }

    public static T1cResponse<GclEmvAllData> getGclEmvAllDataResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclEmvAllData data = getGclEmvAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("applications")) data.setApplicationData(null);
            if (!filterParams.contains("application-data")) data.setApplicationData(null);
        }
        return getSuccessResponse(data);
    }

    public static GclEmvAllData getGclEmvAllData() {
        return new GclEmvAllData().withApplicationData(getGclEmvApplicationData()).withApplications(getGclEmvApplications());
    }

    public static GclEmvPublicKeyCertificate getGclEmvIssuerPublicKeyCertificate() {
        return new GclEmvPublicKeyCertificate()
                .withData("owD7hDFbMOg3AG2IESD8j78T2Etrvv+7tZAkrSdMZZ8wXgFlAaXdOWngrRp3c2GWSkeHIR5YfgRbDeUtvCMObGG6qyGc1yDmAP6m6UBcEBWYbxjqHtSmnsAroz9lt4prMvKLx4AHLWwiJV3cc/PTHG4NoMIKXlK/dOxdVmjrul0lcEwBEZ+TwvYjQ8lLxOjW1LnD8pbFCt3mhB/WDikySLLITKEsY0rQBTbcJ0fHTYo=")
                .withExponent("Aw==")
                .withRemainder("ZkSGPIZ4v4rEe7JOO4HX3VrAytZZBvMITumBsGTtVvrXszzl");
    }

    public static GclEmvPublicKeyCertificate getGclEmvIccPublicKeyCertificate() {
        return new GclEmvPublicKeyCertificate()
                .withData("C9UsasgS2x98a8tCtCvPxiLCT/B6KuaoqJAvAOfigrqLXQt8gRCLnkLKwwVNA9iwFJ/YdWet9bHBVLZjPIro3TPze26O1rjPrehAskMr/EWA86H9NP7AuY45p58eUrrc+Ks7tT3dByvVUYNAFP9iK1Sndhl7ExWY6nxKFRqSY651x3xs1Y0UcVzf7RJgAsIUT9pJvpU4sX+EQpa8Eb85q0mVu1wsbLsfSA5P6f8tgtg=")
                .withExponent("AQAB")
                .withRemainder("");
    }

    public static GclEmvApplicationData getGclEmvApplicationData() {
        return new GclEmvApplicationData()
                .withCountry("BE")
                .withCountryCode("0056")
                .withEffectiveDate("091101")
                .withExpirationDate("141130")
                .withLanguage("nl")
                .withName("")
                .withPan("67034200172725018");
    }

    public static List<GclEmvApplication> getGclEmvApplications() {
        return Arrays.asList(
                new GclEmvApplication()
                        .withAid("A0000000048002")
                        .withLabel("SECURE CODE")
                        .withPriority(0),
                new GclEmvApplication()
                        .withAid("D056000666111010")
                        .withLabel("BANCONTACT")
                        .withPriority(1),
                new GclEmvApplication()
                        .withAid("A0000000043060")
                        .withLabel("MAESTRO")
                        .withPriority(1)
        );
    }

    //
    // MOBIB responses
    //

    public static T1cResponse<Boolean> getGclMobibStatusResponse() {
        return getSuccessResponse(true);
    }

    public static T1cResponse<String> getGclMobibPictureResponse() {
        return getSuccessResponse(getGclMobibPicture());
    }

    public static T1cResponse<GclMobibCardIssuing> getGclMobibCardIssuingResponse() {
        return getSuccessResponse(getGclMobibCardIssuing());
    }

    public static T1cResponse<List<GclMobibContract>> getGclMobibContractsResponse() {
        return getSuccessResponse(Arrays.asList(getGclMobibContract(), getGclMobibContract()));
    }

    public static T1cResponse<GclMobibAllData> getGclMobibAllDataResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclMobibAllData data = getGclMobibAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("picture")) data.setPicture(null);
            if (!filterParams.contains("status")) data.setActive(null);
            if (!filterParams.contains("card-issuing")) data.setCardIssuing(null);
            if (!filterParams.contains("contracts")) data.setContracts(null);
        }
        return getSuccessResponse(data);
    }

    public static GclMobibAllData getGclMobibAllData() {
        return new GclMobibAllData()
                .withActive(true)
                .withCardIssuing(getGclMobibCardIssuing())
                .withContracts(Arrays.asList(getGclMobibContract(), getGclMobibContract()))
                .withPicture(getGclMobibPicture());
    }

    public static GclMobibContract getGclMobibContract() {
        return new GclMobibContract()
                .withAuthenticatorKvc(122)
                .withAuthenticatorValue(14646958)
                .withJourneyInterchangesAllowed(true)
                .withOperatorMap(15)
                .withPassengersMax(0)
                .withPriceAmount(0)
                .withProvider(1)
                .withRestrictCode(5)
                .withRestrictTime(2)
                .withSaleDate("2015-03-06")
                .withSaleSamCount(398)
                .withSaleSamId(1135996)
                .withSpatials(Arrays.asList(getGclMobibSpatial(), getGclMobibSpatial()))
                .withTariff(getGclMobibTariff())
                .withTypeId(15373)
                .withValidityDuration(getGclMobibValidityDuration())
                .withValidityStartDate("2015-03-06")
                .withVehicleClassAllowed(0)
                .withVersion(4);
    }

    public static GclMobibSpatial getGclMobibSpatial() {
        return new GclMobibSpatial().withRouteDestination(false).withRouteOrigin(1).withType(6);
    }

    public static GclMobibTariff getGclMobibTariff() {
        return new GclMobibTariff().withCounter(getGclMobibCounter()).withMultimodal(true).withNameref(3);
    }

    public static GclMobibCounter getGclMobibCounter() {
        return new GclMobibCounter().withTime("2017-02-21T06:20:00").withType(2).withJourneys(0);
    }

    public static GclMobibValidityDuration getGclMobibValidityDuration() {
        return new GclMobibValidityDuration().withUnit(3).withValue(3);
    }

    public static GclMobibCardIssuing getGclMobibCardIssuing() {
        return new GclMobibCardIssuing()
                .withCardExpirationDate("2020-03-06")
                .withCardHolderBirthDate("1968-09-03")
                .withCardHolderEndDate("not-a-date-time")
                .withCardHolderId("6665601000000000015")
                .withCardHolderName("TEST|KAART                                         ")
                .withCardHolderStartDate("2015-03-06")
                .withCardRevalidationDate("not-a-date-time")
                .withCardType(1)
                .withCompanyId(1)
                .withGender(1)
                .withLanguage(1)
                .withVersion(1);
    }

    public static String getGclMobibPicture() {
        return "";
    }

    //
    // OCRA responses
    //

    public static T1cResponse<GclOcraAllData> getGclOcraAllDataResponse(String filter) throws RestException {
        List<String> filterParams = splitFilterParams(filter);
        GclOcraAllData data = getGclOcraAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("counter")) data.setCounter(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<Long> getGclOcraChallengeResponse(String pin) throws RestException {
        if (pin != null && !"1111".equals(pin)) {
            throw new RestException("PIN verification failed", 412, "https://localhost:10443/v1/plugins/pluginid/readerid/method", "{\n" +
                    "  \"code\": 103,\n" +
                    "  \"description\": \"Wrong pin, 2 tries remaining\",\n" +
                    "  \"success\": false\n" +
                    "}");
        }
        return getSuccessResponse(1L);
    }

    public static T1cResponse<String> getGclOcraCounterResponse(String pin) throws RestException {
        if (pin != null && !"1111".equals(pin)) {
            throw new RestException("PIN verification failed", 412, "https://localhost:10443/v1/plugins/pluginid/readerid/method", "{\n" +
                    "  \"code\": 103,\n" +
                    "  \"description\": \"Wrong pin, 2 tries remaining\",\n" +
                    "  \"success\": false\n" +
                    "}");
        }
        return getSuccessResponse(getGclOcraCounter());
    }

    public static GclOcraAllData getGclOcraAllData() {
        return new GclOcraAllData().withCounter(getGclOcraCounter());
    }

    public static String getGclOcraCounter() {
        return "zMzMzMzMzQM=";
    }

    //
    // Aventra responses
    //

    public static T1cResponse<Object> getGclAventraResetPinResponse(String puk) {
        if (StringUtils.isNotEmpty(puk) && !puk.equals("1111")) {
            throw new RestException("PIN verification failed", 412, "https://localhost:10443/v1/plugins/pluginid/readerid/method", "{\n" +
                    "  \"code\": 103,\n" +
                    "  \"description\": \"Wrong pin, 2 tries remaining\",\n" +
                    "  \"success\": false\n" +
                    "}");
        }
        return getSuccessResponseWithoutData();
    }

    public static T1cResponse<String> getGclAventraRootCertificateResponse() {
        return getSuccessResponse(getGclAventraRootCertificate());
    }

    public static T1cResponse<String> getGclAventraAuthenticationCertificateResponse() {
        return getSuccessResponse(getGclAventraAuthenticationCertificate());
    }

    public static T1cResponse<String> getGclAventraSigningCertificateResponse() {
        return getSuccessResponse(getGclAventraSigningCertificate());
    }

    public static T1cResponse<String> getGclAventraEncryptionCertificateResponse() {
        return getSuccessResponse(getGclAventraEncryptionCertificate());
    }

    public static T1cResponse<String> getGclAventraIssuerCertificateResponse() {
        return getSuccessResponse(getGclAventraIssuerCertificate());
    }

    public static T1cResponse<List<String>> getGclAventraAuthenticationAlgoRefsResponse() {
        return getSuccessResponse(getGclAventraAuthenticationAlgoRefs());
    }

    public static T1cResponse<List<String>> getGclAventraSignAlgoRefsResponse() {
        return getSuccessResponse(getGclAventraSignAlgoRefs());
    }

    public static T1cResponse<GclAventraAllCertificates> getAventraAllCertificatesResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclAventraAllCertificates data = getGclAventraAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("issuer-certificate")) data.setIssuerCertificate(null);
            if (!filterParams.contains("encryption-certificate")) data.setEncryptionCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclAventraAllData> getGclAventraAllDataResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclAventraAllData data = getGclAventraAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("applet-info")) data.setAppletInfo(null);
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("issuer-certificate")) data.setIssuerCertificate(null);
            if (!filterParams.contains("encryption-certificate")) data.setEncryptionCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static GclAventraAllData getGclAventraAllData() {
        return new GclAventraAllData()
                .withAppletInfo(getGclAventraAppletInfo())
                .withRootCertificate(getGclAventraRootCertificate())
                .withAuthenticationCertificate(getGclAventraAuthenticationCertificate())
                .withSigningCertificate(getGclAventraSigningCertificate())
                .withIssuerCertificate(getGclAventraIssuerCertificate())
                .withEncryptionCertificate(getGclAventraEncryptionCertificate());
    }

    public static GclAventraAppletInfo getGclAventraAppletInfo() {
        return new GclAventraAppletInfo()
                .withChangeCounter(77)
                .withName("MyEID")
                .withSerial("00006064024054982647")
                .withVersion("3.3.3");
    }

    public static GclAventraAllCertificates getGclAventraAllCertificates() {
        return new GclAventraAllCertificates()
                .withRootCertificate(getGclAventraRootCertificate())
                .withAuthenticationCertificate(getGclAventraAuthenticationCertificate())
                .withSigningCertificate(getGclAventraSigningCertificate())
                .withIssuerCertificate(getGclAventraIssuerCertificate())
                .withEncryptionCertificate(getGclAventraEncryptionCertificate());
    }

    public static List<String> getGclAventraSignAlgoRefs() {
        return Arrays.asList("SHA1");
    }

    public static List<String> getGclAventraAuthenticationAlgoRefs() {
        return Arrays.asList("SHA1", "SHA256", "SHA512");
    }

    public static String getGclAventraRootCertificate() {
        return "MIIFujCCBKKgAwIBAgIIEVPWTD6qlpowDQYJKoZIhvcNAQELBQAwggEAMSIwIAYDVQQDExlDUE4gUm9vdCBTSEEyNTYgQ0EgLSBURVNUMUQwQgYDVQQLEztDb21tZmlkZXMgVHJ1c3QgRW52aXJvbm1lbnQoQykgVEVTVCAyMDEwIENvbW1maWRlcyBOb3JnZSBBUzErMCkGA1UECxMiQ1BOIFRFU1QgLSBGb3IgYXV0aG9yaXplZCB1c2Ugb25seTEvMC0GA1UECxMmQ1BOIFByaW1hcnkgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IFRFU1QxKTAnBgNVBAoTIENvbW1maWRlcyBOb3JnZSBBUyAtIDk4OCAzMTIgNDk1MQswCQYDVQQGEwJOTzAeFw0xMjEwMDIxMjUzNDRaFw0yMjEwMDMxMjUzNDRaMIIBADEiMCAGA1UEAxMZQ1BOIFJvb3QgU0hBMjU2IENBIC0gVEVTVDFEMEIGA1UECxM7Q29tbWZpZGVzIFRydXN0IEVudmlyb25tZW50KEMpIFRFU1QgMjAxMCBDb21tZmlkZXMgTm9yZ2UgQVMxKzApBgNVBAsTIkNQTiBURVNUIC0gRm9yIGF1dGhvcml6ZWQgdXNlIG9ubHkxLzAtBgNVBAsTJkNQTiBQcmltYXJ5IENlcnRpZmljYXRlIEF1dGhvcml0eSBURVNUMSkwJwYDVQQKEyBDb21tZmlkZXMgTm9yZ2UgQVMgLSA5ODggMzEyIDQ5NTELMAkGA1UEBhMCTk8wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC33PFTNbpzg1KUaphis0QyJ4e2Ka/o2wzFmCthw46bmx/mMKH7hNxefMX7YU/IHJ4W23izKyxhAqGO2tjNXe/kz2ww9AFJvWKJqiWtBEAGybDQRueCePcmESZJ2nB+YZf3Yl091AjQwD2VZXosCjV73SkBm5hiTalYk0qpsfWq+Q9waXOvQ3uFw2Eg9TQxVAN3h4zgkfpQary0vwBaK2bBBDGi9SNyTEO5YUGPZ+fAvIvCLGzYsvZn22+ZyJXo64jv4m7LfZkp929hRh/F97lAlKaRD/SGuxEo48OVUq4GL1IB9EuUKQfeRyBET4XHq9c0CNoE053Zl/ehM5sAckuzAgMBAAGjggEyMIIBLjBABggrBgEFBQcBAQQ0MDIwMAYIKwYBBQUHMAGGJGh0dHA6Ly9vY3NwMS50ZXN0LmNvbW1maWRlcy5jb20vb2NzcDAdBgNVHQ4EFgQUHpAN9SpjZKZqH0lRW5jPrnwhRHIwDwYDVR0TAQH/BAUwAwEB/zAfBgNVHSMEGDAWgBQekA31KmNkpmofSVFbmM+ufCFEcjCBiAYDVR0fBIGAMH4wPaA7oDmGN2h0dHA6Ly9jcmwxLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNSb290LVNIQTI1Ni5jcmwwPaA7oDmGN2h0dHA6Ly9jcmwyLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNSb290LVNIQTI1Ni5jcmwwDgYDVR0PAQH/BAQDAgEGMA0GCSqGSIb3DQEBCwUAA4IBAQB429FURBiQVTJyHLUsEAEAljKO7eyilDSSdBAu0iyIrA+1hXviQhalROsfBas/wZYvDd1xZQ80IlSHKrtqMf2+Jtl/2t2LMwFl+Ml7tA3/VCr2ClDWC2VTjUJxQ75Lo6QyOPqHnUZApWUkxxS03i89GI8XfAvtRU8OwKR8qTuWHLt16y3dPRtUVu58DGjYCeT97Tx0VnzHNBdWe7LCIms5gvTVZT845zcsedb0EmGo5hJMyhut4o9U9OcDHwoaF6GKPWupCoVxvZlqvSHQElqv2QzA1I8MsEAKDV3cqdJe5v/eL6Cg/qDdraVhCKRCTOefSW2OGfdxRzLzsBYjkCIx";
    }

    public static String getGclAventraAuthenticationCertificate() {
        return "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";
    }

    public static String getGclAventraSigningCertificate() {
        return "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";
    }

    public static String getGclAventraIssuerCertificate() {
        return "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";
    }

    public static String getGclAventraEncryptionCertificate() {
        return "MIIF2DCCBMCgAwIBAgIIBxEk2Env+2swDQYJKoZIhvcNAQELBQAwgeAxMzAxBgNVBAMTKkNvbW1maWRlcyBDUE4gUGVyc29uLUhpZ2ggU0hBMjU2IENBIC0gVEVTVDFGMEQGA1UECxM9Q29tbWZpZGVzIFRydXN0IEVudmlyb25tZW50KEMpIDIwMTQgQ29tbWZpZGVzIE5vcmdlIEFTIC0gVEVTVDEpMCcGA1UECxMgQ1BOIFBlcnNvbiBIaWdoIFNIQTI1NiBDQSAtIFRFU1QxKTAnBgNVBAoTIENvbW1maWRlcyBOb3JnZSBBUyAtIDk4OCAzMTIgNDk1MQswCQYDVQQGEwJOTzAeFw0xNzAzMDMxMDI0MTNaFw0yMjEwMDMxMjUzNDRaMEgxGTAXBgNVBAMTEEFNQU5EQSBGT1MgRUdFTEkxHjAcBgNVBAUTFTk1NzgtNDUxMC0wMDAwMVJDdm1TTDELMAkGA1UEBhMCTk8wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCPptkfC4QiLsDM9Z1ZMzqP15t9RTH1YbzwUG6onNmFSZjTJwC+hXfFIpjq05/cNh0/JSlGx0kbfXW0N6A36CYitscaEi44Y74gAQCURv/o3vjp7mCcPFsIck5KxYqL1Nsb+K1i5OHRiIozHj6pXxMgyJXch5k7YZYuNkDUpD2N9sIxJ/MTmAU+G9ch+Viid9/2ZO77IsIcYleELOSUygw41a3HSzp8IaGiQePTHJNnlzeitH+WYe12HZaJqNSYBWQ2DTPEfFb1SJVaVHlviQ3h/Q/sI2DwZiKRv3w7m4RDGtMYwjY3uKx5+ygVbuZmnq4YRw6CWXYv259yQN5/rHaDAgMBAAGjggIrMIICJzCB2AYIKwYBBQUHAQEEgcswgcgwSQYIKwYBBQUHMAKGPWh0dHA6Ly9jcmwxLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNQZXJzb25IaWdoLVNIQTI1Ni5jcnQwSQYIKwYBBQUHMAKGPWh0dHA6Ly9jcmwyLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNQZXJzb25IaWdoLVNIQTI1Ni5jcnQwMAYIKwYBBQUHMAGGJGh0dHA6Ly9vY3NwMS50ZXN0LmNvbW1maWRlcy5jb20vb2NzcDAdBgNVHQ4EFgQUF+na2XDY9NxZ96dc49a7l0ZiE44wDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBTHYG0lixim/tqGnERcka4JeF2DAzAXBgNVHSAEEDAOMAwGCmCEQgEdhxABAQEwgZcGA1UdHwSBjzCBjDBEoEKgQIY+aHR0cDovL2NybDEudGVzdC5jb21tZmlkZXMuY29tL0NvbW1maWRlc1BlcnNvbi1IaWdoLVNIQTI1Ni5jcmwwRKBCoECGPmh0dHA6Ly9jcmwyLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNQZXJzb24tSGlnaC1TSEEyNTYuY3JsMA4GA1UdDwEB/wQEAwIDODAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwGgYDVR0RBBMwEYEPdGVzdDEzQHRlc3QuY29tMA0GCSqGSIb3DQEBCwUAA4IBAQApxsJ7rV9i7zrkeiB/AJSZzFFcKimW8aUzr1VxsuVYD77+CLdWZ8MjXN2f7G0dy8lzKTtrOko3OMzh+VnJDX0h0gVW2fGjx2OrTVynEz11NAzEztblo+tWwfI7Y643YhTTGMg+U/i3aSegUzfvcy7W8YA3yqErKMQSWuAYGRrxiE6PKeTIP1ykQre/xCBwJnUU3MhEBx9wqzHJaIy1Kx5EElHe3mb1VOO7xneAFAPQuoCPFquf3M89VdsB2OOJ0SyvAa+4ufHBbTcAyNNVk+JOkXe5ZfhE1kUJlM/SPybgE/8T/Ka13hl5+p/NJWVyj3d90PkS/A1/EUc4jpeoAAvz";
    }

    //
    // Oberthur responses
    //

    public static T1cResponse<String> getGclOberthurRootCertificateResponse() {
        return getSuccessResponse(getGclOberthurRootCertificate());
    }

    public static T1cResponse<String> getGclOberthurAuthenticationCertificateResponse() {
        return getSuccessResponse(getGclOberthurAuthenticationCertificate());
    }

    public static T1cResponse<String> getGclOberthurSigningCertificateResponse() {
        return getSuccessResponse(getGclOberthurSigningCertificate());
    }

    public static T1cResponse<String> getGclOberthurEncryptionCertificateResponse() {
        return getSuccessResponse(getGclOberthurEncryptionCertificate());
    }

    public static T1cResponse<String> getGclOberthurIssuerCertificateResponse() {
        return getSuccessResponse(getGclOberthurIssuerCertificate());
    }

    public static T1cResponse<List<String>> getGclOberthurAuthenticationAlgoRefsResponse() {
        return getSuccessResponse(getGclOberthurAuthenticationAlgoRefs());
    }

    public static T1cResponse<List<String>> getGclOberthurSignAlgoRefsResponse() {
        return getSuccessResponse(getGclOberthurSignAlgoRefs());
    }

    public static T1cResponse<GclOberthurAllCertificates> getOberthurAllCertificatesResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclOberthurAllCertificates data = getGclOberthurAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("issuer-certificate")) data.setIssuerCertificate(null);
            if (!filterParams.contains("encryption-certificate")) data.setEncryptionCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclOberthurAllData> getGclOberthurAllDataResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclOberthurAllData data = getGclOberthurAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("root-certificate")) data.setRootCertificate(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("issuer-certificate")) data.setIssuerCertificate(null);
            if (!filterParams.contains("encryption-certificate")) data.setEncryptionCertificate(null);
        }
        return getSuccessResponse(data);
    }

    public static GclOberthurAllData getGclOberthurAllData() {
        return new GclOberthurAllData()
                .withRootCertificate(getGclOberthurRootCertificate())
                .withAuthenticationCertificate(getGclOberthurAuthenticationCertificate())
                .withSigningCertificate(getGclOberthurSigningCertificate())
                .withIssuerCertificate(getGclOberthurIssuerCertificate())
                .withEncryptionCertificate(getGclOberthurEncryptionCertificate());
    }

    public static GclOberthurAllCertificates getGclOberthurAllCertificates() {
        return new GclOberthurAllCertificates()
                .withRootCertificate(getGclOberthurRootCertificate())
                .withAuthenticationCertificate(getGclOberthurAuthenticationCertificate())
                .withSigningCertificate(getGclOberthurSigningCertificate())
                .withIssuerCertificate(getGclOberthurIssuerCertificate())
                .withEncryptionCertificate(getGclOberthurEncryptionCertificate());
    }

    public static List<String> getGclOberthurSignAlgoRefs() {
        return Arrays.asList("SHA1", "SHA256");
    }

    public static List<String> getGclOberthurAuthenticationAlgoRefs() {
        return Arrays.asList("SHA1", "SHA256");
    }

    public static String getGclOberthurRootCertificate() {
        return "MIIFujCCBKKgAwIBAgIIEVPWTD6qlpowDQYJKoZIhvcNAQELBQAwggEAMSIwIAYDVQQDExlDUE4gUm9vdCBTSEEyNTYgQ0EgLSBURVNUMUQwQgYDVQQLEztDb21tZmlkZXMgVHJ1c3QgRW52aXJvbm1lbnQoQykgVEVTVCAyMDEwIENvbW1maWRlcyBOb3JnZSBBUzErMCkGA1UECxMiQ1BOIFRFU1QgLSBGb3IgYXV0aG9yaXplZCB1c2Ugb25seTEvMC0GA1UECxMmQ1BOIFByaW1hcnkgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IFRFU1QxKTAnBgNVBAoTIENvbW1maWRlcyBOb3JnZSBBUyAtIDk4OCAzMTIgNDk1MQswCQYDVQQGEwJOTzAeFw0xMjEwMDIxMjUzNDRaFw0yMjEwMDMxMjUzNDRaMIIBADEiMCAGA1UEAxMZQ1BOIFJvb3QgU0hBMjU2IENBIC0gVEVTVDFEMEIGA1UECxM7Q29tbWZpZGVzIFRydXN0IEVudmlyb25tZW50KEMpIFRFU1QgMjAxMCBDb21tZmlkZXMgTm9yZ2UgQVMxKzApBgNVBAsTIkNQTiBURVNUIC0gRm9yIGF1dGhvcml6ZWQgdXNlIG9ubHkxLzAtBgNVBAsTJkNQTiBQcmltYXJ5IENlcnRpZmljYXRlIEF1dGhvcml0eSBURVNUMSkwJwYDVQQKEyBDb21tZmlkZXMgTm9yZ2UgQVMgLSA5ODggMzEyIDQ5NTELMAkGA1UEBhMCTk8wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC33PFTNbpzg1KUaphis0QyJ4e2Ka/o2wzFmCthw46bmx/mMKH7hNxefMX7YU/IHJ4W23izKyxhAqGO2tjNXe/kz2ww9AFJvWKJqiWtBEAGybDQRueCePcmESZJ2nB+YZf3Yl091AjQwD2VZXosCjV73SkBm5hiTalYk0qpsfWq+Q9waXOvQ3uFw2Eg9TQxVAN3h4zgkfpQary0vwBaK2bBBDGi9SNyTEO5YUGPZ+fAvIvCLGzYsvZn22+ZyJXo64jv4m7LfZkp929hRh/F97lAlKaRD/SGuxEo48OVUq4GL1IB9EuUKQfeRyBET4XHq9c0CNoE053Zl/ehM5sAckuzAgMBAAGjggEyMIIBLjBABggrBgEFBQcBAQQ0MDIwMAYIKwYBBQUHMAGGJGh0dHA6Ly9vY3NwMS50ZXN0LmNvbW1maWRlcy5jb20vb2NzcDAdBgNVHQ4EFgQUHpAN9SpjZKZqH0lRW5jPrnwhRHIwDwYDVR0TAQH/BAUwAwEB/zAfBgNVHSMEGDAWgBQekA31KmNkpmofSVFbmM+ufCFEcjCBiAYDVR0fBIGAMH4wPaA7oDmGN2h0dHA6Ly9jcmwxLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNSb290LVNIQTI1Ni5jcmwwPaA7oDmGN2h0dHA6Ly9jcmwyLnRlc3QuY29tbWZpZGVzLmNvbS9Db21tZmlkZXNSb290LVNIQTI1Ni5jcmwwDgYDVR0PAQH/BAQDAgEGMA0GCSqGSIb3DQEBCwUAA4IBAQB429FURBiQVTJyHLUsEAEAljKO7eyilDSSdBAu0iyIrA+1hXviQhalROsfBas/wZYvDd1xZQ80IlSHKrtqMf2+Jtl/2t2LMwFl+Ml7tA3/VCr2ClDWC2VTjUJxQ75Lo6QyOPqHnUZApWUkxxS03i89GI8XfAvtRU8OwKR8qTuWHLt16y3dPRtUVu58DGjYCeT97Tx0VnzHNBdWe7LCIms5gvTVZT845zcsedb0EmGo5hJMyhut4o9U9OcDHwoaF6GKPWupCoVxvZlqvSHQElqv2QzA1I8MsEAKDV3cqdJe5v/eL6Cg/qDdraVhCKRCTOefSW2OGfdxRzLzsBYjkCIx";
    }

    public static String getGclOberthurAuthenticationCertificate() {
        return "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";
    }

    public static String getGclOberthurSigningCertificate() {
        return "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";
    }

    public static String getGclOberthurIssuerCertificate() {
        return "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";
    }

    public static String getGclOberthurEncryptionCertificate() {
        return "MIIGAzCCBOugAwIBAgIIbqQmozkI4bYwDQYJKoZIhvcNAQELBQAwgeAxMzAxBgNVBAMTKkNvbW1maWRlcyBDUE4gUGVyc29uLUhpZ2ggU0hBMjU2IENBIC0gVEVTVDFGMEQGA1UECxM9Q29tbWZpZGVzIFRydXN0IEVudmlyb25tZW50KEMpIDIwMTQgQ29tbWZpZGVzIE5vcmdlIEFTIC0gVEVTVDEpMCcGA1UECxMgQ1BOIFBlcnNvbiBIaWdoIFNIQTI1NiBDQSAtIFRFU1QxKTAnBgNVBAoTIENvbW1maWRlcyBOb3JnZSBBUyAtIDk4OCAzMTIgNDk1MQswCQYDVQQGEwJOTzAeFw0xNjEwMTMwOTU0NTRaFw0yMjEwMDMxMjUzNDRaMHQxGDAWBgNVBAMTD0luZ2EgUHNhIEJlcmdlcjEeMBwGA1UEBRMVOTU3OC00NTEwLTAwMDAyRVNaRTNjMSswKQYDVQQKEyJTWUtFSFVTUEFSVE5FUiBIRiBURVNUIC0gODE0NjM3NjUxMQswCQYDVQQGEwJOTzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKYPudfFsCxEZkdLZ/UcPnq+8SzGHLRrRmwa6Y+6bi/x/iN4ucbWJuugxTLWXHiU8zFvbxgaoSe0At2lrUvMeQnA/d4RxrXXtAlWbep95WsXjKInPnOryPy2CNKQWKaRu2h74qzm9JbixfeiKCVGglOSkjVGLy5jvPFu+0ZuXnDa6QYipE7k8+/M3YchxWfz6X6WYfubXjuq2tzXjAZkjTIPcvY9ZTycf2rEfweJyf97q+MWbIlMeWMieyAAZ4scLzWTXu5mIqT6bni37wdwryyqOoubXSVTrxyvy2Nyb6kIl8l7dIW/Vl0gTUt8cAfr+AayEYTKlt1xzZmipI3y7tMCAwEAAaOCAiowggImMIHYBggrBgEFBQcBAQSByzCByDBJBggrBgEFBQcwAoY9aHR0cDovL2NybDEudGVzdC5jb21tZmlkZXMuY29tL0NvbW1maWRlc1BlcnNvbkhpZ2gtU0hBMjU2LmNydDBJBggrBgEFBQcwAoY9aHR0cDovL2NybDIudGVzdC5jb21tZmlkZXMuY29tL0NvbW1maWRlc1BlcnNvbkhpZ2gtU0hBMjU2LmNydDAwBggrBgEFBQcwAYYkaHR0cDovL29jc3AxLnRlc3QuY29tbWZpZGVzLmNvbS9vY3NwMB0GA1UdDgQWBBSWav40RJmPMof+VbUgT6MmNpnAJDAMBgNVHRMBAf8EAjAAMB8GA1UdIwQYMBaAFMdgbSWLGKb+2oacRFyRrgl4XYMDMBcGA1UdIAQQMA4wDAYKYIRCAR2HEAEBATCBlwYDVR0fBIGPMIGMMESgQqBAhj5odHRwOi8vY3JsMS50ZXN0LmNvbW1maWRlcy5jb20vQ29tbWZpZGVzUGVyc29uLUhpZ2gtU0hBMjU2LmNybDBEoEKgQIY+aHR0cDovL2NybDIudGVzdC5jb21tZmlkZXMuY29tL0NvbW1maWRlc1BlcnNvbi1IaWdoLVNIQTI1Ni5jcmwwDgYDVR0PAQH/BAQDAgM4MB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDBDAZBgNVHREEEjAQgQ5pbmdhQHZ2dGVzdC5ubzANBgkqhkiG9w0BAQsFAAOCAQEAI/F27XwDvPSJ6QzDWlHVTyNaR0euMelouPHmSpzYOwsQdW9tQP7b5zFrurTVnzGboXn4OjHqBFRuTtFhVPX4W0fXtu4UU8hPnAGX04opwMyLqC8AOPch30zUxCsWEjzPAxV9Bh49steoqJDgvBc5gAkkURwkbvc+biNK0qLSLjxlgXJQaeCNpniim61zaQpX1S6I/oFZ90zMDxvCcAscJmbZpkXtHDmeHQgRzbYC3c2SfQLAvFCq83BYnVH5lu32bHKP9SV+fBb6Yi7q9jC8P/6LMl8VgT/ajWTLtlQ0EU2RZmkY3cJxSt7VfT+USvy6vNxCsEN8sDmCHbdtVzv7gQ==";
    }

    //
    // PIV responses
    //

    public static T1cResponse<String> getGclPivAuthenticationCertificateResponse() {
        return getSuccessResponse(getGclPivAuthenticationCertificate());
    }

    public static T1cResponse<String> getGclPivSigningCertificateResponse() {
        return getSuccessResponse(getGclPivSigningCertificate());
    }

    public static T1cResponse<GclPivPrintedInformation> getGclPivPrintedInformationResponse() {
        return getSuccessResponse(getGclPivPrintedInformation());
    }

    public static GclPivPrintedInformation getGclPivPrintedInformation() {
        return new GclPivPrintedInformation()
                .withAgencyCardSerialNumber("12345")
                .withEmployeeAffiliation("CEO")
                .withExpirationDate("2020DEC01")
                .withIssuerIdentification("T1T")
                .withName("Thibaut Delhaye")
                .withOrganizationAffiliationLine1("")
                .withOrganizationAffiliationLine2("");
    }

    public static T1cResponse<GclPivFacialImage> getGclPivFacialImageResponse() {
        return getSuccessResponse(getGclPivFacialImage());
    }

    public static T1cResponse<GclPivAllData> getGclPivAllDataResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclPivAllData data = getGclPivAllData();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("printed-information")) data.setPrintedInformation(null);
            if (!filterParams.contains("authentication-certificate")) data.setAuthenticationCertificate(null);
            if (!filterParams.contains("signing-certificate")) data.setSigningCertificate(null);
            if (!filterParams.contains("facial-image")) data.setFacialImage(null);
        }
        return getSuccessResponse(data);
    }

    public static T1cResponse<GclPivAllCertificates> getGclPivAllCertificatesResponse(String filter) {
        List<String> filterParams = splitFilterParams(filter);
        GclPivAllCertificates certs = getGclPivAllCertificates();
        if (!filterParams.isEmpty()) {
            if (!filterParams.contains("authentication-certificate")) certs.setAuthenticationCertificate(null);
            if (!filterParams.contains("signing-certificate")) certs.setSigningCertificate(null);
        }
        return getSuccessResponse(certs);
    }

    public static T1cResponse<List<String>> getGclPivAuthenticationAlgoRefsResponse() {
        return getSuccessResponse(getGclPivAuthenticationAlgoRefs());
    }

    public static T1cResponse<List<String>> getGclPivSignAlgoRefsResponse() {
        return getSuccessResponse(getGclPivSignAlgoRefs());
    }

    public static List<String> getGclPivSignAlgoRefs() {
        return Collections.singletonList("SHA1");
    }

    public static List<String> getGclPivAuthenticationAlgoRefs() {
        return Arrays.asList("SHA1", "SHA256", "SHA512");
    }

    public static GclPivAllData getGclPivAllData() {
        return new GclPivAllData()
                .withAuthenticationCertificate(getGclPivAuthenticationCertificate())
                .withSigningCertificate(getGclPivSigningCertificate())
                .withPrintedInformation(getGclPivPrintedInformation())
                .withFacialImage(getGclPivFacialImage());
    }

    public static GclPivAllCertificates getGclPivAllCertificates() {
        return new GclPivAllCertificates()
                .withAuthenticationCertificate(getGclPivAuthenticationCertificate())
                .withSigningCertificate(getGclPivSigningCertificate());
    }

    public static String getGclPivAuthenticationCertificate() {
        //TODO - provide test certificates of PIV instead
        return getGclAventraAuthenticationCertificate();
    }

    public static String getGclPivSigningCertificate() {
        //TODO - provide test certificates of PIV instead
        return getGclAventraSigningCertificate();
    }

    public static GclPivFacialImage getGclPivFacialImage() {
        //TODO - provide actual PIV picture data
        return new GclPivFacialImage().withImage(getBeIdPicture());
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

    private static List<String> splitFilterParams(String filter) throws RestException {
        // To mock rest exceptions, if filter contains "throwException", we throw a RestException
        if (StringUtils.isEmpty(filter)) {
            return Collections.emptyList();
        }
        if (filter.contains("throwException"))
            throw ExceptionFactory.restException("request failed", 400, "https://localhost:10443/v1", null);
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