/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by the DAOs to signify a problem with accessing the
 * data store.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class DAOException extends BaseException {

    /**
     * Constructor accepting a message.
     * @param message
     *            the message of the exception.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor accepting a message and cause.
     * @param message
     *            the message of the exception.
     * @param cause
     *            the cause of the exception.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
