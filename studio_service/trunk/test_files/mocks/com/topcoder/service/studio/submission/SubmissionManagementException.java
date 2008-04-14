package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Extends BaseCriticalException. It is thrown by all CRUDE methods if there is an error during an operation.
 */
public class SubmissionManagementException extends BaseCriticalException {
    /**
     * Creates a new exception instance with this error message.
     *
     * @param message The error message
     */
    public SubmissionManagementException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message The error message
     * @param cause   The cause of error
     */
    public SubmissionManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with this error message and any additional data to attach to the exception.
     *
     * @param message The error message
     * @param data    The additional data to attach to the exception
     */
    public SubmissionManagementException(String message, ExceptionData data) {
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
    public SubmissionManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

