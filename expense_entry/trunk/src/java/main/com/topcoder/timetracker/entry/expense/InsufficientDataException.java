/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Defines a custom exception which is thrown when some required fields (NOT NULL) are not set when creating or
 * updating an entry, type or status in the persistence. This exception are thrown by the persistence interfaces and
 * implementations, EJBs, and the manager interfaces and implementations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class InsufficientDataException extends BaseException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 3028089326968110841L;

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
