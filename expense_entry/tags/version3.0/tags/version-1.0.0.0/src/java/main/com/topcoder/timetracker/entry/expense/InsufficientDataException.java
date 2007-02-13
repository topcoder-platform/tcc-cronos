/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Defines a custom exception which is thrown if mandatory fields of entries, types or statues are not set when creating
 * or updating the persistence.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class InsufficientDataException extends BaseException {
    /**
     * <p>
     * Creates a new instance of <code>InsufficientDataException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public InsufficientDataException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>InsufficientDataException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public InsufficientDataException(String message, Throwable cause) {
        super(message, cause);
    }
}






