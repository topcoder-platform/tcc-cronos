/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

/**
 * This exception indicates that the particular Report requested is not registered.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportNotFoundException extends ReportException {

    /**
     * ^Creates a new ReportNotFoundException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     */
    public ReportNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates anew ReportNotFoundException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     * @param cause   the cause of the exception
     */
    public ReportNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
