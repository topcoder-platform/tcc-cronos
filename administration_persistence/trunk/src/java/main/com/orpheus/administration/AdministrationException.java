/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration;

import com.topcoder.util.errorhandling.BaseException;

/**
 * The base class for all administration exceptions.
 *
 * @author TopCoder, TCSDEVELOPER
 * @version 1.0
 */

public class AdministrationException extends BaseException {
    /**
     * Creates a new <code>AdministrationException</code> with the specifed message.
     *
     * @param message a message describing the reason for the exception
     */
    public AdministrationException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>AdministrationException</code> with the specified message and cause.
     *
     * @param message a message describing the reason for the exception
     * @param cause the lower-level exception that caused this exception to be thrown
     */
    public AdministrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
