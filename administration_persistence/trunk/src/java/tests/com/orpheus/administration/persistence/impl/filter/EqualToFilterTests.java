/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * Unit tests for the <code>EqualToFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class EqualToFilterTests extends AbstractSimpleFilterTestCase {
    /**
     * Returns a concrete instance of <code>AbstractSimpleFilter</code> to test.
     *
     * @param name the field name
     * @param value the value to filter on
     * @return a concrete instance of <code>AbstractSimpleFilter</code> to test
     */
    AbstractSimpleFilter getFilter(String name, Comparable value) {
        return new EqualToFilter(name, value);
    }
}
