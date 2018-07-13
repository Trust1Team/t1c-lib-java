package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Luxembourg ID Container
 */
public interface GclLuxIdRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Query("filter") String filter) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Query("filter") String filter) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/biometric")
    Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/signature-image")
    Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Body GclAuthenticateOrSignData request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Body GclAuthenticateOrSignData request) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<List<String>>> getRootCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<List<DigestAlgorithm>>> getAvailableSignAlgos(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<List<DigestAlgorithm>>> getAvailableAuthenticateAlgos(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/pin-try-counter")
    Call<T1cResponse<Integer>> getPinTryCount(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Body GclPinTryCounterRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/change-pin")
    Call<T1cResponse<Boolean>> changePin(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Body GclChangePinRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/reset-pin")
    Call<T1cResponse<Boolean>> resetPin(@Path("containerId") String containerId, @Path("reader") String readerId, @HeaderMap Map<String, String> headerMap, @Body GclResetPinRequest request);
}
