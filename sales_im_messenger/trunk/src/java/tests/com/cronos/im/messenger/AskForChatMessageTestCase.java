/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit test cases for class <c>AskForChatMessage</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public class AskForChatMessageTestCase extends XMLMessageTestCase {
    /**
     * File path for the XSD file which contains the requested structure for the
     * xml string representation of AskForChatMessage.
     */
    private static final String ASK_FOR_CHAT_MESSAGE_XSD_FILE = TestHelper.XSD_DIRECTORY
        + File.separator + "AskForChatMessage.xsd";

    /**
     * Represents a concrete instance of <c>XMLMessage</c> class is initialized
     * in <c>setUp()</c> method.
     */
    private AskForChatMessage askForChatMessage;


    /**
     * Represents the document builder used to verify that the schema definition of the
     * resulted xml string from <c>toXMLString(DateFormatContext)</c> method is respected.
     */
    private DocumentBuilder docBuilder;

    /**
     * Represents the session creation time for the message.
     * It is initialized in <c>setUp()</c> method.
     */
    private Date sessionCreationTime;

    /**
     * Represents the acknowledge time for the message.
     * It is initialized in <c>setUp()</c> method.
     */
    private Date acknowledgeTime;

    /**
     * Represents the author profile properties of the message. It is initialized
     * in <c>setUp()</c> method.
     */
    private Map authorProfileProperties;

    /**
     * Represents the request user id for the message. It is initialized in
     * <c>setUp()</c> method.
     */
    private long requestUserId;

    /**
     * This methods returns an instance of <c>AskForChatMessage</c> and is used in <c>XMLMessage</c>
     * to initialize <c>xmlMessage</c> field.
     *
     * @return An instance of <c>AskForChatMessage</c> class.
     */
    protected XMLMessage getXMLMessage() {
        return new AskForChatMessage();
    }

    /**
     * Initializes testing environment by setting the value for <c>askForChatMessage</c> field.
     *
     * @throws Exception propagated to JUnit
     */
    protected void setUp() throws Exception {
        // Setup first xmlMessage and then assign its value to askForChatMessage.
        super.setUp();
        askForChatMessage = (AskForChatMessage) xmlMessage;

        sessionCreationTime = new Date();
        acknowledgeTime = new Date();
        authorProfileProperties = new HashMap();
        authorProfileProperties.put("userName", new String[]{"marius_neo"});
        requestUserId = 100;

        // Setup also the document builder which verifies that the structure of the
        // generated xml string is correct.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");

        // Specify our own schema - this overrides the schemaLocation in the xml file
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", ASK_FOR_CHAT_MESSAGE_XSD_FILE);
        docBuilder = dbf.newDocumentBuilder();
        docBuilder.setErrorHandler(new SimpleErrorHandler());
    }

    /**
     * Tests the accuracy of the constructor <c>AskForChatMessage</c>.
     */
    public void testConstructor() {
        assertNotNull("askForChatMessage was not created", askForChatMessage);
    }

    /**
     * Tests the accuracy for <c>setSessionCreationTime(Date)</c> method.Since <c>getSessionCreationTime()</c>
     * method is used in this method, we can consider that this method tests also
     * <c>getSessionCreationTime()</c> method for accuracy.
     */
    public void testSetGetSessionCreationTime() {
        askForChatMessage.setSessionCreationTime(sessionCreationTime);

        assertEquals("The session creation times should be equal"
            , sessionCreationTime, askForChatMessage.getSessionCreationTime());
    }

    /**
     * Tests for failure <c>setSessionCreationTime(Date)</c> method when a null parameter is passed
     * to the method.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testSetSessionCreationTimeNull() {
        try {
            askForChatMessage.setSessionCreationTime(null);

            fail("Should have thrown IllegalArgumentException because of null parameter");
        } catch (IllegalArgumentException e) {
            // Succes.
        }
    }

    /**
     * Tests the accuracy for <c>setAcknowledgeTime(Date)</c> method.Since <c>getAcknowledgeTime()</c>
     * method is used in this method, we can consider that this method tests also
     * <c>getAcknowledgeTime()</c> method for accuracy.
     */
    public void testSetGetAcknowledgeTime() {
        askForChatMessage.setAcknowledgeTime(acknowledgeTime);

        assertEquals("The acknowledge times should be equal"
            , acknowledgeTime, askForChatMessage.getAcknowledgeTime());
    }

    /**
     * Tests for failure <c>setAcknowledgeTime(Date)</c> method when a null parameter is passed
     * to the method.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testSetAcknowledgeTimeNull() {
        try {
            askForChatMessage.setAcknowledgeTime(null);

            fail("Should have thrown IllegalArgumentException because of null parameter");
        } catch (IllegalArgumentException e) {
            // Succes.
        }
    }

    /**
     * Tests the accuracy for <c>setRequestUserId(long)</c> method.Since <c>getRequestUserId()</c>
     * method is used in this method, we can consider that this method tests also
     * <c>getRequestUserId()</c> method for accuracy.
     */
    public void testSetGetRequestUserId() {
        askForChatMessage.setRequestUserId(requestUserId);

        assertEquals("The request user ids should be equal"
            , requestUserId, askForChatMessage.getRequestUserId());
    }

    /**
     * Tests for failure <c>setRequestUserId(long)</c> method when a negative parameter is passed
     * to the method.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testSetRequestUserIdNegative() {
        try {
            askForChatMessage.setRequestUserId(-1999);

            fail("Should have thrown IllegalArgumentException because of negative parameter");
        } catch (IllegalArgumentException e) {
            // Succes.
        }
    }

    /**
     * Tests the accuracy for <c>setAuthorProfileProperties(Map)</c> method.Since
     * <c>getAuthorProfileProperties()</c> method is called several times, we can consider
     * that this method tests also <c>getAuthorProfileProperties()</c> method for accuracy.
     */
    public void testSetGetChatProfileProperties() {
        // The structure of chat profile properties map is: <string, string[]>
        Map properties = new HashMap();

        String key1 = "key1";
        String[] values1 = new String[]{"value1"};
        properties.put(key1, values1);
        String key2 = "key2";
        String[] values2 = new String[]{"value2_1", "value2_2"};
        properties.put(key2, values2);

        askForChatMessage.setAuthorProfileProperties(properties);

        Map chatProfileProperties = askForChatMessage.getAuthorProfileProperties();

        assertTrue("The sizes for the chat profile properties maps should be the same"
            , properties.size() == chatProfileProperties.size());
        for (Iterator i = chatProfileProperties.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            String key = (String) entry.getKey();
            String[] values = (String[]) entry.getValue();
            if (key.equals(key1)) {
                assertTrue("The number of values should be equal", values.length == values1.length);
                assertEquals("The values should be equal", values[0], values1[0]);
            } else if (key.equals(key2)) {
                assertTrue("The number of values should be equal", values.length == values2.length);
                assertEquals("The values should be equal", values[0], values2[0]);
                assertEquals("The values should be equal", values[1], values2[1]);
            } else {
                fail("The key doesn't match key1 or key2");
            }
        }
    }

    /**
     * Tests <c>setAuthorProfileProperties(Map)</c> method for failure
     * when an null map is passed to the method.
     */
    public void testSetChatProfilePropertiesNullMap() {
        try {
            askForChatMessage.setAuthorProfileProperties(null);

            fail("IllegalArgumentException should be thrown for null properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setAuthorProfileProperties(Map)</c> method for failure
     * when an empty map is passed for validation.
     */
    public void testSetChatProfilePropertiesEmptyMap() {
        try {
            askForChatMessage.setAuthorProfileProperties(new HashMap());

            fail("IllegalArgumentException should be thrown for empty properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setAuthorProfileProperties(Map)</c> method for failure
     * when a map containing null values or non-string keys or non String[] values is passed for validation.
     */
    public void testSetChatProfilePropertiesMapWithInvalidElements() {
        Map properties = new HashMap();
        properties.put("key1", null);
        try {
            askForChatMessage.setAuthorProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains null values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put(new Object(), new String[]{"value1"});
        try {
            askForChatMessage.setAuthorProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains non-string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("key1", new Object());
        try {
            askForChatMessage.setAuthorProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains non String[] values");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setAuthorProfileProperties(Map)</c> method for failure
     * when a map containing empty string keys/ empty string array values.
     */
    public void testSetChatProfilePropertiesEmptyStringKeysOrEmptyStringArrayValues() {
        Map properties = new HashMap();
        properties.put("key1", new String[]{});
        try {
            askForChatMessage.setAuthorProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because"
                + " properties map contains empty string array values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("", new String[]{"value1"});
        try {
            askForChatMessage.setAuthorProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties"
                + " map contains empty string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for the method <c>toXMLString(DateFormatContext)</c>.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLString() throws Exception {
        //Set session creation time, acknowledget time and author profile properties and requestUserId
        askForChatMessage.setSessionCreationTime(sessionCreationTime);
        askForChatMessage.setAcknowledgeTime(acknowledgeTime);
        askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
        askForChatMessage.setRequestUserId(requestUserId);

        String xmlString = askForChatMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with ASK_FOR_CHAT_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));

    }

    /**
     * Tests the accuracy for the method <c>toXMLString(DateFormatContext)</c> when
     * the message has no attributes.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoAttributes() throws Exception {
        //Set session creation time, acknowledget time and author profile properties
        askForChatMessage.setSessionCreationTime(sessionCreationTime);
        askForChatMessage.setAcknowledgeTime(acknowledgeTime);
        askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
        askForChatMessage.setRequestUserId(requestUserId);

        askForChatMessage.removeAllAttributes();

        String xmlString = askForChatMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with ASK_FOR_CHAT_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when the session
     * creation time is missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoSessionCreationTime() throws Exception {
        askForChatMessage.setAcknowledgeTime(acknowledgeTime);
        askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
        askForChatMessage.setRequestUserId(requestUserId);
        try {
            askForChatMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because session creation time is not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when acknowledge time
     * is missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoAcknowledgeTime() throws Exception {
        askForChatMessage.setSessionCreationTime(sessionCreationTime);
        askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
        askForChatMessage.setRequestUserId(requestUserId);
        try {
            askForChatMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because acknowledge time is not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when request user id
     * is missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoRequestUserId() throws Exception {
        askForChatMessage.setSessionCreationTime(sessionCreationTime);
        askForChatMessage.setAcknowledgeTime(acknowledgeTime);
        askForChatMessage.setAuthorProfileProperties(authorProfileProperties);

        try {
            askForChatMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because request user id is not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when the author
     * profile properties are missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoAuthorUserProfileProperties() throws Exception {
        askForChatMessage.setSessionCreationTime(sessionCreationTime);
        askForChatMessage.setAcknowledgeTime(acknowledgeTime);
        askForChatMessage.setRequestUserId(requestUserId);
        try {
            askForChatMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because chat user profile properties are not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }
}
