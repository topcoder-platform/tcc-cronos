/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Represents an exception related to persistence operations such as cannot create connection,
 * database table does not exist, etc. Inner exception should be provided to give more details about
 * the error. It is used in persistence implementation classes.
 * </p>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceException extends BaseException {

    /**
     * Create a new PersistenceException instance with the specified error message.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Call super(message);</li>
     * @param message the error message of the exception
     */
    public PersistenceException(String message) {
        // your code here
        super(message);
    }

    /**
     * Create a new PersistenceException instance with the specified error message and inner
     * exception.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Call super(message, cause);</li>
     * @param message the error message of the exception
     * @param cause the inner exception
     */
    public PersistenceException(String message, Throwable cause) {
        // your code here
        super(message, cause);
    }
}
