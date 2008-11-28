/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.ApplicationException;

/**
 * This exception is the base exception for all exceptions raised in JiraManagementService. It is a direct wrapper for
 * the JIRA Management's JiraManagerException. It extends Exception.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class JiraServiceException extends Exception {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1239293523453054987l;

    /**
     * Creates a new exception instance with no error message and no cause of error.
     */
    public JiraServiceException() {
    }

    /**
     * Creates a new exception instance with this error message.
     *
     * @param message error message
     */
    public JiraServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with no error message and the given cause of error.
     *
     * @param cause error cause
     */
    public JiraServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message error message
     * @param cause   error cause
     */
    public JiraServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
