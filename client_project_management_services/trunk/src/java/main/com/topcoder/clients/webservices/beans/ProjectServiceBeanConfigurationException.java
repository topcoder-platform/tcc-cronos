/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

/**
 * <p>
 *  This runtime exception signals an issue if the configured value is
 *  invalid in ProjectServiceBean (in this design, when managers are null,
 *  user mapping retriever is null or required resources are null or empty String).
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
public class ProjectServiceBeanConfigurationException extends ServiceBeanConfigurationException {

    /**
     * Constructs a new 'ProjectServiceBeanConfigurationException'
     * instance with the given message.
     *
     * @param message the exception message
     */
    public ProjectServiceBeanConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'ProjectServiceBeanConfigurationException'
     * exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public ProjectServiceBeanConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

