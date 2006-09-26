/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The unit test cases for class DAOException.
 * @author urtks
 * @version 1.0
 */
public class DAOExceptionTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DAOExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>DAOException(String message)</code>.
     * </p>
     * <p>
     * An instance of DAOException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        Exception e = new DAOException("abc");

        assertNotNull("check the instance", e);
        assertEquals("check message", "abc", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>DAOException(String message, Throwable cause)</code>.
     * </p>
     * <p>
     * An instance of DAOException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor2() throws Exception {
        Exception cause = new Exception();
        Exception e = new DAOException("abc", cause);

        assertNotNull("check the instance", e);
        assertEquals("check message", "abc, caused by null", e.getMessage());
        assertEquals("check message", cause, e.getCause());
    }

}