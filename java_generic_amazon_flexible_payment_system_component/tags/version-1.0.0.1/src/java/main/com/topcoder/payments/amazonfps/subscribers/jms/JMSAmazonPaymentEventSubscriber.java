/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers.jms;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.subscribers.ConfigurablePaymentEventSubscriber;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * The {@code JMSAmazonPaymentEventSubscriber} class is an implementation of {@code ConfigurablePaymentEventSubscriber}
 * that simply sends all obtained payment events to a configured queue with use of JMS. This class uses
 * <i>Logging Wrapper</i> component to log errors and debug information.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is mutable, but thread safe when {@code configure()} method is called
 * just once right after instantiation and entities passed to it are used by the caller in thread safe manner.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class JMSAmazonPaymentEventSubscriber implements ConfigurablePaymentEventSubscriber {
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
     * The JNDI name of the JMS connection factory to be used by this class. It is initialized in the configure()
     * method and never changed after that (assuming that configure() is called just once right after construction).
     * Cannot be null/empty after initialization. It is used in processPaymentEvent().
     */
    private String jmsConnectionFactoryName;

    /**
     * The JNDI name of the queue to be used by this class. It is initialized in the configure() method and never
     * changed after that (assuming that configure() is called just once right after construction). Cannot be
     * null/empty after initialization. It is used in processPaymentEvent().
     */
    private String queueName;

    /**
     * The logger used for logging errors and debug information. It is initialized in the configure() method and never
     * changed after that (assuming that configure() is called just once right after construction). If it is
     * null after initialization, logging is not performed. It is used in processPaymentEvent().
     */
    private Log log;

    /**
     * Constructs new {@code JMSAmazonPaymentEventSubscriber} instance.
     */
    public JMSAmazonPaymentEventSubscriber() {
        // Empty
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *            if configuration is {@code null}
     * @throws com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if some error occurred when initializing an instance using the given configuration.
     */
    @Override
    public void configure(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        log = Helper.getLog(configuration);

        // retrieve JMS connection factory name
        jmsConnectionFactoryName = Helper.getProperty(configuration, JMS_CONNECTION_FACTORY_NAME_PARAMETER, true);

        // retrieve queue name
        queueName = Helper.getProperty(configuration, QUEUE_NAME_PARAMETER, true);
    }

    /**
     * <p>
     * Processes the given payment event.
     * </p>
     *
     * <p>
     * NOTE: this method doesn't throw any exceptions (both checked and runtime) except
     * {@code IllegalArgumentException} and {@code IllegalStateException}. All caught exceptions except the
     * above mentioned are logged at ERROR level and ignored.
     * </p>
     *
     * @param paymentEvent
     *            the payment event to be processed
     *
     * @throws IllegalArgumentException
     *            if paymentEvent is {@code null}
     * @throws IllegalStateException
     *            if subscriber instance was not initialized properly with {@code configure} method
     */
    @Override
    public void processPaymentEvent(PaymentEvent paymentEvent) {
        Date entranceTimestamp = new Date();
        final String signature = CLASS_NAME + ".processPaymentEvent(PaymentEvent paymentEvent)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"paymentEvent"},
                new Object[] {paymentEvent == null ? "null" : paymentEvent.toString()});

        try {
            ParameterCheckUtility.checkNotNull(paymentEvent, "paymentEvent");
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }

        if (queueName == null) {
            throw LoggingWrapperUtility.logException(log, signature, new IllegalStateException(
                    "The subscriber instance was not initialized with configure() method"), true, Level.ERROR);
        }

        try {
            String paymentEventXml = Helper.convertPaymentEventToXml(paymentEvent);

            QueueConnectionFactory queueConnectionFactory =
                    (QueueConnectionFactory) JNDIUtils.getObject(jmsConnectionFactoryName);

            QueueConnection queueConnection = null;
            QueueSession queueSession = null;
            QueueSender queueSender = null;

            try {
                queueConnection = queueConnectionFactory.createQueueConnection();
                queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                queueConnection.start();
                Queue queue = (Queue) JNDIUtils.getObject(queueName);
                queueSender = queueSession.createSender(queue);
                queueSender.send(queueSession.createTextMessage(paymentEventXml));
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
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (JAXBException e) {
            LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (NamingException e) {
            LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (JMSException e) {
            LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (RuntimeException e) { // we should ensure that all runtime exceptions are caught here (by design)
            LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }
    }
}
