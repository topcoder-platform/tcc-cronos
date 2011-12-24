/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.stresstests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.subscribers.ConfigurablePaymentEventSubscriber;


/**
 * Mock implementation of PaymentEventSubscriber for test.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockPaymentEventSubscriber implements ConfigurablePaymentEventSubscriber {

	/**
	 * Mock configuration.
	 * @param configuration the configuration object.
	 */
	public void configure(ConfigurationObject configuration) {
	}

    /**
     * Mock the processing of payment event.
     *
     * @param paymentEvent the payment event to be processed
     */
    public void processPaymentEvent(PaymentEvent paymentEvent) {
    }
}
