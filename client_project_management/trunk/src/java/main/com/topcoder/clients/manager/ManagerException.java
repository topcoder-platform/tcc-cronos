/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is the base exception of all other exceptions in this component, it's not thrown directly by this
 * component, it extends from BaseCriticalException in Base Exception 2.0.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class ManagerException extends BaseCriticalException {
    /**
     * Constructs an instance of this exception.
     *
     */
    public ManagerException() {
        // empty
    }

    /**
     * Constructs an instance of this exception with error message.
     *
     * @param message
     *            the error message
     */
    public ManagerException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of this exception with error message and error cause.
     *
     * @param message
     *            the error message
     * @param cause
     *            the error message
     */
    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an instance of this exception with error message, error cause and the exception data.
     *
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     * @param exceptionData
     *            an object carrying the details of this exception
     */
    public ManagerException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
