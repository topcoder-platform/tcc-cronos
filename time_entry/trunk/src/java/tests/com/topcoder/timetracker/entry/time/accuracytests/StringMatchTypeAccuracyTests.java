/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.entry.time.StringMatchType;

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
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(StringMatchTypeAccuracyTests.class);
    }

    /**
     * <p>
     * Tests StringMatchType#getFilterPrefix() for accuracy.
     * </p>
     */
    public void testGetFilterPrefix() {
        assertEquals("Failed to get the filter prefix.", "EW:", StringMatchType.ENDS_WITH.getFilterPrefix());
    }

    /**
     * <p>
     * Tests StringMatchType#toString() for accuracy.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string.", "EW:", StringMatchType.ENDS_WITH.toString());
    }

}