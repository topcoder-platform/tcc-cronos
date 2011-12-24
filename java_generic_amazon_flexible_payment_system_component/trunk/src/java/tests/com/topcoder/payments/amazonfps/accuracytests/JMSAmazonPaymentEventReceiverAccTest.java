/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventReceiver;

/**
 * <p>
 * Accuracy tests for class <code>JMSAmazonPaymentEventReceiver</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class JMSAmazonPaymentEventReceiverAccTest extends BaseAccTest {

    /**
     * Represents the <code>JMSAmazonPaymentEventReceiver</code> instance used to test against.
     */
    private JMSAmazonPaymentEventReceiver impl;

    /**
     * Connection factory name for testing.
     */
    private String connectionFactoryName;

    /**
     * JMS queue name for testing.
     */
    private String queueName;

    /**
     * Delay between successive operations on the same payment. When issuing several payment
     * operations in order on the same payment Amazon can refuse one of these operations since the
     * previous one has not finished and some resource is locked. To have some guarantee that tests
     * will run successfully when need this delay constant. This should be increased if for test
     * session failures in payment operations take place. The delay is used only in few places, so
     * in general it takes not too much time.
     */
    private static final long PAYMENT_OPERATION_DELAY = 5000;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(JMSAmazonPaymentEventReceiverAccTest.class);
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
        impl = new JMSAmazonPaymentEventReceiver(configuration);
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
        connectionFactoryName = null;
        queueName = null;
    }

    /**
     * Accuracy test for the constructor <code>JMSAmazonPaymentEventReceiver()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>receivePaymentEvents(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testReceivePaymentEvents() throws Exception {
        getMessagesFromQueue(connectionFactoryName, queueName, 0);
        Thread.sleep(PAYMENT_OPERATION_DELAY);

        PaymentEvent event = createTestPaymentEvent();

        sendMessageToQueue(connectionFactoryName, queueName, convertPaymentEventToXml(event));

        List<PaymentEvent> res = impl.receivePaymentEvents(PAYMENT_OPERATION_DELAY);

        assertEquals("'receivePaymentEvents' should be correct.", 1, res.size());
        assertEquals("'receivePaymentEvents' should be correct.", "error code", res.get(0)
            .getErrorCode());
        assertEquals("'receivePaymentEvents' should be correct.", "error type", res.get(0)
            .getErrorType());
        assertEquals("'receivePaymentEvents' should be correct.", "error message", res.get(0)
            .getErrorMessage());
        assertEquals("'receivePaymentEvents' should be correct.", 5L, res.get(0)
            .getAuthorizationId());
        assertEquals("'receivePaymentEvents' should be correct.", 8L, res.get(0).getPaymentId());
        assertEquals("'receivePaymentEvents' should be correct.", 25, res.get(0)
            .getPaymentDetails().getAmount().longValue());
    }

    /**
     * Accuracy test for the method <code>receivePaymentEvents(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testReceivePaymentEvents2() throws Exception {
        getMessagesFromQueue(connectionFactoryName, queueName, 0);
        Thread.sleep(PAYMENT_OPERATION_DELAY);

        PaymentEvent event = createTestPaymentEvent();
        PaymentEvent event2 = createTestPaymentEvent();
        event.setAuthorizationId(1L);
        event.setErrorMessage("error message 1");
        event2.setAuthorizationId(2L);
        event2.setErrorMessage("error message 2");

        sendMessageToQueue(connectionFactoryName, queueName, convertPaymentEventToXml(event));
        sendMessageToQueue(connectionFactoryName, queueName, convertPaymentEventToXml(event2));

        List<PaymentEvent> res = impl.receivePaymentEvents(PAYMENT_OPERATION_DELAY);

        assertEquals("'receivePaymentEvents' should be correct.", 2, res.size());
        for (PaymentEvent pe : res) {
            if (pe.getAuthorizationId() == 1L) {
                assertEquals("'receivePaymentEvents' should be correct.", "error message 1",
                    pe.getErrorMessage());
            } else if (pe.getAuthorizationId() == 2L) {
                assertEquals("'receivePaymentEvents' should be correct.", "error message 2",
                    pe.getErrorMessage());
            }
        }
    }

    /**
     * Accuracy test for the method <code>receivePaymentEvents(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testReceivePaymentEvents3() throws Exception {
        getMessagesFromQueue(connectionFactoryName, queueName, 0);
        Thread.sleep(PAYMENT_OPERATION_DELAY);

        PaymentEvent event = createTestPaymentEvent();
        PaymentEvent event2 = createTestPaymentEvent();
        event.setAuthorizationId(1L);
        event.setErrorMessage("error message 1");
        event2.setAuthorizationId(2L);
        event2.setErrorMessage("error message 2");

        sendMessageToQueue(connectionFactoryName, queueName, convertPaymentEventToXml(event));
        sendMessageToQueue(connectionFactoryName, queueName, convertPaymentEventToXml(event2));
        sendMessageToQueue(connectionFactoryName, queueName, "invalid");

        List<PaymentEvent> res = impl.receivePaymentEvents(PAYMENT_OPERATION_DELAY);

        assertEquals("'receivePaymentEvents' should be correct.", 2, res.size());
        for (PaymentEvent pe : res) {
            if (pe.getAuthorizationId() == 1L) {
                assertEquals("'receivePaymentEvents' should be correct.", "error message 1",
                    pe.getErrorMessage());
            } else if (pe.getAuthorizationId() == 2L) {
                assertEquals("'receivePaymentEvents' should be correct.", "error message 2",
                    pe.getErrorMessage());
            }
        }
    }

    /**
     * Accuracy test for the method <code>receivePaymentEvents(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testReceivePaymentEvents4() throws Exception {
        getMessagesFromQueue(connectionFactoryName, queueName, 0);
        Thread.sleep(PAYMENT_OPERATION_DELAY);

        List<PaymentEvent> res = impl.receivePaymentEvents(PAYMENT_OPERATION_DELAY);

        assertEquals("'receivePaymentEvents' should be correct.", 0, res.size());
    }

}
