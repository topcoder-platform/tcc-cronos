package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Extends BaseRuntimeException. Constitutes a general configuration exception that all implementations that require
 * such configuration can throw it something goes wrong during that configuration. Called by the SubmissionManagerBean’s
 * initialize method if the “unitName” is not available.
 */
public class SubmissionManagementConfigurationException extends BaseRuntimeException {
    /**
     * Creates a new exception instance with this error message.
     *
     * @param message The error message
     */
    public SubmissionManagementConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message The error message
     * @param cause   The cause of error
     */
    public SubmissionManagementConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with this error message and any additional data to attach to the exception.
     *
     * @param message The error message
     * @param data    The additional data to attach to the exception
     */
    public SubmissionManagementConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new exception instance with this error message, cause of error, and any additional data to attach to the
     * exception.
     *
     * @param message The error message
     * @param cause   The cause of error
     * @param data    The additional data to attach to the exception
     */
    public SubmissionManagementConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

