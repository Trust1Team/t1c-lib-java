package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PtIdContainerException extends GenericContainerException {

    public PtIdContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.PT_CONTAINER_REST_ERROR;
    }

}