/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>This is an exception that is thrown when an issue with persistence (connection issue, missing table issue)
 * occurs. This is a broad exception.</p>
 *  <p>Thread Safety: This class is thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class PersistenceException extends BaseException {
    /**
     * Creates a new exception with passed error message.
     * @param msg error message
     */
    public PersistenceException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception with this error message and cause of error.
     * @param msg error message
     * @param cause the cause of the error
     */
    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
