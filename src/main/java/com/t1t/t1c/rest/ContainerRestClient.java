package com.t1t.t1c.rest;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ContainerRestClient {

    String CONTAINER_CONTEXT_PATH = "plugins/";
    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{readerId}";

    //
    // Generic methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<List<String>>> getRootCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getCitizenCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSigningCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/issuer")
    Call<T1cResponse<String>> getIssuerCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/encryption")
    Call<T1cResponse<String>> getEncryptionCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclVerifyPinRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclAuthenticateOrSignData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclAuthenticateOrSignData request);

    //
    // Generic methods with PIN query parameter
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<List<String>>> getRootCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getCitizenCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSigningCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/issuer")
    Call<T1cResponse<String>> getIssuerCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/encryption")
    Call<T1cResponse<String>> getEncryptionCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Body GclVerifyPinRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request);

    //
    // PT ID Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/id")
    Call<T1cResponse<GclPtIdData>> getPtIdData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("photo") boolean includePhoto);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/photo")
    Call<T1cResponse<String>> getPtIdPhoto(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root-authentication")
    Call<T1cResponse<String>> getRootAuthenticationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root-non-repudiation")
    Call<T1cResponse<String>> getRootNonRepudiationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    //
    // EMV Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/applications")
    Call<T1cResponse<List<GclEmvApplication>>> getEmvApplications(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/application-data")
    Call<T1cResponse<GclEmvApplicationData>> getEmvApplicationData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/issuer-public-key-certificate")
    Call<T1cResponse<GclEmvCertificate>> getEmvIssuerPublicKeyCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclEmvAidRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/icc-public-key-certificate")
    Call<T1cResponse<GclEmvCertificate>> getEmvIccPublicKeyCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclEmvAidRequest request);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclEmvAllData>> getEmvAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclEmvAllData>> getEmvAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    //
    // MOBIB Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/status")
    Call<T1cResponse<Boolean>> getMobibStatus(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getMobibPicture(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/card-issuing")
    Call<T1cResponse<GclMobibCardIssuing>> getMobibCardIssuing(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/contracts")
    Call<T1cResponse<List<GclMobibContract>>> getMobibContracts(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclMobibAllData>> getMobibAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclMobibAllData>> getMobibAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    //
    // OCRA Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> ocraChallenge(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclOcraChallengeData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> getOcraReadCounter(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclVerifyPinRequest request);

    //
    // SafeNet Container specific methods
    //

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<List<String>>> getSafeNetCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclSafeNetRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclSafeNetInfo>> getSafeNetInfo(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclSafeNetRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/slots")
    Call<T1cResponse<List<GclSafeNetSlot>>> getSafeNetSlots(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclSafeNetRequest request, @Query("token-present") Boolean tokenPresent);
}
