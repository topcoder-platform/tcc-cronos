/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Exception used by the Rates component whenever there is a problem within a RateManager instance.
 * For example, if its pluggable persistence encounters an error, that will be re-thrown as a
 * RateManager exception. Two constructors are provided that both take a reason why the error
 * occurs, and one taking the error which caused this to be thrown.
 * </p>
 * <p>
 * Thread-Safety: This class is thread safe since it's not mutable.
 * </p>
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
 */
public class RateManagerException extends BaseException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -2674965143703331832L;

    /**
     * String-only constructor for the exception, taking a message detailing why the exception
     * occurred.
     *
     * @param message
     *            Descriptive reason the error occurred.
     */
    public RateManagerException(String message) {
        super(message);
    }

    /**
     * Cause Constructor for the exception, taking a message detailing why the exception occurred as
     * well as the initial problem causing this to be raised.
     *
     * @param message
     *            Descriptive reason the error occurred.
     * @param cause
     *            Throwable cause which triggered this exception.
     */
    public RateManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
