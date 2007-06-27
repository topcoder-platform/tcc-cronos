/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be thrown by MessageBodyGenerator and its implementation if any error occurred during generating
 * the message body.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 1.0
 */
public class MessageBodyGeneratorException extends BaseException {

    /**
     * Automatically generated unique ID for use with serialization.
      */
    private static final long serialVersionUID = -9160553141208560595L;

    /**
     * Create the exception with error message.
     *
     * @param msg the error message
     */
    public MessageBodyGeneratorException(String msg) {
        super(msg);
    }

    /**
     * Create the exception with error message and cause exception.
     *
     * @param msg the error message
     * @param cause the cause exception
     */
    public MessageBodyGeneratorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
