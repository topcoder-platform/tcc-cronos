/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception signals an issue if a user attempts an unauthorized call (the token used to authenticate is
 * invalid or the user does not have access to the specific areas being manipulated). It is a direct wrapper for
 * the Confluence Management's ConfluenceNotAuthorizedException.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by having no mutable state.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ConfluenceManagementServiceNotAuthorizedException extends ConfluenceManagementServiceException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public ConfluenceManagementServiceNotAuthorizedException(String message) {
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
    public ConfluenceManagementServiceNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of error.
     * </p>
     */
    public ConfluenceManagementServiceNotAuthorizedException() {
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
    public ConfluenceManagementServiceNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
