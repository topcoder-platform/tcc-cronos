/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

/**
 * This exception will be thrown by ReviewPersistence and its implementation if
 * any error occurred during persisting the review entity.
 * @author woodjhon, urtks, saarixx, TCSDEVELOPER
 * @version 1.1
 */
public class ReviewPersistenceException extends ReviewManagementException {

    /**
     * Create the instance with error message.
     * @param msg
     *            the error message
     */
    public ReviewPersistenceException(String msg) {
        super(msg);
    }

    /**
     * Create the instance with error message and cause exception.
     * @param msg
     *            the error message
     * @param cause
     *            the cause exception
     */
    public ReviewPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
