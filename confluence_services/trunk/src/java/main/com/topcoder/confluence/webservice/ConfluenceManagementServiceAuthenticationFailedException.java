/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception signals an issue if the authorization process fails, such as incorrect credentials being provided
 * to be able to log in. It is a direct wrapper for the Confluence Management's
 * ConfluenceAuthenticationFailedException.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by having no mutable state.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ConfluenceManagementServiceAuthenticationFailedException extends ConfluenceManagementServiceException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public ConfluenceManagementServiceAuthenticationFailedException(String message) {
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
    public ConfluenceManagementServiceAuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of error.
     * </p>
     */
    public ConfluenceManagementServiceAuthenticationFailedException() {
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
    public ConfluenceManagementServiceAuthenticationFailedException(Throwable cause) {
        super(cause);
    }
}