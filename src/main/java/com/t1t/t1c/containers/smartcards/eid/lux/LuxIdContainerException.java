package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxIdContainerException extends GenericContainerException {

    public LuxIdContainerException(final String message) {
        super(message);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_LUXID_CONTAINER_REST;
    }

}