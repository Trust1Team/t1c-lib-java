package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclAdminClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.rest.GclUpdatePublicKeyRequest;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.GclAdminRestClient;
import com.t1t.t1c.rest.RestExecutor;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclAdminClient implements IGclAdminClient {
    private static final Logger log = LoggerFactory.getLogger(GclAdminClient.class);
    private GclAdminRestClient gclAdminRestClient;
    private LibConfig config;

    public GclAdminClient(GclAdminRestClient gclAdminRestClient, LibConfig config) {
        this.gclAdminRestClient = gclAdminRestClient;
        this.config = config;
    }

    @Override
    public String getUrl() {
        return config.getDsUri();
    }

    @Override
    public Boolean activate() throws GclAdminClientException {
        checkJwtValidity();
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.activate()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not activate GCL", ex);
        }
    }

    @Override
    public String getPublicKey() throws GclAdminClientException {
        checkJwtValidity();
        try {
            return RestExecutor.returnData(RestExecutor.executeCall(gclAdminRestClient.getPublicKey());
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not retrieve GCL public key", ex);
        }
    }

    @Override
    public Boolean setPublicKey(String publicKey) throws GclAdminClientException {
        checkJwtValidity();
        GclUpdatePublicKeyRequest request = new GclUpdatePublicKeyRequest().withCertificate(publicKey);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.setPublicKey(request)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not set GCL public key", ex);
        }
    }

    //TODO
    private void checkJwtValidity() {
        if (config.getEnvironment() != Environment.DEV) {
            JwtConsumer consumer = new JwtConsumerBuilder().setRequireExpirationTime().setSkipSignatureVerification().setSkipAllDefaultValidators().setDisableRequireSignature().setRelaxVerificationKeyValidation().build();
            String jwt = config.getJwt();
            if (StringUtils.isNotEmpty(jwt)) {
                try {
                    JwtContext context = consumer.process(jwt);
                    NumericDate refreshTreshold = NumericDate.now();
                    refreshTreshold.addSeconds(-240);
                    if (context.getJwtClaims().getExpirationTime().isOnOrAfter(refreshTreshold)) {
                        jwt = ConnectionFactory.getDsClient().refreshJWT(jwt);
                        if (StringUtils.isNotEmpty(jwt)) {
                            config.setJwt(jwt);
                            ConnectionFactory.setConfig(config);
                            setHttpClient(ConnectionFactory.getGclAdminRestClient());
                        }
                    }
                } catch (InvalidJwtException | MalformedClaimException ex) {
                    log.error("Token invalid: ", ex);
                    jwt = ConnectionFactory.getDsClient().getJWT();
                    if (StringUtils.isNotEmpty(jwt)) {
                        config.setJwt(jwt);
                        ConnectionFactory.setConfig(config);
                        setHttpClient(ConnectionFactory.getGclAdminRestClient());
                    }
                }
            } else {
                jwt = ConnectionFactory.getDsClient().getJWT();
                if (StringUtils.isNotEmpty(jwt)) {
                    config.setJwt(jwt);
                    ConnectionFactory.setConfig(config);
                    setHttpClient(ConnectionFactory.getGclAdminRestClient());
                }
            }
        }
    }
}