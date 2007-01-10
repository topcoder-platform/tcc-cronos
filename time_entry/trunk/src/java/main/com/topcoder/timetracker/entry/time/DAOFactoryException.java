/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is thrown by the DAOFactory when it cannot accomplish the task of matching a DAO to the given
 * DataObject.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DAOFactoryException extends BaseException {
    /**
     * Constructs an <code>DAOFactoryException</code> with detail message.
     *
     * @param message Error message
     */
    public DAOFactoryException(String message) {
        super(message);
    }

    /**
     * Constructs an <code>DAOFactoryException</code> with detail message and the undelying cause.
     *
     * @param message Error message
     * @param cause Underlying cause
     */
    public DAOFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
