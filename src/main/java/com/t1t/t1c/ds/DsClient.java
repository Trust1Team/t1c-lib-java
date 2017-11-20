package com.t1t.t1c.ds;

import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.DsPublicKeyEncoding;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.rest.DsRestClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.utils.UriUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class DsClient extends RestExecutor<DsRestClient> implements IDsClient {

    private static final Logger log = LoggerFactory.getLogger(DsClient.class);

    public DsClient(DsRestClient httpClient) {
        super(httpClient);
    }

    @Override
    public String getUrl() {
        return ConnectionFactory.getConfig().getDsUri();
    }

    @Override
    public DsInfo getInfo() throws DsClientException {
        try {
            return executeCall(getHttpClient().getInfo());
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not retrieve Distribution Service info", ex);
        }
    }

    @Override
    public DsDevice getDevice(String deviceId) throws DsClientException {
        try {
            return executeCall(getHttpClient().getDevice(deviceId));
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not retrieve device info from Distribution Service", ex);
        }
    }

    @Override
    public String getJWT() throws DsClientException {
        try {
            return executeCall(getHttpClient().getJWT()).getToken();
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not retrieve JWT from Distribution Service", ex);
        }
    }

    @Override
    public String refreshJWT(String token) throws DsClientException {
        try {
            return executeCall(getHttpClient().refreshJWT(new DsTokenRefreshRequest().withOriginalJWT(token))).getToken();
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not refresh JWT on Distribution Service", ex);
        }
    }

    @Override
    public String getPublicKey() throws DsClientException {
        return getPublicKey(null);
    }

    @Override
    public String getPublicKey(DsPublicKeyEncoding encoding) throws DsClientException {
        String encodingParam;
        if (encoding == null) {
            encodingParam = null;
        } else {
            encodingParam = encoding.getQueryParamValue();
        }
        try {
            DsPublicKey publicKeyResponse = executeCall(getHttpClient().getPubKey(encodingParam));
            return publicKeyResponse != null && publicKeyResponse.getSuccess() ? publicKeyResponse.getPubkey() : null;
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not retrieve GCL public key", ex);
        }
    }

    @Override
    public String getDownloadLink(PlatformInfo info) throws DsClientException {
        try {
            DsDownloadRequest request = new DsDownloadRequest()
                    .withOs(new DsOs()
                            .withArchitecture(info.getOs().getArchitecture())
                            .withName(info.getOs().getName())
                            .withVersion(info.getOs().getVersion()));
            DsDownloadPath clientResponse = executeCall(getHttpClient().getDownloadLink(request));
            if (StringUtils.isNotBlank(clientResponse.getPath())) {
                return UriUtils.uriFinalSlashAppender(ConnectionFactory.getConfig().getGatewayUri()) + UriUtils.uriLeadingSlashRemover(clientResponse.getPath());
            } else return null;
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not retrieve download link from Distribution Service", ex);
        }
    }

    @Override
    public String register(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException {
        try {
            return executeCall(getHttpClient().register(deviceId, request)).getToken();
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not register device on Distribution Service", ex);
        }
    }

    @Override
    public String sync(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException {
        try {
            return executeCall(getHttpClient().register(deviceId, request)).getToken();
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not sync device with Distribution Service", ex);
        }
    }
}