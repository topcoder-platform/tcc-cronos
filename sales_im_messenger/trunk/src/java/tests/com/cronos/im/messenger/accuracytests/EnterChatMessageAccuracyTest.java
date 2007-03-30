/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.EnterChatMessage;
import com.cronos.im.messenger.XMLMessage;

import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;


/**
 * Tests the functionality for class <code>EnterChatMessage</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class EnterChatMessageAccuracyTest extends XMLMessageAccuracyTest {
    /** An instance of <code>XMLMessage</code> class for testing. */
    private EnterChatMessage enterChatMessage;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        enterChatMessage = (EnterChatMessage) xmlMessage;
    }

    /**
     * Test method for 'EnterChatMessage()'.
     */
    public void testEnterChatMessage() {
        assertTrue("Test method for 'EnterChatMessage()' failed.", enterChatMessage instanceof XMLMessage);
    }

    /**
     * Test method for 'EnterChatMessage.toXMLString(DateFormatContext)'
     *
     * @throws Exception to JUnit
     */
    public void testToXMLString() throws Exception {
        DocumentBuilder db = AccuracyHelper.getDocumentBuilder("EnterChatMessage.xsd");
        String xmlStr = enterChatMessage.toXMLString(dateFormatContext);
        db.parse(new InputSource(new StringReader(xmlStr)));
    }

    /**
     * Represents an abstract method which get a concrete implementation of <code>XMLMessage</code>.
     *
     * @return A concrete implementation of <code>XMLMessage</code> class.
     */
    protected XMLMessage getInstance() {
        return new EnterChatMessage();
    }
}
