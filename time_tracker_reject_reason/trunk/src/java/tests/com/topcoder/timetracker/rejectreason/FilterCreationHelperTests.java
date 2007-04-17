/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;

import junit.framework.TestCase;


/**
 * Unit test for <code>FilterCreationHelper</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FilterCreationHelperTests extends TestCase {
    /**
     * Tests createRangeFilter method with two null arguments. IllegalArgumentException should be thrown.
     */
    public void testCreateRangeFilter_bothNull() {
        try {
            FilterCreationHelper.createRangeFilter("test", null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests createRangeFilter method with an invalid range. IllegalArgumentException should be thrown.
     */
    public void testCreateRangeFilter_invalidRange() {
        try {
            FilterCreationHelper.createRangeFilter("test", new Integer(100), new Integer(10));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests createRangeFilter method with null lower bound. Verifies correctly <code>LessThanOrEqualToFilter</code> is
     * returned.
     */
    public void testCreateRangeFilter_nullLower_accuracy() {
        Comparable value = new Long(90);
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) FilterCreationHelper.createRangeFilter("test", null,
                value);
        assertEquals("The column name is not set correctly.", "test", filter.getName());
        assertEquals("The upper bound is not set correctly.", value, filter.getUpperThreshold());
    }

    /**
     * Tests createRangeFilter method with null upper bound. Verifies correctly <code>GreaterThanOrEqualToFilter</code>
     * is returned.
     */
    public void testCreateRangeFilter_nullUpper_accuracy() {
        Comparable value = new Long(90);
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) FilterCreationHelper.createRangeFilter("test",
                value, null);
        assertEquals("The column name is not set correctly.", "test", filter.getName());
        assertEquals("The lower bound is not set correctly.", value, filter.getLowerThreshold());
    }

    /**
     * Tests createEntryDateFilter method with not null lower bound and upper bound. Verifies correctly
     * <code>BetweenFilter</code> is returned.
     */
    public void testCreateRangeFilter_accuracy() {
        Comparable start = new Long(1);
        Comparable end = new Long(2);

        BetweenFilter filter = (BetweenFilter) FilterCreationHelper.createRangeFilter("test", start, end);
        assertEquals("The column name is not set correctly.", "test", filter.getName());
        assertEquals("The lower bound is not set correctly.", start, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", end, filter.getUpperThreshold());
    }
}
