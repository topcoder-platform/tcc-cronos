/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.ExceptionData;

import javax.ejb.ApplicationException;


/**
 * This is a new custom exception defined in version 1.2.
 * <p>
 * This exception is thrown by the setSubmissionMilestonePrize method if we've
 * already reached the maximum number of submissions receiving milestone prizes
 * for that contest.
 * </p>
 *
 * @ApplicationException(rollback = true) is used for this custom exception.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 */
@ApplicationException(rollback = true)
public class NumberOfSubmissionsExceededException
    extends SubmissionManagementException {
    /**
     * It represents the serial version unique id. It's static and final.
     */
    private static final long serialVersionUID = -2759205945721406329L;

    /**
     * Creates a <code>NumberOfSubmissionsExceededException</code> instance with
     * this error message.
     *
     * @param message
     *            The error message
     */
    public NumberOfSubmissionsExceededException(String message) {
        super(message);
    }

    /**
     * Creates a <code>NumberOfSubmissionsExceededException</code> instance with
     * this error message and cause of error.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     */
    public NumberOfSubmissionsExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>NumberOfSubmissionsExceededException</code> instance with
     * this error message and any additional data to attach to the exception.
     *
     * @param message
     *            The error message
     * @param data
     *            The additional data to attach to the exception
     */
    public NumberOfSubmissionsExceededException(String message,
        ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a <code>NumberOfSubmissionsExceededException</code> instance with
     * this error message, cause of error, and any additional data to attach to
     * the exception.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     * @param data
     *            The additional data to attach to the exception
     */
    public NumberOfSubmissionsExceededException(String message,
        Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
