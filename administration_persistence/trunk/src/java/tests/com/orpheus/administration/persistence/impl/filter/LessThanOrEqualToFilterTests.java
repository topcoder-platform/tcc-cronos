/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * Unit tests for the <code>LessThanOrEqualToFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class LessThanOrEqualToFilterTests extends AbstractSimpleFilterTestCase {
    /**
     * Returns a concrete instance of <code>AbstractSimpleFilter</code> to test.
     *
     * @param name the field name
     * @param value the value to filter on
     * @return a concrete instance of <code>AbstractSimpleFilter</code> to test
     */
    AbstractSimpleFilter getFilter(String name, Comparable value) {
        return new LessThanOrEqualToFilter(name, value);
    }

    /**
     * Tests the constructor.
     */
    public void test_ctor() {
        LessThanOrEqualToFilter filter = new LessThanOrEqualToFilter("field", "upper");
        assertEquals("upper threshold should be set", "upper", filter.getUpperThreshold());
        assertTrue("upper should be inclusive", filter.isUpperInclusive());
    }
}
