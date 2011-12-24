/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

/**
 * <p>
 * This is an enumeration for payment operation types.
 * </p>
 *
 * <strong>Thread Safety:</strong> The enumeration is immutable and thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public enum PaymentOperationType {
    /**
     * Represents the complete payment operation type.
     */
    PAY,

    /**
     * Represents the reservation payment operation type.
     */
    RESERVE,

    /**
     * Represents the settlement payment operation type.
     */
    SETTLE,

    /**
     * Represents the refunding payment operation type.
     */
    REFUND,

    /**
     * Represents the cancellation payment operation type.
     */
    CANCEL
}
