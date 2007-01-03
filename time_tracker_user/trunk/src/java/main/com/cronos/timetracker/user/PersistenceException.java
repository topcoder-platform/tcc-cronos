/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by all classes of this component if some error occurs while persisting
 * User information (either loading or saving data to the data store). Usually this exception will
 * wrap the original exception which caused the operation to fail (e.g., SQLException).
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceException extends BaseException {

    /**
     * <p>
     * Constructs a new <code>PersistenceException</code> with the given message providing the
     * details of the error that occurred.
     * </p>
     *
     * @param message the details of the error that occurred.
     */
    public PersistenceException(String message) {
        super(message);
    }


    /**
     * <p>
     * Constructs a new <code>PersistenceException</code>, with the given message and cause.
     * </p>
     *
     * @param message the details of the error that occurred.
     * @param cause the original cause of this exception, e.g., a SQLException
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>
     * Constructs a new <code>PersistenceException</code> with the given cause.
     * </p>
     *
     * @param cause the original cause of this exception, e.g., a SQLException
     */
    public PersistenceException(Throwable cause) {
        super(cause);
    }
}