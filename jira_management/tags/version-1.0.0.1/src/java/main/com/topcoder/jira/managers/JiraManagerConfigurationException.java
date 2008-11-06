/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This runtime exception is thrown when configuration data are invalid.
 * <p>
 * Thread-safety. This class is not thread-safe as its base class is not thread-safe.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public class JiraManagerConfigurationException extends BaseRuntimeException {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -7365941835257280586L;

    /**
     * Creates a new <code>JiraManagerConfigurationException</code> instance with the given error
     * message.
     *
     * @param message explanation of the error. Can be empty string or null.
     */
    public JiraManagerConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>JiraManagerConfigurationException</code> instance with the given error
     * message and cause.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param cause inner cause of this exception. Can be null.
     */
    public JiraManagerConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new <code>JiraManagerConfigurationException</code> instance with the given error
     * message and exception data.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param exceptionData data associated with this exception. Can be null.
     */
    public JiraManagerConfigurationException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * Creates a new <code>JiraManagerConfigurationException</code> instance with the given error
     * message, cause and exception data.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param cause inner cause of this exception. Can be null.
     * @param exceptionData data associated with this exception. Can be null.
     */
    public JiraManagerConfigurationException(String message, Throwable cause,
        ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
