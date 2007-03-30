/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit test cases for class <c>PresenceMessage</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public class PresenceMessageTestCase extends XMLMessageTestCase {
    /**
     * File path for the XSD file which contains the requested structure for the
     * xml string representation of PresenceMessage.
     */
    public static final String PRESENCE_MESSAGE_XSD_FILE = TestHelper.XSD_DIRECTORY
        + File.separator + "PresenceMessage.xsd";

    /**
     * Represents a concrete instance of <c>XMLMessage</c> class is initialized
     * in <c>setUp()</c> method.
     */
    private PresenceMessage presenceMessage;

    /**
     * Represents the document builder used to verify that the schema definition of the
     * resulted xml string from <c>toXMLString(DateFormatContext)</c> method is respected.
     */
    private DocumentBuilder docBuilder;

    /**
     * This methods returns an instance of <c>PresenceMessage</c> and is used in <c>XMLMessage</c>
     * to initialize <c>xmlMessage</c> field.
     *
     * @return An instance of <c>PresenceMessage</c> class.
     */
    protected XMLMessage getXMLMessage() {
        return new PresenceMessage();
    }

    /**
     * Initializes testing environment by setting the value for <c>presenceMessage</c> field.
     *
     * @throws Exception propagated to JUnit
     */
    public void setUp() throws Exception {
        // Setup first xmlMessage and then assign its value to presenceMessage.
        super.setUp();
        presenceMessage = (PresenceMessage) xmlMessage;

        // Setup also the document builder which verifies that the structure of the
        // generated xml string is correct.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");

        // Specify our own schema - this overrides the schemaLocation in the xml file
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", PRESENCE_MESSAGE_XSD_FILE);

        docBuilder = dbf.newDocumentBuilder();
        docBuilder.setErrorHandler(new SimpleErrorHandler());
    }

    /**
     * Tests the accuracy of the constructor <c>PresenceMessage</c>.
     */
    public void testConstructor() {
        assertNotNull("presenceMessage was not created", presenceMessage);
    }

    /**
     * Tests <c>setPresent(boolean)</c> method. Since <c>isPresent()</c> method is used in the body
     * of this test method several times, we can consider that this method tests also
     * <c>isPresent()</c> method.
     */
    public void testSetPresent() {
        presenceMessage.setPresent(false);
        assertFalse("The user shouldn't be present", presenceMessage.isPresent());
        presenceMessage.setPresent(true);
        assertTrue("The user should be present", presenceMessage.isPresent());
    }

    /**
     * Tests the accuracy for <c>setChatProfileProperties(Map)</c> method.Since <c>getChatProfileProperties()</c>
     * method is called several times, we can consider that this method tests also
     * <c>getChatProfileProperties()</c> method for accuracy.
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

        presenceMessage.setChatProfileProperties(properties);

        Map chatProfileProperties = presenceMessage.getChatProfileProperties();

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
     * Tests <c>setChatProfileProperties(Map)</c> method for failure
     * when an null map is passed to the method.
     */
    public void testSetChatProfilePropertiesNullMap() {
        try {
            presenceMessage.setChatProfileProperties(null);

            fail("IllegalArgumentException should be thrown for null properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setChatProfileProperties(Map)</c> method for failure
     * when an empty map is passed for validation.
     */
    public void testSetChatProfilePropertiesEmptyMap() {
        try {
            presenceMessage.setChatProfileProperties(new HashMap());

            fail("IllegalArgumentException should be thrown for empty properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setChatProfileProperties(Map)</c> method for failure
     * when a map containing null values or non-string keys or non String[] values is passed for validation.
     */
    public void testsetChatProfilePropertiesMapWithInvalidElements() {
        Map properties = new HashMap();
        properties.put("key1", null);
        try {
            presenceMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains null values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put(new Object(), new String[]{"value1"});
        try {
            presenceMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains non-string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("key1", new Object());
        try {
            presenceMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains non String[] values");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setChatProfileProperties(Map)</c> method for failure
     * when a map containing empty string keys/ empty string array values.
     */
    public void testSetChatProfilePropertiesEmptyStringKeysOrEmptyStringArrayValues() {
        Map properties = new HashMap();
        properties.put("key1", new String[]{});
        try {
            presenceMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because"
                + " properties map contains empty string array values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("", new String[]{"value1"});
        try {
            presenceMessage.setChatProfileProperties(properties);

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
        //Set presence and chat user profile properties
        presenceMessage.setPresent(true);
        Map properties = new HashMap();
        properties.put("key1", new String[]{"value1_1", "value1_2"});
        presenceMessage.setChatProfileProperties(properties);

        String xmlString = presenceMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with PRESENCE_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));

    }

    /**
     * Tests the accuracy for the method <c>toXMLString(DateFormatContext)</c> when
     * the message has no attributes.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoAttributes() throws Exception {
        //Set presence and chat user profile properties
        presenceMessage.setPresent(true);
        Map properties = new HashMap();
        properties.put("key1", new String[]{"value1_1", "value1_2"});
        presenceMessage.setChatProfileProperties(properties);

        presenceMessage.removeAllAttributes();

        String xmlString = presenceMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with PRESENCE_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when the chat user
     * profile properties are missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoChatUserProfileProperties() throws Exception {
        try {
            presenceMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because chat user profile properties are not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }
}
