package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for EMV Container
 */
public interface GclEmvRestClient {

    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/applications")
    Call<T1cResponse<List<GclEmvApplication>>> getEmvApplications(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/application-data")
    Call<T1cResponse<GclEmvApplicationData>> getEmvApplicationData(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/issuer-public-key-certificate")
    Call<T1cResponse<GclEmvPublicKeyCertificate>> getEmvIssuerPublicKeyCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclEmvAidRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/icc-public-key-certificate")
    Call<T1cResponse<GclEmvPublicKeyCertificate>> getEmvIccPublicKeyCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclEmvAidRequest request) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclEmvAllData>> getEmvAllData(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclEmvAllData>> getEmvAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;
}
