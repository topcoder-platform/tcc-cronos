/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Filter;

import com.orpheus.administration.persistence.impl.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>SearchCriteriaDTOImpl</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class SearchCriteriaDTOImplTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * filter.
     */
    public void test_ctor_null() {
        try {
            new SearchCriteriaDTOImpl(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of the constructor and the <code>getSearchFilter</code> method.
     */
    public void test_getSearchFilter() {
        Filter filter = new EqualToFilter("test", "test");
        assertEquals("getSearchFilter should return the filter passed to the constructor",
                     filter, new SearchCriteriaDTOImpl(filter).getSearchFilter());
    }
}
