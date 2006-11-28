/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>NotFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class NotFilterTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * filter.
     */
    public void test_ctor_null() {
        try {
            new NotFilter(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the constructor and the <code>getFilter</code> method.
     */
    public void test_getFilter() {
        Filter filter = new EqualToFilter("name", "value");

        assertEquals("getFilter() should return the filter passed to the constructor",
                     filter, new NotFilter(filter).getFilter());
    }
}
