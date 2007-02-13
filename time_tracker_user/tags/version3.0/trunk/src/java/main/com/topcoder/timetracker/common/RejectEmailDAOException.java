/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while working with the RejectEmail DAOs.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class RejectEmailDAOException extends BaseException {

    /**
     * <p>
     * The Reject Email that the DAO was working with when a problem occurred.
     * </p>
     * <p>
     * Initialized In: Constructor (may be null).
     * </p>
     * <p>
     * Accessed In: getProblemRejectEmail
     * </p>
     *
     */
    private final RejectEmail problemRejectEmail;

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
    public RejectEmailDAOException(String message, Throwable cause, RejectEmail problemRejectEmail) {
        super(message, cause);
        Utils.checkString(message, "message", false);
        this.problemRejectEmail = problemRejectEmail;
    }

    /**
     * Retrieves the Reject Email that the DAO was working with when a problem occurred.
     *
     *
     * @return the Reject Email that the DAO was working with when a problem occurred.
     */
    public RejectEmail getProblemRejectEmail() {
        return problemRejectEmail;
    }
}
