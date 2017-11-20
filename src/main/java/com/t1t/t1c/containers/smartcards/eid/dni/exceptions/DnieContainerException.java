package com.t1t.t1c.containers.smartcards.eid.dni.exceptions;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class DnieContainerException extends GenericContainerException {

    public DnieContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.DNIE_CONTAINER_REST_ERROR;
    }

}