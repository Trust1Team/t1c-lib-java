package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.containers.ContainerRestClient;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Belgian eID Container
 */
public interface GclBeidRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/rn")
    Call<T1cResponse<GclBeIdRn>> getRnData(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/address")
    Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getBeIdPicture(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclAuthenticateOrSignData request);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<String>> getRootCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/citizen")
    Call<T1cResponse<String>> getCitizenCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/rrn")
    Call<T1cResponse<String>> getRrnCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);
}