/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This exception is thrown if the involved MileageR ate was not found in the data store. This is thrown during
 * update/delete methods.
 * </p>
 *
 * @author TheCois
 * @author TheCois
 * @version 2.0
 */
public class MileageRateNotFoundException extends MileageRateDAOException {

    /**
     * <p>
     * Constructor accepting a message, cause and ProblemMileageRate.
     * </p>
     *
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemMileageRate The MileageRate that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public MileageRateNotFoundException(String message, Throwable cause, MileageRate problemMileageRate) {
        super(message, cause, problemMileageRate);
    }
}
