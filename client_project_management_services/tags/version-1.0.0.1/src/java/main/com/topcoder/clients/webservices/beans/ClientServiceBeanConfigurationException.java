/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

/**
 * <p>
 *  This runtime exception signals an issue if the configured value is invalid in ClientServiceBean
 *  (in this design, when managers are null, user mapping retriever is null or required resources
 *  are null or empty String). Wraps the underlying exceptions when using the configured values.
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
public class ClientServiceBeanConfigurationException extends ServiceBeanConfigurationException {

    /**
     * Constructs a new 'ClientServiceBeanConfigurationException'
     * instance with the given message.
     *
     * @param message the exception message
     */
    public ClientServiceBeanConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'ClientServiceBeanConfigurationException'
     * exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public ClientServiceBeanConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

