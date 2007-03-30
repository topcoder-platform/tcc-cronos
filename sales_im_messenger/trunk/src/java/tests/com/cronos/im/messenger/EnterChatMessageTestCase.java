/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;

/**
 * Unit test cases for class <c>EnterChatMessage</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public class EnterChatMessageTestCase extends XMLMessageTestCase {
    /**
     * File path for the XSD file which contains the requested structure for the
     * xml string representation of EnterChatMessage.
     */
    public static final String ENTER_CHAT_MESSAGE_XSD_FILE = TestHelper.XSD_DIRECTORY
        + File.separator + "EnterChatMessage.xsd";

    /**
     * Represents a concrete instance of <c>XMLMessage</c> class is initialized
     * in <c>setUp()</c> method.
     */
    private EnterChatMessage enterChatMessage;

    /**
     * Represents the document builder used to verify that the schema definition of the
     * resulted xml string from <c>toXMLString(DateFormatContext)</c> method is respected.
     */
    private DocumentBuilder docBuilder;

    /**
     * This methods returns an instance of <c>EnterChatMessage</c> and is used in <c>XMLMessage</c>
     * to initialize <c>xmlMessage</c> field.
     *
     * @return An instance of <c>EnterChatMessage</c> class.
     */
    protected XMLMessage getXMLMessage() {
        return new EnterChatMessage();
    }

    /**
     * Initializes testing environment by setting the value for <c>enterChatMessage</c> field.
     *
     * @throws Exception propagated to JUnit
     */
    public void setUp() throws Exception {
        // Setup first xmlMessage and then assign its value to enterChatMessage.
        super.setUp();
        enterChatMessage = (EnterChatMessage) xmlMessage;

        // Setup also the document builder which verifies that the structure of the
        // generated xml string is correct.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");

        // Specify our own schema - this overrides the schemaLocation in the xml file
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", ENTER_CHAT_MESSAGE_XSD_FILE);
        docBuilder = dbf.newDocumentBuilder();
        docBuilder.setErrorHandler(new SimpleErrorHandler());
    }

    /**
     * Tests the accuracy of the constructor <c>EnterChatMessage</c>.
     */
    public void testConstructor() {
        assertNotNull("enterChatMessage was not created", enterChatMessage);
    }

    /**
     * Tests the accuracy for the method <c>toXMLString(DateFormatContext)</c>.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLString() throws Exception {
        String xmlString = enterChatMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with ENTER_CHAT_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));

    }

    /**
     * Tests the accuracy for the method <c>toXMLString(DateFormatContext)</c> when
     * the message has no attributes.
     *
     * @throws Exception Should not throw.
     */
    public void testToXMLStringNoAttributes() throws Exception {
        enterChatMessage.removeAllAttributes();

        String xmlString = enterChatMessage.toXMLString(context);

        // Verify that the structure of xmlString is consistent with ENTER_CHAT_MESSAGE_XSD_FILE
        docBuilder.parse(new InputSource(new StringReader(xmlString)));
    }
}
