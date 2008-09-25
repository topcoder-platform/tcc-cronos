/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping;

import javax.ejb.ApplicationException;

/**
 * <p>
 *  This exception signals an issue if the needed user id can not be found
 *  (user id is not mapped with any needed entity: project or client).
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
public class UserNotFoundException extends UserMappingRetrievalException {

    /**
     * Constructs a new 'UserNotFoundException' instance with the given message.
     *
     * @param message the exception message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'UserNotFoundException' exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

