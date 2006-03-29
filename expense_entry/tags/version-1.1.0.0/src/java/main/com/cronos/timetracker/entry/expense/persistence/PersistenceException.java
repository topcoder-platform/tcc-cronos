/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.persistence;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * Defines a custom exception which is thrown if error occurs when operating on persistence, or the value in the
 * persistence is not valid.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class PersistenceException extends BaseException {
    /**
     * <p>
     * Creates a new instance of <code>PersistenceException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>PersistenceException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
