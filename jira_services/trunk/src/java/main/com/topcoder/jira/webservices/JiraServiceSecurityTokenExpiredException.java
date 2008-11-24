/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.ApplicationException;

/**
 * This exception signals an issue if the token obtained for authorization expires or becomes stale. It is a direct
 * wrapper for the JIRA Management's JiraSecurityTokenExpiredException. It extends JiraServiceException.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class JiraServiceSecurityTokenExpiredException extends JiraServiceException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 965028239065865204l;

    /**
     * Creates a new exception instance with no error message and no cause of error.
     */
    public JiraServiceSecurityTokenExpiredException() {
    }

    /**
     * Creates a new exception instance with this error message.
     *
     * @param message error message
     */
    public JiraServiceSecurityTokenExpiredException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with no error message and the given cause of error.
     *
     * @param cause error cause
     */
    public JiraServiceSecurityTokenExpiredException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message error message
     * @param cause   error cause
     */
    public JiraServiceSecurityTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
