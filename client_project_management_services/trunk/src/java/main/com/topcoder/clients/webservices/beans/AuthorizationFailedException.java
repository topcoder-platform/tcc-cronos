/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import javax.ejb.ApplicationException;

/**
 * This exception signals an issue when performing
 * the authentications and signals that the given user
 * is not authorized to perform the given operation.
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This exception is not thread safe because parent exception is not thread safe.
 *  The application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
@ApplicationException(rollback = true)
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