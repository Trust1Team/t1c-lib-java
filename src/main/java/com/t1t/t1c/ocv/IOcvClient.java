package com.t1t.t1c.ocv;

import com.t1t.t1c.exceptions.OcvClientException;
import com.t1t.t1c.model.DigestAlgorithm;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IOcvClient {

    OcvChallengeRequest getChallenge(DigestAlgorithm digestAlgorithm) throws OcvClientException;

    OcvChallengeVerificationResponse verifyChallenge(OcvChallengeVerificationRequest request) throws OcvClientException;

    OcvCertificateChainValidationResponse validateCertificateChain(String... certificates) throws OcvClientException;

}
