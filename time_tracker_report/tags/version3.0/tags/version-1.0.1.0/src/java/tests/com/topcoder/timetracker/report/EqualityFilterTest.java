/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link EqualityFilter}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EqualityFilterTest extends TestCase {
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String FILTER_VALUE1 = "blah";
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String FILTER_VALUE2 = "balhblah";
    /**
     * This is the {@link EqualityFilter} instance tested in the test cases. It is instantiated in {@link #setUp()}.
     */
    private EqualityFilter equalityFilter;

    /**
     * This method tests {@link EqualityFilter#EqualityFilter(Column, FilterCategory)} for correctness. The values
     * returned from {@link EqualityFilter#getColumn()}, {@link EqualityFilter#getType()}  and {@link
     * EqualityFilter#getCategory()} are also tested.
     */
    public void testCtor() {
        final EqualityFilter filter = new EqualityFilter(Column.BILLABLE, FilterCategory.BILLABLE);
        assertEquals(Column.BILLABLE, filter.getColumn());
        assertEquals(FilterCategory.BILLABLE, filter.getCategory());
        assertEquals(FilterType.EQUALITY, filter.getType());
        assertNotNull(filter.getFilterValues());
    }

    /**
     * This method tests {@link EqualityFilter#addFilterValue(String)} and {@link EqualityFilter#getFilterValues()} for
     * correctness.
     */
    public void testAddGetFilterValue() {
        List filterValues = equalityFilter.getFilterValues();
        assertEquals(0, filterValues.size());

        equalityFilter.addFilterValue(FILTER_VALUE1);
        filterValues = equalityFilter.getFilterValues();
        assertEquals(1, filterValues.size());
        assertTrue(filterValues.contains(FILTER_VALUE1));

        equalityFilter.addFilterValue(FILTER_VALUE2);
        filterValues = equalityFilter.getFilterValues();
        assertEquals(2, filterValues.size());
        assertTrue(filterValues.contains(FILTER_VALUE1));
        assertTrue(filterValues.contains(FILTER_VALUE2));

        try {
            filterValues.add(FILTER_VALUE2);
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link EqualityFilter#EqualityFilter(Column, FilterCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the column is <tt>null</tt>
     */
    public void testCtorFailNullColumn() {
        try {
            new EqualityFilter(null, FilterCategory.BILLABLE);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link EqualityFilter#EqualityFilter(Column, FilterCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the category is <tt>null</tt>
     */
    public void testCtorFailNullCategory() {
        try {
            new EqualityFilter(Column.BILLABLE, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link EqualityFilter#addFilterValue(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the filterValue is <tt>null</tt>
     */
    public void testAddFilterValueFailNullValue() {
        try {
            equalityFilter.addFilterValue(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link EqualityFilter#addFilterValue(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the filterValue is an empty String
     */
    public void testAddFilterValueFailEmptyValue() {
        try {
            equalityFilter.addFilterValue(" \t ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        equalityFilter = new EqualityFilter(Column.BILLABLE, FilterCategory.BILLABLE);
    }
}