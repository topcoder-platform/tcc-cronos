/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is the base exception for all exceptions raised in ConfluenceManagementService It is a direct
 * wrapper for the Confluence Management's ConfluenceManagerException.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by having no mutable state.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ConfluenceManagementServiceException extends Exception {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public ConfluenceManagementServiceException(String message) {
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
    public ConfluenceManagementServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of error.
     * </p>
     */
    public ConfluenceManagementServiceException() {
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
    public ConfluenceManagementServiceException(Throwable cause) {
        super(cause);
    }
}