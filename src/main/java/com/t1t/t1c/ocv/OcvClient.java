package com.t1t.t1c.ocv;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.OcvClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.rest.RestExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class OcvClient implements IOcvClient {
    private OcvRestClient ocvRestClient;

    public OcvClient(final OcvRestClient ocvRestClient) {
        this.ocvRestClient = ocvRestClient;
    }

    @Override
    public OcvChallengeRequest getChallenge(final DigestAlgorithm digestAlgorithm) throws OcvClientException {
        try {
            return RestExecutor.executeCall(ocvRestClient.getChallenge(digestAlgorithm.toString().toLowerCase()), false);
        } catch (final RestException ex) {
            throw ExceptionFactory.ocvException("Could not retrieve challenge", ex);
        }
    }

    @Override
    public OcvChallengeVerificationResponse verifyChallenge(final OcvChallengeVerificationRequest request) throws OcvClientException {
        try {
            return RestExecutor.executeCall(ocvRestClient.verifyChallenge(request), false);
        } catch (final RestException ex) {
            throw ExceptionFactory.ocvException("Could not verify challenge", ex);
        }
    }

    @Override
    public OcvCertificateChainValidationResponse validateCertificateChain(final String... certificates) throws OcvClientException {
        if (certificates != null && certificates.length > 0) {
            final List<OcvCertificate> orderedCertificates = new ArrayList<>();
            long order = 0;
            for (final String certificate : certificates) {
                orderedCertificates.add(new OcvCertificate().withOrder(order).withCertificate(certificate));
                order++;
            }
            final OcvCertificateChainValidationRequest request = new OcvCertificateChainValidationRequest().withCertificateChain(orderedCertificates);
            try {
                return RestExecutor.executeCall(ocvRestClient.validateCertificateChain(request), false);
            } catch (final RestException ex) {
                throw ExceptionFactory.ocvException("Could not validate certificate chain", ex);
            }
        } else return null;
    }

    @Override
    public OcvSignatureValidationResponse validateSignature(final String base64RawData, final String base64Signature, final DigestAlgorithm digestAlgorithm, final String base64SignatureCertificate) throws OcvClientException {
        try {
            return RestExecutor.executeCall(ocvRestClient.validateSignature(new OcvSignatureValidationRequest().withRawData(base64RawData).withSignature(base64Signature).withDigest(digestAlgorithm.getStringValue()).withCertificate(base64SignatureCertificate)), false);
        } catch (final RestException ex) {
            throw ExceptionFactory.ocvException("Could not validate signature", ex);
        }
    }
}