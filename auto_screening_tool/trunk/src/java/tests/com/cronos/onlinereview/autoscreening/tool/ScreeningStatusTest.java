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
public class ScreeningStatusTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreeningStatusTest.class);
    }

    /**
     * <p>
     * Accuracy Test for the string values of the enum.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        assertEquals("check enum 'pending' value", "Pending", ScreeningStatus.PENDING.toString());
        assertEquals("check enum 'screening' value", "Screening", ScreeningStatus.SCREENING
            .toString());
        assertEquals("check enum 'failed' value", "Failed", ScreeningStatus.FAILED.toString());
        assertEquals("check enum 'passed' value", "Passed", ScreeningStatus.PASSED.toString());
        assertEquals("check enum 'passed with warning' value", "Passed with Warning",
            ScreeningStatus.PASSED_WITH_WARNING.toString());

        assertEquals("check enum 'pending' value", "Pending", ScreeningStatus.PENDING.getName());
        assertEquals("check enum 'screening' value", "Screening", ScreeningStatus.SCREENING
            .getName());
        assertEquals("check enum 'failed' value", "Failed", ScreeningStatus.FAILED.getName());
        assertEquals("check enum 'passed' value", "Passed", ScreeningStatus.PASSED.getName());
        assertEquals("check enum 'passed with warning' value", "Passed with Warning",
            ScreeningStatus.PASSED_WITH_WARNING.getName());
    }

    /**
     * <p>
     * Accuracy Test for the ids of the enum.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor2() throws Exception {
        assertEquals("check enum 'pending' id", 1, ScreeningStatus.PENDING.getId());
        assertEquals("check enum 'screening' id", 2, ScreeningStatus.SCREENING.getId());
        assertEquals("check enum 'failed' id", 3, ScreeningStatus.FAILED.getId());
        assertEquals("check enum 'passed' id", 4, ScreeningStatus.PASSED.getId());
        assertEquals("check enum 'passed with warning' id", 5, ScreeningStatus.PASSED_WITH_WARNING
            .getId());
    }
}