/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers.jms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * The {@code JMSAmazonPaymentEventReceiver} class serves as a receiver of payment events sent via a queue with use
 * of JMS. This class should be used in conjunction with {@code JMSAmazonPaymentEventSubscriber} when payment is
 * initiated by one system, and payment event should be handled by another system.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is immutable and thread safe. But in most cases it doesn't very
 * helpful to perform multiple read operations on the same queue from multiple threads.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class JMSAmazonPaymentEventReceiver {
    /**
     * Represents the class name.
     */
    private static final String CLASS_NAME = JMSAmazonPaymentEventSubscriber.class.getName();

    /**
     * Constant for jmsConnectionFactoryName parameter name.
     */
    private static final String JMS_CONNECTION_FACTORY_NAME_PARAMETER = "jmsConnectionFactoryName";

    /**
     * Constant for queueName parameter name.
     */
    private static final String QUEUE_NAME_PARAMETER = "queueName";

    /**
     * The JNDI name of the JMS connection factory to be used by this class. Is initialized in the constructor and
     * never changed after that. Cannot be {@code null}/empty after initialization. Is used in receivePaymentEvents().
     */
    private final String jmsConnectionFactoryName;

    /**
     * The JNDI name of the queue to be used by this class. Is initialized in the constructor and never changed after
     * that. Cannot be {@code null}/empty after initialization. Is used in receivePaymentEvents().
     */
    private final String queueName;

    /**
     * The logger used for logging errors and debug information. Is initialized in the constructor and never changed
     * after that. If is {@code null}, logging is not performed. Is used in receivePaymentEvents().
     */
    private final Log log;

    /**
     * Creates an instance of {@code JMSAmazonPaymentEventReceiver} using the given configuration.
     *
     * @param configuration
     *            the configuration of this class
     *
     * @throws IllegalArgumentException
     *            if configuration is {@code null}
     * @throws com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if some error occurred when initializing an instance using the given configuration.
     */
    public JMSAmazonPaymentEventReceiver(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        log = Helper.getLog(configuration);

        // retrieve JMS connection factory name
        jmsConnectionFactoryName = Helper.getProperty(configuration, JMS_CONNECTION_FACTORY_NAME_PARAMETER, true);

        // retrieve queue name
        queueName = Helper.getProperty(configuration, QUEUE_NAME_PARAMETER, true);
    }

    /**
     * <p>
     * Receives all available payment events from the queue. Returns an empty list if no events are available.
     * </p>
     *
     * <p>
     * NOTE: According to design all runtime exceptions should also be caught and wrapped with
     * {@code AmazonPaymentEventReceivingException} exception.
     * </p>
     *
     * @param timeout
     *            the timeout in milliseconds
     *
     * @return the list of obtained payment events (not {@code null})
     *
     * @throws IllegalArgumentException
     *            if {@literal timeout < 0}
     * @throws AmazonPaymentEventReceivingException
     *            if any other error occurred
     */
    public List<PaymentEvent> receivePaymentEvents(long timeout) throws AmazonPaymentEventReceivingException {
        Date entranceTimestamp = new Date();
        final String signature = CLASS_NAME + ".receivePaymentEvents(long timeout)";

        // 1. log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(log, signature, new String[] {"timeout"}, new Object[] {timeout});

        try {
            ParameterCheckUtility.checkNotNegative(timeout, "timeout");
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }

        // 2. read messages from the queue, take care of exceptions
        try {
            QueueConnectionFactory queueConnectionFactory =
                    (QueueConnectionFactory) JNDIUtils.getObject(jmsConnectionFactoryName);

            QueueConnection queueConnection = null;
            QueueSession queueSession = null;
            QueueReceiver queueReceiver = null;
            try {
                queueConnection = queueConnectionFactory.createQueueConnection();
                queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                queueConnection.start();
                Queue queue = (Queue) JNDIUtils.getObject(queueName);
                queueReceiver = queueSession.createReceiver(queue);

                List<PaymentEvent> result = getMessagesFromReceiver(queueReceiver, timeout, signature);

                LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(result)},
                        entranceTimestamp);
                return result;
            } finally {
                if (queueReceiver != null) {
                    queueReceiver.close();
                }
                if (queueSession != null) {
                    queueSession.close();
                }
                if (queueConnection != null) {
                    queueConnection.close();
                }
            }
        } catch (NamingException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonPaymentEventReceivingException(
                    "Failed to retrieve object from JNDI", e), true, Level.ERROR);
        } catch (JMSException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonPaymentEventReceivingException(
                    "JMS error occurred", e), true, Level.ERROR);
        } catch (JAXBException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonPaymentEventReceivingException(
                    "Failed to convert xml to payment event", e), true, Level.ERROR);
        } catch (SAXException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonPaymentEventReceivingException(
                    "Failed to parse validation schema", e), true, Level.ERROR);
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonPaymentEventReceivingException(
                    "Unexpected runtime error", e), true, Level.ERROR);
        }
    }

    /**
     * <p>
     * Retrieves messages from the queue and converts them to payment events instances.
     * </p>
     *
     * <p>
     * According to design <b>all</b> runtime exceptions should be caught and processed in addition to the checked
     * exceptions. If during messages processing we'll get an exception then:
     * a) if result is empty then re-throw the exception (it will be wrapped  and logged later).
     * b) else log exception, ignore it and break the loop (we still need to return the previously
     * retrieved messages).
     * </p>
     *
     * @param queueReceiver
     *              the queue receiver to get messages from the queue (not {@code null})
     * @param timeout
     *              the timeout to wait for the first message if the queue is empty
     * @param signature
     *              the signature of the public business method caller (not {@code null})
     *
     * @return list of payment events
     *
     * @throws JMSException
     *              if some JMS error occurred
     * @throws SAXException
     *              if failed to parse schema for payment event xml validation
     * @throws JAXBException
     *              if some JAXB error occurred (including xml validation failure)
     */
    private List<PaymentEvent> getMessagesFromReceiver(QueueReceiver queueReceiver, long timeout, String signature)
        throws JMSException, SAXException, JAXBException {
        List<PaymentEvent> result = new ArrayList<PaymentEvent>();

        TextMessage txtMessage = (TextMessage) queueReceiver.receive(timeout);
        try {
            while (txtMessage != null) {
                String xml = txtMessage.getText();
                result.add(Helper.getPaymentEventFromXml(xml));
                txtMessage = (TextMessage) queueReceiver.receiveNoWait();
            }
        } catch (JMSException e) {
            if (result.isEmpty()) {
                throw e;
            } else { // just log and ignore
                LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
            }
        } catch (JAXBException e) {
            if (result.isEmpty()) {
                throw e;
            } else { // just log and ignore
                LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
            }
        } catch (RuntimeException e) {
            if (result.isEmpty()) {
                throw e;
            } else { // just log and ignore
                LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
            }
        }
        return result;
    }
}
