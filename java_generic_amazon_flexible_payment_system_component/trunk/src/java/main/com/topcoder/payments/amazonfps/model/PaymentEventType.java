/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

/**
 * <p>
 * This is an enumeration for payment event types.
 * </p>
 *
 * <strong>Thread Safety:</strong> The enumeration is immutable and thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public enum PaymentEventType {
    /**
     * Represents the payment success event type.
     */
    PAYMENT_SUCCESS,

    /**
     * Represents the payment failure event type.
     */
    PAYMENT_FAILURE,

    /**
     * Represents the reservation success event type.
     */
    RESERVATION_SUCCESS,

    /**
     * Represents the reservation failure event type.
     */
    RESERVATION_FAILURE,

    /**
     * Represents the settlement success event type.
     */
    SETTLEMENT_SUCCESS,

    /**
     * Represents the settlement failure event type.
     */
    SETTLEMENT_FAILURE,

    /**
     * Represents the refund success event type.
     */
    REFUND_SUCCESS,

    /**
     * Represents the refund failure event type.
     */
    REFUND_FAILURE,

    /**
     * Represents the payment cancellation success event type.
     */
    PAYMENT_CANCELLATION_SUCCESS,

    /**
     * Represents the payment cancellation failure event type.
     */
    PAYMENT_CANCELLATION_FAILURE,

    /**
     * Represents the authorization success event type.
     */
    AUTHORIZATION_SUCCESS,

    /**
     * Represents the authorization failure event type.
     */
    AUTHORIZATION_FAILURE,

    /**
     * Represents the authorization cancellation success event type.
     */
    AUTHORIZATION_CANCELLATION_SUCCESS,

    /**
     * Represents the authorization cancellation failure event type.
     */
    AUTHORIZATION_CANCELLATION_FAILURE
}
