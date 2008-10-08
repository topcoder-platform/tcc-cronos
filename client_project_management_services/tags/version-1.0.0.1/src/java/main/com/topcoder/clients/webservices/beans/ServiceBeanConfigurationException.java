/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

/**
 * <p>
 *  This runtime exception signals an issue if the configured value is invalid (in this design,
 *  when managers are null, user mapping retriever is null or required
 *  resources are null or empty String). It is not thrown directly in this design,
 *  it is just a base class for the concrete service bean configuration exceptions.
 *  Wraps the underlying exceptions when using the configured values.
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
public class ServiceBeanConfigurationException extends RuntimeException {

    /**
     * Constructs a new 'ServiceBeanConfigurationException'
     * instance with the given message.
     *
     * @param message the exception message
     */
    public ServiceBeanConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'ServiceBeanConfigurationException'
     * exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public ServiceBeanConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

