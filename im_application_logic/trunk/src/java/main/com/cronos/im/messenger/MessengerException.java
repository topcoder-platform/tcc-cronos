/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception represents the base exception for Sales IM Messenger component.
 * It will be thrown if any error occurs during the routing the messages.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class MessengerException extends BaseException {

    /**
     * Create the exception with error message.
     *
     * @param msg the error message
     */
    public MessengerException(String msg) {
        super(msg);
    }

    /**
     * Creates the exception with error message and cause.
     *
     * @param msg   the error message
     * @param cause the cause exception
     */
    public MessengerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
