/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.informix.TimeEntriesReportBuilder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for <code>TimeEntriesReportBuilder</code>.
 *
 * @author enefem21
 * @version 3.1
 */
public class TimeEntriesReportBuilderTest extends TestCase {

    /** Unit under test. */
    private TimeEntriesReportBuilder timeEntriesReportBuilder;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(TimeEntriesReportBuilderTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {

        TestHelper.loadConfiguration("failure/config.xml");

        timeEntriesReportBuilder = new TimeEntriesReportBuilder();

        super.setUp();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportContainNull() throws Exception {
        try {
            timeEntriesReportBuilder.getReport(null, new String[] {null}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns contains empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportContainEmptyString() throws Exception {
        try {
            timeEntriesReportBuilder.getReport(null, new String[] {"  \n"}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns and ascendingOrders have different
     * length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportNotSameLength() throws Exception {
        try {
            timeEntriesReportBuilder.getReport(null, new String[] {"test"}, new boolean[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns and ascendingOrders have different
     * length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportNotSameLength2() throws Exception {
        try {
            timeEntriesReportBuilder.getReport(null, new String[] {"test"}, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns and ascendingOrders have different
     * length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportNotSameLength3() throws Exception {
        try {
            timeEntriesReportBuilder.getReport(null, null, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
