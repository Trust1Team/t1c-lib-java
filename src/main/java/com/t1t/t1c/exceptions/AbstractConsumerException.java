package com.t1t.t1c.exceptions;

/**
 * Created by michallispashidis on 31/10/2017.
 *
 * Base class for all consumer exceptions.  A consumer exception happens when the consumer
 * does something that is problematic, such as try to perform a faulty function.
 *
 */
public abstract class AbstractConsumerException extends AbstractException {
    /**
     * Constructor.
     */
    public AbstractConsumerException() {
    }

    /**
     * Constructor.
     * @param message the exception message
     */
    public AbstractConsumerException(String message) {
        super(message);
    }
    
    /**
     * Constructor.
     * @param cause the exception cause
     */
    public AbstractConsumerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * @param message the exception message
     * @param cause the exception cause
     */
    public AbstractConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

}
