/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This is the base custom exception defined in this component. All the other custom exceptions
 * extend from this one. This exception is not thrown directly by any class.
 * </p>
 * <p>
 * Thread safe: this class is not thread safe, because the base class is not thread safe.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class MessageBoardManagementException extends BaseCriticalException {
    /**
     * Creates a new instance with the given message.
     *
     * @param message
     *            the message
     */
    public MessageBoardManagementException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with the given message and cause.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public MessageBoardManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance with the given message and exception data.
     *
     * @param message
     *            the message
     * @param data
     *            the exception data
     */
    public MessageBoardManagementException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance with the given message,cause and exception data.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param data
     *            the exception data
     */
    public MessageBoardManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
