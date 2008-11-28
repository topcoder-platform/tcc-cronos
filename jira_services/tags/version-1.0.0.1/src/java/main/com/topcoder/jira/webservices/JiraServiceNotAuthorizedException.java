/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.ApplicationException;

/**
 * This exception signals an issue if a user attempts an unauthorized call (the token used to authenticate is invalid or
 * the user does not have access to the specific areas being manipulated). Also this exception might be thrown if the
 * user or password are invalid. It is a direct wrapper for the JIRA Management's JiraNotAuthorizedException. It extends
 * JiraServiceException.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class JiraServiceNotAuthorizedException extends JiraServiceException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3095409796010323103l;

    /**
     * Creates a new exception instance with no error message and no cause of error.
     */
    public JiraServiceNotAuthorizedException() {
    }

    /**
     * Creates a new exception instance with this error message.
     *
     * @param message error message
     */
    public JiraServiceNotAuthorizedException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with no error message and the given cause of error.
     *
     * @param cause error cause
     */
    public JiraServiceNotAuthorizedException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message error message
     * @param cause   error cause
     */
    public JiraServiceNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
