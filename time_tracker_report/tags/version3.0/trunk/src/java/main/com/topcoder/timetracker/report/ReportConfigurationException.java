/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * This exception is thrown if any invalid configuration is detected  for a report. This includes <ul><li>Missing
 * required parameters in the configuration.</li><li>Invalid Filters are specified for a report. (Filters not applicable
 * for a particular report are specified in the Configuration)</li><li>Missing parameter values for mandatory filter for
 * a report.</li> <li>Used as a wrapper for wrapping an exception thrown from the ConfigurationManager
 * component.</li></ul>
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportConfigurationException extends ReportException {

    /**
     * Creates a new ReportConfigurationException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     */
    public ReportConfigurationException(final String message) {
        super(message);
    }

    /**
     * Creates a new ReportConfigurationException.
     * <p/>
     * The message needs to be meaningful, so that the user will benefit from meaningful messages.
     *
     * @param message the message describing the exception
     * @param cause   the cause of the exception
     */
    public ReportConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
