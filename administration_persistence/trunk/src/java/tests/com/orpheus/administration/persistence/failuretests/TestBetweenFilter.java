/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.BetweenFilter;

import junit.framework.TestCase;


/**
 * Unit tests for BetweenFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestBetweenFilter extends TestCase {
    /**
     * Tests BetweenFilter(String name, Comparable upper, Comparable lower) method with null String
     * name, Expected IllegalArgumentException.
     */
    public void testBetweenFilter_NullName() {
        try {
            new BetweenFilter(null, "1", "2");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests BetweenFilter(String name, Comparable upper, Comparable lower) method with empty
     * String name, Expected IllegalArgumentException.
     */
    public void testBetweenFilter_EmptyName() {
        try {
            new BetweenFilter(" ", "1", "2");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests BetweenFilter(String name, Comparable upper, Comparable lower) method with null
     * Comparable upper, Expected IllegalArgumentException.
     */
    public void testBetweenFilter_NullUpper() {
        try {
            new BetweenFilter("between", null, "2");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests BetweenFilter(String name, Comparable upper, Comparable lower) method with null
     * Comparable lower, Expected IllegalArgumentException.
     */
    public void testBetweenFilter_NullLower() {
        try {
            new BetweenFilter("between", "1", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
