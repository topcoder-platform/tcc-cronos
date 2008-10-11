/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

/**
 * <p>
 * This exception is the base exception for all exceptions raised from operations from LookupServiceException
 * (excerpt loading of the configurations). This exception wraps exceptions raised from persistence or from
 * usage of the J2EE utilities or from the used topCoder components.
 * </p>
 * <p>
 * Thread safety: This exception is not thread safe because parent exception is not thread safe.The
 * application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceException extends ClientProjectManagementServicesException {
    /**
     * <p>
     * Constructor. Constructs a new 'LookupServiceException' instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public LookupServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor. Constructs a new 'LookupServiceException' exception with the given message and cause.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public LookupServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
