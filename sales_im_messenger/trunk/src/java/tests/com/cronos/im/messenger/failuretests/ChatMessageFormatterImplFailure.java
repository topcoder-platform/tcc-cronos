/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.formatterimpl.ChatMessageFormatterImpl;
import com.topcoder.document.highlight.ContentHighlighter;
import com.topcoder.document.highlight.XmlTagFormatter;

/**
 * Tests the {@link ChatMessageFormatterImpl} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class ChatMessageFormatterImplFailure extends TestCase {

    /**
     * Represents the ChatMessageFormatterImpl.
     */
    private ChatMessageFormatterImpl chatMessageFormatterImpl;

    /**
     * Represents the Formatter.
     */
    private XmlTagFormatter formatter;

    /**
     * Represents the ContentHighlighter.
     */
    private ContentHighlighter contentHighlighter;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        formatter = new XmlTagFormatter("font");
        formatter.addAttribute("face", "Arial");
        contentHighlighter = new ContentHighlighter();
        chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, formatter, formatter, contentHighlighter,
                "pattern");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        chatMessageFormatterImpl = null;
    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageFormatterImpl</code> for null userNameFormatter.
     * </p>
     *
     */
    public void testChatMessageFormatterImpl() {
        try {
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(null, formatter, formatter, contentHighlighter,
                    "pattern");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageFormatterImpl</code> for null timeStampFormatter.
     * </p>
     *
     */
    public void testChatMessageFormatterImpl1() {
        try {
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, null, formatter, contentHighlighter,
                    "pattern");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageFormatterImpl</code> for null chatTextFormatter.
     * </p>
     *
     */
    public void testChatMessageFormatterImpl2() {
        try {
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, formatter, null, contentHighlighter,
                    "pattern");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageFormatterImpl</code> for null <code>ContentHighlighter</code>.
     * </p>
     *
     */
    public void testChatMessageFormatterImpl3() {
        try {
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, formatter, formatter, null, "pattern");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageFormatterImpl</code> for null pattern.
     * </p>
     *
     */
    public void testChatMessageFormatterImpl4() {
        try {
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, formatter, formatter,
                    contentHighlighter, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageFormatterImpl</code> for empty pattern.
     * </p>
     *
     */
    public void testChatMessageFormatterImpl5() {
        try {
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, formatter, formatter,
                    contentHighlighter, "   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Sets the user name as null.
     * </p>
     *
     */
    public void testFormat() {
        try {
            chatMessageFormatterImpl.format(null, "timestamp", "chatText");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }

    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Sets the user name as empty string.
     * </p>
     *
     */
    public void testFormat1() {
        try {
            chatMessageFormatterImpl.format("    ", "timestamp", "chatText");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }

    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Sets the timestamp as null.
     * </p>
     *
     */
    public void testFormat2() {
        try {
            chatMessageFormatterImpl.format("user", null, "chatText");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }

    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Sets the timestamp as empty string.
     * </p>
     *
     */
    public void testFormat3() {
        try {
            chatMessageFormatterImpl.format("user", "   ", "chatText");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }

    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Sets the chat text as null.
     * </p>
     *
     */
    public void testFormat4() {
        try {
            chatMessageFormatterImpl.format("user", "timestamp", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }

    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Sets the chat text as empty string.
     * </p>
     *
     */
    public void testFormat5() {
        try {
            chatMessageFormatterImpl.format("user", "timestamp", "   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (ChatMessageFormattingException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the format method of <code>ChatMessageFormatterImpl</code>.
     * </p>
     *
     * <p>
     * Checks for the handling of formatting exception.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testFormat6() throws Exception {
        try {
            XmlTagFormatter badFormatter = new XmlTagFormatter("font");
            badFormatter.addDynamicAttribute("face", "arial");
            chatMessageFormatterImpl = new ChatMessageFormatterImpl(formatter, badFormatter, formatter,
                    contentHighlighter, "[a-z]");
            chatMessageFormatterImpl.format("user", "timestamp", "chat Text");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            fail("Should throw ChatMessageFormattingException");
        } catch (ChatMessageFormattingException e) {
            // expect
        }
    }

}
