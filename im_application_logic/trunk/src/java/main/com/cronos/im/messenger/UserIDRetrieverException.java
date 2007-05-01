/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

/**
 * This exception will be thrown by <c>UserIDRetriever</c> interface's concrete implementations
 * if any error occurred during retrieving the user id.
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class UserIDRetrieverException extends MessengerException {

    /**
     * Creates the exception with the specified error message.
     *
     * @param msg The error message of the exception.
     */
    public UserIDRetrieverException(String msg) {
        super(msg);
    }

    /**
     * Creates the exception with the specified error message and cause.
     *
     * @param msg   The error message for the exception.
     * @param cause The cause of the exception.
     */
    public UserIDRetrieverException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
