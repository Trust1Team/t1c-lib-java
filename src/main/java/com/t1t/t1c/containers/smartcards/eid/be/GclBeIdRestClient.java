package com.t1t.t1c.containers.smartcards.eid.be;

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
 * Specific GCL interface for Belgian eID Container
 */
public interface GclBeIdRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/rn")
    Call<T1cResponse<GclBeIdRn>> getRnData(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/address")
    Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getBeIdPicture(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getCitizenCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/rrn")
    Call<T1cResponse<String>> getRrnCertificate(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<List<DigestAlgorithm>>> getAvailableSignAlgos(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<List<DigestAlgorithm>>> getAvailableAuthenticateAlgos(@Path("containerId") String containerId, @Path("reader") String readerId);
}