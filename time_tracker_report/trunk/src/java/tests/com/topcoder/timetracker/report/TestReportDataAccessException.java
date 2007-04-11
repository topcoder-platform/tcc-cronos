/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

/**
 * <p>
 * This class tests the <code>ReportDataAccessException</code> class. It tests:
 * <ul>
 * <li>ReportDataAccessException(String) constructor</li>
 * <li>ReportDataAccessException(String, Throwable) constructor</li>
 * </ul>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestReportDataAccessException extends BaseTestCase {
    /**
     * <p>
     * The instance of <code>ReportDataAccessException</code> for unit tests.
     * </p>
     */
    private ReportDataAccessException test = null;

    /**
     * <p>
     * Test if <code>ReportDataAccessException</code> works properly. With the constructor of
     * <code>ReportDataAccessException(String)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDataAccessException_Ctor1_Usage() throws Exception {
        try {
            throw new ReportDataAccessException(ERROR_MESSAGE);
        } catch (ReportDataAccessException e) {
            // success
        }
    }

    /**
     * <p>
     * Detailed test of <code>ReportDataAccessException(String)</code> constructor. Creates a
     * instance and get it's attributes for test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDataAccessException_Ctor1_Detailed() throws Exception {
        // create a exception instance for test.
        test = new ReportDataAccessException(ERROR_MESSAGE);

        // check null here.
        assertNotNull("Create ReportDataAccessException failed.", test);

        // check the type here.
        assertTrue(
                "The ReportDataAccessException should extend from ReportException.",
                test instanceof ReportException);

        // check error message here.
        assertEquals("Equal error message expected.", ERROR_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test if <code>ReportDataAccessException</code> works properly. With the constructor of
     * <code>ReportDataAccessException(String, Throwable)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDataAccessException_Ctor2_Usage() throws Exception {
        try {
            throw new ReportDataAccessException(ERROR_MESSAGE, CAUSE);
        } catch (ReportDataAccessException e) {
            // success
        }
    }

    /**
     * <p>
     * Detailed test of <code>ReportDataAccessException(String, Throwable)</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDataAccessException_Ctor2_Detailed() throws Exception {

        // create a exception instance for test.
        test = new ReportDataAccessException(ERROR_MESSAGE, CAUSE);

        // check null here.
        assertNotNull("Create ReportDataAccessException failed.", test);

        // check the type here.
        assertTrue(
                "The ReportDataAccessException should extend from ReportException.",
                test instanceof ReportException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", CAUSE, test.getCause());
    }
}
