package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SafeNetContainerException extends GenericContainerException {

    public SafeNetContainerException(String message) {
        super(message);
    }

    public SafeNetContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.SAFENET_CONTAINER_REST_ERROR;
    }

}