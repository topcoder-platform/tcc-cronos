/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

/**
 * <p>
 * This exception is thrown if the involved RejectEmail was not found in the data store. This is thrown during
 * update/delete methods.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class RejectEmailNotFoundException extends RejectEmailDAOException {

    /**
     * <p>
     * Constructor accepting a message, cause and ProblemEmail.
     * </p>
     *
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemRejectEmail The RejectEmail that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public RejectEmailNotFoundException(String message, Throwable cause, RejectEmail problemRejectEmail) {
        super(message, cause, problemRejectEmail);
    }
}
