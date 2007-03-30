/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.stresstests;

import java.util.regex.Pattern;

import com.cronos.im.messenger.ChatMessageFormatter;
import com.cronos.im.messenger.formatterimpl.ChatMessageFormatterImpl;
import com.topcoder.document.highlight.ContentHighlighter;
import com.topcoder.document.highlight.Matcher;
import com.topcoder.document.highlight.XmlTagFormatter;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test for <code>{@link ChatMessageFormatterImpl}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class ChatMessageFormatterImplStressTests extends TestCase {

    /**
     * <p>
     * Represents the <code>{@link ChatMessageFormatterImpl}</code> instance.
     * </p>
     */
    private ChatMessageFormatter chatMessageFormatter;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     */
    protected void setUp() throws Exception {
        super.setUp();

        XmlTagFormatter userNameFormatter = new XmlTagFormatter("font");
        userNameFormatter.addAttribute("face", "Arial");
        userNameFormatter.addAttribute("size", "6");
        userNameFormatter.addAttribute("color", "#2248");

        XmlTagFormatter timestampFormatter = new XmlTagFormatter("font");
        timestampFormatter.addAttribute("face", "Times New Roman");
        timestampFormatter.addAttribute("size", "8");

        String pattern = "http://([^ ]+)";

        XmlTagFormatter dynamicChatTextFormatter = new XmlTagFormatter("a", Pattern.compile(pattern));
        dynamicChatTextFormatter.addDynamicAttribute("href", "http://$1");
        Matcher matcher = new Matcher();
        matcher.addFormatter(dynamicChatTextFormatter);
        ContentHighlighter chl = new ContentHighlighter();
        chl.addMatcher(matcher);

        XmlTagFormatter chatTextFormatter = new XmlTagFormatter("font");
        chatTextFormatter.addAttribute("face", "Helvetica");
        chatTextFormatter.addAttribute("size", "10");

        chatMessageFormatter = new ChatMessageFormatterImpl(userNameFormatter, timestampFormatter, chatTextFormatter,
            chl, pattern);
    }

    /**
     * <p>
     * Stress test for <code>{@link ChatMessageFormatterImpl#format(String, String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFormat1000Times() throws Exception {
        String username = "Ivern";
        String timestamp = "2007-3-18 17:32";
        String chatText = "If you've created an article that you would like published on http://www.topcoder.com";
        String expectedResult = "[<font color=\"#2248\" size=\"6\" face=\"Arial\">"
            + username
            + "</font>]:[<font size=\"8\" face=\"Times New Roman\">"
            + timestamp
            + "</font>]:[<font size=\"10\" face=\"Helvetica\">"
            + "If you've created an article that you would like published on <a href=\"http://www.topcoder.com\">http://www.topcoder.com</a>"
            + "</font>]";
        int count = 10000;

        long start = System.currentTimeMillis();
        String[] results = new String[count];

        for (int i = 0; i < count; i++) {
            results[i] = chatMessageFormatter.format(username, timestamp, chatText);
        }

        long end = System.currentTimeMillis();

        // verify the result.
        for (int i = 0; i < count; i++) {
            assertEquals("the result is unexpected.", expectedResult, results[i]);
        }

        System.out.println("Run ChatMessageFormatterImpl#format " + count + " times cost " + (end - start) + "ms.");
    }
}
