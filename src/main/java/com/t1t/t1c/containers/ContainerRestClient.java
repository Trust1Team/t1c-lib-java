package com.t1t.t1c.containers;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
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
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    //
    // Generic methods
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<List<String>>> getRootCertificates(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getCitizenCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSigningCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/issuer")
    Call<T1cResponse<String>> getIssuerCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/encryption")
    Call<T1cResponse<String>> getEncryptionCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request) throws RestException;

    //
    // Generic methods with PIN query parameter
    //

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<List<String>>> getRootCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getCitizenCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSigningCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/issuer")
    Call<T1cResponse<String>> getIssuerCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/encryption")
    Call<T1cResponse<String>> getEncryptionCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Body GclVerifyPinRequest request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request) throws RestException;

}
