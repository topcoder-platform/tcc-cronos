/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.report.ReportConfigException;
import com.topcoder.timetracker.report.ReportDataAccessException;
import com.topcoder.timetracker.report.informix.InformixReportSearchBuilder;
import com.topcoder.timetracker.report.informix.ReportEntryBean;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for <code>InformixReportSearchBuilder</code>.
 *
 * @author enefem21
 * @version 3.1
 */
public class InformixReportSearchBuilderTest extends TestCase {

    /** Unit under test. */
    private MockInformixReportSearchBuilder informixReportSearchBuilder;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InformixReportSearchBuilderTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfiguration("failure/config.xml");

        informixReportSearchBuilder = new MockInformixReportSearchBuilder();

        super.setUp();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test <code>getCustomResultSet</code> for failure. Condition: the sortingColumns contains
     * null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit
     */
    public void testGetCustomResultSetContainNull() throws Exception {
        try {
            informixReportSearchBuilder.getCustomResultSet(null, new String[] {null }, new boolean[] {false });
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getCustomResultSet</code> for failure. Condition: the sortingColumns contains
     * empty string. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit
     */
    public void testGetCustomResultSetContainEmptyString() throws Exception {
        try {
            informixReportSearchBuilder.getCustomResultSet(
                    null,
                    new String[] {"  \n" },
                    new boolean[] {false });
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getCustomResultSet</code> for failure. Condition: the sortingColumns and
     * ascendingOrders have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit
     */
    public void testGetCustomResultSetNotSameLength() throws Exception {
        try {
            informixReportSearchBuilder.getCustomResultSet(null, new String[] {"test" }, new boolean[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getCustomResultSet</code> for failure. Condition: the sortingColumns and
     * ascendingOrders have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit
     */
    public void testGetCustomResultSetNotSameLength2() throws Exception {
        try {
            informixReportSearchBuilder.getCustomResultSet(null, new String[] {"test" }, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getCustomResultSet</code> for failure. Condition: the sortingColumns and
     * ascendingOrders have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit
     */
    public void testGetCustomResultSetNotSameLength3() throws Exception {
        try {
            informixReportSearchBuilder.getCustomResultSet(null, null, new boolean[] {false });
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Mock class for <code>InformixReportSarchBuilder</code>.
     */
    public class MockInformixReportSearchBuilder extends InformixReportSearchBuilder {

        /**
         * Mock constructor.
         *
         * @throws ReportConfigException
         */
        protected MockInformixReportSearchBuilder() throws ReportConfigException {
            super();
        }

        protected String getSearchBundleName() {

            return null;
        }

        public ReportEntryBean[] getReport(Filter filter, String[] sortingColumns, boolean[] ascendingOrders)
            throws ReportDataAccessException {

            return null;
        }

        public CustomResultSet getCustomResultSet(Filter filter, String[] sortingColumns,
                boolean[] ascendingOrders) throws ReportDataAccessException {
            return super.getCustomResultSet(filter, sortingColumns, ascendingOrders);
        }

    }

}
