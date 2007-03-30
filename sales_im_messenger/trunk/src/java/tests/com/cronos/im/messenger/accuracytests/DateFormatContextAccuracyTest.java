/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.DateFormatContext;

import junit.framework.TestCase;

import java.text.DateFormat;

import java.util.Locale;
import java.util.Map;


/**
 * Accuracy test for class <code>DateFormatContext</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class DateFormatContextAccuracyTest extends TestCase {
    /** An instance of <code>DateFormatContext</code> class for testing. */
    private DateFormatContext dateFormatContext;

    /** Represents the attribute value for LOCALE_KEY used in tests. */
    private String localeStr = Locale.CHINA.toString();

    /** Represents the attribute value for TIMEZONE_KEY used in tests. */
    private String timeZoneID = "TimeZoneID";

    /** Represents the attribute value for DATE_FORMAT_KEY used in tests. */
    private String df = DateFormat.getInstance().toString();

    /**
     * Sets up testing environment.
     */
    protected void setUp() {
        dateFormatContext = new DateFormatContext();
        dateFormatContext.addAttribute(DateFormatContext.DATE_FORMAT_KEY, df);
        dateFormatContext.addAttribute(DateFormatContext.TIMEZONE_KEY, timeZoneID);
        dateFormatContext.addAttribute(DateFormatContext.LOCALE_KEY, localeStr);
    }

    /**
     * Test method for 'DateFormatContext.DateFormatContext()'.
     */
    public void testDateFormatContext() {
        assertNotNull("Test method for 'DateFormatContext.DateFormatContext()' failed.", dateFormatContext);
    }

    /**
     * Tests the accuracy for <code>DateFormatContext.getAttributes()</code> method.
     */
    public void testGetAttributes() {
        Map attributes = dateFormatContext.getAttributes();
        assertEquals("Tests the accuracy for DateFormatContext.getAttributes() method failed.", 3, attributes.size());
        assertEquals("Tests the accuracy for DateFormatContext.getAttributes() method failed.", localeStr,
            attributes.get(DateFormatContext.LOCALE_KEY));
        assertEquals("Tests the accuracy for DateFormatContext.getAttributes() method failed.", timeZoneID,
            attributes.get(DateFormatContext.TIMEZONE_KEY));
        assertEquals("Tests the accuracy for DateFormatContext.getAttributes() method failed.", df,
            attributes.get(DateFormatContext.DATE_FORMAT_KEY));
    }

    /**
     * Tests the accuracy for <code>getAttribute</code> method.
     */
    public void testGetAttribute() {
        assertEquals("Tests the accuracy for DateFormatContext.getAttribute(String) method failed.", localeStr,
            dateFormatContext.getAttribute(DateFormatContext.LOCALE_KEY));
    }

    /**
     * Tests the accuracy for <code>removeAttribute</code> method.
     */
    public void testRemoveAttribute() {
        dateFormatContext.removeAttribute(DateFormatContext.LOCALE_KEY);

        Map attributes = dateFormatContext.getAttributes();

        assertFalse("Tests the accuracy for removeAttribute(String) method failed.",
            attributes.containsKey(DateFormatContext.LOCALE_KEY));
    }

    /**
     * Tests the accuracy for <code>addAttribute(String,String)</code> method.
     */
    public void testAddAttribute() {
        dateFormatContext = new DateFormatContext();
        dateFormatContext.addAttribute(DateFormatContext.LOCALE_KEY, localeStr);
        assertEquals("Tests the accuracy for addAttribute(String,String) method failed.", localeStr,
            dateFormatContext.getAttribute(DateFormatContext.LOCALE_KEY));
    }

    /**
     * Tests the accuracy for <code>contains(String)</code> method.
     */
    public void testContains() {
        assertTrue("Tests the accuracy for contains(String) method failed.",
            dateFormatContext.contains(DateFormatContext.LOCALE_KEY));
        assertTrue("Tests the accuracy for contains(String) method failed.",
            dateFormatContext.contains(DateFormatContext.TIMEZONE_KEY));
        assertTrue("Tests the accuracy for contains(String) method failed.",
            dateFormatContext.contains(DateFormatContext.DATE_FORMAT_KEY));
    }
}
