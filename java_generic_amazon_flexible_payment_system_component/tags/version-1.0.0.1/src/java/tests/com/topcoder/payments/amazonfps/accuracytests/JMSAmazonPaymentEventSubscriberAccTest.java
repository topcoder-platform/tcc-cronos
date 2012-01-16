/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.subscribers.ConfigurablePaymentEventSubscriber;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventSubscriber;

/**
 * <p>
 * Accuracy tests for class <code>JMSAmazonPaymentEventSubscriber</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class JMSAmazonPaymentEventSubscriberAccTest extends BaseAccTest {

    /**
     * Represents the <code>JMSAmazonPaymentEventSubscriber</code> instance used to test against.
     */
    private JMSAmazonPaymentEventSubscriber impl;

    /**
     * Connection factory name for testing.
     */
    private String connectionFactoryName;

    /**
     * JMS queue name for testing.
     */
    private String queueName;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(JMSAmazonPaymentEventSubscriberAccTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        connectionFactoryName = "myJMSConnectionFactory";
        queueName = "paymentEventQueue";
        ConfigurationObject configuration = new DefaultConfigurationObject(
            "JMSAmazonPaymentEventReceiver");
        configuration.setPropertyValue("loggerName", "myLogger");
        configuration.setPropertyValue("jmsConnectionFactoryName", connectionFactoryName);
        configuration.setPropertyValue("queueName", queueName);

        impl = new JMSAmazonPaymentEventSubscriber();
        impl.configure(configuration);
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>JMSAmazonPaymentEventSubscriber</code> subclasses should be
     * correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            impl instanceof ConfigurablePaymentEventSubscriber);
    }

    /**
     * Accuracy test for the constructor <code>JMSAmazonPaymentEventSubscriber()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>processPaymentEvent(PaymentEvent)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testProcessPaymentEvent() throws Exception {
        getMessagesFromQueue(connectionFactoryName, queueName, 0);

        PaymentEvent event = createTestPaymentEvent();

        impl.processPaymentEvent(event);

        List<String> res = getMessagesFromQueue(connectionFactoryName, queueName, 1);

        assertEquals("'processPaymentEvent' should be correct.", 1, res.size());
        PaymentEvent pe = getPaymentEventFromXml(res.get(0));
        assertEquals("'processPaymentEvent' should be correct.", "error code", pe.getErrorCode());
        assertEquals("'processPaymentEvent' should be correct.", "error type", pe.getErrorType());
        assertEquals("'processPaymentEvent' should be correct.", "error message",
            pe.getErrorMessage());
        assertEquals("'processPaymentEvent' should be correct.", 5L, pe.getAuthorizationId());
        assertEquals("'processPaymentEvent' should be correct.", 8L, pe.getPaymentId());
        assertEquals("'processPaymentEvent' should be correct.", 25, pe.getPaymentDetails()
            .getAmount().longValue());
    }

    /**
     * Accuracy test for the method <code>processPaymentEvent(PaymentEvent)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testProcessPaymentEvent2() throws Exception {
        getMessagesFromQueue(connectionFactoryName, queueName, 0);

        PaymentEvent event1 = createTestPaymentEvent();
        PaymentEvent event2 = createTestPaymentEvent();
        event1.setAuthorizationId(1L);
        event1.setErrorMessage("error message 1");
        event2.setAuthorizationId(2L);
        event2.setErrorMessage("error message 2");

        impl.processPaymentEvent(event1);
        impl.processPaymentEvent(event2);

        List<String> res = getMessagesFromQueue(connectionFactoryName, queueName, 2);

        assertEquals("'processPaymentEvent' should be correct.", 2, res.size());
        PaymentEvent pe1 = getPaymentEventFromXml(res.get(0));
        PaymentEvent pe2 = getPaymentEventFromXml(res.get(1));
        if (pe1.getAuthorizationId() == 1L) {
            assertEquals("'processPaymentEvent' should be correct.", "error message 1",
                pe1.getErrorMessage());
            assertEquals("'processPaymentEvent' should be correct.", 2L, pe2.getAuthorizationId());
            assertEquals("'processPaymentEvent' should be correct.", "error message 2",
                pe2.getErrorMessage());
        } else if (pe1.getAuthorizationId() == 2L) {
            assertEquals("'processPaymentEvent' should be correct.", "error message 2",
                pe1.getErrorMessage());
            assertEquals("'processPaymentEvent' should be correct.", 1L, pe2.getAuthorizationId());
            assertEquals("'processPaymentEvent' should be correct.", "error message 1",
                pe2.getErrorMessage());
        }
    }

}
