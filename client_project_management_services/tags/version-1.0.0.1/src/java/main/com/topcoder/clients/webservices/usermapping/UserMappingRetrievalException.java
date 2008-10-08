/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping;

import javax.ejb.ApplicationException;

/**
 * <p>
 *  This exception signals an issue when performing the operations:
 *  get valid users for client, get valid users for project,
 *  get clients for user id and get projects for user id.
 *  This exception wraps exceptions raised from persistence or from usage of the J2EE utilities.
 * </p>
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
public class UserMappingRetrievalException extends Exception {

    /**
     * Constructs a new 'UserMappingRetrievalException' instance with the given message.
     *
     * @param message the exception message
     */
    public UserMappingRetrievalException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'UserMappingRetrievalException' exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public UserMappingRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}

