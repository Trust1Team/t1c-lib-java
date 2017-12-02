package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Luxembourg ID Container
 */
public interface GclLuxIdRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/biometric")
    Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/signature-image")
    Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Body GclVerifyPinRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/authenticate")
    Call<T1cResponse<String>> authenticate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/sign")
    Call<T1cResponse<String>> sign(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Body GclAuthenticateOrSignData request);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/non-repudiation")
    Call<T1cResponse<String>> getNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root")
    Call<T1cResponse<List<String>>> getRootCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);
}
