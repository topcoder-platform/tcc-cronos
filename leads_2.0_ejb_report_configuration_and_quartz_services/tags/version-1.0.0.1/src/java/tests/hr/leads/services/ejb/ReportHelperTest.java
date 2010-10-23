/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * <p>
 * All tests for <code>ReportHelper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportHelperTest extends TestCase {

    /**
     * <p>
     * Represents the method name using in the logger methods.
     * </p>
     */
    private static final String METHOD_NAME = "ReportHelperTest.TestCase";

    /**
     * <p>
     * Represents the logger using in testing ReportHelper.
     * </p>
     */
    private Logger logger = null;


    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        logger = Logger.getLogger("UnitTest");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * <p>
     * Tests method: {@link ReportHelper#checkNullOrEmpty(String, String)}.
     * </p>
     *
     * <p>
     * If the argument is null, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testCheckNullOrEmptyNull() {
        try {
            ReportHelper.checkNullOrEmpty(logger, METHOD_NAME, null, "test name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportHelper#checkNullOrEmpty(String, String)}.
     * </p>
     *
     * <p>
     * If the argument is empty, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testCheckNullOrEmptyEmpty() {
        try {
            ReportHelper.checkNullOrEmpty(logger, METHOD_NAME, "  ", "test name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            ReportHelper.checkNullOrEmpty(logger, METHOD_NAME, "", "test name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportHelper#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * If the argument is null, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testCheckNull() {
        try {
            ReportHelper.checkNull(logger, METHOD_NAME, null, "test name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests constructor: {ReportHelper#ReportHelper()}.
     * </p>
     *
     * <p>
     * Check if the constructor is private.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testReportHelper() throws Exception {
        Constructor<ReportHelper> constructor = ReportHelper.class.getDeclaredConstructor();
        assertFalse("cannot be accessible.", constructor.isAccessible());
        constructor.setAccessible(true);
        // execute it
        constructor.newInstance(new Object[]{});

    }

    /**
     * <p>
     * Tests method: {@link ReportHelper#checkContainsNullOrEmpty(String[], String)}.
     * </p>
     *
     * <p>
     * If it is null, throw IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void checkContainsNullOrEmptyNull() throws Exception {
        try {
            ReportHelper.checkContainsNullOrEmpty(logger, METHOD_NAME, null, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportHelper#checkContainsNullOrEmpty(String[], String)}.
     * </p>
     *
     * <p>
     * If it is Empty, throw IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void checkContainsNullOrEmptyEmpty() throws Exception {
        try {
            ReportHelper.checkContainsNullOrEmpty(logger, METHOD_NAME, new String[] {}, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportHelper#checkContainsNullOrEmpty(String[], String)}.
     * </p>
     *
     * <p>
     * If it contains null, throw IllegalArgumentException.
     * </p>
     * @throws Exception to Junit.
     */
    public void checkContainsNullOrEmptyContainsNull() throws Exception {
        try {
            ReportHelper.checkContainsNullOrEmpty(logger, METHOD_NAME, new String[] {null}, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportHelper#checkContainsNullOrEmpty(String[], String)}.
     * </p>
     *
     * <p>
     * If it contains empty, throw IllegalArgumentException.
     * </p>
     * @throws Exception to Junit.
     */
    public void checkContainsNullOrEmptyContainsEmpty() throws Exception {
        try {
            ReportHelper.checkContainsNullOrEmpty(logger, METHOD_NAME, new String[] {""}, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests the method {@link ReportHelper#logEntrance(Logger, String)}.
     * </p>
     *
     * <p>
     * Checks if it can properly log.
     * </p>
     */
    public void testLogEntrance() {
        logger = Logger.getLogger("UnitTest");
        try {
            ReportHelper.logEntrance(logger, "testMethod");
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }
    }

    /**
     * <p>
     * Tests the method {@link ReportHelper#logParameters(Logger, Object[], String[])}.
     * </p>
     *
     * <p>
     * Checks if it can properly log.
     * </p>
     */
    public void testLogParameters() {
        logger = Logger.getLogger("UnitTest");
        try {
            ReportHelper.logParameters(logger, new Object[] {new Long(1), new String("abc")},
                    new String[] {"param1", "param2"});
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }
    }

    /**
     * <p>
     * Tests the method {@link ReportHelper#logError(Logger, String, String}.
     * </p>
     *
     * <p>
     * Checks if it can properly log.
     * </p>
     */
    public void testLogError() {
        logger = Logger.getLogger("UnitTest");
        try {
            ReportHelper.logError(logger, "testMethod", "some error occurs.");
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }
    }

    /**
     * <p>
     * Tests the method {@link ReportHelper#logOutput(Logger, Object).
     * </p>
     *
     * <p>
     * Checks if it can properly log.
     * </p>
     */
    public void testLogOutput() {
        logger = Logger.getLogger("UnitTest");
        try {
            ReportHelper.logOutput(logger, "123");
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }

        try {
            ReportHelper.logOutput(logger, null);
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }
    }

    /**
     * <p>
     * Tests the method {@link ReportHelper#logExit(Logger, String).
     * </p>
     *
     * <p>
     * Checks if it can properly log.
     * </p>
     */
    public void testLogExit() {
        logger = Logger.getLogger("UnitTest");
        try {
            ReportHelper.logExit(logger, "fun");
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }
    }

}
