/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import com.cronos.im.messenger.AskForChatMessage;
import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.DateFormatContext;

/**
 * Tests the {@link AskForChatMessage} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class AskForChatMessageFailure extends TestCase {

    /**
     * Represents the AskForChatMessage.
     */
    private AskForChatMessage askForChatMessage;

    /**
     * Represents the DateFormatContext.
     */
    private DateFormatContext dateFormatContext;

    /**
     * Represents the author profile properties of AskChatMessage.
     */
    private Map authorProfileProperties;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        dateFormatContext = new DateFormatContext();
        askForChatMessage = new AskForChatMessage();
        authorProfileProperties = new HashMap();
        authorProfileProperties.put("key", new String[] {"values" });
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        askForChatMessage = null;
        dateFormatContext = null;
    }

    /**
     * <p>
     * Tests the setAuthorProfileProperties method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties as null.
     * </p>
     *
     */
    public void testAuthorProfileProperties() {
        try {
            askForChatMessage.setAuthorProfileProperties(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setAuthorProfileProperties method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties which contains null key.
     * </p>
     *
     */
    public void testAuthorProfileProperties1() {
        try {
            Map properties = new HashMap();
            properties.put(null, "ok");
            askForChatMessage.setAuthorProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setAuthorProfileProperties method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the author profile properties which contains null value.
     * </p>
     *
     */
    public void testAuthorProfileProperties2() {
        try {
            Map properties = new HashMap();
            properties.put("ok", null);
            askForChatMessage.setAuthorProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setAuthorProfileProperties method of <code>AskForChatMessage</code>.
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
            askForChatMessage.setAuthorProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setAuthorProfileProperties method of <code>AskForChatMessage</code>.
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
            askForChatMessage.setAuthorProfileProperties(properties);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setSessionCreationTime method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the session creation time as null.
     * </p>
     *
     */
    public void testSessionCreationTime() {
        try {
            askForChatMessage.setSessionCreationTime(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setAcknowledgeTime method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the session acknowledge time as null.
     * </p>
     *
     */
    public void testAcknowledgeTime() {
        try {
            askForChatMessage.setAcknowledgeTime(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setRequestUserId method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the RequestUserId as negative.
     * </p>
     *
     */
    public void testRequestUserId() {
        try {
            askForChatMessage.setRequestUserId(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the setRequestUserId method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the RequestUserId as zero.
     * </p>
     *
     */
    public void testRequestUserId1() {
        try {
            askForChatMessage.setRequestUserId(0);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the DateFormatContext as null.
     * </p>
     *
     */
    public void testToXMLString() {
        try {
            askForChatMessage.toXMLString(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the sender
     * </p>
     *
     */
    public void testToXMLString1() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getNullSenderXmlMessage(askForChatMessage);
            askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
            askForChatMessage.setAcknowledgeTime(new Date());
            askForChatMessage.setSessionCreationTime(new Date());
            askForChatMessage.setRequestUserId(2);
            askForChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the recipients
     * </p>
     *
     */
    public void testToXMLString2() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getNoRecipientsXmlMessage(askForChatMessage);
            askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
            askForChatMessage.setAcknowledgeTime(new Date());
            askForChatMessage.setSessionCreationTime(new Date());
            askForChatMessage.setRequestUserId(2);
            askForChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat session id
     * </p>
     *
     */
    public void testToXMLString3() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getNoChatSessionId(askForChatMessage);
            askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
            askForChatMessage.setAcknowledgeTime(new Date());
            askForChatMessage.setSessionCreationTime(new Date());
            askForChatMessage.setRequestUserId(2);
            askForChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the acknowldege time
     * </p>
     *
     */
    public void testToXMLString4() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getAllReqdSet(askForChatMessage);
            askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
            askForChatMessage.setSessionCreationTime(new Date());
            askForChatMessage.setRequestUserId(2);
            askForChatMessage.toXMLString(dateFormatContext);

            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the author profile properties
     * </p>
     *
     */
    public void testToXMLString5() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getAllReqdSet(askForChatMessage);
            askForChatMessage.setAcknowledgeTime(new Date());
            askForChatMessage.setSessionCreationTime(new Date());
            askForChatMessage.setRequestUserId(2);
            askForChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the session create time
     * </p>
     *
     */
    public void testToXMLString6() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getAllReqdSet(askForChatMessage);
            askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
            askForChatMessage.setAcknowledgeTime(new Date());
            askForChatMessage.setRequestUserId(2);
            askForChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>AskForChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the request id.
     * </p>
     *
     */
    public void testToXMLString7() {
        try {
            askForChatMessage = (AskForChatMessage) FailureTestHelper.getAllReqdSet(askForChatMessage);
            askForChatMessage.setAuthorProfileProperties(authorProfileProperties);
            askForChatMessage.setAcknowledgeTime(new Date());
            askForChatMessage.setSessionCreationTime(new Date());
            askForChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }
}
