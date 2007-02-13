/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while working with the Rate DAOs.
 * </p>
 *
 * @author TheCois
 * @author TheCois
 * @version 2.0
 */
public class RateDAOException extends BaseException {

    /**
     * <p>
     * The Rate that the DAO was working with when a problem occurred.
     * </p>
     * <p>
     * Initialized In: Constructor (may be null).
     * </p>
     * <p>
     * Accessed In: getProblemRate
     * </p>
     *
     */
    private final Rate problemRate;

    /**
     * <p>
     * Constructor accepting a message, cause and ProblemRate.
     * </p>
     *
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemRate The Rate that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public RateDAOException(String message, Throwable cause, Rate problemRate) {
        super(message, cause);
        Utils.checkString(message, "message", false);
        this.problemRate = problemRate;
    }

    /**
     * Retrieves the Rate that the DAO was working with when a problem occurred.
     *
     *
     * @return the Rate that the DAO was working with when a problem occurred.
     */
    public Rate getProblemRate() {
        return problemRate;
    }
}
