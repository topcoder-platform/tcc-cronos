/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception shows the most common failure that occurred to confluence manager. It should be used to wrap
 * exceptions that appeared if no other suitable exceptions can wrap it.
 * </p>
 * <p>
 * <b>Thread Safety:</b> It's not thread safe since its base class is not thread safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagerException extends BaseException {

    /**
     * <p>
     * This constructor creates exception with corresponding message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public ConfluenceManagerException(String message) {
        super(message);
    }

    /**
     * <p>
     * This constructor creates exception with corresponding parameters.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the message cause
     */
    public ConfluenceManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * This constructor creates exception with corresponding parameters.
     * </p>
     *
     * @param data
     *            the exception data
     * @param message
     *            the exception message
     */
    public ConfluenceManagerException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     *<p>
     * This constructor creates exception with corresponding parameters.
     * </p>
     *
     * @param data
     *            the exception data
     * @param message
     *            the exception message
     * @param cause
     *            the message cause
     */
    public ConfluenceManagerException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}