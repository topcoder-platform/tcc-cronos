/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This exception is thrown if a problem occurs during the screening process.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class ScreeningException extends BaseRuntimeException {

    /**
     * Constructor accepting a message.
     * @param message
     *            The message of the exception.
     */
    public ScreeningException(String message) {
        super(message);
    }

    /**
     * Constructor accepting a message and cause.
     * @param message
     *            The message of the exception.
     * @param cause
     *            The cause of the exception.
     */
    public ScreeningException(String message, Throwable cause) {
        super(message, cause);
    }
}
