package com.t1t.t1c.ocv;

import com.t1t.t1c.exceptions.OcvClientException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.rest.*;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IOcvClient {

    OcvChallengeRequest getChallenge(DigestAlgorithm digestAlgorithm) throws OcvClientException;

    OcvChallengeVerificationResponse verifyChallenge(OcvChallengeVerificationRequest request) throws OcvClientException;

    OcvCertificateChainValidationResponse validateCertificateChain(String... certificates) throws OcvClientException;

}
