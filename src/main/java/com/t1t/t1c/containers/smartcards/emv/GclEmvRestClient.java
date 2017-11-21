package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.containers.ContainerRestClient;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for EMV Container
 */
public interface GclEmvRestClient extends ContainerRestClient{
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
}
