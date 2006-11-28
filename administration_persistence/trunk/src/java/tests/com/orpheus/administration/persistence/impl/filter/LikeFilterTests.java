/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>LikeFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class LikeFilterTests extends TestCase {
    /**
     * Tests that the two-argument constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> name.
     */
    public void test_ctor2_null_name() {
        try {
            new LikeFilter(null, "SW:value");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the two-argument constructor throws <code>IllegalArgumentException</code> when passed an empty
     * name.
     */
    public void test_ctor2_empty_name() {
        try {
            new LikeFilter("  ", "SW:value");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the two-argument constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> value.
     */
    public void test_ctor2_null_value() {
        try {
            new LikeFilter("name", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the two-argument constructor throws <code>IllegalArgumentException</code> when passed an empty
     * value.
     */
    public void test_ctor2_empty_value() {
        try {
            new LikeFilter("name", "  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the three-argument constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> name.
     */
    public void test_ctor3_null_name() {
        try {
            new LikeFilter(null, "SW:value", 'a');
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the three-argument constructor throws <code>IllegalArgumentException</code> when passed an empty
     * name.
     */
    public void test_ctor3_empty_name() {
        try {
            new LikeFilter("  ", "SW:value", 'a');
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the three-argument constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> value.
     */
    public void test_ctor3_null_value() {
        try {
            new LikeFilter("name", null, 'a');
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the three-argument constructor throws <code>IllegalArgumentException</code> when passed an empty
     * value.
     */
    public void test_ctor3_empty_value() {
        try {
            new LikeFilter("name", "  ", 'a');
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the three-argument constructor throws <code>IllegalArgumentException</code> when passed an
     * invalid escape character.
     */
    public void test_ctor3_bad_escape() {
        try {
            new LikeFilter("name", "SW:value", '_');
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getName</code> method.
     */
    public void test_getName() {
        assertEquals("getName() should return the name passed to the constructor",
                     "name", new LikeFilter("name", "SW:value").getName());
    }

    /**
     * Tests the <code>getValue</code> method.
     */
    public void test_getValue() {
        assertEquals("getValue() should return the value passed to the constructor",
                     "SW:value", new LikeFilter("name", "SW:value").getValue());
    }

    /**
     * Tests the <code>getEscapeCharacter</code> method.
     */
    public void test_getEscapeCharacter() {
        assertEquals("getEscapeCharacter() should return the escape chacter passed to the constructor",
                     'a', new LikeFilter("name", "SW:value", 'a').getEscapeCharacter());
    }
}
