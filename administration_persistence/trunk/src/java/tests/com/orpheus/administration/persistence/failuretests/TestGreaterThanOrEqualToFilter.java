/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.GreaterThanOrEqualToFilter;

import junit.framework.TestCase;


/**
 * Unit tests for GreaterThanOrEqualToFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestGreaterThanOrEqualToFilter extends TestCase {
    /**
     * Tests GreaterThanOrEqualToFilter(String name, Comparable value) method with null String name,
     * Expected IllegalArgumentException.
     */
    public void testGreaterThanOrEqualToFilter_NullName() {
        try {
            new GreaterThanOrEqualToFilter(null, "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests GreaterThanOrEqualToFilter(String name, Comparable value) method with empty String name,
     * Expected IllegalArgumentException.
     */
    public void testGreaterThanOrEqualToFilter_EmptyName() {
        try {
            new GreaterThanOrEqualToFilter(" ", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests GreaterThanOrEqualToFilter(String name, Comparable value) method with null Comparable
     * value, Expected IllegalArgumentException.
     */
    public void testGreaterThanOrEqualToFilter_NullValue() {
        try {
            new GreaterThanOrEqualToFilter("less", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
