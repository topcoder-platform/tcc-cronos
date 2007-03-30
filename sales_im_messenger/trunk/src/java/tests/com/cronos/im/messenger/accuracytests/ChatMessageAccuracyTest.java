/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.XMLMessage;

import org.xml.sax.InputSource;

import java.io.StringReader;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;


/**
 * Accuracy test for class <code>ChatMessage</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class ChatMessageAccuracyTest extends XMLMessageAccuracyTest {
    /** An instance of <code>XMLMessage</code> class for testing. */
    private ChatMessage chatMessage;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        
    	AccuracyHelper.clearConfig();
    	AccuracyHelper.loadConfig("formatter_loader.xml");
    	
        chatMessage = (ChatMessage) xmlMessage;

        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});
        chatMessage.setChatProfileProperties(properties);
        chatMessage.setChatText("Hi, Topcoder...");
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
    	AccuracyHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Test method for 'ChatMessage.toXMLString(DateFormatContext)'
     *
     * @throws Exception to JUnit
     */
    public void testToXMLString() throws Exception {
    
    	
        DocumentBuilder db = AccuracyHelper.getDocumentBuilder("ChatMessage.xsd");
        String xmlStr = chatMessage.toXMLString(dateFormatContext);
        db.parse(new InputSource(new StringReader(xmlStr)));
    }

    /**
     * Test method for 'ChatMessage.ChatMessage()'.
     */
    public void testChatMessage() {
        assertTrue("Test method for 'ChatMessage.ChatMessage()' failed.", chatMessage instanceof XMLMessage);
    }

    /**
     * Test method for 'ChatMessage.getChatProfileProperties()'
     */
    public void testGetChatProfileProperties() {
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});

        AccuracyHelper.setPrivateField(ChatMessage.class, chatMessage, "chatProfileProperties", properties);
        assertEquals("Test method for 'AskForChatMessage.getChatProfileProperties(Map)' failed.", properties,
            chatMessage.getChatProfileProperties());
    }

    /**
     * Test method for 'ChatMessage.setChatProfileProperties(Map)'
     */
    public void testSetChatProfileProperties() {
        Map properties = new HashMap();
        properties.put("Key1", new String[] {"Value1"});
        properties.put("Key2", new String[] {"Value1"});
        chatMessage.setChatProfileProperties(properties);

        Map value = (Map) AccuracyHelper.getPrivateField(ChatMessage.class, chatMessage, "chatProfileProperties");
        assertEquals("Test method for 'AskForChatMessage.setAuthorProfileProperties(Map)' failed.", properties, value);
    }

    /**
     * Test method for 'ChatMessage.getChatText()'
     */
    public void testGetChatText() {
        AccuracyHelper.setPrivateField(ChatMessage.class, chatMessage, "chatText", "some text");
        assertEquals("Test method for 'ChatMessage.getChatText()' failed.", "some text", chatMessage.getChatText());
    }

    /**
     * Test method for 'ChatMessage.setChatText(String)'.
     */
    public void testSetChatText() {
        chatMessage.setChatText("some text");

        String value = (String) AccuracyHelper.getPrivateField(ChatMessage.class, chatMessage, "chatText");
        assertEquals("Test method for 'ChatMessage.setChatText(String)' failed.", "some text", value);
    }

    /**
     * Represents an abstract method which get a concrete implementation of <code>XMLMessage</code>.
     *
     * @return A concrete implementation of <code>XMLMessage</code> class.
     */
    protected XMLMessage getInstance() {
        return new ChatMessage();
    }
}
