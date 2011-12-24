/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventSubscriber;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for JMSAmazonPaymentEventSubscriber.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class JMSAmazonPaymentEventSubscriberFailureTests extends TestCase {

    /**
     * <p>
     * Represent the JMSAmazonPaymentEventSubscriber instance for testing.
     * </p>
     */
    private JMSAmazonPaymentEventSubscriber instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new JMSAmazonPaymentEventSubscriber();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(JMSAmazonPaymentEventSubscriberFailureTests.class);
    }

    /**
     * <p>
     * Tests JMSAmazonPaymentEventSubscriber#processPaymentEvent(PaymentEvent) for failure.
     * It tests the case that when paymentEvent is null and expects IllegalArgumentException.
     * </p>
     */
    public void testProcessPaymentEvent_NullPaymentEvent() {
        try {
            instance.processPaymentEvent(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JMSAmazonPaymentEventSubscriber#processPaymentEvent(PaymentEvent) for failure.
     * It tests the case that when queueName is null and expects IllegalStateException.
     * </p>
     */
    public void testProcessPaymentEvent_NullqueueName() {
        try {
            instance.processPaymentEvent(new PaymentEvent());
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JMSAmazonPaymentEventSubscriber#configure(ConfigurationObject) for failure.
     * It tests the case that when configuration is null and expects IllegalArgumentException.
     * </p>
     */
    public void testConfigure_NullConfiguration() {
        try {
            instance.configure(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JMSAmazonPaymentEventSubscriber#configure(ConfigurationObject) for failure.
     * It tests the case that when authorizationIdGeneratorName is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConfigure_NullauthorizationIdGeneratorName() throws Exception {
        try {
            instance.configure(new DefaultConfigurationObject("test"));
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

}