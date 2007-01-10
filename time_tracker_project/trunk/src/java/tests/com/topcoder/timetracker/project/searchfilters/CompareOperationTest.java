/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for CompareOperation implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class CompareOperationTest extends TestCase {
    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(CompareOperationTest.class);

        return suite;
    }

    /**
     * Test of toString method with EQUAL operation. Verifies if it returns the string "=".
     */
    public void testToString_EQUAL() {
        assertEquals("Returns an incorrect string", "=", CompareOperation.EQUAL.toString());
    }

    /**
     * Test of toString method with GREATER operation. Verifies if it returns the string "&gt;".
     */
    public void testToString_GREATER() {
        assertEquals("Returns an incorrect string", ">", CompareOperation.GREATER.toString());
    }

    /**
     * Test of toString method with GREATER_OR_EQUAL operation. Verifies if it returns the string "&gt;=".
     */
    public void testToString_GREATER_OR_EQUAL() {
        assertEquals("Returns an incorrect string", ">=", CompareOperation.GREATER_OR_EQUAL.toString());
    }

    /**
     * Test of toString method with LESS operation. Verifies if it returns the string "&lt;".
     */
    public void testToString_LESS() {
        assertEquals("Returns an incorrect string", "<", CompareOperation.LESS.toString());
    }

    /**
     * Test of toString method with LESS_OR_EQUAL operation. Verifies if it returns the string "&lt;=".
     */
    public void testToString_LESS_OR_EQUAL() {
        assertEquals("Returns an incorrect string", "<=", CompareOperation.LESS_OR_EQUAL.toString());
    }

    /**
     * Test of toString method with LIKE operation. Verifies if it returns the string "like".
     */
    public void testToString_LIKE() {
        assertEquals("Returns an incorrect string", "like", CompareOperation.LIKE.toString());
    }
}
