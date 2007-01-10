/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while working with the MileageRate DAOs.
 * </p>
 *
 * @author TheCois
 * @author TheCois
 * @version 2.0
 */
public class MileageRateDAOException extends BaseException {

    /**
     * <p>
     * The MileageRate that the DAO was working with when a problem occurred.
     * </p>
     * <p>
     * Initialized In: Constructor (may be null).
     * </p>
     * <p>
     * Accessed In: getProblemMileageRate
     * </p>
     *
     */
    private final MileageRate problemMileageRate;

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
    public MileageRateDAOException(String message, Throwable cause, MileageRate problemMileageRate) {
        super(message, cause);
        Utils.checkString(message, "message", false);
        this.problemMileageRate = problemMileageRate;
    }

    /**
     * Retrieves the MileageRate that the DAO was working with when a problem occurred.
     *
     *
     * @return the MileageRate that the DAO was working with when a problem occurred.
     */
    public MileageRate getProblemMileageRate() {
        return problemMileageRate;
    }
}
