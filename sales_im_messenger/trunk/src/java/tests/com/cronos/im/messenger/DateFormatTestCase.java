/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * DateFormatTestCase.java
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Test the methods of <c>DateFormatContext</c> class for accuracy and failure.
 *
 * @author marius_neo
 * @version 1.0
 */
public class DateFormatTestCase extends TestCase {
    /**
     * Represents the <c>DateFormatContext</c> instance used in tests.
     */
    private DateFormatContext context;

    /**
     * Represents the attribute value for LOCALE_KEY used in tests.
     */
    private String localeString;

    /**
     * Represents the attribute value for TIMEZONE_KEY used in tests.
     */
    private String timeZoneID;

    /**
     * Represents the attribute value for DATE_FORMAT_KEY used in tests.
     */
    private String dateFormat;

    /**
     * Setup testing environment by initializing all variables used in tests.
     */
    protected void setUp() {
        context = new DateFormatContext();

        localeString = Locale.US.toString();
        timeZoneID = TimeZone.getTimeZone("America/Los_Angeles").getID();
        dateFormat = "hh:mm:ss a z";

        // Add attributes to the context
        context.addAttribute(DateFormatContext.DATE_FORMAT_KEY, dateFormat);
        context.addAttribute(DateFormatContext.TIMEZONE_KEY, timeZoneID);
        context.addAttribute(DateFormatContext.LOCALE_KEY, localeString);
    }

    /**
     * Tests the constructor <c>DateFormatContext()</c> for accuracy.
     */
    public void testCtor() {
        assertNotNull("The instance was not created", context);
    }

    /**
     * Tests the accuracy for <c>getAttributes</c> method when the
     * number and the values of the attributes are updated.
     */
    public void testGetAttributes() {
        Map attributes = context.getAttributes();
        assertTrue("There should be 3 attributes set", attributes.size() == 3);
        assertEquals("The locale strings should be equal"
            , localeString, attributes.get(DateFormatContext.LOCALE_KEY));
        assertEquals("The timezone keys should be equal"
            , timeZoneID, attributes.get(DateFormatContext.TIMEZONE_KEY));
        assertEquals("The date formats should be equal"
            , dateFormat, attributes.get(DateFormatContext.DATE_FORMAT_KEY));

        // Update values for attributes
        String newLocaleString = Locale.FRANCE.toString();
        context.addAttribute(DateFormatContext.LOCALE_KEY, newLocaleString);
        attributes = context.getAttributes();
        assertTrue("There should be 3 attributes set", attributes.size() == 3);
        assertEquals("The locale strings should be equal"
            , newLocaleString, attributes.get(DateFormatContext.LOCALE_KEY));

        // Delete LOCALE_KEY attribute
        context.removeAttribute(DateFormatContext.LOCALE_KEY);
        attributes = context.getAttributes();
        assertTrue("There should be 2 attributes set", attributes.size() == 2);
        assertFalse("LOCALE_KEY attribute shouldn't exist anymore between the attributes of the context"
            , context.getAttributes().containsKey(DateFormatContext.LOCALE_KEY));

        // Tests getAttributes method on a newly created context
        context = new DateFormatContext();
        assertTrue("The attributes map should be empty", context.getAttributes().size() == 0);
    }

    /**
     * Tests the accuracy for <c>getAttribute</c> method when the
     * values of the attributes are updated or removed.
     */
    public void testGetAttribute() {
        assertTrue("There should be 3 attributes set", context.getAttributes().size() == 3);
        assertEquals("The locale strings should be equal"
            , localeString, context.getAttribute(DateFormatContext.LOCALE_KEY));
        assertEquals("The timezone keys should be equal"
            , timeZoneID, context.getAttribute(DateFormatContext.TIMEZONE_KEY));
        assertEquals("The date formats should be equal"
            , dateFormat, context.getAttribute(DateFormatContext.DATE_FORMAT_KEY));

        // Update values for attributes
        String newLocaleString = Locale.FRANCE.toString();
        context.addAttribute(DateFormatContext.LOCALE_KEY, newLocaleString);
        assertEquals("The locale strings should be equal"
            , newLocaleString, context.getAttribute(DateFormatContext.LOCALE_KEY));

        // Delete LOCALE_KEY attribute
        context.removeAttribute(DateFormatContext.LOCALE_KEY);
        assertTrue("There should be 2 attributes set", context.getAttributes().size() == 2);
        assertFalse("LOCALE_KEY attribute shouldn't exist anymore between the attributes of the context"
            , context.contains(DateFormatContext.LOCALE_KEY));
        assertNull("The value for LOCAL_KEY attribute should be null"
            , context.getAttribute(DateFormatContext.LOCALE_KEY));
    }

    /**
     * Tests the failure for <c>getAttribute</c> method when the
     * attribute name specified to the method is null.
     */
    public void testGetAttributeNullName() {
        try {
            context.getAttribute(null);

            fail("Should have thrown IllegalArgumentException because of null name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for <c>getAttribute</c> method when the
     * attribute name specified to the method is an empty string.
     */
    public void testGetAttributeEmptyName() {
        try {
            context.getAttribute("  ");

            fail("Should have thrown IllegalArgumentException because of empty string name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for <c>removeAttribute</c> method when the
     * values of the attributes are updated or removed.
     */
    public void testRemoveAttribute() {
        assertTrue("There should be 3 attributes set", context.getAttributes().size() == 3);

        // Delete LOCALE_KEY attribute
        context.removeAttribute(DateFormatContext.LOCALE_KEY);
        assertTrue("There should be 2 attributes set", context.getAttributes().size() == 2);
        assertFalse("LOCALE_KEY attribute shouldn't exist anymore between the attributes of the context"
            , context.contains(DateFormatContext.LOCALE_KEY));
        assertNull("The value for LOCAL_KEY attribute should be null"
            , context.getAttribute(DateFormatContext.LOCALE_KEY));

        // Delete TIMEZONE_KEY and DATE_FORMAT_KEY attributes
        context.removeAttribute(DateFormatContext.TIMEZONE_KEY);
        context.removeAttribute(DateFormatContext.DATE_FORMAT_KEY);
        assertTrue("There should be no more attributes left in the context"
            , context.getAttributes().size() == 0);
    }

    /**
     * Tests the failure for <c>removeAttribute</c> method when the
     * attribute name specified to the method is null.
     */
    public void testRemoveAttributeNullName() {
        try {
            context.removeAttribute(null);

            fail("Should have thrown IllegalArgumentException because of null name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for <c>removeAttribute</c> method when the
     * attribute name specified to the method is an empty string.
     */
    public void testRemoveAttributeEmptyName() {
        try {
            context.removeAttribute("  ");

            fail("Should have thrown IllegalArgumentException because of empty string name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for <c>addAttribute(String,String)</c> method.
     */
    public void testAddAttribute() {
        context = new DateFormatContext();
        context.addAttribute(DateFormatContext.LOCALE_KEY, localeString);
        assertTrue("There should be 1 attribute set", context.getAttributes().size() == 1);
        assertEquals("The locale strings should be equal"
            , localeString, context.getAttribute(DateFormatContext.LOCALE_KEY));

        // Update values for attribute LOCALE_KEY
        String newLocaleString = Locale.FRANCE.toString();
        context.addAttribute(DateFormatContext.LOCALE_KEY, newLocaleString);
        assertEquals("The locale strings should be equal"
            , newLocaleString, context.getAttribute(DateFormatContext.LOCALE_KEY));
    }

    /**
     * Tests the failure for <c>addAttribute(String, String)</c> method when the
     * attribute name or value specified to the method is null.
     */
    public void testAddAttributeNull() {
        try {
            context.addAttribute(null, "value");

            fail("Should have thrown IllegalArgumentException because of null attribute name");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            context.addAttribute("name", null);

            fail("Should have thrown IllegalArgumentException because of null attribute value");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for <c>addAttribute(String, String)</c> method when the
     * attribute name or value specified to the method is an empty string.
     */
    public void testAddAttributeEmptyString() {
        try {
            context.addAttribute("  ", "value");

            fail("Should have thrown IllegalArgumentException because of empty string attribute name");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            context.addAttribute("key", " ");

            fail("Should have thrown IllegalArgumentException because of empty string attribute value");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for <c>contains(String)</c> method.
     */
    public void testContains() {
        assertTrue("There should be 3 attributes set", context.getAttributes().size() == 3);
        assertTrue("LOCALE_KEY attribute should be contained in context"
            , context.contains(DateFormatContext.LOCALE_KEY));
        assertTrue("TIMEZONE_KEY attribute should be contained in context"
            , context.contains(DateFormatContext.TIMEZONE_KEY));
        assertTrue("DATE_FORMAT_KEY attribute should be contained in context"
            , context.contains(DateFormatContext.DATE_FORMAT_KEY));

        // Delete LOCALE_KEY attribute
        context.removeAttribute(DateFormatContext.LOCALE_KEY);
        assertFalse("LOCALE_KEY attribute shouldn't exist anymore between the attributes of the context"
            , context.contains(DateFormatContext.LOCALE_KEY));
        assertNull("The value for LOCAL_KEY attribute should be null"
            , context.getAttribute(DateFormatContext.LOCALE_KEY));
    }

    /**
     * Tests the failure for <c>contains(String)</c> method when the
     * attribute name specified to the method is null.
     */
    public void testContainsNullName() {
        try {
            context.contains(null);

            fail("Should have thrown IllegalArgumentException because of null name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for <c>contains(String)</c> method when the
     * attribute name specified to the method is an empty string.
     */
    public void testContainsEmptyName() {
        try {
            context.contains("  ");

            fail("Should have thrown IllegalArgumentException because of empty string name");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }
}
