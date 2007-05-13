/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

/**
 * <p>Exception used by the Rates component whenever there is a problem within the pluggable persistence layer. Any
 * class implementing the RatePersistence interface may throw this if errors are encountered while persisting
 * information, for example caused by SQL or IO exceptions. This class extends the RateManager exception, to allow a
 * manager to re-throw problems from persistence as a RateManagerException.</p>
 *  <p>Thread-Safety: This class is thread safe since it's not mutable.</p>
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
 */
public class RatePersistenceException extends RateManagerException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -3443950911545234995L;

	/**
     * String-only constructor for the exception, taking a message detailing why the exception occurred.
     * @param message Descriptive reason the error occurred.
     */
    public RatePersistenceException(String message) {
        super(message);
    }

    /**
     * Cause Constructor for the exception, taking a message detailing why the exception occurred as well as the
     * initial problem causing this to be raised.
     * @param message Descriptive reason the error occurred.
     * @param cause Throwable cause which triggered this exception.
     */
    public RatePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
