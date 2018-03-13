package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
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
 * Specific GCL interface for PIV (Personal Identity Verification) Container
 */
public interface GclPivRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @POST(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPivAllData>> getAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request, @Query("filter") String filter) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPivAllCertificates>> getAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request, @Query("filter") String filter) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/printed-information")
    Call<T1cResponse<GclPivPrintedInformation>> getPrintedInformation(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/facial-image")
    Call<T1cResponse<GclPivFacialImage>> getFacialImage(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/authentication")
    Call<T1cResponse<String>> getAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/signing")
    Call<T1cResponse<String>> getSigningCertificate(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

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

}
