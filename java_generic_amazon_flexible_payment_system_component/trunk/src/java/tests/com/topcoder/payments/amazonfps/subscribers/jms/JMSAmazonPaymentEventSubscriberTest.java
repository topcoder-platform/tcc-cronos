/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers.jms;

import java.util.List;

import javax.jms.JMSException;
import javax.naming.NamingException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.payments.amazonfps.TestHelper;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.model.PaymentEventType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <p>
 * Unit tests for {@link JMSAmazonPaymentEventSubscriber} class.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class JMSAmazonPaymentEventSubscriberTest {
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
     * This configuration defines unknown connection factory JNDI name.
     */
    private static final String FAILURE_CONFIGURATION_3 = "JMSFailure3";
    /**
     * This configuration defines unknown queue JNDI name.
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
     * The {@code JMSAmazonPaymentEventSubscriber} instance used for testing.
     */
    private JMSAmazonPaymentEventSubscriber instance;

     /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JMSAmazonPaymentEventSubscriberTest.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new JMSAmazonPaymentEventSubscriber();
        configuration = TestHelper.getConfiguration(CONFIGURATION);

        connectionFactoryName = configuration.getPropertyValue("jmsConnectionFactoryName", String.class);
        queueName = configuration.getPropertyValue("queueName", String.class);
    }

    /**
     * Accuracy test for {@code JMSAmazonPaymentEventSubscriber} constructor. <br/>
     * Check that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor_JMSAmazonPaymentEventSubscriberTest() {
        assertNull("The log should be null",
                TestHelper.getField(instance, "log"));
        assertNull("The jmsConnectionFactoryName should be null",
                TestHelper.getField(instance, "jmsConnectionFactoryName"));
        assertNull("The queueName should be null",
                TestHelper.getField(instance, "queueName"));
    }

    //-------------------------------------------------------------------------
    // configure() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code configure} method. <br/>
     * Check that fields are initialized according to configuration.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_configure_1() throws Exception {
        instance.configure(configuration);

        String parameter = (String) TestHelper.getField(instance, "jmsConnectionFactoryName");
        assertNotNull("The jmsConnectionFactoryName parameter should not be null", parameter);
        assertFalse("The jmsConnectionFactoryName parameter should not be empty", parameter.trim().isEmpty());

        parameter = (String) TestHelper.getField(instance, "queueName");
        assertNotNull("The queueName parameter should not be null", parameter);
        assertFalse("The queueName parameter should not be empty", parameter.trim().isEmpty());
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Pass null configuration.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configureFailure_1() throws Exception {
        instance.configure(null);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * jmsConnectionFactoryName parameter is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_1));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * queueName parameter is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_2));
    }

    //-------------------------------------------------------------------------
    // processPaymentEvent() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code processPaymentEvent} method. <br/>
     * Create fully initialized payment event, process it, then retrieve the content of the
     * message queue and ensure it represents correct data.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPaymentEvent_1() throws Exception {
        instance.configure(configuration);
        PaymentEvent event = TestHelper.createTestPaymentEvent();
        ensureJMSQueueIsEmpty();

        instance.processPaymentEvent(event);

        List<String> messages = getMessagesFromQueue(1);

        PaymentEvent result = Helper.getPaymentEventFromXml(messages.get(0));
        TestHelper.checkEquals(event, result);
    }

    /**
     * Accuracy test for {@code processPaymentEvent} method. <br/>
     * Send two payment events and then check that received events are correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPaymentEvent_2() throws Exception {
        instance.configure(configuration);
        PaymentEvent event = TestHelper.createTestPaymentEvent();
        PaymentEvent event2 = TestHelper.createTestPaymentEvent();

        event.setEventType(PaymentEventType.AUTHORIZATION_SUCCESS);
        event.setAuthorizationId(10L);
        event.getPaymentDetails().setParameters(null);
        event.setErrorCode(null);
        event.setErrorType(null);
        event.setErrorMessage(null);

        event2.setEventType(PaymentEventType.AUTHORIZATION_FAILURE);
        event2.setAuthorizationId(20L);
        event2.setPaymentDetails(null);
        event2.setErrorCode("authError");

        ensureJMSQueueIsEmpty();

        instance.processPaymentEvent(event);
        instance.processPaymentEvent(event2);

        List<String> messages = getMessagesFromQueue(2);

        PaymentEvent result = Helper.getPaymentEventFromXml(messages.get(0));
        PaymentEvent result2 = Helper.getPaymentEventFromXml(messages.get(1));

        // message receiving order is not guaranteed to be the same as sending order
        if (result.getEventType() != PaymentEventType.AUTHORIZATION_SUCCESS) {
            PaymentEvent temp = result;
            result = result2;
            result2 = temp;
        }

        TestHelper.checkEquals(event, result);
        TestHelper.checkEquals(event2, result2);
    }

    /**
     * Accuracy test for {@code processPaymentEvent} method. <br/>
     * Put null key in the parameters map. There should be no errors (all not null events should be
     * handled correctly).
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPaymentEvent_3() throws Exception {
        instance.configure(configuration);
        PaymentEvent event = TestHelper.createTestPaymentEvent();
        event.getPaymentDetails().getParameters().put(null, "value");
        ensureJMSQueueIsEmpty();

        instance.processPaymentEvent(event);

        List<String> messages = getMessagesFromQueue(1);

        PaymentEvent result = Helper.getPaymentEventFromXml(messages.get(0));
        TestHelper.checkEquals(event, result);
    }

    /**
     * Accuracy test for {@code processPaymentEvent} method. <br/>
     * Process not null but not initialized payment event. There should be no errors (all not null events should be
     * handled correctly).
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPaymentEvent_4() throws Exception {
        instance.configure(configuration);
        ensureJMSQueueIsEmpty();

        instance.processPaymentEvent(new PaymentEvent());

        List<String> messages = getMessagesFromQueue(1);

        PaymentEvent result = Helper.getPaymentEventFromXml(messages.get(0));
        TestHelper.checkEquals(new PaymentEvent(), result);
    }

    /**
     * Accuracy test for {@code processPaymentEvent} method. <br/>
     * Specify incorrect connection factory JNDI name in configuration. There should be no exceptions
     * since the method should catch all of them.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPaymentEvent_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
        // Just ensure that there is no exception for bad configuration
        instance.processPaymentEvent(new PaymentEvent());
    }

    /**
     * Accuracy test for {@code processPaymentEvent} method. <br/>
     * Specify incorrect queue JNDI name in configuration. There should be no exceptions
     * since the method should catch all of them.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPaymentEvent_6() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_4));
        // Just ensure that there is no exception for bad configuration
        instance.processPaymentEvent(new PaymentEvent());
    }

    /**
     * Failure test for {@code processPaymentEvent} method. <br/>
     * Pass null event.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_processPaymentEventFailure_1() throws Exception {
        instance.processPaymentEvent(null);
    }

    /**
     * Failure test for {@code processPaymentEvent} method. <br/>
     * Instance was not initialized with {@code configure} method.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_processPaymentEventFailure_2() throws Exception {
        PaymentEvent event = TestHelper.createTestPaymentEvent();
        instance.processPaymentEvent(event);
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

    /**
     * Simplifies TestHelper.getMessagesFromQueue helper method call by providing 2 name parameters.
     *
     * Gets messages from the queue. When expected message count is not zero this method waits till all messages
     * will be received. If this parameter is zero the method retrieves all available messages and returns.
     *
     * @param expectedMessagesCount
     *              how many messages are expected to receive. 0 if unknown number of messages are expected.
     *
     * @return the list of payment events
     *
     * @throws NamingException
     *              if naming exception is encountered
     * @throws JMSException
     *              if some JMS error occurred
     */
    public List<String> getMessagesFromQueue(int expectedMessagesCount) throws NamingException, JMSException {
        return TestHelper.getMessagesFromQueue(connectionFactoryName, queueName, expectedMessagesCount);
    }
}
