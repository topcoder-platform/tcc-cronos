/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * Unit tests for the <code>NullFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class NullFilterTests extends AbstractSimpleFilterTestCase {
    /**
     * Returns a concrete instance of <code>AbstractSimpleFilter</code> to test.
     *
     * @param name the field name
     * @param value the value to filter on
     * @return a concrete instance of <code>AbstractSimpleFilter</code> to test
     */
    AbstractSimpleFilter getFilter(String name, Comparable value) {
        return new NullFilter(name);
    }

    /**
     * Override this test case to do nothing, because this implementation of <code>AbstractSimpleFilter</code>
     * allows <code>null</code> values.
     */
    public void test_ctor_null_value() {
    }

    /**
     * Tests the <code>getValue</code> method.
     */
    public void test_getValue() {
        assertNull("getValue() should return null", new NullFilter("name").getValue());
    }
}
