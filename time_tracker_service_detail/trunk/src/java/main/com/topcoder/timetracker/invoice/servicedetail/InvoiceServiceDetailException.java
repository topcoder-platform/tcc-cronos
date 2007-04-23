/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception should never be thrown. This is used only as a parent for all rest exception.
 * </p>
 * <p>
 * Class is thread safe because it is immutable
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class InvoiceServiceDetailException extends BaseException {

    /** Serial version UID. */
    private static final long serialVersionUID = -3212883683386477847L;

    /**
     * <p>
     * Constructor which create exception with given text message.
     * </p>
     *
     * @param message
     *            A string representing the message for this exception.
     */
    public InvoiceServiceDetailException(String message) {
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
    public InvoiceServiceDetailException(String message, Throwable cause) {
        super(message, cause);
    }
}
