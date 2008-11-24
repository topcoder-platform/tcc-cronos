/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is thrown when attempt to reestablish connection to the JIRA system fails.
 * <p>
 * Thread-safety. This class is not thread-safe as its base class is not thread-safe.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public class JiraConnectionException extends JiraManagerException {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -1599759615722124501L;

    /**
     * Creates a new <code>JiraConnectionException</code> instance with the given error message.
     *
     * @param message explanation of the error. Can be empty string or null.
     */
    public JiraConnectionException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>JiraConnectionException</code> instance with the given error message
     * and cause.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param cause inner cause of this exception. Can be null.
     */
    public JiraConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new <code>JiraConnectionException</code> instance with the given error message
     * and exception data.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param exceptionData data associated with this exception. Can be null.
     */
    public JiraConnectionException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * Creates a new <code>JiraConnectionException</code> instance with the given error message,
     * cause and exception data.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param cause inner cause of this exception. Can be null.
     * @param exceptionData data associated with this exception. Can be null.
     */
    public JiraConnectionException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
