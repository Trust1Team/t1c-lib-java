package com.t1t.t1c.ds;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.AbstractRestClient;
import com.t1t.t1c.rest.DsRestClient;
import com.t1t.t1c.utils.UriUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class DsClient extends AbstractRestClient<DsRestClient> implements IDsClient {

    private static final Logger log = LoggerFactory.getLogger(DsClient.class);

    private LibConfig config;

    public DsClient(LibConfig config, DsRestClient httpClient) {
        super(httpClient);
        this.config = config;
    }

    @Override
    public String getUrl() {
        return config.getDsUri();
    }

    @Override
    public DsInfo getInfo() {
        return executeCall(getHttpClient().getInfo());
    }

    @Override
    public DsDevice getDevice(String deviceId) {
        return executeCall(getHttpClient().getDevice(deviceId));
    }

    @Override
    public String getJWT() {
        DsToken token = executeCall(getHttpClient().getJWT());
        return token == null ? null : token.getToken();
    }

    @Override
    public String refreshJWT(String token) {
        DsToken refreshedToken = executeCall(getHttpClient().refreshJWT(new DsTokenRefreshRequest().withOriginalJWT(token)));
        return refreshedToken == null ? null : refreshedToken.getToken();
    }

    @Override
    public String getPubKey() {
        DsPublicKey publicKeyResponse = executeCall(getHttpClient().getPubKey());
        return publicKeyResponse != null && publicKeyResponse.getSuccess() ? publicKeyResponse.getPubkey() : null;
    }

    @Override
    public String getDownloadLink(DsDownloadRequest request) {
        DsDownloadPath clientResponse = executeCall(getHttpClient().getDownloadLink(request));
        if (clientResponse != null && StringUtils.isNotBlank(clientResponse.getPath())) {
            return UriUtils.uriFinalSlashAppender(config.getGatewayUri()) + UriUtils.uriLeadingSlashRemover(clientResponse.getPath());
        }
        return null;
    }

    @Override
    public String register(String deviceId, DsDeviceRegistrationRequest request) {
        DsToken token = executeCall(getHttpClient().register(deviceId, request));
        return token == null ? null : token.getToken();
    }

    @Override
    public String sync(String deviceId, DsDeviceRegistrationRequest request) {
        DsToken token = executeCall(getHttpClient().register(deviceId, request));
        return token == null ? null : token.getToken();
    }
}