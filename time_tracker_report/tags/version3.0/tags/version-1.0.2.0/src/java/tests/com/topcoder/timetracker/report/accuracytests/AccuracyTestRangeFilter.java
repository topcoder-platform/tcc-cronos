/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.FilterCategory;
import com.topcoder.timetracker.report.FilterType;
import com.topcoder.timetracker.report.RangeFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>RangeFilter</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestRangeFilter extends TestCase {
    /** The instance of the Column used for test. */
    private static final Column COLUMN = Column.DATE;

    /** The instance of the FilterCategory used for test. */
    private static final FilterCategory CATEGORY = FilterCategory.DATE;

    /** The instance of the RangeFilter used for test. */
    private RangeFilter filter = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestRangeFilter.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        filter = new RangeFilter(COLUMN, CATEGORY);
    }

    /**
     * Tests Creating <code>RangeFilter</code> with legal parameters.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertNotNull("The instance of RangeFilter is not valid.", filter);
    }

    /**
     * Tests addFilterRange(), getUpperRangeValues() and getLowerRangeValues().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testAddAndGetFilterRanges() throws Exception {
        final String lowerValue1 = "lower1";
        final String lowerValue2 = "lower2";
        final String upperValue1 = "upper1";
        final String upperValue2 = "upper2";

        filter.addFilterRange(lowerValue1, upperValue1);
        filter.addFilterRange(lowerValue2, upperValue2);

        List lowers = filter.getLowerRangeValues();

        assertTrue("There should be two members.", lowers.size() == 2);

        assertTrue("The first lower range value is not valid.", lowers.get(0).equals(lowerValue1));
        assertTrue("The second lower range value is not valid.", lowers.get(1).equals(lowerValue2));

        List uppers = filter.getUpperRangeValues();

        assertTrue("There should be two members.", uppers.size() == 2);

        assertTrue("The first upper range value is not valid.", uppers.get(0).equals(upperValue1));
        assertTrue("The second upper range value is not valid.", uppers.get(1).equals(upperValue2));
    }

    /**
     * Tests getType().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetType() throws Exception {
        assertTrue("The type of this filter is not valid.", filter.getType().equals(FilterType.RANGE));
    }

    /**
     * Tests getColumn().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetColumn() throws Exception {
        assertTrue("The column of this filter is not valid.", filter.getColumn().equals(COLUMN));
    }

    /**
     * Tests getCategory().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetCategory() throws Exception {
        assertTrue("The category of this filter is not valid.", filter.getCategory().equals(CATEGORY));
    }
}
