/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.GreaterThanFilter;

import junit.framework.TestCase;


/**
 * Unit tests for GreaterThanFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestGreaterThanFilter extends TestCase {
    /**
     * Tests GreaterThanFilter(String name, Comparable value) method with null String name,
     * Expected IllegalArgumentException.
     */
    public void testGreaterThanFilter_NullName() {
        try {
            new GreaterThanFilter(null, "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests GreaterThanFilter(String name, Comparable value) method with empty String name,
     * Expected IllegalArgumentException.
     */
    public void testGreaterThanFilter_EmptyName() {
        try {
            new GreaterThanFilter(" ", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests GreaterThanFilter(String name, Comparable value) method with null Comparable
     * value, Expected IllegalArgumentException.
     */
    public void testGreaterThanFilter_NullValue() {
        try {
            new GreaterThanFilter("less", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
