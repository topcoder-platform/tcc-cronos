/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.SessionUnavailableMessage;
import com.cronos.im.messenger.XMLMessage;

import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;


/**
 * Tests the functionality for class <code>SessionUnavailableMessage</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class SessionUnavailableMessageAccuracyTest extends XMLMessageAccuracyTest {
    /** An instance of <code>SessionUnavailableMessage</code> class for testing. */
    private SessionUnavailableMessage sessionUnavailableMessage;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        sessionUnavailableMessage = (SessionUnavailableMessage) xmlMessage;
    }

    /**
     * Test method for 'SessionUnavailableMessage.toXMLString(DateFormatContext)'.
     *
     * @throws Exception to JUnit
     */
    public void testToXMLString() throws Exception {
        DocumentBuilder db = AccuracyHelper.getDocumentBuilder("SessionUnavailableMessage.xsd");
        String xmlStr = sessionUnavailableMessage.toXMLString(dateFormatContext);
        db.parse(new InputSource(new StringReader(xmlStr)));
    }

    /**
     * Test method for 'SessionUnavailableMessage.SessionUnavailableMessage()'
     */
    public void testSessionUnavailableMessage() {
        assertTrue("Test method for 'SessionUnavailableMessage.toXMLString(DateFormatContext)' failed.",
            sessionUnavailableMessage instanceof XMLMessage);
    }

    /**
     * Represents an abstract method which get a concrete implementation of <code>XMLMessage</code>.
     *
     * @return A concrete implementation of <code>XMLMessage</code> class.
     */
    protected XMLMessage getInstance() {
        return new SessionUnavailableMessage();
    }
}
