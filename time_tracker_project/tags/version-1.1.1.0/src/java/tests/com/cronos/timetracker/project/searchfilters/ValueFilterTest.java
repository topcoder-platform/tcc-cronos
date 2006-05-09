/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.searchfilters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for ValueFilter implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class ValueFilterTest extends TestCase {
    /**
     * The ValueFilter instance to test against.
     */
    private ValueFilter filter = null;

    /**
     * The operation to be used to compare the value with the specified field of entity.
     */
    private CompareOperation operation = CompareOperation.EQUAL;

    /**
     * The name of the field to compare the values with.
     */
    private String fieldName = "name";

    /**
     * The value to compare.
     */
    private Object value = "value";

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ValueFilterTest.class);

        return suite;
    }

    /**
     * Prepares a ValueFilter instance for testing.
     */
    protected void setUp() {
        filter = new ValueFilter(operation, fieldName, value);
    }

    /**
     * Test of constructor with null operation. Expects IllegalArgumentException.
     */
    public void testConstructor_NullOperation() {
        try {
            new ValueFilter(null, fieldName, value);
            fail("Creates ValueFilter with null operation");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null field name. Expects IllegalArgumentException.
     */
    public void testConstructor_NullFieldName() {
        try {
            new ValueFilter(operation, null, value);
            fail("Creates ValueFilter with null field name");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with empty field name. Expects IllegalArgumentException.
     */
    public void testConstructor_EmptyFieldName() {
        try {
            new ValueFilter(operation, "", value);
            fail("Creates ValueFilter with empty field name");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null value. Expects IllegalArgumentException.
     */
    public void testConstructor_NullValue() {
        try {
            new ValueFilter(operation, fieldName, null);
            fail("Creates ValueFilter with null value");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with invalid value (not of the allowed types). Expects IllegalArgumentException.
     */
    public void testConstructor_InvalidValue() {
        try {
            new ValueFilter(operation, fieldName, new Object());
            fail("Creates ValueFilter with invalid value");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getFieldName method. Verifies if it returns the field name set in constructor.
     */
    public void testGetFieldName() {
        assertEquals("Returns an incorrect field name", fieldName, filter.getFieldName());
    }

    /**
     * Test of getValue method. Verifies if it returns the value set in constructor.
     */
    public void testGetValue() {
        assertEquals("Returns an incorrect value", value, filter.getValue());
    }

    /**
     * Test of getOperation method. Verifies if it returns the operation set in constructor.
     */
    public void testGetOperation() {
        assertEquals("Returns an incorrect operation", operation, filter.getOperation());
    }
}
