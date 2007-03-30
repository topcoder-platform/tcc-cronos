/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests.formatterimpl;

import com.cronos.im.messenger.ChatMessageFormatter;
import com.cronos.im.messenger.formatterimpl.ChatMessageFormatterImpl;
import com.topcoder.document.highlight.ContentHighlighter;
import com.topcoder.document.highlight.Matcher;
import com.topcoder.document.highlight.XmlTagFormatter;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


/**
 * Tests the functionality for class <code>ChatMessageFormatterImplAccuracyTest.java</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class ChatMessageFormatterImplAccuracyTest extends TestCase {
    /**
     * An instance of <code>ChatMessageFormatterImpl</code> for testing.
     */
    private ChatMessageFormatter chatMessageFormatter;

    /**
     * Setup testing environment by setting value for all variables used in testing.
     */
    protected void setUp() {
        String pattern = "http://([^ ]+)";

        XmlTagFormatter unf = new XmlTagFormatter("face");
        unf.addAttribute("face", "Arial");
        unf.addAttribute("size", "5");
        unf.addAttribute("color", "#2248");

        XmlTagFormatter tsf = new XmlTagFormatter("face");
        tsf.addAttribute("face", "Arial");
        tsf.addAttribute("size", "10");
        tsf.addAttribute("color", "#2248");

        XmlTagFormatter ctf = new XmlTagFormatter("font");
        ctf.addAttribute("face", "Helvetica");
        ctf.addAttribute("size", "12");

        XmlTagFormatter dctf = new XmlTagFormatter("a", Pattern.compile(pattern));
        dctf.addDynamicAttribute("href", "http://$1");

        Matcher matcher = new Matcher();
        matcher.addFormatter(dctf);

        ContentHighlighter chl = new ContentHighlighter();
        chl.addMatcher(matcher);

        chatMessageFormatter = new ChatMessageFormatterImpl(unf, tsf, ctf, chl, pattern);
    }

    /**
     * Test method for 'ChatMessageFormatterImpl(Formatter, Formatter, Formatter, ContentHighlighter, String)'
     */
    public void testChatMessageFormatterImpl() {
        assertNotNull("Test method for 'ChatMessageFormatterImpl(Formatter, Formatter, Formatter, ContentHighlighter, String)' failed.",
            chatMessageFormatter);
    }

    /**
     * Test method for 'ChatMessageFormatterImpl.format(String, String, String)'.
     *
     * @throws Exception to JUnit
     */
    public void testFormat() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(
            new FileInputStream(
                new File("test_files" + File.separator + "Accuracy" + File.separator + "format_result"))));

        String expected = br.readLine();
        br.close();

        String formatText = chatMessageFormatter.format("Topcoder", "21/03/2007 10:15", "hi, everyone");
        assertEquals("Test method for 'ChatMessageFormatterImpl.format(String, String, String)' failed.", expected,
            formatText);
    }
}
