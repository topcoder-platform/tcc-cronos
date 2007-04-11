/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This class tests the <code>ReportException</code> class. It tests:
 * <ul>
 * <li>ReportException(String) constructor</li>
 * <li>ReportException(String, Throwable) constructor</li>
 * </ul>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestReportException extends BaseTestCase {
    /**
     * <p>
     * The instance of <code>ReportException</code> for unit tests.
     * </p>
     */
    private ReportException test = null;

    /**
     * <p>
     * Test if <code>ReportException</code> works properly. With the constructor of
     * <code>ReportException(String)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportException_Ctor1_Usage() throws Exception {
        try {
            throw new ReportException(ERROR_MESSAGE);
        } catch (ReportException e) {
            // success
        }
    }

    /**
     * <p>
     * Detailed test of <code>ReportException(String)</code> constructor. Creates a instance and
     * get it's attributes for test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportException_Ctor1_Detailed() throws Exception {
        // create a exception instance for test.
        test = new ReportException(ERROR_MESSAGE);

        // check null here.
        assertNotNull("Create ReportException failed.", test);

        // check the type here.
        assertTrue("The ReportException should extend from BaseException.", test instanceof BaseException);

        // check error message here.
        assertEquals("Equal error message expected.", ERROR_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test if <code>ReportException</code> works properly. With the constructor of
     * <code>ReportException(String, Throwable)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportException_Ctor2_Usage() throws Exception {
        try {
            throw new ReportException(ERROR_MESSAGE, CAUSE);
        } catch (ReportException e) {
            // success
        }
    }

    /**
     * <p>
     * Detailed test of <code>ReportException(String, Throwable)</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportException_Ctor2_Detailed() throws Exception {

        // create a exception instance for test.
        test = new ReportException(ERROR_MESSAGE, CAUSE);

        // check null here.
        assertNotNull("Create ReportException failed.", test);

        // check the type here.
        assertTrue("The ReportException should extend from BaseException.", test instanceof BaseException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", CAUSE, test.getCause());
    }
}
