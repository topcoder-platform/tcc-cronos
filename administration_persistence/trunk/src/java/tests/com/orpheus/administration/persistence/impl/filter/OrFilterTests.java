/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Unit tests for the <code>OrFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class OrFilterTests extends AbstractAssociativeFilterTestCase {
    /**
     * Returns a concrete instance of <code>AbstractAssociativeFilter</code> to test.
     *
     * @return a concrete instance of <code>AbstractAssociativeFilter</code> to test
     */
    AbstractAssociativeFilter getFilter() {
        List filters = new ArrayList();
        filters.add(new EqualToFilter("test", "test"));

        return new OrFilter(filters);
    }

    /**
     * Tests that the two-argument constructor throws <code>IllegalArgumentException</code> when the first argument
     * is <code>null</code>.
     */
    public void test_ctor2_null_augend() {
        try {
            new OrFilter(null, new EqualToFilter("test", "test"));
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the two-argument constructor throws <code>IllegalArgumentException</code> when the second
     * argument is <code>null</code>.
     */
    public void test_ctor2_null_addend() {
        try {
            new OrFilter(new EqualToFilter("test", "test"), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of the two-argument constructor.
     */
    public void test_ctor2() {
        Filter filter1 = new EqualToFilter("filter 1", "filter 1");
        Filter filter2 = new EqualToFilter("filter 2", "filter 2");

        List filters = new OrFilter(filter1, filter2).getFilters();

        assertEquals("there should be two filters", 2, filters.size());
        assertTrue("one of the filters should be filter1", filters.contains(filter1));
        assertTrue("one of the filters should be filter2", filters.contains(filter2));
    }

    /**
     * Tests that the one-argument constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> filter list.
     */
    public void test_ctor1_null() {
        try {
            new OrFilter(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the one-argument constructor throws <code>IllegalArgumentException</code> when passed an empty
     * filter list.
     */
    public void test_ctor1_empty() {
        try {
            new OrFilter(new ArrayList());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the one-argument constructor throws <code>IllegalArgumentException</code> when passed a filter
     * list that contains non-<code>Filter</code> elements.
     */
    public void test_ctor1_bad_value() {
        List filters = new ArrayList();
        filters.add("oh no");

        try {
            new OrFilter(filters);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the one-argument constructor throws <code>IllegalArgumentException</code> when passed a filter
     * list that contains <code>null</code> elements.
     */
    public void test_ctor1_null_value() {
        List filters = new ArrayList();
        filters.add(null);

        try {
            new OrFilter(filters);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the the one-argument constructor.
     */
    public void test_ctor1() {
        List filters = new ArrayList();
        for (int i = 0; i < 5; ++i) {
            filters.add(new EqualToFilter("filter", new Integer(i)));
        }

        assertEquals("getFilters() should return the filters passed to ctor1()",
                     new HashSet(filters), new HashSet(new OrFilter(filters).getFilters()));
    }
}
