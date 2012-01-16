/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers.jms;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.payments.amazonfps.TestHelper;
import com.topcoder.payments.amazonfps.model.PaymentEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link JMSAmazonPaymentEventReceiver} class.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class JMSAmazonPaymentEventReceiverTest {
    /**
     * Constant for configuration namespace for accuracy tests.
     */
    private static final String CONFIGURATION = "JMSTest";
    /**
     * Constant for configuration namespace for failure tests.
     * Connection factory name is not specified.
     */
    private static final String FAILURE_CONFIGURATION_1 = "JMSFailure1";
    /**
     * Constant for configuration namespace for failure tests.
     * Queue name is not specified.
     */
    private static final String FAILURE_CONFIGURATION_2 = "JMSFailure2";
    /**
     * Constant for configuration namespace for failure tests.
     * Unknown connection factory JNDI name.
     */
    private static final String FAILURE_CONFIGURATION_3 = "JMSFailure3";
    /**
     * Constant for configuration namespace for failure tests.
     * Unknown queue JNDI name.
     */
    private static final String FAILURE_CONFIGURATION_4 = "JMSFailure4";

    /**
     * Configuration object for accuracy tests.
     */
    private ConfigurationObject configuration;

    /**
     * Connection factory name for testing.
     */
    private String connectionFactoryName;

    /**
     * JMS queue name for testing.
     */
    private String queueName;

    /**
     * The {@code JMSAmazonPaymentEventReceiver} instance used for testing.
     */
    private JMSAmazonPaymentEventReceiver instance;

     /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JMSAmazonPaymentEventReceiverTest.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        configuration = TestHelper.getConfiguration(CONFIGURATION);
        instance = new JMSAmazonPaymentEventReceiver(configuration);

        connectionFactoryName = configuration.getPropertyValue("jmsConnectionFactoryName", String.class);
        queueName = configuration.getPropertyValue("queueName", String.class);
    }

    //-------------------------------------------------------------------------
    // constructor tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code JMSAmazonPaymentEventReceiver} constructor. <br/>
     * Check that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor_JMSAmazonPaymentEventReceiver() {
        String parameter = (String) TestHelper.getField(instance, "jmsConnectionFactoryName");
        assertNotNull("The jmsConnectionFactoryName parameter should not be null", parameter);
        assertFalse("The jmsConnectionFactoryName parameter should not be empty", parameter.trim().isEmpty());

        parameter = (String) TestHelper.getField(instance, "queueName");
        assertNotNull("The queueName parameter should not be null", parameter);
        assertFalse("The queueName parameter should not be empty", parameter.trim().isEmpty());
    }

    /**
     * Failure test for constructor. <br/>
     * Set null configuration.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configureFailure_1() throws Exception {
        new JMSAmazonPaymentEventReceiver(null);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * jmsConnectionFactoryName is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_2() throws Exception {
        new JMSAmazonPaymentEventReceiver(TestHelper.getConfiguration(FAILURE_CONFIGURATION_1));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * queueName is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_3() throws Exception {
        new JMSAmazonPaymentEventReceiver(TestHelper.getConfiguration(FAILURE_CONFIGURATION_2));
    }

    //-------------------------------------------------------------------------
    // receivePaymentEvents tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code receivePaymentEvents} method. <br/>
     * Create fully initialized payment event, send it to the queue, then receive the message with this method
     * and ensure it represents correct data.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_receivePaymentEvents_1() throws Exception {
        ensureJMSQueueIsEmpty();

        PaymentEvent event = TestHelper.createTestPaymentEvent();
        sendMessageToQueue(Helper.convertPaymentEventToXml(event));

        List<PaymentEvent> result = instance.receivePaymentEvents(0L); // wait as long as needed

        assertEquals("There should be only one message", 1, result.size());
        TestHelper.checkEquals(event, result.get(0));
    }

    /**
     * Accuracy test for {@code receivePaymentEvents} method. <br/>
     * Send two event to the queue, then receive the messages with this method and ensure result is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_receivePaymentEvents_2() throws Exception {
        ensureJMSQueueIsEmpty();

        PaymentEvent event = TestHelper.createTestPaymentEvent();
        PaymentEvent event2 = TestHelper.createTestPaymentEvent();

        event.setAuthorizationId(1L);
        event.setErrorMessage("event error message");
        event2.setAuthorizationId(2L);
        event2.setErrorCode("the second event error message");

        sendMessageToQueue(Helper.convertPaymentEventToXml(event));
        sendMessageToQueue(Helper.convertPaymentEventToXml(event2));

        List<PaymentEvent> result = instance.receivePaymentEvents(0L); // wait as long as needed
        // if not all messages are received then wait for them. This never happened in practice during
        // test sessions but the situation when only the first message is ready and
        // the second is processed by the server is very realistically.
        while (result.size() != 2) {
            List<PaymentEvent> result2 = instance.receivePaymentEvents(0L); // wait as long as needed
            result.addAll(result2);
        }

        // message receiving order is not guaranteed to be the same as sending order
        if (result.get(0).getAuthorizationId() != 1L) {
            PaymentEvent temp = result.get(0);
            result.set(0, result.get(1));
            result.set(1, temp);
        }

        TestHelper.checkEquals(event, result.get(0));
        TestHelper.checkEquals(event2, result.get(1));
    }

    /**
     * Accuracy test for {@code receivePaymentEvents} method. <br/>
     * Send two valid events and the third malformed one. We should receive the fist two events despite the third
     * is malformed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_receivePaymentEvents_3() throws Exception {
        ensureJMSQueueIsEmpty();

        PaymentEvent event = TestHelper.createTestPaymentEvent();
        PaymentEvent event2 = TestHelper.createTestPaymentEvent();

        event.setAuthorizationId(1L);
        event.setErrorMessage("event error message");
        event2.setAuthorizationId(2L);
        event2.setErrorCode("the second event error message");

        sendMessageToQueue(Helper.convertPaymentEventToXml(event));
        sendMessageToQueue(Helper.convertPaymentEventToXml(event2));
        sendMessageToQueue("malformed event");

        List<PaymentEvent> result = instance.receivePaymentEvents(0L); // wait as long as needed
        // if not all messages are received then wait for them. This never happened in practice during
        // test sessions but the situation when only the first message is ready and
        // the second is processed by the server is very realistically.
        while (result.size() != 2) {
            List<PaymentEvent> result2 = instance.receivePaymentEvents(0L); // wait as long as needed
            result.addAll(result2);
        }

        // message receiving order is not guaranteed to be the same as sending order
        if (result.get(0).getAuthorizationId() != 1L) {
            PaymentEvent temp = result.get(0);
            result.set(0, result.get(1));
            result.set(1, temp);
        }

        TestHelper.checkEquals(event, result.get(0));
        TestHelper.checkEquals(event2, result.get(1));
    }

    /**
     * Accuracy test for {@code receivePaymentEvents} method. <br/>
     * Do not send any messages. Check the empty list is returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_receivePaymentEvents_4() throws Exception {
        ensureJMSQueueIsEmpty();

        List<PaymentEvent> result = instance.receivePaymentEvents(100L);

        assertNotNull("result should not be null", result);
        assertTrue("result should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for {@code receivePaymentEvents} method. <br/>
     * Send not null but not initialized payment event. There should be no errors (all not null events should be
     * handled correctly).
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_receivePaymentEvents_5() throws Exception {
        ensureJMSQueueIsEmpty();

        sendMessageToQueue(Helper.convertPaymentEventToXml(new PaymentEvent()));

        List<PaymentEvent> result = instance.receivePaymentEvents(0L); // wait as long as needed

        assertEquals("There should be only one message", 1, result.size());
        TestHelper.checkEquals(new PaymentEvent(), result.get(0));
    }

    /**
     * Failure test for {@code receivePaymentEvents} method. <br/>
     * Pass negative timeout.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_receivePaymentEventsFailure_1() throws Exception {
        instance.receivePaymentEvents(-100L);
    }

    /**
     * Failure test for {@code receivePaymentEvents} method. <br/>
     * Send malformed message (not xml).
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonPaymentEventReceivingException.class)
    public void test_receivePaymentEventsFailure_2() throws Exception {
        ensureJMSQueueIsEmpty();

        sendMessageToQueue("it's definitely not an XML");
        instance.receivePaymentEvents(0L); // wait as long as needed
    }

    /**
     * Failure test for {@code receivePaymentEvents} method. <br/>
     * Send malformed message (xml but not payment event).
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonPaymentEventReceivingException.class)
    public void test_receivePaymentEventsFailure_3() throws Exception {
        ensureJMSQueueIsEmpty();

        sendMessageToQueue("<?xml version=\"1.0\"?><message>hello</message>");
        instance.receivePaymentEvents(0L); // wait as long as needed
    }


    /**
     * Failure test for {@code receivePaymentEvents} method. <br/>
     * Specify incorrect connection factory JNDI name in configuration.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonPaymentEventReceivingException.class)
    public void test_receivePaymentEventsFailure_4() throws Exception {
        JMSAmazonPaymentEventReceiver instance2
            = new JMSAmazonPaymentEventReceiver(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));

        instance2.receivePaymentEvents(100L);
    }

    /**
     * Failure test for {@code receivePaymentEvents} method. <br/>
     * Specify incorrect queue JNDI name in configuration.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonPaymentEventReceivingException.class)
    public void test_receivePaymentEventsFailure_5() throws Exception {
        JMSAmazonPaymentEventReceiver instance2
            = new JMSAmazonPaymentEventReceiver(TestHelper.getConfiguration(FAILURE_CONFIGURATION_4));

        instance2.receivePaymentEvents(100L);
    }

    /**
     * Sends message to the queue.
     *
     * @param message
     *              the message to send
     *
     * @throws NamingException
     *              if naming exception is encountered
     * @throws JMSException
     *              if some JMS error occurred
     */
    private void sendMessageToQueue(String message) throws NamingException, JMSException {
        QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) JNDIUtils.getObject(connectionFactoryName);
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;
        QueueSender queueSender = null;

        try {
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueConnection.start();
            Queue queue = (Queue) JNDIUtils.getObject(queueName);
            queueSender = queueSession.createSender(queue);
            TextMessage txtMessage = queueSession.createTextMessage();
            txtMessage.setText(message);
            queueSender.send(txtMessage);
        } finally {
            if (queueSender != null) {
                queueSender.close();
            }
            if (queueSession != null) {
                queueSession.close();
            }
            if (queueConnection != null) {
                queueConnection.close();
            }
        }
    }

    /**
     * Simplifies TestHelper.ensureJMSQueueIsEmpty helper method call by providing 2 name parameters.
     *
     * Just ensures that JMS queue is empty to guarantee successful tests execution.
     * It can be not-empty for various reasons, for example, fail of the previous test
     * or the queue was modified manually from the admin console.
     *
     * @throws NamingException
     *              if naming exception is encountered
     * @throws JMSException
     *              if some JMS error occurred
     */
    private void ensureJMSQueueIsEmpty() throws NamingException, JMSException {
        TestHelper.ensureJMSQueueIsEmpty(connectionFactoryName, queueName);
    }
}
