/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.searchfilters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for BinaryOperationFilter implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class BinaryOperationFilterTest extends TestCase {
    /**
     * The BinaryOperationFilter instance to test against.
     */
    private BinaryOperationFilter filter = null;

    /**
     * The binary logical operation to be applied to the operand filters.
     */
    private BinaryOperation operation = BinaryOperation.AND;

    /**
     * The left operand of the BinaryOperationFilter.
     */
    private Filter leftOperand = new ValueFilter(CompareOperation.EQUAL, "name1", "value1");

    /**
     * The right operand of the BinaryOperationFilter.
     */
    private Filter rightOperand = new ValueFilter(CompareOperation.EQUAL, "name2", "value2");

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BinaryOperationFilterTest.class);

        return suite;
    }

    /**
     * Prepares a BinaryOperationFilter instance for testing.
     */
    protected void setUp() {
        filter = new BinaryOperationFilter(operation, leftOperand, rightOperand);
    }

    /**
     * Test of constructor with null operation. Expects IllegalArgumentException.
     */
    public void testConstructor_NullOperation() {
        try {
            new BinaryOperationFilter(null, leftOperand, rightOperand);
            fail("Creates BinaryOperationFilter with null operation");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null left operand. Expects IllegalArgumentException.
     */
    public void testConstructor_NullLeftOperand() {
        try {
            new BinaryOperationFilter(operation, null, rightOperand);
            fail("Creates BinaryOperationFilter with null left operand");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null right operand. Expects IllegalArgumentException.
     */
    public void testConstructor_NullRightOperand() {
        try {
            new BinaryOperationFilter(operation, leftOperand, null);
            fail("Creates BinaryOperationFilter with null right operand");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getLeftOperand method. Verifies if it returns the left operand set in constructor.
     */
    public void testGetLeftOperand() {
        assertEquals("Returns an incorrect left operand", leftOperand, filter.getLeftOperand());
    }

    /**
     * Test of getRightOperand method. Verifies if it returns the right operand set in constructor.
     */
    public void testGetRightOperand() {
        assertEquals("Returns an incorrect right operand", rightOperand, filter.getRightOperand());
    }

    /**
     * Test of getOperation method. Verifies if it returns the operation set in constructor.
     */
    public void testGetOperation() {
        assertEquals("Returns an incorrect operation", operation, filter.getOperation());
    }
}
