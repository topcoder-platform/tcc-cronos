/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

/**
 * Base class for tests of <code>AbstractAssociativeFilter</code> implementations.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class AbstractAssociativeFilterTestCase extends TestCase {
    /**
     * Returns a concrete instance of <code>AbstractAssociativeFilter</code> to test.
     *
     * @return a concrete instance of <code>AbstractAssociativeFilter</code> to test
     */
    abstract AbstractAssociativeFilter getFilter();

    /**
     * Tests that <code>addFilter</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> filter.
     */
    public void test_addFilter_null() {
        try {
            getFilter().addFilter(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>addFilter</code> and <code>getFilters</code> method.
     */
    public void test_addFilter() {
        AbstractAssociativeFilter abstractFilter = getFilter();

        for (int i = 0; i < 5; ++i) {
            Filter filter = new EqualToFilter("some field", new Integer(i));
            abstractFilter.addFilter(filter);
            assertTrue("filter should be added", abstractFilter.getFilters().contains(filter));
        }
    }

    /**
     * Tests that <code>setFilters</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> filter list.
     */
    public void test_setFilters_null() {
        try {
            getFilter().setFilters(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>setFilters</code> throws <code>IllegalArgumentException</code> when passed an empty filter
     * list.
     */
    public void test_setFilters_empty() {
        try {
            getFilter().setFilters(new ArrayList());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>setFilters</code> throws <code>IllegalArgumentException</code> when passed a filter list
     * that contains non-<code>Filter</code> elements.
     */
    public void test_setFilters_bad_value() {
        List filters = new ArrayList();
        filters.add("oh no");

        try {
            getFilter().setFilters(filters);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>setFilters</code> throws <code>IllegalArgumentException</code> when passed a filter list
     * that contains <code>null</code> elements.
     */
    public void test_setFilters_null_value() {
        List filters = new ArrayList();
        filters.add(null);

        try {
            getFilter().setFilters(filters);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>setFilters</code> method.
     */
    public void test_setFilters() {
        AbstractAssociativeFilter abstractFilter = getFilter();

        List filters = new ArrayList();
        for (int i = 0; i < 5; ++i) {
            filters.add(new EqualToFilter("filter", new Integer(i)));
        }
        abstractFilter.setFilters(filters);

        assertEquals("getFilters() should return the filters passed to setFilters()",
                     new HashSet(filters), new HashSet(abstractFilter.getFilters()));
    }
}
