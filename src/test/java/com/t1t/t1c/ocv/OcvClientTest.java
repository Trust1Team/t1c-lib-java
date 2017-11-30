package com.t1t.t1c.ocv;

import com.t1t.t1c.AbstractTestClass;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.rest.OcvChallengeVerificationRequest;
import com.t1t.t1c.rest.RestServiceBuilder;
import com.t1t.t1c.factories.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import t1c.ocv.OcvChallengeVerificationRequest;

import static org.junit.Assert.assertNotNull;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
public class OcvClientTest extends AbstractTestClass {


    @Test
    public void getChallenge() throws Exception {
        assertNotNull(getClient().getOcvClient().getChallenge(DigestAlgorithm.SHA1));
    }

    @Test
    public void verifyChallenge() throws Exception {
        assertNotNull(getClient().getOcvClient().verifyChallenge(new OcvChallengeVerificationRequest()
                .withDigestAlgorithm("SHA1")
                .withHash("xxxxxx")
                .withBase64Signature("xxxxxxx")
                .withBase64Certificate("xxxxxx")));
    }

    @Test
    public void validateCertificateChain() throws Exception {
        assertNotNull(getClient().getOcvClient().validateCertificateChain("xxxxxxx", "xxxxxxx", "xxxxxxx"));
    }

}