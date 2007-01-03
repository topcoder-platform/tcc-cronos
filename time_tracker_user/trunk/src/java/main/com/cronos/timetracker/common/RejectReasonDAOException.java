/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while working with the RejectReason DAOs.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class RejectReasonDAOException extends BaseException {

    /**
     * <p>
     * The Reject Reason that the DAO was working with when a problem occurred.
     * </p>
     * <p>
     * Initialized In: Constructor (may be null).
     * </p>
     * <p>
     * Accessed In: getProblemRejectReason
     * </p>
     *
     */
    private RejectReason problemRejectReason;

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
    public RejectReasonDAOException(String message, Throwable cause, RejectReason reason) {
        super(message, cause);
        Utils.checkString(message, "message", false);
        this.problemRejectReason = reason;
    }

    /**
     * <p>
     * Retrieves the Reject Reason that the DAO was working with when a problem occurred.
     * </p>
     *
     *
     * @return the Reject Reason that the DAO was working with when a problem occurred.
     */
    public RejectReason getProblemRejectReason() {
        return problemRejectReason;
    }
}
