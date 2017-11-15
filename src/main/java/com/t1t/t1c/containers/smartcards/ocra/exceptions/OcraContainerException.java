package com.t1t.t1c.containers.smartcards.ocra.exceptions;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class OcraContainerException extends GenericContainerException {

    public OcraContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.OCRA_CONTAINER_REST_ERROR;
    }

}