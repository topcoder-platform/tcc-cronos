/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import junit.framework.TestCase;

/**
 * Base class for tests of <code>AbstractSimpleFilter</code> implementations.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class AbstractSimpleFilterTestCase extends TestCase {
    /**
     * Returns a concrete instance of <code>AbstractSimpleFilter</code> to test.
     *
     * @param name the field name
     * @param value the value to filter on
     * @return a concrete instance of <code>AbstractSimpleFilter</code> to test
     */
    abstract AbstractSimpleFilter getFilter(String name, Comparable value);

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * name.
     */
    public void test_ctor_null_name() {
        try {
            getFilter(null, "oh no");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty name.
     */
    public void test_ctor_empty_name() {
        try {
            getFilter("  ", "oh no");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * value.
     */
    public void test_ctor_null_value() {
        try {
            getFilter("oh no", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    // the normal operation of the constructor is tested by the rest of the tests, so no need for a special test case

    /**
     * Tests the <code>getName</code> method.
     */
    public void test_getName() {
        assertEquals("getName() should return the name passed to the constructor",
                     "name", getFilter("name", "value").getName());
    }

    /**
     * Tests the <code>getValue</code> method.
     */
    public void test_getValue() {
        assertEquals("getValue() should return the name passed to the constructor",
                     "value", getFilter("name", "value").getValue());
    }

    /**
     * Tests the <code>getUpperThreshold</code> method.
     */
    public void test_getUpperThreshold() {
        AbstractSimpleFilter filter = getFilter("name", "value");
        filter.setUpperThreshold("upper threshold", true);
        assertEquals("getUpperThreshold should return the upper threshold", "upper threshold",
                     filter.getUpperThreshold());
    }

    /**
     * Tests the <code>getLowerThreshold</code> method.
     */
    public void test_getLowerThreshold() {
        AbstractSimpleFilter filter = getFilter("name", "value");
        filter.setLowerThreshold("lower threshold", true);
        assertEquals("getLowerThreshold should return the lower threshold", "lower threshold",
                     filter.getLowerThreshold());
    }

    /**
     * Tests the <code>isLowerInclusive</code> method.
     */
    public void test_isLowerInclusive() {
        AbstractSimpleFilter filter = getFilter("name", "value");
        filter.setLowerThreshold("lower threshold", true);
        assertTrue("lower should be inclusive", filter.isLowerInclusive());
        filter.setLowerThreshold("lower threshold", false);
        assertFalse("lower should not be inclusive", filter.isLowerInclusive());
    }

    /**
     * Tests the <code>isUpperInclusive</code> method.
     */
    public void test_isUpperInclusive() {
        AbstractSimpleFilter filter = getFilter("name", "value");
        filter.setUpperThreshold("upper threshold", true);
        assertTrue("upper should be inclusive", filter.isUpperInclusive());
        filter.setUpperThreshold("upper threshold", false);
        assertFalse("upper should not be inclusive", filter.isUpperInclusive());
    }
}
