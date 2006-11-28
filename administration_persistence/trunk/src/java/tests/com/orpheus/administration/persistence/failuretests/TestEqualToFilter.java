/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.EqualToFilter;

import junit.framework.TestCase;


/**
 * Unit tests for EqualToFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestEqualToFilter extends TestCase {
    /**
     * Tests EqualToFilter(String name, Comparable value) method with null String name, Expected
     * IllegalArgumentException.
     */
    public void testEqualToFilter_NullName() {
        try {
            new EqualToFilter(null, "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests EqualToFilter(String name, Comparable value) method with empty String name, Expected
     * IllegalArgumentException.
     */
    public void testEqualToFilter_EmptyName() {
        try {
            new EqualToFilter(" ", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests EqualToFilter(String name, Comparable value) method with null Comparable value,
     * Expected IllegalArgumentException.
     */
    public void testEqualToFilter_NullValue() {
        try {
            new EqualToFilter("less", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
