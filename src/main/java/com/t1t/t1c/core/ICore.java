package com.t1t.t1c.core;

import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.model.PlatformInfo;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ICore {
    /**
     * Retrieve Platform information.
     * @return
     */
    PlatformInfo getPlatformInfo() throws GclCoreException;

    /**
     * Retrieve the version of the installed T1C-GCL.
     * @return
     */
    String getVersion() throws GclCoreException;

    /**
     * Verify the activation state of the installed T1C-GCL.
     * @return
     */
    Boolean activate() throws GclCoreException;

    /**
     * Retrieve the public key for the installed T1C-GCL.
     * @return
     */
    String getPubKey() throws GclCoreException;

    /**
     * Set the public key for the installed T1C-GCL.
     * The public key can be updated.
     *
     * @param publicKey
     */
    Boolean setPubKey(String publicKey) throws GclCoreException;

    /**
     * Return T1C-GCL status information.
     *
     * @return
     */
    GclStatus getInfo() throws GclCoreException;

    /**
     * Return installed containers.
     * @return
     */
    List<GclContainer> getContainers() throws GclCoreException;

    /**
     * Poll readers for inserted card.
     *
     * @return
     */
    GclReader pollCardInserted() throws GclCoreException;
    GclReader pollCardInserted(Integer pollIntervalInSeconds) throws GclCoreException;
    GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException;

    /**
     * Poll for readers
     *
     * @return
     */
    List<GclReader> pollReadersWithCards() throws GclCoreException;
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws GclCoreException;
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException;
    List<GclReader> pollReaders() throws GclCoreException;
    List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws GclCoreException;
    List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException;

    /**
     * Poll readers.
     *
     * @return
     */
    List<GclReader> getReaders() throws GclCoreException;
    List<GclReader> getReadersWithInsertedCard() throws GclCoreException;
    List<GclReader> getReadersWithoutInsertedCard() throws GclCoreException;
    GclReader getReader(String readerId) throws GclCoreException;

    /**
     * Reader capabilities.
     *
     * @return
     */
    List<GclReader> getAuthenticationCapableReaders() throws GclCoreException;
    List<GclReader> getSignCapableReaders() throws GclCoreException;
    List<GclReader> getPinVerificationCapableReaders() throws GclCoreException;
}
