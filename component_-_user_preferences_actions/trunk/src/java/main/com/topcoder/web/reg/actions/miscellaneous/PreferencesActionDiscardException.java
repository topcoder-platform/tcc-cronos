/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown from all the actions of this component if some error occurs while attempting to discard the
 * changes.
 * </p>
 * <p>
 * Thread safety: This class is not thread-safe as it's base class is not.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PreferencesActionDiscardException extends BaseCriticalException {

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message
     */
    public PreferencesActionDiscardException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message
     * @param data the exception data
     */
    public PreferencesActionDiscardException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates instance with specified parameters. Simply delegates to base class constructor with same signature.
     * </p>
     * @param message the error message the message.
     * @param cause the exception cause
     */
    public PreferencesActionDiscardException(String message, Throwable cause) {
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
    public PreferencesActionDiscardException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
