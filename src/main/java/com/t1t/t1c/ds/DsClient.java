package com.t1t.t1c.ds;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.rest.RestExecutor;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class DsClient implements IDsClient {

    private DsRestClient dsRestClient;
    private LibConfig config;

    public DsClient(DsRestClient dsRestClient, LibConfig config) {
        this.dsRestClient = dsRestClient;
        this.config = config;
    }

    @Override
    public DsSystemStatus getInfo() throws DsClientException {
        try {
            return RestExecutor.executeCall(dsRestClient.getInfo(), false);
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not retrieve Distribution Service info", ex);
        }
    }

    @Override
    public DsPublicKey getPublicKey(String deviceId) throws DsClientException {
        try {
            DsPublicKey publicKeyResponse = RestExecutor.executeCall(dsRestClient.getPubKey(deviceId, config.getDsNamespace()), false);
            return publicKeyResponse != null && publicKeyResponse.getSuccess() ? publicKeyResponse : null;
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not retrieve GCL public key", ex);
        }
    }

    @Override
    public String getDownloadLink() throws DsClientException {
        return getDownloadLink(null);
    }

    @Override
    public String getDownloadLink(final PlatformInfo information) throws DsClientException {
        try {
            PlatformInfo info = information;
            if (info == null) {
                info = new PlatformInfo();
            }
            DsDownloadRequest request = new DsDownloadRequest()
                    .withOs(new DsOs()
                            .withArchitecture(info.getOs().getArchitecture())
                            .withName(info.getOs().getName())
                            .withVersion(info.getOs().getVersion()))
                    .withProxyDomain(config.getProxyDomain());
            return RestExecutor.executeCall(dsRestClient.getDownloadLink(request), false).getLink();
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not retrieve download link from Distribution Service", ex);
        }
    }

    @Override
    public DsSyncResponseDto register(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException {
        try {
            return new DsSyncResponseDto(RestExecutor.executeCallAndReturnAccessTokenHeader(dsRestClient.register(deviceId, request), false));
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not register device on Distribution Service", ex);
        }
    }

    @Override
    public DsSyncResponseDto sync(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException {
        try {
            return new DsSyncResponseDto(RestExecutor.executeCallAndReturnAccessTokenHeader(dsRestClient.sync(deviceId, request), false));
        } catch (RestException ex) {
            throw ExceptionFactory.dsClientException("Could not register device on Distribution Service", ex);
        }
    }
}