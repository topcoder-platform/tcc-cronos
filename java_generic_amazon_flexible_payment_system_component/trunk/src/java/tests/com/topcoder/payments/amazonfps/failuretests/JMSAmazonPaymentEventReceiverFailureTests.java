/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.subscribers.jms.AmazonPaymentEventReceivingException;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventReceiver;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for JMSAmazonPaymentEventReceiver.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class JMSAmazonPaymentEventReceiverFailureTests extends TestCase {

    /**
     * <p>
     * Represent the JMSAmazonPaymentEventReceiver instance for testing.
     * </p>
     */
    private JMSAmazonPaymentEventReceiver instance;

    /**
     * <p>
     * Represent the ConfigurationObject instance for testing.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        configuration = FailureTestHelper.getConfig("com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventReceiver");
        instance = new JMSAmazonPaymentEventReceiver(configuration);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(JMSAmazonPaymentEventReceiverFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor JMSAmazonPaymentEventReceiver#JMSAmazonPaymentEventReceiver(ConfigurationObject) for failure.
     * It tests the case that when configuration is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullConfiguration() {
        try {
            new JMSAmazonPaymentEventReceiver(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor JMSAmazonPaymentEventReceiver#JMSAmazonPaymentEventReceiver(ConfigurationObject) for failure.
     * It tests the case that when jmsConnectionFactoryName is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     */
    public void testCtor_NulljmsConnectionFactoryName() {
        try {
            new JMSAmazonPaymentEventReceiver(new DefaultConfigurationObject("test"));
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JMSAmazonPaymentEventReceiver#receivePaymentEvents(long) for failure.
     * It tests the case that when timeout is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testReceivePaymentEvents_NegativeTimeout() throws Exception {
        try {
            instance.receivePaymentEvents(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JMSAmazonPaymentEventReceiver#receivePaymentEvents(long) for failure.
     * Expects AmazonPaymentEventReceivingException.
     * </p>
     */
    public void testReceivePaymentEvents_AmazonPaymentEventReceivingException() {
        try {
            instance.receivePaymentEvents(1);
            fail("AmazonPaymentEventReceivingException expected.");
        } catch (AmazonPaymentEventReceivingException e) {
            //good
        }
    }
}