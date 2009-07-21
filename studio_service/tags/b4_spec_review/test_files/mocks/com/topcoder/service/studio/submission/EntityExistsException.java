package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Extends SubmissionManagementException. It is thrown by some of the addXXX methods if the given entity already exists
 * in persistence.
 */
public class EntityExistsException extends SubmissionManagementException {
    /**
     * Creates a new exception instance with this error message.
     *
     * @param message The error message
     */
    public EntityExistsException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message The error message
     * @param cause   The cause of error
     */
    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with this error message and any additional data to attach to the exception.
     *
     * @param message The error message
     * @param data    The additional data to attach to the exception
     */
    public EntityExistsException(String message, ExceptionData data) {
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
    public EntityExistsException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

