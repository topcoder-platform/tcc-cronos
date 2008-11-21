package com.topcoder.clients.webservices.beans;

public class AuthorizationFailedException extends Exception {
    /**
     * Constructs a new 'AuthorizationFailedException' instance with the given message.
     *
     * @param message the exception message
     */
    public AuthorizationFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'AuthorizationFailedException' exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public AuthorizationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
