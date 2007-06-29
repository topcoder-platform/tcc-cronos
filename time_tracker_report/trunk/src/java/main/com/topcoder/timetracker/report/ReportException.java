/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is the base for other exception of this component. This is thrown by the
 * interfaces ReportDAO, Report and ReportSearchBuilder.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> Exceptions are thread-safe.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 *
 *
 * @see BaseException
 */
public class ReportException extends BaseException {

    /**
     * Automatically generated unique ID for use qith serialization.
     */
    private static final long serialVersionUID = -5498598824361127627L;

    /**
     * <p>
     * Constructs a new <code>ReportException</code>, with the given error message.
     * </p>
     *
     * @param message A descriptive message to describe why this exception is generated.
     */
    public ReportException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>ReportException</code>, with the given error message and cause of
     * exception.
     * </p>
     *
     * @param message A descriptive message to describe why this exception is generated.
     * @param cause The exception(or a chain of exceptions) that generated this exception.
     */
    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
