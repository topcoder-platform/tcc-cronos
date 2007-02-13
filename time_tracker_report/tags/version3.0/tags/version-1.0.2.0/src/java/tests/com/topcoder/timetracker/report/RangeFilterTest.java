/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link RangeFilter}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RangeFilterTest extends TestCase {
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String FILTER_VALUE1 = "blah";
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String FILTER_VALUE2 = "balhblah";
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String FILTER_VALUE3 = "y";
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String FILTER_VALUE4 = "u";
    /**
     * This is the {@link RangeFilter} instance tested in the test cases. It is instantiated in {@link #setUp()}.
     */
    private RangeFilter rangeFilter;

    /**
     * This method tests {@link RangeFilter#RangeFilter(Column, FilterCategory)} for correctness. The values returned
     * from {@link RangeFilter#getColumn()}, {@link RangeFilter#getType()}  and {@link RangeFilter#getCategory()} are
     * also tested.
     */
    public void testCtor() {
        final RangeFilter filter = new RangeFilter(Column.BILLABLE, FilterCategory.BILLABLE);
        assertEquals(Column.BILLABLE, filter.getColumn());
        assertEquals(FilterCategory.BILLABLE, filter.getCategory());
        assertEquals(FilterType.RANGE, filter.getType());
        assertNotNull(filter.getLowerRangeValues());
        assertNotNull(filter.getUpperRangeValues());
    }

    /**
     * This method tests {@link RangeFilter#addFilterRange(String, String)} and {@link
     * RangeFilter#getLowerRangeValues()} + {@link RangeFilter#getUpperRangeValues()} for correctness.
     */
    public void testAddGetFilterRange() {
        List lowerValues = rangeFilter.getLowerRangeValues();
        List upperValues = rangeFilter.getUpperRangeValues();
        assertEquals(0, lowerValues.size());
        assertEquals(0, upperValues.size());

        rangeFilter.addFilterRange(FILTER_VALUE1, FILTER_VALUE2);
        lowerValues = rangeFilter.getLowerRangeValues();
        upperValues = rangeFilter.getUpperRangeValues();
        assertEquals(1, upperValues.size());
        assertEquals(1, lowerValues.size());
        assertTrue(lowerValues.contains(FILTER_VALUE1));
        assertTrue(upperValues.contains(FILTER_VALUE2));

        rangeFilter.addFilterRange(FILTER_VALUE3, FILTER_VALUE4);
        lowerValues = rangeFilter.getLowerRangeValues();
        upperValues = rangeFilter.getUpperRangeValues();
        assertEquals(2, upperValues.size());
        assertEquals(2, lowerValues.size());
        assertTrue(lowerValues.contains(FILTER_VALUE1));
        assertTrue(upperValues.contains(FILTER_VALUE2));
        assertTrue(lowerValues.contains(FILTER_VALUE3));
        assertTrue(upperValues.contains(FILTER_VALUE4));

        try {
            lowerValues.add(FILTER_VALUE2);
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }
        try {
            upperValues.add(FILTER_VALUE3);
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link RangeFilter#RangeFilter(Column, FilterCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the column is <tt>null</tt>
     */
    public void testCtorFailNullColumn() {
        try {
            new RangeFilter(null, FilterCategory.BILLABLE);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link RangeFilter#RangeFilter(Column, FilterCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the category is <tt>null</tt>
     */
    public void testCtorFailNullCategory() {
        try {
            new RangeFilter(Column.BILLABLE, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link RangeFilter#addFilterRange(String, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the lowerValue is <tt>null</tt>
     */
    public void testAddFilterValueFailNullLowerValue() {
        try {
            rangeFilter.addFilterRange(null, FILTER_VALUE1);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link RangeFilter#addFilterRange(String, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the upperValue is <tt>null</tt>
     */
    public void testAddFilterValueFailNullUpperValue() {
        try {
            rangeFilter.addFilterRange(FILTER_VALUE1, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link RangeFilter#addFilterRange(String, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the lowerValue is an empty String
     */
    public void testAddFilterValueFailEmptyLowerValue() {
        try {
            rangeFilter.addFilterRange(" \t ", FILTER_VALUE1);
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link RangeFilter#addFilterRange(String, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the upperValue is an empty String
     */
    public void testAddFilterValueFailEmptyUpperValue() {
        try {
            rangeFilter.addFilterRange(FILTER_VALUE1, " \t ");
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
        rangeFilter = new RangeFilter(Column.BILLABLE, FilterCategory.BILLABLE);
    }
}