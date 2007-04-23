/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This exception should be thrown if appears problem to look up session bean. This exception has neutral name to
 * keep back user about usage EJB
 * </p>
 * <p>
 * Class is thread safe because it is immutable
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class TransactionCreationException extends InvoiceServiceDetailException {

    /** Serial version UID. */
    private static final long serialVersionUID = 1409170634762246736L;

    /**
     * <p>
     * constructor which create exception with given text message.
     * </p>
     *
     * @param message
     *            An exception representing the cause of this exception.
     */
    public TransactionCreationException(String message) {
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
    public TransactionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
