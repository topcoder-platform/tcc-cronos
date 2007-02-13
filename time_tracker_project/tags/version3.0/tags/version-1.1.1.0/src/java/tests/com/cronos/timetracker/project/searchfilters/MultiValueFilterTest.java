/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.searchfilters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;


/**
 * Unit tests for MultiValueFilter implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class MultiValueFilterTest extends TestCase {
    /**
     * The MultiValueFilter instance to test against.
     */
    private MultiValueFilter filter = null;

    /**
     * The name of the field to compare the values with.
     */
    private String fieldName = "name";

    /**
     * The values to compare.
     */
    private Object[] values = new Object[] {"value1", "value2"};

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(MultiValueFilterTest.class);

        return suite;
    }

    /**
     * Prepares a MultiValueFilter instance for testing.
     */
    protected void setUp() {
        filter = new MultiValueFilter(fieldName, values);
    }

    /**
     * Test of constructor with null field name. Expects IllegalArgumentException.
     */
    public void testConstructor_NullFieldName() {
        try {
            new MultiValueFilter(null, values);
            fail("Creates MultiValueFilter with null field name");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with empty field name. Expects IllegalArgumentException.
     */
    public void testConstructor_EmptyFieldName() {
        try {
            new MultiValueFilter("", values);
            fail("Creates MultiValueFilter with empty field name");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null values. Expects IllegalArgumentException.
     */
    public void testConstructor_NullValues() {
        try {
            new MultiValueFilter(fieldName, null);
            fail("Creates MultiValueFilter with null values");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with invalid values (not of allowed types). Expects IllegalArgumentException.
     */
    public void testConstructor_InvalidValues() {
        try {
            new MultiValueFilter(fieldName, new Object[] {new Object()});
            fail("Creates MultiValueFilter with invalid values");
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
     * Test of getValues method. Verifies if it returns the values set in constructor.
     */
    public void testGetValues() {
        assertTrue("Returns an incorrect values array", Arrays.equals(values, filter.getValues()));
    }
}
