/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.AskForChatMessage;
import com.cronos.im.messenger.XMLMessage;

import org.xml.sax.InputSource;

import java.io.StringReader;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;


/**
 * Accuracy test for class <code>AskForChatMessage</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class AskForChatMessageAccuracyTest extends XMLMessageAccuracyTest {
    /** An instance of <code>AskForChatMessage</code> class for testing. */
    private AskForChatMessage askForChatMessage;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        askForChatMessage = (AskForChatMessage) xmlMessage;
        askForChatMessage.setSessionCreationTime(new Date());
        askForChatMessage.setAcknowledgeTime(new Date());
        
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});

        askForChatMessage.setAuthorProfileProperties(properties);
        askForChatMessage.setRequestUserId(23545);
    }

    /**
     * Represents an abstract method which get a concrete implementation of <code>XMLMessage</code>.
     *
     * @return A concrete implementation of <code>XMLMessage</code> class.
     */
    protected XMLMessage getInstance() {
        return new AskForChatMessage();
    }

    /**
     * Test method for 'AskForChatMessage.toXMLString(DateFormatContext)'
     *
     * @throws Exception to JUnit
     */
    public void testToXMLString() throws Exception {
        DocumentBuilder builder = AccuracyHelper.getDocumentBuilder("AskForChatMessage.xsd");
        String xml = askForChatMessage.toXMLString(dateFormatContext);
        builder.parse(new InputSource(new StringReader(xml)));
    }

    /**
     * Test method for 'AskForChatMessage.AskForChatMessage()'.
     */
    public void testAskForChatMessage() {
        assertTrue("Test method for 'AskForChatMessage.AskForChatMessage()' failed.",
            askForChatMessage instanceof XMLMessage);
    }

    /**
     * Test method for 'AskForChatMessage.getAuthorProfileProperties()'
     */
    public void testGetAuthorProfileProperties() {
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});

        AccuracyHelper.setPrivateField(AskForChatMessage.class, askForChatMessage, "authorProfileProperties", properties);
        assertEquals("Test method for 'AskForChatMessage.setAuthorProfileProperties(Map)' failed.", properties,
            askForChatMessage.getAuthorProfileProperties());
    }

    /**
     * Test method for 'AskForChatMessage.setAuthorProfileProperties(Map)'
     */
    public void testSetAuthorProfileProperties() {
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});

        askForChatMessage.setAuthorProfileProperties(properties);

        Map value = (Map) AccuracyHelper.getPrivateField(AskForChatMessage.class, askForChatMessage,
                "authorProfileProperties");
        assertEquals("Test method for 'AskForChatMessage.setAuthorProfileProperties(Map)' failed.", properties, value);
    }

    /**
     * Test method for 'AskForChatMessage.getSessionCreationTime()'
     */
    public void testGetSessionCreationTime() {
        Date time = new Date();
        AccuracyHelper.setPrivateField(AskForChatMessage.class, askForChatMessage, "sessionCreationTime", time);
        assertEquals("Test method for 'AskForChatMessage.getSessionCreationTime()' failed.", time,
            askForChatMessage.getSessionCreationTime());
    }

    /**
     * Test method for 'AskForChatMessage.setSessionCreationTime(Date)'
     */
    public void testSetSessionCreationTime() {
        Date time = new Date();
        askForChatMessage.setSessionCreationTime(time);

        Date value = (Date) AccuracyHelper.getPrivateField(AskForChatMessage.class, askForChatMessage,
                "sessionCreationTime");
        assertEquals("Test method for 'AskForChatMessage.setSessionCreationTime(Date)' failed.", time, value);
    }

    /**
     * Test method for 'AskForChatMessage.getAcknowledgeTime()'.
     */
    public void testGetAcknowledgeTime() {
        Date time = new Date();
        AccuracyHelper.setPrivateField(AskForChatMessage.class, askForChatMessage, "acknowledgeTime", time);
        assertEquals("Test method for 'AskForChatMessage.getAcknowledgeTime()' failed.", time,
            askForChatMessage.getAcknowledgeTime());
    }

    /**
     * Test method for 'AskForChatMessage.setAcknowledgeTime(Date)'.
     */
    public void testSetAcknowledgeTime() {
        Date time = new Date();
        askForChatMessage.setAcknowledgeTime(time);

        Date value = (Date) AccuracyHelper.getPrivateField(AskForChatMessage.class, askForChatMessage, "acknowledgeTime");
        assertEquals("Test method for 'AskForChatMessage.setAcknowledgeTime(Date)' failed.", time, value);
    }

    /**
     * Test method for 'AskForChatMessage.getRequestUserId()'.
     */
    public void testGetRequestUserId() {
        long id = 233351;
        AccuracyHelper.setPrivateField(AskForChatMessage.class, askForChatMessage, "requestUserId", new Long(id));
        assertEquals("Test method for 'AskForChatMessage.getRequestUserId()' failed.", id,
            askForChatMessage.getRequestUserId());
    }

    /**
     * Test method for 'AskForChatMessage.setRequestUserId(long)'.
     */
    public void testSetRequestUserId() {
        long id = 23521;
        askForChatMessage.setRequestUserId(id);

        long value = ((Long) AccuracyHelper.getPrivateField(AskForChatMessage.class, askForChatMessage, "requestUserId"))
            .longValue();
        assertEquals("Test method for 'AskForChatMessage.setRequestUserId(long)' failed.", id, value);
    }
}
