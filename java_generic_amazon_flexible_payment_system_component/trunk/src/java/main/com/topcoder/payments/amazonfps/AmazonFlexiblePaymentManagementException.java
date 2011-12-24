/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The <code>AmazonFlexiblePaymentManagementException</code> exception is thrown by implementations of
 * <code>AmazonPaymentManager</code> when some critical error that is not expected to be handled via payment
 * event occurs. Also this exception is used as a base class for other specific custom exceptions.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AmazonFlexiblePaymentManagementException extends BaseCriticalException {
    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentManagementException</code> with the specified detail
     * message.
     *
     * @param message
     *          the detail message
     */
    public AmazonFlexiblePaymentManagementException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentManagementException</code> with the specified detail
     * message and cause.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     */
    public AmazonFlexiblePaymentManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentManagementException</code> with the specified detail
     * message and exception data.
     *
     * @param message
     *          the detail message
     * @param data
     *          the exception data
     */
    public AmazonFlexiblePaymentManagementException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentManagementException</code> with the specified detail
     * message, cause and exception data.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param data
     *          the exception data
     */
    public AmazonFlexiblePaymentManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
