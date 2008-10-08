/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is the base exception for all exceptions raised from operations from
 * ProjectStatusServiceException (excerpt loading of the configurations). This exception wraps exceptions
 * raised from persistence or from usage of the J2EE utilities or from the used topCoder components.
 * </p>
 * <p>
 * Thread safety: This exception is not thread safe because parent exception is not thread safe. The
 * application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ProjectStatusServiceException extends ClientProjectManagementServicesException {
    /**
     * <p>
     * Constructor. Constructs a new 'ProjectStatusServiceException' instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public ProjectStatusServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor. Constructs a new 'ProjectStatusServiceException' exception with the given message and
     * cause.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public ProjectStatusServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
