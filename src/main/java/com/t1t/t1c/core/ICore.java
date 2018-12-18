package com.t1t.t1c.core;

import com.t1t.t1c.ds.DsAtrList;
import com.t1t.t1c.ds.DsContainerResponse;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.T1cAdminPublicKeys;
import com.t1t.t1c.model.T1cPublicKey;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ICore {
    /**
     * Verify the activation state of the installed T1C-GCL.
     *
     * @return true if activation is successful
     * @throws GclCoreException: on failure
     */
    Boolean activate() throws GclCoreException;

    /**
     * Retrieves all public keys/certificates for the installed T1C-GCL.
     *
     * @return
     * @throws GclCoreException
     */
    T1cAdminPublicKeys getAdminPublicKeys() throws GclCoreException;

    /**
     * Retrieves all public keys/certificates for the installed T1C-GCL.
     *
     * @param parse Boolean toggle to parse the public key
     * @return
     * @throws GclCoreException
     */
    T1cAdminPublicKeys getAdminPublicKeys(Boolean parse) throws GclCoreException;

    /**
     * Get list of available agents
     *
     * @return the available agents
     * @throws GclCoreException
     */
    List<GclAgent> getAgents() throws GclCoreException;

    /**
     * Get list of available agents, matching the provided String filter parameters.
     *
     * @param usernameToFilter the user name to filter the results to
     * @return list of agents matching the filter
     * @throws GclCoreException
     */
    List<GclAgent> getAgents(String usernameToFilter) throws GclCoreException;

    /**
     * Get list of readers containing cards that can authenticate
     *
     * @return list of readers with cards that can authenticate
     * @throws GclCoreException: on failure
     */
    List<GclReader> getAuthenticationCapableReaders() throws GclCoreException;

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
    boolean getConsent(String title, String codeWord, Integer durationInDays, GclAlertLevel alertLevel, GclAlertPosition alertPosition, GclConsentType consentType, Integer timeoutInSeconds);

    /**
     * Retrieve the device public key for the installed T1C-GCL.
     *
     * @return the public key
     * @throws GclCoreException: on failure
     */
    T1cPublicKey getDevicePubKey() throws GclCoreException;

    /**
     * Retrieve the device public key for the installed T1C-GCL.
     *
     * @param parse Boolean toggle to parse the public key
     * @return the public key
     * @throws GclCoreException: on failure
     */
    T1cPublicKey getDevicePubKey(Boolean parse) throws GclCoreException;

    /**
     * Retrieve the Distribution Service public keys set for the installed T1C-GCL.
     *
     * @return the public keys
     * @throws GclCoreException: on failure
     */
    Map<String, T1cPublicKey> getDsPubKeys() throws GclCoreException;

    /**
     * Retrieve the Distribution Service public keys set for the installed T1C-GCL.
     *
     * @param parse Boolean toggle to parse the public key
     * @return the public key
     * @throws GclCoreException: on failure
     */
    Map<String, T1cPublicKey> getDsPubKeys(Boolean parse) throws GclCoreException;

    /**
     * Return T1C-GCL status information.
     *
     * @return the core info.
     * @throws GclCoreException: on failure
     */
    GclInfo getInfo() throws GclCoreException;

    /**
     * Get list of readers containing cards that can verify a PIN
     *
     * @return list of readers with cards that can verify PIN
     * @throws GclCoreException: on failure
     */
    List<GclReader> getPinVerificationCapableReaders() throws GclCoreException;

    /**
     * Retrieve Platform information.
     *
     * @return the platform info.
     * @throws GclCoreException: on failure
     */
    PlatformInfo getPlatformInfo() throws GclCoreException;

    /**
     * Get reader for given ID
     *
     * @param readerId the reader ID
     * @return the reader for given ID
     * @throws GclCoreException: on failure
     */
    GclReader getReader(String readerId) throws GclCoreException;

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
     * Get list of readers containing cards that can sign
     *
     * @return list of readers with cards that can sign
     * @throws GclCoreException: on failure
     */
    List<GclReader> getSignCapableReaders() throws GclCoreException;

    /**
     * Retrieve the SSL public key for the installed T1C-GCL.
     *
     * @return the public key
     * @throws GclCoreException: on failure
     */
    T1cPublicKey getSslPubKey() throws GclCoreException;

    /**
     * Retrieve the SSL public key for the installed T1C-GCL.
     *
     * @param parse Boolean toggle to parse the public key
     * @return the public key
     * @throws GclCoreException: on failure
     */
    T1cPublicKey getSslPubKey(Boolean parse) throws GclCoreException;

    /**
     * Retrieve the version of the installed T1C-GCL.
     *
     * @return the version.
     * @throws GclCoreException: on failure
     */
    String getVersion() throws GclCoreException;

    /**
     * Loads the ATR list obtained from the DS
     *
     * @param atrList the ATR list to load
     * @return true if successful
     * @throws GclCoreException
     */
    boolean loadAtrList(DsAtrList atrList) throws GclCoreException;

    /**
     * Loads the containers specified by the DS in the Core
     *
     * @param containerResponses the container info
     * @return true if successful
     * @throws GclCoreException
     */
    boolean loadContainers(List<DsContainerResponse> containerResponses) throws GclCoreException;

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
     * Poll the GCL for a configured duration or until all downloads are completed
     *
     * @param containers the containers to load
     * @return the GCL info
     * @throws GclCoreException
     */
    GclInfo pollContainerDownloadStatus(List<DsContainerResponse> containers) throws GclCoreException;

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
     * Resolve the local agent. This is done based on the "user.name" system property. Even though it is unlikely
     * that a user will have more than one agent, we return a list of possible agents in that unlikely event.
     *
     * @return the agents matching the challenge
     * @throws GclCoreException on failure
     */
    List<GclAgent> resolveAgent() throws GclCoreException;

    /**
     * Resolve the local agent for a given user name. Even though it is unlikely
     * that a user will have more than one agent, we return a list of possible agents in that unlikely event.
     *
     * @param username the username to filter on.
     * @return the agents matching the challenge
     * @throws GclCoreException on failure
     */
    List<GclAgent> resolveAgent(String username) throws GclCoreException;

    /**
     * Set the public key for the installed T1C-GCL.
     * The public key can be updated.
     *
     * @param encryptedPublicKey the public key to set.
     * @param encryptedAesKey    the aes key with which the public key was encrypted
     * @param namespace          the distribution service namespace
     * @return true if successful
     * @throws GclCoreException: on failure
     */
    Boolean setDsPubKey(String encryptedPublicKey, String encryptedAesKey, String namespace) throws GclCoreException;
}
