/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * Unit tests for the <code>LessThanFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class LessThanFilterTests extends AbstractSimpleFilterTestCase {
    /**
     * Returns a concrete instance of <code>AbstractSimpleFilter</code> to test.
     *
     * @param name the field name
     * @param value the value to filter on
     * @return a concrete instance of <code>AbstractSimpleFilter</code> to test
     */
    AbstractSimpleFilter getFilter(String name, Comparable value) {
        return new LessThanFilter(name, value);
    }

    /**
     * Tests the constructor.
     */
    public void test_ctor() {
        LessThanFilter filter = new LessThanFilter("field", "upper");
        assertEquals("upper threshold should be set", "upper", filter.getUpperThreshold());
        assertFalse("upper should not be inclusive", filter.isUpperInclusive());
    }
}
