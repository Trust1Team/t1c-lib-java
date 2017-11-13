package com.t1t.t1c.containers.smartcards.emv.exceptions;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class EmvContainerException extends GenericContainerException {

    public EmvContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.EMV_CONTAINER_REST_ERROR;
    }

}