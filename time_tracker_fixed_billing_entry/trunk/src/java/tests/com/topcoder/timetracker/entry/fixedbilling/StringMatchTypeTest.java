/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link StringMatchType}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class StringMatchTypeTest extends TestCase {
    /**
     * Test the <code>{@link StringMatchType#getFilterPrefix()}</code> with success process.
     */
    public void testGetFilterPrefix_Success() {
        assertEquals("The return result should be same.", StringMatchType.STARTS_WITH.getFilterPrefix(), "SW:");
        assertEquals("The return result should be same.", StringMatchType.ENDS_WITH.getFilterPrefix(), "EW:");
        assertEquals("The return result should be same.", StringMatchType.SUBSTRING.getFilterPrefix(), "SS:");
        assertEquals("The return result should be same.", StringMatchType.EXACT_MATCH.getFilterPrefix(), "");
    }

    /**
     * Test the <code>{@link StringMatchType#toString()}</code> with success process.
     */
    public void testToString_Success() {
        assertEquals("The return result should be same.", StringMatchType.STARTS_WITH.toString(), "SW:");
        assertEquals("The return result should be same.", StringMatchType.ENDS_WITH.toString(), "EW:");
        assertEquals("The return result should be same.", StringMatchType.SUBSTRING.toString(), "SS:");
        assertEquals("The return result should be same.", StringMatchType.EXACT_MATCH.toString(), "");
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(StringMatchTypeTest.class);
    }
}
