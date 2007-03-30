/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.cronos.im.messenger.formatterimpl.ChatMessageFormatterImpl;
import com.topcoder.document.highlight.ContentHighlighter;
import com.topcoder.document.highlight.Matcher;
import com.topcoder.document.highlight.XmlTagFormatter;
import junit.framework.TestCase;

import java.util.regex.Pattern;

/**
 * Test the methods of <c>Helper</c> class for accuracy and failure.
 *
 * @author marius_neo
 * @version 1.0
 */
public class ChatMessageFormatterImplTestCase extends TestCase {
    /**
     * Represents the <c>userNameFormatter</c> used as parameter for
     * <c>ChatMessageFormatterImpl</c> class constructor. It is initialized
     * in <c>setUp()</c> method.
     */
    private XmlTagFormatter userNameFormatter;

    /**
     * Represents the <c>timestampFormatter</c> used as parameter for
     * <c>ChatMessageFormatterImpl</c> class constructor. It is initialized
     * in <c>setUp()</c> method.
     */
    private XmlTagFormatter timestampFormatter;

    /**
     * Represents the <c>chatTextFormatter</c> used as parameter for
     * <c>ChatMessageFormatterImpl</c> class constructor. It is initialized
     * in <c>setUp()</c> method.
     */
    private XmlTagFormatter chatTextFormatter;

    /**
     * Represents the <c>chl</c> used as parameter for
     * <c>ChatMessageFormatterImpl</c> class constructor. It is initialized
     * in <c>setUp()</c> method.
     */
    private ContentHighlighter chl;

    /**
     * Represents the <c>pattern</c> used as parameter for
     * <c>ChatMessageFormatterImpl</c> class constructor. It is initialized
     * in <c>setUp()</c> method.
     */
    private String pattern;

    /**
     * <c>ChatMessageFormatterImpl</c> instance used in tests. It is initialized
     * in <c>setUp()</c> method.
     */
    private ChatMessageFormatter cmf;

    /**
     * User name used in testing <c>format(String,String, String)</c> method.
     */
    private final String userName = "user";

    /**
     * Timestamp used in testing <c>format(String,String, String)</c> method.
     */
    private final String timestamp = "15/03/2007 13:15";

    /**
     * Chat text used in testing <c>format(String,String, String)</c> method.
     */
    private final String chatText = "Visit http://www.google.com/finance everyday and you will be very rich.";

    /**
     * Setup testing environment by setting value for all variables used in testing.
     */
    protected void setUp() {
        userNameFormatter = new XmlTagFormatter("font");
        userNameFormatter.addAttribute("face", "Arial");
        userNameFormatter.addAttribute("size", "5");
        userNameFormatter.addAttribute("color", "#2248");

        timestampFormatter = new XmlTagFormatter("font");
        timestampFormatter.addAttribute("face", "Arial");
        timestampFormatter.addAttribute("size", "10");

        pattern = "http://([^ ]+)";

        XmlTagFormatter dynamicChatTextFormatter = new XmlTagFormatter("a", Pattern.compile(pattern));
        dynamicChatTextFormatter.addDynamicAttribute("href", "http://$1");
        Matcher matcher = new Matcher();
        matcher.addFormatter(dynamicChatTextFormatter);
        chl = new ContentHighlighter();
        chl.addMatcher(matcher);

        chatTextFormatter = new XmlTagFormatter("font");
        chatTextFormatter.addAttribute("face", "Helvetica");
        chatTextFormatter.addAttribute("size", "12");

        cmf = new ChatMessageFormatterImpl(userNameFormatter
            , timestampFormatter, chatTextFormatter, chl, pattern);
    }

    /**
     * Tests the accuracy for the constructor of the class.
     */
    public void testChatMessageFormatterImpl() {
        assertNotNull("cmf was not created", cmf);
    }

    /**
     * Tests the failure for the constructor of the class when <c>userNameFormatter</c>
     * argument is null.
     */
    public void testChatMessageFormatterImplNullUserNameFormatter() {
        try {
            new ChatMessageFormatterImpl(null, timestampFormatter, chatTextFormatter, chl, pattern);

            fail("Should have thrown IllegalArgumentException because of null userNameFormatter");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor of the class when <c>timestampFormatter</c>
     * argument is null.
     */
    public void testChatMessageFormatterImplNullTimestampFormatter() {
        try {
            new ChatMessageFormatterImpl(userNameFormatter, null, chatTextFormatter, chl, pattern);

            fail("Should have thrown IllegalArgumentException because of null timestampFormatter");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor of the class when <c>userNameFormatter</c>
     * argument is null.
     */
    public void testChatMessageFormatterImplNullChatTextFormatter() {
        try {
            new ChatMessageFormatterImpl(userNameFormatter, timestampFormatter, null, chl, pattern);
            fail("Should have thrown IllegalArgumentException because of null chatTextFormatter");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor of the class when <c>chl</c>
     * argument is null.
     */
    public void testChatMessageFormatterImplNullContentHighlighter() {
        try {
            new ChatMessageFormatterImpl(userNameFormatter, timestampFormatter, chatTextFormatter, null, pattern);
            fail("Should have thrown IllegalArgumentException because of null chl");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor of the class when <c>pattern</c>
     * argument is null.
     */
    public void testChatMessageFormatterImplNullPattern() {
        try {
            new ChatMessageFormatterImpl(userNameFormatter, timestampFormatter, chatTextFormatter, chl, null);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor of the class when <c>pattern</c>
     * argument is an empty string.
     */
    public void testChatMessageFormatterImplEmptyPattern() {
        try {
            new ChatMessageFormatterImpl(userNameFormatter, timestampFormatter, chatTextFormatter, chl, "  ");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for <c>format(String, String, String)</c> method.
     *
     * @throws ChatMessageFormattingException Should not be thrown.
     */
    public void testFormatAccuracy() throws ChatMessageFormattingException {
        String result = cmf.format(userName, timestamp, chatText);
        String expectedResult = "[<font color=\"#2248\" size=\"5\" face=\"Arial\">user</font>]"
            + ":[<font size=\"10\" face=\"Arial\">15/03/2007 13:15</font>]"
            + ":[<font size=\"12\" face=\"Helvetica\">Visit"
            + " <a href=\"http://www.google.com/finance\">http://www.google.com/finance</a>"
            + " everyday and you will be very rich.</font>]";
        assertEquals("The results should be equal", expectedResult, result);
    }

    /**
     * Tests the failure for the method <c>format(String, String, String)</c> when
     * <c>userName</c> parameter is null or empty string.<c>IllegalArgumentException</c>
     * is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Should not be thrown.
     */
    public void testFormatInvalidUserName() throws ChatMessageFormattingException {
        try {
            cmf.format(null, timestamp, chatText);

            fail("Should have thrown IllegalArgumentException because of null user name");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            cmf.format(" ", timestamp, chatText);
            fail("Should have thrown IllegalArgumentException because of empty string user name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>format(String, String, String)</c> when
     * <c>timestamp</c> parameter is null or empty string.<c>IllegalArgumentException</c>
     * is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Should not be thrown
     */
    public void testFormatInvalidTimestamp() throws ChatMessageFormattingException {
        try {
            cmf.format(userName, null, chatText);
            fail("Should have thrown IllegalArgumentException because of null timestamp");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            cmf.format(userName, "  ", chatText);
            fail("Should have thrown IllegalArgumentException because of empty string timestamp");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>format(String, String, String)</c> when
     * <c>chatText</c> parameter is null or empty string.<c>IllegalArgumentException</c>
     * is expected to be thrown.
     *
     * @throws ChatMessageFormattingException Should not be thrown
     */
    public void testFormatInvalidChatText() throws ChatMessageFormattingException {
        try {
            cmf.format(userName, timestamp, null);
            fail("Should have thrown IllegalArgumentException because of null chatText");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            cmf.format(userName, timestamp, " ");
            fail("Should have thrown IllegalArgumentException because of empty string chat text");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }
}
