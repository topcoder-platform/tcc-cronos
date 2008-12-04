/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

/**
 * <p>
 * This exception signals an issue if the configured value is invalid It is used in the initialized method of the
 * EJB.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by having no mutable state.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceConfigurationException extends RuntimeException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public ConfluenceManagementServiceConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error.
     * </p>
     *
     * @param message
     *            error message
     * @param cause
     *            cause of error
     */
    public ConfluenceManagementServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of error.
     * </p>
     */
    public ConfluenceManagementServiceConfigurationException() {
        super();
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and the given cause of error.
     * </p>
     *
     * @param cause
     *            cause of error
     */
    public ConfluenceManagementServiceConfigurationException(Throwable cause) {
        super(cause);
    }
}