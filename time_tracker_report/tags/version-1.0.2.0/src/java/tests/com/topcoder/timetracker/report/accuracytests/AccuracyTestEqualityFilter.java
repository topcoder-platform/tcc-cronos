/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.EqualityFilter;
import com.topcoder.timetracker.report.FilterCategory;
import com.topcoder.timetracker.report.FilterType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>EqualityFilter</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestEqualityFilter extends TestCase {
    /** The instance of the Column used for test. */
    private static final Column COLUMN = Column.CLIENT;

    /** The instance of the FilterCategory used for test. */
    private static final FilterCategory CATEGORY = FilterCategory.CLIENT;

    /** The instance of the EqualityFilter used for test. */
    private EqualityFilter filter = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestEqualityFilter.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        filter = new EqualityFilter(COLUMN, CATEGORY);
    }

    /**
     * Tests creating <code>EqualityFilter</code> instance.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertTrue("The column of the filter is not valid.", filter.getColumn().equals(COLUMN));
        assertTrue("The category of the filter is not valid.", filter.getCategory().equals(CATEGORY));
    }

    /**
     * Tests addFilter() and getFilterValues().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testAddAndGetFilterValue() throws Exception {
        final String value1 = "value1";
        final String value2 = "value2";

        filter.addFilterValue(value1);
        filter.addFilterValue(value2);

        List values = filter.getFilterValues();

        assertTrue("The filters list should contain two members.", values.size() == 2);

        assertTrue("The first filter value is not valid.", values.get(0).equals(value1));
        assertTrue("The second filter value is not valid.", values.get(1).equals(value2));
    }

    /**
     * Tests getType().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetType() throws Exception {
        assertTrue("The type of the filter is not valid.", filter.getType().equals(FilterType.EQUALITY));
    }

    /**
     * Tests getColumn().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetColumn() throws Exception {
        assertTrue("The column of the filter is not valid.", filter.getColumn().equals(COLUMN));
    }

    /**
     * Tests getCategory().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetCategory() throws Exception {
        assertTrue("The category of the filter is not valid.", filter.getCategory().equals(CATEGORY));
    }
}
