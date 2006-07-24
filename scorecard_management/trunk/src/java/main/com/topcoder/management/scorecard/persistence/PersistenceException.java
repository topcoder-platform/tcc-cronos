/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Represents an exception related to persistence operations such as cannot create connection, database table does not
 * exist, etc. Inner exception should be provided to give more details about the error. It is used in persistence
 * implementation classes. Thread safety: This class is immutable and thread safe.
 * @author tuenm, zhuzeyuan
 * @version 1.0
 */
public class PersistenceException extends BaseException {

    /**
     * Create a new PersistenceException instance with the specified error message. Implementation notes:
     * @param message
     *            the error message of the exception
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Create a new PersistenceException instance with the specified error message and inner exception.
     * @param message
     *            the error message of the exception
     * @param cause
     *            the inner exception
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
