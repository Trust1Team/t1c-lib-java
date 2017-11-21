package com.t1t.t1c.core;

import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.rest.RestExecutor;

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
    PlatformInfo getPlatformInfo() throws RestException;

    /**
     * Retrieve the version of the installed T1C-GCL.
     * @return
     */
    String getVersion() throws RestException;

    /**
     * Verify the activation state of the installed T1C-GCL.
     * @return
     */
    Boolean activate() throws RestException;

    /**
     * Retrieve the public key for the installed T1C-GCL.
     * @return
     */
    String getPubKey() throws RestException;

    /**
     * Set the public key for the installed T1C-GCL.
     * The public key can be updated.
     *
     * @param publicKey
     */
    Boolean setPubKey(String publicKey) throws RestException;

    /**
     * Return T1C-GCL status information.
     *
     * @return
     */
    GclStatus getInfo() throws RestException;

    /**
     * Return installed containers.
     * @return
     */
    List<GclContainer> getContainers() throws RestException;

    /**
     * Poll readers for card inserted.
     *
     * @return
     */
    GclReader pollCardInserted() throws RestException;
    GclReader pollCardInserted(Integer pollIntervalInSeconds) throws RestException;
    GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws RestException;

    /**
     * Poll for readers with card inserted.
     *
     * @return
     */
    List<GclReader> pollReadersWithCards() throws RestException;
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws RestException;
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws RestException;
    List<GclReader> getReadersWithoutInsertedCard() throws RestException;

    /**
     * Poll readers.
     *
     * @return
     */
    List<GclReader> getReaders() throws RestException;
    GclReader getReader(String readerId) throws RestException;
    List<GclReader> pollReaders() throws RestException;
    List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws RestException;
    List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws RestException;

    /**
     * Reader capabilities.
     *
     * @return
     */
    List<GclReader> getAuthenticationCapableReaders() throws RestException;
    List<GclReader> getSignCapableReaders() throws RestException;
    List<GclReader> getPinVerificationCapableReaders() throws RestException;
}
