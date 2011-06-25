/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is thrown from the actions if any unexpected error occurs in execute method. (it's used to wrap all
 * underlying exceptions).
 * <p>
 * Thread safety: This class is not thread-safe as it's base class is not.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UserPreferencesActionExecutionException extends BaseCriticalException {

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message
     */
    public UserPreferencesActionExecutionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message
     * @param data the exception data
     */
    public UserPreferencesActionExecutionException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message the message.
     * @param cause the exception cause
     */
    public UserPreferencesActionExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message
     * @param cause the exception cause
     * @param data the exception data
     */
    public UserPreferencesActionExecutionException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
