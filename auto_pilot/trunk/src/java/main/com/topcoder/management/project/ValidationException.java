/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Represents an exception related to validating projects. When a project validation failed due to
 * some reason, this exception will be thrown including the validation message.Inner exception
 * should be provided whenever possible to give more details about the error. It is used in classes
 * of the validation package.
 * </p>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationException extends BaseException {

    /**
     * Create a new ValidationException instance with the specified error message.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Call super(message);</li>
     * </ul>
     * @param message the error message of the exception
     */
    public ValidationException(String message) {
        // your code here
    }

    /**
     * Create a new ValidationException instance with the specified error message and inner
     * exception.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Call super(message, cause);</li>
     * </ul>
     * @param message the error message of the exception
     * @param cause the inner exception
     */
    public ValidationException(String message, Throwable cause) {
        // your code here
    }
}
