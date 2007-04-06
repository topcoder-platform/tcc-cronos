/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.errorhandling.BaseException;


/**
 * This exception will be thrown if failed to create the object from configuration file.
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationConfigurationException extends BaseException {
    /**
     * Create the exception with error message.
     *
     * @param msg the error message
     */
    public NotificationConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Create the exception with error message and cause exception.
     *
     * @param msg the error message
     * @param cause the cause exception
     */
    public NotificationConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
