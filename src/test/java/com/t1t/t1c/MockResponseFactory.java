package com.t1t.t1c;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

import java.io.IOException;
import java.net.URL;
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
    private static final String JSON_MEDIATYPE = "application/json";
    private static final String RESPONSE_RESOURCE_PATH = "/responses/";

    private static final Gson GSON = new Gson();

    private MockResponseFactory() {
    }

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
        GclEmvApplication app = new GclEmvApplication().withAid("123").withName("name").withPriority(0);
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

    public static T1cResponse<GclEmvCertificate> getEmvCertResponse(GclEmvAidRequest request) {
        GclEmvCertificate cert = new GclEmvCertificate().withData("data").withExponent("exponent").withRemainder("remainder");
        return getResponse(cert);
    }

    public static T1cResponse<Boolean> getBooleanResponse() {
        return getResponse(true);
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
        return getResponse(GSON.fromJson(json, GclMobibCardIssuing.class));
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
        return getResponse(contracts);
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
        return getResponse(GSON.fromJson(json, GclMobibAllData.class));
    }

    public static T1cResponse<GclOcraAllData> getGclOcraAllDataResponse() {
        return getResponse(new GclOcraAllData().withCounter("Data Counter"));
    }

    public static T1cResponse<String> getStringResponse() {
        return getResponse("This is a String response");
    }

    public static T1cResponse<List<String>> getStringListResponse() {
        return getResponse(Arrays.asList("This is a String response", "This is a second String response"));
    }

    public static T1cResponse<GclSafeNetInfo> getSafeNetInfoResponse() {
        String json = "{\n" +
                "\"cryptoki_version\": \"2.20\",\n" +
                "\"manufacturer_id\": \"SafeNet, Inc.\",\n" +
                "\"flags\": 7,\n" +
                "\"library_description\": \"SafeNet eToken PKCS#11\",\n" +
                "\"library_version\": \"9.1\"\n" +
                "}";
        return getResponse(GSON.fromJson(json, GclSafeNetInfo.class));
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
        return getResponse(slots);
    }

    private static <T> T1cResponse<T> getResponse(T data) {
        return new T1cResponse<T>().withSuccess(true).withData(data);
    }

    private static T1cCertificate getT1cCertificate() {
        return new T1cCertificate().withBase64(getCertificateResponse().getData());
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