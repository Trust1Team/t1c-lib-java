package com.t1t.t1c.containers.remoteloading;

import com.t1t.t1c.exceptions.ErrorCodes;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class RemoteLoadingContainerException extends GenericContainerException {

    public RemoteLoadingContainerException(String message) {
        super(message);
    }

    public RemoteLoadingContainerException(String message, RestException cause) {
        super(message, cause);
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.REMOTE_LOADING_CONTAINER_REST_ERROR;
    }
}