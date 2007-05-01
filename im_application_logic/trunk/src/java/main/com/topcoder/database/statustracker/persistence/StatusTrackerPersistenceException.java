/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker.persistence;

import com.topcoder.database.statustracker.StatusTrackerException;

/**
 * <p>
 * This exception, or one of it subclasses, is thrown from nearly every method in the persistence
 * subpackage of the Status Tracker component.
 * </p>
 *
 * <p>
 * When not subclassed, it is intended to be used with an underlying 'cause' exception.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is immutable and thread safe.
 * </p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class StatusTrackerPersistenceException extends StatusTrackerException {
    /**
     * <p>
     * Construct this exception with the given message.
     * </p>
     *
     * <p>
     * Note, the message can be null.
     * </p>
     *
     * @param message the message to include.
     *
     * @throws IllegalArgumentException if message is empty after trim
     */
    public StatusTrackerPersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Construct this exception with the given message and underlying cause.
     * This constructor can be used to wrap another exception, such as SQLException.
     * </p>
     *
     * <p>
     * Note, the message can be null.
     * </p>
     *
     * @param message the message to include.
     * @param cause the underlying cause of this exception
     *
     * @throws IllegalArgumentException if message is empty after trim, or cause is null.
     */
    public StatusTrackerPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
