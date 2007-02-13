/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * Thrown if any method in the DAO fails. Most cases will involve <code>SQLException</code> thrown during the course
 * of executing a query.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DAOActionException extends BaseException {
    /**
     * Constructs an <code>DAOActionException</code> with detail message.
     *
     * @param message Error message
     */
    public DAOActionException(String message) {
        super(message);
    }

    /**
     * Constructs an <code>DAOActionException</code> with detail message and the undelying cause.
     *
     * @param message Error message
     * @param cause Underlying cause
     */
    public DAOActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
