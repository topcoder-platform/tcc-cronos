/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>InFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class InFilterTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * name.
     */
    public void test_ctor_null_name() {
        try {
            new InFilter(null, new ArrayList());
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
            new InFilter("  ", new ArrayList());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code> list
     * of values.
     */
    public void test_ctor_null_values() {
        try {
            new InFilter("name", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * value.
     */
    public void test_ctor_null_values_elements() {
        List values = new ArrayList();
        values.add(null);
        try {
            new InFilter("name", values);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a value that is not
     * comparable.
     */
    public void test_ctor_values_element_not_comparable() {
        List values = new ArrayList();
        values.add(new Object());
        try {
            new InFilter("name", values);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    // the normal operation of the constructor is tested below, so there's no need for a special test

    /**
     * Tests the <code>getName</code> method.
     */
    public void test_getName() {
        List values = new ArrayList();
        values.add("hello");
        assertEquals("getName should return the name passed to the constructor",
                     "name", new InFilter("name", values).getName());

    }

    /**
     * Tests the <code>getList</code> method.
     */
    public void test_getList() {
        List values = new ArrayList();
        values.add("hello");

        assertEquals("the list should be the same list passed to the constructor", values,
                     new InFilter("name", values).getList());
    }
}
