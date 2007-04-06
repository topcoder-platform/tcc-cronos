/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.notification.StringMatchType;

/**
 * <p>
 * Accuracy Unit test cases for StringMatchType.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class StringMatchTypeAccuracyTests extends TestCase {
    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(StringMatchTypeAccuracyTests.class);
    }

    /**
     * <p>Tests StringMatchType#getFilterPrefix() for accuracy.</p>
     */
    public void testGetFilterPrefix() {
        assertEquals("Failed to get the filter prefix correctly.", "SW:",
            StringMatchType.STARTS_WITH.getFilterPrefix());
        assertEquals("Failed to get the filter prefix correctly.", "EW:", StringMatchType.ENDS_WITH.getFilterPrefix());
        assertEquals("Failed to get the filter prefix correctly.", "SS:", StringMatchType.SUBSTRING.getFilterPrefix());
        assertEquals("Failed to get the filter prefix correctly.", "", StringMatchType.EXACT_MATCH.getFilterPrefix());
    }

    /**
     * <p>Tests StringMatchType#toString() for accuracy.</p>
     */
    public void testToString() {
        assertEquals("Failed to String correctly.", "SW:", StringMatchType.STARTS_WITH.toString());
        assertEquals("Failed to String correctly.", "EW:", StringMatchType.ENDS_WITH.toString());
        assertEquals("Failed to String correctly.", "SS:", StringMatchType.SUBSTRING.toString());
        assertEquals("Failed to String correctly.", "", StringMatchType.EXACT_MATCH.toString());
    }

}