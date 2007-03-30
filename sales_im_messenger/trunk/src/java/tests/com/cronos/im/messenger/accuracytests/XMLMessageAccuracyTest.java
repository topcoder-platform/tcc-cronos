/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import java.util.Date;
import java.util.Locale;

import com.cronos.im.messenger.DateFormatContext;
import com.cronos.im.messenger.XMLMessage;

import com.topcoder.chat.message.pool.Message;

import junit.framework.TestCase;


/**
 * Accuracy test for class <code>XMLMessage</code>.
 *
 * @author lyt
 * @version 1.0
 */
public abstract class XMLMessageAccuracyTest extends TestCase {
    /** An instance of <code>XMLMessage</code> class for testing. */
    protected XMLMessage xmlMessage;

    /** An instance of <code>DateFormatContext</code> class for testing. */
    protected DateFormatContext dateFormatContext;

    /** Represents the attribute value for LOCALE_KEY used in tests. */
    private String localeStr = Locale.CHINA.toString();

    /** Represents the attribute value for TIMEZONE_KEY used in tests. */
    private String timeZoneID = "TimeZoneID";

    /** Represents the attribute value for DATE_FORMAT_KEY used in tests. */
    private String df = "hh:mm:ss a z";

    //DateFormat.getInstance().toString();

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        dateFormatContext = new DateFormatContext();
        dateFormatContext.addAttribute(DateFormatContext.DATE_FORMAT_KEY, df);
        dateFormatContext.addAttribute(DateFormatContext.TIMEZONE_KEY, timeZoneID);
        dateFormatContext.addAttribute(DateFormatContext.LOCALE_KEY, localeStr);

        xmlMessage = getInstance();

        xmlMessage.setSender(new Long(3265));
        xmlMessage.setTimestamp(new Date());

        xmlMessage.setAttribute("key1", "value1");
        xmlMessage.setAttribute("key2", "value2");

        xmlMessage.addRecipient(new Long(2436));
        xmlMessage.addRecipient(new Long(2437));
        xmlMessage.addRecipient(new Long(2438));

        xmlMessage.setChatSessionId(235231232);
    }

    /**
     * Test method for 'XMLMessage.XMLMessage()'.
     */
    public void testXMLMessage() {
        assertTrue("Test method for 'XMLMessage.XMLMessage()'failed.", xmlMessage instanceof Message);
    }

    /**
     * Test method for 'XMLMessage.getChatSessionId()'.
     */
    public void testGetChatSessionId() {
        AccuracyHelper.setPrivateField(XMLMessage.class, xmlMessage, "chatSessionId", new Long(3469));
        assertEquals("Test method for 'XMLMessage.getChatSessionId()' failed.", 3469, xmlMessage.getChatSessionId());
    }

    /**
     * Test method for 'XMLMessage.setChatSessionId(long)'.
     */
    public void testSetChatSessionId() {
        xmlMessage.setChatSessionId(3452);

        long value = ((Long) AccuracyHelper.getPrivateField(XMLMessage.class, xmlMessage, "chatSessionId")).longValue();
        assertEquals("Test method for 'XMLMessage.setChatSessionId(long)' failed.", 3452, value);
    }

    /**
     * Represents an abstract method which get a concrete implementation of <code>XMLMessage</code>.
     *
     * @return A concrete implementation of <code>XMLMessage</code> class.
     */
    protected abstract XMLMessage getInstance();
}
