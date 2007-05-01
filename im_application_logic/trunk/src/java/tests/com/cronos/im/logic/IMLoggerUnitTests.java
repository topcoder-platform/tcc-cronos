/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import java.text.DateFormat;

import com.topcoder.util.log.Level;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for IMLogger class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IMLoggerUnitTests extends TestCase {

    /**
     * An instance of IMLogger for the following tests.
     */
    private IMLogger logger = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        logger = new IMLogger(null);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the constructor IMLogger(DateFormat dateFormat). No exception should be thrown.
     */
    public void test_constructor1_1() {
        new IMLogger(null);
    }

    /**
     * Tests the constructor IMLogger(DateFormat dateFormat). No exception should be thrown.
     */
    public void test_constructor1_2() {
        new IMLogger(DateFormat.getDateInstance());
    }

    /**
     * Tests the constructor IMLogger(String logName, DateFormat dateFormat). No exception should be thrown.
     */
    public void test_constructor2_1() {
        new IMLogger("name", null);
    }

    /**
     * Tests the constructor IMLogger(String logName, DateFormat dateFormat). No exception should be thrown.
     */
    public void test_constructor2_2() {
        new IMLogger("name", DateFormat.getDateInstance());
    }

    /**
     * Tests the constructor IMLogger(String logName, DateFormat dateFormat). No exception should be thrown.
     */
    public void test_constructor2_3() {
        new IMLogger(null, DateFormat.getDateInstance());
    }

    /**
     * Tests the constructor IMLogger(String logName, DateFormat dateFormat). No exception should be thrown.
     */
    public void test_constructor2_4() {
        new IMLogger(null, null);
    }

    /**
     * Tests the method log(Level level, String action, String[] entities). No exception should be thrown.
     */
    public void test_log1_1() {
        logger.log(Level.INFO, "action", new String[] {"entity1", "entity2"});
    }

    /**
     * Tests the method log(Level level, String action, String[] entities). No exception should be thrown.
     */
    public void test_log1_2() {
        logger.log(Level.INFO, "action", null);
    }

    /**
     * Tests the method log(Level level, String action, String[] entities). No exception should be thrown.
     */
    public void test_log1_3() {
        logger.log(Level.INFO, "action", new String[] {"entity1", "entity2"});
    }

    /**
     * Tests the method log(Level level, String message). No exception should be thrown.
     */
    public void test_log2_1() {
        logger.log(Level.DEBUG, "message");
    }

    /**
     * Tests the method log(Level level, String action, long[] userIds, long[] sessionIds). No exception
     * should be thrown.
     */
    public void test_log3_1() {
        logger.log(Level.INFO, "action", new long[] {1, 2, 3}, new long[] {4, 5, 6});
    }

    /**
     * Tests the method log(Level level, String action, long[] userIds, long[] sessionIds). No exception
     * should be thrown.
     */
    public void test_log3_2() {
        logger.log(Level.INFO, "action", null, null);
    }

}
