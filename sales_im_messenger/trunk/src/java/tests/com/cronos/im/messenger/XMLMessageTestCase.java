/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Abstract class inherited by all classes which are testing the methods of classes
 * inheriting <c>XMLMessage</c>. The concrete instance of tested class is obtained
 * via template method <c>getXMLMessage()</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public abstract class XMLMessageTestCase extends TestCase {
    /**
     * Represents a concrete instance of <c>XMLMessage</c> class which is retrieved
     * with the template method <c>getXMLMessage()</c> and initialized in
     * <c>setUp()</c> method.
     */
    protected XMLMessage xmlMessage;

    /**
     * Represents the context used in testing <c>toXMLString(DateFormatContext)</c> methods
     * in the classes which inherit this class. It is initialized in <c>setUp()</c> method.
     */
    protected DateFormatContext context;

    /**
     * Represents the sender of the message. It is initialized and assigned to the <c>enterChatMessage</c>
     * in <c>setUp()</c> method.
     */
    protected Long sender;

    /**
     * Represents the timestamp of the message. It is initialized and assigned to the <c>enterChatMessage</c>
     * in <c>setUp()</c> method.
     */
    protected Date timestamp;

    /**
     * Represents the attributes of the message. It is initialized and assigned to the <c>enterChatMessage</c>
     * in <c>setUp()</c> method.
     */
    protected Map attributes;

    /**
     * Represents the recipients of the message. It is initialized and assigned to the <c>enterChatMessage</c>
     * in <c>setUp()</c> method.
     */
    protected Long[] recipients;

    /**
     * Represents the chat session id of the message. It is initialized and assigned to the <c>enterChatMessage</c>
     * in <c>setUp()</c> method.
     */
    protected long chatSessionId;

    /**
     * Represents a template method which is to be implemented by subclasses of this
     * testcase in order to retrieve a concrete implementation of <c>XMLMessage</c>.
     *
     * @return A concrete implementation of <c>XMLMessage</c> class.
     */
    protected abstract XMLMessage getXMLMessage();

    /**
     * Initializes testing environment by setting <c>xmlMessage</c> field.
     *
     * @throws Exception propagated to JUnit.
     */
    protected void setUp() throws Exception {
        xmlMessage = getXMLMessage();

        sender = new Long(123);
        timestamp = new Date();
        attributes = new HashMap();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");
        chatSessionId = 1234;
        recipients = new Long[]{new Long(123), new Long(124), new Long(125)};

        // Set the required fields on the message for calling without
        // problems the method <c>toXMLString(DateFormatContext)</c>.
        xmlMessage.setSender(sender);
        xmlMessage.setTimestamp(timestamp);
        for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            xmlMessage.setAttribute((String) entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < recipients.length; i++) {
            xmlMessage.addRecipient(recipients[i]);
        }
        xmlMessage.setChatSessionId(chatSessionId);

        // Set up context field.
        String localeString = Locale.US.toString();
        String timeZoneID = TimeZone.getTimeZone("America/Los_Angeles").getID();
        String dateFormat = "hh:mm:ss a z";

        // Add attributes to the context
        context = new DateFormatContext();
        context.addAttribute(DateFormatContext.DATE_FORMAT_KEY, dateFormat);
        context.addAttribute(DateFormatContext.TIMEZONE_KEY, timeZoneID);
        context.addAttribute(DateFormatContext.LOCALE_KEY, localeString);

    }

    /**
     * Tests the accuracy for the set/getChatSessionId methods.
     */
    public void testSetGetChatSessionId() {
        long chatSessionId = 1234;
        xmlMessage.setChatSessionId(chatSessionId);
        assertEquals("chat session ids should be equal", chatSessionId, xmlMessage.getChatSessionId());
    }

    /**
     * Tests for failure the method <c>toXMLString(DateFormatContext)</c> when the parameter
     * of the method is null. <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Shouldn't be thrown.
     */
    public void testToXMLStringNullContext() throws ChatMessageFormattingException {
        try {
            xmlMessage.toXMLString(null);

            fail("Should have thrown IllegalArgumentException because of null context");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests for failure the method <c>toXMLString(DateFormatContext)</c> when the sender
     * of the message is not set. <c>IllegalStateException</c> is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Shouldn't be thrown.
     */
    public void testToXMLStringNoSender() throws ChatMessageFormattingException {
        xmlMessage = getXMLMessage();

        // Set the required fields, except the sender, on the message for calling without
        // problems the method <c>toXMLString(DateFormatContext)</c>.
        xmlMessage.setTimestamp(timestamp);
        for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            xmlMessage.setAttribute((String) entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < recipients.length; i++) {
            xmlMessage.addRecipient(recipients[i]);
        }
        xmlMessage.setChatSessionId(chatSessionId);

        try {
            xmlMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because of missing sender");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure the method <c>toXMLString(DateFormatContext)</c> when the sender
     * of the message is not set. <c>IllegalStateException</c> is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Shouldn't be thrown.
     */
    public void testToXMLStringNoRecipients() throws ChatMessageFormattingException {
        xmlMessage.removeAllRecipients();
        try {
            xmlMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because of missing recipients");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure the method <c>toXMLString(DateFormatContext)</c> when the chat session id
     * of the message is not set. <c>IllegalStateException</c> is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Shouldn't be thrown.
     */
    public void testToXMLStringNoChatSessionId() throws ChatMessageFormattingException {
        xmlMessage = getXMLMessage();

        // Set the required fields, except the chat session id, on the message for calling without
        // problems the method <c>toXMLString(DateFormatContext)</c>.
        xmlMessage.setTimestamp(timestamp);
        xmlMessage.setSender(sender);
        for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            xmlMessage.setAttribute((String) entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < recipients.length; i++) {
            xmlMessage.addRecipient(recipients[i]);
        }

        try {
            xmlMessage.toXMLString(context);

            fail("Should have thrown IllegalStateException because of missing chat session id");
        } catch (IllegalStateException e) {
            // Success.
        }
    }

    /**
     * Tests for failure the method <c>toXMLString(DateFormatContext)</c> when context passed to the
     * method is invalid (contains an invalid date format).
     * <c>ChatMessageFormattingException</c> is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Shouldn't be thrown.
     */
    public void testToXMLStringInvalidContext() throws ChatMessageFormattingException {
        // Set up context field.
        String pattern = "hh:mm:ss invalid context";

        // Add attributes to the context
        DateFormatContext context = new DateFormatContext();
        context.addAttribute(DateFormatContext.DATE_FORMAT_KEY, pattern);

        try {
            xmlMessage.toXMLString(context);

            fail("Should have thrown ChatMessageFormattingException because of invalid date format in the context");
        } catch (ChatMessageFormattingException e) {
            // Success.
        }
    }

}
