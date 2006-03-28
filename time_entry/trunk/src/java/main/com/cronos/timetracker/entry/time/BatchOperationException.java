/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

/**
 * <p>
 * This exception signals that something went wrong with the batch operation.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.1
 */
public class BatchOperationException extends DAOActionException {
    /**
     * Constructs an <code>BatchOperationException</code> with detail message.
     *
     * @param message Error message
     */
    public BatchOperationException(String message) {
        super(message);
    }

    /**
     * Constructs an <code>BatchOperationException</code> with detail message and the undelying cause.
     *
     * @param message Error message
     * @param cause Underlying cause
     */
    public BatchOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
