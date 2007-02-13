/*
 * PersistenceException.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.persistence;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is used to wrap any persistence implementation specific exception. These exceptions are thrown by the
 * TimeTrackerProjectPersistence interface implementations. Since they are implementation specific, there needs to be
 * a common way to report them to the user, and that is what this exception is used for.
 * </p>
 *
 * <p>
 * This exception is originally thrown in the persistence implementations. The business logic layer (the utility
 * classes) will forward them to the user.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceException extends BaseException {
    /**
     * <p>
     * Creates a new instance of this custom exception with the given message. The message can be null, however.
     * </p>
     *
     * @param message the message describing the exception
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this custom exception with the given message and cause. The message and cause can be
     * null, however.
     * </p>
     *
     * @param message the message describing the exception
     * @param cause the cause of the exception
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
