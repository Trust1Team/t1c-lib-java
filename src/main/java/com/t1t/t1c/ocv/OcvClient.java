package com.t1t.t1c.ocv;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.OcvClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.AbstractRestClient;
import com.t1t.t1c.rest.OcvRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class OcvClient extends AbstractRestClient<OcvRestClient> implements IOcvClient {

    private static final Logger log = LoggerFactory.getLogger(OcvClient.class);

    public OcvClient(OcvRestClient httpClient) {
        super(httpClient);
    }

    @Override
    public OcvChallengeRequest getChallenge(DigestAlgorithm digestAlgorithm) throws OcvClientException {
        try {
            return executeCall(getHttpClient().getChallenge(digestAlgorithm.toString().toLowerCase()));
        } catch (RestException ex) {
            throw ExceptionFactory.ocvException("Could not retrieve challenge", ex);
        }
    }

    @Override
    public OcvChallengeVerificationResponse verifyChallenge(OcvChallengeVerificationRequest request) throws OcvClientException {
        try {
            return executeCall(getHttpClient().verifyChallenge(request));
        } catch (RestException ex) {
            throw ExceptionFactory.ocvException("Could not verify challenge", ex);
        }
    }

    @Override
    public OcvCertificateChainValidationResponse validateCertificateChain(String... certificates) throws OcvClientException {
        if (certificates != null && certificates.length > 0) {
            List<OcvCertificate> orderedCertificates = new ArrayList<>();
            int order = 0;
            for (String certificate : certificates) {
                orderedCertificates.add(new OcvCertificate().withOrder(order).withCertificate(certificate));
                order++;
            }
            OcvCertificateChainValidationRequest request = new OcvCertificateChainValidationRequest().withCertificateChain(orderedCertificates);
            try {
                return executeCall(getHttpClient().validateCertificateChain(request));
            } catch (RestException ex) {
                throw ExceptionFactory.ocvException("Could not validate certificate chain", ex);
            }
        } else return null;
    }
}