/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

/**
 * <p>
 *  This runtime exception signals an issue when the creation of
 *  the web service client failed (wraps the MalformedURLException
 *  when creating the URL given a String wsdl document location).
 *  It is not thrown directly in this design, it is just a base class
 *  for the concrete service client exceptions.
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
public class ServiceClientCreationException extends RuntimeException {

    /**
     * Constructs a new 'ServiceClientCreationException' instance with the given message.
     *
     * @param message
     *      the exception message
     */
    public ServiceClientCreationException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'ServiceClientCreationException' exception with the given message and cause.
     *
     * @param message
     *      the exception message
     * @param cause
     *      the exception cause
     */
    public ServiceClientCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
