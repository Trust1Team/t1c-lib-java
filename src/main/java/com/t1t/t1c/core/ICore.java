package com.t1t.t1c.core;

import com.t1t.t1c.ds.DsAtrList;
import com.t1t.t1c.ds.DsContainerResponse;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.model.PlatformInfo;

import java.util.List;
import java.util.Map;

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
     * Retrieve the Distribution Service public key set for the installed T1C-GCL.
     *
     * @return the public key
     * @throws GclCoreException: on failure
     */
    String getDsPubKey() throws GclCoreException;

    /**
     * Retrieve the device public key for the installed T1C-GCL.
     *
     * @return the public key
     * @throws GclCoreException: on failure
     */
    String getDevicePubKey() throws GclCoreException;

    /**
     * Retrieve the SSL public key for the installed T1C-GCL.
     *
     * @return the public key
     * @throws GclCoreException: on failure
     */
    String getSslPubKey() throws GclCoreException;

    /**
     * Set the public key for the installed T1C-GCL.
     * The public key can be updated.
     *
     * @param encryptedPublicKey the public key to set.
     * @param encryptedAesKey the aes key with which the public key was encrypted
     * @return true if successful
     * @throws GclCoreException: on failure
     */
    Boolean setDsPubKey(String encryptedPublicKey, String encryptedAesKey) throws GclCoreException;

    /**
     * Return T1C-GCL status information.
     *
     * @return the core info.
     * @throws GclCoreException: on failure
     */
    GclInfo getInfo() throws GclCoreException;

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

    /**
     * Get list of available agents, matching the provided String filter parameters.
     *
     * @param filterParams values to filter
     * @return
     * @throws GclCoreException
     */
    List<GclAgent> getAgents(Map<String, String> filterParams) throws GclCoreException;

    /**
     * Get list of available agents
     *
     * @return the available agents
     * @throws GclCoreException
     */
    List<GclAgent> getAgents() throws GclCoreException;

    /**
     * Generate a consent request.
     *
     * @param title    the title for the consent dialog.
     * @param codeWord a code word that will be shown in the consent dialog.
     * @return true if granted, false if not.
     * @throws GclCoreException
     */
    boolean getConsent(String title, String codeWord) throws GclCoreException;

    /**
     * Generate a consent request.
     *
     * @param title            the title for the consent dialog.
     * @param codeWord         a code word that will be shown in the consent dialog.
     * @param durationInDays   how long the consent should be valid if granted. To prevent timeouts, the value must be less than the default duration set in the configuration
     * @param alertLevel       the severity of the popup. Defaults to "warning".
     * @param alertPosition    the positioning of the consent popup on the screen. Defaults to "standard" (exact meaning of this varies between OS's).
     * @param consentType      the type of consent being requested.
     * @param timeoutInSeconds the timeout of the consent popup in seconds. If the user does not respond within this timespan, no consent will be granted. Defaults to 30s but can be overridden in the configuration. To prevent SocketTimeoutExceptions, the value <b>must</b> be less than the configured default value.
     * @return true if granted, false if not.
     */
    boolean getConsent(String title, String codeWord, Integer durationInDays, GclConsent.AlertLevel alertLevel, GclConsent.AlertPosition alertPosition, GclConsent.Type consentType, Integer timeoutInSeconds);

    /**
     * Loads the containers specified by the DS in the Core
     *
     * @param containerResponses the container info
     * @return true if successful
     * @throws GclCoreException
     */
    boolean loadContainers(List<DsContainerResponse> containerResponses) throws GclCoreException;

    /**
     * Loads the ATR list obtained from the DS
     * @param atrList the ATR list to load
     * @return true if successful
     * @throws GclCoreException
     */
    boolean loadAtrList(DsAtrList atrList) throws GclCoreException;

    /**
     * Poll the GCL for a configured duration or until all downloads are completed
     *
     * @param containers the containers to load
     * @param isRetry if this is a retry attempt
     * @return the GCL info
     * @throws GclCoreException
     */
    GclInfo pollContainerDownloadStatus(List<DsContainerResponse> containers, boolean isRetry) throws GclCoreException;
}
