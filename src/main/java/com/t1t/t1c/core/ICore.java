package com.t1t.t1c.core;

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
     *
     * @return the platform info.
     * @throws GclCoreException: on failure
     */
    PlatformInfo getPlatformInfo() throws GclCoreException;

    /**
     * Retrieve the version of the installed T1C-GCL.
     *
     * @return the version.
     * @throws GclCoreException: on failure
     */
    String getVersion() throws GclCoreException;

    /**
     * Verify the activation state of the installed T1C-GCL.
     *
     * @return true if activation is successful
     * @throws GclCoreException: on failure
     */
    Boolean activate() throws GclCoreException;

    /**
     * Retrieve the public key for the installed T1C-GCL.
     *
     * @return the public key
     * @throws GclCoreException: on failure
     */
    String getPubKey() throws GclCoreException;

    /**
     * Set the public key for the installed T1C-GCL.
     * The public key can be updated.
     *
     * @param publicKey the public key to set.
     * @return true if successful
     * @throws GclCoreException: on failure
     */
    Boolean setPubKey(String publicKey) throws GclCoreException;

    /**
     * Return T1C-GCL status information.
     *
     * @return the core info.
     * @throws GclCoreException: on failure
     */
    GclStatus getInfo() throws GclCoreException;

    /**
     * Return installed containers.
     *
     * @return the list of containers
     * @throws GclCoreException: on failure
     */
    List<GclContainer> getContainers() throws GclCoreException;

    /**
     * Poll for readers until a reader with an inserted card is found.
     *
     * @return a reader with inserted card
     * @throws GclCoreException: on failure
     */
    GclReader pollCardInserted() throws GclCoreException;

    /**
     * Poll for readers until a reader with an inserted card is found.
     *
     * @param pollIntervalInSeconds interval in seconds between polling attempts
     * @return a reader with inserted card
     * @throws GclCoreException: on failure
     */
    GclReader pollCardInserted(Integer pollIntervalInSeconds) throws GclCoreException;

    /**
     * Poll for readers until a reader with an inserted card is found.
     *
     * @param pollIntervalInSeconds interval in seconds between polling attempts
     * @param pollTimeoutInSeconds  timeout in seconds after which to cease polling attempts
     * @return a reader with inserted card
     * @throws GclCoreException: on failure
     */
    GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException;

    /**
     * Poll for list of readers with inserted cards.
     *
     * @return list of readers with inserted cards.
     * @throws GclCoreException: on failure
     */
    List<GclReader> pollReadersWithCards() throws GclCoreException;

    /**
     * Poll for list of readers with inserted cards.
     *
     * @param pollIntervalInSeconds interval in seconds between polling attempts
     * @return list of readers with inserted cards.
     * @throws GclCoreException: on failure
     */
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws GclCoreException;

    /**
     * Poll for list of readers with inserted cards.
     *
     * @param pollIntervalInSeconds interval in seconds between polling attempts
     * @param pollTimeoutInSeconds  timeout in seconds after which to cease polling attempts
     * @return list of readers with inserted cards.
     * @throws GclCoreException: on failure
     */
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException;

    /**
     * Poll for a list of readers
     *
     * @return list of available readers
     * @throws GclCoreException: on failure
     */
    List<GclReader> pollReaders() throws GclCoreException;

    /**
     * Poll for list of readers
     *
     * @param pollIntervalInSeconds interval in seconds between polling attempts
     * @return list of available readers
     * @throws GclCoreException: on failure
     */
    List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws GclCoreException;

    /**
     * Poll for list of readers
     *
     * @param pollIntervalInSeconds interval in seconds between polling attempts
     * @param pollTimeoutInSeconds  timeout in seconds after which to cease polling attempts
     * @return list of available readers.
     * @throws GclCoreException: on failure
     */
    List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException;

    /**
     * Get all readers
     *
     * @return list of available readers
     * @throws GclCoreException: on failure
     */
    List<GclReader> getReaders() throws GclCoreException;

    /**
     * Get all readers with inserted cards
     *
     * @return list of readers with inserted cards
     * @throws GclCoreException: on failure
     */
    List<GclReader> getReadersWithInsertedCard() throws GclCoreException;

    /**
     * Get all readers without inserted cards
     *
     * @return list of readers without cards
     * @throws GclCoreException: on failure
     */
    List<GclReader> getReadersWithoutInsertedCard() throws GclCoreException;

    /**
     * Get reader for given ID
     *
     * @param readerId the reader ID
     * @return the reader for given ID
     * @throws GclCoreException: on failure
     */
    GclReader getReader(String readerId) throws GclCoreException;

    /**
     * Get list of readers containing cards that can authenticate
     *
     * @return list of readers with cards that can authenticate
     * @throws GclCoreException: on failure
     */
    List<GclReader> getAuthenticationCapableReaders() throws GclCoreException;

    /**
     * Get list of readers containing cards that can sign
     *
     * @return list of readers with cards that can sign
     * @throws GclCoreException: on failure
     */
    List<GclReader> getSignCapableReaders() throws GclCoreException;

    /**
     * Get list of readers containing cards that can verify a PIN
     *
     * @return list of readers with cards that can verify PIN
     * @throws GclCoreException: on failure
     */
    List<GclReader> getPinVerificationCapableReaders() throws GclCoreException;
}
