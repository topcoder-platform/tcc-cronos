/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

/**
 * This exception signals an issue if the configured value is invalid It is used in the initialize method of the EJB. It
 * extends RuntimeException.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class JiraServiceConfigurationException extends RuntimeException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 343453463276709l;

    /**
     * Creates a new exception instance with this error message.
     *
     * @param message error message
     */
    public JiraServiceConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message error message
     * @param cause   cause of error
     */
    public JiraServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with no error message and no cause of error.
     */
    public JiraServiceConfigurationException() {
    }

    /**
     * Creates a new exception instance with no error message and the given cause of error.
     *
     * @param cause cause of error
     */
    public JiraServiceConfigurationException(Throwable cause) {
        super(cause);
    }
}

