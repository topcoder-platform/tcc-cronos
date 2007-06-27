/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be thrown by NotificationPersistence and its implementation if any error occurred during
 * persisting the notification entity.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 1.0
 */
public class NotificationPersistenceException extends BaseException {

    /**
      * Automatically generated unique ID for use with serialization.
      */
    private static final long serialVersionUID = 7025155006313090495L;

    /**
     * Create the exception with error message.
     *
     * @param msg the error message
     */
    public NotificationPersistenceException(String msg) {
        super(msg);
    }

    /**
     * Create the exception with error message and cause exception.
     *
     * @param msg the error message
     * @param cause the cause exception
     */
    public NotificationPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
