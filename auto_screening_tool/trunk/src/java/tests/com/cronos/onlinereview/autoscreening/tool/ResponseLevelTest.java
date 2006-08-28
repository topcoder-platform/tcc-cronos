/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The unit test cases for class ResponseLevel.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResponseLevelTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ResponseLevelTest.class);
    }

    /**
     * <p>
     * Accuracy Test for the string values of the enum.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        assertEquals("check enum 'pass' value", "pass", ResponseLevel.PASS.toString());
        assertEquals("check enum 'warn' value", "warn", ResponseLevel.WARN.toString());
        assertEquals("check enum 'fail' value", "fail", ResponseLevel.FAIL.toString());
    }
}