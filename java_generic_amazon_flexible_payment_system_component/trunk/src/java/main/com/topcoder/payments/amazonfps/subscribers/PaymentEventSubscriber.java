/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers;

import com.topcoder.payments.amazonfps.model.PaymentEvent;

/**
 * <p>
 * The {@code PaymentEventSubscriber} interface represents a subscriber that is expected to listen to and handle
 * payment events.
 * </p>
 *
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when entities
 * that are passed to them are used by the caller in thread safe manner.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public interface PaymentEventSubscriber {
    /**
     * Processes the given payment event.
     *
     * @param paymentEvent
     *            the payment event to be processed
     *
     * @throws IllegalArgumentException
     *            if paymentEvent is {@code null}
     */
    public void processPaymentEvent(PaymentEvent paymentEvent);
}
