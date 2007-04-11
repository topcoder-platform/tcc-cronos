/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;

/**
 * <p>
 * This class provides failure tests for <code>TimeEntriesReportBuilder</code> class. It tests:
 * <ol>
 * <li>TimeEntriesReportBuilder() constructor</li>
 * <li>getReport() method</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestTimeEntriesReportBuilderFailure extends BaseTestCase {
    /**
     * <p>
     * This is an instance of TimeEntriesReportBuilder which will be used in test cases.
     * </p>
     */
    private TimeEntriesReportBuilder builder = null;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.setupDatabase(DATA_FILE_LOCATION);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.clearDatabase();
        super.tearDown();
    }

    /**
     * <p>
     * Retrieves a TimeEntriesReportBuilder to use in test cases.
     * </p>
     *
     * @throws Exception to JUnit
     * @return a builder to test
     */
    private TimeEntriesReportBuilder getTimeEntriesReportBuilder() throws Exception {
        return new TimeEntriesReportBuilder();
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns = null </li>
     * <li> ascendingOrders is not null, not empty</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_1_NullColumnNotNullOrder() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, null, new boolean[] {true, true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns = null </li>
     * <li> ascendingOrders is not null, but empty</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_2_NullColumnNotEmptyOrder() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, null, new boolean[] {});
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, but contains a null element </li>
     * <li> ascendingOrders is not null, contains the same number of element with sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_3_ColumnsContainNull1() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"a", null, "b" }, new boolean[] {true, true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, but contains a null element </li>
     * <li> ascendingOrders is not null, contains the different number of element with
     * sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_4_ColumnsContainNull2() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"a", null, "b" }, new boolean[] {true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, but contains an empty string </li>
     * <li> ascendingOrders is not null, contains the same number of element with sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_5_ColumnsContainEmptyString1()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"a", "b", "" }, new boolean[] {true, true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, but contains an empty string </li>
     * <li> ascendingOrders is not null, contains the different number of element with
     * sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_6_ColumnsContainEmptyString2()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"a", "b", "" }, new boolean[] {true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, but contains an empty string after being trimmed </li>
     * <li> ascendingOrders is not null, contains the same number of element with sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_7_ColumnsContainEmptyStringTrimmed1()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"   ", "b", "c" }, new boolean[] {true, true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, but contains an empty string after being trimmed</li>
     * <li> ascendingOrders is not null, contains the different number of element with
     * sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_8_ColumnsContainEmptyStringTrimmed2()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"   ", "b", "c" }, new boolean[] {true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, all elements are valid</li>
     * <li> ascendingOrders is null</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_9_ValidComlumnsNullOrder() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"a", "b", "c" }, null);
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <li> filter = null </li>
     * <li> sortingColumns not null, all elements are valid</li>
     * <li> ascendingOrders is not null, contains the different number of element with
     * sortingColumns</li>
     * <li> IllegalArgumentException is expected </li>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Failure_10_DifferentLengths() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        try {
            builder.getReport(null, new String[] {"a", "b", "c" }, new boolean[] {true, false });
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
