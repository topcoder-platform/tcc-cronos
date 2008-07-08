/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This is a custom exception that will be thrown in case errors occur when accessing the
 * persistence.
 * </p>
 * <p>
 * Thread safe: this class is not thread safe, because the base class is not thread safe.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class MessageBoardPersistenceException extends MessageBoardManagementException {
    /**
     * Creates a new instance with the given message.
     *
     * @param message
     *            the message
     */
    public MessageBoardPersistenceException(String message) {
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
    public MessageBoardPersistenceException(String message, Throwable cause) {
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
    public MessageBoardPersistenceException(String message, ExceptionData data) {
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
    public MessageBoardPersistenceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
