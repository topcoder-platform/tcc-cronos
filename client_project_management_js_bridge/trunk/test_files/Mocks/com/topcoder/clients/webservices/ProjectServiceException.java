/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

/**
 * <p>
 *  This exception is the base exception for all exceptions raised
 *  from operations from ProjectService (excerpt loading of the configurations).
 *  This exception wraps exceptions raised from persistence or from usage of
 *  the J2EE utilities or from the used topCoder components.
 * </p>
 *
 * <p>
 *  This exception is not thread safe because parent exception is not thread safe.
 *  The application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author  TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServiceException extends ClientProjectManagementServicesException {

    /**
     * Constructor. Constructs a new 'ProjectServiceException'
     * instance with the given message.
     *
     * @param message the exception message
     */
    public ProjectServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'ProjectServiceException' exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public ProjectServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

