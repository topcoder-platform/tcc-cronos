/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.LessThanFilter;

import junit.framework.TestCase;


/**
 * Unit tests for LessThanFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestLessThanFilter extends TestCase {
    /**
     * Tests LessThanFilter(String name, Comparable value) method with null String name, Expected
     * IllegalArgumentException.
     */
    public void testLessThanFilter_NullName() {
        try {
            new LessThanFilter(null, "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LessThanFilter(String name, Comparable value) method with empty String name, Expected
     * IllegalArgumentException.
     */
    public void testLessThanFilter_EmptyName() {
        try {
            new LessThanFilter(" ", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LessThanFilter(String name, Comparable value) method with null Comparable value,
     * Expected IllegalArgumentException.
     */
    public void testLessThanFilter_NullValue() {
        try {
            new LessThanFilter("less", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
