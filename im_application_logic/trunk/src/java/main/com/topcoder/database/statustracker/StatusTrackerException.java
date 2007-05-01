/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception, or one of it subclasses, represents a general exception that occurred during persistence or
 * configuration of the Status Tracker component.
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
public class StatusTrackerException extends BaseException {
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
    public StatusTrackerException(String message) {
        super(message);
        if (message != null && message.trim().length() == 0) {
            throw new IllegalArgumentException("message is empty after trimmed.");
        }
    }

    /**
     * <p>
     * Construct this exception with the given message and underlying cause. This constructor can be
     * used to wrap another exception, such as SQLException or ConfigManagerException.
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
    public StatusTrackerException(String message, Throwable cause) {
        super(message, cause);
        if (message != null && message.trim().length() == 0) {
            throw new IllegalArgumentException("message is empty after trimmed.");
        }
        Util.checkNull(cause, "cause");
    }
}
