/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import com.topcoder.util.config.ConfigManager;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit test cases for class <c>ChatMessage</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public class ChatMessageTestCase extends XMLMessageTestCase {
    /**
     * File path for the XSD file which contains the requested structure for the
     * xml string representation of ChatMessage.
     */
    private static final String CHAT_MESSAGE_XSD_FILE = TestHelper.XSD_DIRECTORY
        + File.separator + "ChatMessage.xsd";

    /**
     * Represents a valid configuration file for loading namespaces requested by <c>FormatterLoader</c> class.
     */
    private static final String FORMATTER_LOADER_VALID_CONFIG = "test_files"
        + File.separator + "formatter_loader_config.xml";

    /**
     * Represents a concrete instance of <c>XMLMessage</c> class is initialized
     * in <c>setUp()</c> method.
     */
    private ChatMessage chatMessage;

    /**
     * Represents the document builder used to verify that the schema definition of the
     * resulted xml string from <c>toXMLString(DateFormatContext)</c> method is respected.
     */
    private DocumentBuilder docBuilder;

    /**
     * Represents the chat profile properties of the message. It is initialized
     * in <c>setUp()</c> method.
     */
    private Map chatProfileProperties;

    /**
     * Represents the chat text for the message. It is initialized in
     * <c>setUp()</c> method.
     */
    private String chatText;

    /**
     * This methods returns an instance of <c>ChatMessage</c> and is used in <c>XMLMessage</c>
     * to initialize <c>xmlMessage</c> field.
     *
     * @return An instance of <c>ChatMessage</c> class.
     */
    protected XMLMessage getXMLMessage() {
        return new ChatMessage();
    }

    /**
     * Initializes testing environment by setting the value for <c>chatMessage</c> field.
     *
     * @throws Exception propagated to JUnit
     */
    public void setUp() throws Exception {
        // Setup first xmlMessage and then assign its value to chatMessage.
        super.setUp();
        chatMessage = (ChatMessage) xmlMessage;

        chatProfileProperties = new HashMap();
        chatProfileProperties.put("userName", new String[]{"marius_neo"});
        chatText = "Visit http://www.google.com/finance everyday and you will be very rich.";

        // Setup also the document builder which verifies that the structure of the
        // generated xml string is correct.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");

        // Specify our own schema - this overrides the schemaLocation in the xml file
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", CHAT_MESSAGE_XSD_FILE);

        docBuilder = dbf.newDocumentBuilder();
        docBuilder.setErrorHandler(new SimpleErrorHandler());

        // Add the configuration for the namespace needed by FormatterLoader class.
        ConfigManager cm = ConfigManager.getInstance();
        File configFile = new File(FORMATTER_LOADER_VALID_CONFIG);
        cm.add(configFile.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager and sets to
     * <c>null</c> the singleton instance of <c>FormatterLoader</c> class.
     * </p>
     *
     * @throws Exception Any exception that may appear is thrown to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        // Clear the instace static field of FormatterLoader class by reflection.
        Field instanceField = FormatterLoader.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }


    /**
     * Tests the accuracy of the constructor <c>ChatMessage</c>.
     */
    public void testConstructor() {
        assertNotNull("chatMessage was not created", chatMessage);
    }

    /**
     * Tests the accuracy for <c>setChatText(String)</c> method.Since <c>getChatText()</c>
     * method is used in this method, we can consider that this method tests also
     * <c>getChatText()</c> method for accuracy.
     */
    public void testSetGetChatText() {
        chatMessage.setChatText(chatText);

        assertEquals("The chat texts be equal"
            , chatText, chatMessage.getChatText());
    }

    /**
     * Tests for failure <c>setChatText(String)</c> method when a null parameter is passed
     * to the method.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testSetChatTextNull() {
        try {
            chatMessage.setChatText(null);

            fail("Should have thrown IllegalArgumentException because of null parameter");
        } catch (IllegalArgumentException e) {
            // Succes.
        }
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

        chatMessage.setChatProfileProperties(properties);

        Map chatProfileProperties = chatMessage.getChatProfileProperties();

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
            chatMessage.setChatProfileProperties(null);

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
            chatMessage.setChatProfileProperties(new HashMap());

            fail("IllegalArgumentException should be thrown for empty properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>setChatProfileProperties(Map)</c> method for failure
     * when a map containing null values or non-string keys or non String[] values is passed to the method.
     */
    public void testSetChatProfilePropertiesMapWithInvalidElements() {
        Map properties = new HashMap();
        properties.put("key1", null);
        try {
            chatMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains null values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put(new Object(), new String[]{"value1"});
        try {
            chatMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because properties map contains non-string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("key1", new Object());
        try {
            chatMessage.setChatProfileProperties(properties);

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
            chatMessage.setChatProfileProperties(properties);

            fail("IllegalArgumentException should be thrown because"
                + " properties map contains empty string array values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("", new String[]{"value1"});
        try {
            chatMessage.setChatProfileProperties(properties);

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
        //Set chat profile properties and chat text
        chatMessage.setChatProfileProperties(chatProfileProperties);
        chatMessage.setChatText(chatText);

        String xmlString = chatMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with CHAT_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));

    }

    /**
     * Tests the accuracy for the method <c>toXMLString(DateFormatContext)</c> when
     * the message has no attributes.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoAttributes() throws Exception {
        //Set chat profile properties and chat text
        chatMessage.setChatProfileProperties(chatProfileProperties);
        chatMessage.setChatText(chatText);
        chatMessage.removeAllAttributes();

        String xmlString = chatMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with CHAT_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when chat text
     * is missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoChatText() throws Exception {
        //Set chat profile properties but don't set chat text
        chatMessage.setChatProfileProperties(chatProfileProperties);

        try {
            chatMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because chat text is not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure <c>toXMLString(DateFormatContext)</c> method when the chat
     * profile properties are missing from the message.<c>IllegalStateException</c> is
     * expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoChatUserProfileProperties() throws Exception {
        //Set chat text, but don't set chat profile properties.
        chatMessage.setChatText(chatText);

        try {
            chatMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because chat user profile properties are not set");
        } catch (IllegalStateException e) {
            // Success.
        }
    }
}
