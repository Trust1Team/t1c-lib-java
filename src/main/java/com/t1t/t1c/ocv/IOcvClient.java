package com.t1t.t1c.ocv;

import com.t1t.t1c.exceptions.OcvClientException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.rest.OcvCertificateChainValidationResponse;
import com.t1t.t1c.model.rest.OcvChallengeRequest;
import com.t1t.t1c.model.rest.OcvChallengeVerificationRequest;
import com.t1t.t1c.model.rest.OcvChallengeVerificationResponse;
import t1c.ocv.OcvCertificateChainValidationResponse;
import t1c.ocv.OcvChallengeRequest;
import t1c.ocv.OcvChallengeVerificationRequest;
import t1c.ocv.OcvChallengeVerificationResponse;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IOcvClient {

    OcvChallengeRequest getChallenge(DigestAlgorithm digestAlgorithm) throws OcvClientException;

    OcvChallengeVerificationResponse verifyChallenge(OcvChallengeVerificationRequest request) throws OcvClientException;

    OcvCertificateChainValidationResponse validateCertificateChain(String... certificates) throws OcvClientException;

}
