package com.t1t.t1c.core;

import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
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
    PlatformInfo getPlatformInfo() throws Exception;

    /**
     * Retrieve the version of the installed T1C-GCL.
     * @return
     */
    String getVersion();

    /**
     * Verify the activation state of the installed T1C-GCL.
     * @return
     */
    Boolean activate();

    /**
     * Retrieve the public key for the installed T1C-GCL.
     * @return
     */
    String getPubKey();

    /**
     * Set the public key for the installed T1C-GCL.
     * The public key can be updated.
     *
     * @param publicKey
     */
    void setPubKey(String publicKey);

    /**
     * Return T1C-GCL status information.
     *
     * @return
     */
    GclStatus getInfo();

    /**
     * Return installed containers.
     * @return
     */
    List<GclContainer> getContainers();

    /**
     * Poll readers for card inserted.
     *
     * @return
     */
    GclReader pollCardInserted();
    GclReader pollCardInserted(Integer pollIntervalInSeconds);
    GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds);

    /**
     * Poll for readers with card inserted.
     *
     * @return
     */
    List<GclReader> pollReadersWithCards();
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds);
    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds);
    List<GclReader> getReadersWithoutInsertedCard();

    /**
     * Poll readers.
     *
     * @return
     */
    List<GclReader> getReaders();
    GclReader getReader(String readerId);
    List<GclReader> pollReaders();
    List<GclReader> pollReaders(Integer pollIntervalInSeconds);
    List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds);

    /**
     * Reader capabilities.
     *
     * @return
     */
    List<GclReader> getAuthenticationCapableReaders();
    List<GclReader> getSignCapableReaders();
    List<GclReader> getPinVerificationCapableReaders();
}
