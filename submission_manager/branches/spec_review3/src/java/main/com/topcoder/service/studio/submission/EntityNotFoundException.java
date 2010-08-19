/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by the updateXXX and removeXXX methods if the given entity is not found in persistence.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class EntityNotFoundException extends SubmissionManagementException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -4216514662150870067L;

    /**
     * Creates an <code>EntityNotFoundException</code> instance with the error message.
     *
     * @param message
     *            The error message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an <code>EntityNotFoundException</code> instance with the error message and cause of error.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an <code>EntityNotFoundException</code> instance with the error message and any additional data to
     * attach to the exception.
     *
     * @param message
     *            The error message
     * @param data
     *            The additional data to attach to the exception
     */
    public EntityNotFoundException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates an <code>EntityNotFoundException</code> instance with the error message, cause of error, and any
     * additional data to attach to the exception.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     * @param data
     *            The additional data to attach to the exception
     */
    public EntityNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
