package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ocra.exceptions.OcraContainerException;
import com.t1t.t1c.model.rest.GclOcraChallengeData;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IOcraContainer extends GenericContainer {

    String getCounter(String... pin) throws OcraContainerException;

    String challenge(GclOcraChallengeData data) throws OcraContainerException;
}
