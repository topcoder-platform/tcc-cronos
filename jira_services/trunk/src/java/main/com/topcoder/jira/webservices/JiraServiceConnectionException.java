/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.ApplicationException;

/**
 * This exception signals an issue when an attempt to reestablish the connection to JIRA fails. It is a direct wrapper
 * for the JIRA Management's JiraConnectionException. It extends JiraServiceException.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class JiraServiceConnectionException extends JiraServiceException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4353209888790462654l;

    /**
     * Creates a new exception instance with no error message and no cause of error.
     */
    public JiraServiceConnectionException() {
    }

    /**
     * Creates a new exception instance with this error message.
     *
     * @param message error message
     */
    public JiraServiceConnectionException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with no error message and the given cause of error.
     *
     * @param cause error cause
     */
    public JiraServiceConnectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message error message
     * @param cause   error cause
     */
    public JiraServiceConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
