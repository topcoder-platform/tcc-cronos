package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Extends SubmissionManagementException. It is thrown by the updateXXX and removeXXX methods if the given entity is not
 * found in persistence.
 */
public class EntityNotFoundException extends SubmissionManagementException {
    /**
     * Creates a new exception instance with this error message.
     *
     * @param message The error message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message The error message
     * @param cause   The cause of error
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with this error message and any additional data to attach to the exception.
     *
     * @param message The error message
     * @param data    The additional data to attach to the exception
     */
    public EntityNotFoundException(String message, ExceptionData data) {
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
    public EntityNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

