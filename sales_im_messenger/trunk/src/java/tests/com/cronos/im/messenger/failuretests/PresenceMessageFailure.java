/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.DateFormatContext;
import com.cronos.im.messenger.PresenceMessage;

/**
 * Tests the {@link PresenceMessage} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class PresenceMessageFailure extends TestCase{

    /**
     * Represents the PresenceMessage.
     */
    private PresenceMessage presenceMessage;

    /**
     * Represents the chat profile properties of AskChatMessage.
     */
    private Map chatProfileProperties;

    /**
     * Represents the DateFormatContext.
     */
    private DateFormatContext dateFormatContext;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        dateFormatContext = new DateFormatContext();
        presenceMessage = new PresenceMessage();
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
        dateFormatContext = null;
        presenceMessage = null;
    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Sets the chat profile properties as null.
     * </p>
     *
     */
    public void testChatProfileProperties() {
        try {
            presenceMessage.setChatProfileProperties(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Sets the chat profile properties which contains null key.
     * </p>
     *
     */
    public void testChatProfileProperties1() {
        try {
            Map properties = new HashMap();
            properties.put(null, "ok");
            presenceMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Sets the chat profile properties which contains null value.
     * </p>
     *
     */
    public void testChatProfileProperties2() {
        try {
            Map properties = new HashMap();
            properties.put("ok", null);
            presenceMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Sets the chat profile properties which contains empty key.
     * </p>
     *
     */
    public void testChatProfileProperties3() {
        try {
            Map properties = new Properties();
            properties.put("   ", "ok");
            presenceMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the setChatProfileProperties method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Sets the Chat profile properties which contains empty value.
     * </p>
     *
     */
    public void testChatProfileProperties4() {
        try {
            Map properties = new Properties();
            properties.put("ok", "   ");
            presenceMessage.setChatProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Sets the DateFormatContext as null.
     * </p>
     *
     */
    public void testToXMLString() {
        try {
            presenceMessage.toXMLString(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the sender
     * </p>
     *
     */
    public void testToXMLString1() {
        try {
            presenceMessage = (PresenceMessage) FailureTestHelper.getNullSenderXmlMessage(presenceMessage);
            presenceMessage.setChatProfileProperties(chatProfileProperties);
            presenceMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the recipients
     * </p>
     *
     */
    public void testToXMLString2() {
        try {
            presenceMessage = (PresenceMessage) FailureTestHelper.getNoRecipientsXmlMessage(presenceMessage);
            presenceMessage.setChatProfileProperties(chatProfileProperties);
            presenceMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat session id
     * </p>
     *
     */
    public void testToXMLString3() {
        try {
            presenceMessage = (PresenceMessage) FailureTestHelper.getNoChatSessionId(presenceMessage);
            presenceMessage.setChatProfileProperties(chatProfileProperties);
            presenceMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>PresenceMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the author profile properties
     * </p>
     *
     */
    public void testToXMLString5() {
        try {
            presenceMessage = (PresenceMessage) FailureTestHelper.getAllReqdSet(presenceMessage);
            presenceMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }
}
