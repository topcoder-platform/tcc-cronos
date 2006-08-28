/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The unit test cases for class UpdateFailedException.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpdateFailedExceptionTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UpdateFailedExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>UpdateFailedException(String message)</code>.
     * </p>
     * <p>
     * An instance of UpdateFailedException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        Exception e = new UpdateFailedException("abc");

        assertNotNull("check the instance", e);
        assertEquals("check message", "abc", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>UpdateFailedException(String message, Throwable e)</code>.
     * </p>
     * <p>
     * An instance of UpdateFailedException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor2() throws Exception {
        Exception cause = new Exception();
        Exception e = new UpdateFailedException("abc", cause);

        assertNotNull("check the instance", e);
        assertEquals("check message", "abc, caused by null", e.getMessage());
        assertEquals("check message", cause, e.getCause());
    }
}