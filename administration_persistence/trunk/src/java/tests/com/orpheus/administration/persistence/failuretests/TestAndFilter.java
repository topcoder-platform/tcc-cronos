/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.AndFilter;
import com.orpheus.administration.persistence.impl.filter.GreaterThanFilter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * Unit tests for AndFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestAndFilter extends TestCase {
    /** AndFilter used to test. */
    private AndFilter af = new AndFilter(new GreaterThanFilter("Greater", "2"),
            new GreaterThanFilter("Greater", "1"));

    /**
     * Tests AndFilter(Filter augend, Filter addend) method with null Filter augend, Expected
     * IllegalArgumentException.
     */
    public void testAndFilter_NullAugend() {
        try {
            new AndFilter(null, new GreaterThanFilter("Greater", "1"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests AndFilter(Filter augend, Filter addend) method with null Filter addend, Expected
     * IllegalArgumentException.
     */
    public void testAndFilter_NullAddend() {
        try {
            new AndFilter(new GreaterThanFilter("Greater", "1"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests AndFilter(List filters) method with null List filters, Expected
     * IllegalArgumentException.
     */
    public void testAndFilter_NullFilters() {
        try {
            new AndFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests AndFilter(List filters) method with empty List filters, Expected
     * IllegalArgumentException.
     */
    public void testAndFilter_EmptyFilters() {
        try {
            new AndFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests AndFilter(List filters) method with null with List filters, Expected
     * IllegalArgumentException.
     */
    public void testAndFilter_NullWithFilters() {
        List list = new ArrayList();
        list.add(null);

        try {
            new AndFilter(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests AndFilter(List filters) method with invalid List filters, Expected
     * IllegalArgumentException.
     */
    public void testAndFilter_InvalidFilters() {
        List list = new ArrayList();
        list.add("invalid");

        try {
            new AndFilter(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests addFilter(Filter filter) method with null List filters, Expected
     * IllegalArgumentException.
     */
    public void testAddFilter_NullFilter() {
        try {
            af.addFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests setFilters(List filters) method with null List filters, Expected
     * IllegalArgumentException.
     */
    public void testSetFilters_NullFilters() {
        try {
            af.setFilters(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests setFilters(List filters) method with empty List filters, Expected
     * IllegalArgumentException.
     */
    public void testSetFilters_EmptyFilters() {
        try {
            af.setFilters(new ArrayList());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests setFilters(List filters) method with null with List filters, Expected
     * IllegalArgumentException.
     */
    public void testSetFilters_NullWithFilters() {
        List list = new ArrayList();
        list.add(null);

        try {
            af.setFilters(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests setFilters(List filters) method with invalid List filters, Expected
     * IllegalArgumentException.
     */
    public void testSetFilters_InvalidFilters() {
        List list = new ArrayList();
        list.add("invalid");

        try {
            af.setFilters(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
