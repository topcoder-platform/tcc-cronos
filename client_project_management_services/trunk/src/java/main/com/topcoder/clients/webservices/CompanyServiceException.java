/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.ejb.ApplicationException;

/**
 * <p>
 *  This exception is the base exception for all exceptions
 *  raised from operations from CompanyService (excerpt loading of the configurations).
 *  This exception wraps exceptions raised from persistence or
 *  from usage of the J2EE utilities or from the used topCoder components.
 * </p>
 *
 * <p>
 *  This exception is not thread safe because parent exception is not thread safe.
 *  The application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
@ApplicationException(rollback = true)
public class CompanyServiceException extends ClientProjectManagementServicesException {

    /**
     * Constructs a new 'CompanyServiceException' instance with the given message.
     *
     * @param message the exception message
     */
    public CompanyServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'CompanyServiceException' exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public CompanyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

