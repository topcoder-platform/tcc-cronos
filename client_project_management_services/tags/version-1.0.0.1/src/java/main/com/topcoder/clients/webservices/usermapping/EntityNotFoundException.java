/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping;

import javax.ejb.ApplicationException;

/**
 * <p>
 *  This exception signals an issue if the needed entity
 *  (in this design, entities inherited from AuditableEntity) can not be found.
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
public class EntityNotFoundException extends UserMappingRetrievalException {

    /**
     * Constructs a new 'EntityNotFoundException' instance with the given message.
     *
     * @param message the exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'EntityNotFoundException'
     * exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

