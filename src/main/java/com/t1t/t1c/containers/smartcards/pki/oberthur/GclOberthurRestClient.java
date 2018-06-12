package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Oberthur Cosmo Container
 */
public interface GclOberthurRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOberthurAllData>> getAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclOberthurAllData>> getAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<List<String>>> getAuthenticationAlgoRefs(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<List<String>>> getSignAlgoRefs(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSigningCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/issuer")
    Call<T1cResponse<String>> getIssuerCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/encryption")
    Call<T1cResponse<String>> getEncryptionCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<List<DigestAlgorithm>>> getAvailableSignAlgos(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<List<DigestAlgorithm>>> getAvailableAuthenticateAlgos(@Path("containerId") String containerId, @Path("reader") String readerId);

}
