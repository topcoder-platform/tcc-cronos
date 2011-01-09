/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for ReviewApplicationConfigurationException class.
 * </p>
 *
 * @author pvmagacho
 * @version 1.1
 */
public class ReviewApplicationConfigurationExceptionUnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ReviewApplicationConfigurationExceptionUnitTests.class);
    }

    /**
     * Accuracy test of <code>ReviewApplicationConfigurationException(String message)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewApplicationConfigurationExceptionAccuracy1() throws Exception {
        ReviewApplicationConfigurationException ce = new ReviewApplicationConfigurationException("test");
        assertEquals("message is incorrect.", "test", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ReviewApplicationConfigurationException(String message, Throwable cause)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewApplicationConfigurationExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ReviewApplicationConfigurationException ce = new ReviewApplicationConfigurationException("error2", e);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }

    /**
     * Accuracy test of
     * <code>ReviewApplicationConfigurationException(String message, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewApplicationConfigurationExceptionAccuracy3() throws Exception {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewApplicationConfigurationException ce = new ReviewApplicationConfigurationException("error2", data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }

    /**
     * Accuracy test of
     * <code>ReviewApplicationConfigurationException(String message, Throwable cause, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewApplicationConfigurationExceptionAccuracy4() throws Exception {
        Exception e = new Exception("error1");
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewApplicationConfigurationException ce = new ReviewApplicationConfigurationException("error2", e, data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }
}
