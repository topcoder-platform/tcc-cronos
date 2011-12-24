/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.accuracytests;

import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.After;
import org.junit.Before;
import org.xml.sax.SAXException;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.payments.amazonfps.model.PaymentDetails;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.model.PaymentEventType;

/**
 * <p>
 * The base class for unit tests for.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BaseAccTest {

    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    public static final String EMPTY_STRING = " \t ";
    /**
     * <p>
     * Represents the token id string.
     * </p>
     */
    public static final String TOKEN_ID = "P6ATQZF6APBEDBMGE736FAYULM9IANHHUJPHPDKINNG7QTQA8H83FSBRXQF3CREQ";

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String ACC_TEST_FILES = "test_files" + File.separator + "accuracytests"
        + File.separator;

    /**
     * <p>
     * Represents the configuration file used in tests.
     * </p>
     */
    private static final String CONFIG_FILE = ACC_TEST_FILES + "config.xml";

    /**
     * Filename of the schema file used to validate XMl presentation of this class.
     */
    public static final String PAYMENT_EVENT_SCHEMA_FILENAME = "PaymentEvent.xsd";

    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        connection = getConnection();
        clearDB();
        loadDB();
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        try {
            clearDB();
        } finally {
            closeConnection(connection);
        }
        connection = null;
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig() throws Exception {
        return getConfig(CONFIG_FILE, "com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl");
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @param configFile
     *            the configuration file.
     * @param name
     *            the name.
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig(String configFile, String name) throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();

        // Get configuration
        ConfigurationObject obj = persistence.loadFile(name, new File(configFile));

        return obj.getChild(name);
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            try {
                value = declaredField.get(obj);
            } finally {
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }

    /**
     * <p>
     * Sets value to the field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     * @param value
     *            the field value.
     */
    public static void setField(Object obj, String field, Object value) {
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
            }
            declaredField.setAccessible(true);

            declaredField.set(obj, value);

            declaredField.setAccessible(false);
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Gets a connection.
     * </p>
     *
     * @return the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static Connection getConnection() throws Exception {
        ConfigurationObject config = getConfig().getChild("authorizationPersistenceConfig");

        // Get configuration of DB Connection Factory
        ConfigurationObject dbConnectionFactoryConfig = config
            .getChild("dbConnectionFactoryConfig");

        // Create database connection factory using the extracted configuration
        DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(
            dbConnectionFactoryConfig);

        return dbConnectionFactory.createConnection();
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void clearDB() throws Exception {
        executeSQL(connection, ACC_TEST_FILES + "DBClear.sql");
    }

    /**
     * <p>
     * Closes the given connection.
     * </p>
     *
     * @param connection
     *            the given connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void closeConnection(Connection connection) throws Exception {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void loadDB() throws Exception {
        executeSQL(connection, ACC_TEST_FILES + "DBData.sql");
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Empty lines will be ignored.
     * </p>
     *
     * @param connection
     *            the connection.
     * @param file
     *            the file.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(Connection connection, String file) throws Exception {
        String[] values = readFile(file).split(";");

        Statement statement = connection.createStatement();
        try {

            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if (sql.length() != 0) {
                    statement.executeUpdate(sql);
                }
            }
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException
     *             if any error occurs during reading.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString().replace("\r\n", "\n");
        } finally {
            reader.close();
        }
    }

    /**
     * Creates {@code PaymentEvent} instance with all fields initialized. This methods is used for
     * testing both event subscriber and event receiver classes.
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
     * Gets messages from the queue. When expected message count is not zero this method waits till
     * all messages will be received. If this parameter is zero the method retrieves all available
     * messages and returns. <br/>
     *
     * This method is used by subscriber test class and also indirectly by receiver test class which
     * calls {@code ensureJMSQueueIsEmpty} method.
     *
     * @param connFactoryName
     *            the JNDI name of the connection factory
     * @param queueName
     *            the JNDI name of the queue
     * @param expectedMessagesCount
     *            how many messages are expected to receive. 0 if unknown number of messages are
     *            expected
     *
     * @return the list of payment events
     *
     * @throws NamingException
     *             if naming exception is encountered
     * @throws JMSException
     *             if some JMS error occurred
     */
    public static List<String> getMessagesFromQueue(String connFactoryName, String queueName,
        int expectedMessagesCount) throws NamingException, JMSException {
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) JNDIUtils
            .getObject(connFactoryName);
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
                    TextMessage txtMessage = (TextMessage) queueReceiver.receive(1000L);
                    if (txtMessage != null) {
                        result.add(txtMessage.getText());
                    }
                }
                assertNull("there should not be any messages in the queque",
                    queueReceiver.receive(1000L));
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
     * Sends message to the queue.
     *
     * @param connFactoryName
     *            the JNDI name of the connection factory
     * @param queueName
     *            the JNDI name of the queue
     * @param message
     *            the message to send
     *
     * @throws NamingException
     *             if naming exception is encountered
     * @throws JMSException
     *             if some JMS error occurred
     */
    protected void sendMessageToQueue(String connectionFactoryName, String queueName, String message)
        throws NamingException, JMSException {
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) JNDIUtils
            .getObject(connectionFactoryName);
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
     * Extracts the payment event details from the given XML string.
     *
     * @param xml
     *            the XML string with payment event details (assumed not {@code null})
     *
     * @return the extracted payment event (not {@code null})
     *
     * @throws JAXBException
     *             if some JAXB error occurred (including XML validation failure)
     * @throws SAXException
     *             if error occurred while parsing schema for payment event XML
     */
    public static PaymentEvent getPaymentEventFromXml(String xml) throws JAXBException,
        SAXException {
        JAXBContext context = JAXBContext.newInstance(PaymentEvent.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // set schema for validation. If validation fails we will get JAXBException
        SchemaFactory schemaFactory = SchemaFactory
            .newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = ClassLoader.getSystemResource(PAYMENT_EVENT_SCHEMA_FILENAME);
        Schema schema = schemaFactory.newSchema(url);
        unmarshaller.setSchema(schema);

        // unmarshall XML and do validation
        StreamSource streamSource = new StreamSource(new StringReader(xml));
        return unmarshaller.unmarshal(streamSource, PaymentEvent.class).getValue();
    }

    /**
     * Converts the given payment event instance to an XML string.
     *
     * @param paymentEvent
     *            the payment event to be converted (assumed not {@code null})
     *
     * @return the XML string containing payment event details (not {@code null})
     *
     * @throws JAXBException
     *             if any error occurred
     */
    public static String convertPaymentEventToXml(PaymentEvent paymentEvent) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PaymentEvent.class);
        Marshaller marshaller = context.createMarshaller();

        QName name = new QName("", "PaymentEvent");
        Object jaxbElement = new JAXBElement<PaymentEvent>(name, PaymentEvent.class, paymentEvent);

        StringWriter writer = new StringWriter();
        marshaller.marshal(jaxbElement, writer);
        return writer.toString();
    }
}
