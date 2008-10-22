/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if a converter is provided with an object that it cannot accurately convert.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe since its parent is not.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ConversionException extends BaseCriticalException {

    /**
     * <p>
     * Constructs an instance of this exception with specified.
     * </p>
     * @param message
     *        The message of the exception.
     */
    public ConversionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs an instance of this exception with specified error message and inner cause.
     * </p>
     * @param message
     *        The message of the exception
     * @param cause
     *        The cause of the exception
     */
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructs an instance of this exception with specified error message and exception data.
     * </p>
     * @param data
     *        Exception data to include with the exception
     * @param message
     *        The message of the exception
     */
    public ConversionException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructs an instance of this exception with specified error message, inner cause and exception data.
     * </p>
     * @param data
     *        Exception data to include with the exception
     * @param message
     *        The message of the exception
     * @param cause
     *        The cause of the exception
     */
    public ConversionException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
