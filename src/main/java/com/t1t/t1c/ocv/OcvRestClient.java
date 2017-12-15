package com.t1t.t1c.ocv;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface OcvRestClient {

    @GET("challenge")
    Call<OcvChallengeRequest> getChallenge(@Query("digest") String digestAlgorithm);

    @POST("challenge")
    Call<OcvChallengeVerificationResponse> verifyChallenge(@Body OcvChallengeVerificationRequest request);

    @POST("certs/validate-chain")
    Call<OcvCertificateChainValidationResponse> validateCertificateChain(@Body OcvCertificateChainValidationRequest request);

}
