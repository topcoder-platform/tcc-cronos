/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for <code>{@link ReliabilityCalculationUtility}</code> class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReliabilityCalculationUtilityUnitTests {
    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the command line arguments used in tests.
     * </p>
     */
    private String[] args;

    /**
     * <p>
     * Represents the security manager.
     * </p>
     */
    private SecurityManager oldSecurityManager;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReliabilityCalculationUtilityUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        connection = TestsHelper.getConnection();
        TestsHelper.clearDB(connection);
        TestsHelper.loadDB(connection);

        // Save the security manager
        oldSecurityManager = System.getSecurityManager();
        // Throw SecurityException if System.exit called
        System.setSecurityManager(new ExitSecurityManager());

        args = new String[] {"-c", TestsHelper.TEST_FILES + "ReliabilityCalculationUtility.properties"};
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        // Set the security manager
        System.setSecurityManager(oldSecurityManager);

        TestsHelper.clearDB(connection);
        TestsHelper.closeConnection(connection);
    }

    /**
     * <p>
     * Accuracy test for the method <code>main(String[] args)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_main_1() throws Exception {
        ReliabilityCalculationUtility.main(args);

        assertEquals("'main' should be correct.",
            2, TestsHelper.getRowsCount(connection, "project_reliability"));
        assertEquals("'main' should be correct.",
            0, TestsHelper.getRowsCount(connection, "user_reliability"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>main(String[] args)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_main_2() throws Exception {
        args = new String[] {"-c", TestsHelper.TEST_FILES + "ReliabilityCalculationUtility.properties",
            "-pc", "1,2", "-u", "yes"};

        ReliabilityCalculationUtility.main(args);

        assertEquals("'main' should be correct.",
            2, TestsHelper.getRowsCount(connection, "project_reliability"));
        assertEquals("'main' should be correct.",
            1, TestsHelper.getRowsCount(connection, "user_reliability"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>main(String[] args)</code> with no argument.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_Helpe_1() {
        args = new String[] {};

        ReliabilityCalculationUtility.main(args);
    }

    /**
     * <p>
     * Accuracy test for the method <code>main(String[] args)</code> with '-h'.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_Helpe_2() {
        args = new String[] {"-h"};

        ReliabilityCalculationUtility.main(args);
    }

    /**
     * <p>
     * Accuracy test for the method <code>main(String[] args)</code> with '-?'.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_Helpe_3() {
        args = new String[] {"-?"};

        ReliabilityCalculationUtility.main(args);
    }

    /**
     * <p>
     * Accuracy test for the method <code>main(String[] args)</code> with '-?'.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_Helpe_4() {
        args = new String[] {"-help"};

        ReliabilityCalculationUtility.main(args);
    }

    /**
     * <p>
     * Failure test for the method <code>main(String[] args)</code> with '-c' is incorrect.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_cIncorrect() {
        args = new String[] {"-c", "not_exist"};

        callMainFailure(args);
    }

    /**
     * <p>
     * Failure test for the method <code>main(String[] args)</code> with '-pc' is incorrect.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_pcIncorrect() {
        args = new String[] {"-c", TestsHelper.TEST_FILES + "ReliabilityCalculationUtility.properties",
            "-pc", "invalid_numm"};

        callMainFailure(args);
    }

    /**
     * <p>
     * Failure test for the method <code>main(String[] args)</code> with '-u' is incorrect.<br>
     * <code>System.exit</code> is expected.
     * </p>
     */
    @Test
    public void test_main_uIncorrect() {
        args = new String[] {"-c", TestsHelper.TEST_FILES + "ReliabilityCalculationUtility.properties",
            "-u", "invalid"};

        callMainFailure(args);
    }

    /**
     * <p>
     * Tests the main method. <code>System.exit</code> is expected.
     * </p>
     *
     * @param args
     *            the arguments.
     */
    private static void callMainFailure(String[] args) {
        try {
            ReliabilityCalculationUtility.main(args);

            fail("System.exit is expected.");
        } catch (SecurityException e) {
            // Good
        }
    }
}
