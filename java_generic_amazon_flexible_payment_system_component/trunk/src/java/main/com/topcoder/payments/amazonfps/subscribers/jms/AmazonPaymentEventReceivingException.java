/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers.jms;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The <code>AmazonPaymentEventReceivingException</code> exception is thrown by
 * <code>JMSAmazonPaymentEventReceiver</code> when some error occurs while receiving payment events from the queue.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AmazonPaymentEventReceivingException extends BaseCriticalException {
    /**
     * Creates a new instance of <code>AmazonPaymentEventReceivingException</code> with the specified detail message.
     *
     * @param message
     *          the detail message
     */
    public AmazonPaymentEventReceivingException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of <code>AmazonPaymentEventReceivingException</code> with the specified detail message
     * and cause.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     */
    public AmazonPaymentEventReceivingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of <code>AmazonPaymentEventReceivingException</code> with the specified detail message
     * and exception data.
     *
     * @param message
     *          the detail message
     * @param data
     *          the exception data
     */
    public AmazonPaymentEventReceivingException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of <code>AmazonPaymentEventReceivingException</code> with the specified detail message,
     * cause and exception data.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param data
     *          the exception data
     */
    public AmazonPaymentEventReceivingException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
