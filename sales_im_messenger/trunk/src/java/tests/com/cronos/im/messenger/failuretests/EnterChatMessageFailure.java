/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.DateFormatContext;
import com.cronos.im.messenger.EnterChatMessage;

/**
 * Tests the {@link EnterChatMessage} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class EnterChatMessageFailure extends TestCase {

    /**
     * Represents the EnterChatMessage.
     */
    private EnterChatMessage enterChatMessage;

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
        enterChatMessage = new EnterChatMessage();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        enterChatMessage = null;
        dateFormatContext = null;
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>EnterChatMessage</code>.
     * </p>
     *
     * <p>
     * Sets the DateFormatContext as null.
     * </p>
     *
     */
    public void testToXMLString() {
        try {
            enterChatMessage.toXMLString(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>EnterChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the sender
     * </p>
     *
     */
    public void testToXMLString1() {
        try {
            enterChatMessage = (EnterChatMessage) FailureTestHelper.getNullSenderXmlMessage(enterChatMessage);
            enterChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>EnterChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the recipients
     * </p>
     *
     */
    public void testToXMLString2() {
        try {
            enterChatMessage = (EnterChatMessage) FailureTestHelper.getNoRecipientsXmlMessage(enterChatMessage);
            enterChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>EnterChatMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat session id
     * </p>
     *
     */
    public void testToXMLString3() {
        try {
            enterChatMessage = (EnterChatMessage) FailureTestHelper.getNoChatSessionId(enterChatMessage);
            enterChatMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }
}
