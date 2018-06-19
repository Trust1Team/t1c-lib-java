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

    public OcvClient(OcvRestClient ocvRestClient) {
        this.ocvRestClient = ocvRestClient;
    }

    @Override
    public OcvChallengeRequest getChallenge(DigestAlgorithm digestAlgorithm) throws OcvClientException {
        try {
            return RestExecutor.executeCall(ocvRestClient.getChallenge(digestAlgorithm.toString().toLowerCase()), false);
        } catch (RestException ex) {
            throw ExceptionFactory.ocvException("Could not retrieve challenge", ex);
        }
    }

    @Override
    public OcvChallengeVerificationResponse verifyChallenge(OcvChallengeVerificationRequest request) throws OcvClientException {
        try {
            return RestExecutor.executeCall(ocvRestClient.verifyChallenge(request), false);
        } catch (RestException ex) {
            throw ExceptionFactory.ocvException("Could not verify challenge", ex);
        }
    }

    @Override
    public OcvCertificateChainValidationResponse validateCertificateChain(String... certificates) throws OcvClientException {
        if (certificates != null && certificates.length > 0) {
            List<OcvCertificate> orderedCertificates = new ArrayList<>();
            long order = 0;
            for (String certificate : certificates) {
                orderedCertificates.add(new OcvCertificate().withOrder(order).withCertificate(certificate));
                order++;
            }
            OcvCertificateChainValidationRequest request = new OcvCertificateChainValidationRequest().withCertificateChain(orderedCertificates);
            try {
                return RestExecutor.executeCall(ocvRestClient.validateCertificateChain(request), false);
            } catch (RestException ex) {
                throw ExceptionFactory.ocvException("Could not validate certificate chain", ex);
            }
        } else return null;
    }

    @Override
    public OcvSignatureValidationResponse validateSignature(String base64RawData, String base64Signature, DigestAlgorithm digestAlgorithm, String base64SignatureCertificate) throws OcvClientException {
        try {
            return RestExecutor.executeCall(ocvRestClient.validateSignature(new OcvSignatureValidationRequest().withRawData(base64RawData).withSignature(base64Signature).withDigest(digestAlgorithm.getStringValue()).withCertificate(base64SignatureCertificate)), false);
        } catch (RestException ex) {
            throw ExceptionFactory.ocvException("Could not validate signature", ex);
        }
    }
}