package com.t1t.t1c.ds;

import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.model.DsPublicKeyEncoding;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.DsDevice;
import com.t1t.t1c.model.rest.DsDeviceRegistrationRequest;
import com.t1t.t1c.model.rest.DsInfo;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IDsClient {
    String getUrl();

    DsInfo getInfo() throws DsClientException;

    DsDevice getDevice(String deviceId) throws DsClientException;

    String getJWT() throws DsClientException;

    String refreshJWT(String token) throws DsClientException;

    String getPublicKey() throws DsClientException;

    String getPublicKey(DsPublicKeyEncoding encoding) throws DsClientException;

    String getDownloadLink(PlatformInfo info) throws DsClientException;

    String register(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException;

    String sync(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException;
}
