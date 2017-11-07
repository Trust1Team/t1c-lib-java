package com.t1t.t1c.ds;

import com.t1t.t1c.model.rest.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IDsClient {
    String getUrl();

    DsInfoResponse getInfo();

    DsDeviceResponse getDevice(String deviceId);

    String getJWT();

    String refreshJWT(DsToken token);

    String getPubKey();

    String getDownloadLink(DsDownloadRequest request);

    String register(String deviceId, DsDeviceRegistrationRequest request);

    String sync(String deviceId, DsDeviceRegistrationRequest request);
}
