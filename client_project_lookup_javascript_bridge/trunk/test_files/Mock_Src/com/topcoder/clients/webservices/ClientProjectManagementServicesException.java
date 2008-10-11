/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

/**
 * <p>
 *  This exception is the base exception for all exceptions raised
 *  from operations from Web Service (excerpt loading of the configurations).
 *  This exception wraps exceptions raised from persistence or from usage of
 *  the J2EE utilities or from the used TopCoder components. It is not thrown directly in this design,
 *  it is just a base class for the service exceptions.
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
public class ClientProjectManagementServicesException extends Exception {

    /**
     * Constructs a new 'ClientProjectManagementServicesException'
     * instance with the given message.
     *
     * @param message the exception message
     */
    public ClientProjectManagementServicesException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'ClientProjectManagementServicesException'
     * exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public ClientProjectManagementServicesException(String message, Throwable cause) {
        super(message, cause);
    }
}

