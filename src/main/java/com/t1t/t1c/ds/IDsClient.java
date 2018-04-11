package com.t1t.t1c.ds;

import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.model.DsPublicKeyEncoding;
import com.t1t.t1c.model.PlatformInfo;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IDsClient {
    /**
     * Returns info for the T1C Distribution Server.
     *
     * @return the DS system status
     * @throws DsClientException: on failure
     */
    DsSystemStatus getInfo() throws DsClientException;

    /**
     * Returns device information stored centrally.
     *
     * @param deviceId the device ID
     * @return the device information
     * @throws DsClientException: on failure
     */
    DsDevice getDevice(String deviceId) throws DsClientException;

    /**
     * Refreshes (re-sign) a valid JWT.
     * The client should verify if JWT is about to expire.
     *
     * @param token the token to refresh
     * @return the refreshed token
     * @throws DsClientException: on failure
     */
    String refreshJWT(String token) throws DsClientException;

    /**
     * Retrieves the public key of the T1C-DS.
     * The key can be used for validation purposes.
     *
     * @param deviceId the Device ID
     * @return the DS public key
     * @throws DsClientException: on failure
     */
    DsPublicKey getPublicKey(String deviceId) throws DsClientException;

    /**
     * Retrieves the public key of the T1C-DS.
     * The key can be used for validation purposes.
     *
     * @param deviceId the device ID
     * @param encoding can be used for different certificate encoding
     * @return the DS public key
     * @throws DsClientException: on failure
     */
    DsPublicKey getPublicKey(String deviceId, DsPublicKeyEncoding encoding) throws DsClientException;

    /**
     * Returns the download link based on the provided platform information.
     * T1C-GCL has different distributables for each operating system.
     * The downloadlink depends on the application context.
     *
     * @param info the platform information
     * @return the download link
     * @throws DsClientException: on failure
     */
    String getDownloadLink(PlatformInfo info) throws DsClientException;

    /**
     * Registers or synchronize a device towards the T1C-DS.
     * Public properties are send in order to determine operating system, client, desktop application, ...
     *
     * @param deviceId the devide ID
     * @param request  the registration request
     * @return the sync or registration response and the access token obtained from the headers
     * @throws DsClientException: on failure
     */
    DsSyncResponseDto registerOrSync(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException;

    /**
     * Returns the download link based on the provided platform information.
     * T1C-GCL has different distributables for each operating system.
     * The downloadlink depends on the application context.
     *
     * @return a download link based application context
     * @throws DsClientException: on failure
     */
    String getDownloadLink() throws DsClientException;
}
