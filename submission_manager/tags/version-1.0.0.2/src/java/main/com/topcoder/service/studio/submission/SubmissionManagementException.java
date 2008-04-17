/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by all CRUDE methods if there is an error during an operation.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class SubmissionManagementException extends BaseCriticalException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -5601327487893228559L;

    /**
     * Creates a <code>SubmissionManagementException</code> instance with the error message.
     *
     * @param message
     *            The error message
     */
    public SubmissionManagementException(String message) {
        super(message);
    }

    /**
     * Creates a <code>SubmissionManagementException</code> instance with the error message and cause of error.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     */
    public SubmissionManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>SubmissionManagementException</code> instance with the error message and any additional data to
     * attach to the exception.
     *
     * @param message
     *            The error message
     * @param data
     *            The additional data to attach to the exception
     */
    public SubmissionManagementException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a <code>SubmissionManagementException</code> instance with the error message, cause of error, and any
     * additional data to attach to the exception.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     * @param data
     *            The additional data to attach to the exception
     */
    public SubmissionManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
