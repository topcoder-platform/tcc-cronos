/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.DateFormatContext;

/**
 * Tests the {@link ChatMessage} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class ChatMessageFailure extends TestCase {

    /**
     * Represents the ChatMessage.
     */
    private ChatMessage chatMessage;

    /**
     * Represents the DateFormatContext.
     */
    private DateFormatContext dateFormatContext;

    /**
     * Represents the chat profile properties of AskChatMessage.
     */
    private Map chatProfileProperties;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        chatMessage = new ChatMessage();
        dateFormatContext = new DateFormatContext();
        chatProfileProperties = new HashMap();
        chatProfileProperties.put("key", new String[] {"values" });
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        chatMessage = null;
        dateFormatContext = null;
    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties as null.
     * </p>
     *
     */
    public void testAuthorProfileProperties() {
        try {
            chatMessage.setChatProfileProperties(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties which contains null key.
     * </p>
     *
     */
    public void testChatProfileProperties1() {
        try {
            Map properties = new HashMap();
            properties.put(null, "ok");
            chatMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties which contains null value.
     * </p>
     *
     */
    public void testChatProfileProperties2() {
        try {
            Map properties = new HashMap();
            properties.put("ok", null);
            chatMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties which contains empty key.
     * </p>
     *
     */
    public void testAuthorProfileProperties3() {
        try {
            Map properties = new Properties();
            properties.put("   ", "ok");
            chatMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties which contains empty value.
     * </p>
     *
     */
    public void testAuthorProfileProperties4() {
        try {
            Map properties = new Properties();
            properties.put("ok", "   ");
            chatMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setChatText method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the chat text as null.
     * </p>
     *
     */
    public void testChatText() {
        try {
            chatMessage.setChatText(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the DateFormatContext as null.
     * </p>
     *
     */
    public void testToXMLString() {
        try {
            chatMessage.toXMLString(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the sender
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString1() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config.xml");
            chatMessage = (ChatMessage) FailureTestHelper.getNullSenderXmlMessage(chatMessage);
            chatMessage.setChatProfileProperties(chatProfileProperties);
            chatMessage.setChatText("sample");
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the recipients
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString2() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config.xml");
            chatMessage = (ChatMessage) FailureTestHelper.getNoRecipientsXmlMessage(chatMessage);
            chatMessage.setChatProfileProperties(chatProfileProperties);
            chatMessage.setChatText("sample");
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat session id
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString3() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config.xml");
            chatMessage = (ChatMessage) FailureTestHelper.getNoRecipientsXmlMessage(chatMessage);
            chatMessage.setChatProfileProperties(chatProfileProperties);
            chatMessage.setChatText("sample");
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat session id
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString4() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config.xml");
            chatMessage = (ChatMessage) FailureTestHelper.getNoChatSessionId(chatMessage);
            chatMessage.setChatProfileProperties(chatProfileProperties);
            chatMessage.setChatText("sample");
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat profile properties
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString5() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config.xml");
            chatMessage = (ChatMessage) FailureTestHelper.getAllReqdSet(chatMessage);
            chatMessage.setChatText("sample");
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat text
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString6() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config.xml");
            chatMessage = (ChatMessage) FailureTestHelper.getAllReqdSet(chatMessage);
            chatMessage.setChatProfileProperties(chatProfileProperties);
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>ChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without loading the properties reqd for chat dynamic text
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testToXMLString7() throws Exception {
        try {
            chatMessage = (ChatMessage) FailureTestHelper.getAllReqdSet(chatMessage);
            chatMessage.setChatProfileProperties(chatProfileProperties);
            chatMessage.setChatText("sample");
            chatMessage.toXMLString(dateFormatContext);
            fail("Should throw ChatMessageFormattingException");
        } catch (IllegalStateException e) {
            fail("Should throw ChatMessageFormattingException");
        } catch (ChatMessageFormattingException e) {
            // expect
        }
    }
}
