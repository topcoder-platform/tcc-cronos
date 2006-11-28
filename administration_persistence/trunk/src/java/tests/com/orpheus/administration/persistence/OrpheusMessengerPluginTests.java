/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.persistence.impl.AdminMessage;
import com.orpheus.administration.persistence.impl.RSSItemTranslator;

import com.topcoder.message.messenger.MessageException;

import java.util.Date;

/**
 * Unit tests for the <code>OrpheusMessengerPlugin</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class OrpheusMessengerPluginTests extends MessageTestBase {
    /**
     * Returns an instance of <code>OrpheusMessengerPlugin</code> to use for the test.
     *
     * @return an instance of <code>OrpheusMessengerPlugin</code> to use for the test
     * @throws Exception if an unexpected exception occurs
     */
    abstract OrpheusMessengerPlugin getInstance() throws Exception;

    /**
     * Tests that <code>sendMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_sendMessage_null() throws Exception {
        try {
            getInstance().sendMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>sendMessage</code> throws <code>MessageException</code> when passed an invalid message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_sendMessage_invalid() throws Exception {
        AdminMessage message = new AdminMessage();
        try {
            getInstance().sendMessage(message);
            fail("should have thrown MessageException");
        } catch (MessageException ex) {
            // pass
        }
    }

    /**
     * Tests the successful operation of <code>sendMessage</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_sendMessage() throws Exception {
        AdminMessage message = createMessage();
        getInstance().sendMessage(message);
        assertTrue("the message should be in storage",
                   messageExists("guid", "category", "content type", "content",
                                 (Date) message.getParameterValue(AdminMessage.TIMESTAMP)));
    }

    /**
     * Tests the <code>getMessageType</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getMessageType() throws Exception {
        assertEquals("getMessageType() should return AdminMessage.class",
                     AdminMessage.class, getInstance().getMessageType());
    }

    /**
     * Tests the <code>ejbSendMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbSendMessage() throws Exception {
        AdminMessage message = createMessage();
        getInstance().ejbSendMessage(new RSSItemTranslator().assembleMessageDTO(message));
        assertTrue("the message should be in storage",
                   messageExists("guid", "category", "content type", "content",
                                 (Date) message.getParameterValue(AdminMessage.TIMESTAMP)));
    }

    /**
     * Returns a fully-formed <code>AdminMessage</code>.
     *
     * @return a fully-formed <code>AdminMessage</code>
     */
    private static AdminMessage createMessage() {
        AdminMessage message = new AdminMessage();
        message.setParameterValue(AdminMessage.GUID, "guid");
        message.setParameterValue(AdminMessage.CATEGORY, "category");
        message.setParameterValue(AdminMessage.CONTENT_TYPE, "content type");
        message.setParameterValue(AdminMessage.CONTENT, "content");
        message.setParameterValue(AdminMessage.TIMESTAMP, new Date());
        return message;
    }
}
