/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This is the base exception for all exceptions raised from operations performed against JIRA
 * system. It includes exceptions raised by JIRA itself and exceptions raised by utilized TopCoder
 * components (except issues with configuration).
 * <p>
 * Thread-safety. This class is not thread-safe as its base class is not thread-safe.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public class JiraManagerException extends BaseCriticalException {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 4986037794506917997L;

    /**
     * Creates a new <code>JiraManagerException</code> instance with the given error message.
     *
     * @param message explanation of the error. Can be empty string or null.
     */
    public JiraManagerException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>JiraManagerException</code> instance with the given error message and
     * cause.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param cause inner cause of this exception. Can be null.
     */
    public JiraManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new <code>JiraManagerException</code> instance with the given error message and
     * exception data.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param exceptionData data associated with this exception. Can be null.
     */
    public JiraManagerException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * Creates a new <code>JiraManagerException</code> instance with the given error message,
     * cause and exception data.
     *
     * @param message explanation of the error. Can be empty string or null.
     * @param cause inner cause of this exception. Can be null.
     * @param exceptionData data associated with this exception. Can be null.
     */
    public JiraManagerException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
