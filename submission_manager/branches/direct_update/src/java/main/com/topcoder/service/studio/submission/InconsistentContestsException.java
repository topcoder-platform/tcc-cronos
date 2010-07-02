/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.ExceptionData;

import javax.ejb.ApplicationException;


/**
 * This is a new custom exception defined in version 1.2.
 * <p>
 * This exception is thrown by the the setSubmissionMilestonePrize method if the
 * contest the submission belongs to is not one of the contests set of the
 * milestone prize.
 * </p>
 *
 * @ApplicationException(rollback = true) is used for this custom exception.
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 */
@ApplicationException(rollback = true)
public class InconsistentContestsException extends SubmissionManagementException {
    /**
     * It represents the serial version unique id. It's static and final.
     */
    private static final long serialVersionUID = 1174891023948529132L;

    /**
     * Creates an <code> InconsistentContestsException</code> instance with this
     * error message.
     *
     * @param message
     *            The error message
     */
    public InconsistentContestsException(String message) {
        super(message);
    }

    /**
     * Creates an <code> InconsistentContestsException</code> instance with this
     * error message and cause of error.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     */
    public InconsistentContestsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an <code> InconsistentContestsException</code> instance with this
     * error message and any additional data to attach to the exception.
     *
     * @param message
     *            The error message
     * @param data
     *            The additional data to attach to the exception
     */
    public InconsistentContestsException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates an <code> InconsistentContestsException</code> instance with this
     * error message, cause of error, and any additional data to attach to the
     * exception.
     *
     * @param message
     *            The error message
     * @param cause
     *            The error cause
     * @param data
     *            The additional data to attach to the exception
     */
    public InconsistentContestsException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
