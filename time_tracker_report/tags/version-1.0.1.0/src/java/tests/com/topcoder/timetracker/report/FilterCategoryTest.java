/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link FilterCategory}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FilterCategoryTest extends TestCase {

    /**
     * This method tests {@link FilterCategory#getCategoryValue()} for correctness.
     */
    public void testGetCategoryValue() {
        assertEquals("FILTER_BILLABLE", FilterCategory.BILLABLE.getCategoryValue());
        assertEquals("FILTER_CLIENT", FilterCategory.CLIENT.getCategoryValue());
        assertEquals("FILTER_DATE", FilterCategory.DATE.getCategoryValue());
        assertEquals("FILTER_EMPLOYEE", FilterCategory.EMPLOYEE.getCategoryValue());
        assertEquals("FILTER_PROJECT", FilterCategory.PROJECT.getCategoryValue());
    }

    /**
     * This method tests {@link FilterCategory#toString()} for correctness.
     */
    public void testToString() {
        assertEquals("FILTER_BILLABLE", FilterCategory.BILLABLE.toString());
        assertEquals("FILTER_CLIENT", FilterCategory.CLIENT.toString());
        assertEquals("FILTER_DATE", FilterCategory.DATE.toString());
        assertEquals("FILTER_EMPLOYEE", FilterCategory.EMPLOYEE.toString());
        assertEquals("FILTER_PROJECT", FilterCategory.PROJECT.toString());
    }

    /**
     * This method tests {@link FilterCategory#getFilterCategories()}.
     */
    public void testGetFilterCategories() {
        final List filterCategories = FilterCategory.getFilterCategories();
        assertTrue(filterCategories.contains(FilterCategory.BILLABLE));
        assertTrue(filterCategories.contains(FilterCategory.CLIENT));
        assertTrue(filterCategories.contains(FilterCategory.DATE));
        assertTrue(filterCategories.contains(FilterCategory.EMPLOYEE));
        assertTrue(filterCategories.contains(FilterCategory.PROJECT));
        assertEquals(5, filterCategories.size());
        try {
            filterCategories.add(new Object());
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }

    }

}