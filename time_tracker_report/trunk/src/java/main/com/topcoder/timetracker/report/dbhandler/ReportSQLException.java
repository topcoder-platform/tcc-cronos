/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.dbhandler;

import com.cronos.timetracker.report.ReportException;


/**
 * This exception is thrown, if there is an exception during access to the Database or during the fetch of the data from
 * the Database.
 * <p/>
 * This exception is basically a wrapper around a caught {@link java.sql.SQLException} and indicates an exception during
 * Database operation.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportSQLException extends ReportException {

    /**
     * Creates a new ReportSQLException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     */
    public ReportSQLException(final String message) {
        super(message);
    }

    /**
     * Creates a new ReportSQLException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     * @param cause   the cause of the exception
     */
    public ReportSQLException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
