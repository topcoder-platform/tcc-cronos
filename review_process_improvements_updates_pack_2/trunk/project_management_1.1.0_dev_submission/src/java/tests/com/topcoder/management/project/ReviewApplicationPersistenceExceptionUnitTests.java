/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for ReviewApplicationPersistenceException class.
 * </p>
 *
 * @author pvmagacho
 * @version 1.1
 */
public class ReviewApplicationPersistenceExceptionUnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ReviewApplicationPersistenceExceptionUnitTests.class);
    }

    /**
     * Accuracy test of <code>ReviewApplicationPersistenceException(String message)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewApplicationPersistenceExceptionAccuracy1() throws Exception {
        ReviewApplicationPersistenceException ce = new ReviewApplicationPersistenceException("test");
        assertEquals("message is incorrect.", "test", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ReviewApplicationPersistenceException(String message, Throwable cause)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewApplicationPersistenceExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ReviewApplicationPersistenceException ce = new ReviewApplicationPersistenceException("error2", e);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }
}
