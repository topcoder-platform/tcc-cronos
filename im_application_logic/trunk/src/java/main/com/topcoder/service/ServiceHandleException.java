package com.topcoder.service;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception should be thrown when problems with component configuration appear. It encapsulates any
 * possible problems with configuration.
 * </p>
 * <p>
 * Class is thread safe because it is immutable.
 * </p>
 * 
 */
public class ServiceHandleException extends BaseException {

    /**
     * <p>
     * constructor
     * </p>
     */
    public ServiceHandleException() {
    }

    /**
     * <p>
     * constructor which create exception with given text message
     * </p>
     * 
     * 
     * @param message
     *            the message to associate with this exception
     */
    public ServiceHandleException(String message) {
        super(message);
    }

    /**
     * <p>
     * constructor which create exception with given text message and underlying cause
     * </p>
     * 
     * 
     * @param message
     *            the message to associate with this exception
     * @param cause
     *            the underlying exception.
     */
    public ServiceHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
