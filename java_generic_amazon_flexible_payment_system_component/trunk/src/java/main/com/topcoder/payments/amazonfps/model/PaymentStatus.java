/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

/**
 * <p>
 * This is an enumeration for payment statuses.
 * </p>
 *
 * <strong>Thread Safety:</strong> The enumeration is immutable and thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public enum PaymentStatus {
    /**
     * Represents the not initiated payment status.
     */
    NOT_INITIATED,

    /**
     * Represents the reserved payment status.
     */
    RESERVED,

    /**
     * Represents the completed payment status.
     */
    COMPLETED,

    /**
     * Represents the cancelled payment status.
     */
    CANCELLED,

    /**
     * Represents the refunded payment status.
     */
    REFUNDED,

    /**
     * Represents the failed payment status.
     */
    FAILED
}
