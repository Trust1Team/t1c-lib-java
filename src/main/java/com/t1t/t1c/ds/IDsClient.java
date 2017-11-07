package com.t1t.t1c.ds;

import com.t1t.t1c.model.rest.DsDeviceRegistrationRequest;
import com.t1t.t1c.model.rest.DsDeviceResponse;
import com.t1t.t1c.model.rest.DsDownloadRequest;
import com.t1t.t1c.model.rest.DsInfoResponse;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IDsClient {
    String getUrl();

    DsInfoResponse getInfo();

    DsDeviceResponse getDevice(String deviceId);

    String getJWT();

    String refreshJWT(String token);

    String getPubKey();

    String getDownloadLink(DsDownloadRequest request);

    String register(String deviceId, DsDeviceRegistrationRequest request);

    String sync(String deviceId, DsDeviceRegistrationRequest request);
}
