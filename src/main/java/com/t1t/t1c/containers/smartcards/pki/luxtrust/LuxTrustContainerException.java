package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxTrustContainerException extends GenericContainerException {

    public LuxTrustContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.LUXTRUST_CONTAINER_REST_ERROR;
    }

}