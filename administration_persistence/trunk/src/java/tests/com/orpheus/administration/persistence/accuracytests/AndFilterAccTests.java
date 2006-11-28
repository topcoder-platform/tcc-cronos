/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.orpheus.administration.persistence.Filter;
import com.orpheus.administration.persistence.impl.filter.AndFilter;
import com.orpheus.administration.persistence.impl.filter.EqualToFilter;
import com.orpheus.administration.persistence.impl.filter.GreaterThanFilter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Accuracy test cases for class AndFilter.
 * </p>
 * @author waits
 * @version 1.0
 */
public class AndFilterAccTests extends TestCase {
    /** A filter used in some tests. */
    private static final Filter FILTER2 = new EqualToFilter("name", "value2");

    /** the filter to construct the filter list in AndFilter. */
    private Filter f1 = null;

    /** the filter to construct the filter list in AndFilter. */
    private Filter f2 = null;

    /** the AndFilter instance to test. */
    private AndFilter andFilter = null;

    /**
     * setUp.
     */
    public void setUp() {
        /**
         * get the filters.
         */
        f1 = new GreaterThanFilter("The age", new Integer(1));
        f2 = new EqualToFilter("The age", new Integer(10));

        andFilter = new AndFilter(f1, f2);
    }

    /**
     * test the construct with exception.
     */
    public void testconstruct() {
        try {
            List list = new ArrayList();
            list.add(f1);
            list.add(f2);
            andFilter = new AndFilter(list);
        } catch (Exception e) {
            fail("no Exception should be throw");
        }
    }

    /**
     * test the construct with exception.
     */
    public void testconstruct2() {
        try {
            andFilter = new AndFilter(f1, f2);
        } catch (Exception e) {
            fail("no Exception should be throw");
        }
    }

    /**
     * Verify behavior of the addFilter method. Check if the filter is correctly added.
     */
    public void testAddFilter() {
        andFilter.addFilter(FILTER2);

        List filters = andFilter.getFilters();

        assertEquals("Filter is not added", 3, filters.size());
        assertEquals("Incorrect filter", FILTER2, filters.get(2));
    }

    /**
     * Verify behavior of the getFilters method.
     */
    public void testGetFilters() {
        List filters = andFilter.getFilters();

        assertEquals("List has incorrect size", 2, filters.size());
        assertEquals("List contains invalid items", f1, filters.get(0));
    }
}
