/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.converter;

/**
 * This is thrown if any error occurs during conversion between date and string.
 * It's thrown by DateConverter.
 *
 * Thread Safety: This class is not thread-safe because the base class is not
 * thread-safe.
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DateConversionException extends RuntimeException {
    /**
     * Create a new exception with error message.
     *
     * @param message the error message
     *
     */
    public DateConversionException(String message) {
        super(message);
    }

    /**
     * Create a new exception with error message and inner cause.
     *
     * @param message the error message
     * @param cause inner cause
     *
     */
    public DateConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
