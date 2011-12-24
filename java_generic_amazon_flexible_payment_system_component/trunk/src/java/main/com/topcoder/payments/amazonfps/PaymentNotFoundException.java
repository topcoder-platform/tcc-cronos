/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The <code>PaymentNotFoundException</code> exception is thrown by implementations of
 * <code>PaymentPersistence</code> and <code>AmazonPaymentManager</code> when payment with the
 * specified ID doesn't exist.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PaymentNotFoundException extends AmazonFlexiblePaymentManagementException {
    /**
     * The ID of the authorization that doesn't exist.
     * It is initialized in the constructor and never changed after that.
     * Can be any value. Has a getter.
     */
    private final long paymentId;

    /**
     * Creates a new instance of <code>PaymentNotFoundException</code> with the specified detail message and
     * payment id.
     *
     * @param message
     *          the detail message
     * @param paymentId
     *          the ID of the payment that doesn't exist
     */
    public PaymentNotFoundException(String message, long paymentId) {
        super(message);
        this.paymentId = paymentId;
    }

    /**
     * Creates a new instance of <code>PaymentNotFoundException</code> with the specified detail message, cause and
     * payment id.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param paymentId
     *          the ID of the payment that doesn't exist
     */
    public PaymentNotFoundException(String message, Throwable cause, long paymentId) {
        super(message, cause);
        this.paymentId = paymentId;
    }

    /**
     * Creates a new instance of <code>PaymentNotFoundException</code> with the specified detail message,
     * exception data and payment id.
     *
     * @param message
     *          the detail message
     * @param data
     *          the exception data
     * @param paymentId
     *          the ID of the payment that doesn't exist
     */
    public PaymentNotFoundException(String message, ExceptionData data, long paymentId) {
        super(message, data);
        this.paymentId = paymentId;
    }

    /**
     * Creates a new instance of <code>PaymentNotFoundException</code> with the specified detail message,
     * cause, exception data and payment id.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param data
     *          the exception data
     * @param paymentId
     *          the ID of the payment that doesn't exist
     */
    public PaymentNotFoundException(String message, Throwable cause, ExceptionData data, long paymentId) {
        super(message, cause, data);
        this.paymentId = paymentId;
    }

    /**
     * Retrieves the ID of the payment that doesn't exist.

     * @return the ID of the payment that doesn't exist
     */
    public long getPaymentId() {
        return paymentId;
    }
}
