/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.ApplicationException;

/**
 * This exception signals an issue if the needed JIRA project is invalid (it does not exists in JIRA). It is a direct
 * wrapper for the JIRA Management's JiraProjectValidationException. It extends JiraServiceException.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class JiraServiceProjectValidationException extends JiraServiceException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -92045868503458654l;    

    /**
     * Creates a new exception instance with no error message and no cause of error.
     */
    public JiraServiceProjectValidationException() {
    }

    /**
     * Creates a new exception instance with this error message.
     *
     * @param message error message
     */
    public JiraServiceProjectValidationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with no error message and the given cause of error.
     *
     * @param cause error cause
     */
    public JiraServiceProjectValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message error message
     * @param cause   error cause
     */
    public JiraServiceProjectValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
