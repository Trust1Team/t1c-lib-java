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
     * @return
     * @throws DsClientException
     */
    DsSystemStatus getInfo() throws DsClientException;

    /**
     * Returns device information stored centrally.
     *
     * @param deviceId
     * @return
     * @throws DsClientException
     */
    DsDevice getDevice(String deviceId) throws DsClientException;

    /**
     * Returns a singed JWT for admin use cases on the T1C-GCL.
     *
     * @return
     * @throws DsClientException
     */
    String getJWT() throws DsClientException;

    /**
     * Refreshes (re-sign) a valid JWT.
     * The client should verify if JWT is about to expire.
     *
     * @param token
     * @return
     * @throws DsClientException
     */
    String refreshJWT(String token) throws DsClientException;

    /**
     * Retrieves the public key of the T1C-DS.
     * The key can be used for validation purposes.
     *
     * @return
     * @throws DsClientException
     */
    String getPublicKey() throws DsClientException;

    /**
     * Retrieves the public key of the T1C-DS.
     * The key can be used for validation purposes.
     *
     * @param encoding can be used for different certificate encoding
     * @return
     * @throws DsClientException
     */
    String getPublicKey(DsPublicKeyEncoding encoding) throws DsClientException;

    /**
     * Returns the download link based on the provided platform information.
     * T1C-GCL has different distributables for each operating system.
     * The downloadlink depends on the application context.
     *
     * @param info
     * @return
     * @throws DsClientException
     */
    String getDownloadLink(PlatformInfo info) throws DsClientException;

    /**
     * Registers or synchronized a device towards the T1C-DS.
     * Public properties are send in order to determine operating system browser, ...
     *
     * @param deviceId
     * @param request
     * @return
     * @throws DsClientException
     */
    String register(String deviceId, DsDeviceRegistrationRequest request) throws DsClientException;

    /**
     * Returns the download link based on the provided platform information.
     * T1C-GCL has different distributables for each operating system.
     * The downloadlink depends on the application context.
     *
     * @return
     * @throws DsClientException
     */
    String getDownloadLink() throws DsClientException;
}
