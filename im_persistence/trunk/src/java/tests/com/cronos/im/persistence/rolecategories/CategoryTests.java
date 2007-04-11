/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>Category</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class CategoryTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a negative ID.
     */
    public void test_ctor1_negative_arg1() {
        try {
            new Category(-1, "name", "desc", true);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * name.
     */
    public void test_ctor1_null_arg2() {
        try {
            new Category(1, null, "desc", true);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed an empty name.
     */
    public void test_ctor1_empty_arg2() {
        try {
            new Category(1, "  ", "desc", true);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> description.
     */
    public void test_ctor1_null_arg3() {
        try {
            new Category(1, "name", null, true);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed an empty
     * description.
     */
    public void test_ctor1_empty_arg3() {
        try {
            new Category(1, "name", "  ", true);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getName</code> method.
     */
    public void test_getName() {
        assertEquals("getName should return the name passed to the constructor", "name",
                     new Category(1, "name", "description", true).getName());
    }

    /**
     * Tests the <code>getId</code> method.
     */
    public void test_getId() {
        assertEquals("getName should return the name passed to the constructor", 1,
                     new Category(1, "name", "description", true).getId());
    }

    /**
     * Tests the <code>getDescription</code> method.
     */
    public void test_getDescription() {
        assertEquals("getDescription should return the description passed to the constructor", "description",
                     new Category(1, "description", "description", true).getDescription());
    }

    /**
     * Tests the <code>getChattable</code> method.
     */
    public void test_getChattable() {
        assertTrue("getDescription should return the description passed to the constructor",
                     new Category(1, "description", "description", true).getChattable());
    }
}
