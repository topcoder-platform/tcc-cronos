/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception will be thrown by SendNotification and its implementation if any error occurred during sending the
 * notification.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationSendingException extends BaseException {
    /**
     * Create the exception with error message.
     *
     * @param msg the error message
     */
    public NotificationSendingException(String msg) {
        super(msg);
    }

    /**
     * Create the exception with error message and cause exception.
     *
     * @param msg the error message
     * @param cause the cause exception
     */
    public NotificationSendingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
