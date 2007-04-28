/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for StringMatchType.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class StringMatchTypeTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(StringMatchTypeTests.class);
    }

    /**
     * <p>
     * Tests StringMatchType#getFilterPrefix() for accuracy.
     * </p>
     *
     * <p>
     * It verifies StringMatchType#getFilterPrefix() is correct.
     * </p>
     */
    public void testGetFilterPrefix() {
        assertEquals("Failed to get the filter prefix correctly.", "SW:",
            StringMatchType.STARTS_WITH.getFilterPrefix());
        assertEquals("Failed to get the filter prefix correctly.", "EW:", StringMatchType.ENDS_WITH.getFilterPrefix());
        assertEquals("Failed to get the filter prefix correctly.", "SS:", StringMatchType.SUBSTRING.getFilterPrefix());
        assertEquals("Failed to get the filter prefix correctly.", "", StringMatchType.EXACT_MATCH.getFilterPrefix());
    }

    /**
     * <p>
     * Tests StringMatchType#toString() for accuracy.
     * </p>
     *
     * <p>
     * It verifies StringMatchType#toString() is correct.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string correctly.", "SW:", StringMatchType.STARTS_WITH.toString());
        assertEquals("Failed to string correctly.", "EW:", StringMatchType.ENDS_WITH.toString());
        assertEquals("Failed to string correctly.", "SS:", StringMatchType.SUBSTRING.toString());
        assertEquals("Failed to string correctly.", "", StringMatchType.EXACT_MATCH.toString());
    }

}