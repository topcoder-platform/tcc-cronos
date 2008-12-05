/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception signals an issue when an attempt to reestablish the connection to Confluence fails It is a direct
 * wrapper for the Confluence Management's ConfluenceConnectionException.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by having no mutable state.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ConfluenceManagementServiceConnectionException extends ConfluenceManagementServiceException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public ConfluenceManagementServiceConnectionException(String message) {
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
    public ConfluenceManagementServiceConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of error.
     * </p>
     */
    public ConfluenceManagementServiceConnectionException() {
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
    public ConfluenceManagementServiceConnectionException(Throwable cause) {
        super(cause);
    }
}