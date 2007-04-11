/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * <p>
 * This exception is thrown if an error occurs when the configuration file is read.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> Exceptions are thread-safe.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class ReportConfigException extends ReportException {

    /**
     * <p>
     * Constructs a new <code>ReportConfigException</code>, with the given error message.
     * </p>
     *
     * @param message A descriptive message to describe why this exception is generated.
     */
    public ReportConfigException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>ReportConfigException</code>, with the given error message and
     * cause of exception.
     * </p>
     *
     * @param message A descriptive message to describe why this exception is generated.
     * @param cause The exception(or a chain of exceptions) that generated this exception.
     */
    public ReportConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
