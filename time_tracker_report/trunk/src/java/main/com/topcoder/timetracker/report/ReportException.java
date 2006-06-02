/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import com.topcoder.util.errorhandling.BaseException;


/**
 * This is the Base exception of all the exceptions that could occur in generating the Custom Report
 * <p/>
 * Basically this Exception is thrown if there are any problems during the report generation.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportException extends BaseException {

    /**
     * Creates a new ReportException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     */
    public ReportException(final String message) {
        super(message);
    }

    /**
     * Creates a new ReportException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     * <p/>
     * The cause is the inner exception
     *
     * @param message the message describing the exception
     * @param cause   the cause of the exception
     */
    public ReportException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
