/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
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
