package com.topcoder.confluence.client;

/**
 * <p>
 * Component's base exception. The service will throw this exception and all other specific exceptions should inherit
 * from it.
 * </p>
 * 
 * @author romanoTC
 * @since 1.0
 */
public class ConfluenceClientServiceException extends Exception {
    /**
     * <p>
     * Constructs a <code>ConfluenceClientServiceException</code> with the given <code>message</code>.
     * </p>
     * 
     * @param message Useful message containing a description of why the exception was thrown - may be null.
     */
    public ConfluenceClientServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a <code>ConfluenceClientServiceException</code> taking both a <code>message</code> and a
     * <code>cause</code> detailing why the critical exception occurs.
     * </p>
     * 
     * @param message Useful message containing a description of why the exception was thrown - may be null.
     * @param cause The initial throwable reason which triggered this exception to be thrown - may be null.
     */
    public ConfluenceClientServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}


