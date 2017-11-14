package com.t1t.t1c.mock;

import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.OcvRestClient;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.Arrays;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockOcvRestClient implements OcvRestClient {

    private final BehaviorDelegate<OcvRestClient> delegate;

    public MockOcvRestClient(BehaviorDelegate<OcvRestClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<OcvChallengeRequest> getChallenge(String digestAlgorithm) {
        return delegate.returningResponse(new OcvChallengeRequest().withDigestAlgorithm(digestAlgorithm).withHash("I2e+u/sgy7fYgh+DWA0p2jzXQ7E=")).getChallenge(digestAlgorithm);
    }

    @Override
    public Call<OcvChallengeVerificationResponse> verifyChallenge(OcvChallengeVerificationRequest request) {
        return delegate.returningResponse(new OcvChallengeVerificationResponse().withHash("I2e+u/sgy7fYgh+DWA0p2jzXQ7E=").withResult(true).withDigestAlgorithm(request.getDigestAlgorithm())).verifyChallenge(request);
    }

    @Override
    public Call<OcvCertificateChainValidationResponse> validateCertificateChain(OcvCertificateChainValidationRequest request) {
        return delegate.returningResponse(new OcvCertificateChainValidationResponse()
                .withCrlResponse(new OcvCrlResponse().withCrlLocations(Arrays.asList("string", "string")).withIssuerCertificate("String").withProductionDate("01/01/2000").withSignatureAlgorithm("sha1").withStatus(true).withVersion("1.0"))
                .withOcspResponse(new OcvOcspResponse().withOcspLocation("string").withStatus(true))).validateCertificateChain(request);
    }
}