/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * <p>
 * This exception is thrown if an error occurs when data is read. For example is thrown when some
 * error occurs during the query into database and normally wrap the SQLException. This exception is
 * throws by ReportDAO.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> Exceptions are thread-safe.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class ReportDataAccessException extends ReportException {

    /**
	 * Automatically generated unique ID for use qith serialization.
	 */
	private static final long serialVersionUID = 6064265589243176418L;

	/**
     * <p>
     * Constructs a new <code>ReportDataAccessException</code>, with the given error message.
     * </p>
     *
     * @param message A descriptive message to describe why this exception is generated.
     */
    public ReportDataAccessException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>ReportDataAccessException</code>, with the given error message and
     * cause of exception.
     * </p>
     *
     * @param message A descriptive message to describe why this exception is generated.
     * @param cause The exception(or a chain of exceptions) that generated this exception.
     */
    public ReportDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
