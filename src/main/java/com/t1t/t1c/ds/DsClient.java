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
public class DsClient extends AbstractRestClient {

    private static final Logger log = LoggerFactory.getLogger(DsClient.class);

    private DsRestClient httpClient;
    private LibConfig config;

    public DsClient(LibConfig config, DsRestClient httpClient) {
        this.config = config;
        this.httpClient = httpClient;
    }

    public String getUrl() {
        return config.getDsUri();
    }

    public DsInfoResponse getInfo() {
        return executeCall(httpClient.getInfo());
    }

    public DsDeviceResponse getDevice(String deviceId) {
        return executeCall(httpClient.getDevice(deviceId));
    }
    public String getJWT() {
        DsToken token = executeCall(httpClient.getJWT());
        return token == null ? null : token.getToken();
    }

    public String refreshJWT(DsToken token) {
        DsToken refreshedToken = executeCall(httpClient.refreshJWT(token));
        return refreshedToken == null ? null : refreshedToken.getToken();
    }

    public String getPubKey() {
        DsPublicKeyResponse publicKeyResponse = executeCall(httpClient.getPubKey());
        return publicKeyResponse != null && publicKeyResponse.getSuccess() ? publicKeyResponse.getPubkey() : null;
    }

    public String getDownloadLink(DsDownloadRequest request) {
        DsDownloadResponse clientResponse = executeCall(httpClient.getDownloadLink(request));
        if (clientResponse != null && StringUtils.isNotBlank(clientResponse.getPath())) {
            return UriUtils.uriFinalSlashAppender(config.getGatewayUri()) + UriUtils.uriLeadingSlashRemover(clientResponse.getPath());
        }
        return null;
    }

    public String register(String deviceId, DsDeviceRegistrationRequest request) {
        DsToken token = executeCall(httpClient.register(deviceId, request));
        return token == null ? null : token.getToken();
    }

    public String sync(String deviceId, DsDeviceRegistrationRequest request) {
        DsToken token = executeCall(httpClient.register(deviceId, request));
        return token == null ? null : token.getToken();
    }
}