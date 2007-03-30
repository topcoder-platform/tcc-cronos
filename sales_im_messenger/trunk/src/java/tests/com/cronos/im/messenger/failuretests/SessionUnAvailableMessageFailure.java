/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.DateFormatContext;
import com.cronos.im.messenger.SessionUnavailableMessage;

/**
 * Tests the {@link SessionUnavailableMessage} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class SessionUnAvailableMessageFailure extends TestCase {

    /**
     * Represents the SessionUnavailableMessage.
     */
    private SessionUnavailableMessage sessionUnavailableMessage;

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
        sessionUnavailableMessage = new SessionUnavailableMessage();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        sessionUnavailableMessage = null;
        dateFormatContext = null;
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>SessionUnavailableMessage</code>.
     * </p>
     *
     * <p>
     * Sets the DateFormatContext as null.
     * </p>
     *
     */
    public void testToXMLString() {
        try {
            sessionUnavailableMessage.toXMLString(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>SessionUnavailableMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the sender
     * </p>
     *
     */
    public void testToXMLString1() {
        try {
            sessionUnavailableMessage = (SessionUnavailableMessage) FailureTestHelper.
                getNullSenderXmlMessage(sessionUnavailableMessage);
            sessionUnavailableMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>SessionUnavailableMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the recipients
     * </p>
     *
     */
    public void testToXMLString2() {
        try {
            sessionUnavailableMessage = (SessionUnavailableMessage) FailureTestHelper.
                getNoRecipientsXmlMessage(sessionUnavailableMessage);
            sessionUnavailableMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

    /**
     * <p>
     * Tests the toXMLString method of <code>SessionUnavailableMessage</code>.
     * </p>
     *
     * <p>
     * Tests the method without setting the chat session id
     * </p>
     *
     */
    public void testToXMLString3() {
        try {
            sessionUnavailableMessage = (SessionUnavailableMessage) FailureTestHelper.
                getNoChatSessionId(sessionUnavailableMessage);
            sessionUnavailableMessage.toXMLString(dateFormatContext);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalStateException");
        }
    }

}
