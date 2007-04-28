/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for BinaryOperation implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class BinaryOperationTest extends TestCase {
    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BinaryOperationTest.class);

        return suite;
    }

    /**
     * Test of toString method with AND operation. Verifies if it returns the string "AND".
     */
    public void testToString_AND() {
        assertEquals("Returns an incorrect string", "AND", BinaryOperation.AND.toString());
    }

    /**
     * Test of toString method with OR operation. Verifies if it returns the string "OR".
     */
    public void testToString_OR() {
        assertEquals("Returns an incorrect string", "OR", BinaryOperation.OR.toString());
    }
}
