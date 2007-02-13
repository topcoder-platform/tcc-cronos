/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests.searchflters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.timetracker.project.searchfilters.NotFilter;

/**
 * The class <code>NotFilterAccuracytest</code> contains tests for the class {@link <code>NotFilter</code>}.
 * @author FireIce
 * @version 1.1
 */
public class NotFilterAccuracytest extends TestCase {

    /**
     * Represents the filter instance passed to constructor.
     */
    private Filter innerFilter = new MockFilter();

    /**
     * Represents the NotFilter instance.
     */
    private NotFilter instance = new NotFilter(innerFilter);

    /**
     * accuracy test for constructor.
     */
    public void testCtorAccuracy() {
        Filter mockFilter = new MockFilter();
        NotFilter filter = new NotFilter(mockFilter);
        // should implement the Filter interface.
        assertTrue(filter instanceof Filter);

        // field should be set correctly.
        assertSame("field not set correctly", mockFilter, filter.getOperand());
    }

    /**
     * accuracy test for getOperand method.
     */
    public void testGetOperand() {
        // field should be set correctly.
        assertSame("field not set correctly", innerFilter, instance.getOperand());
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(NotFilterAccuracytest.class);
    }
}
