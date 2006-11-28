/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.LessThanOrEqualToFilter;

import junit.framework.TestCase;


/**
 * Unit tests for LessThanOrEqualToFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestLessThanOrEqualToFilter extends TestCase {
    /**
     * Tests LessThanOrEqualToFilter(String name, Comparable value) method with null String name,
     * Expected IllegalArgumentException.
     */
    public void testLessThanOrEqualToFilter_NullName() {
        try {
            new LessThanOrEqualToFilter(null, "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LessThanOrEqualToFilter(String name, Comparable value) method with empty String name,
     * Expected IllegalArgumentException.
     */
    public void testLessThanOrEqualToFilter_EmptyName() {
        try {
            new LessThanOrEqualToFilter(" ", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LessThanOrEqualToFilter(String name, Comparable value) method with null Comparable
     * value, Expected IllegalArgumentException.
     */
    public void testLessThanOrEqualToFilter_NullValue() {
        try {
            new LessThanOrEqualToFilter("less", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
