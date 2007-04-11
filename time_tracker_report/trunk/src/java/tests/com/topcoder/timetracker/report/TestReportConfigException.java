/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

/**
 * <p>
 * This class tests the <code>ReportConfigException</code> class. It tests:
 * <ul>
 * <li>ReportConfigException(String) constructor</li>
 * <li>ReportConfigException(String, Throwable) constructor</li>
 * </ul>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestReportConfigException extends BaseTestCase {
    /**
     * <p>
     * The instance of <code>ReportConfigException</code> for unit tests.
     * </p>
     */
    private ReportConfigException test = null;

    /**
     * <p>
     * Test if <code>ReportConfigException</code> works properly. With the constructor of
     * <code>ReportConfigException(String)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportConfigException_Ctor1_Usage() throws Exception {
        try {
            throw new ReportConfigException(ERROR_MESSAGE);
        } catch (ReportConfigException e) {
            // success
        }
    }

    /**
     * <p>
     * Detailed test of <code>ReportConfigException(String)</code> constructor. Creates a instance
     * and get it's attributes for test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportConfigException_Ctor1_Detailed() throws Exception {
        // create a exception instance for test.
        test = new ReportConfigException(ERROR_MESSAGE);

        // check null here.
        assertNotNull("Create ReportConfigException failed.", test);

        // check the type here.
        assertTrue(
                "The ReportConfigException should extend from ReportException.",
                test instanceof ReportException);

        // check error message here.
        assertEquals("Equal error message expected.", ERROR_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test if <code>ReportConfigException</code> works properly. With the constructor of
     * <code>ReportConfigException(String, Throwable)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportConfigException_Ctor2_Usage() throws Exception {
        try {
            throw new ReportConfigException(ERROR_MESSAGE, CAUSE);
        } catch (ReportConfigException e) {
            // success
        }
    }

    /**
     * <p>
     * Detailed test of <code>ReportConfigException(String, Throwable)</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportConfigException_Ctor2_Detailed() throws Exception {

        // create a exception instance for test.
        test = new ReportConfigException(ERROR_MESSAGE, CAUSE);

        // check null here.
        assertNotNull("Create ReportConfigException failed.", test);

        // check the type here.
        assertTrue(
                "The ReportConfigException should extend from ReportException.",
                test instanceof ReportException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", CAUSE, test.getCause());
    }
}
