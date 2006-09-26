/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This exception is thrown if the DAO has failed to successfully update the
 * Screening Task because it has already been modified by another concurrently
 * running Screening Task.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class UpdateFailedException extends DAOException {

    /**
     * Constructor accepting a message.
     * @param message
     *            the message of the exception.
     */
    public UpdateFailedException(String message) {
        super(message);
    }

    /**
     * Constructor accepting a message and cause.
     * @param message
     *            the message of the exception.
     * @param cause
     *            the cause of the exception.
     */
    public UpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
