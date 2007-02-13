/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for NotFilter implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class NotFilterTest extends TestCase {
    /**
     * The NotFilter instance to test against.
     */
    private NotFilter filter = null;

    /**
     * The operand of the NotFilter.
     */
    private Filter operand = new ValueFilter(CompareOperation.EQUAL, "name", "value");

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NotFilterTest.class);

        return suite;
    }

    /**
     * Prepares a NotFilter instance for testing.
     */
    protected void setUp() {
        filter = new NotFilter(operand);
    }

    /**
     * Test of constructor with null operand. Expects IllegalArgumentException.
     */
    public void testConstructor_NullOperand() {
        try {
            new NotFilter(null);
            fail("Creates NotFilter with null operand");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getOperand method. Verifies if it returns the operand set in constructor.
     */
    public void testGetOperand() {
        assertEquals("Returns an incorrect operand", operand, filter.getOperand());
    }
}
