package com.t1t.t1c.ocv;

import com.t1t.t1c.exceptions.OcvClientException;
import com.t1t.t1c.model.DigestAlgorithm;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IOcvClient {

    /**
     * Generate a challenge hash for the requested digest algorithm
     *
     * @param digestAlgorithm the digest algorithm
     * @return the challenge
     * @throws OcvClientException
     */
    OcvChallengeRequest getChallenge(DigestAlgorithm digestAlgorithm) throws OcvClientException;

    /**
     * Validate a certificate chain. The chain must be built up with the leaf certificate as the first certificate.
     *
     * @param certificates the certicates to validate, ordered
     * @return the validation result
     * @throws OcvClientException
     */
    OcvCertificateChainValidationResponse validateCertificateChain(String... certificates) throws OcvClientException;

    /**
     * Validate a signature
     *
     * @param base64RawData              the raw data
     * @param base64Signature            the signature
     * @param digestAlgorithm            the digest algorithm
     * @param base64SignatureCertificate the certificate
     * @return the validation result
     * @throws OcvClientException
     */
    OcvSignatureValidationResponse validateSignature(String base64RawData, String base64Signature, DigestAlgorithm digestAlgorithm, String base64SignatureCertificate) throws OcvClientException;

    /**
     * Verify the signed challenge
     *
     * @param request the verification request
     * @return the verification results
     * @throws OcvClientException
     */
    OcvChallengeVerificationResponse verifyChallenge(OcvChallengeVerificationRequest request) throws OcvClientException;

}
