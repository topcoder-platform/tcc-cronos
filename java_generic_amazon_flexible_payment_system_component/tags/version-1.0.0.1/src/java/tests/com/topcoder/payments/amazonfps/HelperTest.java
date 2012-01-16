/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.persistence.db.MockBaseDatabasePersistence;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * Unit tests for {@link Helper} class. <br/>
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class HelperTest {
    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperTest.class);
    }

    /**
     * Accuracy test for {@code convertPaymentEventToXml} method. <br/>
     * Convert fully initialized payment event to XML. Validate resulted XML against schema for PaymentEvent.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_convertPaymentEventToXml_1() throws Exception {
        PaymentEvent event = TestHelper.createTestPaymentEvent();
        String xml = Helper.convertPaymentEventToXml(event);
        assertNotNull("The xml string should not be null", xml);
        assertFalse("The xml string should not be empty", xml.trim().isEmpty());

        validatePaymentEventXml(xml);
    }

    /**
     * Accuracy test for {@code convertPaymentEventToXml} method. <br/>
     * Convert payment event with default values to xml. Validate resulted XML against schema for PaymentEvent.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_convertPaymentEventToXml_2() throws Exception {
        String xml = Helper.convertPaymentEventToXml(new PaymentEvent());
        assertNotNull("The xml string should not be null", xml);
        assertFalse("The xml string should not be empty", xml.trim().isEmpty());

        validatePaymentEventXml(xml);
    }

    /**
     * Accuracy test for {@code getPaymentEventFromXml} method. <br/>
     * Create simple xml which represents payment event and then instantiate payment event object from it.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentEventFromXml_1() throws Exception {
        String xml = "<?xml version=\"1.0\"?><PaymentEvent><authorizationId>1</authorizationId>"
                    + "<paymentId>2</paymentId></PaymentEvent>";
        validatePaymentEventXml(xml); // at first check that we created valid xml string

        PaymentEvent event = Helper.getPaymentEventFromXml(xml);
        assertNotNull("payment event should not be null", event);

        PaymentEvent expected = new PaymentEvent();
        expected.setAuthorizationId(1L);
        expected.setPaymentId(2L);
        TestHelper.checkEquals(expected, event);
    }

    /**
     * Accuracy test for {@code getPaymentEventFromXml} method. <br/>
     * Check that from xml obtained from convertPaymentEventToXml() we can restore object state.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentEventFromXml_2() throws Exception {
        PaymentEvent event = TestHelper.createTestPaymentEvent();
        String xml = Helper.convertPaymentEventToXml(event);
        PaymentEvent result = Helper.getPaymentEventFromXml(xml);
        assertNotNull("restored payment event should not be null", result);

        TestHelper.checkEquals(event, result);
    }

    /**
     * Accuracy test for {@code toString} method. <br/>
     * For null object "null" should be returned
     */
    @Test
    public void test_toString_1() {
        String result = Helper.toString(null);
        assertEquals("result should be 'null' string", "null", result);
    }

    /**
     * Accuracy test for {@code toString} method. <br/>
     * Check that BigDecimal string presentation is correct
     */
    @Test
    public void test_toString_2() {
        String result = Helper.toString(BigDecimal.valueOf(12.56));
        assertEquals("result should be correct", "12.56", result);
    }

    /**
     * Accuracy test for {@code toString} method. <br/>
     * Check that List string presentation is correct
     */
    @Test
    public void test_toString_3() {
        List<String> list = Arrays.asList("1", "2", "3");
        String result = Helper.toString(list);
        String expected = "[1, 2, 3]";
        assertEquals("result should be correct", expected, result);
    }

    /**
     * Accuracy test for {@code toString} method. <br/>
     * Check that Map string presentation is correct
     */
    @Test
    public void test_toString_4() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        // we can test only map with one element, since map entries are unordered
        map.put("key1", 10);
        String result = Helper.toString(map);
        String expected = "{(key1, 10)}";
        assertEquals("result should be correct", expected, result);
    }

    /**
     * Accuracy test for {@code closeConnection} method. <br/>
     * Check that there are no exceptions when closing connection and that connection
     * is closed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_closeConnection_1() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("PersistenceTest");
        MockBaseDatabasePersistence testConnectionProvider = new MockBaseDatabasePersistence();
        testConnectionProvider.configure(configuration);

        Connection connection = testConnectionProvider.createConnection();
        Log log = LogManager.getLog("myLog");

        assertFalse("Connection should not be closed", connection.isClosed());
        Helper.closeConnection(connection, log, "mySignature");
        assertTrue("Connection should be closed", connection.isClosed());
    }

    /**
     * Accuracy test for {@code getLog} method. <br/>
     * The log should not be null if it is defined in the configuration.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getLog_1() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("PersistenceTest");
        assertNotNull("The log instance should not be null", Helper.getLog(configuration));
    }

    /**
     * Accuracy test for {@code getLog} method. <br/>
     * The log should be null if it is not defined in the configuration.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getLog_2() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("BaseDatabasePersistenceTest2");
        assertNull("The log instance should be null", Helper.getLog(configuration));
    }

    /**
     * Accuracy test for {@code getProperty} method. <br/>
     * Check that property value is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProperty_1() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("PersistenceTest");
        String expected = "myLogger";
        String actual = Helper.getProperty(configuration, "loggerName", false);
        assertEquals("Property value should be correct", expected, actual);
    }

    /**
     * Accuracy test for {@code getProperty} method. <br/>
     * Check that if not required property is missing then null is returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProperty_2() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("PersistenceTest");
        String value = Helper.getProperty(configuration, "someProperty", false);
        assertNull("Value should be null", value);
    }

    /**
     * Accuracy test for {@code getChildConfiguration} method. <br/>
     * Check that for existed child configuration not null value is returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getChildConfiguration_1() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("PersistenceTest");

        ConfigurationObject child = Helper.getChildConfiguration(configuration, "dbConnectionFactoryConfig");
        assertNotNull("Child object should not be null", child);
    }

    /**
     * Failure test for {@code getChildConfiguration} method. <br/>
     * Check that for not-existed child configuration exception is thrown.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_getChildConfiguration_2() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration("PersistenceTest");
        Helper.getChildConfiguration(configuration, "someChild");
    }

    /**
     * Validates the given XML string.
     *
     * @param xml
     *              the XML to validate
     *
     * @throws SAXException
     *              if validation failed
     * @throws JAXBException
     *              if some JAXB error occurred
     * @throws java.io.UnsupportedEncodingException
     *              if encoding of provided XML is unsupported
     * @throws ParserConfigurationException
     *              if failed to configure XML parser
     * @throws IOException
     *              if some IO error occurred
     */
    private void validatePaymentEventXml(String xml)
        throws SAXException, JAXBException, ParserConfigurationException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = ClassLoader.getSystemResource(Helper.PAYMENT_EVENT_SCHEMA_FILENAME);
        Schema schema = schemaFactory.newSchema(url);

        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parser.parse(is);

        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document)); // Throws SAXException if validation failed
    }
}
