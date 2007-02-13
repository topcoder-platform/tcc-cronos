/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This exception is thrown if the involved Rate was not found in the data store. This is thrown during
 * update/delete methods.
 * </p>
 *
 * @author TheCois
 * @author TheCois
 * @version 2.0
 */
public class RateNotFoundException extends RateDAOException {

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
    public RateNotFoundException(String message, Throwable cause, Rate problemRate) {
        super(message, cause, problemRate);
    }
}
