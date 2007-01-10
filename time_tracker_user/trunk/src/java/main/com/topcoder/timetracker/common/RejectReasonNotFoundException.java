/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This exception is thrown if the involved RejectReason was not found in the data store. This is thrown during
 * update/delete methods.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class RejectReasonNotFoundException extends RejectReasonDAOException {

    /**
     * <p>
     * Constructor accepting a message, cause and problem RejectReason.
     * </p>
     *
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param reason The RejectReason that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public RejectReasonNotFoundException(String message, Throwable cause, RejectReason reason) {
        super(message, cause, reason);
    }
}
