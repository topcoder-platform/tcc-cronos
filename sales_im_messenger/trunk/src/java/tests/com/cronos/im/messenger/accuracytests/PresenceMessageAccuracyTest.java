/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.PresenceMessage;
import com.cronos.im.messenger.XMLMessage;

import org.xml.sax.InputSource;

import java.io.StringReader;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;


/**
 * Tests the functionality for class <code>PresenceMessage</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class PresenceMessageAccuracyTest extends XMLMessageAccuracyTest {
    /** An instance of <code>PresenceMessage</code> for testing. */
    private PresenceMessage presenceMessage;

    /**
     * Sets up testing environment.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        presenceMessage = (PresenceMessage) xmlMessage;

        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});

        presenceMessage.setChatProfileProperties(properties);
        presenceMessage.setPresent(false);
    }

    /**
     * Test method for 'PresenceMessage.toXMLString(DateFormatContext)'
     *
     * @throws Exception to JUnit
     */
    public void testToXMLString() throws Exception {
        DocumentBuilder db = AccuracyHelper.getDocumentBuilder("PresenceMessage.xsd");
        String xmlStr = presenceMessage.toXMLString(dateFormatContext);
        db.parse(new InputSource(new StringReader(xmlStr)));
    }

    /**
     * Test method for 'PresenceMessage.PresenceMessage()'.
     */
    public void testPresenceMessage() {
        assertTrue("Test method for 'PresenceMessage.PresenceMessage()' failed.", presenceMessage instanceof XMLMessage);
    }

    /**
     * Test method for 'PresenceMessage.isPresent()'
     */
    public void testIsPresent() {
        AccuracyHelper.setPrivateField(PresenceMessage.class, presenceMessage, "present", Boolean.TRUE);

        assertTrue("Test method for 'PresenceMessage.setPresent(boolean)' failed.", presenceMessage.isPresent());
        AccuracyHelper.setPrivateField(PresenceMessage.class, presenceMessage, "present", Boolean.FALSE);

        assertFalse("Test method for 'PresenceMessage.setPresent(boolean)' failed.", presenceMessage.isPresent());
    }

    /**
     * Test method for 'PresenceMessage.setPresent(boolean)'.
     */
    public void testSetPresent() {
        presenceMessage.setPresent(true);

        boolean value = ((Boolean) AccuracyHelper.getPrivateField(PresenceMessage.class, presenceMessage, "present"))
            .booleanValue();
        assertTrue("Test method for 'PresenceMessage.setPresent(boolean)' failed.", value);
        presenceMessage.setPresent(false);
        value = ((Boolean) AccuracyHelper.getPrivateField(PresenceMessage.class, presenceMessage, "present"))
            .booleanValue();
        assertFalse("Test method for 'PresenceMessage.setPresent(boolean)' failed.", value);
    }

    /**
     * Test method for 'PresenceMessage.getChatProfileProperties()'
     */
    public void testGetChatProfileProperties() {
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});

        AccuracyHelper.setPrivateField(PresenceMessage.class, presenceMessage, "chatProfileProperties", properties);
        assertEquals("Test method for 'PresenceMessage.getChatProfileProperties(Map)' failed.", properties,
            presenceMessage.getChatProfileProperties());
    }

    /**
     * Test method for 'PresenceMessage.setChatProfileProperties(Map)'
     */
    public void testSetChatProfileProperties() {
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});
        presenceMessage.setChatProfileProperties(properties);

        Map value = (Map) AccuracyHelper.getPrivateField(PresenceMessage.class, presenceMessage, "chatProfileProperties");
        assertEquals("Test method for 'PresenceMessage.setAuthorProfileProperties(Map)' failed.", properties, value);
    }

    /**
     * Represents an abstract method which get a concrete implementation of <code>XMLMessage</code>.
     *
     * @return A concrete implementation of <code>XMLMessage</code> class.
     */
    protected XMLMessage getInstance() {
        return new PresenceMessage();
    }
}
