package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxIdContainerException extends GenericContainerException {

    public LuxIdContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.LUXID_CONTAINER_REST_ERROR;
    }

}