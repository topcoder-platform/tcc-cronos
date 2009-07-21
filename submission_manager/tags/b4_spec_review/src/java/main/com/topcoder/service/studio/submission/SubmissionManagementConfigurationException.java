/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This class represents a general configuration exception that all implementations that require such configuration can
 * throw it something goes wrong during that configuration. Currently, it will be thrown by Called by the initialize
 * method of <code>SubmissionManagerBean</code> if the configuration is not available.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class SubmissionManagementConfigurationException extends BaseRuntimeException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -6447689757816161386L;

    /**
     * Creates a <code>SubmissionManagementConfigurationException</code> instance with the error message.
     *
     * @param message
     *            The error message
     */
    public SubmissionManagementConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a <code>SubmissionManagementConfigurationException</code> instance with the error message and cause of
     * error.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     */
    public SubmissionManagementConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>SubmissionManagementConfigurationException</code> instance with the error message and any
     * additional data to attach to the exception.
     *
     * @param message
     *            The error message
     * @param data
     *            The additional data to attach to the exception
     */
    public SubmissionManagementConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a <code>SubmissionManagementConfigurationException</code> instance with the error message, cause of
     * error, and any additional data to attach to the exception.
     *
     * @param message
     *            The error message
     * @param cause
     *            The cause of error
     * @param data
     *            The additional data to attach to the exception
     */
    public SubmissionManagementConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
