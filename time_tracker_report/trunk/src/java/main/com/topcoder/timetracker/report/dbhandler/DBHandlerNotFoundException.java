/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.dbhandler;

import com.cronos.timetracker.report.ReportException;


/**
 * This exception indicates that the particular {@link DBHandler} requested is not registered. During the loading of the
 * {@link DBHandlerFactory}, the {@link DBHandler} instances are loaded through reflection after reading the
 * corresponding properties from the configuration file and registered with the name specified in the configuration.
 * <p/>
 * When a request for a particular {@link DBHandler} is made using <tt>DBHandlerFactory.createDbHandler(String)</tt> and
 * there is no {@link DBHandler} registered with the requested name in the {@link DBHandlerFactory}, then this exception
 * is thrown to indicate this circumstance.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class DBHandlerNotFoundException extends ReportException {

    /**
     * Creates a new DBHandlerNotFoundException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     */
    public DBHandlerNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates a new DBHandlerNotFoundException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     * @param cause   the cause of the exception
     */
    public DBHandlerNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
