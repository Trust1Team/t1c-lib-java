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
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdBiometric;
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

    public static T1cResponse<List<GclReader>> getListOfEmptyReaders() {
        return new T1cResponse<List<GclReader>>().withSuccess(true).withData(Collections.singletonList(getReaderWithCard(null)));
    }

    public static T1cResponse<GclReader> getReaderWithCardId(String readerId) {
        return new T1cResponse<GclReader>()
                .withSuccess(true)
                .withData(getReaderWithCard(ContainerType.valueOfId(readerId)));
    }

    public static T1cResponse<List<GclContainer>> getAllContainers() {
        List<GclContainer> containers = new ArrayList<>();
        for (ContainerType type : ContainerType.values()) {
            containers.add(new GclContainer().withId(type.getId()).withName(type.getId()).withVersion("1.0"));
        }
        return new T1cResponse<List<GclContainer>>().withSuccess(true).withData(containers);
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

    public static T1cResponse<GclLuxIdBiometric> getLuxIdBiometricResponse() {
        return new T1cResponse<GclLuxIdBiometric>()
                .withSuccess(true)
                .withData(new GclLuxIdBiometric()
                        .withDocumentNumber("1")
                        .withDocumentType("1")
                        .withFirstName("John")
                        .withLastName("Doe")
                        .withGender("M")
                        .withIssuingState("Luxembourg")
                        .withNationality("Luxembourgian")
                        .withValidityStartDate("01.01.2000")
                        .withValidityEndDate("01.01.2100")
                        .withBirthDate("00.00.00"));
    }

    public static T1cResponse<GclLuxIdAllCertificates> getLuxIdAllCertsResponse() {
        String cert = getT1cCertificate().getBase64();
        return new T1cResponse<GclLuxIdAllCertificates>().withSuccess(true)
                .withData(new GclLuxIdAllCertificates()
                        .withRootCertificates(Arrays.asList(cert, cert))
                        .withNonRepudiationCertificate(cert)
                        .withAuthenticationCertificate(cert));
    }

    public static T1cResponse<GclLuxTrustAllData> getLuxTrustAllDataResponse() {
        String cert = getCertificateResponse().getData();
        return new T1cResponse<GclLuxTrustAllData>().withSuccess(true)
                .withData(new GclLuxTrustAllData()
                        .withRootCertificates(Arrays.asList(cert, cert))
                        .withSigningCertificate(cert)
                        .withAuthenticationCertificate(cert));
    }

    public static T1cResponse<GclLuxTrustAllCertificates> getLuxTrustAllCertsResponse() {
        String cert = getCertificateResponse().getData();
        return new T1cResponse<GclLuxTrustAllCertificates>().withSuccess(true)
                .withData(new GclLuxTrustAllCertificates()
                        .withRootCertificates(Arrays.asList(cert, cert))
                        .withSigningCertificate(cert)
                        .withAuthenticationCertificate(cert));
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

    public static GclReader getReaderWithCard(ContainerType type) {
        GclReader reader = new GclReader().withName("MockedReader").withPinpad(false);
        if (type == null) {
            reader = reader.withCard(null).withId("no-card");
        } else {
            reader.setId(type.getId());
            switch (type) {
                case AVENTRA:
                    reader = reader.withCard(new GclCard().withAtr("3BF51800008131FE454D794549449A").withDescription(type.getCardDescriptions()));
                case BEID:
                    reader = reader.withCard(new GclCard().withAtr("3B9813400AA503010101AD1311").withDescription(type.getCardDescriptions()));
                case DNIE:
                    reader = reader.withCard(new GclCard().withAtr("3B7F380000006A444E496520024C340113039000").withDescription(type.getCardDescriptions()));
                case EMV:
                    reader = reader.withCard(new GclCard().withAtr("3BE700FF8131FE454430382E30203657").withDescription(type.getCardDescriptions()));
                case EST:
                    reader = reader.withCard(new GclCard().withAtr("3BFE1800008031FE454573744549442076657220312E30A8").withDescription(type.getCardDescriptions()));
                case LUXID:
                    reader = reader.withCard(new GclCard().withAtr("3B8B800100640411010131800090005A").withDescription(type.getCardDescriptions()));
                case LUXTRUST:
                    reader = reader.withCard(new GclCard().withAtr("3B6D000080318065B08302047E83009000").withDescription(type.getCardDescriptions()));
                case MOBIB:
                    reader = reader.withCard(new GclCard().withAtr("3B6F0000805A2C23C310100571034232829000").withDescription(type.getCardDescriptions()));
                case OBERTHUR:
                    reader = reader.withCard(new GclCard().withAtr("3B7B1800000031C06490E31100829000").withDescription(type.getCardDescriptions()));
                case OCRA:
                    reader = reader.withCard(new GclCard().withAtr("OCRA").withDescription(type.getCardDescriptions()));
                case PIV:
                    reader = reader.withCard(new GclCard().withAtr("3BDB9600801F030031C064B0F310000F900088").withDescription(type.getCardDescriptions()));
                case PT:
                    reader = reader.withCard(new GclCard().withAtr("3B7D95000080318065B08311----83009000").withDescription(type.getCardDescriptions()));
                case SAFENET:
                    reader = reader.withCard(new GclCard().withAtr("SAFENET").withDescription(type.getCardDescriptions()));
            }
        }
        return reader;
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