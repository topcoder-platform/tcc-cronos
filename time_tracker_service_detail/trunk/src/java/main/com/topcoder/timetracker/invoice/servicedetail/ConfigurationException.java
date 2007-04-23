/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This exception should be thrown when problems with component configuration appear. It encapsulates any possible
 * problems with configuration.
 * </p>
 * <p>
 * Class is thread safe because it is immutable
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class ConfigurationException extends InvoiceServiceDetailException {

    /** Serial version UID. */
    private static final long serialVersionUID = 6910342074374356925L;

    /**
     * <p>
     * Constructor which create exception with given text message.
     * </p>
     *
     * @param message
     *            A string representing the message for this exception.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor which create exception with given text message and underlying cause.
     * </p>
     *
     * @param message
     *            A string representing the message for this exception.
     * @param cause
     *            An exception representing the cause of this exception.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
