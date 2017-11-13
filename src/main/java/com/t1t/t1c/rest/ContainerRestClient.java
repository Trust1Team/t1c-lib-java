package com.t1t.t1c.rest;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ContainerRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{readerId}";

    //
    // Generic methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

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
    Call<T1cResponse<String>> getSecuredRootCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getSecuredCitizenCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getSecuredAuthenticationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getSecuredNonRepudiationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSecuredSigningCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/issuer")
    Call<T1cResponse<String>> getSecuredIssuerCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/encryption")
    Call<T1cResponse<String>> getSecuredEncryptionCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPinSecured(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Body GclVerifyPinRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPinSecured(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticateSecured(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> signSecured(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request);

    //
    // BE ID Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/rn")
    Call<T1cResponse<GclBeIdRn>> getRnData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/address")
    Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getBeIdPicture(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/rrn")
    Call<T1cResponse<String>> getRrnCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIDAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIDAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    //
    // Lux ID Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/biometric")
    Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/signature-image")
    Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    //
    // LuxTrust Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/activate")
    Call<T1cResponse<Object>> isLuxTrustActivated(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    //
    // DNIE Container specific methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclDnieInfo>> getDnieInfo(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclDnieAllData>> getDnieAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclDnieAllData>> getDnieAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/intermediate")
    Call<T1cResponse<String>> getIntermediateCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

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
}
