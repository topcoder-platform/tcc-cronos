/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.payments.amazonfps.model.PaymentDetails;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.model.PaymentEventType;

/**
 * The {@code TestHelper} class provides static methods used to facilitate component testing.
 *
 * @author KennyAlive
 * @version 1.0
 */
public class TestHelper {
    /**
     * Represents the line separator.
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Constant for configuration filename for testing.
     */
    private static final String CONFIGURATION_FILENAME = "test.properties";

    /**
     * Do not allow this class to be instantiated.
     */
    private TestHelper() {
        // Empty
    }

    /**
     * Helper method for getting the configuration object from the specified namespace.
     *
     * @param namespace
     *          the configuration namespace
     *
     * @return the configuration object for the given namespace
     *
     * @throws IOException
     *              if some IO error occurred
     * @throws ConfigurationPersistenceException
     *              if some error occurred in configuration persistence component
     * @throws ConfigurationAccessException
     *              if failed to access configuration data
     */
    public static ConfigurationObject getConfiguration(String namespace)
        throws IOException, ConfigurationPersistenceException, ConfigurationAccessException {
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(CONFIGURATION_FILENAME);
        ConfigurationObject configuration = configurationFileManager.getConfiguration(namespace);
        return configuration.getChild(namespace);
    }

    /**
     * Just ensures that JMS queue is empty to guarantee successful tests execution.
     * It can be not-empty for various reasons, for example, fail of the previous test
     * or the queue was modified manually from the admin console. <br/>
     *
     * This method is used by both subscriber and receiver test classes.
     *
     * @param connFactoryName
     *              the JNDI name of the connection factory
     * @param queueName
     *              the JNDI name of the queue
     *
     * @throws NamingException
     *              if naming exception is encountered
     * @throws JMSException
     *              if some JMS error occurred
     */
    public static void ensureJMSQueueIsEmpty(String connFactoryName, String queueName)
        throws NamingException, JMSException {
        getMessagesFromQueue(connFactoryName, queueName, 0);
    }

    /**
     * Gets messages from the queue. When expected message count is not zero this method waits till all messages
     * will be received. If this parameter is zero the method retrieves all available messages and returns. <br/>
     *
     * This method is used by subscriber test class and also indirectly by receiver test class which calls
     * {@code ensureJMSQueueIsEmpty} method.
     *
     * @param connFactoryName
     *              the JNDI name of the connection factory
     * @param queueName
     *              the JNDI name of the queue
     * @param expectedMessagesCount
     *              how many messages are expected to receive. 0 if unknown number of messages are expected
     *
     * @return the list of payment events
     *
     * @throws NamingException
     *              if naming exception is encountered
     * @throws JMSException
     *              if some JMS error occurred
     */
    public static List<String> getMessagesFromQueue(String connFactoryName, String queueName,
        int expectedMessagesCount) throws NamingException, JMSException {
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) JNDIUtils.getObject(connFactoryName);
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;
        QueueReceiver queueReceiver = null;
        try {
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueConnection.start();
            Queue queue = (Queue) JNDIUtils.getObject(queueName);
            queueReceiver = queueSession.createReceiver(queue);

            List<String> result = new ArrayList<String>();
            if (expectedMessagesCount == 0) {
                TextMessage txtMessage = (TextMessage) queueReceiver.receive(100L);
                while (txtMessage != null) {
                    result.add(txtMessage.getText());
                    txtMessage = (TextMessage) queueReceiver.receive(100L);
                }
            } else {
                for (int i = 0; i < expectedMessagesCount; i++) {
                    TextMessage txtMessage = (TextMessage) queueReceiver.receive(0L); // wait for the message
                    result.add(txtMessage.getText());
                }
                assertNull("there should not be any messages in the queue", queueReceiver.receive(1000L));
            }
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
    }

    /**
     * Creates {@code PaymentEvent} instance with all fields initialized.
     * This methods is used for testing both event subscriber and event receiver classes.
     *
     * @return the {@code PaymentEvent} instance
     */
    public static PaymentEvent createTestPaymentEvent() {
        PaymentEventType type = PaymentEventType.PAYMENT_SUCCESS;
        BigDecimal amount = BigDecimal.valueOf(25);
        long authorizationId = 5L;
        long paymentId = 8L;
        String errorCode = "error code";
        String errorType = "error type";
        String errorMessage = "error message";

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setParameters(parameters);
        paymentDetails.setAmount(amount);
        paymentDetails.setReservation(true);

        PaymentEvent event = new PaymentEvent();
        event.setEventType(type);
        event.setPaymentDetails(paymentDetails);
        event.setAuthorizationId(authorizationId);
        event.setPaymentId(paymentId);
        event.setErrorCode(errorCode);
        event.setErrorType(errorType);
        event.setErrorMessage(errorMessage);
        return event;
    }

    /**
     * Checks using JUnit asserts that both events are equals.
     *
     * @param expected
     *              the expected payment event instance
     * @param event
     *              the payment event instance to check for equality
     */
    public static void checkEquals(PaymentEvent expected, PaymentEvent event) {
        // check event type
        assertEquals("type should be correct", expected.getEventType(), event.getEventType());

        // check payment details
        PaymentDetails expectedPaymentDetails = expected.getPaymentDetails();
        PaymentDetails paymentDetails = event.getPaymentDetails();
        if (expectedPaymentDetails != null) {
            assertNotNull("payment details should not be null", paymentDetails);

            // Map implementations provide correct equals method.
            assertEquals("parameters should be the same",
                    expectedPaymentDetails.getParameters(), paymentDetails.getParameters());

            assertEquals("payment details amount should be correct",
                        expectedPaymentDetails.getAmount(), paymentDetails.getAmount());

            assertEquals("reservation should be correct",
                    expectedPaymentDetails.isReservation(), paymentDetails.isReservation());
        } else {
            assertNull("payment details should be null", paymentDetails);
        }

        // check authorization id and payment id
        assertEquals("authorization id should be correct", expected.getAuthorizationId(), event.getAuthorizationId());
        assertEquals("payment id should be correct", expected.getPaymentId(), event.getPaymentId());

        // check error-related fields
        assertEquals("error code should be correct", expected.getErrorCode(), event.getErrorCode());
        assertEquals("error type should be correct", expected.getErrorType(), event.getErrorType());
        assertEquals("error message should be correct", expected.getErrorMessage(), event.getErrorMessage());
    }

    /**
     * Loads properties from the specified file on the classpath.
     *
     * @param fileName
     *              the properties file name
     *
     * @return the properties object
     *
     * @throws IOException
     *              if IO error occurs while working with properties file
     */
    public static Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        URL url = ClassLoader.getSystemResource(fileName);
        InputStream inputStream = url.openStream();
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }

    /**
     * Returns the value of the field with the given name in the given object. The method
     * has access to all the fields, despite theirs access level. This is achieved through reflection.
     *
     * @param object
     *              the object that holds the field
     * @param fieldName
     *              the field's name
     *
     * @return the value of the field or null if the field was not found
     */
    public static Object getField(Object object, String fieldName) {
        return getField(object, fieldName, false);
    }

    /**
     * Returns the value of the field with the given name in the super type object. The method
     * has access to all the fields, despite theirs access level. This is achieved through reflection.
     *
     * @param object
     *              the object that holds the field
     * @param fieldName
     *              the field's name
     *
     * @return the value of the field or null if the field was not found
     */
    public static Object getSuperField(Object object, String fieldName) {
        return getField(object, fieldName, true);
    }

    /**
     * Sets the value of the field with the given name in the given object. The method has access
     * to all the fields, despite theirs access level. This is achieved through reflection.
     *
     * @param object
     *              the object that holds the field
     * @param fieldName
     *              the field's name
     * @param value
     *              the field's value
     */
    public static void setField(Object object, String fieldName, Object value) {
        setField(object, fieldName, value, false);
    }

    /**
     * Helper method for retrieving object fields via reflection.
     *
     * @param object
     *              the object that holds the field
     * @param fieldName
     *              the field's name
     * @param useSuperClass
     *          indicates whether to get field from base class or from this class
     *
     * @throws RuntimeException
     *          if fieldName field is absent in the given object. Used primarily during debugging
     *          to fix typos in fields names
     *
     * @return the value of the field or null if the field was not found
     */
    private static Object getField(Object object, String fieldName, boolean useSuperClass) {
        Object value = null;
        try {
            Class<?> cls = object.getClass();
            if (useSuperClass) {
                cls = cls.getSuperclass();
            }
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            try {
                value = field.get(object);
            } finally {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("There is a typo in field name '" + fieldName + "'");
        } catch (IllegalAccessException e) {
            // Ignore
        }
        return value;
    }

    /**
     * The helper method used to implement corresponding public methods.
     * Sets the value of the field with the given name in the given object if <code>useSuperClass</code> is false,
     * or in the super type sub-object of the given object if <code>useSuperClass</code> is true.
     * The method has access to all the fields, despite theirs access level. This is achieved through reflection.
     *
     * @param object
     *              the object that holds the field or whose super type sub-object holds the field.
     * @param fieldName
     *              the field's name
     * @param value
     *              the field's value
     * @param useSuperClass
     *              indicates whether to get field from base class or from this class.
     */
    private static void setField(Object object, String fieldName, Object value, boolean useSuperClass) {
        try {
            Class<?> cls = object.getClass();
            if (useSuperClass) {
                cls = cls.getSuperclass();
            }
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            try {
                field.set(object, value);
            } finally {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
    }

    /**
     * Checks that the methods of the given class obeys JavaBean naming conventions.
     * The following conditions are checked:
     * <ul>
     * <li>All public methods have getXXX/isXXX or setXXX form</li>
     * <li>For each getXXX/isXXX method there is corresponding setXXX method and vice versa</li>
     * </ul>
     *
     * @param cls
     *              the class whose methods are tested against the defined conditions
     *
     * @throws Exception
     *              if the given class doesn't obey the conditions
     */
    public static void checkJavaBeanMethods(Class<?> cls) throws Exception {
        Method[] methods = cls.getMethods();
        List<String> getters = new ArrayList<String>();
        List<String> setters = new ArrayList<String>();

        // Store setters and getters names without get/set prefixes.
        for (Method method : methods) {
            if (method.getDeclaringClass() == cls) {
                String name = method.getName();
                if (name.startsWith("get")) {
                    getters.add(name.substring(3));
                } else if (name.startsWith("is")) {
                    getters.add(name.substring(2));
                } else if (name.startsWith("set")) {
                    setters.add(name.substring(3));
                }
            }
        }
        // Prepare data for search.
        List<String> data = null;
        Set<String> lookup = new HashSet<String>();
        if (getters.size() >= setters.size()) {
            data = getters;
            lookup.addAll(setters);
        } else {
            data = setters;
            lookup.addAll(getters);
        }
        // Perform the search of missing getter/setter.
        for (String name : data) {
            if (!lookup.contains(name)) {
                String message = null;
                if (getters.size() >= setters.size()) {
                    message = String.format("can not find 'set%s' setter", name);
                } else {
                    message = String.format("can not find 'get%s'/'is%s' getter", name, name);
                }
                throw new Exception(message);
            }
        }
    }

    /**
     * Clears the database.
     *
     * @param connection
     *            the connection
     *
     * @throws Exception to JUnit
     */
    public static void clearDB(Connection connection) throws Exception {
        executeSQL(connection, "db_clear.sql");
    }

    /**
     * Reads the content of a given file.
     *
     * @param fileName
     *            the name of the file to read
     *
     * @return a string with content of the given file
     *
     * @throws IOException
     *             if any error occurs during reading
     */
    private static String readFile(String fileName) throws IOException {
        BufferedReader reader = null;
        try {
            URL fileURL = ClassLoader.getSystemResource(fileName);
            reader = new BufferedReader(new InputStreamReader(fileURL.openStream()));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append(LINE_SEPARATOR);
                line = reader.readLine();
            }
            return sb.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * Executes the SQL statements from the given file.
     *
     * @param connection
     *            the connection
     * @param fileName
     *            the file to read SQL from
     *
     * @throws Exception to JUnit
     */
    private static void executeSQL(Connection connection, String fileName) throws Exception {
        String[] lines = readFile(fileName).split(";");
        Statement statement = connection.createStatement();
        try {
            for (String line : lines) {
                String sql = line.trim();
                if (sql.length() != 0) {
                    statement.executeUpdate(sql);
                }
            }
        } finally {
            statement.close();
        }
    }
}
