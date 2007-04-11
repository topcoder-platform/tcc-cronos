/*
 * Copyright (C) 2006 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.cronos.im.persistence.rolecategories.Category;

import junit.framework.TestCase;


/**
 * Failure Test case for the class <code>Category</code>.
 *
 * @author waits
 * @version 1.0
 */
public class CategoryFailureTests extends TestCase {
    /**
     * Tests that the constructor witha negative ID, iae expected.
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
     * Tests that the constructor with a <code>null</code> name, iae expected.
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
     * Tests that the ctor with empty name, iae expected.
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
     * test the ctor with null desc, iae expected.
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
     * Tests the ctor with empty desc, iae expected.
     */
    public void test_ctor1_empty_arg3() {
        try {
            new Category(1, "name", "  ", true);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }
}
